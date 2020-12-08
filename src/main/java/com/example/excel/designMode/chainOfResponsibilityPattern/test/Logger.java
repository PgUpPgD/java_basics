package com.example.excel.designMode.chainOfResponsibilityPattern.test;

public abstract class Logger {
    public static int Info = 1;
    public static int Debug = 2;
    public static int Error = 3;

    protected int level;

    //责任链中的下一个元素
    protected Logger nextLoger;

    public void setNextLoger(Logger loger){
        this.nextLoger = loger;
    }

    public void logMsg(int level, String msg){
        if (this.level <= level){
            System.out.println(this.level);
            write(msg);
        }
        if (nextLoger != null){
            nextLoger.logMsg(level, msg);
        }
    }

    abstract protected void write(String msg);
}
