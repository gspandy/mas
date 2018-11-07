package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class LetvUserResult {
    private String result;
    private Long ssouid;
    private String username;

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getSsouid() {
        return this.ssouid;
    }

    public void setSsouid(Long ssouid) {
        this.ssouid = ssouid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}