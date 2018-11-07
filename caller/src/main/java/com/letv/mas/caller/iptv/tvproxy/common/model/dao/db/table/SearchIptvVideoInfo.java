package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class SearchIptvVideoInfo {

    private Long id;// 视频ID
    private Long video_info_id;// 原video_info_id
    private Long pid;// 专辑ID
    private Long itv_album_id;// iptv的album_id
    private String name_cn;// 中文名称
    private Integer porder;// 在专辑顺序
    private String sub_title;// 副标题
    private String name_en;// 英文名称
    private String directory;// 导演
    private String actor;// 演员
    private String starring;// 纪录片中的演员
    private String release_date;// 发行时间
    private Integer language;// 语言
    private String language_name;//
    private Float score;// 评分
    private String maker;// 音乐频道制片人监制
    private String short_desc;//
    private String description;// 描述
    private String tag;// 标签
    private String area;// 地区
    private String area_name;// 地区名称
    private Integer category;// 电视剧 电影
    private String category_name;// 分类名称
    private String sub_category;// 动作 言情 惊悚 都市
    private String sub_category_name;//
    private String copyright_company;// 版权公司
    private String copyright_start;// 版权开始时间
    private String copyright_end;// 版权结束时间
    private String copyright_type;// 版权类型
    private String copyright_type_name;// 版权类型名称
    private String music_authors;// 音乐作词人
    private String music_compose;// 音乐作曲者
    private String style;// 分类
    private String style_name;//
    private String record_company;// 唱片公司
    private String issue_company;// 发行公司
    private String video_type;// 视频类型
    private String video_type_name;// 视频类型
    private Integer deleted;// 删除标记
    private String pic_all;// 图片地址
    private String trans_code_prefix;// 转码视频截图前缀
    private Integer duration;// 时长
    private String name_pinyin_abb;// 名称拼音简写
    private Integer cntv;// 1:cntv上线 0 cntv下线
    private Integer wasu = 1;// 1上线 0 下线
    private Integer cibn = 1;// 1上线 0 下线
    private String play_streams;// 所有的码流
    private Date create_time;// 创建时间
    private Date update_time;// 修改时间
    private Integer pushflag;// 1TV版 0外跳
    private Integer video_attr;// 1正片

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

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getItv_album_id() {
        return this.itv_album_id;
    }

    public void setItv_album_id(Long itv_album_id) {
        this.itv_album_id = itv_album_id;
    }

    public String getName_cn() {
        return this.name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public Integer getPorder() {
        return this.porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public String getSub_title() {
        return this.sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getName_en() {
        return this.name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
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

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
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

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
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

    public String getMusic_authors() {
        return this.music_authors;
    }

    public void setMusic_authors(String music_authors) {
        this.music_authors = music_authors;
    }

    public String getMusic_compose() {
        return this.music_compose;
    }

    public void setMusic_compose(String music_compose) {
        this.music_compose = music_compose;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyle_name() {
        return this.style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public String getRecord_company() {
        return this.record_company;
    }

    public void setRecord_company(String record_company) {
        this.record_company = record_company;
    }

    public String getIssue_company() {
        return this.issue_company;
    }

    public void setIssue_company(String issue_company) {
        this.issue_company = issue_company;
    }

    public String getVideo_type() {
        return this.video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public String getVideo_type_name() {
        return this.video_type_name;
    }

    public void setVideo_type_name(String video_type_name) {
        this.video_type_name = video_type_name;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getPic_all() {
        return this.pic_all;
    }

    public void setPic_all(String pic_all) {
        this.pic_all = pic_all;
    }

    public String getTrans_code_prefix() {
        return this.trans_code_prefix;
    }

    public void setTrans_code_prefix(String trans_code_prefix) {
        this.trans_code_prefix = trans_code_prefix;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getName_pinyin_abb() {
        return this.name_pinyin_abb;
    }

    public void setName_pinyin_abb(String name_pinyin_abb) {
        this.name_pinyin_abb = name_pinyin_abb;
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

    public Integer getPushflag() {
        return this.pushflag;
    }

    public void setPushflag(Integer pushflag) {
        this.pushflag = pushflag;
    }

    public Integer getVideo_attr() {
        return this.video_attr;
    }

    public void setVideo_attr(Integer video_attr) {
        this.video_attr = video_attr;
    }

    public Integer getWasu() {
        return this.wasu;
    }

    public void setWasu(Integer wasu) {
        this.wasu = wasu;
    }

    public Integer getCibn() {
        return this.cibn;
    }

    public void setCibn(Integer cibn) {
        this.cibn = cibn;
    }

}
