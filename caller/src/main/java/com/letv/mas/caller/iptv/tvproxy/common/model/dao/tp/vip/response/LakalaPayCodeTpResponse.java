package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 调用拉卡拉支付接口得到的图片形式的二维码返回参数
 * @author yikun
 * @since 2014-08-27
 */
public class LakalaPayCodeTpResponse {

    private Integer status; // 状态，1--成功，0--失败
    private String errormsg; // 错误信息，status为1时，errormsg为空
    private String url; // 拉卡拉支付URL
    private String corderid; // 乐视会员订单号
    private String ordernumber; // api.zhifu业务订单号

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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public String getOrdernumber() {
        return this.ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

}
