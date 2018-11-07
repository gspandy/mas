package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.threescreen.request;

public class PushServerRequest {

    private String appId;

    private String servId;

    /**
     * 接收方 根据异地登陆的策略 一个VIP账号只能在一台电视登陆 to 值定死1:点对点
     */
    private int type;

    private String from;

    private String to;

    private String msg;

    private String sign;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getServId() {
        return servId;
    }

    public void setServId(String servId) {
        this.servId = servId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
