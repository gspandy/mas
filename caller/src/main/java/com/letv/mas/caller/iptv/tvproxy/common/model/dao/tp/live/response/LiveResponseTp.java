package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import org.apache.commons.lang.StringUtils;

import java.util.List;

public class LiveResponseTp {
    private String id;
    private String title;// 标题
    private String description;// 描述
    private String viewPic;// 预览图片
    private String beginTime;// 播放时间
    private String endTime;// 结束时间
    private Long duration;// 比赛时长
    private Long vid; // 视频iD
    private Long pid;// 专辑id（体育的专题id用这个字段）
    private String aid;// 专题id（音乐和娱乐的专题id用这个字段）
    private String preVideoId;// 推荐视频id（音乐和娱乐的推荐视频id用这个字段）
    private String preVID;// 推荐视频id（体育的推荐视频id用这个字段）
    private String chatRoomNum;// 聊天室RoomId
    private Integer recordingId;// 录播id
    private String ch;
    private List<TvStreamLiveUrl> tvStreamLiveUrls;
    private String programType;// 节目类型
    private String season;// 赛季
    private String level1;// 1级赛事
    private String level2;// 2级赛事
    private String match;// 比赛轮 1/8决赛
    private String home;// 主场队
    private String guest;// 客场队
    private String homescore;// 主场得分
    private String guestscore;// 客场得分
    private String backgroundImgUrl;// 背景图
    private String screenings;// 场次
    private String videoUrl;// 详情页背景图

    private String END = "&ext=m3u8";// TV版统一加一个直播参数。由于直播中心统一对各个终端提供url,不同终端根据自己需要添加ext值。像html版值xml等等

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewPic() {
        return viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        if (!StringUtils.isEmpty(beginTime)) {
            beginTime = beginTime.replaceAll("T", " ");
        }
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if (!StringUtils.isEmpty(endTime)) {
            endTime = endTime.replaceAll("T", " ");
        }
        this.endTime = endTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getVid() {
        return vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getChatRoomNum() {
        return chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public Integer getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(Integer recordingId) {
        this.recordingId = recordingId;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public List<TvStreamLiveUrl> getTvStreamLiveUrls() {
        return tvStreamLiveUrls;
    }

    public void setTvStreamLiveUrls(List<TvStreamLiveUrl> tvStreamLiveUrls) {
        this.tvStreamLiveUrls = tvStreamLiveUrls;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLevel1() {
        return level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getHomescore() {
        return homescore;
    }

    public void setHomescore(String homescore) {
        this.homescore = homescore;
    }

    public String getGuestscore() {
        return guestscore;
    }

    public void setGuestscore(String guestscore) {
        this.guestscore = guestscore;
    }

    public String getEND() {
        return END;
    }

    public void setEND(String eND) {
        END = eND;
    }

    public String getPreVideoId() {
        return preVideoId;
    }

    public void setPreVideoId(String preVideoId) {
        this.preVideoId = preVideoId;
    }

    public String getBackgroundImgUrl() {
        return backgroundImgUrl;
    }

    public void setBackgroundImgUrl(String backgroundImgUrl) {
        this.backgroundImgUrl = backgroundImgUrl;
    }

    public String getScreenings() {
        return screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPreVID() {
        return preVID;
    }

    public void setPreVID(String preVID) {
        this.preVID = preVID;
    }

    public static class TvStreamLiveUrl {
        private String code;
        private String liveUrl;
        private String streamId;

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLiveUrl() {
            return this.liveUrl;
        }

        public void setLiveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
        }

        public String getStreamId() {
            return this.streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

    }
}
