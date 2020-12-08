package com.example.excel.designMode.prototypePattern.test;

import java.util.Hashtable;

public class ShapeCache {

    private static Hashtable<String, Shape> shapeMap = new Hashtable<>();
    
    public static Shape getShape(String id){
        Shape cachedShape = shapeMap.get(id);
        return (Shape) cachedShape.clone();
    }

    // 对每种形状都运行数据库查询，并创建该形状
    // shapeMap.put(shapeKey, shape);
    // 例如，我们要添加三种形状
    public static void loadCache(){
        Rectangle rectangle = new Rectangle();
        rectangle.setId("3");
        shapeMap.put(rectangle.getId(),rectangle);

        Square square = new Square();
        square.setId("2");
        shapeMap.put(square.getId(),square);
    }

    //使用 ShapeCache 类来获取存储在 Hashtable 中的形状的克隆。
    public static void main(String[] args) {
        ShapeCache.loadCache();

        Shape clonedShape2 = (Shape) ShapeCache.getShape("2");
        System.out.println("Shape : " + clonedShape2.getType());

        Shape clonedShape3 = (Shape) ShapeCache.getShape("3");
        System.out.println("Shape : " + clonedShape3.getType());
    }



}
