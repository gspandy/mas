package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 调用微信支付接口返回参数
 * @author dunhongqin
 */
public class WeixinPayCodeTpResponse {

    private Integer status; // 状态，1--成功，0--失败
    private String errormsg; // 错误信息，status为1时，errormsg为空
    private String wxurl; // 微信支付URL
    private String ordernumber; // 微信支付订单号
    private String corderid; // 乐视会员订单号

    public Boolean isSucess() {
        if (this.status == 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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

    public String getWxurl() {
        return this.wxurl;
    }

    public void setWxurl(String wxurl) {
        this.wxurl = wxurl;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getCorderid() {
        return corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

}
