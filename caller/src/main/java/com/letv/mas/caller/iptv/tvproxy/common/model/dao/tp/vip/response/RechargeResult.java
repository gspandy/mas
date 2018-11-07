package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

public class RechargeResult {
    private Float money;
    private Long userid;
    private Integer code;
    private String msg;
    private Integer beforepoint;

    public Float getMoney() {
        return this.money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getBeforepoint() {
        return this.beforepoint;
    }

    public void setBeforepoint(Integer beforepoint) {
        this.beforepoint = beforepoint;
    }

}
