package com.example.excel.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//@Aspect
//@Component
public class PayFlowAspect {

    private Logger logger = LoggerFactory.getLogger("PayFlowAspect");

    @Pointcut("execution(public * com.example.excel.controller.*Controller.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint point) throws Throwable {
        logger.info(">>>>>>");
        printLog(point);
    }

    @After(value = "log()")
    public void doAfter(JoinPoint point) {
        logger.info("<<<<<<");
        printLog(point);
    }

    private void printLog(JoinPoint point) {
        HttpServletRequest request = getHttpRequest();

        if (request == null) {
            return;
        }

        logger.info("URL          : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD  : " + request.getMethod());
        //logger.info("IP           : " + IpAddressUtil.getIpAddress(request));
        logger.info("QUERY PARAMS : " + request.getQueryString());
        logger.info("CLASS_METHOD : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        logger.info("ARGS         : " + Arrays.toString(point.getArgs()));
    }

    private HttpServletRequest getHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;
        if (attributes == null) {
            logger.info("attributes          : null");
            return null;
        }
        request = attributes.getRequest();
        return request;
    }
}
