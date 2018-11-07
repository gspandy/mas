package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.ButtonDto;

/**
 * 购买按钮信息封装类
 * @author liyunlong
 */
public class BuyButtonDto extends ButtonDto {

    /**
     * 收银台类型，1--单点收银台，2--套餐收银台，3--直播收银台
     */
    private Integer purchaseType;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 价格
     */
    private String price;

    /**
     * 会员价格
     */
    private String vipPrice;

    /**
     * 普通价格
     */
    private String commonPrice;

    /**
     * 直播开始时间
     */
    private String liveStartTime;

    /**
     * 直播结束时间
     */
    private String liveEndTime;

    /**
     * 海报图
     */
    private String pic;

    /**
     * 正式直播开始后，客户端需要弹出一个免责声明对话框
     */
    private String tips;

    /**
     * 当此按钮变成跳H5时，需要提供URL
     */
    private String jumpUrl;

    public BuyButtonDto(String title, Integer type, Integer browserType, Integer openType) {
        super(title, type, browserType, openType);
    }

    public Integer getPurchaseType() {
        return this.purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVipPrice() {
        return this.vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getCommonPrice() {
        return this.commonPrice;
    }

    public void setCommonPrice(String commonPrice) {
        this.commonPrice = commonPrice;
    }

    public String getLiveStartTime() {
        return this.liveStartTime;
    }

    public void setLiveStartTime(String liveStartTime) {
        this.liveStartTime = liveStartTime;
    }

    public String getLiveEndTime() {
        return this.liveEndTime;
    }

    public void setLiveEndTime(String liveEndTime) {
        this.liveEndTime = liveEndTime;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getJumpUrl() {
        return this.jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

}
