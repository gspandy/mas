package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.util.List;

/**
 * 推荐接口通用返回参数
 * 参数详情参照：http://wiki.letv.cn/pages/viewpage.action?pageId=32708712
 * @author hongqin
 */
public class RecBaseResponse {
    private String reid; // 推荐事件id;每次推荐请求，生成的一个全局唯一的ID
    private String bucket; // 分桶测试编号;
    private String area; // 页面区域
    private List<RecommendDetail> rec; // 推荐结果的列表

    public String getReid() {
        return this.reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

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

    public List<RecommendDetail> getRec() {
        return this.rec;
    }

    public void setRec(List<RecommendDetail> rec) {
        this.rec = rec;
    }

    public static class RecommendDetail {
        private Long vid; // 视频id
        private Long pid; // 专辑id
        private Integer cid; // 频道id
        private String title; // 标题
        private String subtitle; // 副标题
        private String pidname; // 专辑名称
        private String playurl; // 播放页地址
        private String director; // 导演
        private String starring; // 主演
        private String actors; // 演员
        private String singer; // 歌手
        private String picurl; // 图片地址
        private String picurl_st; // 竖图
        private String picsize;// 图片尺寸;1、表示图片是专辑图片，eg：album200*150；2、普通的单视频图片尺寸，eg：200*150，180*135等
        private Integer isend; // 是否已完结 1:完结 0:未完结
        private Integer vcount; // 如果isend为0，则vcount表示跟播到第几集
        private Integer episodes; // 总集数
        private String description; // 视频描述
        private String createtime; // 上线时间
        private Integer jump; // 是否可以外跳;取1或者0 ，1表示可以外跳，0表示否
        private String picHT; // 横图地址;用于移动端，尺寸400*300
        private String picST; // 竖图地址;用于移动端 ，尺寸150*200
        private Integer isalbum; // 视频类型;0表示单视频，1表示专辑，该字段一定要保证可以返回
        private Long duration; // 视频长度
        private String is_pay; // 是否是付费视频; "1"表示付费视频，"0"表示非付费视频
        /**
         * 该视频的悬浮标识，可用于前端醒目显示等
         * 取值"classical"代表是经典视频
         * 取值"zhuanti"表示是专题视频
         * 如果没有该字段，则表示该视频没有悬浮标识需求。
         */
        private String float_flag;

        private String video_type; // 标识对应视频或专辑的类型
        private String album_play_platform; // 专辑播放平台
        private String score;
        private String album_sub_category_code;// 二级分类id

        public String getAlbum_sub_category_code() {
            return this.album_sub_category_code;
        }

        public void setAlbum_sub_category_code(String album_sub_category_code) {
            this.album_sub_category_code = album_sub_category_code;
        }

        public String getScore() {
            return this.score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public Long getVid() {
            return this.vid;
        }

        public void setVid(Long vid) {
            this.vid = vid;
        }

        public Long getPid() {
            return this.pid;
        }

        public void setPid(Long pid) {
            this.pid = pid;
        }

        public Integer getCid() {
            return this.cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return this.subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPidname() {
            return this.pidname;
        }

        public void setPidname(String pidname) {
            this.pidname = pidname;
        }

        public String getPlayurl() {
            return this.playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }

        public String getDirector() {
            return this.director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getStarring() {
            return this.starring;
        }

        public void setStarring(String starring) {
            this.starring = starring;
        }

        public String getActors() {
            return this.actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getSinger() {
            return this.singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public String getPicurl() {
            return this.picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getPicurl_st() {
            return this.picurl_st;
        }

        public void setPicurl_st(String picurl_st) {
            this.picurl_st = picurl_st;
        }

        public String getPicsize() {
            return this.picsize;
        }

        public void setPicsize(String picsize) {
            this.picsize = picsize;
        }

        public Integer getIsend() {
            return this.isend;
        }

        public void setIsend(Integer isend) {
            this.isend = isend;
        }

        public Integer getVcount() {
            return this.vcount;
        }

        public void setVcount(Integer vcount) {
            this.vcount = vcount;
        }

        public Integer getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(Integer episodes) {
            this.episodes = episodes;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatetime() {
            return this.createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public Integer getJump() {
            return this.jump;
        }

        public void setJump(Integer jump) {
            this.jump = jump;
        }

        public String getPicHT() {
            return this.picHT;
        }

        public void setPicHT(String picHT) {
            this.picHT = picHT;
        }

        public String getPicST() {
            return this.picST;
        }

        public void setPicST(String picST) {
            this.picST = picST;
        }

        public Integer getIsalbum() {
            return this.isalbum;
        }

        public void setIsalbum(Integer isalbum) {
            this.isalbum = isalbum;
        }

        public Long getDuration() {
            return this.duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public String getIs_pay() {
            return this.is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getFloat_flag() {
            return this.float_flag;
        }

        public void setFloat_flag(String float_flag) {
            this.float_flag = float_flag;
        }

        public String getVideo_type() {
            return this.video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
        }

        public String getAlbum_play_platform() {
            return this.album_play_platform;
        }

        public void setAlbum_play_platform(String album_play_platform) {
            this.album_play_platform = album_play_platform;
        }

    }

}
