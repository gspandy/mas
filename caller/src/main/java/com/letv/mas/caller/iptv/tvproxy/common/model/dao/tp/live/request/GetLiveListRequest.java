package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.request;

public class GetLiveListRequest {

    private Integer channelId; // 直播频道ID 默认 797
    private Integer size; // 请求节目列表大小 默认100
    private Integer broadcastId; // 播控方ID 默认 0
    private String langCode; // 语言代码
    private String wCode;// 地区代码
    private Integer refresh; // 为1时表示强制从TP中取数据并放入缓存，默认为0时依次从内存、缓存、TP中取数据
    private String queryType;// 直播大厅数据查询类型,1--直播大厅数据列表，2--单场(或若干场)直播
    private String liveIds; // 直播ID，多个ID由逗号分隔
    // private Integer categoryId; 频道类型ID
    private String splatId;// 直播业务平台编号，1007--TV直播（包含直播大厅和直播专题），1034--直播SDK

    /**
     * 产品系列号，乐视自有设备有值
     */
    private String terminalSeries;

    public GetLiveListRequest(Integer channelId, Integer size, Integer broadcastId, String langCode, String wCode,
            Integer refresh, String queryType, String liveIds, String splatId) {
        this.broadcastId = broadcastId;
        this.size = size;
        this.refresh = refresh;
        this.queryType = queryType;
        this.channelId = channelId;
        this.langCode = langCode;
        this.wCode = wCode;
        this.liveIds = liveIds;
        this.splatId = splatId;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getLangCode() {
        return this.langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getwCode() {
        return this.wCode;
    }

    public void setwCode(String wCode) {
        this.wCode = wCode;
    }

    public Integer getRefresh() {
        return this.refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getLiveIds() {
        return this.liveIds;
    }

    public void setLiveIds(String liveIds) {
        this.liveIds = liveIds;
    }

    public String getSplatId() {
        return this.splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

}
