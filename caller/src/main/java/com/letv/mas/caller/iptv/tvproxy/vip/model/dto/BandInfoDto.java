package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class BandInfoDto extends BaseDto {
    /**
     * 银行名称
     */
    private String bankname;

    /**
     * 银行卡号
     */
    private String cardno;

    /**
     * 绑定id
     */
    private String identityid;

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getCardno() {
        return this.cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getIdentityid() {
        return this.identityid;
    }

    public void setIdentityid(String identityid) {
        this.identityid = identityid;
    }

}
