package com.example.excel.designMode.observerPattern;

//步骤 2
//创建 Observer 类。
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
