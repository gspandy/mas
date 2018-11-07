package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.Map;

/**
 * 乐搜新首页使用的专辑类型
 */
public class MainPageAlbum {
    private String avail_records; // 专辑更新到第几集
    private Map<String, String> cover; // 封面图片
    private String description; // 专辑描述
    private String id; // 专辑id
    private String latest_record; // 专辑最近更新的记录
    private String name; // 专辑名称
    private String push_flag; // 推送平台
    private String rating; // 评分
    private String region; // 区域
    private String source; // 搜索内部使用的数据来源
    private String src; // 视频来源 1:letv 2: 外网视频
    private String sub_src; // 视频来源，例如youku iqiyi
    private String subname; // 专辑子标题
    private String total_records; // 专辑总集数
    private String url; // 播放地址
    private Integer dataType;// 数据类型 1专辑、2视频、3明星、4专题
    private String videoType;// 影片类型 ,例如180001代表正片
    private String isHomemade; // 是否自制
    private String ispay; // 是否付费
    private String payPlatform; // 支付平台
    private String letv_original_id;// 数据上报用到
    private String global_id;

    public String getGlobal_id() {
        return this.global_id;
    }

    public void setGlobal_id(String global_id) {
        this.global_id = global_id;
    }

    public String getLetv_original_id() {
        return this.letv_original_id;
    }

    public void setLetv_original_id(String letv_original_id) {
        this.letv_original_id = letv_original_id;
    }

    public String getPayPlatform() {
        return this.payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getAvail_records() {
        return this.avail_records;
    }

    public void setAvail_records(String avail_records) {
        this.avail_records = avail_records;
    }

    public Map<String, String> getCover() {
        return this.cover;
    }

    public void setCover(Map<String, String> cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatest_record() {
        return this.latest_record;
    }

    public void setLatest_record(String latest_record) {
        this.latest_record = latest_record;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPush_flag() {
        return this.push_flag;
    }

    public void setPush_flag(String push_flag) {
        this.push_flag = push_flag;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getSubname() {
        return this.subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getTotal_records() {
        return this.total_records;
    }

    public void setTotal_records(String total_records) {
        this.total_records = total_records;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getVideoType() {
        return this.videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getIsHomemade() {
        return this.isHomemade;
    }

    public void setIsHomemade(String isHomemade) {
        this.isHomemade = isHomemade;
    }

    public String getIspay() {
        return this.ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

}
