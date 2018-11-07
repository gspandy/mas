package com.letv.mas.caller.iptv.tvproxy.cashier;

/**
 * Created by wangli on 4/24/17.
 */
public class CashierException extends RuntimeException {

    private String code;

    public CashierException(String code) {
        this.code = code;
    }

    public CashierException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CashierException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CashierException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public CashierException(String code, String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
