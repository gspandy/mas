package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 检查订单支付状态返回参数
 * @author dunhongqin
 */
public class OrderStatusTpResponse {
    private Integer total;
    private List<UserPackageInfo> data;
    private ConsumptionCenterError error;

    public OrderStatusTpResponse() {
    }

    public OrderStatusTpResponse(Integer total, List<UserPackageInfo> data) {
        this.total = total;
        this.data = data;
    }

    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<UserPackageInfo> getData() {
        return this.data;
    }

    public void setData(List<UserPackageInfo> data) {
        this.data = data;
    }

    public ConsumptionCenterError getError() {
        return this.error;
    }

    public void setError(ConsumptionCenterError error) {
        this.error = error;
    }

    public static class ConsumptionCenterError {
        private Integer code;
        private String msg;
        private String end;
        private String pId;

        public ConsumptionCenterError() {
        }

        public ConsumptionCenterError(Integer code, String msg, String end, String pId) {
            this.code = code;
            this.msg = msg;
            this.end = end;
            this.pId = pId;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getEnd() {
            return this.end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getpId() {
            return this.pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }
    }

    public static class UserPackageInfo {
        private String status;
        private String userName;
        private String orderId;
        private String orderFrom;
        private String orderType;
        private String createTime;
        private String aid2;
        private String aid;
        private String money;
        private String aidName;
        private String payType;
        private String statusName;
        private String moneyName;
        private String cancelTime;
        private String orderName;
        private String payTime;

        public UserPackageInfo() {
        }

        public UserPackageInfo(String status, String userName, String orderId, String orderFrom, String orderType,
                String createTime, String aid2, String aid, String money, String aidName, String payType,
                String statusName, String moneyName, String cancelTime, String orderName, String payTime) {
            this.status = status;
            this.userName = userName;
            this.orderId = orderId;
            this.orderFrom = orderFrom;
            this.orderType = orderType;
            this.createTime = createTime;
            this.aid2 = aid2;
            this.aid = aid;
            this.money = money;
            this.aidName = aidName;
            this.payType = payType;
            this.statusName = statusName;
            this.moneyName = moneyName;
            this.cancelTime = cancelTime;
            this.orderName = orderName;
            this.payTime = payTime;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserName() {
            return this.userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderFrom() {
            return this.orderFrom;
        }

        public void setOrderFrom(String orderFrom) {
            this.orderFrom = orderFrom;
        }

        public String getOrderType() {
            return this.orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAid2() {
            return this.aid2;
        }

        public void setAid2(String aid2) {
            this.aid2 = aid2;
        }

        public String getAid() {
            return this.aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getMoney() {
            return this.money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAidName() {
            return this.aidName;
        }

        public void setAidName(String aidName) {
            this.aidName = aidName;
        }

        public String getPayType() {
            return this.payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getStatusName() {
            return this.statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public String getMoneyName() {
            return this.moneyName;
        }

        public void setMoneyName(String moneyName) {
            this.moneyName = moneyName;
        }

        public String getCancelTime() {
            return this.cancelTime;
        }

        public void setCancelTime(String cancelTime) {
            this.cancelTime = cancelTime;
        }

        public String getOrderName() {
            return this.orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getPayTime() {
            return this.payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }
    }

}
