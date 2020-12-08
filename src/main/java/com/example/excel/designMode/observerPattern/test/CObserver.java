package com.example.excel.designMode.observerPattern.test;

public class CObserver extends Observer {
    public CObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("CObserver" + subject.getState());
    }
}
