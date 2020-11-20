package com.example.excel.applicationListener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

//@Component    //监听ContextRefreshedEvent
public class TestApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        System.out.println("1.---------" + contextRefreshedEvent);
    }


}
