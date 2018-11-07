package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.List;

/**
 * 体育直播节目单项
 * @author letv
 */
public class SportsLiveProgramDTO {
    public enum EVENTS_TYPE {
        XI_JIA("xijia", "西甲"),
        ZHONG_CHAO("zhongchao", "中超"),
        YI_JIA("yijia", "意甲"),
        FA_JIA("fajia", "法甲"),
        O_ZHOU_LAN_QIU("ozhoulanqiu", "欧洲篮球"),
        AOWANG("aowang", "澳网"),
        CBA("CBA", "CBA"),
        ALL("index", "首页");
        private String name;
        private String typeCode;

        private EVENTS_TYPE(String typeCode, String event_type) {
            this.name = event_type;
            this.typeCode = typeCode;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypeCode() {
            return this.typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public static String getNameByType(String channelType) {
            if (channelType == null) {
                return "";
            }
            return getEVENT_TYPEByType(channelType).getName();
        }

        public static EVENTS_TYPE getEVENT_TYPEByType(String channelType) {
            if (channelType == null) {
                return ALL;
            }
            if (XI_JIA.typeCode.equalsIgnoreCase(channelType.trim())) {
                return XI_JIA;
            }
            if (ZHONG_CHAO.typeCode.equalsIgnoreCase(channelType.trim())) {
                return ZHONG_CHAO;
            }
            if (ALL.typeCode.equalsIgnoreCase(channelType.trim())) {
                return ALL;
            }
            if (FA_JIA.typeCode.equalsIgnoreCase(channelType.trim())) {
                return FA_JIA;
            }
            if (YI_JIA.typeCode.equalsIgnoreCase(channelType.trim())) {
                return YI_JIA;
            }
            if (O_ZHOU_LAN_QIU.typeCode.equalsIgnoreCase(channelType.trim())) {
                return O_ZHOU_LAN_QIU;
            }
            if (CBA.typeCode.equalsIgnoreCase(channelType.trim())) {
                return CBA;
            }
            if (AOWANG.typeCode.equalsIgnoreCase(channelType.trim())) {
                return AOWANG;
            }
            return ALL;
        }

    }

    private String id;
    private String title;// 标题
    private String description;// 描述
    private String viewPic;// 预览图片
    private String beginTime;// 播放时间
    private String endTime;// 结束时间
    private Long duration;// 比赛时长
    private String season;// 赛季
    private String level1;// 1级赛事
    private String level2;// 2级赛事
    private String match;// 比赛轮 1/8决赛
    private String home;// 主场队
    private String guest;// 客场队
    private String homescore;// 主场得分
    private String guestscore;// 客场得分
    private Integer vid; // 视频iD VideoInfo.java中ID为Integer类型
    private Integer pid;// 专辑id album.java 中ID为Integer类型
    private String chatRoomNum;// 聊天室RoomId
    private Integer recordingId;// 录播id
    private String END = "&ext=m3u8";// TV版统一加一个直播参数。由于直播中心统一对各个终端提供url,不同终端根据自己需要添加ext值。像html版值xml等等
    private List<TvStreamLiveUrl> tvStreamLiveUrls;
    private String ch;

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
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
        if (this.beginTime != null) {
            return this.beginTime.replaceAll("T", " ");
        }
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        if (this.endTime != null) {
            return this.endTime.replaceAll("T", " ");
        }
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

    public Integer getVid() {
        return this.vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getRecordingId() {
        return this.recordingId;
    }

    public void setRecordingId(Integer recordingId) {
        this.recordingId = recordingId;
    }

    public List<TvStreamLiveUrl> getTvStreamLiveUrls() {
        return this.tvStreamLiveUrls;
    }

    public void setTvStreamLiveUrls(List<TvStreamLiveUrl> tvStreamLiveUrls) {
        this.tvStreamLiveUrls = tvStreamLiveUrls;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getScore() {
        return getHomescore() + "-" + getGuestscore();
    }

    public String getVS() {
        return getHome() + "-" + getGuest();
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
