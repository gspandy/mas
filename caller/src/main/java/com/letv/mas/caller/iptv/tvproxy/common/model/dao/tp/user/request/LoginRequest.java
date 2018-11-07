package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;

import java.nio.charset.Charset;
import java.util.Calendar;

public class LoginRequest {

    private String username;
    private String password;
    private Calendar loginTime;
    private String teminallUuid;
    private Integer broadcastId;
    private String tvId;
    private Long user_center_uid;
    private String tv_token;
    private String terminal_brand;
    private String terminal_series;
    private Integer isGG;
    private String mac;
    private String channel;
    private String clientIp;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Long getUser_center_uid() {
        return this.user_center_uid;
    }

    public void setUser_center_uid(Long user_center_uid) {
        this.user_center_uid = user_center_uid;
    }

    public String getTvId() {
        return this.tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getTeminallUuid() {
        return this.teminallUuid;
    }

    public void setTeminallUuid(String teminallUuid) {
        this.teminallUuid = teminallUuid;
    }

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest(String username, String password, Calendar loginTime) {
        this.username = username;
        this.password = password;
        this.loginTime = loginTime;
    }

    public LoginRequest(String username, String password, Calendar loginTime, String terminalUuid, Integer broadcastId,
            String tvId) {
        this.username = username;
        this.password = password;
        this.loginTime = loginTime;
        this.broadcastId = broadcastId;
        this.tvId = tvId;
        this.teminallUuid = terminalUuid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public Calendar getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Calendar loginTime) {
        this.loginTime = loginTime;
    }

    public String getTv_token() {
        return this.tv_token;
    }

    public void setTv_token(String tv_token) {
        this.tv_token = tv_token;
    }

    public String getMd5password() {

        String md5 = null;
        try {
            md5 = MessageDigestUtil.md5(this.password.getBytes(Charset.forName("UTF-8")));
        } catch (Exception ex) {
            throw new IllegalStateException("Exception was thrown from LoginRequest.getMd5password", ex);
        }

        return md5;
    }

    public String getTerminal_brand() {
        return this.terminal_brand;
    }

    public void setTerminal_brand(String terminal_brand) {
        this.terminal_brand = terminal_brand;
    }

    public String getTerminal_series() {
        return this.terminal_series;
    }

    public void setTerminal_series(String terminal_series) {
        this.terminal_series = terminal_series;
    }

    public Integer getIsGG() {
        return isGG;
    }

    public void setIsGG(Integer isGG) {
        this.isGG = isGG;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

}
