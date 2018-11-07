package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.util.List;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LiveResponseTp.TvStreamLiveUrl;

public class LiveComplexProgramResponse {
    private static final long serialVersionUID = 6533573636872490534L;

    private String id;
    private String title;// 标题
    private String description;// 描述
    private String viewPic;// 预览图片
    private String beginTime;// 播放时间
    private String endTime;// 结束时间
    private Long duration;// 比赛时长
    private Long vid; // 视频iD VideoInfo.java中ID为Integer类型
    private Long pid;// 专辑id album.java 中ID为Integer类型
    private String chatRoomNum;// 聊天室RoomId
    private Integer recordingId;// 录播id
    private String ch;
    private List<LiveResponseTp.TvStreamLiveUrl> tvStreamLiveUrls;
    private String programType;// 节目类型

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public Long getVid() {
        return this.vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
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

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public Integer getRecordingId() {
        return this.recordingId;
    }

    public void setRecordingId(Integer recordingId) {
        this.recordingId = recordingId;
    }

    public List<LiveResponseTp.TvStreamLiveUrl> getTvStreamLiveUrls() {
        return this.tvStreamLiveUrls;
    }

    public void setTvStreamLiveUrls(List<TvStreamLiveUrl> tvStreamLiveUrls) {
        this.tvStreamLiveUrls = tvStreamLiveUrls;
    }

    public String[] getPlayUrl(Integer src) {
        String[] codeAndPlayUrl = null;
        if (this.tvStreamLiveUrls != null && this.tvStreamLiveUrls.size() > 0) {
            codeAndPlayUrl = new String[2];
            for (TvStreamLiveUrl tvStreamLiveUrl : this.tvStreamLiveUrls) {
                codeAndPlayUrl[0] = getLiveUrl(tvStreamLiveUrl.getLiveUrl(), src);
                codeAndPlayUrl[1] = tvStreamLiveUrl.getCode();
                break;
            }
        }
        return codeAndPlayUrl;
    }

    private String getLiveUrl(String liveUrl, Integer src) {
        String url = null;
        if (src != null && src == 2) {
            if (liveUrl != null) {
                if (liveUrl.indexOf("?") != -1) {
                    url = liveUrl + "&platid=10&splatid=1007&pay=0&ext=m3u8";
                } else {
                    url = liveUrl + "?platid=10&splatid=1007&pay=0&ext=m3u8";
                }
            }
        } else {
            if (liveUrl != null) {
                if (liveUrl.indexOf("?") != -1) {
                    url = liveUrl + "&platid=10&splatid=1008&pay=0&ext=m3u8";
                } else {
                    url = liveUrl + "?platid=10&splatid=1008&pay=0&ext=m3u8";
                }
            }
        }
        return url;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

}
