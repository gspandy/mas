package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

public class MmsDictionnaryInfo {
    private static final long serialVersionUID = 8699520887175107318L;

    private Integer id;
    private Integer typeId;
    private String value;
    private Integer parentValueId;
    private Integer propertyObject;
    private String channelId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return this.typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getParentValueId() {
        return this.parentValueId;
    }

    public void setParentValueId(Integer parentValueId) {
        this.parentValueId = parentValueId;
    }

    public Integer getPropertyObject() {
        return this.propertyObject;
    }

    public void setPropertyObject(Integer propertyObject) {
        this.propertyObject = propertyObject;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

}
