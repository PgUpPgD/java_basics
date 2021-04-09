package com.example.excel.javaLog;

import org.junit.Test;

import java.io.InputStream;
import java.util.logging.*;

public class JULTest {

    //java.util.logging.*;   该日志不用导包，jdk自带
    //鼠标操作：
    // 1.按住ALT,鼠标左键下滑，会选择这一列
    // 2.按住shift + alt,鼠标左键点击，可点击出多个光标

    @Test
    public void testQuick()throws Exception{
        //1.创建日志记录器对象
        Logger logger = Logger.getLogger("com.example.excel.javaLog.JULTest");
        //2.日志记录输出
        logger.info("hello jul");

        logger.log(Level.INFO, "info msg");

        String name = "jack";
        Integer age = 18;

        logger.log(Level.INFO, "用户信息：{0}，{1}", new Object[]{name, age});
    }

    @Test
    public void testLogLevel() throws Exception {
        // 1.获取日志对象
        Logger logger = Logger.getLogger("com.example.excel.javaLog.JULTest");
        // 2.日志记录输出
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");  //默认级别  比默认级别高的会显示，反之不会显示
        logger.config("cofnig");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    /**
     *  介绍：
     *  Application -> Logger -> Handler -> Outside World
     *                  |           \
     *                 filter       filter
     *  通常时应用程序访问日志系统的入口程序。
     *  Appenders：也被称为Handlers，每个Logger都会关联一组Handlers，Logger会将日志交给关联
     *  Handlers处理，由Handlers负责将日志做记录。Handlers在此是一个抽象，其具体的实现决定了
     *  日志记录的位置可以是控制台、文件、网络上的其他日志服务或操作系统日志等。
     *  Layouts：也被称为Formatters，它负责对日志事件中的数据进行转换和格式化。Layouts决定了
     *  数据在一条日志记录中的最终形式。
     *  Level：每条日志消息都有一个关联的日志级别。该级别粗略指导了日志消息的重要性和紧迫，我
     *  可以将Level和Loggers，Appenders做关联以便于我们过滤消息。
     *  Filters：过滤器，根据需要定制哪些信息会被记录，哪些信息会被放过。
     *
     *  总结：
     *  用户使用Logger来进行日志记录，Logger持有若干个Handler，日志的输出操作是由Handler完成的。
     *  在Handler在输出日志前，会经过Filter的过滤，判断哪些日志级别过滤放行哪些拦截，Handler会将日
     *  志内容输出到指定位置（日志文件、控制台等）。Handler在输出日志时会使用Layout，将输出内容进
     *  行排版。
     */
    @Test
    @SuppressWarnings("all")
    public void testLogConfig() throws Exception {
        //1.创建日志记录器对象
        Logger logger = Logger.getLogger("com.example.excel.javaLog.JULTest");

        //一、自定义日志级别
        //a.关闭系统的默认配置
        logger.setUseParentHandlers(false);
        //b.创建handler对象
        ConsoleHandler consoleHandler = new ConsoleHandler();
        //c.创建formatter对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        //d.进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
        //e.设置日志级别
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);

        //二、输出到日志文件
        //a.创建handler对象                     需要先在该地址创建好目录和文件
        FileHandler fileHandler = new FileHandler("d:/logs/jul.log");
        //b.进行关联
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);

        //2.日志记录输出
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.config("cofnig");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    //Logger对象的父子关系
    @Test
    @SuppressWarnings("all")
    public void testParent() throws Exception{

        Logger logger1 = Logger.getLogger("com.example.excel");
        Logger logger2 = Logger.getLogger("com.example");

        //测试      true   logger1的父对象logger2
        System.out.println(logger1.getParent()==logger2);
        //所有日志记录器对象的顶级父元素 class为java.util.logging.LogManager$RootLogger name为""
        System.out.println("logger2 parent:" + logger2.getParent() + "，name：" +
                logger2.getParent().getName());

        //一、自定义日志级别
        //a.关闭系统的默认配置
        logger2.setUseParentHandlers(false);
        //b.创建handler对象
        ConsoleHandler consoleHandler = new ConsoleHandler();
        //c.创建formatter对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        //d.进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger2.addHandler(consoleHandler);
        //e.设置日志级别
        logger2.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);

        //2.日志记录输出      logger1继承父对象logger2的日志规则
        logger1.severe("severe");
        logger1.warning("warning");
        logger1.info("info");
        logger1.config("cofnig");
        logger1.fine("fine");
        logger1.finer("finer");
        logger1.finest("finest");
    }

    @Test
    @SuppressWarnings("all")
    public void testProperties() throws Exception {
        // 读取自定义配置文件            通过类加载器
        InputStream in = JULTest.class.getClassLoader().getResourceAsStream("jul_log.properties");
        // 获取日志管理器对象
        LogManager logManager = LogManager.getLogManager();
        // 通过日志管理器加载配置文件
        logManager.readConfiguration(in);

        Logger logger = Logger.getLogger("com.example.excel");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");

        Logger logger2 = Logger.getLogger("TEST");
        logger2.severe("severe");
        logger2.warning("warning");
        logger2.info("info");
        logger2.config("config");
        logger2.fine("fine");
        logger2.finer("finer");
        logger2.finest("finest");
    }

}
