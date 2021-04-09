package com.example.excel.javaLog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * 日志门面JLC，换日志框架不用改动代码  被Apache在2014年放弃
 * 没有log4j包则使用jdk自带的JUL,有则使用log4j
 */
public class JlcTest {
    @Test
    public void testQuick() throws Exception {
        // 创建日志对象
        Log log = LogFactory.getLog(JULTest.class);
        // 日志记录输出
        log.fatal("fatal");
        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
    }
}
