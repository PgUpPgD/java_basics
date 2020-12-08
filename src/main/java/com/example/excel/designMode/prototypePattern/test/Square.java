package com.example.excel.designMode.prototypePattern.test;

public class Square extends Shape {

    public Square(){
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Square");
    }
}
