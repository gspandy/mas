package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

public class DeviceBind {
    private Integer vipType;// 会员类型，1--乐次元，2--全屏，3--体育
    private String vipTypeName;// 会员类型名称

    /**
     * 自带机卡绑定套餐是否已激活，0--数据不可用，1--未激活，可领取；2--已激活，不可领取
     */
    private Integer isDeviceActive;

    /**
     * 自带机卡绑定时长，单位：月，仅在isDeviceActive为1时有效
     */
    private Integer bindMonths;
    private Integer hasAuth;// 是否有操作权限 1是 0否
    private Boolean isActived;// 乐次元是否已经激活
    private Integer cardDuration;// 乐次元兑换时长，单位为天
    private String vipEndTime;// 机卡会员结束时间
    private Long activeTime; // 乐次元激活时间 有值为long 无值为0

    /**
     * 绑定时长，大陆版-乐次元绑定时长 单位为天，美国版纯表示数据，需要结合bindDurationUnit使用
     */
    private Integer bindDuration;

    /**
     * 绑定时长单位，1-年，2-月，5-天
     */
    private Integer bindDurationUnit;

    public Integer getVipType() {
        return this.vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public String getVipTypeName() {
        return this.vipTypeName;
    }

    public void setVipTypeName(String vipTypeName) {
        this.vipTypeName = vipTypeName;
    }

    public Integer getIsDeviceActive() {
        return this.isDeviceActive;
    }

    public void setIsDeviceActive(Integer isDeviceActive) {
        this.isDeviceActive = isDeviceActive;
    }

    public Integer getBindMonths() {
        return this.bindMonths;
    }

    public void setBindMonths(Integer bindMonths) {
        this.bindMonths = bindMonths;
    }

    public Integer getHasAuth() {
        return this.hasAuth;
    }

    public void setHasAuth(Integer hasAuth) {
        this.hasAuth = hasAuth;
    }

    public Boolean getIsActived() {
        return this.isActived;
    }

    public void setIsActived(Boolean isActived) {
        this.isActived = isActived;
    }

    public Integer getBindDuration() {
        return this.bindDuration;
    }

    public void setBindDuration(Integer bindDuration) {
        this.bindDuration = bindDuration;
    }

    public Integer getCardDuration() {
        return this.cardDuration;
    }

    public void setCardDuration(Integer cardDuration) {
        this.cardDuration = cardDuration;
    }

    public String getVipEndTime() {
        return this.vipEndTime;
    }

    public void setVipEndTime(String vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

    public Long getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Long activeTime) {
        this.activeTime = activeTime;
    }

    public Integer getBindDurationUnit() {
        return bindDurationUnit;
    }

    public void setBindDurationUnit(Integer bindDurationUnit) {
        this.bindDurationUnit = bindDurationUnit;
    }

}
