package com.letv.mas.caller.iptv.tvproxy.common.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;

public class LetvUserDto {

    @CopyFieldAnnotation(isCopy = false)
    private LetvUserInfoDto bean;
    private String action;
    private String responseType;
    private Integer status;
    private Integer errorCode;
    private String message;
    private String tv_token;

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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LetvUserInfoDto getBean() {
        return this.bean;
    }

    public void setBean(LetvUserInfoDto bean) {
        this.bean = bean;
    }

    public boolean isResult() {
        if (this.status != null && this.status == 1) {
            return true;
        }
        return false;
    }

    public String getTv_token() {
        return this.tv_token;
    }

    public void setTv_token(String tv_token) {
        this.tv_token = tv_token;
    }

}
