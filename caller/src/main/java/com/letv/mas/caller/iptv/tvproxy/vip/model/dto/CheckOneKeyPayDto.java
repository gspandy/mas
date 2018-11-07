package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

public class CheckOneKeyPayDto {

    /**
     * 是否已经绑定Paypal账号，0--未绑定，1--已绑定
     */
    private int hasBindPaypal;

    /**
     * 绑定的paypal账号，hasBindPaypal为1时有值
     */
    private String paypalAccount;

    /**
     * 用户ID
     */
    private String userid;

    /**
     * 是否可以一元绑卡，是--true，否--false
     */
    private Boolean issale;

    public int getHasBindPaypal() {
        return this.hasBindPaypal;
    }

    public void setHasBindPaypal(int hasBindPaypal) {
        this.hasBindPaypal = hasBindPaypal;
    }

    public String getPaypalAccount() {
        return this.paypalAccount;
    }

    public void setPaypalAccount(String paypalAccount) {
        this.paypalAccount = paypalAccount;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Boolean getIssale() {
        return this.issale;
    }

    public void setIssale(Boolean issale) {
        this.issale = issale;
    }

}
