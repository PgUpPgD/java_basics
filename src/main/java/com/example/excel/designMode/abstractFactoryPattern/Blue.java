package com.example.excel.designMode.abstractFactoryPattern;

public class Blue implements Color {

    @Override
    public void fill() {
        System.out.println("Inside Blue::fill() method.");
    }
}
