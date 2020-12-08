package com.example.excel.designMode.chainOfResponsibilityPattern.test;

public class ELogger extends Logger {

    public ELogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String msg) {
        System.out.println("ELogger" + msg);
    }
}
