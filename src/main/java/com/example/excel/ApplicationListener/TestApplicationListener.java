package com.example.excel.ApplicationListener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component    //监听ContextRefreshedEvent
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("1.---------" + contextRefreshedEvent);
        System.out.println("2.---------TestApplicationListener............................");
    }
}
