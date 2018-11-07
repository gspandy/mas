package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StreamCode {
    private String name;
    private String code;
    private String ifCanPlay = "1";
    private String ifCanDown;
    private String ifCharge = "1";
    private String ifDownloadCharge = "1";
    private String enabled = "1";
    private String kbps;
    private Long fileSize;
    private String tipText;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIfCanPlay() {
        return this.ifCanPlay;
    }

    public void setIfCanPlay(String ifCanPlay) {
        this.ifCanPlay = ifCanPlay;
    }

    public String getIfCanDown() {
        return this.ifCanDown;
    }

    public void setIfCanDown(String ifCanDown) {
        this.ifCanDown = ifCanDown;
    }

    public String getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
    }

    public String getEnabled() {
        return this.enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getKbps() {
        return this.kbps;
    }

    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    public String getIfDownloadCharge() {
        return this.ifDownloadCharge;
    }

    public void setIfDownloadCharge(String ifDownloadCharge) {
        this.ifDownloadCharge = ifDownloadCharge;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTipText() {
        return this.tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

}
