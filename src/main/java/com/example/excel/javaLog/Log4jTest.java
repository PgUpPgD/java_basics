package com.example.excel.javaLog;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.junit.Test;

public class Log4jTest {
    @Test
    public void testQuick() throws Exception {
        //开启 log4j 内置的日志记录
        LogLog.setInternalDebugging(true);
        // 初始化系统配置，不需要配置文件  有配置文件则注释
        //BasicConfigurator.configure();
        // 创建日志记录器对象
        Logger logger = Logger.getLogger(Log4jTest.class);
        // 日志记录输出
        logger.info("hello log4j");
        // 日志级别
        for (int i = 0; i < 1; i++){
            logger.fatal("fatal"); // 严重错误，一般会造成系统崩溃和终止运行
            logger.error("error"); // 错误信息，但不会影响系统运行
            logger.warn("warn"); // 警告信息，可能会发生问题
            logger.info("info"); // 程序运行信息，数据库的连接、网络、IO操作等
            logger.debug("debug"); // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
            logger.trace("trace"); // 追踪信息，记录程序的所有流程信息
        }

        //在创建一个测试log4j.logger.org.apache = error
        Logger logger1 = Logger.getLogger(Logger.class);
        logger1.fatal("fatal"); // 严重错误，一般会造成系统崩溃和终止运行
        logger1.error("error"); // 错误信息，但不会影响系统运行
        logger1.warn("warn"); // 警告信息，可能会发生问题
        logger1.info("info"); // 程序运行信息，数据库的连接、网络、IO操作等
        logger1.debug("debug"); // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
        logger1.trace("trace"); // 追踪信息，记录程序的所有流程信息
    }

}
