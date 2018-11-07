package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class MmsResponse<T> {

    private String callback;
    private T data;
    private Integer result;

    private String statusCode;// 状态编码 {3001：媒资专辑屏蔽}

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
