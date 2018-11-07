package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class WorksResponse<T> {

    private T data;
    private String code;
    private String msg;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
