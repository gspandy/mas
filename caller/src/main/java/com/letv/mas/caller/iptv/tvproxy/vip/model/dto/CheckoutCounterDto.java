package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 收银台信息封装类，包含套餐信息（含套餐基本信息，套餐下优惠活动，套餐下支付渠道活动），收银台焦点图，收银台会员权益文案。
 * @author
 */
public class CheckoutCounterDto extends BaseDto {

    private Collection<VipPackageDto> vipPackages;

    /**
     * 优惠活动，key--活动id，value--活动信息
     */
    private Map<String, PaymentActivityDto> activities;

    /**
     * 支付渠道列表
     */
    private List<String> paymentChannelList;

    /**
     * 当前用户的易宝支付绑卡信息
     */
    private List<BandInfoDto> yeepaybandinfo;

    public CheckoutCounterDto() {
        super();
    }

    public Collection<VipPackageDto> getVipPackages() {
        return this.vipPackages;
    }

    public void setVipPackages(Collection<VipPackageDto> vipPackages) {
        this.vipPackages = vipPackages;
    }

    public Map<String, PaymentActivityDto> getActivities() {
        return this.activities;
    }

    public void setActivities(Map<String, PaymentActivityDto> activities) {
        this.activities = activities;
    }

    public List<String> getPaymentChannelList() {
        return this.paymentChannelList;
    }

    public void setPaymentChannelList(List<String> paymentChannelList) {
        this.paymentChannelList = paymentChannelList;
    }

    public List<BandInfoDto> getYeepaybandinfo() {
        return this.yeepaybandinfo;
    }

    public void setYeepaybandinfo(List<BandInfoDto> yeepaybandinfo) {
        this.yeepaybandinfo = yeepaybandinfo;
    }

}
