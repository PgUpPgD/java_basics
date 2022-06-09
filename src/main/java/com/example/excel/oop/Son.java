package com.example.excel.oop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Son extends Parent{

    @Value("${server.port}")
    private String port;

    static {
        System.out.println("Son static");
    }

    {
        System.out.println("Son 普通代码块");
    }

    //构造方法之后，init之前
    @PostConstruct
    public void save(){
        {
            System.out.println("端口port：" + this.port);
        }
        {
            System.out.println("save   ******   2");
        }
    }

    public Son(){
        System.out.println("Son 构造方法");
        //为空
        System.out.println("端口port：" + this.port);
    }

    public static void main(String[] args) {
        short i1 = 1; i1 += 1;
        //short i1 = 1; i1 = i1+1;   //报错
        Son son = new Son();
//        son.save();
    }
    //parent static
    //Son static
    //parent 普通代码块
    //parent 构造方法
    //Son 普通代码块
    //Son 构造方法

    public String a(String a){
        super.a(a);
        return "";
    }

    public String a(Integer a){
        return "";
    }

    public Integer a(char a){
        return 0;
    }
}
