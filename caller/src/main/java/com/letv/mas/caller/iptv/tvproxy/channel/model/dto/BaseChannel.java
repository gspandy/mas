package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

public class BaseChannel extends BaseData {

    /**
     * 
     */
    private static final long serialVersionUID = -2870578613434608612L;

    /**
     * 数据类型
     */
    private Integer titleDataType;

    /**
     * URL
     */
    private String dataUrl;

    /**
     * 统一跳转配置信息，该属性对客户端不可见（其实完全可以由dataUrl替代，但是为了兼容已有的逻辑，这里重新定义新属性）
     */
    private String configInfo;

    /**
     * cpId
     */
    private String cpId;

    /**
     * 某一cp下的类别Id
     */
    private String cpCategoryId;
    /**
     * id标识
     */
    private String globalId;

    private String iconType;

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getCpCategoryId() {
        return cpCategoryId;
    }

    public void setCpCategoryId(String cpCategoryId) {
        this.cpCategoryId = cpCategoryId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Integer getTitleDataType() {
        return titleDataType;
    }

    public void setTitleDataType(Integer titleDataType) {
        this.titleDataType = titleDataType;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

}
