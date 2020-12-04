package com.example.excel.designMode.mementoPattern;

import lombok.Data;

//备忘录模式
//步骤 1
//创建 Memento 类。
@Data
public class Memento {
    private String state;

    public Memento(String state){
        this.state = state;
    }

}
