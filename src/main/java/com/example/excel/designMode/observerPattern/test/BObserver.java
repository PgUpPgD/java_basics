package com.example.excel.designMode.observerPattern.test;

public class BObserver extends Observer {

    public BObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("BObserver" + subject.getState());
    }
}
