package com.example.excel.designMode.interceptingFilterPattern;

//拦截过滤器模式
//步骤 1
//创建过滤器接口 Filter。
public interface Filter {
    public void execute(String request);
}
