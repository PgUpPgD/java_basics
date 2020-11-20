package com.example.excel.reflect;

/**
 * 优点：单例对象的生成是在需要使用单例对象的时候才去构造
 * 可以提高应用的启动速度
 * 缺点：不是线程安全的
 */
public class Singleton {

    private static Singleton singleton = null;
    //私有构造器，不让外部通过new创建对象
    private Singleton(){}
    public static Singleton getInstance(){
        if (null == singleton){
            //当为空的时候，在类的内部通过new关键字
            singleton = new Singleton();
        }
        return singleton;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();
        Singleton singleton1 = Singleton.getInstance();
        if (singleton == singleton1){
            System.out.println("1");
        }

    }

}
