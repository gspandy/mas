package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 调用阿里支付接口返回参数
 * @author dunhongqin
 */
public class AliPayCodeTpResponse {
    private static final long serialVersionUID = -5178843326190223492L;

    private Integer status;
    private String errormsg;
    private String iframeurl;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean isSucess() {
        if (this.status == 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getIframeurl() {
        return this.iframeurl;
    }

    public void setIframeurl(String iframeurl) {
        this.iframeurl = iframeurl;
    }
}
