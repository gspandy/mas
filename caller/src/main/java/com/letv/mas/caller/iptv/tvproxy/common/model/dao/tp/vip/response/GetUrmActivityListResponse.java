package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.GetUrmActivityResponse.*;

import java.io.Serializable;
import java.util.List;

public class GetUrmActivityListResponse implements Serializable {
    private String touchSpotId;

    /**
     * urm activity data list
     */
    private List<UrmActivityData> creatives;

    /**
     * report info
     */
    private UrmActivityReport reporting;

    public String getTouchSpotId() {
        return touchSpotId;
    }

    public void setTouchSpotId(String touchSpotId) {
        this.touchSpotId = touchSpotId;
    }

    public List<UrmActivityData> getCreatives() {
        return creatives;
    }

    public void setCreatives(List<UrmActivityData> creatives) {
        this.creatives = creatives;
    }

    public UrmActivityReport getReporting() {
        return reporting;
    }

    public void setReporting(UrmActivityReport reporting) {
        this.reporting = reporting;
    }
}