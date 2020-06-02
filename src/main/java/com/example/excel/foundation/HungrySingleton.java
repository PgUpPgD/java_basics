package com.example.excel.foundation;

/**
 * 优点;简单方便
 * 缺点;不管程序中是否使用到了单例对象，都会生成单例对象
 * 并且静态对象是在类加载就需要生成，会降低应用的启动速度
 * 静态代码在调用之前创建实例，所以线程安全
 */
public class HungrySingleton {
    private static HungrySingleton hungrySingleton = null;

    private HungrySingleton(){}
    public static HungrySingleton getInstance(){
        hungrySingleton = new HungrySingleton();
        return hungrySingleton;
    }


}
