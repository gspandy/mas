package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import java.util.List;

public class VoucherDto {

    private String amount4Yuan;// 代金券金额，单位元
    private String couponCode;// 代金券
    private String startTime;// 代金券开始时间，时间戳，单位毫秒
    private String endTime;// 代金券结束时间，时间戳，单位毫秒
    private String name;// 代金券名称，来源
    private List<String> productIds;
    private Integer templateId;// 模板id

    public String getAmount4Yuan() {
        return amount4Yuan;
    }

    public void setAmount4Yuan(String amount4Yuan) {
        this.amount4Yuan = amount4Yuan;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

}
