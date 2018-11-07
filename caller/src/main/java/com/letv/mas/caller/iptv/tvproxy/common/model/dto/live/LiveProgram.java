package com.letv.mas.caller.iptv.tvproxy.common.model.dto.live;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiveProgram implements Comparable<LiveProgram>, Serializable {
    private static final long serialVersionUID = 7507886216559215270L;

    private String id;// 唯一标识
    private String liveName;// 直播标题--直播、回看
    private String stateName;
    private Integer playCategory;

    /**
     * 比赛开始时间，long型毫秒值
     */
    private String startTime;

    /**
     * 比赛结束时间，long型毫秒值
     */
    private String endTime;

    private Long duration;// 直播时长
    private String liveContentDesc;// 直播简介
    private String playUrl;// 播放地址
    private String defaultStreamCode;// 默认播放码流
    private String score;// 比分 3：3
    private String pk;// MMMM
    private String matchStage;// 比赛阶段 赛事信息(第XX轮、1/8决赛，1/4决赛，半决赛，总决赛等）
    private String sportsType;// 一级赛事 足球、篮球
    private String sportsSubType;// 二级赛事 ：西甲、中超
    private String vid;// 回时点播使用，去vrs取播放地址，2016-01-06更正为集锦视频id
    private String season;// 赛季
    private String ablumId;
    private String viewPic;// 预览图片
    private Integer state;// 1--未开始，2--进行中，3--已结束，4--回看
    private String chatRoomNum;
    private String ename;
    private String cid;
    private String vType;
    private String ch;
    private String date;
    private String time;
    private String type;
    private String preVideoId;// 推荐视频id，2016-01-06更正为预告视频id
    private String backgroundImgUrl;// 背景图
    private String screenings;// 场次
    private String musicV2Screenings;// 音乐场次
    private Integer isPay;// 是否收费，0--不收费，1--收费

    /**
     * 回看开始时间，表示毫秒值的long型字符串
     */
    private String replayTime;

    /**
     * 回看结束时间，表示毫秒值的long型字符串
     */
    private String replayEndTime;

    /**
     * 是否允许弹幕，0--不允许，1--允许
     */
    private Integer isDanmaku;

    /**
     * 回看视频id
     */
    private String recordingId;

    /**
     * 回看文案，目前用在直播后台未填写直播回看结束时间时展示，如"回看-{replayText}"（“回看--当前回看中”）
     */
    private String replayText;

    private String home;// 主队
    private String guest;// 客队

    /**
     * 是否付费 false：付费 true：免费
     * 2016-04-12，客户端直播model采用LiveProject设计，这里需要兼容
     */
    private boolean isFree = true;
    private String selectId;

    private List<TvStreamInfoDto> tvStreamInfo = new ArrayList<TvStreamInfoDto>();

    @Override
    public int compareTo(LiveProgram liveProgram) {
        if (this.getState() > liveProgram.getState()) {
            return -1;
        } else if (this.getState() < liveProgram.getState()) {
            return 1;
        } else if (Long.valueOf(this.getStartTime()).longValue() > Long.valueOf(liveProgram.getStartTime()).longValue()) {
            return 1;
        } else if (Long.valueOf(this.getStartTime()).longValue() < Long.valueOf(liveProgram.getStartTime()).longValue()) {
            return -1;
        } else if (Long.valueOf(this.getEndTime()).longValue() > Long.valueOf(liveProgram.getEndTime()).longValue()) {
            return 1;
        } else if (Long.valueOf(this.getEndTime()).longValue() < Long.valueOf(liveProgram.getEndTime()).longValue()) {
            return -1;
        }

        return 1;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiveName() {
        return this.liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getPlayCategory() {
        return this.playCategory;
    }

    public void setPlayCategory(Integer playCategory) {
        this.playCategory = playCategory;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getLiveContentDesc() {
        return this.liveContentDesc;
    }

    public void setLiveContentDesc(String liveContentDesc) {
        this.liveContentDesc = liveContentDesc;
    }

    public String getPlayUrl() {
        return this.playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getDefaultStreamCode() {
        return defaultStreamCode;
    }

    public void setDefaultStreamCode(String defaultStreamCode) {
        this.defaultStreamCode = defaultStreamCode;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPk() {
        return this.pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMatchStage() {
        return this.matchStage;
    }

    public void setMatchStage(String matchStage) {
        this.matchStage = matchStage;
    }

    public String getSportsType() {
        return this.sportsType;
    }

    public void setSportsType(String sportsType) {
        this.sportsType = sportsType;
    }

    public String getSportsSubType() {
        return this.sportsSubType;
    }

    public void setSportsSubType(String sportsSubType) {
        this.sportsSubType = sportsSubType;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getAblumId() {
        return this.ablumId;
    }

    public void setAblumId(String ablumId) {
        this.ablumId = ablumId;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getEname() {
        return this.ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getvType() {
        return this.vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TvStreamInfoDto> getTvStreamInfo() {
        return this.tvStreamInfo;
    }

    public void setTvStreamInfo(List<TvStreamInfoDto> tvStreamInfo) {
        this.tvStreamInfo = tvStreamInfo;
    }

    public String getPreVideoId() {
        return this.preVideoId;
    }

    public void setPreVideoId(String preVideoId) {
        this.preVideoId = preVideoId;
    }

    public String getBackgroundImgUrl() {
        return this.backgroundImgUrl;
    }

    public void setBackgroundImgUrl(String backgroundImgUrl) {
        this.backgroundImgUrl = backgroundImgUrl;
    }

    public String getScreenings() {
        return this.screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public Integer getIsPay() {
        return this.isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getReplayTime() {
        return this.replayTime;
    }

    public void setReplayTime(String replayTime) {
        this.replayTime = replayTime;
    }

    public String getReplayEndTime() {
        return this.replayEndTime;
    }

    public void setReplayEndTime(String replayEndTime) {
        this.replayEndTime = replayEndTime;
    }

    public String getRecordingId() {
        return this.recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public String getHome() {
        return this.home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return this.guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Integer getIsDanmaku() {
        return this.isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getReplayText() {
        return replayText;
    }

    public void setReplayText(String replayText) {
        this.replayText = replayText;
    }

    public boolean getIsFree() {
        return this.isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getMusicV2Screenings() {
        return musicV2Screenings;
    }

    public void setMusicV2Screenings(String musicV2Screenings) {
        this.musicV2Screenings = musicV2Screenings;
    }
}
