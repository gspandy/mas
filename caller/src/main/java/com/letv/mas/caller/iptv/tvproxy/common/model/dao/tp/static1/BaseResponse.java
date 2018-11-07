package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

/**
 * 响应基类
 */
public abstract class BaseResponse {

    /**
     * 响应状态
     */
    private Integer resultStatus = 1;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    private String errorCode;

    private String message;

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
        this.errorCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        this.message = errMsg;
    }
}
