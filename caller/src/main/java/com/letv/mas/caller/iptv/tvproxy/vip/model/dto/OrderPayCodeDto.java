package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

/**
 * 单点支付返回数据封装基类，封装了会员订单号、支付二维码类型、订单价格和订单产品名称四个必须回传客户端的数据。
 * 需求中涉及支付宝支付、微信支付和拉卡拉支付，这三种支付方式均采用二维码扫描的形式。这里进行抽象，提取必须回传的公共
 * 参数，而各支付方式的特有返回数据，需要在本类的子类用进行定义。
 * @author yikun
 * @since 2014-08-26
 */
public class OrderPayCodeDto {

    // private String status; // 响应状态，1--表成功，0--失败
    // private String errormsg; // 错误信息，如果status为1时，errormsg为空

    /**
     * 会员订单号
     */
    private String corderid;

    /**
     * 支付二维码类型，1--支付宝网页，2--支付宝图片，3--微信，4--拉卡拉
     */
    private Integer payCodeType;

    /**
     * 订单价格（单位：元）
     */
    private String price;

    /**
     * 订单产品名称
     */
    private String productName;

    /**
     * 支付二维码图片URL（支付宝、微信、拉卡拉均使用此字段作为返回值标识）
     */
    private String url;

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public Integer getPayCodeType() {
        return this.payCodeType;
    }

    public void setPayCodeType(Integer payCodeType) {
        this.payCodeType = payCodeType;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
