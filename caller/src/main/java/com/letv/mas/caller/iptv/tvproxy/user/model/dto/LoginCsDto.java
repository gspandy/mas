package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserRoleDto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class LoginCsDto {
    private String username;
    private String loginTime;
    private Long uid;
    private String token;
    private Boolean result;
    private String action;
    private String responseType;
    private Integer isGG;
    private String erosToken;
    private String erosTokenSecret;
    private Long expireTime;
    private List<LetvUserRoleDto> roleList;
    private String OpenId;

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Long getUid() {
        return this.uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginCsDto() {
    }

    public Boolean getResult() {
        return this.result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public LoginCsDto(boolean result, String action, String responseType, String username, String loginTime) {
        this.username = username;
        this.loginTime = loginTime;
    }

    public Integer getIsGG() {
        return this.isGG;
    }

    public void setIsGG(Integer isGG) {
        this.isGG = isGG;
    }

    public List<LetvUserRoleDto> getRoleList() {
        return this.roleList;
    }

    public void setRoleList(List<LetvUserRoleDto> roleList) {
        this.roleList = roleList;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getErosToken() {
        return erosToken;
    }

    public void setErosToken(String erosToken) {
        this.erosToken = erosToken;
    }

    public String getErosTokenSecret() {
        return erosTokenSecret;
    }

    public void setErosTokenSecret(String erosTokenSecret) {
        this.erosTokenSecret = erosTokenSecret;
    }

}
