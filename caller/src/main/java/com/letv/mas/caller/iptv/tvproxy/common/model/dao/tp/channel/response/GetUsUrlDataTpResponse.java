package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response;

/**
 * 获取北美收银台地址接口
 */
public class GetUsUrlDataTpResponse {

    /**
     * 接口返回的状态码
     */
    private Integer code;

    private Boolean success;

    private String message;

    private Long lastUpdated;

    private UsUrlData data;

    public class UsUrlData {
        private String renewUrl;

        public String getRenewUrl() {
            return renewUrl;
        }

        public void setRenewUrl(String renewUrl) {
            this.renewUrl = renewUrl;
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public UsUrlData getData() {
        return data;
    }

    public void setData(UsUrlData data) {
        this.data = data;
    }
}
