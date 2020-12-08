package com.example.excel.designMode.abstractFactoryPattern.test;

public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape);
}
