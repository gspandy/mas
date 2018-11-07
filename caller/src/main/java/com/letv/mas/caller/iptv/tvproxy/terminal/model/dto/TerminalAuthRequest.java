package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;

public class TerminalAuthRequest {

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
    private Integer auditType;
    private String wcode;
    private String appCode;
    /**
     * 渠道号
     */
    private String bsChannel;
    private String devId;// 移动设备唯一标识，目前值为设备imei

    public Integer getAuditType() {
        return this.auditType;
    }

    public void setAuditType(Integer auditType) {
        this.auditType = auditType;
    }

    public String getTerminalUiVersion() {
        return this.terminalUiVersion;
    }

    public void setTerminalUiVersion(String terminalUiVersion) {
        this.terminalUiVersion = terminalUiVersion;
    }

    public String getTerminalUiCode() {
        return this.terminalUiCode;
    }

    public void setTerminalUiCode(String terminalUiCode) {
        this.terminalUiCode = terminalUiCode;
    }

    public TerminalAuthRequest() {
    }

    public TerminalAuthRequest(String terminalUuid, String identifyCode, String clientUuid, String terminalBrand,
            String terminalSeries, String terminalApplication, String terminalUnique, String client, String mac,
            String installVersion, String ip, String broadcastId, String langcode, String terminalUiVersion,
            String terminalUiCode, String devId) {
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

    }

    public TerminalAuthRequest(String terminalUuid, String identifyCode, String clientUuid, String terminalBrand,
            String terminalSeries, String terminalApplication, String terminalUnique, String client, String mac,
            String installVersion, String ip, String broadcastId, String langcode) {
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

    @Override
    public String toString() {
        return "TerminalAuthRequest [terminalUuid=" + this.terminalUuid + ", identifyCode=" + this.identifyCode
                + ", clientUuid=" + this.clientUuid + ", terminalBrand=" + this.terminalBrand + ", terminalSeries="
                + this.terminalSeries + ", terminalApplication=" + this.terminalApplication + ", terminalUnique="
                + this.terminalUnique + ", client=" + this.client + ", mac=" + this.mac + ", installVersion="
                + this.installVersion + ", ip=" + this.ip + ", mcode=" + this.mcode + ", terminalUiVersion="
                + this.terminalUiVersion + ", terminalUiCode=" + this.terminalUiCode + "]";
    }

    public String getUrl() {
        return TerminalCommonConstant.getTerminalEnterURL(null) + "?terminalUuid=" + this.terminalUuid
                + "&identifyCode=" + this.identifyCode + "&clientUuid=" + this.clientUuid + "&terminalBrand="
                + this.terminalBrand + "&terminalSeries=" + this.terminalSeries + "&terminalApplication="
                + this.terminalApplication + "&terminalUnique=" + this.terminalUnique + "&client=" + this.client
                + "&mac=" + this.mac + "&installVersion=" + this.installVersion + "&appCode=" + this.appCode + "&ip="
                + this.ip + "&mcode=" + this.mcode + "&terminalUiVersion=" + this.terminalUiVersion
                + "&terminalUiCode=" + this.terminalUiCode + "&devId=" + this.devId;
    }

    public static void main(String[] args) {
        System.out.println(new TerminalAuthRequest().getUrl());
    }

    public String getWcode() {
        return this.wcode;
    }

    public void setWcode(String wcode) {
        this.wcode = wcode;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getBsChannel() {
        return this.bsChannel;
    }

    public void setBsChannel(String bsChannel) {
        this.bsChannel = bsChannel;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

}
