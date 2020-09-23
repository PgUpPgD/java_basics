package com.example.excel.oop;

public class Bclass implements Binterface {
    @Override
    public void method1() {
        System.out.println("MyClass method1");
    }

    //接口中的default方法，子类可以直接调用，也可以重写，但是必须去掉default
    //default是接口中专用的
    @Override
    public void method3() {
        Binterface.super.method3();//!!! 如何调用接口的default方法
    }

    @Override
    public void method3(String name) {
        Binterface.super.method3("ywq");
    }

    public static void main(String[] args) {
        Binterface myClass = new Bclass();
        myClass.method1();
        myClass.method3();
        myClass.method3("abc");//default方法只能通过对象名调用
        //myClass.method2();//static 报错
        Binterface.method2();//静态方法只能通过接口名调用
        //因为一个类可能实现多个接口,万一多个接口中都有static的且非抽象的method2
    }

}
