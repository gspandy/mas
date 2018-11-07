package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class UserPlayAuth {
    private Integer status;// 1:通过,0:不通过
    private String errorCode;// 错误号
    private String errorMsg;// 错误信息
    private String token;

    /**
     * 参与url拼接的userId，该参数十分重要，登录状态下必须赋值
     */
    private String tokenUserId;

    /**
     * 单点观影有效时长，单位--小时
     */
    private Integer expiredTime;
    private String price;
    private String vipPrice;
    private String uinfo;

    /**
     * 是否支持单点
     */
    private Integer isPay;

    /**
     * 单点剩余秒数
     */
    private Long leftTime;

    /**
     * 收费类型：0单点；1单点且包月；2包月
     */
    private Integer chargeType;

    /**
     * 是否被封禁，0--否，1--是
     */
    private String isForbidden;

    /**
     * 鉴权状态:通过
     */
    public static final int STATUS_PASS = 1;
    /**
     * 鉴权状态:不通过
     */
    public static final int STATUS_UNPASS = 0;

    /**
     * 用户被封禁
     */
    public static final String FORBIDDEN_TRUE = "1";

    /**
     * 用户未被封禁
     */
    public static final String FORBIDDEN_FALSE = "0";

    public UserPlayAuth() {

    }

    public UserPlayAuth(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenUserId() {
        return this.tokenUserId;
    }

    public void setTokenUserId(String tokenUserId) {
        this.tokenUserId = tokenUserId;
    }

    public Integer getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVipPrice() {
        return this.vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getUinfo() {
        return this.uinfo;
    }

    public void setUinfo(String uinfo) {
        this.uinfo = uinfo;
    }

    public Long getLeftTime() {
        return this.leftTime;
    }

    public void setLeftTime(Long leftTime) {
        this.leftTime = leftTime;
    }

    public Integer getChargeType() {
        return this.chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getIsPay() {
        return this.isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getIsForbidden() {
        return this.isForbidden;
    }

    public void setIsForbidden(String isForbidden) {
        this.isForbidden = isForbidden;
    }

}
