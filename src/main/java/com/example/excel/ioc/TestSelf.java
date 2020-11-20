package com.example.excel.ioc;

@SelfService
public class TestSelf {

    public void play(){
        System.out.println("成功拿到该类的实例对象");
    }

    public static void main(String[] args) {
        try {
            SelfPathXmlApplicationContext context = new SelfPathXmlApplicationContext("com.example.excel.ioc");
            TestSelf bean = (TestSelf)context.getBean("testSelf");
            bean.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
