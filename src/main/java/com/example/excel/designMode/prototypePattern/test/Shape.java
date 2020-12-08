package com.example.excel.designMode.prototypePattern.test;

import lombok.Data;

@Data
public abstract class Shape implements Cloneable{

    private String id;
    protected String type;

    abstract void draw();

    public Object clone(){
        Object clone = null;
        try{
            clone = super.clone();
        }catch (Exception e){

        }
        return clone;
    }


}














