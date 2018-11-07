package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LiveComplexProgramResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;

import java.util.Date;
import java.util.List;

public class ComplexLiveProgramDto extends BaseDto implements Comparable<Object> {
    private static final long serialVersionUID = -1508953286194397418L;

    private String id;
    private String liveName;
    private Integer state;
    private String stateName;
    private Long startTime;
    private Long endTime;
    private Long duration;
    private String liveContentDesc;
    private String playUrl;
    private Long vid;
    private Long albumId;
    private String viewPic;
    private String liveTime;
    private String liveCategoryName;
    private String chatRoomNum;
    private String ename;
    private Integer cid;
    private String vtype;
    private String ch;
    private List<TvStreamInfoDto> tvStreamInfo;

    public List<TvStreamInfoDto> getTvStreamInfo() {
        return this.tvStreamInfo;
    }

    public void setTvStreamInfo(List<TvStreamInfoDto> tvStreamInfo) {
        this.tvStreamInfo = tvStreamInfo;
    }

    public String getVtype() {
        return this.vtype;
    }

    public void setVtype(String vtype) {
        this.vtype = vtype;
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

    public ComplexLiveProgramDto() {
    }

    public ComplexLiveProgramDto(LiveComplexProgramResponse p) {
        if (p != null) {
            this.id = p.getId();
            this.liveName = p.getTitle();
            Date btime = TimeUtil.getDate(p.getBeginTime(), TimeUtil.SIMPLE_DATE_FORMAT);
            this.startTime = btime != null ? btime.getTime() : 0L;
            Date etime = TimeUtil.getDate(p.getEndTime(), TimeUtil.SIMPLE_DATE_FORMAT);
            this.endTime = etime != null ? etime.getTime() : 0L;
            this.duration = this.endTime - this.startTime;
            this.liveContentDesc = p.getDescription();
            String[] cp = p.getPlayUrl(2);
            if (cp != null && cp.length == 2) {
                this.playUrl = cp[0];
                this.vtype = cp[1];
            }
            this.vid = p.getVid();
            this.albumId = p.getPid();
            this.viewPic = p.getViewPic();
            this.chatRoomNum = p.getChatRoomNum();
            this.ch = p.getCh();
        }
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

    public String getLiveName() {
        return this.liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
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

    public Long getVid() {
        return this.vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Long getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public String getLiveTime() {
        return this.liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getLiveCategoryName() {
        return this.liveCategoryName;
    }

    public void setLiveCategoryName(String liveCategoryName) {
        this.liveCategoryName = liveCategoryName;
    }

    @Override
    public int compareTo(Object o) {
        ComplexLiveProgramDto complexLiveProgram = (ComplexLiveProgramDto) o;

        if (this.getState() > complexLiveProgram.getState()) {
            return -1;
        } else if (this.getState() < complexLiveProgram.getState()) {
            return 1;
        } else if (this.getStartTime().longValue() > complexLiveProgram.getStartTime().longValue()) {
            return 1;
        } else if (this.getStartTime().longValue() < complexLiveProgram.getStartTime().longValue()) {
            return -1;
        } else if (this.getEndTime().longValue() > complexLiveProgram.getEndTime().longValue()) {
            return 1;
        } else if (this.getEndTime().longValue() < complexLiveProgram.getEndTime().longValue()) {
            return -1;
        }

        return 0;
    }
}
