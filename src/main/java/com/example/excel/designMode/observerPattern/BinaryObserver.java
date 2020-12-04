package com.example.excel.designMode.observerPattern;

//步骤 3
//创建实体观察者类。
public class BinaryObserver extends Observer{

    public BinaryObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println( "Binary String: "
                + Integer.toBinaryString( subject.getState())  + "----" + subject.getState());
    }
}