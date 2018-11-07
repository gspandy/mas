package com.letv.mas.caller.iptv.tvproxy.common.plugin;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CommonError")
public class CommonError {

    protected String resultStatus;
    protected String message;
    private String errorCode;

    public String getResultStatus() {
        return this.resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "CommonError [resultStatus=" + this.resultStatus + ", message=" + this.message + ", errorCode="
                + this.errorCode + "]";
    }

}
