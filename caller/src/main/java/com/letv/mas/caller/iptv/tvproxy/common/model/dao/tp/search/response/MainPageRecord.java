package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.Map;

/**
 * 乐搜新首页使用的视频类型
 */
public class MainPageRecord {
    private String id; // 视频id
    private String topic; // 对应的话题
    private String name; // 视频名称
    private String subname; // 视频子名称
    private String src; // 视频来源 1:letv 2:外网
    private String sub_src; // 视频子来源 例如 youku iqiyi
    private String push_flag; // 视频推送平台
    private String source; // 搜索内部视频来源
    private String rating; // 评分
    private Map<String, String> cover; // 图片
    private String url; // 播放地址
    private String description; // 描述
    private String letv_original_id;// 数据上报用到

    public String getLetv_original_id() {
        return this.letv_original_id;
    }

    public void setLetv_original_id(String letv_original_id) {
        this.letv_original_id = letv_original_id;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return this.subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSub_src() {
        return this.sub_src;
    }

    public void setSub_src(String sub_src) {
        this.sub_src = sub_src;
    }

    public String getPush_flag() {
        return this.push_flag;
    }

    public void setPush_flag(String push_flag) {
        this.push_flag = push_flag;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Map<String, String> getCover() {
        return this.cover;
    }

    public void setCover(Map<String, String> cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
