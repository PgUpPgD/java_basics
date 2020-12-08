package com.example.excel.designMode.chainOfResponsibilityPattern.test;

public class CLogger extends Logger {

    public CLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String msg) {
        System.out.println("CLogger" + msg);
    }
}
