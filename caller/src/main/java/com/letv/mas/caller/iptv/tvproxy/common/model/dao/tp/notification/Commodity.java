package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.notification;

import java.io.Serializable;

/**
 * 边看边买
 */
public class Commodity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5423332326730229096L;

    private String title; // 商品标题

    private String triggerTime; // 触发时间

    private String durationTime; // 持续时间

    private String commodityId;// 商品id

    private String originalPrice;// 原价

    private String currentPrice;// 现价

    private String detailImage1;// 商品详情图
    private String detailImage2;// 商品详情图
    private String detailImage3;// 商品详情图
    private String detailImage4;// 商品详情图
    private String listImage;// 商品列表图

    private String source;// 商品url

    private String praise;// 好评度

    private String desc; // 简介
    private String buttonText;// 按钮文案
    private String qrCodeText;// 二维码文案
    private String cardText1;// 弹出卡片文案1
    private String cardText2;// 弹出卡片文案2

    public String getDetailImage1() {
        return this.detailImage1;
    }

    public void setDetailImage1(String detailImage1) {
        this.detailImage1 = detailImage1;
    }

    public String getDetailImage2() {
        return this.detailImage2;
    }

    public void setDetailImage2(String detailImage2) {
        this.detailImage2 = detailImage2;
    }

    public String getDetailImage3() {
        return this.detailImage3;
    }

    public void setDetailImage3(String detailImage3) {
        this.detailImage3 = detailImage3;
    }

    public String getDetailImage4() {
        return this.detailImage4;
    }

    public void setDetailImage4(String detailImage4) {
        this.detailImage4 = detailImage4;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTriggerTime() {
        return this.triggerTime;
    }

    public void setTriggerTime(String triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getDurationTime() {
        return this.durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getCommodityId() {
        return this.commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getOriginalPrice() {
        return this.originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCurrentPrice() {
        return this.currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getListImage() {
        return this.listImage;
    }

    public void setListImage(String listImage) {
        this.listImage = listImage;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPraise() {
        return this.praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getQrCodeText() {
        return qrCodeText;
    }

    public void setQrCodeText(String qrCodeText) {
        this.qrCodeText = qrCodeText;
    }

    public String getCardText1() {
        return cardText1;
    }

    public void setCardText1(String cardText1) {
        this.cardText1 = cardText1;
    }

    public String getCardText2() {
        return cardText2;
    }

    public void setCardText2(String cardText2) {
        this.cardText2 = cardText2;
    }

}
