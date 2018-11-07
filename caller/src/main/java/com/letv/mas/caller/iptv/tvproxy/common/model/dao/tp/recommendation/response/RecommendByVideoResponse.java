package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.util.List;
import java.util.Map;

public class RecommendByVideoResponse {
    /**
     * 后台分桶测试标示
     */
    private String bucket;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 状态码
     */
    private String status;

    /**
     * 本次推荐事件id
     */
    private String reid;

    /**
     * 推荐返回结果集
     */
    private List<Rec> rec;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public List<Rec> getRec() {
        return rec;
    }

    public void setRec(List<Rec> rec) {
        this.rec = rec;
    }

    public static class Rec {
        private Long aid; // 专辑id
        private Integer caetgory; // 频道id
        private String episodes; // 总集数
        private String nowepisodes; // 更新至
        private String name; // 名称
        private Map<String, String> play_platform; // 播放平台
        private String playurl; // 播放地址
        private String poster; // 图片地址
        private String source; // 视频来源
        private String src; // 1:乐视网 2:外网
        private String subname; // 子标题
        private Long vid; // 视频id

        public Long getAid() {
            return aid;
        }

        public void setAid(Long aid) {
            this.aid = aid;
        }

        public Integer getCaetgory() {
            return caetgory;
        }

        public void setCaetgory(Integer caetgory) {
            this.caetgory = caetgory;
        }

        public String getEpisodes() {
            return episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

        public String getNowepisodes() {
            return nowepisodes;
        }

        public void setNowepisodes(String nowepisodes) {
            this.nowepisodes = nowepisodes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getPlay_platform() {
            return play_platform;
        }

        public void setPlay_platform(Map<String, String> play_platform) {
            this.play_platform = play_platform;
        }

        public String getPlayurl() {
            return playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSubname() {
            return subname;
        }

        public void setSubname(String subname) {
            this.subname = subname;
        }

        public Long getVid() {
            return vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

    }
}
