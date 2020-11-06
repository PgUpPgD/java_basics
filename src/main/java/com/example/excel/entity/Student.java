package com.example.excel.entity;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class Student {
    private String name;
    private int age;
    private int stature;
    private List<SpecialityEnum> specialities;

    public Student(){}
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", stature=" + stature +
                ", specialities=" + specialities +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                stature == student.stature &&
                Objects.equals(name, student.name) &&
                Objects.equals(specialities, student.specialities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, stature, specialities);
    }
}
