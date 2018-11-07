package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowPackageName;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class SportsDto {

    public static class SportsVideoBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 4089729583474507997L;
        private String from;// @BurrowPackageName
        private long vid;
        private String videoName;

        public String getFrom() {
            return from;
        }

        public void setFrom(BurrowPackageName burrowPackageName) {
            this.from = burrowPackageName.getFrom();
        }

        public long getVid() {
            return vid;
        }

        public void setVid(long vid) {
            this.vid = vid;
        }

        public String getVideoName() {
            return videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

    }

    public static class SportsSubjectBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -1221719765406958265L;
        private String from;// @BurrowPackageName
        private String topicId;

        public String getFrom() {
            return from;
        }

        public void setFrom(BurrowPackageName burrowPackageName) {
            this.from = burrowPackageName.getFrom();
        }

        public String getTopicId() {
            return topicId;
        }

        public void setTopicId(String topicId) {
            this.topicId = topicId;
        }

    }

}
