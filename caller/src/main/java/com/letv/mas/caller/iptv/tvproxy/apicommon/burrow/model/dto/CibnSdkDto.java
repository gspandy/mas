package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;

public class CibnSdkDto {

    /**
     * cibn 专辑
     * @author liupeng5
     */
    public static class CibnAlbumBurrowDto extends BaseDto implements Serializable {

        private static final long serialVersionUID = -697581988040510269L;
        private String albumId;

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }
    }

    /**
     * cibn 视频
     * @author liupeng5
     */
    public static class CibnVideoBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 2310066695370193498L;
        private String videoId;

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }
    }

    /**
     * cibn 专题
     * @author liupeng5
     */
    public static class CibnSubjectBurrowDto extends BaseDto implements Serializable {

        private static final long serialVersionUID = -4679827786406219032L;
        private String specialId;

        public String getSpecialId() {
            return specialId;
        }

        public void setSpecialId(String specialId) {
            this.specialId = specialId;
        }
    }

    /**
     * cibn 频道
     * @author liupeng5
     */
    public static class CibnChannelBurrowDto extends BaseDto implements Serializable {

        private static final long serialVersionUID = -7538298708923332973L;
        private String categoryId; // 频道id

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }
}
