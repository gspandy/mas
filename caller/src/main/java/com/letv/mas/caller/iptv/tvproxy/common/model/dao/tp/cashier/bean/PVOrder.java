package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

/**
 * Created by wangli on 4/24/17.
 * 付费会员订单封装类
 */
public class PVOrder {

    // 订单信息
    private OrderInfo order_info;

    public OrderInfo getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfo order_info) {
        this.order_info = order_info;
    }

    /**
     * 订单信息
     */
    public static class OrderInfo {

        // 订单编号
        private String order_no;
        // 商品ID
        private String cashier_product_id;
        // 商品名称
        private String cashier_product_name;
        // 商品价格
        private float price;
        // 交易状态(success : 支付成功; unpaid : 待支付; init : 初始化订单)
        private String trade_result;
        // 支付时间
        private long pay_time;

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getCashier_product_id() {
            return cashier_product_id;
        }

        public void setCashier_product_id(String cashier_product_id) {
            this.cashier_product_id = cashier_product_id;
        }

        public String getCashier_product_name() {
            return cashier_product_name;
        }

        public void setCashier_product_name(String cashier_product_name) {
            this.cashier_product_name = cashier_product_name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getTrade_result() {
            return trade_result;
        }

        public void setTrade_result(String trade_result) {
            this.trade_result = trade_result;
        }

        public long getPay_time() {
            return pay_time;
        }

        public void setPay_time(long pay_time) {
            this.pay_time = pay_time;
        }
    }
}
