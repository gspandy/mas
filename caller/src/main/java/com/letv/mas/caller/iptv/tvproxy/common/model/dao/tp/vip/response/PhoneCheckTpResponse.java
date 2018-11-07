package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 手机套餐信息检查返回参数
 * @author dunhongqin
 */
public class PhoneCheckTpResponse {

    private String code; // 状态码，0代表可用，1代表不可用
    private String provider; // 手机号码所属运营商类型，1-移动，2-联通，3-电信
    private String msg; // 描述，code为0时为空，code为1时为错误描述(这里使用UTF-8编码，URLEncoder.encode(msg,
    // "UTF-8"))
    private Integer payType; // 支付通道类型，31代表联通，34代表联通，35代表电信
    private String price; // 普通VIP包月价格
    private String gjprice; // 高级VIP包月价格

    public PhoneCheckTpResponse() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGjprice() {
        return this.gjprice;
    }

    public void setGjprice(String gjprice) {
        this.gjprice = gjprice;
    }

}
