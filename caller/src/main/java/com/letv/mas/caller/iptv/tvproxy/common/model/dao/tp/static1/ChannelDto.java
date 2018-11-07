package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * TV v2.5 大首页
 * @author wanglonghu
 */
@XmlRootElement
public class ChannelDto implements Comparable<ChannelDto> {

    private String cName;// 频道名称
    private String cCode;// 频道编码
    private Integer position;// 频道位置，在调整频道顺序时使用

    private List<ResourceDto> resources;

    private String color;

    private String condition;

    private String searchUrl;

    private String channelId;

    private String categoryId;

    private int dataSrc;

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int compareTo(ChannelDto o) {
        if (this.position > o.getPosition()) {
            return 1;
        } else if (this.position < o.getPosition()) {
            return -1;
        } else {
            return 0;
        }
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(int dataSrc) {
        this.dataSrc = dataSrc;
    }

}
