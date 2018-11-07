package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto;

import java.util.List;

public class LiveProgramDto {

    public final static String PROGRAM_STAUTS_REPLAY = "3"; // 回看
    public final static String PROGRAM_STAUTS_LIVING = "2"; // 直播中
    public final static String PROGRAM_STAUTS_UNPLAY = "1"; // 未开始

    private String id; // 节目单id
    private String duration; // 节目时长，单位ms
    private String endTime; // 结束时间
    private String playTime; // 开始时间
    private Long playTimeinMills; // 节目开始时间
    private Long endTimeinMills; // 节目结束时间
    private String title; // 节目标题
    private String viewPic; // 节目配图
    private String price;// 价格
    private String vipPrice;// vip价格
    private String originPrice;
    /**
     * 付费类型
     * 1: 包年以上会员免费
     * 2:包年以上或单点免费
     * 3:会员免费
     * 4:会员或单点免费
     * 5:单点
     * 6:全屏包年以上或单点免费
     */
    private String payType;
    private String isCharge;// 是否已经付费
    private String screenings;// 场次
    private String status;// 直播专用字段，状态 1:未开始 2:直播中 3:已结束
    private String type;// 二级分类
    private String subType;// 三级分类
    private String chatRoomNum;// 聊天室RoomId
    private String isChat;// 是否启用聊天室是否启用聊天（1 是 0 否）
    private String aid; // 专辑id
    private String vid; // 视频id
    private String liveId;
    private String pid;
    private String programType;
    private String mediaType;
    private List<ProgramView> videos;

    public List<ProgramView> getVideos() {
        return videos;
    }

    public void setVideos(List<ProgramView> videos) {
        this.videos = videos;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatRoomNum() {
        return chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getIsChat() {
        return isChat;
    }

    public void setIsChat(String isChat) {
        this.isChat = isChat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public Long getPlayTimeinMills() {
        return playTimeinMills;
    }

    public void setPlayTimeinMills(Long playTimeinMills) {
        this.playTimeinMills = playTimeinMills;
    }

    public Long getEndTimeinMills() {
        return endTimeinMills;
    }

    public void setEndTimeinMills(Long endTimeinMills) {
        this.endTimeinMills = endTimeinMills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewPic() {
        return viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(String originPrice) {
        this.originPrice = originPrice;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(String isCharge) {
        this.isCharge = isCharge;
    }

    public String getScreenings() {
        return screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ProgramView {
        private String vid;
        private Long playTime;
        private Long endTime;

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public Long getPlayTime() {
            return playTime;
        }

        public void setPlayTime(Long playTime) {
            this.playTime = playTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }
    }
}
