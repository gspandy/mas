package com.letv.mas.caller.iptv.tvproxy.common.dto.response;

public class BalanceQueryResponse {

    private LetvPayResponseStatus status;
    private String description;
    private String username;
    private Integer letvPoint;
    private String msg = "";

    public BalanceQueryResponse() {

    }

    public BalanceQueryResponse(LetvPayResponseStatus status, String description, String username, int letvPoint) {
        this.status = status;
        this.description = description;
        this.username = username;
        this.letvPoint = letvPoint;
    }

    public LetvPayResponseStatus getStatus() {
        return this.status;
    }

    public void setStatus(LetvPayResponseStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLetvPoint() {
        return this.letvPoint;
    }

    public void setLetvPoint(Integer letvPoint) {
        this.letvPoint = letvPoint;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
