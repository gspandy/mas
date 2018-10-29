package com.letv.mas.caller.iptv.tvproxy.common.model.bean;

import java.io.Serializable;

/**
 * 用户状态
 */
@SuppressWarnings("serial")
public class UserStatus implements Serializable {

    /**
     * 用户ID
     */
    private long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * Token
     */
    private String userToken;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 登录时间
     */
    private long loginTime;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }
}
