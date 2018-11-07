package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

/**
 * Created by root on 4/28/17.
 * 新增订单返回类
 */
public class PVOrderI {

    // 订单编号
    private String order_no;
    // 订单有效期
    private int expired_in;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getExpired_in() {
        return expired_in;
    }

    public void setExpired_in(int expired_in) {
        this.expired_in = expired_in;
    }

}
