package com.example.excel.designMode.chainOfResponsibilityPattern.test;

public class FLogger extends Logger {

    public FLogger(int level){
        this.level = level;
    }

    @Override
    protected void write(String msg) {
        System.out.println("FLogger" + msg);
    }
}
