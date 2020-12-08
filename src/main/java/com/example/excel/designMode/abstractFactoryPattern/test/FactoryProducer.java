package com.example.excel.designMode.abstractFactoryPattern.test;

public class FactoryProducer {

    public static AbstractFactory getFactory(String choice){
        if (choice.equalsIgnoreCase("SHAPE")){
            return new ShapeFactory();
        }else if (choice.equalsIgnoreCase("COLOR")){
            return new ColorFactory();
        }
        return null;
    }

    public static void main(String[] args) {
        AbstractFactory factory = FactoryProducer.getFactory("SHAPE");
        Shape shape = factory.getShape("RECTANGLE");
        shape.draw();
    }
}
