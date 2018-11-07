package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 调用手机和拉卡拉支付返回值
 * 移动话费支付支付请求返回结果：{"code":"","posturl":"","msg":"","corderid":""}
 * 电信话费支付请求返回结果：{"code":"","status":"","command":"","servicecode":"","msg":"",
 * "corderid":""}
 * 联通话费支付请求返回结果：{"code":"","msg":"","corderid":""}
 * @author dunhongqin
 */
public class PhonePayTpResponse {
    private String code; // 状态码，0代表成功，1代表失败
    private String posturl; // 移动话费支付专用返回值，跳转至移动话费支付页面地址
    private String msg; // 错误描述
    private String status; // 拉卡拉和电信话费支付专用，1表成功，0失败
    private String command; // 电信话费支付专用，表示发送指令
    private String servicecode; // 电信话费支付专用，表示服务代码
    private String url; // 拉卡拉支付URL
    private String corderid; // 本次购买套餐的订单号,支付失败该属性为空

    public PhonePayTpResponse() {

    }

    public Boolean isSucess() {
        if ("1".equals(this.status)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public String getCorderid() {
        return corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getServicecode() {
        return servicecode;
    }

    public void setServicecode(String servicecode) {
        this.servicecode = servicecode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
