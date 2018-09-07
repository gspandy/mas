package com.letv.mas.caller.iptv.tvproxy.user.response;

import java.io.Serializable;

public class ReportTpResponse implements Serializable {

    /**
     * 返回code: 1:成功，0:失败，-1:服务异常
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 时间戳(毫秒)
     */
    private Long ttl;

    /**
     * 记录键
     */
    private String key;
    /**
     * 记录值
     */
    private String value;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
