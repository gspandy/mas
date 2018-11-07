package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response;

/**
 * Created by guoyunfeng on 2017/6/27.
 */
public class IPayResponse<T> {

    private int code;

    private String msg;

    private int errorcode;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
