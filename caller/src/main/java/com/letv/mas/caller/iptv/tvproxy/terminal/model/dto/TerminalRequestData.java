package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TerminalRequestData {

    private String status = TerminalCommonConstant.RESPONSE_STATUS_FAILURE;
    private String message = TerminalCommonConstant.RESPONSE_MESSAGE_FAILURE;
    private String terminalUuid = "";
    private String username = "";
    private String identifyCode = "aaaa";
    private String versionUrl = "";
    private String versionId = "";
    private String other = "";
    private String description = "";
    private String playFormatIsTs = "";
    private String broadcastId = "";
    private String config = "";
    private String romMinimum = "";
    private String CurRomMinimum = "";
    private String publishTime = "";
    private String pojieVersion = "";
    private String versionName = "";
    private String freeVIP = "";
    private String broadcastStatus = "";
    private String broadcastMessage = "";
    private String groupId = "";
    private Prompt prompt;

    public TerminalRequestData(String status, String message, String terminalUuid, String username,
            String identifyCode, String versionUrl, String versionId, String other, String description,
            String playFormatIsTs, String broadcastId, String config, String romMinimum, String CurRomMinimum,
            String publishTime, String pojieVersion, String versionName, String freeVIP, String broadcastStatus,
            String broadcastMessage, String groupId) {
        this.status = status;
        this.message = message;
        this.terminalUuid = terminalUuid;
        this.username = username;
        this.identifyCode = identifyCode;
        this.versionUrl = versionUrl;
        this.versionId = versionId;
        this.other = other;
        this.description = description;
        this.playFormatIsTs = playFormatIsTs;
        this.broadcastId = broadcastId;
        this.config = config;
        this.romMinimum = romMinimum;
        this.CurRomMinimum = CurRomMinimum;
        this.publishTime = publishTime;
        this.pojieVersion = pojieVersion;
        this.versionName = versionName;
        this.freeVIP = freeVIP;
        this.broadcastStatus = broadcastStatus;
        this.broadcastMessage = broadcastMessage;
        this.groupId = groupId;
    }

    public TerminalRequestData() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTerminalUuid() {
        return terminalUuid;
    }

    public void setTerminalUuid(String terminalUuid) {
        this.terminalUuid = terminalUuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlayFormatIsTs() {
        return playFormatIsTs;
    }

    public void setPlayFormatIsTs(String playFormatIsTs) {
        this.playFormatIsTs = playFormatIsTs;
    }

    public String getBroadcastId() {
        return broadcastId;
    }

    public void setBroadcastId(String broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getRomMinimum() {
        return romMinimum;
    }

    public void setRomMinimum(String romMinimum) {
        this.romMinimum = romMinimum;
    }

    public String getCurRomMinimum() {
        return CurRomMinimum;
    }

    public void setCurRomMinimum(String curRomMinimum) {
        CurRomMinimum = curRomMinimum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getPojieVersion() {
        return pojieVersion;
    }

    public void setPojieVersion(String pojieVersion) {
        this.pojieVersion = pojieVersion;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getFreeVIP() {
        return freeVIP;
    }

    public void setFreeVIP(String freeVIP) {
        this.freeVIP = freeVIP;
    }

    public String getBroadcastStatus() {
        return broadcastStatus;
    }

    public void setBroadcastStatus(String broadcastStatus) {
        this.broadcastStatus = broadcastStatus;
    }

    public String getBroadcastMessage() {
        return broadcastMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }
}
