package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 消费返回参数
 * @author dunhongqin
 */
public class ConsumptionTpResponse {
    private String response;// 应答编码
    private String orderid;// 订单号
    private String alipay_orderid;// 支付宝订单号
    private String letv_userid;// 乐视的账号
    private String signno;// 唯一签约号
    private String order_creat_time;// 订单创建时间
    private String order_pay_time;// 订单付款时间
    private String total_price;// 支付金额
    private String seller_logod_id;// 卖家支付宝账号
    private String uyer_logon_id;// 买家支付宝账号
    private String seller_id;// 卖家支付宝账号
    private String buyer_id;// 买家支付宝账号
    private Integer orderstatus;// 订单状态
    private String subject;// 商品名称
    private String confirm_type;// 确认付款方式

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAlipay_orderid() {
        return this.alipay_orderid;
    }

    public void setAlipay_orderid(String alipay_orderid) {
        this.alipay_orderid = alipay_orderid;
    }

    public String getLetv_userid() {
        return this.letv_userid;
    }

    public void setLetv_userid(String letv_userid) {
        this.letv_userid = letv_userid;
    }

    public String getSignno() {
        return this.signno;
    }

    public void setSignno(String signno) {
        this.signno = signno;
    }

    public String getOrder_creat_time() {
        return this.order_creat_time;
    }

    public void setOrder_creat_time(String order_creat_time) {
        this.order_creat_time = order_creat_time;
    }

    public String getOrder_pay_time() {
        return this.order_pay_time;
    }

    public void setOrder_pay_time(String order_pay_time) {
        this.order_pay_time = order_pay_time;
    }

    public String getTotal_price() {
        return this.total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getSeller_logod_id() {
        return this.seller_logod_id;
    }

    public void setSeller_logod_id(String seller_logod_id) {
        this.seller_logod_id = seller_logod_id;
    }

    public String getUyer_logon_id() {
        return this.uyer_logon_id;
    }

    public void setUyer_logon_id(String uyer_logon_id) {
        this.uyer_logon_id = uyer_logon_id;
    }

    public String getSeller_id() {
        return this.seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getBuyer_id() {
        return this.buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public Integer getOrderstatus() {
        return this.orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getConfirm_type() {
        return this.confirm_type;
    }

    public void setConfirm_type(String confirm_type) {
        this.confirm_type = confirm_type;
    }

}
