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

    public String a(String a){
        return  "";
    }
    public String a(String a, String b){
        String[] v = new String[]{};
        String[] x = new String[8];
        return  "";
    }


}
