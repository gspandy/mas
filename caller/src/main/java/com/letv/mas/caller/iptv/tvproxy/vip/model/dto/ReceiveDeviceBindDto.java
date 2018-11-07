package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class ReceiveDeviceBindDto extends BaseDto {

    /**
     * 领取的赠送机卡时长对应会员有效期的到期时间
     */
    private String endTime;

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
