package com.letv.mas.caller.iptv.tvproxy.common.model.dto.response;

import java.util.List;

public class LetvUserResponse {

    private List<LetvUserInfoResponse> bean;
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

    // public LetvUserInfoResponse getBean() {
    // return this.bean;
    // }
    //
    // public void setBean(LetvUserInfoResponse bean) {
    // this.bean = bean;
    // }

    public boolean isResult() {
        if (this.status != null && this.status == 1) {
            return true;
        }
        return false;
    }

    public List<LetvUserInfoResponse> getBean() {
        return bean;
    }

    public void setBean(List<LetvUserInfoResponse> bean) {
        this.bean = bean;
    }

    public String getTv_token() {
        return this.tv_token;
    }

    public void setTv_token(String tv_token) {
        this.tv_token = tv_token;
    }

}
