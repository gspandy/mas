package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;

/**
 * 封装角色接口返回的数据格式
 * @author jijianlong
 */
public class RoleCommonPostTP {
    private String action;

    private String responseType;

    /**
     * 响应状态，0--失败，1--成功
     */
    private String status;

    private String errorCode;

    private String message;

    private List<RoleBean> bean;

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RoleBean> getBean() {
        return bean;
    }

    public void setBean(List<RoleBean> bean) {
        this.bean = bean;
    }

    public static class RoleBean {

        /**
         * 操作结果状态， 0--失败，1--成功
         */
        private String result;

        public String getResult() {
            return this.result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
