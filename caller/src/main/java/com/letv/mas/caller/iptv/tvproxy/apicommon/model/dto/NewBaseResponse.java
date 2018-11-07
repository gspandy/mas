package com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto;

public abstract class NewBaseResponse {
    public static final Integer STATUS_OK = 0; // 响应状态:成功
    public static final Integer STATUS_ERROR = 1; // 响应状态:失败
    /**
     * 接口状态码
     */
    private Integer status = STATUS_OK;
    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
