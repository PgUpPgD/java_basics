package com.example.excel.entity;

import lombok.Data;
import org.springframework.context.annotation.Scope;

@Data
@Scope(scopeName = "prototype")
public class Scope1 {
    public Scope1(){
        System.out.println("Scope1 初始化了。。。");
    }
    private int state;
}
