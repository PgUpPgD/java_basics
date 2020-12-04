package com.example.excel.designMode.interceptingFilterPattern;

//步骤 6
//创建客户端 Client。
public class Client {
    FilterManager filterManager;

    public void setFilterManager(FilterManager filterManager){
        this.filterManager = filterManager;
    }

    public void sendRequest(String request){
        filterManager.filterRequest(request);
    }
}
