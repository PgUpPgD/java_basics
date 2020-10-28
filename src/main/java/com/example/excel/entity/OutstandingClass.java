package com.example.excel.entity;

import lombok.Data;

import java.util.List;

@Data
public class OutstandingClass {

    private String name;
    private List<Student> students;

    public OutstandingClass() {}
    public OutstandingClass(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

}
