package com.example.excel.designMode.proxyPattern.test;

public class ImageT2 implements ImageT {
    private ImageT1 imageT1;

    @Override
    public void play() {
        if (imageT1 == null){
            imageT1 = new ImageT1("name");
        }
        imageT1.play();
    }

    public static void main(String[] args) {
        ImageT2 t2 = new ImageT2();
        t2.play();
        t2.play();
    }
}
