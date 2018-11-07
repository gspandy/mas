package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;

/**
 * 获取用户播放记录,第三方返回参数
 * @author dunhongqin
 */
public class PlayLogTp {

    private String code;
    private PlayLogPage data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PlayLogPage getData() {
        return this.data;
    }

    public void setData(PlayLogPage data) {
        this.data = data;
    }

    public static class PlayLogPage {

        private Integer page;
        private Integer pagesize;
        private Integer total;
        private List<PlayLogInfo> items;

        public Integer getPage() {
            return this.page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getPagesize() {
            return this.pagesize;
        }

        public void setPagesize(Integer pagesize) {
            this.pagesize = pagesize;
        }

        public Integer getTotal() {
            return this.total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public List<PlayLogInfo> getItems() {
            return this.items;
        }

        public void setItems(List<PlayLogInfo> items) {
            this.items = items;
        }

    }

    public static class PlayLogInfo {
        private String cid;

        /**
         * 播放记录对应专辑id
         */
        private Long pid;
        private Long nvid;

        /**
         * 播放记录对应视频id
         */
        private Long vid;
        private Integer vtype;

        /**
         * 播放记录来源，1--网页，2--手机客户端， 3--平板，4--TV，5--桌面客户端（PC桌面程序）
         */
        private Integer from;
        private Long htime;
        private Long utime;
        private Integer vtime;
        private String title;
        private String img;
        private Integer isend;

        /**
         * 版权
         */
        private String playPlatform;

        /**
         * 视频类型
         */
        private String videoType;
        /**
         * 下一集或者下一期
         */
        private Integer nc;

        /**
         * 视频资源ID
         */
        private String lid;

        public String getLid() {
            return lid;
        }

        public void setLid(String lid) {
            this.lid = lid;
        }

        public String getCid() {
            return this.cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public Long getPid() {
            return this.pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public Long getNvid() {
            return this.nvid;
        }

        public void setNvid(Long nvid) {
            this.nvid = nvid;
        }

        public Long getVid() {
            return this.vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        public Integer getVtype() {
            return this.vtype;
        }

        public void setVtype(Integer vtype) {
            this.vtype = vtype;
        }

        public Integer getFrom() {
            return this.from;
        }

        public void setFrom(Integer from) {
            this.from = from;
        }

        public Long getHtime() {
            return this.htime;
        }

        public void setHtime(Long htime) {
            this.htime = htime;
        }

        public Long getUtime() {
            return this.utime;
        }

        public void setUtime(Long utime) {
            this.utime = utime;
        }

        public Integer getVtime() {
            return this.vtime;
        }

        public void setVtime(Integer vtime) {
            this.vtime = vtime;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return this.img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Integer getIsend() {
            return this.isend;
        }

        public void setIsend(Integer isend) {
            this.isend = isend;
        }

        public String getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(String playPlatform) {
            this.playPlatform = playPlatform;
        }

        public String getVideoType() {
            return this.videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public Integer getNc() {
            return this.nc;
        }

        public void setNc(Integer nc) {
            this.nc = nc;
        }

    }
}
