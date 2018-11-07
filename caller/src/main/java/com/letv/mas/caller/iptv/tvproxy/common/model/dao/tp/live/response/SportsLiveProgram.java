package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.List;
/**
 * 体育直播节目单项
 * @author letv
 */
public class SportsLiveProgram {
    private String id;// 唯一标识
    private String liveName;// 直播标题--直播、回看
    private Integer playCategory;
    private String startTime;// 比赛开始时间 long
    private String endTime;// 比赛结束时间 long
    private Long duration;// 直播时长
    private String liveContentDesc;// 直播简介
    private String playUrl;// 播放地址
    private String score;// 比分 3：3
    private String pk;// XXX-MMMM
    private String home;
    private String guest;
    private String matchStage;// 比赛阶段 赛事信息(第XX轮、1/8决赛，1/4决赛，半决赛，总决赛等）
    private String sportsType;// 一级赛事 足球、篮球
    private String sportsSubType;// 二级赛事 ：西甲、中超
    private Integer videoInfoId;// 回时点播使用，去vrs取播放地址
    private String season;// 赛季
    private Integer ablumId;
    private String viewPic;// 预览图片
    private String timeField;
    private SportsLiveProgram jijin;
    private Integer vid;
    private String chatRoomNum;// 聊天室RoomId
    private String vType;
    private String ch;
    private List<SportsLiveProgramDTO.TvStreamLiveUrl> tvStreamLiveUrls;

    public List<SportsLiveProgramDTO.TvStreamLiveUrl> getTvStreamLiveUrls() {
        return tvStreamLiveUrls;
    }

    public void setTvStreamLiveUrls(List<SportsLiveProgramDTO.TvStreamLiveUrl> tvStreamLiveUrls) {
        this.tvStreamLiveUrls = tvStreamLiveUrls;
    }

    public SportsLiveProgram() {
        super();
    }

    public SportsLiveProgram(String id, String playTime, String endTime2, Long duration2, String desc, String level1,
            String level2, String match, String vs, Integer videoInfoId, String viewPic2, Integer pid, String score2,
            String season2, String liveUrl, Integer vid, String home, String guest, String chatRoomNum,
            String streamCode, String ch) {
        this.id = id;
        this.pk = vs;
        this.startTime = playTime;
        this.endTime = endTime2;
        this.duration = duration2;
        this.liveContentDesc = desc;
        this.sportsType = level1;
        this.sportsSubType = level2;
        this.matchStage = match;
        this.videoInfoId = videoInfoId;
        this.viewPic = viewPic2;
        this.ablumId = pid;
        this.score = score2;
        this.season = season2;
        this.playUrl = liveUrl;
        this.vid = vid;
        this.home = home;
        this.guest = guest;
        this.chatRoomNum = chatRoomNum;
        this.vType = streamCode;
        this.ch = ch;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getvType() {
        return this.vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
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

    public String getTimeField() {
        return this.timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    public SportsLiveProgram getJijin() {
        return this.jijin;
    }

    public void setJijin(SportsLiveProgram jijin) {
        this.jijin = jijin;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPlayCategory() {
        return this.playCategory;
    }

    public void setPlayCategory(Integer playCategory) {
        this.playCategory = playCategory;
    }

    public String getLiveName() {
        return this.liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
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

    public Integer getVideoInfoId() {
        return this.videoInfoId;
    }

    public void setVideoInfoId(Integer videoInfoId) {
        this.videoInfoId = videoInfoId;
    }

    public Integer getAblumId() {
        return this.ablumId;
    }

    public void setAblumId(Integer ablumId) {
        this.ablumId = ablumId;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public Integer getVid() {
        return this.vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return "SportsLiveProgram [id=" + this.id + ", liveName=" + this.liveName + ", playCategory="
                + this.playCategory + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", duration="
                + this.duration + ", liveContentDesc=" + this.liveContentDesc + ", playUrl=" + this.playUrl
                + ", score=" + this.score + ", pk=" + this.pk + ", matchStage=" + this.matchStage + ", sportsType="
                + this.sportsType + ", sportsSubType=" + this.sportsSubType + ", videoInfoId=" + this.videoInfoId
                + ", season=" + this.season + ", ablumId=" + this.ablumId + ", viewPic=" + this.viewPic
                + ", timeField=" + this.timeField + ", jijin=" + this.jijin + ", vid=" + this.vid + "]";
    }

}
