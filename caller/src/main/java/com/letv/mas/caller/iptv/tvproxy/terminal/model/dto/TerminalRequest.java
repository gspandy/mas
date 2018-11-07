package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

public class TerminalRequest {
    private String terminalUuid;
    private String identifyCode;
    private String clientUuid;
    private String terminalBrand;
    private String terminalSeries;
    private String terminalApplication;
    private String terminalUnique;
    private String client;
    private String mac;
    private String installVersion;
    private String ip;
    private String mcode;
    private String broadcastId;
    private String langcode;
    private String terminalUiVersion;
    private String terminalUiCode;
    private String channelCode;
    private Integer auditType;

    public TerminalRequest() {
        this.terminalUuid = "";
        this.identifyCode = "";
        this.clientUuid = "";
        this.terminalBrand = "";
        this.terminalSeries = "";
        this.terminalApplication = "";
        this.terminalUnique = "";
        this.client = "";
        this.mac = "";
        this.installVersion = "";
        this.ip = "";
        this.broadcastId = "";
        this.langcode = "";
        this.terminalUiVersion = "";
        this.terminalUiCode = "";
        this.channelCode = "";
    }

    public TerminalRequest(String terminalUuid, String identifyCode, String clientUuid, String terminalBrand,
            String terminalSeries, String terminalApplication, String terminalUnique, String client, String mac,
            String installVersion, String ip, String broadcastId, String langcode, String terminalUiVersion,
            String terminalUiCode, String channelCode, Integer auditType) {
        this.terminalUuid = terminalUuid;
        this.identifyCode = identifyCode;
        this.clientUuid = clientUuid;
        this.terminalBrand = terminalBrand;
        this.terminalSeries = terminalSeries;
        this.terminalApplication = terminalApplication;
        this.terminalUnique = terminalUnique;
        this.client = client;
        this.mac = mac;
        this.installVersion = installVersion;
        this.ip = ip;
        this.broadcastId = broadcastId;
        this.langcode = langcode;
        this.terminalUiVersion = terminalUiVersion;
        this.terminalUiCode = terminalUiCode;
        this.channelCode = channelCode;
        this.auditType = auditType;
    }

    String getTerminalUiVersion() {
        return terminalUiVersion;
    }

    public void setTerminalUiVersion(String terminalUiVersion) {
        this.terminalUiVersion = terminalUiVersion;
    }

    public String getTerminalUiCode() {
        return terminalUiCode;
    }

    public void setTerminalUiCode(String terminalUiCode) {
        this.terminalUiCode = terminalUiCode;
    }

    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public String getTerminalUuid() {
        return this.terminalUuid;
    }

    public void setTerminalUuid(String terminalUuid) {
        this.terminalUuid = terminalUuid;
    }

    public String getIdentifyCode() {
        return this.identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getClientUuid() {
        return this.clientUuid;
    }

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getTerminalApplication() {
        return this.terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getTerminalUnique() {
        return this.terminalUnique;
    }

    public void setTerminalUnique(String terminalUnique) {
        this.terminalUnique = terminalUnique;
    }

    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getInstallVersion() {
        return this.installVersion;
    }

    public void setInstallVersion(String installVersion) {
        this.installVersion = installVersion;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMcode() {
        return this.mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(String broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getAuditType() {
        return auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public String toString() {
        return "TerminalAuthRequest [terminalUuid=" + this.terminalUuid + ", identifyCode=" + this.identifyCode
                + ", clientUuid=" + this.clientUuid + ", terminalBrand=" + this.terminalBrand + ", terminalSeries="
                + this.terminalSeries + ", terminalApplication=" + this.terminalApplication + ", terminalUnique="
                + this.terminalUnique + ", client=" + this.client + ", mac=" + this.mac + ", installVersion="
                + this.installVersion + ", ip=" + this.ip + ", mcode=" + this.mcode + ", terminalUiVersion="
                + this.terminalUiVersion + ", terminalUiCode=" + this.terminalUiCode + ", auditType=" + this.auditType
                + "]";
    }

}
