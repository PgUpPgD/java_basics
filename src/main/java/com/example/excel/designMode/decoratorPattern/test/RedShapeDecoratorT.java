package com.example.excel.designMode.decoratorPattern.test;

public class RedShapeDecoratorT implements ShapeDecoratorT {

    public void draw(ShapeT shapeT) {
        shapeT.draw();
        play();
    }

    public void play(){
        System.out.println("*******");
    }

    public static void main(String[] args) {
        new RedShapeDecoratorT().draw(new RrctangleT());
    }
}
