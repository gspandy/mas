package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 易宝支付下单返回数据封装类
 * @author liyunlong
 */
public class YeePayOrderTpResponse {

    /**
     * 状态
     */
    private Integer status;

    /**
     * 错误信息
     */
    private String errormsg;

    /**
     * url地址
     */
    private String url;

    /**
     * 支付平台流水
     */
    private String ordernumber;

    /**
     * 消费单号
     */
    private String corderid;

    /**
     * 产品名称
     */
    private String productname;

    /**
     * 价格
     */
    private String price;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 短信确认
     */
    private String smsconfirm;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrdernumber() {
        return this.ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
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

}
