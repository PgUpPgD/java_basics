package com.example.excel.designMode.statePattern;

//步骤 3
//创建 Context 类。
public class Context {
    private State state;

    public Context(){
        state = null;
    }

    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return state;
    }
}
