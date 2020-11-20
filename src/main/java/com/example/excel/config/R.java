package com.example.excel.config;

import lombok.Data;
import org.omg.CORBA.Object;

/**
 * 成功2xx
 * 此类状态码表示客户端的请求已成功接收，理解并被接受。
 * 重定向3xx
 * 这类状态码表示用户代理需要采取进一步的操作才能完成请求。
 * 客户端错误4xx
 * 4xx类的状态码适用于客户端似乎有错误的情况。
 * 服务器错误5xx
 * 以数字“5”开头的响应状态代码表示服务器知道它已经发生错误或无法执行请求的情况。
 */
@Data
public class R {
    private int code;
    private String msg;
    private Object data;

    public R(){}
    public R(Integer code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R data(Object data){
        R r = new R();
        r.setCode(200);
        r.setMsg("ok");
        r.setData(data);
        return r;
    }
    public static R data(Integer code, String msg, Object data){
        R r = new R(code, msg, data);
        return r;
    }

    public static R error(Integer code, String msg){
        R r = new R(code, msg, null);
        return r;
    }

    public static R error(){
        R r = new R();
        r.setCode(-1000);
        r.setMsg("error");
        r.setData(null);
        return r;
    }

}
