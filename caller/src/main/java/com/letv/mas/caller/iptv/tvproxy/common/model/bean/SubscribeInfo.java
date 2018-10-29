package com.letv.mas.caller.iptv.tvproxy.common.model.bean;

/**
 * 订阅信息
 */
public class SubscribeInfo {
    private Long endTime; // 会员截止日期，时间戳
    private String name; // 会员名称
    private Integer productId;// 会员id
    private Long uid; // 用户id
    private Integer typeGroup; // 101:basic包 102：add_on包 103：站外会员包(hulu等)
    private Integer isSubscribe; // 是否订阅 1：未订阅 2：已订阅
    private Long tryEndTime; // 试用结束时间
    private Long createTime;// 订阅的起始时间

    /**
     * 已订阅
     */
    public static final Integer IS_SUBSCRIBED = 2;
    /**
     * 未订阅
     */
    public static final Integer UN_SUBSCRIBED = 1;

    /**
     * 是否在试用中
     */
    public boolean isTryUsing() {
        if (tryEndTime != null && tryEndTime > System.currentTimeMillis()) { // tryEndTime大于当前时间,则可以试用
            return true;
        }
        return false;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(Integer typeGroup) {
        this.typeGroup = typeGroup;
    }

    public Integer getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(Integer isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public Long getTryEndTime() {
        return tryEndTime;
    }

    public void setTryEndTime(Long tryEndTime) {
        this.tryEndTime = tryEndTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

}
