package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;

/**
 * 乐搜新首页topics下数据
 */
public class MainPageTopicDataTpResponse {
    private String status; // 接口调用状态 0:成功
    private String message;
    private TopicData content;
    private String eid;
    private String experiment_bucket_str;
    private String trigger_str;

    public String getEid() {
        return this.eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getExperiment_bucket_str() {
        return this.experiment_bucket_str;
    }

    public void setExperiment_bucket_str(String experiment_bucket_str) {
        this.experiment_bucket_str = experiment_bucket_str;
    }

    public String getTrigger_str() {
        return this.trigger_str;
    }

    public void setTrigger_str(String trigger_str) {
        this.trigger_str = trigger_str;
    }

    public boolean success() {
        return "0".equals(this.status);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TopicData getContent() {
        return this.content;
    }

    public void setContent(TopicData content) {
        this.content = content;
    }

    public static class TopicData {
        private Integer album_count; // 专辑数量
        private Integer record_count; // 视频数量
        private Integer star_count; // 明星数量
        private List<MainPageAlbum> albums; // 专辑数据
        private List<MainPageRecord> records; // 视频数据
        private List<MainPageStar> stars; // 明星数据

        public Integer getAlbum_count() {
            return this.album_count;
        }

        public void setAlbum_count(Integer album_count) {
            this.album_count = album_count;
        }

        public Integer getRecord_count() {
            return this.record_count;
        }

        public void setRecord_count(Integer record_count) {
            this.record_count = record_count;
        }

        public Integer getStar_count() {
            return this.star_count;
        }

        public void setStar_count(Integer star_count) {
            this.star_count = star_count;
        }

        public List<MainPageAlbum> getAlbums() {
            return this.albums;
        }

        public void setAlbums(List<MainPageAlbum> albums) {
            this.albums = albums;
        }

        public List<MainPageRecord> getRecords() {
            return this.records;
        }

        public void setRecords(List<MainPageRecord> records) {
            this.records = records;
        }

        public List<MainPageStar> getStars() {
            return this.stars;
        }

        public void setStars(List<MainPageStar> stars) {
            this.stars = stars;
        }

    }
}
