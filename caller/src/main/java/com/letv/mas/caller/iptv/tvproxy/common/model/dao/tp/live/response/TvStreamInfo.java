package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

public class TvStreamInfo {

    private static long serialVersionUID = -1508953286394397418L;

    private String rateType;
    private String streamUrl;
    private String streamId;
    private String streamName;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static void setSerialversionuid(long serialversionuid) {
        serialVersionUID = serialversionuid;
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

    // @Override
    // public int compareTo(Object o) {
    // TvStreamInfoDto other = (TvStreamInfoDto) o;
    // int otherCodeValue = StreamConstants.STREAM_CODE_SORT_VSLUE.get(
    // other.getCode()).intValue();
    // int thisCodeValue = StreamConstants.STREAM_CODE_SORT_VSLUE.get(
    // this.getCode()).intValue();
    // if (otherCodeValue > thisCodeValue) {
    // return 1;
    // } else if (otherCodeValue < thisCodeValue) {
    // return -1;
    // }
    // return 0;
    // }
}
