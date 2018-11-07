package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

public class RecommendaionPidListResponse {
    private String status;
    private String code;
    private String msg;
    private String serverTime;

    private String[] entity;

    public String[] getEntity() {
        return entity;
    }

    public void setEntity(String[] entity) {
        this.entity = entity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public boolean isSuccess() {
        return status != null && "1".equals(status);
    }

}
