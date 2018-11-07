package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.util.List;

/**
 * 会员套餐
 */
public class VipPackage {
    private Integer id; // 套餐id
    private String name; // 套餐名
    private Integer status; // 状态 1：未发布 3：已发布
    private Integer typeGroup;// 会员类型,见VipTpConstant.Type_Group_US
    private String pic; // 图片225*150
    private String pic2; // 图片100*
    private Integer autoRenewPeriod; // 自动续费期数100
    private String pic3; // 图片800*800
    private String desc; // 描述
    private Integer weight;
    private Integer productId;// 会员id
    private String field;// ‘month’
    private Integer duration;// 时长
    private Integer durationType;// 时长类型 1:年 2:月 5:天
    private String durationName;// 时长类型：year month date
    private Double price; // 现价
    @Deprecated
    private Double originalPrice; // 原价
    private String terminal; // 终端，如"141001,141002,141004,141006,141007"，多个以','号隔开
    private Integer country; // 国家,见VipTpConstant.Country
    private Integer autoRenew;// 是否自动续费：0:否，1：是：0：不自动续费，大于0表示自动续费期数
    private List<VipDiscountPriceInfo> vipDiscountPriceInfo; // 会员折扣价格

    /**
     * 套餐已发布
     */
    public static final Integer STATUS_PUBLISHED = 3;
    /**
     * 套餐未发布
     */
    public static final Integer STATUS_NO_PUBLISH = 1;

    /**
     * 可用终端-tv
     */
    public static final String TERMINAL_TV = "141007";

    /**
     * 会员折扣
     */
    public static class VipDiscountPriceInfo {
        private Integer vipId; // 先置会员ID
        private Float price; // 折扣价

        public Integer getVipId() {
            return vipId;
        }

        public void setVipId(Integer vipId) {
            this.vipId = vipId;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(Integer typeGroup) {
        this.typeGroup = typeGroup;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDurationType() {
        return durationType;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public String getDurationName() {
        return durationName;
    }

    public void setDurationName(String durationName) {
        this.durationName = durationName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Deprecated
    public Double getOriginalPrice() {
        return originalPrice;
    }

    @Deprecated
    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getAutoRenew() {
        return autoRenew;
    }

    public void setAutoRenew(Integer autoRenew) {
        this.autoRenew = autoRenew;
    }

    public Integer getAutoRenewPeriod() {
        return autoRenewPeriod;
    }

    public void setAutoRenewPeriod(Integer autoRenewPeriod) {
        this.autoRenewPeriod = autoRenewPeriod;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public List<VipDiscountPriceInfo> getVipDiscountPriceInfo() {
        return vipDiscountPriceInfo;
    }

    public void setVipDiscountPriceInfo(List<VipDiscountPriceInfo> vipDiscountPriceInfo) {
        this.vipDiscountPriceInfo = vipDiscountPriceInfo;
    }
}
