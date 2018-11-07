package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.text.SimpleDateFormat;

/**
 * 查询包月服务状态
 */
public class MonthlyPaymentStatusDto extends BaseDto {
    /**
     * 自动续费包月状态，0--未开通，1--已开通
     */
    private Integer status;
    /**
     * 自动续费包月状态到期时间(即本轮包月的到期时间)
     */
    private String expiredDate;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setexpiredDate(Long expiredTimestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(expiredTimestamp);
        this.expiredDate = dateString;
    }

    public String getExpiredDate() {
        return this.expiredDate;
    }

}
