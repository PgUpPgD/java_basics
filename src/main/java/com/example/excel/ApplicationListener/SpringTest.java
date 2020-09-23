package com.example.excel.ApplicationListener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.yml");
        //创建一个applicationEvent对象
        EmailEvent event = new EmailEvent("hello","abc@163.com","This is a test");
        //触发该事件
        context.publishEvent(event);
    }
}
