package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user;

import java.io.Serializable;

public class TotalCountStatTpResponse implements Serializable {

    /**
     * 播放数
     */
    private String plist_play_count = "0";
    private String media_play_count = "0";

    /**
     * 专辑评分
     */
    private String plist_score = "0";
    private String plist_count = "0";
    private String vcomm_count = "0";

    /**
     * 评论数
     */
    private String pcomm_count = "0";

    /**
     * 豆瓣评分
     */
    private String dbsp = "0";

    /**
     * 资源id
     */
    private String Id = null;

    public String getPlist_play_count() {
        return this.plist_play_count;
    }

    public void setPlist_play_count(String plist_play_count) {
        this.plist_play_count = plist_play_count;
    }

    public String getMedia_play_count() {
        return this.media_play_count;
    }

    public void setMedia_play_count(String media_play_count) {
        this.media_play_count = media_play_count;
    }

    public String getPlist_score() {
        return this.plist_score;
    }

    public void setPlist_score(String plist_score) {
        this.plist_score = plist_score;
    }

    public String getPlist_count() {
        return this.plist_count;
    }

    public void setPlist_count(String plist_count) {
        this.plist_count = plist_count;
    }

    public String getVcomm_count() {
        return this.vcomm_count;
    }

    public void setVcomm_count(String vcomm_count) {
        this.vcomm_count = vcomm_count;
    }

    public String getPcomm_count() {
        return this.pcomm_count;
    }

    public void setPcomm_count(String pcomm_count) {
        this.pcomm_count = pcomm_count;
    }

    public String getDbsp() {
        return dbsp;
    }

    public void setDpsp(String dbsp) {
        this.dbsp = dbsp;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
