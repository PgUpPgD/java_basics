package com.example.excel.designMode.observerPattern.test;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();
    public int state;

    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state = state;
        notifyAllObserver();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObserver(){
        for (Observer observer : observers){
            observer.update();
        }
    }

    public static void main(String[] args) {
        Subject subject = new Subject();
        new BObserver(subject);
        new CObserver(subject);
        subject.setState(19);

    }
}
