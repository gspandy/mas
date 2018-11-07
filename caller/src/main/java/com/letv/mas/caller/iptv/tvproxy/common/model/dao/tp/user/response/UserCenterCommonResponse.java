package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class UserCenterCommonResponse<T> {

    private T bean;
    private String action;
    private String responseType;
    private Integer status;
    private Integer errorCode;
    private String message;

    public T getBean() {
        return this.bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
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

}
