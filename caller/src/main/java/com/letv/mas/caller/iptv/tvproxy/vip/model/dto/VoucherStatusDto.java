package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.List;
import java.util.Map;

public class VoucherStatusDto extends BaseDto {

    /**
     * 代金券兑换码
     */
    private String couponCode;

    /**
     * 代金券面额，精确到分
     */
    private Integer amount;

    /**
     * 代金卷使用开始时间
     */
    private String startTime;

    /**
     * 代金卷使用结束时间
     */
    private String endTime;

    /**
     * 代金券适用规则；key--产品类型，如1--普通vip套餐；2--高级vip套餐；3--电影单片；4--直播，这里使用String类型；value
     * --对应产品id列表
     */
    private Map<String, List<Integer>> rules;

    /*
     * public static class VoucherStatusRuleDto {
     * private Integer type;
     * private List<Integer> ids;
     * public Integer getType() {
     * return this.type;
     * }
     * public void setType(Integer type) {
     * this.type = type;
     * }
     * public List<Integer> getIds() {
     * return this.ids;
     * }
     * public void setIds(List<Integer> ids) {
     * this.ids = ids;
     * }
     * }
     */

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Map<String, List<Integer>> getRules() {
        return this.rules;
    }

    public void setRules(Map<String, List<Integer>> rules) {
        this.rules = rules;
    }

}
