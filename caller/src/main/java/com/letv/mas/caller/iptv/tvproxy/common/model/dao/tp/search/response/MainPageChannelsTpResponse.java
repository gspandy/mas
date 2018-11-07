package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;

/**
 * 乐搜新首页频道列表
 */
public class MainPageChannelsTpResponse {
    private String status; // 接口调用状态 0:成功
    private String message;
    private ChannelContent content;
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

    public ChannelContent getContent() {
        return this.content;
    }

    public void setContent(ChannelContent content) {
        this.content = content;
    }

    public boolean success() {
        return "0".equals(this.status);
    }

    public static class ChannelContent {
        private List<ChannelObject> channels;

        public List<ChannelObject> getChannels() {
            return this.channels;
        }

        public void setChannels(List<ChannelObject> channels) {
            this.channels = channels;
        }

    }

    public static class ChannelObject {
        private List<MainPageAlbum> albums; // 频道下部分专辑列表
        private List<MainPageStar> stars;
        private String chid; // 频道id
        private String cover; // 频道图片
        private String description; // 频道描述
        private String last_updated;
        private String name; // 频道名称
        private String total; // 频道内数据数量
        private String type; // 频道的英文类型标示
        private String view_total; // 观看量

        public List<MainPageAlbum> getAlbums() {
            return this.albums;
        }

        public void setAlbums(List<MainPageAlbum> albums) {
            this.albums = albums;
        }

        public List<MainPageStar> getStars() {
            return this.stars;
        }

        public void setStars(List<MainPageStar> stars) {
            this.stars = stars;
        }

        public String getChid() {
            return this.chid;
        }

        public void setChid(String chid) {
            this.chid = chid;
        }

        public String getCover() {
            return this.cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLast_updated() {
            return this.last_updated;
        }

        public void setLast_updated(String last_updated) {
            this.last_updated = last_updated;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotal() {
            return this.total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getView_total() {
            return this.view_total;
        }

        public void setView_total(String view_total) {
            this.view_total = view_total;
        }

    }
}
