package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;

/**
 * User center verify
 * @author KevinYi
 */
public class VerifyAccountPasswordTpResponse implements Serializable {

    /**
     * response status,,0--fail, 1--success
     */
    private Integer status;

    /**
     * token returned to requester
     */
    private String sign;

    /**
     * 
     */
    private String errorCode;

    /**
     * 
     */
    private String message;

    public boolean isSuccessed() {
        return status != null && 1 == status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
