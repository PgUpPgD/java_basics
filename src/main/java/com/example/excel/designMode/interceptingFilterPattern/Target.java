package com.example.excel.designMode.interceptingFilterPattern;

//步骤 3
//创建 Target
public class Target {
    public void execute(String request){
        System.out.println("Executing request: " + request);
    }
}
