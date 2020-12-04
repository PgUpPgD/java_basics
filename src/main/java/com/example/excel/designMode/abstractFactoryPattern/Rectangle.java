package com.example.excel.designMode.abstractFactoryPattern;

public class Rectangle implements Shape{

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
