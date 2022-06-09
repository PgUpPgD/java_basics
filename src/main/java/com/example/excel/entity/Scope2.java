package com.example.excel.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Scope2 {
    public Scope2(){
        System.out.println("Scope2 初始化了。。。");
    }
    private int state;
}
