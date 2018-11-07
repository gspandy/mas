package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveProgram;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.util.List;

/**
 * 体育直播节目单项
 * @author letv
 */
public class SportsLiveProgramDto extends BaseDto {
    /**
	 *
	 */
    private static final long serialVersionUID = 2905927423299023730L;
    private String id;// 唯一标识
    private String liveName;// 直播标题--直播、回看（2.0用这个字段显示时间）
    private String liveDate;// 直播标题--直播、回看（新版本接口用该字段标识时间）
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
    private Integer vid;// 回时点播使用，去vrs取播放地址
    private String season;// 赛季
    private Integer ablumId;
    private String viewPic;// 预览图片
    private Integer state;
    private String chatRoomNum;
    private String ename = "sports";
    private Integer cid = 4;
    private String vType;
    private String ch;
    private List<TvStreamInfoDto> tvStreamInfo;

    public SportsLiveProgramDto() {
        super();
    }

    public SportsLiveProgramDto(LiveProgram sportsLiveProgram) {
        if (sportsLiveProgram != null) {
            this.id = sportsLiveProgram.getId();
            this.pk = sportsLiveProgram.getPk();
            this.startTime = sportsLiveProgram.getStartTime();
            this.endTime = sportsLiveProgram.getEndTime();
            this.duration = sportsLiveProgram.getDuration();
            this.liveContentDesc = sportsLiveProgram.getLiveContentDesc();
            this.sportsType = sportsLiveProgram.getSportsType();
            this.sportsSubType = sportsLiveProgram.getSportsSubType();
            this.matchStage = sportsLiveProgram.getSportsSubType() + sportsLiveProgram.getMatchStage();
            this.vid = StringUtil.toInteger(sportsLiveProgram.getVid(), 0);
            this.viewPic = sportsLiveProgram.getViewPic();
            this.ablumId = StringUtil.toInteger(sportsLiveProgram.getAblumId(), 0);
            this.score = " " + sportsLiveProgram.getScore() + " ";
            this.season = sportsLiveProgram.getSeason();
            this.playUrl = sportsLiveProgram.getPlayUrl();
            this.chatRoomNum = sportsLiveProgram.getChatRoomNum();
            this.vType = sportsLiveProgram.getvType();
            this.ch = sportsLiveProgram.getCh();
            this.state = sportsLiveProgram.getState();
            this.stateName = sportsLiveProgram.getStateName();
        }
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

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getEname() {
        return this.ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
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

    public String getLiveDate() {
        return this.liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
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

    public Integer getVid() {
        return this.vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
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

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<TvStreamInfoDto> getTvStreamInfo() {
        return this.tvStreamInfo;
    }

    public void setTvStreamInfo(List<TvStreamInfoDto> tvStreamInfo) {
        this.tvStreamInfo = tvStreamInfo;
    }

    public enum PROGRAM_TYPE {
        LIVE("Live", 2),
        PRELIVE("预告", 1),
        JI_JIN("集锦", 3),
        HUI_KAN("回看", 4);
        private String name;
        private Integer index;

        private PROGRAM_TYPE(String type, Integer index) {
            this.name = type;
            this.index = index;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getIndex() {
            return this.index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

    }

    @Override
    public String toString() {
        return "SportsLiveProgramDto [id=" + this.id + ", liveName=" + this.liveName + ", playCategory="
                + this.playCategory + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", duration="
                + this.duration + ", liveContentDesc=" + this.liveContentDesc + ", playUrl=" + this.playUrl
                + ", score=" + this.score + ", pk=" + this.pk + ", matchStage=" + this.matchStage + ", sportsType="
                + this.sportsType + ", sportsSubType=" + this.sportsSubType + ", vid=" + this.vid + ", season="
                + this.season + ", ablumId=" + this.ablumId + ", viewPic=" + this.viewPic + "]";
    }

}
