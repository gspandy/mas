package com.letv.mas.caller.iptv.tvproxy.common.plugin;

public class LetvCommonException extends RuntimeException {
    private String errorCode = "";

    public LetvCommonException() {
        super();
    }

    public LetvCommonException(String message) {
        super(message);
    }

    public LetvCommonException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public LetvCommonException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public LetvCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String toString() {
        return "LetvCommonException [errorCode=" + this.errorCode + "]";
    }

}
