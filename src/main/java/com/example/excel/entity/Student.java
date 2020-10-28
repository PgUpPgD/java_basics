package com.example.excel.entity;

import lombok.Data;

import java.util.List;

@Data
public class Student {
    private String name;
    private int age;
    private int stature;
    private List<SpecialityEnum> specialities;

    public Student(){}
    public Student(String name, int age, int stature) {
        this.name = name;
        this.age = age;
        this.stature = stature;
    }

    public Student(String name, int age, int stature, List<SpecialityEnum> specialities) {
        this.name = name;
        this.age = age;
        this.stature = stature;
        this.specialities = specialities;
    }
}
