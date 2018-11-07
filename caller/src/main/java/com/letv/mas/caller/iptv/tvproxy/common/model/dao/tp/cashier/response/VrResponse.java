package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.response;

/**
 * Created by root on 5/3/17.
 */
public class VrResponse<T> {

    private String callback;

    private T data;

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
