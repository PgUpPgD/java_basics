package com.example.excel.designMode.adapterPattern.test;

public class VlcPlayer implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {
        System.out.println("VlcPlayer" + fileName);
    }

    @Override
    public void playMp4(String fileName) {

    }
}
