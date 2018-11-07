package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.Vip;

public class GetProductTpResponse {
    private Integer code;// 0代表正常，其他值代表错误
    private String msg;
    private Vip data;

    public boolean isSucceed() {
        if (code != null && code == 0) {
            return true;
        }
        return false;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Vip getData() {
        return data;
    }

    public void setData(Vip data) {
        this.data = data;
    }
}
