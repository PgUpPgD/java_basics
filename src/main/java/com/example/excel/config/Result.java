package com.example.excel.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private Integer code;
    private String msg;
    private Object date;

    public Result(){}
    public Result(RestCode restCode, Object date) {
        this.code = restCode.getCode();
        this.msg = restCode.getMsg();
        this.date = date;
    }

    public static Result setCodeMsg(RestCode restCode){
        Result result = new Result();
        result.setCode(restCode.getCode());
        result.setMsg(restCode.getMsg());
        return result;
    }

    public static Result success(Object date){
        Result result = setCodeMsg(RestCode.SUCCESS);
        result.setDate(date);
        return result;
    }


}
