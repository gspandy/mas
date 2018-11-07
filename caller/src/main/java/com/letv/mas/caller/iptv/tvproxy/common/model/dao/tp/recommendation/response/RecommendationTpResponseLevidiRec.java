package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.io.Serializable;
import java.util.Map;

public class RecommendationTpResponseLevidiRec implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4071850597731820995L;

    private String cp_provider;// cp名称
    private String id;// globalid
    private String global_id;// globalid
    private String external_id;// content_id 播放使用
    private String vid;// 视频id
    private String aid;// 专辑id
    private String mmid;// 媒资id
    private String cid;// 类别
    private String name;// 专辑或视频名称
    private String subname;// 子标题
    private String mini_poster;//
    private String data_type;// 数据类型
    private String description;// 描述信息
    private String is_pay;// 是否付费
    private String is_end;// 是否完结
    private String playurl;// 播放地址
    private String source;// 来源（letv，youtube）
    private String src;// 1、内网 2、外网
    private String play_count;// 播放数
    private String play_platform;// 播放平台
    private String release_date;// 发不日期
    private Map<String, String> poster;// 海报图

    private String channel_id;// cpId
    private String pic_url;// cp图片
    private String category_id;
    private String category_name;
    private String category_pic;
    private String remark;
    private String rec_source;// 来源

    public String getRec_source() {
        return rec_source;
    }

    public void setRec_source(String rec_source) {
        this.rec_source = rec_source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getGlobal_id() {
        return global_id;
    }

    public void setGlobal_id(String global_id) {
        this.global_id = global_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_pic() {
        return category_pic;
    }

    public void setCategory_pic(String category_pic) {
        this.category_pic = category_pic;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCp_provider() {
        return cp_provider;
    }

    public void setCp_provider(String cp_provider) {
        this.cp_provider = cp_provider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getMmid() {
        return mmid;
    }

    public void setMmid(String mmid) {
        this.mmid = mmid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getMini_poster() {
        return mini_poster;
    }

    public void setMini_poster(String mini_poster) {
        this.mini_poster = mini_poster;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getIs_end() {
        return is_end;
    }

    public void setIs_end(String is_end) {
        this.is_end = is_end;
    }

    public String getPlayurl() {
        return playurl;
    }

    public void setPlayurl(String playurl) {
        this.playurl = playurl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPlay_count() {
        return play_count;
    }

    public void setPlay_count(String play_count) {
        this.play_count = play_count;
    }

    public String getPlay_platform() {
        return play_platform;
    }

    public void setPlay_platform(String play_platform) {
        this.play_platform = play_platform;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Map<String, String> getPoster() {
        return poster;
    }

    public void setPoster(Map<String, String> poster) {
        this.poster = poster;
    }

}
