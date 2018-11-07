package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 频道-CMS频道关系表
 */
public class ChannelCmsChannelMysqlTable {

    /**
     * 频道ID
     */
    private Integer channel_id;

    /**
     * CMS频道ID
     */
    private Integer cms_channelid;

    public Integer getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(Integer channel_id) {
        this.channel_id = channel_id;
    }

    public Integer getCms_channelid() {
        return cms_channelid;
    }

    public void setCms_channelid(Integer cms_channelid) {
        this.cms_channelid = cms_channelid;
    }
}
