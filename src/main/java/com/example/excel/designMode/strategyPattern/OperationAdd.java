package com.example.excel.designMode.strategyPattern;

//步骤 2
//创建实现接口的实体类。
public class OperationAdd implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
