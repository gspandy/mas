package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

/**
 * Created by guoyunfeng on 2017/6/27.
 * BOSS代扣接口返回结果
 */
public class IPayResult {
    // 支付订单号
    private String lepay_order_no;

    // 支付流水
    private String payment_id;

    public String getLepay_order_no() {
        return lepay_order_no;
    }

    public void setLepay_order_no(String lepay_order_no) {
        this.lepay_order_no = lepay_order_no;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

}
