package com.letv.mas.caller.iptv.tvproxy.cashier.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by wangli on 4/24/17.
 * 订单信息
 */
@ApiModel(value = "Order", description = "订单信息")
public class Order {
    // 商品ID
    @ApiModelProperty(value = "商品ID")
    private String productId;
    // 订单编号
    @ApiModelProperty(value = "订单编号")
    private String orderId;
    // 订单是否成功(success : 支付成功; unpaid : 待支付; init : 初始化订单)
    @ApiModelProperty(value = "订单是否成功(success : 支付成功; unpaid : 待支付; init : 初始化订单; failed : 支付失败)")
    private String tradeResult;
    // 订单支付二维码
    @ApiModelProperty(value = "订单支付二维码")
    private String qrcodeUrl;
    // 订单支付二维码时长(有效时间)
    @ApiModelProperty(value = "订单支付二维码时长(有效时间)")
    private long qrcodeDuration;
    // 到期时间
    @ApiModelProperty(value = "礼品地址")
    private String presentUrl;
    // 礼品地址
    @ApiModelProperty(value = "乐视超级影视会员到期时间")
    private String proExpiresAt;
    // 成功开通
    @ApiModelProperty(value = "是否成功开通")
    private boolean valid;
    // 是否是连续包月
    @ApiModelProperty(value = "是否是连续包月")
    private boolean isRenewal;
    // 绑卡信息
    @ApiModelProperty(value = "绑卡信息")
    private List<BoundPayment> boundPayments;

    public String getPresentUrl() {
        return presentUrl;
    }

    public void setPresentUrl(String presentUrl) {
        this.presentUrl = presentUrl;
    }

    public boolean isRenewal() {
        return isRenewal;
    }

    public void setRenewal(boolean renewal) {
        isRenewal = renewal;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public List<BoundPayment> getBoundPayments() {
        return boundPayments;
    }

    public String getProExpiresAt() {
        return proExpiresAt;
    }

    public void setProExpiresAt(String proExpiresAt) {
        this.proExpiresAt = proExpiresAt;
    }

    public void setBoundPayments(List<BoundPayment> boundPayments) {
        this.boundPayments = boundPayments;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeResult() {
        return tradeResult;
    }

    public void setTradeResult(String tradeResult) {
        this.tradeResult = tradeResult;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public long getQrcodeDuration() {
        return qrcodeDuration;
    }

    public void setQrcodeDuration(long qrcodeDuration) {
        this.qrcodeDuration = qrcodeDuration;
    }
}
