package com.example.excel.designMode.mementoPattern;

import lombok.Data;

//步骤 2
//创建 Originator 类。
@Data
public class Originator {
    private String state;

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento Memento){
        state = Memento.getState();
    }
}
