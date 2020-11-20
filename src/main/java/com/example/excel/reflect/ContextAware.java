package com.example.excel.reflect;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextAware 通过它Spring容器会自动把上下文环境对象调用
 * ApplicationContextAware接口中的setApplicationContext方法。
 * 我们在ApplicationContextAware的实现类中，就可以通过这个上下文环境对象得到Spring容器中的Bean。
 * Aware就是属性注入，实现了这个接口的bean，当spring容器初始化的时候，会自动的将ApplicationContext注入进来
 */
//@Component
public class ContextAware implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        context = applicationContext;
    }
    //获得applicationContext
    public static ApplicationContext getApplicationContext() {
        //assertContextInjected();
        return context;
    }
    public static void clearHolder(){
        context=null;
    }
    //获取Bean
    public static <T> T getBean(Class<T> requiredType){
        //assertContextInjected();
        return (T) getApplicationContext().getBean(requiredType);
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) getApplicationContext().getBean(name);
    }
    //判断application是否为空
    public static void assertContextInjected(){
        Validate.isTrue(context==null, "application未注入 ，请在springContext.xml中注入SpringHolder!");
    }

    public static void main(String[] args) {
        //例：util用到一个dao的实例  未测试
        ContextAware.getBean("DemoDAOImpl");
    }
}
