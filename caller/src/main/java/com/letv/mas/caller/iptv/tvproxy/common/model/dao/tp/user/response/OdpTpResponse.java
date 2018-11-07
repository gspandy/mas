package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

/**
 * Author：apple on 17/9/15 14:35
 * eMail：dengliwei@le.com
 */
public class OdpTpResponse<T> {
    protected String code;
    protected String message;
    protected T data;

    public boolean isSucceed() {
        if (code != null && "200".equals(code)) {
            return true;
        }
        return false;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
