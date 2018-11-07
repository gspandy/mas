package com.letv.mas.caller.iptv.tvproxy.common.model.dto.live;

import java.io.Serializable;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;

public class TvStreamInfoDto implements Comparable<TvStreamInfoDto>, Serializable {
    private static final long serialVersionUID = 7507996246559215270L;

    private String code;
    private String liveUrl;
    private String streamId;
    private String streamName;
    private String vType;

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLiveUrl() {
        return this.liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public void setLiveUrl(String liveUrl, String splatid, String liveId) {
        this.liveUrl = this.buildLiveUrl(liveUrl, 2, splatid, liveId);
    }

    public String getStreamId() {
        return this.streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getvType() {
        return this.vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    private String buildLiveUrl(String liveUrl, Integer src, String splatid, String liveId) {
        StringBuffer url = new StringBuffer();
        if (liveUrl != null) {
            if (liveUrl.indexOf("?") != -1) {
                url = url.append(liveUrl).append("&platid=10&splatid=").append(splatid).append("&pay=0&ext=m3u8");
            } else {
                url = url.append(liveUrl).append("?platid=10&splatid=").append(splatid).append("&pay=0&ext=m3u8");
            }

            // 在播放地址添加用于CDN监测带宽使用情况的字段
            url.append("&liveid=").append(liveId).append("&p1=2&p2=");
            if (LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM.equals(splatid)
                    || LiveTpConstants.LIVE_SPLITID_PRELIVE.equals(splatid)) {
                url.append("21");
            } else if (LiveTpConstants.LIVE_SPLITID_SDK.equals(splatid)) {
                url.append("28");
            }
        }
        return url.toString();
    }

    @Override
    public int compareTo(TvStreamInfoDto tvStream) {
        Integer otherCodeValueOrder = LetvStreamCommonConstants.STREAM_CODE_SORT_VSLUE.get(tvStream.getCode());
        Integer thisCodeValueOrder = LetvStreamCommonConstants.STREAM_CODE_SORT_VSLUE.get(this.getCode());

        int otherCodeValue = otherCodeValueOrder == null ? Integer.MAX_VALUE : otherCodeValueOrder.intValue();
        int thisCodeValue = thisCodeValueOrder == null ? Integer.MAX_VALUE : thisCodeValueOrder.intValue();
        if (otherCodeValue > thisCodeValue) {
            return 1;
        } else if (otherCodeValue < thisCodeValue) {
            return -1;
        }
        return 0;
    }

}
