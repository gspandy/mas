package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import org.apache.commons.lang.StringUtils;

/**
 * 生成paypal支付二维码接口调用响应封装类；
 * 目前该二维码用于引导用户到paypal移动APP继续操作，先完成绑定操作，然后进行支付
 * @author KevinYi
 */
public class PaypalPaymentWithBindTpResponse {

    private Integer status; // 状态，1--成功，0--失败
    private String errormsg; // 错误信息，status为1时，errormsg为空
    private String url; // paypal支付URL
    private String ordernumber; // paypal支付订单号
    private String corderid; // 乐视会员订单号
    private String productname; // 商品名称
    private String price; // 商品价格

    public Boolean isSucess() {
        if (this.status != null && this.status == 1 && StringUtils.isNotBlank(this.url)
                && StringUtils.isNotBlank(this.corderid)) {
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

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
