package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class CardSecretValidateDto extends BaseDto {

    /**
     * 状态码，0--卡有效，1--卡无效
     */
    private Integer code;

    /**
     * 乐卡类型，1--充值码，2--兑换码
     */
    private Integer cardtype;

    /**
     * 过期时间，乐卡未使用且未过期时返回
     */
    private String expireDate;

    /**
     * 乐卡面额，单位-元，充值码返回有效值，
     */
    private String money;

    /**
     * 错误信息，一般响应成功返回"success"，其他返回错误信息
     */
    private String msg;

    /**
     * 错误码
     */
    private String errorcode;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCardtype() {
        return this.cardtype;
    }

    public void setCardtype(Integer cardtype) {
        this.cardtype = cardtype;
    }

    public String getExpireDate() {
        return this.expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrorcode() {
        return this.errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

}
