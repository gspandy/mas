package com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

public class PlayCenterInfo extends BaseData {
    private String name;
    private String icon;
    private String subTitle;
    private String downloadCount;
    private String[] accessories;
    private Integer type;
    private String value;
    private int dataType;

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public PlayCenterInfo() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String[] getAccessories() {
        return accessories;
    }

    public void setAccessories(String[] accessories) {
        this.accessories = accessories;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDataType() {
        // TODO Auto-generated method stub
        return dataType;
    }

}
