package com.example.excel.innerClass;

//匿名内部类中不能定义静态属性、方法；　
public class ShadowTest {
    public int x = 0;
    interface FirstLevel {
        void methodInFirstLevel(int x);
    }

    FirstLevel firstLevel =  new FirstLevel() {
        @Override
        public void methodInFirstLevel(int x) {

        }
        public int x = 1;
        //public static String str = "Hello World";   // 编译报错
        //public static void aa() { }       // 编译报错
        public static final String finalStr = "Hello World";  // 正常
        public void moth(){}
    };
}
