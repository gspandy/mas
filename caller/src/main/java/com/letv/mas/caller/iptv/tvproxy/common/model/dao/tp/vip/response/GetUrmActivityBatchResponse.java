package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;
import java.util.List;

public class GetUrmActivityBatchResponse implements Serializable {
    /**
     * request id
     */
    private String reqId;

    /**
     * urm activity data list
     */
    private List<GetUrmActivityListResponse> messages;

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public List<GetUrmActivityListResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<GetUrmActivityListResponse> messages) {
        this.messages = messages;
    }
}
