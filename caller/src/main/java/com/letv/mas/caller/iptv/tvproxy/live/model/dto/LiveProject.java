package com.letv.mas.caller.iptv.tvproxy.live.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LiveConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LiveProjectResponseTp;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveProgram;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.io.Serializable;
import java.util.List;

public class LiveProject implements Serializable {
    private static final long serialVersionUID = 7507886746559215270L;
    private boolean isFree = false;// 是否付费 false：付费 true：免费
    private int payStatus = 0;// 付费状态 0：未付费 1：已付费
    private double price;// 价格
    private double vipPrice;// 会员价
    private double tvPrice;// 交易价
    private String focusImg;// 焦点图
    private String liveTitle;// 直播标题
    private String subTitle;// 直播副标题
    private String liveTime;// 直播时间
    private String bgImg;// 背景图
    // private int status; // 状态 1未开始 2直播中 3回看 4已结束

    private String preferTitle;// 活动描述
    private String recommendPid;// 推荐专辑
    private String recommendVideoId;// 推荐视频

    private String screenings;// 场次

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
    /**
     * 付费类型，1--包年以上会员免费，2--包年以上或单点免费，3--会员免费，4--会员或单点免费，5--单点
     */
    private Integer payType;

    /**
     * 回看视频id
     */
    private String recordingId;

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
     * 回看文案，目前用在直播后台未填写直播回看结束时间时展示，如"回看-{replayText}"（“回看--当前回看中”）
     */
    private String replayText;

    private List<TvStreamInfoDto> tvStreamInfo;

    private String selectId;

    /**
     * 付费套餐id(V2)
     */
    private Integer vipPayId;

    public Integer getVipPayId() {
        return vipPayId;
    }

    public void setVipPayId(Integer vipPayId) {
        this.vipPayId = vipPayId;
    }

    public LiveProject(CommonParam commonParam) {
        this.vipPrice = 0;
        this.price = 0;
        this.focusImg = "";
        this.liveTitle = "";
        this.subTitle = "";
        this.liveTime = "";
        this.bgImg = "";
        this.replayTime = "";

        this.id = "";// 唯一标识
        this.liveName = "";// 直播标题--直播、回看
        this.stateName = "";
        this.startTime = "";// 比赛开始时间 long
        this.endTime = "";// 比赛结束时间 long
        this.liveContentDesc = "";// 直播简介
        this.playUrl = "";// 播放地址
        this.score = "";// 比分 3：3
        this.pk = "";// MMMM
        this.matchStage = "";// 比赛阶段
                             // 赛事信息(第XX轮、1/8决赛，1/4决赛，半决赛，总决赛等）
        this.sportsType = "";// 一级赛事 足球、篮球
        this.sportsSubType = "";// 二级赛事 ：西甲、中超
        this.vid = "";// 回时点播使用，去vrs取播放地址
        this.season = "";// 赛季
        this.ablumId = "";
        this.viewPic = "";// 预览图片
        this.chatRoomNum = "";
        this.ename = "";
        this.cid = "";
        this.vType = "";
        this.ch = "";
        this.date = "";
        this.time = "";
        this.type = "";
        this.replayEndTime = "";
        this.preferTitle = MessageUtils.getMessage(LiveConstants.LIVE_ACTIVITY_DESCRIPTION, commonParam.getLangcode());
    }

    /**
     * 根据CMS专题数据设置直播信息
     * @param projectInfo
     */
    public void setCMSLiveInfo(LiveProjectResponseTp.ProjectInfo projectInfo) {
        this.id = projectInfo.getRid();// 唯一标识
        this.liveTitle = projectInfo.getRname();
        this.subTitle = projectInfo.getSubTitle();
        this.focusImg = projectInfo.getPic43();
    }

    public void setLiveInfo(LiveProgram liveProgram) {
        this.id = liveProgram.getId();// 唯一标识
        this.liveName = liveProgram.getLiveName();// 直播标题--直播、回看
        this.stateName = liveProgram.getStateName();
        this.playCategory = liveProgram.getPlayCategory();
        this.startTime = liveProgram.getStartTime();// 比赛开始时间 long
        this.endTime = liveProgram.getEndTime();// 比赛结束时间 long
        this.duration = liveProgram.getDuration();// 直播时长
        this.liveContentDesc = liveProgram.getLiveContentDesc();// 直播简介
        this.playUrl = liveProgram.getPlayUrl();// 播放地址
        this.defaultStreamCode = liveProgram.getDefaultStreamCode();
        this.score = liveProgram.getScore();// 比分 3：3
        this.pk = liveProgram.getPk();// MMMM
        this.matchStage = liveProgram.getMatchStage();// 比赛阶段
                                                      // 赛事信息(第XX轮、1/8决赛，1/4决赛，半决赛，总决赛等）
        this.sportsType = liveProgram.getSportsType();// 一级赛事 足球、篮球
        this.sportsSubType = liveProgram.getSportsSubType();// 二级赛事 ：西甲、中超
        this.vid = liveProgram.getVid();// 回时点播使用，去vrs取播放地址
        this.recordingId = liveProgram.getRecordingId();
        this.season = liveProgram.getSeason();// 赛季
        this.ablumId = liveProgram.getAblumId();
        this.viewPic = liveProgram.getViewPic();// 预览图片
        this.state = liveProgram.getState();
        this.chatRoomNum = liveProgram.getChatRoomNum();
        this.ename = liveProgram.getEname();
        this.cid = liveProgram.getCid();
        this.vType = liveProgram.getvType();
        this.ch = liveProgram.getCh();
        this.date = liveProgram.getDate();
        this.time = liveProgram.getTime();
        this.type = liveProgram.getType();
        this.tvStreamInfo = liveProgram.getTvStreamInfo();

        this.replayTime = liveProgram.getReplayTime();
        this.replayEndTime = liveProgram.getReplayEndTime();
        this.replayText = liveProgram.getReplayText();
        // Long replayTimeValue = StringUtil.toLong(liveProgram.getReplayTime(),
        // 0L);
        // if (replayTimeValue > 0) {
        // this.replayTime = TimeUtil.timestamp2date(replayTimeValue);
        // }

        // 206-03-02，直播时间相关，统一修改为表示毫秒级时间戳的long型（有可能是字符串）
        this.liveTime = liveProgram.getStartTime();

        this.bgImg = liveProgram.getBackgroundImgUrl();// 直播专题图片
        this.recommendPid = liveProgram.getAblumId();
        this.recommendVideoId = liveProgram.getPreVideoId();
        this.screenings = liveProgram.getScreenings();

        if (StringUtil.isNotBlank(liveProgram.getMusicV2Screenings())) {
            this.screenings = liveProgram.getMusicV2Screenings();
        }

        if (this.screenings != null && this.screenings.length() > 0) {
            this.isFree = false;
        } else {
            this.isFree = true;
        }

        this.isDanmaku = liveProgram.getIsDanmaku();
        this.selectId = liveProgram.getSelectId();
    }

    public boolean getIsFree() {
        return this.isFree;
    }

    public void setFree(boolean isFree) {
        this.isFree = isFree;
    }

    public int getPayStatus() {
        return this.payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVipPrice() {
        return this.vipPrice;
    }

    public void setVipPrice(double vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getFocusImg() {
        return this.focusImg;
    }

    public void setFocusImg(String focusImg) {
        this.focusImg = focusImg;
    }

    public String getLiveTitle() {
        return this.liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getLiveTime() {
        return this.liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getBgImg() {
        return this.bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getReplayTime() {
        return this.replayTime;
    }

    public void setReplayTime(String replayTime) {
        this.replayTime = replayTime;
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

    public String getPreferTitle() {
        return this.preferTitle;
    }

    public void setPreferTitle(String preferTitle) {
        this.preferTitle = preferTitle;
    }

    public String getRecommendPid() {
        return this.recommendPid;
    }

    public void setRecommendPid(String recommendPid) {
        this.recommendPid = recommendPid;
    }

    public String getRecommendVideoId() {
        return this.recommendVideoId;
    }

    public void setRecommendVideoId(String recommendVideoId) {
        this.recommendVideoId = recommendVideoId;
    }

    public String getReplayEndTime() {
        return this.replayEndTime;
    }

    public void setReplayEndTime(String replayEndTime) {
        this.replayEndTime = replayEndTime;
    }

    public String getScreenings() {
        return this.screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public double getTvPrice() {
        return this.tvPrice;
    }

    public void setTvPrice(double tvPrice) {
        this.tvPrice = tvPrice;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getRecordingId() {
        return this.recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public Integer getIsDanmaku() {
        return this.isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getReplayText() {
        return this.replayText;
    }

    public void setReplayText(String replayText) {
        this.replayText = replayText;
    }

    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }

}
