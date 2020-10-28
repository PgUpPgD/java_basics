package com.example.excel.innerClass;

public class Hello {

    //包含两个方法的HelloWorld接口
    interface HelloWorld{
        void greet();
        void greetSomeone(String someone);
    }

    public void sayHello(){
        // 1、局部类EnglishGreeting实现了HelloWorld接口
        class EnglishGreeting implements HelloWorld{

            @Override
            public void greet() {
                greetSomeone("李白");
            }
            @Override
            public void greetSomeone(String someone) {
                System.out.println(someone);
            }
        }
        HelloWorld englishGreeting = new EnglishGreeting();

        // 2、匿名类实现HelloWorld接口
        HelloWorld frenchGreeting = new HelloWorld(){

            @Override
            public void greet() {
                greetSomeone("杜甫");
            }
            @Override
            public void greetSomeone(String someone) {
                System.out.println(someone);
            }
        };

        englishGreeting.greet();
        frenchGreeting.greet();
    }

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.sayHello();
    }



}
