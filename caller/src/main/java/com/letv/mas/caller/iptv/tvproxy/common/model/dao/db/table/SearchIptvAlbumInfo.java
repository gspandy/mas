package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.util.Date;

public class SearchIptvAlbumInfo {

    private Long id;// 专辑ID
    private Long itv_album_id;// TV版专辑id
    private String name_cn;// 中文名称
    private String sub_title;// 副标题
    private String name_en;// 英文名称
    private String release_date;// 上映时间
    private Integer language;// 内容语言
    private String language_name;// 内容语言名称
    private String area;// 地区
    private String area_name;// 地区名称
    private String description;// 描述
    private String short_desc;// 简要描述
    private String tag;// 标签
    private Integer episode;// 总集数
    private Float score;// 评分
    private Integer recomm_level;// 推荐指数
    private Integer copyright_type;// 版权类型
    private String copyright_type_name;// 版权名称
    private String copyright_start;// 版权开始时间
    private String copyright_end;// 版权结束时间
    private String copyright_company;// 版权公司
    private Integer is_end;// 是否完结
    private Integer follownum;// 跟播到第几集
    private Date create_time;// 创建时间
    private Date update_time;// 修改时间
    private Integer category;// 电影、电视剧、综艺、动漫
    private String category_name;// 分类名称
    private String sub_category;// 动作 言情
    private String sub_category_name;// 子分类名称
    private String directory;// 导演
    private String actor;// 演员
    private String starring;// 主演
    private String screen_writer;// 编剧
    private String music_authors;// 音乐作词人
    private String music_compose;// 音乐作曲者
    private String style;// 对应letvMakeStyle、popStyle、letvProduceStyle、soprtsType和filmBaseType
    private String style_name;// 相应的名称
    private String record_company;// 唱片公司
    private String issue_company;// 发行公司
    private String pic_collections;// 图片集
    private Integer deleted;// 删除标记
    private String play_tv_name;// 播出电视台名称 可能有多个电视台
    private String play_tv;// 播出电视台 可能有多个电视台
    private Integer duration;// 时长
    private String album_type;// 正片、片花、花絮、资讯
    private String album_type_name;// type名称
    private String letv_release_date;// 乐视上映时间
    private String name_pinyin_abb;// 名字中每个字的拼音的首字母
    private Integer status;// 发布状态： 0未发布 1已发布2发布失败
    private String play_streams;// 专辑所有的码流
    private Integer isyuanxian;// 是否网络院线:1是 0否
    private Integer cntv;// 1:cntv上线 0 cntv下线
    private Integer wasu = 1;// 1上线 0 下线
    private Integer cibn = 1;// 1上线 0 下线
    private String starring_play;// 主演饰演角色
    private String first_play_time;// 赛季
    private Integer is_homemade;// 是否是自制剧
    private String fit_age;// 适应年龄
    private Integer pushflag;// 0TV版外跳 1TV版
    private Integer album_attr;// 1正片

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShort_desc() {
        return this.short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getEpisode() {
        return this.episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getRecomm_level() {
        return this.recomm_level;
    }

    public void setRecomm_level(Integer recomm_level) {
        this.recomm_level = recomm_level;
    }

    public Integer getCopyright_type() {
        return this.copyright_type;
    }

    public void setCopyright_type(Integer copyright_type) {
        this.copyright_type = copyright_type;
    }

    public String getCopyright_type_name() {
        return this.copyright_type_name;
    }

    public void setCopyright_type_name(String copyright_type_name) {
        this.copyright_type_name = copyright_type_name;
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

    public Integer getIs_homemade() {
        return this.is_homemade;
    }

    public void setIs_homemade(Integer is_homemade) {
        this.is_homemade = is_homemade;
    }

    public String getCopyright_company() {
        return this.copyright_company;
    }

    public void setCopyright_company(String copyright_company) {
        this.copyright_company = copyright_company;
    }

    public Integer getIs_end() {
        return this.is_end;
    }

    public void setIs_end(Integer is_end) {
        this.is_end = is_end;
    }

    public Integer getFollownum() {
        return this.follownum;
    }

    public void setFollownum(Integer follownum) {
        this.follownum = follownum;
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

    public String getScreen_writer() {
        return this.screen_writer;
    }

    public void setScreen_writer(String screen_writer) {
        this.screen_writer = screen_writer;
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

    public String getPic_collections() {
        return this.pic_collections;
    }

    public void setPic_collections(String pic_collections) {
        this.pic_collections = pic_collections;
    }

    public Integer getDeleted() {
        return this.deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getPlay_tv_name() {
        return this.play_tv_name;
    }

    public void setPlay_tv_name(String play_tv_name) {
        this.play_tv_name = play_tv_name;
    }

    public String getPlay_tv() {
        return this.play_tv;
    }

    public void setPlay_tv(String play_tv) {
        this.play_tv = play_tv;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAlbum_type() {
        return this.album_type;
    }

    public void setAlbum_type(String album_type) {
        this.album_type = album_type;
    }

    public String getAlbum_type_name() {
        return this.album_type_name;
    }

    public void setAlbum_type_name(String album_type_name) {
        this.album_type_name = album_type_name;
    }

    public String getLetv_release_date() {
        return this.letv_release_date;
    }

    public void setLetv_release_date(String letv_release_date) {
        this.letv_release_date = letv_release_date;
    }

    public String getName_pinyin_abb() {
        return this.name_pinyin_abb;
    }

    public void setName_pinyin_abb(String name_pinyin_abb) {
        this.name_pinyin_abb = name_pinyin_abb;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPlay_streams() {
        return this.play_streams;
    }

    public void setPlay_streams(String play_streams) {
        this.play_streams = play_streams;
    }

    public Integer getIsyuanxian() {
        return this.isyuanxian;
    }

    public void setIsyuanxian(Integer isyuanxian) {
        this.isyuanxian = isyuanxian;
    }

    public Integer getCntv() {
        return this.cntv;
    }

    public void setCntv(Integer cntv) {
        this.cntv = cntv;
    }

    public String getStarring_play() {
        return this.starring_play;
    }

    public void setStarring_play(String starring_play) {
        this.starring_play = starring_play;
    }

    public String getFirst_play_time() {
        return this.first_play_time;
    }

    public void setFirst_play_time(String first_play_time) {
        this.first_play_time = first_play_time;
    }

    public String getFit_age() {
        return this.fit_age;
    }

    public void setFit_age(String fit_age) {
        this.fit_age = fit_age;
    }

    public Integer getPushflag() {
        return this.pushflag;
    }

    public void setPushflag(Integer pushflag) {
        this.pushflag = pushflag;
    }

    public Integer getAlbum_attr() {
        return this.album_attr;
    }

    public void setAlbum_attr(Integer album_attr) {
        this.album_attr = album_attr;
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
