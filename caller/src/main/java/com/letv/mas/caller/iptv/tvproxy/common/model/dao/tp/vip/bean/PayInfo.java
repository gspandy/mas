package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

/**
 * Author：apple on 17/6/15 16:28
 * eMail：dengliwei@le.com
 */
public class PayInfo {
    /**
     * 专辑id
     */
    private String pid;
    /**
     * 专辑id
     */
    private String vid;
    /**
     * 付费类型 0：点播 1：点播和包月 2：包月，3：免费但TV包月收费
     */
    private String chargeType;
    /**
     * 收费：1，免费：0
     */
    private String isCharge;
    /**
     * 付费平台
     */
    private String chargePlatform;
    /**
     * 价格（元）
     */
    private String chargePrice;
    /**
     * 会员优惠
     */
    private String memberDiscounts;
    /**
     * 状态 0:未发布 1： 已发布，2：定时发布
     */
    private String status;
    /**
     * 是否支持观影券。1：支持，2：不支持
     */
    private String supportTicket;
    /**
     * 试看时长（秒）
     */
    private String tryLookTime;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public String getChargePlatform() {
        return chargePlatform;
    }

    public void setChargePlatform(String chargePlatform) {
        this.chargePlatform = chargePlatform;
    }

    public String getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(String chargePrice) {
        this.chargePrice = chargePrice;
    }

    public String getMemberDiscounts() {
        return memberDiscounts;
    }

    public void setMemberDiscounts(String memberDiscounts) {
        this.memberDiscounts = memberDiscounts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupportTicket() {
        return supportTicket;
    }

    public void setSupportTicket(String supportTicket) {
        this.supportTicket = supportTicket;
    }

    public String getTryLookTime() {
        return tryLookTime;
    }

    public void setTryLookTime(String tryLookTime) {
        this.tryLookTime = tryLookTime;
    }
}
