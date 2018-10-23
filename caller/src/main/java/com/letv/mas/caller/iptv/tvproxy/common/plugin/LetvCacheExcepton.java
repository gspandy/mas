package com.letv.mas.caller.iptv.tvproxy.common.plugin;

/**
 * 缓存相关异常的父类
 * @author KevinYi
 */
public class LetvCacheExcepton extends LetvCommonException {

    public LetvCacheExcepton() {
        super();
    }

    public LetvCacheExcepton(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    public LetvCacheExcepton(String errorCode, String message) {
        super(errorCode, message);
    }

    public LetvCacheExcepton(String message, Throwable cause) {
        super(message, cause);
    }

    public LetvCacheExcepton(String message) {
        super(message);
    }

    @Override
    public String getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public String toString() {
        return "LetvCacheExcepton [errorCode=" + super.getErrorCode() + "]";
    }

}
