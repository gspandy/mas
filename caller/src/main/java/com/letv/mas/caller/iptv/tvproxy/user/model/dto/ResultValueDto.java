package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

public class ResultValueDto<T> {

    private static final long serialVersionUID = 8668889519830325316L;

    private T resultCode;

    public T getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(T resultCode) {
        this.resultCode = resultCode;
    }

}
