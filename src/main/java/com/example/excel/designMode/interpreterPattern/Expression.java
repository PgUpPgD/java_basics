package com.example.excel.designMode.interpreterPattern;

//解释器模式
//步骤 1
//创建一个表达式接口。
public interface Expression {
    public boolean interpret(String context);
}
