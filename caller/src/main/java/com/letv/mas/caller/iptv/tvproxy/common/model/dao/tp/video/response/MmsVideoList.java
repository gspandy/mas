package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

public class MmsVideoList implements Serializable {

    private static final long serialVersionUID = -1721140582843093311L;

    private Integer pagenum;// 当前页
    private Integer totalNum;// 总数
    private Integer videoPosition;// 视频位置
    private List<MmsVideoInfo> videoInfo;// 视频列表

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getVideoPosition() {
        return videoPosition;
    }

    public void setVideoPosition(Integer videoPosition) {
        this.videoPosition = videoPosition;
    }

    public List<MmsVideoInfo> getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(List<MmsVideoInfo> videoInfo) {
        this.videoInfo = videoInfo;
    }

}
