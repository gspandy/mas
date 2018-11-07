package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

/**
 * 浏览器
 */
public class Retrieve extends BaseData {

    private String searchCondition;

    private String channelId;

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

}
