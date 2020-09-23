package com.example.excel.oop;

public class Parent {

    static {
        System.out.println("parent static");
    }

    {
        System.out.println("parent 普通代码块");
    }

    public Parent(){
        System.out.println("parent 构造方法");
    }

}
