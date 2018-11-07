package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.request;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request.UserBindRequest;

import java.util.HashMap;
import java.util.Map;

public class GetUserAccountRequest {
    private String username;
    private Long userid;
    private String terminal;
    private String terminalUuid;
    private Integer broadcastId;
    private String cntvMac;
    private Boolean isFromCntv;
    private Boolean isFromCibn;
    private String terminalApplication; // 终端类型

    /**
     * "机卡绑定"需求新增参数封装
     */
    private UserBindRequest userBindRequest;

    public Boolean getIsFromCibn() {
        return this.isFromCibn;
    }

    public void setIsFromCibn(Boolean isFromCibn) {
        this.isFromCibn = isFromCibn;
    }

    public GetUserAccountRequest() {
        super();
    }

    public GetUserAccountRequest(String username, String terminal, String uuId, Integer broadcastId) {
        this.username = username;
        this.terminal = terminal;
        this.terminalUuid = uuId;
        this.broadcastId = broadcastId;
    }

    public GetUserAccountRequest(String username, String terminal) {
        this.username = username;
        this.terminal = terminal;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("uname", this.username);
        parametersMap.put("end", this.terminal);
        parametersMap.put("mac", this.cntvMac);
        parametersMap.put("subend", this.isFromCntv != null && this.isFromCntv ? 7 : this.isFromCibn != null
                && this.isFromCibn ? 12 : "");
        parametersMap.put("userid", this.userid);

        return parametersMap;
    }

    public String getCntvMac() {
        return this.cntvMac;
    }

    public void setCntvMac(String cntvMac) {
        this.cntvMac = cntvMac;
    }

    public Boolean getIsFromCntv() {
        return this.isFromCntv == null ? Boolean.FALSE : this.isFromCntv;
    }

    public void setIsFromCntv(Boolean isFromCntv) {
        this.isFromCntv = isFromCntv;
    }

    public String getTerminalUuid() {
        return this.terminalUuid;
    }

    public void setTerminalUuid(String terminalUuid) {
        this.terminalUuid = terminalUuid;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getTerminal() {
        return this.terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserBindRequest getUserBindRequest() {
        return this.userBindRequest;
    }

    public void setUserBindRequest(UserBindRequest userBindRequest) {
        this.userBindRequest = userBindRequest;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getTerminalApplication() {
        return terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }
}
