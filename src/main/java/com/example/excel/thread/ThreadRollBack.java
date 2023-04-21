package com.example.excel.thread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Description:
 * @Author: tangHC
 * @Date: 2023/4/21 11:33
 */
public class ThreadRollBack {
    @Autowired
    private PlatformTransactionManager transactionManager;

    public void test() throws InterruptedException {
        CountDownLatch rollBackLatch = new CountDownLatch(1);
        CountDownLatch mainThreadLatch = new CountDownLatch(5);
        AtomicBoolean rollbackFlag = new AtomicBoolean(false);
        for (int i = 0; i < 5; i++) {
            int t = i;
            new Thread(() -> {
                if (rollbackFlag.get())
                    return;
                //如果其他线程已经报错 就停止线程
                //设置一个事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                // 事物隔离级别，开启新事务，这样会比较安全些。
                TransactionStatus status = transactionManager.getTransaction(def);
                // 获得事务状态
                try {
//                    AreaController.this.insert();
                    // 插入一条数据
                    if (t == 4) throw new RuntimeException("报错");
                    mainThreadLatch.countDown();
                    rollBackLatch.await();
                    //线程等待
                    if (rollbackFlag.get()) {
                        transactionManager.rollback(status);
                    } else {
                        transactionManager.commit(status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // 如果出错了 就放开锁 让别的线程进入提交/回滚  本线程进行回滚
                    rollbackFlag.set(true);
                    rollBackLatch.countDown();
                    mainThreadLatch.countDown();
                    transactionManager.rollback(status);
                }
            }).start();
        }
        try {
            int i = 1 / 0;
            // 主线程执行相关业务报错
        } catch (Exception e) {
            rollbackFlag.set(true);
            rollBackLatch.countDown();
        }
        // 主线程业务执行完毕 如果其他线程也执行完毕 且没有报异常 正在阻塞状态中 唤醒其他线程 提交所有的事务
        // 如果其他线程或者主线程报错 则不会进入if 会触发回滚
        if (!rollbackFlag.get()) {
            mainThreadLatch.await();
            rollBackLatch.countDown();
        }
    }
}
