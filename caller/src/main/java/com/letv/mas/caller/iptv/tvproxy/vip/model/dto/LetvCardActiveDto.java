package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.LetvUserDto;

import java.io.Serializable;

/**
 * 开机激活页乐卡激活结果封装类；
 * 2016-01-27，原供致新调用，接口已下线，临时恢复，至2016-02-30；
 * @author KevinYi
 */
public class LetvCardActiveDto implements Serializable {

    private LetvUserDto userInfo;
    private LetvCardActiveResultDto purchaseInfo;

    public LetvCardActiveDto() {
        super();
    }

    public LetvCardActiveDto(LetvUserDto userInfo, LetvCardActiveResultDto purchaseInfo) {
        super();
        this.userInfo = userInfo;
        this.purchaseInfo = purchaseInfo;
    }

    public LetvUserDto getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(LetvUserDto userInfo) {
        this.userInfo = userInfo;
    }

    public LetvCardActiveResultDto getPurchaseInfo() {
        return this.purchaseInfo;
    }

    public void setPurchaseInfo(LetvCardActiveResultDto purchaseInfo) {
        this.purchaseInfo = purchaseInfo;
    }

    public static class LetvCardActiveResultDto implements Serializable {
        private Integer status;// 状态 1成功 2失败
        private String confirmType;// N（无需确认） S（手机短信回复）M（PC或tv端输入验证码） 调用确认支付接口
        private String orderId;// 订单号
        private Integer amount;// 本次消费金额，单位-元
        private String purchaseName;// 购买服务名称,如包年，包月等
        private String purchaseType;// 购买方式:单片、产品包
        private String startDate;// 服务开始时间
        private String endDate;// 服务结束时间

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getConfirmType() {
            return this.confirmType;
        }

        public void setConfirmType(String confirmType) {
            this.confirmType = confirmType;
        }

        public String getOrderId() {
            return this.orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Integer getAmount() {
            return this.amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getPurchaseName() {
            return this.purchaseName;
        }

        public void setPurchaseName(String purchaseName) {
            this.purchaseName = purchaseName;
        }

        public String getPurchaseType() {
            return this.purchaseType;
        }

        public void setPurchaseType(String purchaseType) {
            this.purchaseType = purchaseType;
        }

        public String getStartDate() {
            return this.startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return this.endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

    }
}
