package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

public class ActiveFreeVipDto extends ShowInfoDto {

    private String vipEndDesc;// 会员有效期描述
    private String vipEndDate;// 会员有效期结束日期
    private Integer delayPayBackDays;// 还款延期天数
    private String delayPayBackDate;// 延期还款期限
    private String delayPayBackDesc1;// 延期还款描述1
    private String delayPayBackDesc2;// 延期还款描述2
    private String delayPayBackNotice;// 延期还款声明
    private String payBackEndDate;// 还款截止日期
    private String startDate;// 开始日期
    private String endDate;// 结束日期
    private String price;// 价格
    private String unit;// 单位

    public String getVipEndDesc() {
        return vipEndDesc;
    }

    public void setVipEndDesc(String vipEndDesc) {
        this.vipEndDesc = vipEndDesc;
    }

    public String getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(String vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public Integer getDelayPayBackDays() {
        return delayPayBackDays;
    }

    public void setDelayPayBackDays(Integer delayPayBackDays) {
        this.delayPayBackDays = delayPayBackDays;
    }

    public String getDelayPayBackDate() {
        return delayPayBackDate;
    }

    public void setDelayPayBackDate(String delayPayBackDate) {
        this.delayPayBackDate = delayPayBackDate;
    }

    public String getDelayPayBackDesc1() {
        return delayPayBackDesc1;
    }

    public void setDelayPayBackDesc1(String delayPayBackDesc1) {
        this.delayPayBackDesc1 = delayPayBackDesc1;
    }

    public String getDelayPayBackDesc2() {
        return delayPayBackDesc2;
    }

    public void setDelayPayBackDesc2(String delayPayBackDesc2) {
        this.delayPayBackDesc2 = delayPayBackDesc2;
    }

    public String getDelayPayBackNotice() {
        return delayPayBackNotice;
    }

    public void setDelayPayBackNotice(String delayPayBackNotice) {
        this.delayPayBackNotice = delayPayBackNotice;
    }

    public String getPayBackEndDate() {
        return payBackEndDate;
    }

    public void setPayBackEndDate(String payBackEndDate) {
        this.payBackEndDate = payBackEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
