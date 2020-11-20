package com.example.excel.service.impl;

import com.example.excel.entity.Student;
import com.example.excel.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceFilterAgeImpl implements StudentService<Student> {
    @Override
    public Boolean filterStudent(Student student) {
        return student.getAge() < 30;
    }
}
