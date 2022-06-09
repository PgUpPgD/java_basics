package com.example.excel.controller;

import com.example.excel.entity.Scope1;
import com.example.excel.entity.Scope2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sco")
public class ScopController2 {
    private Scope1 user = new Scope1();

    @Autowired
    private Scope2 user2;

    @RequestMapping("/update")
    public Scope1 update(int num){
        user.setState(num);
        System.out.println("update");
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

}
