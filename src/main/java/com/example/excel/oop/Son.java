package com.example.excel.oop;

public class Son extends Parent{
    static {
        System.out.println("Son static");
    }

    {
        System.out.println("Son 普通代码块");
    }

    public Son(){
        System.out.println("Son 构造方法");
    }

    public static void main(String[] args) {
        short i1 = 1; i1 += 1;
        //short i1 = 1; i1 = i1+1;   //报错
        Son son = new Son();
    }

    //parent static
    //Son static
    //parent 普通代码块
    //parent 构造方法
    //Son 普通代码块
    //Son 构造方法

}
