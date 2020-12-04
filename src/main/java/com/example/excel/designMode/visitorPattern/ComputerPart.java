package com.example.excel.designMode.visitorPattern;

//访问者模式
//步骤 1
//定义一个表示元素的接口。
public interface ComputerPart {
    public void accept(ComputerPartVisitor computerPartVisitor);
}
