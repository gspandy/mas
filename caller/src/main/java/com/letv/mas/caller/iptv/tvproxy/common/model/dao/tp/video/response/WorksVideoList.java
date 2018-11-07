package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.io.Serializable;
import java.util.List;

public class WorksVideoList implements Serializable {

    private static final long serialVersionUID = -2160482808725232461L;

    private Integer total;// 总数
    private List<WorksVideoInfo> videoList;// 视频列表

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<WorksVideoInfo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<WorksVideoInfo> videoList) {
        this.videoList = videoList;
    }
}
