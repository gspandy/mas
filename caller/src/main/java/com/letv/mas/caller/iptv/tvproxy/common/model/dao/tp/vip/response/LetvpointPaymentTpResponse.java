package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

public class LetvpointPaymentTpResponse {

    /**
     * 应答编码；返回"0000"表示乐点支付成功
     */
    private String response;

    /**
     * 订单号
     */
    private String corderid;

    private String ordernumber; // api.zhifu业务订单号

    public LetvpointPaymentTpResponse() {
        super();
    }

    public String getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response;
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
