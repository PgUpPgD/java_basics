package com.example.excel.designMode.chainOfResponsibilityPattern.test;

public class Demo {

    public static Logger getLogger(){
        Logger c = new CLogger(Logger.Info);
        Logger f = new FLogger(Logger.Debug);
        Logger e = new ELogger(Logger.Error);

        e.setNextLoger(f);
        f.setNextLoger(c);
        return e;
    }

    public static void main(String[] args) {
        Logger logger = getLogger();
        //logger.logMsg(Logger.Info, "info");
        //logger.logMsg(Logger.Debug, "debug");
        logger.logMsg(Logger.Error, "error");
    }

}
