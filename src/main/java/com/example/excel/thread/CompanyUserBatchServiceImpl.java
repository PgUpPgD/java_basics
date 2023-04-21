package com.example.excel.thread;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Description: 多线程事务回滚  参考
 * @Author: tangHC
 * @Date: 2023/4/21 11:01
 */
public class CompanyUserBatchServiceImpl{
    private static final Logger logger = LoggerFactory.getLogger(CompanyUserBatchServiceImpl.class);

    public Object addNewCurrentCompanyUsers(String params) {
        List<String> companyUsers = new ArrayList<>();
        //每条线程最小处理任务数
        int perThreadHandleCount = 1;
        //线程池的最大线程数
        int nThreads = 10;
        int taskSize = 1000;

        if (taskSize > nThreads * perThreadHandleCount) {
            perThreadHandleCount = taskSize % nThreads == 0 ? taskSize / nThreads : taskSize / nThreads + 1;
            nThreads = taskSize % perThreadHandleCount == 0 ? taskSize / perThreadHandleCount : taskSize / perThreadHandleCount + 1;
        } else {
            nThreads = taskSize;
        }

        logger.info("批量添加参保人taskSize: {}, perThreadHandleCount: {}, nThreads: {}", taskSize, perThreadHandleCount, nThreads);
        CountDownLatch mainLatch = new CountDownLatch(1);
        //监控子线程
        CountDownLatch threadLatch = new CountDownLatch(nThreads);
        //根据子线程执行结果判断是否需要回滚
        BlockingDeque<Boolean> resultList = new LinkedBlockingDeque<>(nThreads);
        //必须要使用对象，如果使用变量会造成线程之间不可共享变量值
        RollBack rollBack = new RollBack(false);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads);

        List<Future<List<Object>>> futures = Lists.newArrayList();
        List<Object> returnDataList = Lists.newArrayList();
        //给每个线程分配任务
        for (int i = 0; i < nThreads; i++) {
            int lastIndex = (i + 1) * perThreadHandleCount;
            List<String> companyUserResultVos = companyUsers.subList(i * perThreadHandleCount, lastIndex >= taskSize ? taskSize : lastIndex);
            AddNewCompanyUserThread addNewCompanyUserThread = new AddNewCompanyUserThread(mainLatch, threadLatch, rollBack, resultList, companyUserResultVos);
            Future<List<Object>> future = fixedThreadPool.submit(addNewCompanyUserThread);
            futures.add(future);
        }

        /** 存放子线程返回结果. */
        List<Boolean> backUpResult = Lists.newArrayList();
        try {
            //等待所有子线程执行完毕
            boolean await = threadLatch.await(20, TimeUnit.SECONDS);
            //如果超时，直接回滚
            if (!await) {
                rollBack.setIsRollBack(true);
            } else {
                logger.info("创建参保人子线程执行完毕，共 {} 个线程", nThreads);
                //查看执行情况，如果有存在需要回滚的线程，则全部回滚
                for (int i = 0; i < nThreads; i++) {
                    Boolean result = resultList.take();
                    backUpResult.add(result);
                    logger.debug("子线程返回结果result: {}", result);
                    if (result) {
                        /** 有线程执行异常，需要回滚子线程. */
                        rollBack.setIsRollBack(true);
                    }
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            //子线程再次开始执行
            mainLatch.countDown();
            logger.info("关闭线程池，释放资源");
            fixedThreadPool.shutdown();
        }

        /** 检查子线程是否有异常，有异常整体回滚. */
        for (int i = 0; i < nThreads; i++) {
            if (CollectionUtils.isNotEmpty(backUpResult)) {
                Boolean result = backUpResult.get(i);
                if (result) {
                    logger.info("创建参保人失败，整体回滚");
                    throw new RuntimeException();
                }
            } else {
                logger.info("创建参保人失败，整体回滚");
                throw new RuntimeException();
            }
        }

        //拼接结果
        try {
            for (Future<List<Object>> future : futures) {
                returnDataList.addAll(future.get());
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return null;
    }

    public class AddNewCompanyUserThread implements Callable<List<Object>> {
        /**
         * 主线程监控
         */
        private CountDownLatch mainLatch;
        /**
         * 子线程监控
         */
        private CountDownLatch threadLatch;
        /**
         * 是否回滚
         */
        private RollBack rollBack;
        private BlockingDeque<Boolean> resultList;
        private List<String> taskList;

        public AddNewCompanyUserThread(CountDownLatch mainLatch, CountDownLatch threadLatch, RollBack rollBack, BlockingDeque<Boolean> resultList, List<String> taskList) {
            this.mainLatch = mainLatch;
            this.threadLatch = threadLatch;
            this.rollBack = rollBack;
            this.resultList = resultList;
            this.taskList = taskList;
        }

        @Override
        public List<Object> call() {
            //为了保证事务不提交，此处只能调用一个有事务的方法，spring 中事务的颗粒度是方法，只有方法不退出，事务才不会提交
            return this.addNewCompanyUsers(mainLatch, threadLatch, rollBack, resultList, taskList);
        }

        public List<Object> addNewCompanyUsers(CountDownLatch mainLatch, CountDownLatch threadLatch, CompanyUserBatchServiceImpl.RollBack rollBack, BlockingDeque<Boolean> resultList, List<String> taskList) {
            List<Object> returnDataList = Lists.newArrayList();
            Boolean result = false;
            logger.debug("线程: {}创建参保人条数 : {}", Thread.currentThread().getName(), taskList.size());
            try {
//                for (CompanyUserResultVo companyUserResultVo : taskList) {
//                    ReturnData returnData = addSingleCompanyUser(companyUserResultVo);
//                    if (returnData.getRetCode() == CommonConstants.RETURN_CODE_FAIL) {
//                        result = true;
//                    }
//                    returnDataList.add(returnData.getData());
//                }
                //Exception 和 Error 都需要抓
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                logger.info("线程: {}创建参保人出现异常： {} ", Thread.currentThread().getName(), throwable);
                result = true;
            }

            resultList.add(result);
            threadLatch.countDown();
            logger.info("子线程 {} 计算过程已经结束，等待主线程通知是否需要回滚", Thread.currentThread().getName());

            try {
                mainLatch.await();
                logger.info("子线程 {} 再次启动", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                logger.error("批量创建参保人线程InterruptedException异常");
                throw new RuntimeException();
            }

            if (rollBack.getIsRollBack()) {
                logger.error("批量创建参保人线程回滚, 线程: {}, 需要更新的信息taskList: {}",
                        Thread.currentThread().getName(),
                        JSONObject.toJSONString(taskList));
                logger.info("子线程 {} 执行完毕，线程退出", Thread.currentThread().getName());
                throw new RuntimeException();
            }

            logger.info("子线程 {} 执行完毕，线程退出", Thread.currentThread().getName());
            return returnDataList;
        }

    }

    @Data
    public class RollBack implements Serializable {
        public RollBack(Boolean isRollBack){
            this.isRollBack = isRollBack;
        }
        private Boolean isRollBack;

        public void setIsRollBack(Boolean isRollBack){
            this.isRollBack = isRollBack;
        }
    }


}