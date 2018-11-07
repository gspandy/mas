package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

public class AlbumSerialsVideoInfo<T> {

    private T data;

    public static class SerialsVideoInfo {
        private Integer totalNum;
        private List<VideoInfo> videoInfo;

        public Integer getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(Integer totalNum) {
            this.totalNum = totalNum;
        }

        public List<VideoInfo> getVideoInfo() {
            return videoInfo;
        }

        public void setVideoInfo(List<VideoInfo> videoInfo) {
            this.videoInfo = videoInfo;
        }

    }

    public static class VideoInfo {
        private String id;
        private String episode;
        private String nowEpisodes; // 媒资当前剧集对应字段
        private String nowEpisode; // api接口对应当前剧集字段

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getNowEpisodes() {
            return nowEpisodes;
        }

        public void setNowEpisodes(String nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public String getNowEpisode() {
            return nowEpisode;
        }

        public void setNowEpisode(String nowEpisode) {
            this.nowEpisode = nowEpisode;
        }
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
