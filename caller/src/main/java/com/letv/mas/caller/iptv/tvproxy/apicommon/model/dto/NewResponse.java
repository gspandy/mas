package com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto;

public class NewResponse<T> extends NewBaseResponse {
    private T data;// 数据主体

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
