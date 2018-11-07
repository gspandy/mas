package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

public class VerificationCodeTpResponse {

    /**
     * 状态码，0--失败，1--成功
     */
    private Integer status;

    /**
     * 返回的错误信息
     */
    private String errormsg;

    /**
     * 订单号
     */
    private String corderid;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

}
