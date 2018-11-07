package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;


import org.apache.commons.lang.StringUtils;

/**
 * 一键快捷支付请求结果封装类
 * @author KevinYi
 */
public class OneKeyQucikPayTpResponse {

    private Integer status; // 状态，1--请求成功，0--失败
    private String errormsg; // 错误信息，status为1时，errormsg为空
    private String ordernumber; // paypal支付订单号
    private String corderid; // 乐视会员订单号

    public boolean isSucess() {
        // TODO
        if (this.status != null && this.status != 0 && StringUtils.isNotBlank(this.corderid)) {
            return true;
        }
        return false;
    }

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

    public String getOrdernumber() {
        return this.ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

}
