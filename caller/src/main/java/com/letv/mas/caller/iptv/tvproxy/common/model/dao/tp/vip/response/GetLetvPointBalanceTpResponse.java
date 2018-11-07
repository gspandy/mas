package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 获取用户账户乐点余额请求第三方接口响应封装类
 * @author KevinYi
 */
public class GetLetvPointBalanceTpResponse {

    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String userName;

    /**
     * 当前乐点余额
     */
    private Integer lepoint;

    /**
     * 乐点总数
     */
    private Integer totalPoint;

    /**
     * 活动赠送乐点余额
     */
    private Integer activePoint;

    /**
     * 活动赠送乐点总额
     */
    private Integer activeChargePoint;

    /**
     * 充值乐点总额
     */
    private Integer chargePoint;

    /**
     * 乐点账户创建时间（boss定义了乐点账户的概念，使用乐点之前需要开通账户），格式"yyyy-MM-dd HH:mm:ss"
     */
    private String createDate;

    /**
     * 乐点账户更新时间，格式"yyyy-MM-dd HH:mm:ss"
     */
    private String updateDate;

    /**
     * 暂不使用，默认"legacy"
     */
    private String source;

    /**
     * 暂不使用
     */
    private String couponStatus;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLepoint() {
        return this.lepoint;
    }

    public void setLepoint(Integer lepoint) {
        this.lepoint = lepoint;
    }

    public Integer getTotalPoint() {
        return this.totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getActivePoint() {
        return this.activePoint;
    }

    public void setActivePoint(Integer activePoint) {
        this.activePoint = activePoint;
    }

    public Integer getActiveChargePoint() {
        return this.activeChargePoint;
    }

    public void setActiveChargePoint(Integer activeChargePoint) {
        this.activeChargePoint = activeChargePoint;
    }

    public Integer getChargePoint() {
        return this.chargePoint;
    }

    public void setChargePoint(Integer chargePoint) {
        this.chargePoint = chargePoint;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCouponStatus() {
        return this.couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

}
