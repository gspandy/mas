package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class PricePackageListDto extends BaseDto {

    /**
     * 套餐id
     */
    private String id;

    /**
     * 套餐id，兼容老代码
     */
    private String packageType;

    /**
     * 产品包适用的终端类型
     */
    private Integer vipEndType;

    /**
     * 产品包名称
     */
    private String packageName;

    /**
     * 原价（单位：元），最多精确到小数点后两位
     * 该字段将废弃
     */
    private String price;

    /**
     * 原价，单位：元，最多保留两位小数
     */
    private String originPrice;

    /**
     * 现价（单位：元），最多保留两位小数
     */
    private String currentPrice;

    /**
     * 乐点价格，为支付中心乐点价格策略新增字段
     */
    private String letvPointPrice;

    /**
     * 套餐时长，单位：天
     */
    private Integer day;

    /**
     * 套餐时长，单位：月，每月按31天算，兼容性设置
     */
    private Integer duration;

    /**
     * 折扣信息，精确到保留一位小数
     */
    private String discount;

    /**
     * 说明，后期去除，将废弃
     */
    private String desc;

    /**
     * 套餐描述，兼容性设置，与desc保持一致
     */
    private String vipDesc;

    /**
     * 是否为选中焦点，1--是，0--不是
     */
    private Integer focusStatus;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageType() {
        return this.packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Integer getVipEndType() {
        return this.vipEndType;
    }

    public void setVipEndType(Integer vipEndType) {
        this.vipEndType = vipEndType;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOriginPrice() {
        return this.originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getLetvPointPrice() {
        return this.letvPointPrice;
    }

    public void setLetvPointPrice(String letvPointPrice) {
        this.letvPointPrice = letvPointPrice;
    }

    public Integer getDay() {
        return this.day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDiscount() {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVipDesc() {
        return this.vipDesc;
    }

    public void setVipDesc(String vipDesc) {
        this.vipDesc = vipDesc;
    }

    public Integer getFocusStatus() {
        return this.focusStatus;
    }

    public void setFocusStatus(Integer focusStatus) {
        this.focusStatus = focusStatus;
    }

}
