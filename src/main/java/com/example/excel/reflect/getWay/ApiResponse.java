package com.example.excel.reflect.getWay;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public abstract class ApiResponse implements Serializable {

    private String appId;
    private String sysNo;
    private String channel;
    private String transNo;
    private String transCode;

    private String msgId;
    private String errMsg;

    public <T extends ApiResponse> T initFromRequest(ApiRequest request) {
        if (request != null) {
            this.setAppId(request.getAppId());
            this.setSysNo(request.getSysNo());
            this.setChannel(request.getChannel());
            this.setTransCode(request.getTransCode());
            this.setTransNo(request.getTransNo());
        }
        return (T) this;
    }

    public static <T extends ApiResponse> T of(Class<T> clz, String msgId, String errMsg) {
        return of(clz, null, msgId, errMsg);
    }

    public static <T extends ApiResponse> T of(Class<T> clz, ApiRequest request, String msgId, String errMsg) {
        try {
            final T t = clz.newInstance(); // 默认使用无参构造函数构造
            t.initFromRequest(request);
            t.setMsgId(msgId);
            t.setErrMsg(errMsg);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends ApiResponse> T of(T t, String msgId, String errMsg) {
        return of(t, null, msgId, errMsg);
    }

    public static <T extends ApiResponse> T of(T t , ApiRequest request, String msgId, String errMsg) {
        t.initFromRequest(request);
        t.setMsgId(msgId);
        t.setErrMsg(errMsg);
        return t;
    }
}
