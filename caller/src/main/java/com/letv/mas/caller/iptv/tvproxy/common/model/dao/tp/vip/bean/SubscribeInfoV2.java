package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

/**
 * 会员产品订阅信息(V2)
 */
public class SubscribeInfoV2 {
    private Long uid; // 用户id
    private String name; // 产品名称
    private String vendorNo; // 商户编号
    private String productId; // 产品id
    private Long createTime; // 订阅的起始时间，时间戳
    private Long endTime; // 会员截止日期，时间戳
    private Boolean isExpire; // 是否过期

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorNo() {
        return vendorNo;
    }

    public void setVendorNo(String vendorNo) {
        this.vendorNo = vendorNo;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsExpire() {
        return isExpire;
    }

    public void setIsExpire(Boolean isExpire) {
        this.isExpire = isExpire;
    }
}
