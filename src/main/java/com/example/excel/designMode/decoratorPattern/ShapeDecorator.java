package com.example.excel.designMode.decoratorPattern;

//步骤 3
//创建实现了 Shape 接口的抽象装饰类。
public abstract class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape){
        this.decoratedShape = decoratedShape;
    }

    public void draw(){
        decoratedShape.draw();
    }
}
