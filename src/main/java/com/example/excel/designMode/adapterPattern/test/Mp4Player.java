package com.example.excel.designMode.adapterPattern.test;

public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void playVlc(String fileName) {

    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Mp4Player" + fileName);
    }
}
