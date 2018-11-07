package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveStreamConstants;

public class LiveStreamDto implements Comparable<LiveStreamDto> {
    private String streamId;// 流ID
    private String streamName;// 流名称
    private String rateType;// 码率类型，参考《码率类型词典编码》http://st.live.letv.com/live/code/00014.json
    private String streamUrl;// 对该客户端有效的直播流播放地址
    private String name; // 码流名称 例如:高清
    // private Map<String, String> multiLangName; // 流名称多语言

    private String code; // 码流代码 例如:720p
    private Integer order; // 码流清晰度排序

    private String cid; // drm cid

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    // public Map<String, String> getMultiLangName() {
    // return multiLangName;
    // }
    //
    // public void setMultiLangName(Map<String, String> multiLangName) {
    // this.multiLangName = multiLangName;
    // }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LiveStreamDto() {
        super();
    }

    public LiveStreamDto(String name, String code, Integer order) {
        super();
        this.name = name;
        this.code = code;
        this.order = order;
    }

    @Override
    public int compareTo(LiveStreamDto o) {
        // TODO Auto-generated method stub
        if (o != null && o.getCode() != null) {
            Integer otherCodeValue = LiveStreamConstants.STREAM_CODE_SORT_VSLUE.get(o.getCode());
            Integer thisCodeValue = LiveStreamConstants.STREAM_CODE_SORT_VSLUE.get(this.getCode());
            // 保证倒序排列
            if (thisCodeValue != null && otherCodeValue != null) {
                return 0 - (thisCodeValue.intValue() - otherCodeValue.intValue());
            } else if (thisCodeValue == null) {
                return 1;
            } else {
                return -1;
            }
        }
        return 1;
    }
}
