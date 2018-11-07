package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class UserTpResponse<T> {
    protected String code;
    protected String msg;
    protected T data;

    public boolean isSucceed() {
        if (code != null && "200".equals(code)) {
            return true;
        }
        return false;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
