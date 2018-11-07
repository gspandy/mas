package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;

import java.io.Serializable;
/**
 * 推荐结果
 */
public class RecommendationTpResponseRec extends CmsBlockContentTpResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4635614683796374108L;
    /*
     * 通用字段
     * 1~cms_num：版块内容，是cms的数据被透传的
     * (cms_num+1)~num对应以下字段，推荐内容，cms数据不够的时候，由这些内容补充
     */
    private Integer cid;// 频道id
    private Integer isalbum;// 视频类型：0表示单视频，1表示专辑
    private String picurl;// 图片地址，横图
    private String picurl_st; // 竖图
    private String pic_16_9; // 16:9(400*225)
    private Integer pid;// 专辑id
    private String pidname;// 专辑名称
    private String subtitle;// 副标题
    private Integer vid;// 视频id
    private String starring;// 主演
    private Integer isend;// 是否已完结
    private Integer vcount;// 更新集数
    private Boolean is_rec;// 是否是推荐
    private String is_pay;// 是否付费

    // for tvod icon type recommendation
    private String pay_type;// 购买类型
    private Integer is_coupon;// 是否支持券

    private String album_play_platform;// 专辑的版权方信息
    private String video_play_platform;// 视频的版权方信息
    private String album_sub_category_code;// 新增二级分类id
    private String score;// 评分
    private String douban_rating; // 豆瓣评分
    private Long duration;// 时长，单位：秒

    private String pay_detail; // 分端付费信息集合

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public Integer getIs_coupon() {
        return is_coupon;
    }

    public void setIs_coupon(Integer is_coupon) {
        this.is_coupon = is_coupon;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDouban_rating() {
        return douban_rating;
    }

    public void setDouban_rating(String douban_rating) {
        this.douban_rating = douban_rating;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }

    public String getAlbum_sub_category_code() {
        return this.album_sub_category_code;
    }

    public void setAlbum_sub_category_code(String album_sub_category_code) {
        this.album_sub_category_code = album_sub_category_code;
    }

    public Integer getCid() {
        return this.cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getIsalbum() {
        return this.isalbum;
    }

    public void setIsalbum(Integer isalbum) {
        this.isalbum = isalbum;
    }

    public String getPicurl() {
        return this.picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getPicurl_st() {
        return this.picurl_st;
    }

    public void setPicurl_st(String picurl_st) {
        this.picurl_st = picurl_st;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPidname() {
        return this.pidname;
    }

    public void setPidname(String pidname) {
        this.pidname = pidname;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getVid() {
        return this.vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public Integer getIsend() {
        return this.isend;
    }

    public void setIsend(Integer isend) {
        this.isend = isend;
    }

    public Integer getVcount() {
        return this.vcount;
    }

    public void setVcount(Integer vcount) {
        this.vcount = vcount;
    }

    public Boolean getIs_rec() {
        return this.is_rec;
    }

    public void setIs_rec(Boolean is_rec) {
        this.is_rec = is_rec;
    }

    public String getAlbum_play_platform() {
        return this.album_play_platform;
    }

    public void setAlbum_play_platform(String album_play_platform) {
        this.album_play_platform = album_play_platform;
    }

    public String getVideo_play_platform() {
        return this.video_play_platform;
    }

    public void setVideo_play_platform(String video_play_platform) {
        this.video_play_platform = video_play_platform;
    }

    public String getPic_16_9() {
        return pic_16_9;
    }

    public void setPic_16_9(String pic_16_9) {
        this.pic_16_9 = pic_16_9;
    }

    public String getPay_detail() {
        return pay_detail;
    }

    public void setPay_detail(String pay_detail) {
        this.pay_detail = pay_detail;
    }
}
