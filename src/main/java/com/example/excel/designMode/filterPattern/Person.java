package com.example.excel.designMode.filterPattern;

import lombok.Data;

//步骤 1
//创建一个类，在该类上应用标准
@Data
public class Person {
    private String name;
    private String gender;
    private String maritalStatus;

    public Person(){}
    public Person(String name,String gender,String maritalStatus){
        this.name = name;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
    }
}
