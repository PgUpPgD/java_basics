package com.example.excel.designMode.proxyPattern.test;

import lombok.Data;

@Data
public class ImageT1 implements ImageT {
    private String name;

    public ImageT1(String name){
        this.name = name;
        System.out.println("构造方法");
    }

    @Override
    public void play() {
        System.out.println(name);
    }
}
