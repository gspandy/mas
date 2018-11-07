package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;

public class PurchaseProductResponse implements Serializable {

    private static final long serialVersionUID = -4398549184244141030L;

    private Integer status; // 状态，1--成功，0--失败，除了乐点支付
    private String response;// 乐点支付的应答编码；返回"0000"表示乐点支付成功，乐点支付
    private String errormsg; // 错误信息，status为1时，errormsg为空
    private String corderid; // 乐视会员订单号
    private String ordernumber; // api.zhifu业务订单号
    private String url; // paypal支付URL、拉卡拉支付URL、易宝支付URL
    private String bigUrl; // 大图URL，根据二维码码串，由支付宝生成的二维码图片（长：512px，宽：512px），支付宝
    private String smallUrl; // 小图URL，根据二维码码串，由支付宝生成的二维码小图片（长：256px，宽：256px）、支付宝
    private String wxurl; // 微信支付URL，微信
    private String productname; // 商品名称，paypal支付、易宝支付
    private String price; // 商品价格，paypal支付、易宝支付
    private String cardNumber;// 卡号，易宝支付
    private String mobileNo;// 手机号，易宝支付
    private String smsconfirm;// 短信确认，易宝支付
    private String cmsOrderKey;// 订单流水号(华数方)
    private String payOrderKey;// 支付订单号(华数方)
    private String qrcodeUrl;// 支付宝二维码图片地址
    private String orderId;// 订单号，不用支付渠道下单得到的订单号

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public String getOrdernumber() {
        return this.ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBigUrl() {
        return this.bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public String getSmallUrl() {
        return this.smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public String getWxurl() {
        return this.wxurl;
    }

    public void setWxurl(String wxurl) {
        this.wxurl = wxurl;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getSmsconfirm() {
        return this.smsconfirm;
    }

    public void setSmsconfirm(String smsconfirm) {
        this.smsconfirm = smsconfirm;
    }

    public String getCmsOrderKey() {
        return this.cmsOrderKey;
    }

    public void setCmsOrderKey(String cmsOrderKey) {
        this.cmsOrderKey = cmsOrderKey;
    }

    public String getPayOrderKey() {
        return this.payOrderKey;
    }

    public void setPayOrderKey(String payOrderKey) {
        this.payOrderKey = payOrderKey;
    }

    public String getQrcodeUrl() {
        return this.qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
