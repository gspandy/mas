package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VideoMysqlTable implements Serializable {
    private final Logger log = LoggerFactory.getLogger(VideoMysqlTable.class);

    private static final long serialVersionUID = 8098565956911921433L;

    private Long id;// 视频ID
    private Long video_info_id;// 原video_info_id
    @CopyFieldAnnotation(isCopy = false)
    private Integer video_type;// 视频类型
    private String video_type_name;// 视频类型
    private Long pid;// 专辑ID
    private Long itv_album_id;// iptv的album_id
    private Integer porder;// 在专辑顺序
    private String source_id; // 来源id集合
    private String ad_point;// 广告插入点
    private String mid;// 媒资ID;// 一个视频可能对应着多个媒资id(比如一个视频国语音轨，也有粤语的音轨，就会转出两个媒资id)
    private String name_cn;// 中文名称
    private String name_pinyin_abb;// 名称拼音简写
    private String name_en;// 英文名称
    private String alias;// 别名
    private String sub_title;// 副标题
    private String tv_title;// TV端显示标题
    private String short_desc;//
    private String description;// 描述
    private String tag;// 标签
    private Integer category;// 电视剧 电影
    private String category_name;// 分类名称
    private String sub_category;// 动作 言情 惊悚 都市
    private String sub_category_name;//
    private String area;// 地区
    private String area_name;// 地区名称
    private String episode;// 在动漫频道，可能有2.5集， 也有可能一个视频文件，包含两集
    private Integer btime;// 片头时间
    private Integer etime;// 片尾时间
    private String watching_focus;// 看点
    private String download_platform;// 允许下载平台
    private String play_platform;// 推送平台
    private String pay_platform;// 付费平台
    private Integer duration;// 时长，单位-秒
    private Integer status;// 发布状态： 0;//未发布 1已发布2发布失败
    private Integer deleted;// 删除标记
    private String remark;// 备注
    private Float score;// 1);//
    private String pic_all;// 图片地址
    private Map<String, String> picMap;// 图片地址
    private String pic_original;// 原图
    private String relative_content;// 关联内容
    private String release_date;// 发行时间
    private String schedule_publish_date;// 定时发布时间
    private String first_play_time;// 首播时间
    private Integer issue;// 期数
    private String school;// 学校
    private String starring;// 纪录片中的演员
    private String starring_play;// 纪录片中的人物
    private String directory;// 导演
    private String actor;// 演员
    private String actor_play;// 饰演角色
    private Integer language;// 语言
    private String language_name;//
    private String single_name;// 动漫频道 单集名称
    @CopyFieldAnnotation(isCopy = false)
    private Integer style;// 分类
    private String style_name;//
    private Integer sports_type;// 篮球 足球 综合
    private String sports_type_name;//
    private Integer letv_make_style;// 魅力研习社 可以说的秘密 我为校花狂 影视风向榜 午间道 星月私房话
    private String letv_make_style_name;//
    private Integer pop_style;// 风尚in坐标 天使爱美丽 ElleTV
    private String pop_style_name;//
    private Integer letv_produce_style;// 电影 微电影
    private String letv_produce_style_name;//
    private String cartoon_style;// 动漫分类
    private String cartoon_style_name;// 动漫分类\n名称
    private Integer field_type;// 领域类型
    private String field_type_name;// 领域类型名称
    private Integer pre;// 关联类型，娱乐频道
    private String pre_name;// 关联类型名称
    private String instructor;// 讲师
    private String compere;// 主持人
    private String guest;// 嘉宾
    private String singer;// 歌手
    private String singer_type;// 歌手类型
    private String singer_type_name;//
    private String music_authors;// 音乐作词人
    private String music_style;// 音乐风格
    private String music_style_name;//
    private Integer travel_theme;// 旅游主题
    private String travel_theme_name;// 旅游主题名称
    private Integer travel_type;// 旅游类型
    private String travel_type_name;// 旅游类型名称
    private String maker;// 音乐频道制片人监制
    private String record_company;// 唱片公司
    private String copyright_company;// 版权公司
    private String copyright_start;// 版权开始时间
    private String copyright_end;// 版权结束时间
    private String copyright_type;// 版权类型
    private String copyright_type_name;// 版权类型名称
    private String disable_type;// 屏蔽类型
    private String disable_type_name;// 屏蔽类型名称
    private String issue_company;// 发行公司
    private String music_compose;// 音乐作曲者
    private String team;// 球队(体育)
    private String trans_code_prefix;// 转码视频截图前缀
    private String recreation_type;// 娱乐分类(娱乐)
    private String recreation_type_name;//
    private Integer cntv;// 1:cntv上线 0 cntv下线
    private Integer cibn;// 国广
    private Integer wasu;// 华数
    private String play_streams;// 所有的码流
    private String carfilm_type;// 汽车频道影片分类
    private String carfilm_type_name;//
    private Date create_time;// 创建时间
    private Date update_time;// 修改时间
    private Long user_id;// 用户ID
    private String style_all;// 原style保留字段
    private String style_all_name;// 风格
    private String video_type_all;// 原video_type保留字段
    private String video_type_all_name;// 原video_type保留字段
    private String actor_all;//
    private String directory_all;// 原directory
    private String starring_all;// 原starring
    private String rcompany_all;//
    private String sub_category_all;//
    private String sub_category_name_all;//
    private Integer category_all;// 原vrs分类
    private String category_name_all;// 原分类名称

    private String mid_streams;// 媒资id和码流的对应关系

    private Integer video_attr;// 视频属性是否正片（1：正片）
    private Integer pushflag;
    private Integer logonum;// 0有默认水印1无默认水印
    private String drmFlagId;
    private String videoPic;
    private Integer isPlayLock;// 家长锁功能0否，1是

    private Long pushTVTime;// 影片第一次推送到TV的时间
    private String auditHis;// cibn审核历史
    private String auditWasu;// wasu审核历史

    private Integer isPushChild;// 是否推送到儿童1推送0不推送
    private Integer contentRatingId;// 内容分级id
    private String contentRatingValue;// 内容分级value
    private String contentRatingDesc;// 内容分级描述（界面显示用）

    /**
     * TV首次上映时间，格式为"yyyy-MM-dd HH:mm:ss:，注意与pushTVTime的使用关系，后期可废掉pushTVTime
     */
    private String tvFirstOnTime;
    private Integer isDanmaku;// 是否有弹幕 1 有，0无

    /**
     * Cooperation platform，合作平台id列表，多个id使用英文逗号","拼接
     */
    private String coopPlatform;

    public Map<String, String> getPicMap() {
        return picMap;
    }

    public void setPicMap(Map<String, String> picMap) {
        this.picMap = picMap;
    }

    public Integer getIsDanmaku() {
        return this.isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getAuditWasu() {
        return this.auditWasu;
    }

    public void setAuditWasu(String auditWasu) {
        this.auditWasu = auditWasu;
    }

    public Integer getContentRatingId() {
        return contentRatingId;
    }

    public void setContentRatingId(Integer contentRatingId) {
        this.contentRatingId = contentRatingId;
    }

    public String getContentRatingValue() {
        return contentRatingValue;
    }

    public void setContentRatingValue(String contentRatingValue) {
        this.contentRatingValue = contentRatingValue;
    }

    public String getContentRatingDesc() {
        return contentRatingDesc;
    }

    public void setContentRatingDesc(String contentRatingDesc) {
        this.contentRatingDesc = contentRatingDesc;
    }

    public String getAuditHis() {
        return this.auditHis;
    }

    public void setAuditHis(String auditHis) {
        this.auditHis = auditHis;
    }

    public Integer getCibn() {
        return this.cibn;
    }

    public void setCibn(Integer cibn) {
        this.cibn = cibn;
    }

    public Integer getWasu() {
        return this.wasu;
    }

    public void setWasu(Integer wasu) {
        this.wasu = wasu;
    }

    public String getPic(Integer width, Integer hight) {
        if (this.pic_all == null && this.trans_code_prefix == null) {
            return "";
        }
        String img = null;
        try {
            Map<String, String> map = JsonUtil.parse(this.pic_all, new TypeReference<Map<String, String>>() {
            });
            if (map != null) {
                img = map.get(width + "*" + hight);
            }
        } catch (Exception e) {

        }
        if (this.trans_code_prefix != null && this.trans_code_prefix.trim().length() > 0 && img == null) {
            img = this.trans_code_prefix + "/thumb/2_" + width + "_" + hight + ".jpg";
        }
        return img == null ? "" : img;
    }

    public String getPicNew(Integer width, Integer hight) {
        if (this.picMap == null) {
            this.picMap = JsonUtil.parse(this.pic_all, new TypeReference<Map<String, String>>() {
            });
        }
        if (this.picMap == null && this.trans_code_prefix == null) {
            return "";
        }
        String img = null;
        img = this.picMap.get(width + "*" + hight);
        if (this.trans_code_prefix != null && this.trans_code_prefix.trim().length() > 0 && img == null) {
            img = this.trans_code_prefix + "/thumb/2_" + width + "_" + hight + ".jpg";
        }
        return img == null ? "" : img;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideo_info_id() {
        return this.video_info_id;
    }

    public void setVideo_info_id(Long video_info_id) {
        this.video_info_id = video_info_id;
    }

    public Integer getVideo_type() {
        return this.video_type;
    }

    public void setVideo_type(Integer video_type) {
        this.video_type = video_type;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public Long getItv_album_id() {
        return this.itv_album_id;
    }

    public void setItv_album_id(Long itv_album_id) {
        this.itv_album_id = itv_album_id;
    }

    public Integer getPorder() {
        return this.porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public String getAd_point() {
        return this.ad_point;
    }

    public void setAd_point(String ad_point) {
        this.ad_point = ad_point;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getName_cn() {
        return this.name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getName_pinyin_abb() {
        return this.name_pinyin_abb;
    }

    public void setName_pinyin_abb(String name_pinyin_abb) {
        this.name_pinyin_abb = name_pinyin_abb;
    }

    public String getName_en() {
        return this.name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSub_title() {
        return this.sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getTv_title() {
        return this.tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public String getShort_desc() {
        return this.short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSub_category() {
        return this.sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getSub_category_name() {
        return this.sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_name() {
        return this.area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getBtime() {
        return this.btime;
    }

    public void setBtime(Integer btime) {
        this.btime = btime;
    }

    public Integer getEtime() {
        return this.etime;
    }

    public void setEtime(Integer etime) {
        this.etime = etime;
    }

    public String getWatching_focus() {
        return this.watching_focus;
    }

    public void setWatching_focus(String watching_focus) {
        this.watching_focus = watching_focus;
    }

    public String getDownload_platform() {
        return this.download_platform;
    }

    public void setDownload_platform(String download_platform) {
        this.download_platform = download_platform;
    }

    public String getPlay_platform() {
        return this.play_platform;
    }

    public void setPlay_platform(String play_platform) {
        this.play_platform = play_platform;
    }

    public String getPay_platform() {
        return this.pay_platform;
    }

    public void setPay_platform(String pay_platform) {
        this.pay_platform = pay_platform;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getPic_all() {
        return this.pic_all;
    }

    public void setPic_all(String pic_all) {
        this.pic_all = pic_all;
    }

    public String getPic_original() {
        return this.pic_original;
    }

    public void setPic_original(String pic_original) {
        this.pic_original = pic_original;
    }

    public String getRelative_content() {
        return this.relative_content;
    }

    public void setRelative_content(String relative_content) {
        this.relative_content = relative_content;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getSchedule_publish_date() {
        return this.schedule_publish_date;
    }

    public void setSchedule_publish_date(String schedule_publish_date) {
        this.schedule_publish_date = schedule_publish_date;
    }

    public String getFirst_play_time() {
        return this.first_play_time;
    }

    public void setFirst_play_time(String first_play_time) {
        this.first_play_time = first_play_time;
    }

    public Integer getIssue() {
        return this.issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getStarring_play() {
        return this.starring_play;
    }

    public void setStarring_play(String starring_play) {
        this.starring_play = starring_play;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActor_play() {
        return this.actor_play;
    }

    public void setActor_play(String actor_play) {
        this.actor_play = actor_play;
    }

    public Integer getLanguage() {
        return this.language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getLanguage_name() {
        return this.language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public String getSingle_name() {
        return this.single_name;
    }

    public void setSingle_name(String single_name) {
        this.single_name = single_name;
    }

    public Integer getStyle() {
        return this.style;
    }

    public void setStyle(Integer style) {
        this.style = style;
    }

    public String getStyle_name() {
        return this.style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public Integer getSports_type() {
        return this.sports_type;
    }

    public void setSports_type(Integer sports_type) {
        this.sports_type = sports_type;
    }

    public String getSports_type_name() {
        return this.sports_type_name;
    }

    public void setSports_type_name(String sports_type_name) {
        this.sports_type_name = sports_type_name;
    }

    public Integer getLetv_make_style() {
        return this.letv_make_style;
    }

    public void setLetv_make_style(Integer letv_make_style) {
        this.letv_make_style = letv_make_style;
    }

    public String getLetv_make_style_name() {
        return this.letv_make_style_name;
    }

    public void setLetv_make_style_name(String letv_make_style_name) {
        this.letv_make_style_name = letv_make_style_name;
    }

    public Integer getPop_style() {
        return this.pop_style;
    }

    public void setPop_style(Integer pop_style) {
        this.pop_style = pop_style;
    }

    public String getPop_style_name() {
        return this.pop_style_name;
    }

    public void setPop_style_name(String pop_style_name) {
        this.pop_style_name = pop_style_name;
    }

    public Integer getLetv_produce_style() {
        return this.letv_produce_style;
    }

    public void setLetv_produce_style(Integer letv_produce_style) {
        this.letv_produce_style = letv_produce_style;
    }

    public String getLetv_produce_style_name() {
        return this.letv_produce_style_name;
    }

    public void setLetv_produce_style_name(String letv_produce_style_name) {
        this.letv_produce_style_name = letv_produce_style_name;
    }

    public String getCartoon_style() {
        return this.cartoon_style;
    }

    public void setCartoon_style(String cartoon_style) {
        this.cartoon_style = cartoon_style;
    }

    public String getCartoon_style_name() {
        return this.cartoon_style_name;
    }

    public void setCartoon_style_name(String cartoon_style_name) {
        this.cartoon_style_name = cartoon_style_name;
    }

    public Integer getField_type() {
        return this.field_type;
    }

    public void setField_type(Integer field_type) {
        this.field_type = field_type;
    }

    public String getField_type_name() {
        return this.field_type_name;
    }

    public void setField_type_name(String field_type_name) {
        this.field_type_name = field_type_name;
    }

    public Integer getPre() {
        return this.pre;
    }

    public void setPre(Integer pre) {
        this.pre = pre;
    }

    public String getPre_name() {
        return this.pre_name;
    }

    public void setPre_name(String pre_name) {
        this.pre_name = pre_name;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getCompere() {
        return this.compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getGuest() {
        return this.guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getSinger_type() {
        return this.singer_type;
    }

    public void setSinger_type(String singer_type) {
        this.singer_type = singer_type;
    }

    public String getSinger_type_name() {
        return this.singer_type_name;
    }

    public void setSinger_type_name(String singer_type_name) {
        this.singer_type_name = singer_type_name;
    }

    public String getMusic_authors() {
        return this.music_authors;
    }

    public void setMusic_authors(String music_authors) {
        this.music_authors = music_authors;
    }

    public String getMusic_style() {
        return this.music_style;
    }

    public void setMusic_style(String music_style) {
        this.music_style = music_style;
    }

    public String getMusic_style_name() {
        return this.music_style_name;
    }

    public void setMusic_style_name(String music_style_name) {
        this.music_style_name = music_style_name;
    }

    public Integer getTravel_theme() {
        return this.travel_theme;
    }

    public void setTravel_theme(Integer travel_theme) {
        this.travel_theme = travel_theme;
    }

    public String getTravel_theme_name() {
        return this.travel_theme_name;
    }

    public void setTravel_theme_name(String travel_theme_name) {
        this.travel_theme_name = travel_theme_name;
    }

    public Integer getTravel_type() {
        return this.travel_type;
    }

    public void setTravel_type(Integer travel_type) {
        this.travel_type = travel_type;
    }

    public String getTravel_type_name() {
        return this.travel_type_name;
    }

    public void setTravel_type_name(String travel_type_name) {
        this.travel_type_name = travel_type_name;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getRecord_company() {
        return this.record_company;
    }

    public void setRecord_company(String record_company) {
        this.record_company = record_company;
    }

    public String getCopyright_company() {
        return this.copyright_company;
    }

    public void setCopyright_company(String copyright_company) {
        this.copyright_company = copyright_company;
    }

    public String getCopyright_start() {
        return this.copyright_start;
    }

    public void setCopyright_start(String copyright_start) {
        this.copyright_start = copyright_start;
    }

    public String getCopyright_end() {
        return this.copyright_end;
    }

    public void setCopyright_end(String copyright_end) {
        this.copyright_end = copyright_end;
    }

    public String getCopyright_type() {
        return this.copyright_type;
    }

    public void setCopyright_type(String copyright_type) {
        this.copyright_type = copyright_type;
    }

    public String getCopyright_type_name() {
        return this.copyright_type_name;
    }

    public void setCopyright_type_name(String copyright_type_name) {
        this.copyright_type_name = copyright_type_name;
    }

    public String getDisable_type() {
        return this.disable_type;
    }

    public void setDisable_type(String disable_type) {
        this.disable_type = disable_type;
    }

    public String getDisable_type_name() {
        return this.disable_type_name;
    }

    public void setDisable_type_name(String disable_type_name) {
        this.disable_type_name = disable_type_name;
    }

    public String getIssue_company() {
        return this.issue_company;
    }

    public void setIssue_company(String issue_company) {
        this.issue_company = issue_company;
    }

    public String getMusic_compose() {
        return this.music_compose;
    }

    public void setMusic_compose(String music_compose) {
        this.music_compose = music_compose;
    }

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTrans_code_prefix() {
        return this.trans_code_prefix;
    }

    public void setTrans_code_prefix(String trans_code_prefix) {
        this.trans_code_prefix = trans_code_prefix;
    }

    public String getRecreation_type() {
        return this.recreation_type;
    }

    public void setRecreation_type(String recreation_type) {
        this.recreation_type = recreation_type;
    }

    public String getRecreation_type_name() {
        return this.recreation_type_name;
    }

    public void setRecreation_type_name(String recreation_type_name) {
        this.recreation_type_name = recreation_type_name;
    }

    public Integer getCntv() {
        return this.cntv;
    }

    public void setCntv(Integer cntv) {
        this.cntv = cntv;
    }

    public String getPlay_streams() {
        return this.play_streams;
    }

    public void setPlay_streams(String play_streams) {
        this.play_streams = play_streams;
    }

    public String getCarfilm_type() {
        return this.carfilm_type;
    }

    public void setCarfilm_type(String carfilm_type) {
        this.carfilm_type = carfilm_type;
    }

    public String getCarfilm_type_name() {
        return this.carfilm_type_name;
    }

    public void setCarfilm_type_name(String carfilm_type_name) {
        this.carfilm_type_name = carfilm_type_name;
    }

    public Date getCreate_time() {
        return this.create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return this.update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getStyle_all() {
        return this.style_all;
    }

    public void setStyle_all(String style_all) {
        this.style_all = style_all;
    }

    public String getStyle_all_name() {
        return this.style_all_name;
    }

    public void setStyle_all_name(String style_all_name) {
        this.style_all_name = style_all_name;
    }

    public String getVideo_type_all() {
        return this.video_type_all;
    }

    public void setVideo_type_all(String video_type_all) {
        this.video_type_all = video_type_all;
    }

    public String getVideo_type_all_name() {
        return this.video_type_all_name;
    }

    public void setVideo_type_all_name(String video_type_all_name) {
        this.video_type_all_name = video_type_all_name;
    }

    public String getActor_all() {
        return this.actor_all;
    }

    public void setActor_all(String actor_all) {
        this.actor_all = actor_all;
    }

    public String getDirectory_all() {
        return this.directory_all;
    }

    public void setDirectory_all(String directory_all) {
        this.directory_all = directory_all;
    }

    public String getStarring_all() {
        return this.starring_all;
    }

    public void setStarring_all(String starring_all) {
        this.starring_all = starring_all;
    }

    public String getRcompany_all() {
        return this.rcompany_all;
    }

    public void setRcompany_all(String rcompany_all) {
        this.rcompany_all = rcompany_all;
    }

    public String getSub_category_all() {
        return this.sub_category_all;
    }

    public void setSub_category_all(String sub_category_all) {
        this.sub_category_all = sub_category_all;
    }

    public String getSub_category_name_all() {
        return this.sub_category_name_all;
    }

    public void setSub_category_name_all(String sub_category_name_all) {
        this.sub_category_name_all = sub_category_name_all;
    }

    public String getVideo_type_name() {
        return this.video_type_name;
    }

    public void setVideo_type_name(String video_type_name) {
        this.video_type_name = video_type_name;
    }

    public Integer getCategory_all() {
        return this.category_all;
    }

    public void setCategory_all(Integer category_all) {
        this.category_all = category_all;
    }

    public String getCategory_name_all() {
        return this.category_name_all;
    }

    public void setCategory_name_all(String category_name_all) {
        this.category_name_all = category_name_all;
    }

    public String getMid_streams() {
        return this.mid_streams;
    }

    public void setMid_streams(String mid_streams) {
        this.mid_streams = mid_streams;
    }

    public Integer getVideo_attr() {
        return this.video_attr;
    }

    public void setVideo_attr(Integer video_attr) {
        this.video_attr = video_attr;
    }

    public Integer getPushflag() {
        return this.pushflag;
    }

    public void setPushflag(Integer pushflag) {
        this.pushflag = pushflag;
    }

    public Integer getLogonum() {
        return this.logonum;
    }

    public void setLogonum(Integer logonum) {
        this.logonum = logonum;
    }

    public Long getMidByStream(String stream) {
        if (LetvStreamCommonConstants.CODE_NAME_1080p6m.equalsIgnoreCase(stream)
                && this.play_streams.indexOf(LetvStreamCommonConstants.CODE_NAME_1080p3m) > -1
                && !LetvStreamCommonConstants.contains(this.getPid())) {// 如果是1080p6m,并且有3m的则取3m
            stream = LetvStreamCommonConstants.CODE_NAME_1080p3m;
        }
        Long mid = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (this.mid_streams != null) {
                Map<String, String> map = mapper.readValue(this.mid_streams, new TypeReference<Map<String, String>>() {
                });
                if (map != null) {
                    Set<Entry<String, String>> entrySet = map.entrySet();
                    for (Entry<String, String> e : entrySet) {
                        String id = e.getKey();
                        String value = e.getValue();
                        if (value == null) {
                            break;
                        }
                        String[] streams = e.getValue().split(",");
                        for (String s : streams) {
                            if (s.equalsIgnoreCase(stream)) {
                                mid = new Long(id);
                                break;
                            }
                        }
                        if (mid != null) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("getMidByStream_ error", e.getMessage(), e);
        }
        return mid;
    }

    public String getStreamByMid(String mid) {
        String stream = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (this.mid_streams != null) {
                Map<String, String> map = mapper.readValue(this.mid_streams, new TypeReference<Map<String, String>>() {
                });
                if (map != null) {
                    stream = map.get(mid);
                }
            }
        } catch (Exception e) {
            log.error("getStreamByMid_ error", e.getMessage(), e);
        }
        return stream;
    }

    public void initSplitProperty() {
        // style
        StringBuffer style_all = new StringBuffer();
        StringBuffer style_all_name = new StringBuffer();
        if (this.style != null && this.style.intValue() > 0) {
            style_all.append(this.style).append(",");
            style_all_name.append(this.style_name).append(",");
        }
        if (this.letv_make_style != null && this.letv_make_style.intValue() > 0) {
            style_all.append(this.letv_make_style).append(",");
            style_all_name.append(this.letv_make_style_name).append(",");
        }
        if (this.pop_style != null && this.pop_style.intValue() > 0) {
            style_all.append(this.pop_style).append(",");
            style_all_name.append(this.pop_style_name).append(",");
        }
        if (this.letv_produce_style != null && this.letv_produce_style.intValue() > 0) {
            style_all.append(this.letv_produce_style).append(",");
            style_all_name.append(this.letv_produce_style_name).append(",");
        }
        if (this.sports_type != null && this.sports_type.intValue() > 0) {
            style_all.append(this.sports_type).append(",");
            style_all_name.append(this.sports_type_name).append(",");
        }
        if (this.travel_type != null && this.travel_type.intValue() > 0) {
            style_all.append(this.travel_type).append(",");
            style_all_name.append(this.travel_type_name).append(",");
        }
        if (this.cartoon_style != null && this.cartoon_style.trim().length() > 0) {
            style_all.append(this.cartoon_style).append(",");
            style_all_name.append(this.cartoon_style_name).append(",");
        }
        StringBuffer videoTypeAll = new StringBuffer();
        StringBuffer videoTypeNameAll = new StringBuffer();
        if (this.video_type != null && this.video_type.intValue() > 0) {
            videoTypeAll.append(this.video_type).append(",");
            videoTypeNameAll.append(this.video_type_name).append(",");
        }
        if (this.recreation_type != null && this.recreation_type.trim().length() > 0) {
            videoTypeAll.append(this.recreation_type).append(",");
            videoTypeNameAll.append(this.recreation_type_name).append(",");
        }
        StringBuffer actorAll = new StringBuffer();
        if (this.actor != null && this.actor.trim().length() > 0) {
            actorAll.append(this.actor).append(",");
        }
        if (this.guest != null && this.guest.trim().length() > 0) {
            actorAll.append(this.guest).append(",");
        }
        StringBuffer directoryAll = new StringBuffer();
        if (this.directory != null && this.directory.trim().length() > 0) {
            directoryAll.append(this.directory).append(",");
        }
        if (this.instructor != null && this.instructor.trim().length() > 0) {
            directoryAll.append(this.instructor).append(",");
        }
        if (this.compere != null && this.compere.trim().length() > 0) {
            directoryAll.append(this.compere).append(",");
        }

        Set<String> starringAllSet = new LinkedHashSet<String>(); // 对starring_allu要求保持原有顺序，且不重复
        if (this.starring != null && this.starring.trim().length() > 0) {
            String[] starringArray = this.starring.split(",");
            for (String starringElement : starringArray) {
                if (StringUtils.trimToNull(starringElement) != null) {
                    starringAllSet.add(StringUtils.trimToNull(starringElement));
                }
            }
        }
        if (this.compere != null && this.compere.trim().length() > 0) {
            String[] compereArray = this.compere.split(",");
            for (String compereElement : compereArray) {
                if (StringUtils.trimToNull(compereElement) != null) {
                    starringAllSet.add(StringUtils.trimToNull(compereElement));
                }
            }
        }
        if (this.singer != null && this.singer.trim().length() > 0) {
            String[] singerArray = this.singer.split(",");
            for (String singerElement : singerArray) {
                if (StringUtils.trimToNull(singerElement) != null) {
                    starringAllSet.add(StringUtils.trimToNull(singerElement));
                }
            }
        }

        StringBuffer rcompanyAll = new StringBuffer();
        if (this.school != null && this.school.trim().length() > 0) {
            rcompanyAll.append(this.starring).append(",");
        }
        if (this.record_company != null && this.record_company.trim().length() > 0) {
            rcompanyAll.append(this.record_company).append(",");
        }
        StringBuffer subcategoryAll = new StringBuffer();
        StringBuffer subcategoryNameAll = new StringBuffer();
        if (this.sub_category != null && this.sub_category.trim().length() > 0) {
            subcategoryAll.append(this.sub_category).append(",");
            subcategoryNameAll.append(this.sub_category_name).append(",");
        }
        if (this.music_style != null && this.music_style.trim().length() > 0) {
            subcategoryAll.append(this.music_style).append(",");
            subcategoryNameAll.append(this.music_style_name).append(",");
        }
        if (this.carfilm_type != null && this.carfilm_type.trim().length() > 0) {
            subcategoryAll.append(this.carfilm_type).append(",");
            subcategoryNameAll.append(this.carfilm_type_name).append(",");
        }
        if (this.field_type != null && this.field_type.intValue() > 0) {
            subcategoryAll.append(this.field_type).append(",");
            subcategoryNameAll.append(this.field_type_name).append(",");
        }
        if (this.travel_theme != null && this.travel_theme.intValue() > 0) {
            subcategoryAll.append(this.travel_theme).append(",");
            subcategoryNameAll.append(this.travel_theme_name).append(",");
        }

        if (videoTypeAll.length() > 0) {
            videoTypeAll = videoTypeAll.deleteCharAt(videoTypeAll.length() - 1);
        }
        if (videoTypeNameAll.length() > 0) {
            videoTypeNameAll = videoTypeNameAll.deleteCharAt(videoTypeNameAll.length() - 1);
        }
        if (style_all.length() > 0) {
            style_all = style_all.deleteCharAt(style_all.length() - 1);
        }
        if (style_all_name.length() > 0) {
            style_all_name = style_all_name.deleteCharAt(style_all_name.length() - 1);
        }
        if (actorAll.length() > 0) {
            actorAll = actorAll.deleteCharAt(actorAll.length() - 1);
        }
        if (directoryAll.length() > 0) {
            directoryAll = directoryAll.deleteCharAt(directoryAll.length() - 1);
        }
        if (subcategoryAll.length() > 0) {
            subcategoryAll = subcategoryAll.deleteCharAt(subcategoryAll.length() - 1);
        }
        if (subcategoryNameAll.length() > 0) {
            subcategoryNameAll = subcategoryNameAll.deleteCharAt(subcategoryNameAll.length() - 1);
        }
        if (rcompanyAll.length() > 0) {
            rcompanyAll = rcompanyAll.deleteCharAt(rcompanyAll.length() - 1);
        }
        this.style_all = style_all.toString();
        this.style_all_name = style_all_name.toString();
        this.video_type_all = videoTypeAll.toString();
        this.video_type_all_name = videoTypeNameAll.toString();
        this.actor_all = actorAll.toString();

        this.starring_all = StringUtils.join(starringAllSet, ",");
        if (this.starring_all != null && this.starring_all.length() > 255) {
            // 2016-02-29修改，数据库设计该字段为varchar(255)，这里做过长截取
            this.starring_all = this.starring_all.substring(0, 255);
            if (this.starring_all.lastIndexOf(",") > -1) {
                this.starring_all = this.starring_all.substring(0, this.starring_all.lastIndexOf(","));
            }
        }
        if (this.tag != null && this.tag.length() > 255) {
            // 2016-07-05修改，数据库设计该字段为varchar(255)，这里做过长截取
            this.tag = this.tag.substring(0, 255);
            if (this.tag.lastIndexOf(",") > -1) {
                this.tag = this.tag.substring(0, this.tag.lastIndexOf(","));
            }
        }

        // 截断过长的副标题
        if (this.sub_title != null && this.sub_title.length() > 255) {
            this.sub_title = this.sub_title.substring(0, 252) + "...";
        }

        this.directory_all = directoryAll.toString();
        this.rcompany_all = rcompanyAll.toString();
        this.sub_category_all = subcategoryAll.toString();
        this.sub_category_name_all = subcategoryNameAll.toString();
    }

    public Boolean isTVCopyright() {
        // if ((!this.isTV() && !this.isTVOut()) || (this.deleted != null &&
        // this.deleted == 1)) {
        // return false;
        // }
        // return true;
        return isTV() || isTVOut();
    }

    public Boolean isTV() {
        return play_platform != null && play_platform.contains(CommonConstants.TV_PLAY_PLAT_FROM);
    }

    public Boolean isTVOut() {
        return play_platform != null && play_platform.contains(CommonConstants.TV_OUT_PLAY_PLAT_FROM);
    }

    public Boolean isPositive() {
        // 电影、电视剧有正片的概念
        return VideoCommonUtil.isPositive(1, this.category, null, this.video_type);
    }

    public String getDrmFlagId() {
        return this.drmFlagId;
    }

    public void setDrmFlagId(String drmFlagId) {
        this.drmFlagId = drmFlagId;
    }

    public String getVideoPic() {
        return this.videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public Long getPushTVTime() {
        return this.pushTVTime;
    }

    public void setPushTVTime(Long pushTVTime) {
        this.pushTVTime = pushTVTime;
    }

    public Integer getIsPlayLock() {
        return this.isPlayLock;
    }

    public void setIsPlayLock(Integer isPlayLock) {
        this.isPlayLock = isPlayLock;
    }

    public String getTvFirstOnTime() {
        return this.tvFirstOnTime;
    }

    public void setTvFirstOnTime(String tvFirstOnTime) {
        this.tvFirstOnTime = tvFirstOnTime;
    }

    public Integer getIsPushChild() {
        return isPushChild;
    }

    public void setIsPushChild(Integer isPushChild) {
        this.isPushChild = isPushChild;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }
}
