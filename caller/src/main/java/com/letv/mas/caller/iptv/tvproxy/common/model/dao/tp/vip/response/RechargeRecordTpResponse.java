package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 用户充值记录第三方接口返回值封装类
 * @author yikun
 */
public class RechargeRecordTpResponse {

    /**
     * 响应状态码，0--成功，1--失败（如空数据等）
     */
    private Integer code;

    /**
     * 错误信息，code为1时有值
     */
    private String msg;

    /**
     * 充值记录属主
     */
    private Long userId;

    /**
     * 充值记录列表
     */
    private List<ChargeRecord> chargeRecords;

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

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ChargeRecord> getChargeRecords() {
        return this.chargeRecords;
    }

    public void setChargeRecords(List<ChargeRecord> chargeRecords) {
        this.chargeRecords = chargeRecords;
    }

    public static class ChargeRecord {

        /**
         * 充值时间
         */
        private String activeDate;

        /**
         * 充值乐点数
         */
        private Integer point;

        /**
         * 充值金额，单位：元
         */
        private Integer amount;

        /**
         * 充值途径
         */
        private String chargeTypeName;

        /**
         * 入账方法
         */
        private String chargeWayName;

        /**
         * 订单号
         */
        private String chargeNumber;

        public String getActiveDate() {
            return this.activeDate;
        }

        public void setActiveDate(String activeDate) {
            this.activeDate = activeDate;
        }

        public Integer getPoint() {
            return this.point;
        }

        public void setPoint(Integer point) {
            this.point = point;
        }

        public Integer getAmount() {
            return this.amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getChargeTypeName() {
            return this.chargeTypeName;
        }

        public void setChargeTypeName(String chargeTypeName) {
            this.chargeTypeName = chargeTypeName;
        }

        public String getChargeWayName() {
            return this.chargeWayName;
        }

        public void setChargeWayName(String chargeWayName) {
            this.chargeWayName = chargeWayName;
        }

        public String getChargeNumber() {
            return this.chargeNumber;
        }

        public void setChargeNumber(String chargeNumber) {
            this.chargeNumber = chargeNumber;
        }
    }
}
