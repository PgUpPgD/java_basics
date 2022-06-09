package com.example.excel.controller;

import com.example.excel.entity.Scope1;
import com.example.excel.entity.Scope2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

//@Controller
@RestController
//当有请求的时候 都创建一个新对象
//@Scope(scopeName = "prototype")
public class ScopController {
    private AtomicInteger atomicInteger = new AtomicInteger(1);

    @Autowired
    private Scope2 user2;

    //RestController注解，会让spring扫描并实例化这个类，此时user只有一份，做为ScopController的属性
    private Scope1 user = new Scope1();

    @RequestMapping("/update")
    public String update(int num){
        int a= atomicInteger.getAndIncrement();
        System.out.println(a);
        return "11111";
    }
    @RequestMapping("/update3")
    public Scope1 update3(int num){
        user.setState(num);
        System.out.println("update3");
        return user;
    }
    @RequestMapping("/update2")
    public Scope2 update2(int num){
        user2.setState(num);
        System.out.println("update2");
        return user2;
    }

    @RequestMapping("/getUser")
    public Scope1 getUser(){
        return user;
    }
    @RequestMapping("/getUser2")
    public Scope2 getUser2(){
        return user2;
    }

    @RequestMapping("/view")
    public String update3(){
        return "view";
    }
}
