package com.example.excel.oop;

public interface Binterface {
    void method1();

    static void method2(){
        System.out.println("JDK8后接口中可以有static修饰的非抽象方法");
    }

    default void method3(){
        System.out.println("JDK8后接口中可以有非static的非抽象方法，必须添加default");
        System.out.println("这个default可不是switch的default，也不是默认权限修饰符");

    }

    default void method3(String name){
        method3();
        System.out.println(name);
    }

    public static void main(String[] args) {
        Binterface.method2();
    }
}
