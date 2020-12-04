package com.example.excel.designMode.adapterPattern;

//适配器模式
//步骤 1
//为媒体播放器和更高级的媒体播放器创建接口。
public interface MediaPlayer {
    public void play(String audioType, String fileName);
}
