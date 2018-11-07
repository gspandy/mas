package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TerminalResults {

    private String status = TerminalCommonConstant.RESPONSE_STATUS_FAILURE;
    private String message = TerminalCommonConstant.RESPONSE_MESSAGE_FAILURE;
    private TerminalRequestData data = new TerminalRequestData();

    public TerminalResults() {

    }

    public TerminalResults(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public TerminalResults(String status, String message, String terminalUuid, String username, String identifyCode,
            String versionUrl, String versionId, String other, String description, String playFormatIsTs,
            String broadcastId, String config, String romMinimum, String CurRomMinimum, String publishTime,
            String pojieVersion, String versionName, String freeVIP, String broadcastStatus, String broadcastMessage,
            String groupId) {
        this(status, message);
        this.data = new TerminalRequestData(status, message, terminalUuid, username, identifyCode, versionUrl,
                versionId, other, description, playFormatIsTs, broadcastId, config, romMinimum, CurRomMinimum,
                publishTime, pojieVersion, versionName, freeVIP, broadcastStatus, broadcastMessage, groupId);
    }

    public TerminalRequestData getData() {
        return data;
    }

    public void setData(TerminalRequestData data) {
        this.data = data;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
