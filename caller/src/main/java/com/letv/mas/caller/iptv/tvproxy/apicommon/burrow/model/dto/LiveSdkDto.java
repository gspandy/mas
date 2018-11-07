package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class LiveSdkDto {

    public static class TVLiveBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 5186275102589830869L;
        private String type;
        private String channelId;// 频道ID
        private String channelEname;
        private String liveId;

        public String getLiveId() {
            return liveId;
        }

        public void setLiveId(String liveId) {
            this.liveId = liveId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelEname() {
            return channelEname;
        }

        public void setChannelEname(String channelEname) {
            this.channelEname = channelEname;
        }

    }

}
