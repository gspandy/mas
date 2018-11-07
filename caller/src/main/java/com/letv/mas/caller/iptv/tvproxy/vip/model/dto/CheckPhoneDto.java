package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 手机校验DTO
 * @author yikun
 */
public class CheckPhoneDto extends BaseDto {

    /**
     * 支付通道类型，31代表联通，34代表联通，35代表电信
     */
    private Integer paymentChannel;

    /**
     * 普通VIP包月价格
     */
    private String price;

    /**
     * 高级VIP包月价格
     */
    private String gjprice;

    public Integer getPaymentChannel() {
        return this.paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGjprice() {
        return this.gjprice;
    }

    public void setGjprice(String gjprice) {
        this.gjprice = gjprice;
    }

}
