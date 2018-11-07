package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;

public class BootStrapResultsDto {

    private String status = TerminalCommonConstant.RESPONSE_STATUS_FAILURE;
    private String message = TerminalCommonConstant.RESPONSE_MESSAGE_FAILURE;
    private String terminalUuid = "";
    private String versionUrl = "";
    private String description = "";
    private String publishTime = "";
    private String versionName = "";

    public BootStrapResultsDto(String status, String message, String terminalUuid, String versionUrl,
            String description, String publishTime, String versionName) {
        this.status = status;
        this.message = message;
        this.terminalUuid = terminalUuid;
        this.versionUrl = versionUrl;
        this.description = description;
        this.publishTime = publishTime;
        this.versionName = versionName;
    }

    public BootStrapResultsDto() {
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

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

}
