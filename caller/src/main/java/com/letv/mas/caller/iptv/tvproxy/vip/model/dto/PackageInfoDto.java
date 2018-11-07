package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.TrialData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 套餐信息dto（对应boss2.0）
 * @author liudaojie
 */
@ApiModel(value = "PackageInfoDto", description = "套餐信息")
public class PackageInfoDto {

    @ApiModelProperty(value = "套餐id")
    private Integer id;// 套餐id
    @ApiModelProperty(value = "套餐名")
    private String name; // 套餐名
    @ApiModelProperty(value = "套餐描述")
    private String desc; // 套餐描述
    @ApiModelProperty(value = "套餐图片")
    private String img; // 套餐图片
    @ApiModelProperty(value = "套餐大图")
    private String bigImg; // 套餐大图
    @ApiModelProperty(value = "套餐价格")
    private Double price; // 套餐价格
    @ApiModelProperty(value = "价格单位")
    private String priceField; // 价格单位
    @ApiModelProperty(value = "时长")
    private Integer duration; // 时长
    @ApiModelProperty(value = "时长类型 1:年 2:月 5:天")
    private Integer durationType; // 时长类型 1:年 2:月 5:天

    /**
     * 获取时长类型-durationType对应的文案，如“天”、“Month(s)”等
     */
    @ApiModelProperty(value = "获取时长类型-durationType对应的文案，如“天”、“Month(s)”等")
    private String durationTypeName;
    @ApiModelProperty(value = "是否自动续费：0:否，1：是")
    private Integer autoRenew;// 是否自动续费：0:否，1：是
    @ApiModelProperty(value = "自动续费期数：0：不自动续费，大于0表示自动续费期数")
    private Integer autoRenewPeriod; // 自动续费期数：0：不自动续费，大于0表示自动续费期数
    @ApiModelProperty(value = "商品id")
    private Integer productId;// 会员id
    @ApiModelProperty(value = "试用信息")
    private TrialData trialData; // 试用信息
    @ApiModelProperty(value = "会员折扣价格")
    private List<VipDiscountPriceInfo> vipDiscountPriceInfo; // 会员折扣价格

    /**
     * 会员折扣
     */
    @ApiModel(value = "VipDiscountPriceInfo", description = "会员折扣")
    public static class VipDiscountPriceInfo {
        @ApiModelProperty(value = "前置会员id")
        private Integer vipId; // 先置会员ID
        @ApiModelProperty(value = "折扣价")
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

    public TrialData getTrialData() {
        return trialData;
    }

    public void setTrialData(TrialData trialData) {
        this.trialData = trialData;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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

    public String getDurationTypeName() {
        return durationTypeName;
    }

    public void setDurationTypeName(String durationTypeName) {
        this.durationTypeName = durationTypeName;
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

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getPriceField() {
        return priceField;
    }

    public void setPriceField(String priceField) {
        this.priceField = priceField;
    }

    public List<VipDiscountPriceInfo> getVipDiscountPriceInfo() {
        return vipDiscountPriceInfo;
    }

    public void setVipDiscountPriceInfo(List<VipDiscountPriceInfo> vipDiscountPriceInfo) {
        this.vipDiscountPriceInfo = vipDiscountPriceInfo;
    }
}
