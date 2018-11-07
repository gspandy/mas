package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

public class OauthCodeResponse {
    private int status; // 0：失败，1：成功
    private String request;
    private OauthCode result;
    private String error_code;/* 1000,1001,1009,1005,500 */
    private String error;

    public boolean isSucceeded() {
        return status == 1 && result != null && StringUtil.isNotBlank(result.getCode());
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public OauthCode getResult() {
        return result;
    }

    public void setResult(OauthCode result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public static class OauthCode {
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }
}
