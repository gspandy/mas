package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.bean;

/**
 * Created by guoyunfeng on 2017/6/30.
 * 会员开通信息
 */
public class PVVipInfo {

    // 订单信息
    private OrderInfo orderInfo;

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    /**
     * 订单信息
     */
    public static class OrderInfo {

        // 订单编号
        private String ordeNo;

        // 会员充值状态 0 未充值 1 已充值 -1 充值失败
        private String status;

        // 会员订单号
        private String vipOrderNo;

        // 本次开通的会员单，会员生效时间
        private String startTime;

        // 本次开通的会员单，会员失效时间
        private String endTime;

        public String getOrdeNo() {
            return ordeNo;
        }

        public void setOrdeNo(String ordeNo) {
            this.ordeNo = ordeNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVipOrderNo() {
            return vipOrderNo;
        }

        public void setVipOrderNo(String vipOrderNo) {
            this.vipOrderNo = vipOrderNo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }

}
