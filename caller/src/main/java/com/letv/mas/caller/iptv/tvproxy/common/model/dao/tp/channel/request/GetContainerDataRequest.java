package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.request;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;

public class GetContainerDataRequest {

    /**
     * 渠道ID
     */
    private Integer containerId;

    /**
     * 播控方ID
     */
    private Integer broadcastId;

    private String wcode;
    private String langCode;

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (this.containerId == null) {
            return ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_CONTAINERID_EMPTY;
        }
        return ChannelMsgCodeConstant.COMMON_REQUEST_SUCCESS;
    }

    public Integer getContainerId() {
        return this.containerId;
    }

    public void setContainerId(Integer containerId) {
        this.containerId = containerId;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public GetContainerDataRequest(Integer containerId, Integer broadcastId, String wcode, String langCode) {
        super();
        this.containerId = containerId;
        this.broadcastId = broadcastId;
        this.wcode = wcode;
        this.langCode = langCode;
    }

    public String getWcode() {
        return this.wcode;
    }

    public void setWcode(String wcode) {
        this.wcode = wcode;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

}
