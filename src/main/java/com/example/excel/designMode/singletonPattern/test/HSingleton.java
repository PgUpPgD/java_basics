package com.example.excel.designMode.singletonPattern.test;

public class HSingleton {

    private static HSingleton singleton = new HSingleton();
    private HSingleton(){}
    public HSingleton getInstance(){
        return singleton;
    }

}
