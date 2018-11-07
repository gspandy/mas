package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 查询订单状态DTO
 * @author dunhongqin
 */
public class OrderStatusDto extends BaseDto {

    /**
     * 订单状态，0--未支付，1--支付成功，2--支付失败，3--订单失效或异常（如已退款等，不表示订单不存在）
     */
    private Integer status;

    /**
     * N（无需确认） S（手机短信回复）M（PC或tv端输入验证码） 调用确认支付接口
     */
    private String confirmType;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 本次消费金额
     */
    private String amount;

    /**
     * 购买服务名称,如包年，包月等
     */
    private String purchaseName;

    /**
     * 购买方式:单片、产品包
     */
    private String purchaseType;

    /**
     * 服务开始时间
     */
    private String startDate;

    /**
     * 服务结束时间
     */
    private String endDate;

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getConfirmType() {
        return this.confirmType;
    }

    public void setConfirmType(String confirmType) {
        this.confirmType = confirmType;
    }

}
