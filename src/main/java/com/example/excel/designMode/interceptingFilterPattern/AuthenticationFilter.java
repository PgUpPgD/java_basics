package com.example.excel.designMode.interceptingFilterPattern;

//步骤 2
//创建实体过滤器。
public class AuthenticationFilter implements Filter {
    public void execute(String request){
        System.out.println("Authenticating request: " + request);
    }
}
