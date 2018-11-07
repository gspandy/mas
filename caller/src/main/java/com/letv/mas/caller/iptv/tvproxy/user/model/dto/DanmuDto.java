package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.DanmuResponse;

import java.io.Serializable;
import java.util.List;

public class DanmuDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8371693813636679721L;
    private List<DanmuResponse> danmu;
    private Integer dmPidCount;
    private Integer dmVidCount;
    private String dmNotice;

    public Integer getDmPidCount() {
        return this.dmPidCount;
    }

    public void setDmPidCount(Integer dmPidCount) {
        this.dmPidCount = dmPidCount;
    }

    public Integer getDmVidCount() {
        return this.dmVidCount;
    }

    public void setDmVidCount(Integer dmVidCount) {
        this.dmVidCount = dmVidCount;
    }

    public String getDmNotice() {
        return this.dmNotice;
    }

    public void setDmNotice(String dmNotice) {
        this.dmNotice = dmNotice;
    }

    public List<DanmuResponse> getDanmu() {
        return this.danmu;
    }

    public void setDanmu(List<DanmuResponse> danmu) {
        this.danmu = danmu;
    }
}
