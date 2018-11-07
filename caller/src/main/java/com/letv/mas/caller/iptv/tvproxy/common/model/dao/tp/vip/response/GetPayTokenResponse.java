package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

public class GetPayTokenResponse {

    private Integer code;// 0--success,1--failure
    private String token;// pay token
    private String lepayOrderNo;// order number
    private Integer merchantBusinessId;// pay business id

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLepayOrderNo() {
        return this.lepayOrderNo;
    }

    public void setLepayOrderNo(String lepayOrderNo) {
        this.lepayOrderNo = lepayOrderNo;
    }

    public Integer getMerchantBusinessId() {
        return this.merchantBusinessId;
    }

    public void setMerchantBusinessId(Integer merchantBusinessId) {
        this.merchantBusinessId = merchantBusinessId;
    }

}
