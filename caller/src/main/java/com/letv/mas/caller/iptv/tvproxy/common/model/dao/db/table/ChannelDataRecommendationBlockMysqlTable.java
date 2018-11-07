package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

/**
 * 频道数据-推荐版块关系表
 */
public class ChannelDataRecommendationBlockMysqlTable {

    /**
     * 推荐版块key
     */
    private String rc_blockkey;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题数据类型
     */
    private Integer title_dataType;

    /**
     * 标题背景色
     */
    private String title_bgcolor;

    /**
     * 标题频道ID
     */
    private Integer title_channelid;

    /**
     * 标题检索条件
     */
    private String title_searchcondition;

    /**
     * 标题专辑ID
     */
    private Integer title_albumid;

    /**
     * 统一跳转配置信息
     */
    private String config_info;

    public String getRc_blockkey() {
        return rc_blockkey;
    }

    public void setRc_blockkey(String rc_blockkey) {
        this.rc_blockkey = rc_blockkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTitle_dataType() {
        return title_dataType;
    }

    public void setTitle_dataType(Integer title_dataType) {
        this.title_dataType = title_dataType;
    }

    public String getTitle_bgcolor() {
        return title_bgcolor;
    }

    public void setTitle_bgcolor(String title_bgcolor) {
        this.title_bgcolor = title_bgcolor;
    }

    public Integer getTitle_channelid() {
        return title_channelid;
    }

    public void setTitle_channelid(Integer title_channelid) {
        this.title_channelid = title_channelid;
    }

    public String getTitle_searchcondition() {
        return title_searchcondition;
    }

    public void setTitle_searchcondition(String title_searchcondition) {
        this.title_searchcondition = title_searchcondition;
    }

    public Integer getTitle_albumid() {
        return title_albumid;
    }

    public void setTitle_albumid(Integer title_albumid) {
        this.title_albumid = title_albumid;
    }

    public String getConfig_info() {
        return config_info;
    }

    public void setConfig_info(String config_info) {
        this.config_info = config_info;
    }
}
