package com.example.excel.reflect.getWay;

import lombok.Data;

import java.io.Serializable;


@Data
public class ApiRequest implements Serializable {

    private String appId;
    private String sysNo;
    private String channel;
    private String transNo;
    private String transCode;
    private String timestamp;

}
