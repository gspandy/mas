package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.io.Serializable;

/**
 * 直播信息封装类
 * @author KevinYi
 */
public class LiveProjectTp implements Serializable {
    private String id;
    private String title;// 标题

    /**
     * 当前直播的上线平台
     */
    private String splatid;

    private String description;// 描述
    private String viewPic;// 预览图片

    /**
     * 直播开始时间，"yyyy-MM-dd HH:mm:ss"格式
     */
    private String beginTime;// 播放时间

    /**
     * 直播结束时间，"yyyy-MM-dd HH:mm:ss"格式
     */
    private String endTime;// 结束时间
    private Long vid; // 视频iD
    private Long pid;// 专辑id（体育的专题id用这个字段）
    private String preVID;// 推荐视频id（体育的推荐视频id用这个字段）
    private String chatRoomNum;// 聊天室RoomId

    /**
     * 录播id，回看视频id
     */
    private String recordingId;
    private String ch;
    private String season;// 赛季
    private String level1;// 1级赛事
    private String level2;// 2级赛事
    private String match;// 比赛轮 1/8决赛
    private String home;// 主场队
    private String guest;// 客场队
    private String homescore;// 主场得分
    private String guestscore;// 客场得分
    private String screenings;// 场次
    private String selectId;// 直播流id
    private String tvBackgroudPic;// 详情页背景图
    private String cibnSelectId;// cibn直播流id

    /**
     * 直播状态，1--未开始，2--进行中，3--已结束，4--延期，5--取消；4、5暂未使用
     */
    private Integer status;// 直播状态 ：1 预告 ，2 直播中 ，3 结束（可回看状态）
    private Integer isPay;// 直播大厅是否收费字段，0--不收费，1--收费

    /**
     * 是否有主队VS客队的数据，0--没有，1--有；等于1时，直播名称可以根据需求，展示成{home} - {guest}的形式；否则展示title
     */
    private Integer isVS;

    /**
     * 回看开始时间，"yyyy-MM-dd HH:mm:ss"格式
     */
    private String playBackStartTime;

    /**
     * 回看结束时间，"yyyy-MM-dd HH:mm:ss"格式
     */
    private String playBackEndTime;

    /**
     * 直播所属频道，如“music”，参见LiveConstants.ChannelType
     */
    private String liveType;

    /**
     * 是否允许聊天，0--不允许，1--允许
     */
    private Integer isChat;

    /**
     * 是否允许弹幕，0--不允许，1--允许
     */
    private Integer isDanmaku;

    private String musicV2Screenings;// music场次(V2)

    public String getMusicV2Screenings() {
        return musicV2Screenings;
    }

    public void setMusicV2Screenings(String musicV2Screenings) {
        this.musicV2Screenings = musicV2Screenings;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getPreVID() {
        return this.preVID;
    }

    public void setPreVID(String preVID) {
        this.preVID = preVID;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getRecordingId() {
        return this.recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getLevel1() {
        return this.level1;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
    }

    public String getLevel2() {
        return this.level2;
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
    }

    public String getMatch() {
        return this.match;
    }

    public void setMatch(String match) {
        this.match = match;
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

    public String getHomescore() {
        return this.homescore;
    }

    public void setHomescore(String homescore) {
        this.homescore = homescore;
    }

    public String getGuestscore() {
        return this.guestscore;
    }

    public void setGuestscore(String guestscore) {
        this.guestscore = guestscore;
    }

    public String getScreenings() {
        return this.screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public String getSelectId() {
        return this.selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

    public String getCibnSelectId() {
        return cibnSelectId;
    }

    public void setCibnSelectId(String cibnSelectId) {
        this.cibnSelectId = cibnSelectId;
    }

    public String getTvBackgroudPic() {
        return this.tvBackgroudPic;
    }

    public void setTvBackgroudPic(String tvBackgroudPic) {
        this.tvBackgroudPic = tvBackgroudPic;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsPay() {
        return this.isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsVS() {
        return this.isVS;
    }

    public void setIsVS(Integer isVS) {
        this.isVS = isVS;
    }

    public String getPlayBackStartTime() {
        return this.playBackStartTime;
    }

    public void setPlayBackStartTime(String playBackStartTime) {
        this.playBackStartTime = playBackStartTime;
    }

    public String getPlayBackEndTime() {
        return this.playBackEndTime;
    }

    public void setPlayBackEndTime(String playBackEndTime) {
        this.playBackEndTime = playBackEndTime;
    }

    public String getLiveType() {
        return this.liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public Integer getIsChat() {
        return this.isChat;
    }

    public void setIsChat(Integer isChat) {
        this.isChat = isChat;
    }

    public Integer getIsDanmaku() {
        return this.isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getSplatid() {
        return splatid;
    }

    public void setSplatid(String splatid) {
        this.splatid = splatid;
    }

}
