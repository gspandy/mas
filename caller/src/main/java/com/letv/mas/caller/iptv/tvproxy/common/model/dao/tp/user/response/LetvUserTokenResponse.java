package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class LetvUserTokenResponse {

    private LetvUserResult bean;
    private String action;
    private String responseType;
    private Integer status;
    private String errorCode;
    private String message;

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

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LetvUserResult getBean() {
        return this.bean;
    }

    public void setBean(LetvUserResult bean) {
        this.bean = bean;
    }

    public boolean isResult() {
        if (this.status != null && this.status == 1) {
            return true;
        }
        return false;
    }

}
