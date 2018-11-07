package com.letv.mas.caller.iptv.tvproxy.cashier.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by guoyunfeng on 2017/6/26.
 */
@ApiModel(value = "BoundPayment", description = "绑卡信息")
public class BoundPayment {
    @ApiModelProperty(value = "绑卡ID (46:支付宝签约支付; 56:微信快捷)")
    private int paymentType;
    @ApiModelProperty(value = "是否绑定")
    private boolean isBound;
    @ApiModelProperty(value = "代扣签约 id")
    private String bind_id;

    public String getBind_id() {
        return bind_id;
    }

    public void setBind_id(String bind_id) {
        this.bind_id = bind_id;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isBound() {
        return isBound;
    }

    public void setBound(boolean bound) {
        isBound = bound;
    }
}
