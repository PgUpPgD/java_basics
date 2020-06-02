package com.example.excel.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyLogAspect {

    // 1.PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
    // 切面最主要的就是切点，所有的故事都围绕切点发生
    // logPointCut()代表切点名称
    @Pointcut("@annotation(com.example.excel.foundation.MyLog)")
    public void logPointCut(){};

    //2.环绕通知
    @Around("logPointCut()")
    public void logAround(ProceedingJoinPoint joinPoint){
        //获取方法名称
        String methodName = joinPoint.getSignature().getName();
        //获取入参
        Object[] param = joinPoint.getArgs();
        StringBuffer buffer = new StringBuffer();
        for (Object o : param){
            buffer.append(o + ";");
        }
        System.out.println("进入[" + methodName + "]方法,参数为:" + buffer.toString());
        //继续执行方法
        try {
            //该方法接收使用该注解的方法的返回值
            String result = (String)joinPoint.proceed();
            System.out.println(result);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        System.out.println(methodName + "方法执行结束");
    }
}
