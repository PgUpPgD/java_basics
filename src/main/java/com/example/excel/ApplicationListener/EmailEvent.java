package com.example.excel.ApplicationListener;

import lombok.Data;
import org.springframework.context.ApplicationEvent;
//事件定义
@Data
public class EmailEvent extends ApplicationEvent {
    private String address;
    private String text;
    public EmailEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }
    public EmailEvent(Object source) {
        super(source);
    }
}
