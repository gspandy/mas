package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;

import java.util.List;

public class LiveProgramDto extends BaseDto implements Comparable<LiveProgramDto> {

    /**
     * 
     */
    private static final long serialVersionUID = -4259262747086133139L;
    private String id;// 唯一标识
    private String liveName;// 直播标题--直播、回看
    private String stateName;
    private Integer playCategory;
    private String startTime;// 比赛开始时间 long
    private String endTime;// 比赛结束时间 long
    private Long duration;// 直播时长
    private String liveContentDesc;// 直播简介
    private String playUrl;// 播放地址
    private String score;// 比分 3：3
    private String pk;// XXX-MMMM
    private String matchStage;// 比赛阶段 赛事信息(第XX轮、1/8决赛，1/4决赛，半决赛，总决赛等）
    private String sportsType;// 一级赛事 足球、篮球
    private String sportsSubType;// 二级赛事 ：西甲、中超
    private String vid;// 回时点播使用，去vrs取播放地址
    private String season;// 赛季
    private String ablumId;
    private String viewPic;// 预览图片
    private Integer state;
    private String chatRoomNum;
    private String ename;
    private String cid;
    private String vType;
    private String ch;
    private String date;
    private String time;
    private String type;
    private List<TvStreamInfoDto> tvStreamInfo;

    public LiveProgramDto(ComplexLiveProgramDto clp) {
        if (clp != null) {
            this.id = clp.getId();
            this.liveName = clp.getLiveName();
            this.stateName = clp.getStateName();
            this.playCategory = null;
            this.startTime = clp.getStartTime().toString();
            this.endTime = clp.getEndTime().toString();
            this.duration = clp.getDuration();
            this.liveContentDesc = clp.getLiveContentDesc();
            this.playUrl = clp.getPlayUrl();
            this.score = null;
            this.pk = null;
            this.matchStage = null;
            this.sportsType = null;
            this.sportsSubType = null;
            this.vid = clp.getVid() == null ? null : clp.getVid().toString();
            this.season = null;
            this.ablumId = clp.getAlbumId() == null ? null : clp.getAlbumId().toString();
            this.viewPic = clp.getViewPic();
            this.state = clp.getState();
            this.chatRoomNum = clp.getChatRoomNum();
            this.ename = clp.getEname();
            this.cid = clp.getCid() == null ? null : clp.getCid().toString();
            this.vType = clp.getVtype();
            this.ch = clp.getCh();
            this.type = clp.getLiveCategoryName();
            this.date = clp.getLiveTime();
            this.tvStreamInfo = clp.getTvStreamInfo();
        }
    }

    public LiveProgramDto(SportsLiveProgramDto slp) {
        if (slp != null) {
            this.id = slp.getId();
            this.liveName = slp.getPk();
            this.stateName = slp.getStateName();
            this.playCategory = slp.getPlayCategory();
            this.startTime = slp.getStartTime();
            this.endTime = slp.getEndTime();
            this.duration = slp.getDuration();
            this.liveContentDesc = slp.getLiveContentDesc();
            this.playUrl = slp.getPlayUrl();
            this.score = slp.getScore();
            this.pk = slp.getPk();
            this.matchStage = slp.getMatchStage();
            this.sportsType = slp.getSportsType();
            this.sportsSubType = slp.getSportsSubType();
            this.vid = slp.getVid() == null ? null : slp.getVid().toString();
            this.season = slp.getSeason();
            this.ablumId = slp.getAblumId() == null ? null : slp.getAblumId().toString();
            this.viewPic = slp.getViewPic();
            this.state = slp.getState();
            this.chatRoomNum = slp.getChatRoomNum();
            this.ename = "sports";
            this.cid = "4";
            this.vType = slp.getvType();
            this.ch = slp.getCh();
            this.type = slp.getSportsSubType();
            this.date = slp.getLiveName();
            this.tvStreamInfo = slp.getTvStreamInfo();
        }
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

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
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

    public void setVid(String vid) {
        this.vid = vid;
    }

    public void setAblumId(String ablumId) {
        this.ablumId = ablumId;
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

    public String getVid() {
        return this.vid;
    }

    public String getAblumId() {
        return this.ablumId;
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

    @Override
    public int compareTo(LiveProgramDto o) {
        LiveProgramDto liveProgram = o;

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

        return 0;
    }

}
