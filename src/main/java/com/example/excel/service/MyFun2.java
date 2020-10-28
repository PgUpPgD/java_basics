package com.example.excel.service;

@FunctionalInterface
public interface MyFun2<T, R> {
    public T getValue(T t, R y);
}
