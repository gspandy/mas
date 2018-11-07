package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.desk.request;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.desk.DeskMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;

public class GetDeskRequest {

    private Integer channelId;
    private Integer model;
    private CommonParam commonParam;

    public GetDeskRequest(Integer channelId, Integer model, CommonParam commonParam) {
        super();
        this.channelId = channelId;
        this.model = model;
        this.commonParam = commonParam;
    }

    public int assertValid() {
        if (this.channelId == null) {
            return DeskMsgCodeConstant.DESK_REQUEST_CHANNELID_ERROR;
        }
        if (this.model == null) {
            return DeskMsgCodeConstant.DESK_REQUEST_MODEL_ERROR;
        }
        return DeskMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getModel() {
        return this.model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public CommonParam getCommonParam() {
        return this.commonParam;
    }

    public void setCommonParam(CommonParam commonParam) {
        this.commonParam = commonParam;
    }
    /*
     * public String getPreFixLog() {
     * return "[channelId=" + this.channelId + ", model=" + this.model +
     * ", mac=" + this.commonParam.getMac()
     * + ", broadcastId=" + this.broadcastId + "]";
     * }
     */

}
