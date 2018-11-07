package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.io.Serializable;
import java.util.List;

/**
 * 数据统计组 专辑推荐
 */
public class SearchMaybeLikeTpResponse implements Serializable {

    private static final long serialVersionUID = -4820456603286979641L;
    private List<SearchMayLikeDTO> rec;
    private String bucket;
    private String area;
    private String reid;

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReid() {
        return this.reid;
    }

    public List<SearchMayLikeDTO> getRec() {
        return this.rec;
    }

    public void setRec(List<SearchMayLikeDTO> rec) {
        this.rec = rec;
    }

    public static class SearchMayLikeDTO implements Serializable {
        private static final long serialVersionUID = 2052510710639141944L;
        private Long cid;
        private Long pid;
        private String pidname;
        private String createtime;
        private Long duration;
        private Integer episodes;
        private String is_pay;
        private String is_rec;
        private Integer isalbum;
        private Integer isend;
        private Integer jump;
        private String playurl;
        private String subtitle;
        private String title;
        private Long vid;

        public Long getCid() {
            return this.cid;
        }

        public void setCid(Long cid) {
            this.cid = cid;
        }

        public Long getPid() {
            return this.pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public String getPidname() {
            return this.pidname;
        }

        public void setPidname(String pidname) {
            this.pidname = pidname;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Long getDuration() {
            return this.duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public Integer getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(Integer episodes) {
            this.episodes = episodes;
        }

        public String getIs_pay() {
            return this.is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getIs_rec() {
            return this.is_rec;
        }

        public void setIs_rec(String is_rec) {
            this.is_rec = is_rec;
        }

        public Integer getIsalbum() {
            return this.isalbum;
        }

        public void setIsalbum(Integer isalbum) {
            this.isalbum = isalbum;
        }

        public Integer getIsend() {
            return this.isend;
        }

        public void setIsend(Integer isend) {
            this.isend = isend;
        }

        public Integer getJump() {
            return this.jump;
        }

        public void setJump(Integer jump) {
            this.jump = jump;
        }

        public String getPlayurl() {
            return this.playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }

        public String getSubtitle() {
            return this.subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getVid() {
            return this.vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        @Override
        public String toString() {
            return "HotSearchRecommendDTO []";
        }
    }

}
