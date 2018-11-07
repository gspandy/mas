package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

/**
 * GoLive影片
 * @author Administrator
 */
public class GoLiveNavigationDto implements Serializable {

    private static final long serialVersionUID = -1207478408193235543L;
    private String name;// 导航名称
    private List<MovieDto> list;// 老版桌面在用

    public GoLiveNavigationDto() {

    }

    public GoLiveNavigationDto(String name, List<MovieDto> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieDto> getList() {
        return this.list;
    }

    public void setList(List<MovieDto> list) {
        this.list = list;
    }

    public static class MovieDto implements Serializable {
        private static final long serialVersionUID = 2166549784458135032L;
        private String action;
        private Integer type;// 跳转类型 3-详情页 7-视频播放
        private MovieType value;

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public MovieType getValue() {
            return value;
        }

        public void setValue(MovieType value) {
            this.value = value;
        }

    }

    public static class MovieType implements Serializable {
        private static final long serialVersionUID = -62610735214379455L;
        private Long id; // 商品id
        private Integer posterType;// 影片类型 1-同步院线 2-在线点播 3-活动 4-预告
        private String bigPoster;// 大海报图
        private String smallPoster;// 小海报图
        private String name;// 名称
        private String clarity;// 清晰度
        private String cp;// 数据来源
        private Integer type;// tv版跳转
        private String activityUrl;// 活动H5跳转url

        private String releaseTime;
        private String movieRelease;
        private String score;
        private String introduction;
        private Long tvStartTime;
        private Long tvEndTime;
        private Long cinemaReleaseTime;

        private String midPoster;
        private String actor;
        private String director;
        private String categoryName;

        private String interestCount;// 感兴趣人数
        private String presellMsg;// 一句话简介
        private String tagImg;// 角标

        public MovieType() {

        }

        public MovieType(Long id, Integer posterType, String bigPoster, String smallPoster, String name,
                String releaseTime, String movieRelease, String clarity, String cp, String score, Integer type,
                String activityUrl, String introduction, Long tvStartTime, Long tvEndTime, Long cinemaReleaseTime,
                String midPoster, String actor, String director, String categoryName, String presellMsg, String tagImg,
                String interestCount) {
            this.id = id;
            this.posterType = posterType;
            this.bigPoster = bigPoster;
            this.smallPoster = smallPoster;
            this.name = name;
            this.releaseTime = releaseTime;
            this.movieRelease = movieRelease;
            this.clarity = clarity;
            this.cp = cp;
            this.score = score;
            this.type = type;
            this.activityUrl = activityUrl;
            this.introduction = introduction;
            this.tvStartTime = tvStartTime;
            this.tvEndTime = tvEndTime;
            this.cinemaReleaseTime = cinemaReleaseTime;
            this.midPoster = midPoster;
            if (actor != null) {
                this.actor = actor.replaceAll("，", "/");
            } else {
                this.actor = actor;
            }
            if (director != null) {
                this.director = director.replaceAll("，", "/");
            } else {
                this.director = director;
            }
            this.categoryName = categoryName;
            this.presellMsg = presellMsg;
            this.interestCount = interestCount;
            this.tagImg = tagImg;
        }

        public String getTagImg() {
            return tagImg;
        }

        public void setTagImg(String tagImg) {
            this.tagImg = tagImg;
        }

        public String getInterestCount() {
            return interestCount;
        }

        public void setInterestCount(String interestCount) {
            this.interestCount = interestCount;
        }

        public String getPresellMsg() {
            return presellMsg;
        }

        public void setPresellMsg(String presellMsg) {
            this.presellMsg = presellMsg;
        }

        public String getCategoryName() {
            return this.categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getMidPoster() {
            return this.midPoster;
        }

        public void setMidPoster(String midPoster) {
            this.midPoster = midPoster;
        }

        public String getActor() {
            return this.actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getDirector() {
            return this.director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public Long getTvStartTime() {
            return this.tvStartTime;
        }

        public void setTvStartTime(Long tvStartTime) {
            this.tvStartTime = tvStartTime;
        }

        public Long getTvEndTime() {
            return this.tvEndTime;
        }

        public void setTvEndTime(Long tvEndTime) {
            this.tvEndTime = tvEndTime;
        }

        public Long getCinemaReleaseTime() {
            return this.cinemaReleaseTime;
        }

        public void setCinemaReleaseTime(Long cinemaReleaseTime) {
            this.cinemaReleaseTime = cinemaReleaseTime;
        }

        public String getIntroduction() {
            return this.introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getActivityUrl() {
            return this.activityUrl;
        }

        public void setActivityUrl(String activityUrl) {
            this.activityUrl = activityUrl;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getPosterType() {
            return this.posterType;
        }

        public void setPosterType(Integer posterType) {
            this.posterType = posterType;
        }

        public String getBigPoster() {
            return this.bigPoster;
        }

        public void setBigPoster(String bigPoster) {
            this.bigPoster = bigPoster;
        }

        public String getSmallPoster() {
            return this.smallPoster;
        }

        public void setSmallPoster(String smallPoster) {
            this.smallPoster = smallPoster;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClarity() {
            return this.clarity;
        }

        public void setClarity(String clarity) {
            this.clarity = clarity;
        }

        public String getCp() {
            return this.cp;
        }

        public void setCp(String cp) {
            this.cp = cp;
        }

        public String getReleaseTime() {
            return this.releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getMovieRelease() {
            return this.movieRelease;
        }

        public void setMovieRelease(String movieRelease) {
            this.movieRelease = movieRelease;
        }

        public String getScore() {
            return this.score;
        }

        public void setScore(String score) {
            this.score = score;
        }

    }

}
