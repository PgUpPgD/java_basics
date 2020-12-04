package com.example.excel.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 *  前置通知(before)：目标方法运行之前调用
 * 	后置通知(after-returning)：在目标方法运行之后调用 (如果出现异常不会调用)
 * 	环绕通知(around)：在目标方法之前和之后都调用(ProceedingJoinPoint对象 -->> 调用proceed方法)
 * 	异常拦截通知(after-throwing)：如果出现异常,就会调用
 * 	最终通知(after)：在目标方法运行之后调用 (无论是否出现 异常都会调用)
 */

//通知类
//@Aspect
//@Component
public class MyAdvice {

    //自己设置一个切点，管理重复代码
    @Pointcut("execution(* com.example.excel.controller.IndexController.sourceC(..))")
    public void pc(){}
    //前置通知
    //指定该方法是前置通知,并制定切入点
    @Before("MyAdvice.pc()")
    public void before(){
        System.out.println("这是前置通知!!");
    }
    //后置通知
    @AfterReturning("execution(* com.example.excel.controller.IndexController.*(..))")
    public void afterReturning(){
        System.out.println("这是后置通知(如果出现异常不会调用)!!");
    }
    //环绕通知
    @Around("execution(* com.example.excel.controller.IndexController.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("这是环绕通知之前的部分!!");
        Object proceed = pjp.proceed();//调用目标方法
        System.out.println("这是环绕通知之后的部分!!");
        return proceed;
    }
    //异常通知
    @AfterThrowing("execution(* com.example.excel.controller.IndexController.*(..))")
    public void afterException(){
        System.out.println("出事啦!出现异常了!!");
    }
    //后置通知
    @After("execution(* com.example.excel.controller.IndexController.*(..))")
    public void after(){
        System.out.println("这是后置通知(出现异常也会调用)!!");
    }
}
