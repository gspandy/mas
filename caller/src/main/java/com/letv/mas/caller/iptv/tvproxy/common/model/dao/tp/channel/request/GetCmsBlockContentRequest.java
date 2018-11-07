package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.request;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;
import org.apache.commons.lang.StringUtils;

public class GetCmsBlockContentRequest {

    /**
     * CMS板块id
     */
    private String blockId;

    private Integer broadcastId;

    public GetCmsBlockContentRequest(String blockId) {
        this.blockId = blockId;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.blockId)) {
            return ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_BLOCKID_EMPTY;
        }
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

}
