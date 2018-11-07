package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.GetUrmActivityResponse.UrmActivityReport;

public class MemberDeskActivityBaseData extends BaseData {
    private int index;

    private String image;
    private String smallImage;

    private UrmActivityReport reporting;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UrmActivityReport getReporting() {
        return reporting;
    }

    public void setReporting(UrmActivityReport reporting) {
        this.reporting = reporting;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }
}