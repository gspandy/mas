package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 充值记录DTO
 * @author dunhongqin
 */
public class RechargeRecordDto extends BaseDto {

    /**
     * 充值类型代码，这里同chargeTypeName
     */
    private String chargetype;

    /**
     * 充值类型名称
     */
    private String chargeTypeName;

    /**
     * 充值金额（单位：分）
     */
    private Integer money;

    /**
     * 充值订单号
     */
    private String orderid;

    /**
     * 充值乐点数
     */
    private Integer point;

    /**
     * 充值时间
     */
    private String chargetime;

    public String getChargetype() {
        return this.chargetype;
    }

    public void setChargetype(String chargetype) {
        this.chargetype = chargetype;
    }

    public String getChargeTypeName() {
        return this.chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public Integer getMoney() {
        return this.money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getPoint() {
        return this.point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getChargetime() {
        return this.chargetime;
    }

    public void setChargetime(String chargetime) {
        this.chargetime = chargetime;
    }

}
