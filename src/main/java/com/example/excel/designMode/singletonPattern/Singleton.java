package com.example.excel.designMode.singletonPattern;

public class Singleton {

    //1、懒汉式，线程不安全
//    private static Singleton instance;
//    private Singleton (){}
//    public static  Singleton getInstance() {
    //2.效率很低 线程安全
//    public static synchronized Singleton getInstance() {
//        if (instance == null) {
//            instance = new Singleton();
//        }
//        return instance;
//    }

    //3、饿汉式 容易产生垃圾对象。
//    private static Singleton instance = new Singleton();
//    private Singleton (){}
//    public static Singleton getInstance() {
//        return instance;
//    }

    //4、双检锁/双重校验锁（DCL，即 double-checked locking）
    //安全且在多线程情况下能保持高性能。
//    private volatile static Singleton singleton;
//    private Singleton (){}
//    public static Singleton getSingleton() {
//        if (singleton == null) {
//            synchronized (Singleton.class) {
//                if (singleton == null) {
//                    singleton = new Singleton();
//                }
//            }
//        }
//        return singleton;
//    }

    //5、登记式/静态内部类
//    private static class SingletonHolder {
//        private static final Singleton INSTANCE = new Singleton();
//    }
//    private Singleton (){}
//    public static final Singleton getInstance() {
//        return SingletonHolder.INSTANCE;
//    }

    //6、枚举
    // 这种实现方式还没有被广泛采用，但这是实现单例模式的最佳方法。
    // 它更简洁，自动支持序列化机制，绝对防止多次实例化。
    public static void main(String[] args) {
        Singleton6.INSTANCE.whateverMethod();
    }
}
