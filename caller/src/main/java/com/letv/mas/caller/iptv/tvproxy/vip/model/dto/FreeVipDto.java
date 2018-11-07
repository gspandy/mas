package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 领取7天免费会员dto
 * @author dunhongqin
 */
public class FreeVipDto extends BaseDto {

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 会员到期时间，单位-毫秒，实际为Long型的毫秒级时间；兼容老接口
     */
    private String cancelTime;

    /**
     * 会员到期时间，格式为'yyyy-MM-dd'，接口升级后的新字段
     */
    private String vipEndTime;

    public FreeVipDto() {

    }

    public FreeVipDto(String oId, String cTime) {
        this.orderId = oId;
        this.cancelTime = cTime;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCancelTime() {
        return this.cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getVipEndTime() {
        return this.vipEndTime;
    }

    public void setVipEndTime(String vipEndTime) {
        this.vipEndTime = vipEndTime;
    }

}
