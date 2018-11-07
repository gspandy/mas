package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.io.Serializable;

/**
 * Author：apple on 17/4/20 20:43
 * eMail：dengliwei@le.com
 */
public class LivePackage implements Serializable {
    // 直播票套餐ID
    private Integer id;
    // 直播套餐名称
    private String name;
    // 频道id
    private String matchId;
    // 赛事id
    private String itemId;
    // 场次id
    private String extendId;
    // 类型 1：赛事 2:场次
    private Integer packageType = 1;
    // 发布状态 1：未发布 3：已发布
    private Integer status;
    // 付费类型
    // private String payType;
    /*
     * //会员价格
     * private Double vipPrice;
     * //非会员价格
     * private Double regularPrice;
     */
    // 现售价
    private Double price;
    // 直播券有效期
    private Integer validateDays;

    // 赛事版权截止时间
    // private Date endTime;
    // 直播卷张数
    private Integer counts;

    // 地域id
    private Integer country;
    // 扩展
    private String app;
    // 更新时间
    private String updateTime;
    // 会员价设置
    private VipPriceSet vipPriceSet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getExtendId() {
        return extendId;
    }

    public void setExtendId(String extendId) {
        this.extendId = extendId;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getValidateDays() {
        return validateDays;
    }

    public void setValidateDays(Integer validateDays) {
        this.validateDays = validateDays;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public VipPriceSet getVipPriceSet() {
        return vipPriceSet;
    }

    public void setVipPriceSet(VipPriceSet vipPriceSet) {
        this.vipPriceSet = vipPriceSet;
    }

    /**
     * 会员价设置
     */
    public static class VipPriceSet implements Serializable {
        private Integer vipCategory; // 适用会员价类别
        private Float vipPrice; // 会员价
        private String vipIpadId; // iPad 会员价产品ID
        private String vipAppId; // iPhone 会员价产品ID

        public Integer getVipCategory() {
            return vipCategory;
        }

        public void setVipCategory(Integer vipCategory) {
            this.vipCategory = vipCategory;
        }

        public Float getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(Float vipPrice) {
            this.vipPrice = vipPrice;
        }

        public String getVipIpadId() {
            return vipIpadId;
        }

        public void setVipIpadId(String vipIpadId) {
            this.vipIpadId = vipIpadId;
        }

        public String getVipAppId() {
            return vipAppId;
        }

        public void setVipAppId(String vipAppId) {
            this.vipAppId = vipAppId;
        }
    }
}
