package com.example.excel.applicationListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

//@Component
public class EmailNotifier implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof EmailEvent){
            EmailEvent emailEvent = (EmailEvent)event;
            System.out.println("4.----------邮件地址：" + emailEvent.getAddress());
            System.out.println("4.----------邮件内容：" + emailEvent.getText());
        }else {
            System.out.println("3.----------容器本身事件：" + event.getSource());
        }
    }

}
