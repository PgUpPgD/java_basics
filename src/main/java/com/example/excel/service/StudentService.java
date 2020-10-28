package com.example.excel.service;

@FunctionalInterface
public interface StudentService<T> {
    public Boolean filterStudent(T t);
}
