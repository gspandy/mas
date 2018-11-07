package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;

/**
 * 调用zhifu接口查询得到的订单信息封装类
 * @author KevinYi
 */
public class OrderStatusFromZhifuTpResponse {

    /**
     * 订单状态，0--查询失败或未支付（目前第三方接口暂无法区分），1--支付成功，2--通知失败，3--退款；该字段一定返回
     */
    private Integer status;

    /**
     * 消费单号
     */
    private String corderid;

    /**
     * 支付订单号
     */
    private String ordernumber;

    /**
     * 价格
     */
    private String money;

    /**
     * 支付时间
     */
    private String paytime;

    /**
     * 错误信息
     */
    private String errormsg;

    public OrderStatusFromZhifuTpResponse() {
        super();
    }

    /**
     * 判断订单是否已支付成功，status=1或2都是
     * @return
     */
    public boolean hasPayedSuccessfully() {
        return this.status != null
                && (VipTpConstant.ORDER_STATUS_API_ZHIFU_PAY_SUCCESS == this.status || VipTpConstant.ORDER_STATUS_API_ZHIFU_INFORM_FAILED == this.status);
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPaytime() {
        return this.paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getErrormsg() {
        return this.errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

}
