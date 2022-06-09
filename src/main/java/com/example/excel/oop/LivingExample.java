package com.example.excel.oop;

public class LivingExample {
    static int a = 0; //类变量
    private int b = 0; //实例变量

    public static void main(String[] args) {
        LivingExample example1 = new LivingExample();
        LivingExample example2 = new LivingExample();

        example1.a = 3; //等同于LivingExample.a = 3;
        example1.b = 3;
        example2.a = 4;
        example2.b = 4;
        //实例变量只改变自身，类变量即静态成员变量，
        //所有的实例对象都共用一个类变量
        //存放位置
        //类变量随着类的加载而加载存在于metaspace元空间（方法区）中.
        //实例变量随着对象的建立而存在于堆内存中.
        //4 - 3 - 4 - 4
        System.out.println(example1.a + "--" + example1.b + "--" + example2.a + "--" + example2.b);

    }

}
