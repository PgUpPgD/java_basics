package com.example.excel.entity;

import lombok.Data;

@Data
public class User {

    private String userId;
    private String name;
    private String userInfo;

    public User() {}
    public User(String userId, String name, String userInfo) {
        this.userId = userId;
        this.name = name;
        this.userInfo = userInfo;
    }
}
