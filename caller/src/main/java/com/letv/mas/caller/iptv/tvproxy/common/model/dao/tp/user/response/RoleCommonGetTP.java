package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;

/**
 * 封装角色接口返回的数据格式
 * @author jijianlong
 */
public class RoleCommonGetTP {

    private String responseType;

    private Integer status;

    private Integer errorCode;

    private String message;

    private List<RoleBean> bean;

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RoleBean> getBean() {
        return this.bean;
    }

    public void setBean(List<RoleBean> bean) {
        this.bean = bean;
    }

    public static class RoleBean {
        private String user_role_id;

        private String uid;

        private String ip;

        private String add_time;

        private String content;

        public String getUser_role_id() {
            return this.user_role_id;
        }

        public void setUser_role_id(String user_role_id) {
            this.user_role_id = user_role_id;
        }

        public String getUid() {
            return this.uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getIp() {
            return this.ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getAdd_time() {
            return this.add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getContent() {
            return this.content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

}
