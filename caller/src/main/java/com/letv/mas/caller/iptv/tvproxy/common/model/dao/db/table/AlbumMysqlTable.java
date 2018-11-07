package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CopyFieldAnnotation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.apache.commons.lang.StringUtils;

public class AlbumMysqlTable implements Serializable {

    private static final long serialVersionUID = -3218303625412475608L;

    private Long id;// 专辑ID
    private Long itv_album_id;// TV版专辑id
    private Integer album_base_type;// 1.目录（越狱）2.专辑（越狱第一季）
    @CopyFieldAnnotation(isCopy = false)
    private Integer album_type;// 正片、片花、花絮、资讯
    private String album_type_name;// type名称
    private String name_cn;// 中文名称
    private String name_pinyin_abb;// 名字中每个字的拼音的首字母
    private String name_en;// 英文名称
    private String sub_title;// 副标题
    private String alias;// 别名
    private String short_desc;// 简要描述
    private String description;// 描述
    private String tag;// 标签
    private String source_id; // 来源id集合
    private Float score;// 评分
    private Float db_score;// 评分
    private Integer category;// 电影、电视剧、综艺、动漫
    private String category_name;// 分类名称
    private String sub_category;// 动作 言情
    private String sub_category_name;// 子分类名称
    private String download_platform;// 允许下载平台
    private String play_platform;// 允许播放平台
    private String pay_platform;// 收费平台
    private Integer status;// 发布状态： 0未发布 1已发布2发布失败
    private Integer deleted;// 删除标记
    private Long fid;// 父专辑ID
    private Integer forder;// 在父专辑顺序
    private Integer is_end;// 是否完结
    private Integer follownum;// 跟播到第几集
    private Integer is_height;// 是否高清
    private String area;// 地区
    private String area_name;// 地区名称
    private String release_date;// 上映时间
    private String letv_release_date;// 乐视上映时间
    private Integer episode;// 总集数
    private String play_status;// 跟播状态
    private String maker_company;// 出品公司
    private String directory;// 导演
    private String actor;// 演员
    private String actor_play;// 饰演角色
    private String actor_desc;// 对演员在视频中的角色的介绍
    private String actor_play_pic;// 角色定妆照
    private String starring;// 主演
    private String starring_play;// 主演饰演角色
    private String starring_play_pic;// 主演定妆照
    private String starring_desc;// 主演介绍
    private String pic_collections;// 图片集
    private Map<String, String> picMap;// 图片集
    private String pic_original;// 原图和缩列图
    private String screen_writer;// 编剧
    private String maker;// 制片人
    private Integer sports_type;// 体育分类
    private String sports_type_name;// 体育分类名称
    private Integer letv_make_style;// 乐视制造分类
    private String letv_make_style_name;// 乐视制造分类名称
    private Integer pop_style;// 风尚分类
    private String pop_style_name;// 风尚分类名称
    private Integer letv_produce_style;// 乐视出品分类
    private String letv_produce_style_name;// 乐视出品分类名称
    private Integer film_base_type;// 电影频道分类 1.电影 2.微电影
    private String film_base_type_name;// 电影分类名称
    private Integer travel_theme;// 旅游主题
    private String travel_theme_name;// 旅游主题名称
    private Integer travel_type;// 旅游类型
    private String travel_type_name;// 旅游类型名称
    private Integer field_type;// 领域类型
    private String field_type_name;// 领域类型名称
    private String r_company;// 节目来源
    private Integer language;// 内容语言
    private String language_name;// 内容语言名称
    private String fit_age;// 适应年龄
    private String cast;// 动漫频道的声优
    private String dub;// 配音
    private String producer;// 监制
    private String compere;// 主持人
    private String instructor;// 讲师
    private String music_authors;// 音乐作词人
    private String music_style;// 音乐风格
    private String music_style_name;// 音乐风格名称
    private String record_company;// 唱片公司
    private String issue_company;// 发行公司
    private String music_compose;// 音乐作曲者
    private Integer program_style;// 项目分类
    private String program_style_name;// 项目分类名称
    private String play_tv_name;// 播出电视台名称 可能有多个电视台
    private String play_tv;// 播出电视台 可能有多个电视台
    private String supervise;// 动漫监督
    private String originator;// 原作
    private String recreation_type;// 娱乐频道 娱乐分类 原娱乐频道的专辑类型
    private String recreation_type_name;// 娱乐频道 娱乐分类 原娱乐频道的专辑类型
    private String carfilm_type;// 汽车频道的专辑类型
    private String carfilm_type_name;// 汽车频道的专辑类型
    private String first_play_time;//
    private String school;// 公开课频道 学校字段
    private Integer is_follow;// 是否跟播
    private String play_streams;// 专辑所有的码流
    private String positive_play_streams;// 正片码流
    private Integer isyuanxian;// 是否网络院线:1是 0否
    private Integer cntv;// 1:cntv上线 0 cntv下线
    private Integer cibn;// 国广
    private Integer wasu;// 华数
    private Date create_time;// 创建时间
    private Date update_time;// 修改时间
    private String album_type_all;// 对应新的album_type和recreationType
    private String album_type_name_all;// 相应的名称
    private String directory_all;// 对应新的directory、instructor和originator
    private String starring_all;// 对应新的starring、compere和supervise
    private String actor_all;// 对应新的actor和cast
    private String actor_play_all;// 对应新的actorplay和dub
    private String maker_all;// 对应新的maker和producer
    @Deprecated
    private Integer category_all;// 对应老的分类id
    private String category_name_all;// 对应老的分类名称
    private String sub_category_all;// 对应新的subcategory和musicStyle
    private String sub_category_name_all;// 相应的名称
    private String rcompany_all;// 对应新的rCompany、scholl、recordCompany
    private String style;// 对应letvMakeStyle、popStyle、letvProduceStyle、soprtsType和filmBaseType
    private String style_name;// 相应的名称
    private String remark;// 备注

    private Integer recomm_level;// 推荐指数

    private Integer copyright_type;// 版权类型
    private String copyright_type_name;// 版权名称
    private String copyright_start;// 版权开始时间
    private String copyright_end;// 版权结束时间
    private String copyright_company;// 版权公司

    private Integer duration;// 时长
    private Integer is_homemade;// 是否是自制剧
    private Integer pushflag;// 是否TV版外跳0TV版 1Tv版外跳
    private Integer album_attr;// 1正片
    private String actor_id;
    private String starring_id;
    private String directory_id;

    /**
     * 专辑关联，可以是多个专辑，使用英文逗号分隔
     */
    private String relationAlbumId;
    private String nowEpisodes;// 更新至{nowEpisodes}集
    private String nowIssue;// 更新至{nowIssue}期

    /**
     * 片段专辑ID，一般是一个专辑id
     */
    private String relationId;
    private Date videoFollowTime;
    private String varietyShow;// 纪录片、电视剧等频道是不是综艺类型,1是 0否
    private Double price;
    private String priceDesc;

    private Integer contentRatingId;// 内容分级id
    private String contentRatingValue;// 内容分级value
    private String contentRatingDesc;// 内容分级描述（界面显示用）

    private String auditHis;// cibn审核历史
    private String auditWasu;// wasu审核历史

    /**
     * TV首次上映时间，即第一集正片剧集上映时间，格式为"yyyy-MM-dd
     * HH:mm:ss:，注意与pushTVTime的使用关系，后期可废掉pushTVTime
     */
    private String tvFirstOnTime;

    /**
     * 当前专辑起播视频id，目前策略是，取正片列表第一集
     */
    private Long videoId;
    private Integer isDanmaku;// 是否有弹幕 1 有，0无
    private String coopPlatform; // Cooperation platform: 默认为null-乐视，1-芒果
    private Long playCount; // CP播放量
    private Integer isPay; // CP是否付费
    private String externalId; // CP播放id

    private Map<Integer, String> video_porder_charge;

    public Map<Integer, String> getVideo_porder_charge() {
        return video_porder_charge;
    }

    public void setVideo_porder_charge(Map<Integer, String> video_porder_charge) {
        this.video_porder_charge = video_porder_charge;
    }

    public Map<String, String> getPicMap() {
        return picMap;
    }

    public void setPicMap(Map<String, String> picMap) {
        this.picMap = picMap;
    }

    private Integer isPushChild;// 是否推送到儿童频道1推送0不推送

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
        if (this.pic_collections == null) {
            return "";
        }
        try {
            Map<String, String> map = JsonUtil.parse(this.pic_collections, new TypeReference<Map<String, String>>() {
            });
            if (map != null) {
                return map.get(width + "*" + hight);
            }
        } catch (Exception e) {

        }
        return "";
    }

    public String getPicNew(Integer width, Integer hight) {
        if (this.picMap == null) {
            this.picMap = JsonUtil.parse(this.pic_collections, new TypeReference<Map<String, String>>() {
            });
        }
        if (this.picMap == null) {
            return "";
        }
        return this.picMap.get(width + "*" + hight);
    }

    /**
     * 根据key值取得第一个不为空的图片
     * @param key
     * @return
     */
    public String getPic(String[] keys) {
        String pic = null;
        if (this.pic_collections == null || keys == null) {
            return pic;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, String> map = objectMapper.readValue(this.pic_collections,
                    new TypeReference<Map<String, String>>() {
                    });
            if (map != null) {
                for (String key : keys) {
                    if (key != null) {
                        pic = StringUtils.trimToNull(map.get(key));
                        if (pic != null) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return pic;
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

    public String getMusic_style_name() {
        return this.music_style_name;
    }

    public void setMusic_style_name(String music_style_name) {
        this.music_style_name = music_style_name;
    }

    public String getProgram_style_name() {
        return this.program_style_name;
    }

    public void setProgram_style_name(String program_style_name) {
        this.program_style_name = program_style_name;
    }

    public String getRecreation_type_name() {
        return this.recreation_type_name;
    }

    public void setRecreation_type_name(String recreation_type_name) {
        this.recreation_type_name = recreation_type_name;
    }

    public String getCarfilm_type_name() {
        return this.carfilm_type_name;
    }

    public void setCarfilm_type_name(String carfilm_type_name) {
        this.carfilm_type_name = carfilm_type_name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage_name() {
        return this.language_name;
    }

    public void setLanguage_name(String language_name) {
        this.language_name = language_name;
    }

    public Long getItv_album_id() {
        return this.itv_album_id;
    }

    public void setItv_album_id(Long itv_album_id) {
        this.itv_album_id = itv_album_id;
    }

    public Integer getAlbum_base_type() {
        return this.album_base_type;
    }

    public void setAlbum_base_type(Integer album_base_type) {
        this.album_base_type = album_base_type;
    }

    public Integer getAlbum_type() {
        return this.album_type;
    }

    public void setAlbum_type(Integer album_type) {
        this.album_type = album_type;
    }

    public String getAlbum_type_name() {
        return this.album_type_name;
    }

    public void setAlbum_type_name(String album_type_name) {
        this.album_type_name = album_type_name;
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

    public Integer getIs_homemade() {
        return this.is_homemade;
    }

    public void setIs_homemade(Integer is_homemade) {
        this.is_homemade = is_homemade;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getSub_title() {
        return this.sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getDb_score() {
        return db_score;
    }

    public void setDb_score(Float db_score) {
        this.db_score = db_score;
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

    public Long getFid() {
        return this.fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Integer getForder() {
        return this.forder;
    }

    public void setForder(Integer forder) {
        this.forder = forder;
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

    public Integer getIs_height() {
        return this.is_height;
    }

    public void setIs_height(Integer is_height) {
        this.is_height = is_height;
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

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getLetv_release_date() {
        return this.letv_release_date;
    }

    public void setLetv_release_date(String letv_release_date) {
        this.letv_release_date = letv_release_date;
    }

    public Integer getEpisode() {
        return this.episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public String getPlay_status() {
        return this.play_status;
    }

    public void setPlay_status(String play_status) {
        this.play_status = play_status;
    }

    public String getMaker_company() {
        return this.maker_company;
    }

    public void setMaker_company(String maker_company) {
        this.maker_company = maker_company;
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

    public String getActor_desc() {
        return this.actor_desc;
    }

    public void setActor_desc(String actor_desc) {
        this.actor_desc = actor_desc;
    }

    public String getActor_play_pic() {
        return this.actor_play_pic;
    }

    public void setActor_play_pic(String actor_play_pic) {
        this.actor_play_pic = actor_play_pic;
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

    public String getStarring_play_pic() {
        return this.starring_play_pic;
    }

    public void setStarring_play_pic(String starring_play_pic) {
        this.starring_play_pic = starring_play_pic;
    }

    public String getStarring_desc() {
        return this.starring_desc;
    }

    public void setStarring_desc(String starring_desc) {
        this.starring_desc = starring_desc;
    }

    public String getPic_collections() {
        return this.pic_collections;
    }

    public void setPic_collections(String pic_collections) {
        this.pic_collections = pic_collections;
    }

    public String getPic_original() {
        return this.pic_original;
    }

    public void setPic_original(String pic_original) {
        this.pic_original = pic_original;
    }

    public String getScreen_writer() {
        return this.screen_writer;
    }

    public void setScreen_writer(String screen_writer) {
        this.screen_writer = screen_writer;
    }

    public String getMaker() {
        return this.maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
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

    public Integer getFilm_base_type() {
        return this.film_base_type;
    }

    public void setFilm_base_type(Integer film_base_type) {
        this.film_base_type = film_base_type;
    }

    public String getFilm_base_type_name() {
        return this.film_base_type_name;
    }

    public void setFilm_base_type_name(String film_base_type_name) {
        this.film_base_type_name = film_base_type_name;
    }

    public String getR_company() {
        return this.r_company;
    }

    public void setR_company(String r_company) {
        this.r_company = r_company;
    }

    public Integer getLanguage() {
        return this.language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getFit_age() {
        return this.fit_age;
    }

    public void setFit_age(String fit_age) {
        this.fit_age = fit_age;
    }

    public String getCast() {
        return this.cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDub() {
        return this.dub;
    }

    public void setDub(String dub) {
        this.dub = dub;
    }

    public String getProducer() {
        return this.producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCompere() {
        return this.compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
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

    public String getMusic_compose() {
        return this.music_compose;
    }

    public void setMusic_compose(String music_compose) {
        this.music_compose = music_compose;
    }

    public Integer getProgram_style() {
        return this.program_style;
    }

    public void setProgram_style(Integer program_style) {
        this.program_style = program_style;
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

    public String getSupervise() {
        return this.supervise;
    }

    public void setSupervise(String supervise) {
        this.supervise = supervise;
    }

    public String getOriginator() {
        return this.originator;
    }

    public void setOriginator(String originator) {
        this.originator = originator;
    }

    public String getRecreation_type() {
        return this.recreation_type;
    }

    public void setRecreation_type(String recreation_type) {
        this.recreation_type = recreation_type;
    }

    public String getCarfilm_type() {
        return this.carfilm_type;
    }

    public void setCarfilm_type(String carfilm_type) {
        this.carfilm_type = carfilm_type;
    }

    public String getFirst_play_time() {
        return this.first_play_time;
    }

    public void setFirst_play_time(String first_play_time) {
        this.first_play_time = first_play_time;
    }

    public String getSchool() {
        return this.school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getIs_follow() {
        return this.is_follow;
    }

    public void setIs_follow(Integer is_follow) {
        this.is_follow = is_follow;
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

    public String getAlbum_type_all() {
        return this.album_type_all;
    }

    public void setAlbum_type_all(String album_type_all) {
        this.album_type_all = album_type_all;
    }

    public String getAlbum_type_name_all() {
        return this.album_type_name_all;
    }

    public void setAlbum_type_name_all(String album_type_name_all) {
        this.album_type_name_all = album_type_name_all;
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

    public String getActor_all() {
        return this.actor_all;
    }

    public void setActor_all(String actor_all) {
        this.actor_all = actor_all;
    }

    public String getActor_play_all() {
        return this.actor_play_all;
    }

    public void setActor_play_all(String actor_play_all) {
        this.actor_play_all = actor_play_all;
    }

    public String getMaker_all() {
        return this.maker_all;
    }

    public void setMaker_all(String make_all) {
        this.maker_all = make_all;
    }

    public String getCategory_name_all() {
        return this.category_name_all;
    }

    public void setCategory_name_all(String category_name_all) {
        this.category_name_all = category_name_all;
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

    public String getRcompany_all() {
        return this.rcompany_all;
    }

    public void setRcompany_all(String rcompany_all) {
        this.rcompany_all = rcompany_all;
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

    public String getCopyright_company() {
        return this.copyright_company;
    }

    public void setCopyright_company(String copyright_company) {
        this.copyright_company = copyright_company;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public void initSplitProperty() {
        // style
        StringBuffer style_all = new StringBuffer();
        StringBuffer style_name_all = new StringBuffer();
        if (this.letv_make_style != null && this.letv_make_style.intValue() > 0) {
            style_all.append(this.letv_make_style).append(",");
            style_name_all.append(this.letv_make_style_name).append(",");
        }
        if (this.pop_style != null && this.pop_style.intValue() > 0) {
            style_all.append(this.pop_style).append(",");
            style_name_all.append(this.pop_style_name).append(",");
        }
        if (this.letv_produce_style != null && this.letv_produce_style.intValue() > 0) {
            style_all.append(this.letv_produce_style).append(",");
            style_name_all.append(this.letv_produce_style_name).append(",");
        }
        if (this.sports_type != null && this.sports_type.intValue() > 0) {
            style_all.append(this.sports_type).append(",");
            style_name_all.append(this.sports_type_name).append(",");
        }
        if (this.film_base_type != null && this.film_base_type.intValue() > 0) {
            style_all.append(this.film_base_type).append(",");
            style_name_all.append(this.film_base_type_name).append(",");
        }
        if (this.travel_type != null && this.travel_type.intValue() > 0) {
            style_all.append(this.travel_type).append(",");
            style_name_all.append(this.travel_type_name).append(",");
        }
        // albumType
        StringBuffer albumType = new StringBuffer();
        StringBuffer albumType_name = new StringBuffer();
        if (this.album_type != null && this.album_type.intValue() > 0) {
            albumType.append(this.album_type).append(",");
            albumType_name.append(this.album_type_name).append(",");
        }
        if (this.recreation_type != null && this.recreation_type.trim().length() > 0) {
            albumType.append(this.recreation_type).append(",");
            albumType_name.append(this.recreation_type_name).append(",");
        }
        // directory
        StringBuffer directory = new StringBuffer();
        if (this.directory != null && this.directory.trim().length() > 0) {
            directory.append(this.directory).append(",");
        }
        if (this.instructor != null && this.instructor.trim().length() > 0) {
            directory.append(this.instructor).append(",");
        }
        if (this.originator != null && this.originator.trim().length() > 0) {
            directory.append(this.originator).append(",");
        }
        // starring
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
        if (this.supervise != null && this.supervise.trim().length() > 0) {
            String[] superviseArray = this.supervise.split(",");
            for (String superviseElement : superviseArray) {
                if (StringUtils.trimToNull(superviseElement) != null) {
                    starringAllSet.add(StringUtils.trimToNull(superviseElement));
                }
            }
        }
        // actor
        StringBuffer actor = new StringBuffer();
        if (this.actor != null && this.actor.trim().length() > 0) {
            actor.append(this.actor).append(",");
        }
        if (this.cast != null && this.cast.trim().length() > 0) {
            actor.append(this.cast).append(",");
        }
        // actorPlay
        StringBuffer actorPlay = new StringBuffer();
        if (this.actor_play != null && this.actor_play.trim().length() > 0) {
            actorPlay.append(this.actor_play).append(",");
        }
        if (this.dub != null && this.dub.trim().length() > 0) {
            actorPlay.append(this.dub).append(",");
        }

        // maker
        StringBuffer maker = new StringBuffer();
        if (this.maker != null && this.maker.trim().length() > 0) {
            maker.append(this.maker).append(",");
        }
        if (this.producer != null && this.producer.trim().length() > 0) {
            maker.append(this.producer).append(",");
        }

        // subcategory
        StringBuffer subcategory = new StringBuffer();
        StringBuffer subcategory_name = new StringBuffer();
        if (this.sub_category != null && this.sub_category.trim().length() > 0) {
            subcategory.append(this.sub_category).append(",");
            subcategory_name.append(this.sub_category_name).append(",");
        }
        if (this.music_style != null && this.music_style.trim().length() > 0) {
            subcategory.append(this.music_style).append(",");
            subcategory_name.append(this.music_style_name).append(",");
        }
        if (this.carfilm_type != null && this.carfilm_type.trim().length() > 0) {
            subcategory.append(this.carfilm_type).append(",");
            subcategory_name.append(this.carfilm_type_name).append(",");
        }
        if (this.field_type != null && this.field_type.intValue() > 0) {
            subcategory.append(this.field_type).append(",");
            subcategory_name.append(this.field_type_name).append(",");
        }
        if (this.travel_theme != null && this.travel_theme.intValue() > 0) {
            subcategory.append(this.travel_theme).append(",");
            subcategory_name.append(this.travel_theme_name).append(",");
        }
        // rcompany
        StringBuffer rcompany = new StringBuffer();
        if (this.r_company != null && this.r_company.trim().length() > 0) {
            rcompany.append(this.r_company).append(",");
        }
        if (this.school != null && this.school.trim().length() > 0) {
            rcompany.append(this.school).append(",");
        }
        if (this.record_company != null && this.record_company.trim().length() > 0) {
            rcompany.append(this.record_company).append(",");
        }
        // ==============================================================================
        if (style_all.length() > 0) {
            style_all = style_all.deleteCharAt(style_all.length() - 1);
        }
        if (style_name_all.length() > 0) {
            style_name_all = style_name_all.deleteCharAt(style_name_all.length() - 1);
        }
        if (albumType.length() > 0) {
            albumType = albumType.deleteCharAt(albumType.length() - 1);
        }
        if (albumType_name.length() > 0) {
            albumType_name = albumType_name.deleteCharAt(albumType_name.length() - 1);
        }
        if (directory.length() > 0) {
            directory = directory.deleteCharAt(directory.length() - 1);
        }

        if (actor.length() > 0) {
            actor = actor.deleteCharAt(actor.length() - 1);
        }
        if (actorPlay.length() > 0) {
            actorPlay = actorPlay.deleteCharAt(actorPlay.length() - 1);
        }
        if (maker.length() > 0) {
            maker = maker.deleteCharAt(maker.length() - 1);
        }
        if (subcategory.length() > 0) {
            subcategory = subcategory.deleteCharAt(subcategory.length() - 1);
        }
        if (subcategory_name.length() > 0) {
            subcategory_name = subcategory_name.deleteCharAt(subcategory_name.length() - 1);
        }
        if (rcompany.length() > 0) {
            rcompany = rcompany.deleteCharAt(rcompany.length() - 1);
        }

        this.album_type_all = albumType.toString();
        this.album_type_name_all = albumType_name.toString();
        this.style = style_all.toString();
        this.style_name = style_name_all.toString();
        this.directory_all = directory.toString();

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
        this.actor_all = actor.toString();
        this.actor_play_all = actorPlay.toString();
        this.maker_all = maker.toString();
        this.sub_category_all = subcategory.toString();
        this.sub_category_name_all = subcategory_name.toString();
        this.rcompany_all = rcompany.toString();
    }

    /**
     * 是否TV版有播放版权
     * @return
     */
    public Boolean isTVCopayright() {
        if ((!this.isTV() && !this.isTVOut()) || (this.deleted != null && this.deleted == 1)) {
            return false;
        }
        return true;
    }

    public Boolean isTV() {
        if (this.getPlay_platform() != null && this.getPlay_platform().contains(CommonConstants.TV_PLAY_PLAT_FROM)) {
            return true;
        }
        return false;
    }

    public Boolean isTVOut() {
        if (this.getPlay_platform() != null
                && this.getPlay_platform().indexOf(CommonConstants.TV_OUT_PLAY_PLAT_FROM) > -1) {
            return true;
        }
        return false;
    }

    /**
     * 是否TV版收费
     * @return
     */
    public boolean isTVPay() {
        if (this.getPay_platform() != null && MmsDataUtil.isSupportPayPlatform(this.getPay_platform())) {
            return true;
        }
        return false;
    }

    public boolean isPay(Integer p_devType) {
        return MmsDataUtil.isCharge4Platform(this.getPay_platform(), p_devType);
    }

    public Boolean isPositive() {
        // 电影、电视剧有正片的概念
        return VideoCommonUtil.isPositive(2, this.category, this.album_type, null);
    }

    /**
     * 是否按照综艺样式展示
     */
    public boolean isVarietyStyleShow() {
        if (StringUtils.isBlank(this.getVarietyShow()) || this.getCategory() == null) {
            return false;
        }

        if (this.category != 2) { // 除电视剧外的频道不需要设置
            return false;
        }

        if (this.isPositive() && "1".equals(this.getVarietyShow())) { // 正片专辑且勾选“栏目”
            return true;
        }

        return false;
    }

    public String getActor_id() {
        return this.actor_id;
    }

    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }

    public String getStarring_id() {
        return this.starring_id;
    }

    public void setStarring_id(String starring_id) {
        this.starring_id = starring_id;
    }

    public String getDirectory_id() {
        return this.directory_id;
    }

    public void setDirectory_id(String directory_id) {
        this.directory_id = directory_id;
    }

    public String getRelationAlbumId() {
        return this.relationAlbumId;
    }

    public void setRelationAlbumId(String relationAlbumId) {
        this.relationAlbumId = relationAlbumId;
    }

    public String getNowEpisodes() {
        return this.nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public String getNowIssue() {
        return this.nowIssue;
    }

    public void setNowIssue(String nowIssue) {
        this.nowIssue = nowIssue;
    }

    public String getRelationId() {
        return this.relationId;
    }

    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }

    public Date getVideoFollowTime() {
        return this.videoFollowTime;
    }

    public void setVideoFollowTime(Date videoFollowTime) {
        this.videoFollowTime = videoFollowTime;
    }

    public String getVarietyShow() {
        return this.varietyShow;
    }

    public void setVarietyShow(String varietyShow) {
        this.varietyShow = varietyShow;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceDesc() {
        return this.priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

    public Integer getContentRatingId() {
        return this.contentRatingId;
    }

    public void setContentRatingId(Integer contentRatingId) {
        this.contentRatingId = contentRatingId;
    }

    public String getContentRatingValue() {
        return this.contentRatingValue;
    }

    public void setContentRatingValue(String contentRatingValue) {
        this.contentRatingValue = contentRatingValue;
    }

    public String getContentRatingDesc() {
        return this.contentRatingDesc;
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

    public String getPositive_play_streams() {
        return this.positive_play_streams;
    }

    public void setPositive_play_streams(String positive_play_streams) {
        this.positive_play_streams = positive_play_streams;
    }

    public String getTvFirstOnTime() {
        return this.tvFirstOnTime;
    }

    public void setTvFirstOnTime(String tvFirstOnTime) {
        this.tvFirstOnTime = tvFirstOnTime;
    }

    public Long getVideoId() {
        return this.videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Long playCount) {
        this.playCount = playCount;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
