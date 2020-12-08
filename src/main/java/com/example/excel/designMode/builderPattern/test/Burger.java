package com.example.excel.designMode.builderPattern.test;

public abstract class Burger implements Item {

    //返回packing的实现
    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract float price();
}
