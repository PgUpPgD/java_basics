package com.example.excel.designMode.builderPattern;

//建造者模式  创建一个表示食物条目和食物包装的接口
public interface Item {
    public String name();
    public Packing packing();
    public float price();

}
