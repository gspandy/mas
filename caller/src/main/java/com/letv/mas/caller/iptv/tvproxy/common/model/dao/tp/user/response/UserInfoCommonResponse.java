package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class UserInfoCommonResponse {

    private Boolean result;
    private Long ssouid;
    private String username;

    public Boolean getResult() {
        return this.result;
    }

    public void setResult(Boolean result) {
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
