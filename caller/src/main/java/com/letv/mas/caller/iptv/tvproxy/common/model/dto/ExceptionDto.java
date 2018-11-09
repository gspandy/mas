package com.letv.mas.caller.iptv.tvproxy.common.model.dto;

/**
 * Created by leeco on 18/11/6.
 */
public class ExceptionDto extends RuntimeException {
    private String errorCode = "";

    public ExceptionDto() {
        super();
    }

    public ExceptionDto(String message) {
        super(message);
    }

    public ExceptionDto(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExceptionDto(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ExceptionDto(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String toString() {
        return "ExceptionDto [errorCode=" + this.errorCode + "]";
    }
}
