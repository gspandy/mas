package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.CountUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.ChildLockDto;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo.ResourceInfo;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PackageInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.*;

@ApiModel(value = "AlbumDto", description = "专辑详情")
public class AlbumDto extends ResourceInfo {

    /**
     *
     */
    private static final long serialVersionUID = 2833596847143827652L;

    /**
     * 专辑id
     */
    @ApiModelProperty(value = "专辑id")
    private String albumId;

    /**
     * 专辑分类id
     */
    @ApiModelProperty(value = "专辑分类id")
    private Integer categoryId;
    /**
     * 专辑二级分类
     */
    @ApiModelProperty(value = "专辑二级分类")
    private Map<String, String> subCategory;
    /**
     * 搜索接口字段兼容
     */
    @ApiModelProperty(value = "专辑二级分类id")
    private String subCategoryId;
    /**
     * 子分类(标签)名称
     */
    @ApiModelProperty(value = "子分类(标签)名称")
    private String subCategoryName;

    /**
     * 子分类(标签)名称；Lecom项目使用，和subCategoryName同一意思，但因subCategoryName被多个项目使用，格式不一，
     * 且lecom项目中老版本使用，存在兼容问题，这里新加字段表示，后学迭代版本中删除
     */
    @ApiModelProperty(value = "子分类(标签)名称：Lecom项目使用")
    private String subCateName;

    /**
     * 标签（2.5详情页中“类型”）
     */
    @ApiModelProperty(value = "标签名称")
    private String tagName;

    /**
     * 焦点图
     */
    @ApiModelProperty(value = "焦点图")
    private String img;

    /**
     * 图片尺寸
     */
    @ApiModelProperty(value = "图片尺寸")
    private String imgSize;

    /**
     * 名称
     */
    @ApiModelProperty(value = "专辑名称")
    private String name;

    /**
     * 子名称
     */
    @ApiModelProperty(value = "专辑子名称")
    private String subName;

    /**
     * 别名
     */
    @ApiModelProperty(value = "专辑别名")
    private String alias;

    /**
     * 描述
     */
    @ApiModelProperty(value = "专辑描述")
    private String description;

    /**
     * 时长，lecom版本返回毫秒
     */
    @ApiModelProperty(value = "时长，lecom版本返回毫秒")
    private String duration;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private Float score;

    /**
     * 是否是推荐
     */
    @ApiModelProperty(value = "是否是推荐")
    private Boolean is_rec;

    /**
     * 是否正片
     */
    @ApiModelProperty(value = "是否正片")
    private Boolean positive;

    /**
     * 正片、预告片等类型id
     */
    @ApiModelProperty(value = "正片、预告片等类型id，如正片－180001，预告－180002，花絮－180003，资讯－180004")
    private Integer albumTypeId;

    /**
     * 正片、预告片等类型名
     */
    @ApiModelProperty(value = "正片、预告片等类型名")
    private String albumTypeName;

    /**
     * 默认选中码流
     */
    @ApiModelProperty(value = "默认选中码流")
    private String defaultStream;
    @ApiModelProperty(value = "默认选中码流名称")
    private String defaultStreamName;

    /**
     * 码流列表
     */
    @ApiModelProperty(value = "码流列表")
    private List<Stream> streams;

    /**
     * 是否收费
     */
    @ApiModelProperty(value = "是否收费")
    private Boolean charge;

    /**
     * 适应年龄
     */
    @ApiModelProperty(value = "适应年龄")
    private String fitAge;

    /**
     * 地区
     */
    @ApiModelProperty(value = "地区")
    private String areaName;

    /**
     * 发行日期/上映日期(详情页界面显示用)
     */
    @ApiModelProperty(value = "发行日期/上映日期(详情页界面显示用)")
    private String releaseDate;

    /**
     * 系列，如变形金刚1、2、3、4等
     */
    @ApiModelProperty(value = "关联系列专辑id，如变形金刚1、2、3、4等")
    private String relationAlbumId;

    /**
     * 系列 , 共几季,可以查看<纸牌屋>
     */
    @ApiModelProperty(value = "关联系列内容 , 共几季,可以查看<纸牌屋>")
    private Integer relationAlbumCnt;

    /**
     * 系列后缀[季、部、场、届]
     */
    @ApiModelProperty(value = "关联系列类型，如季、部、场、届")
    private Integer relationAlbumType;

    /**
     * 是否跟播剧
     */
    @ApiModelProperty(value = "是否跟播剧")
    private Boolean end;

    /**
     * 跟播状态
     */
    @ApiModelProperty(value = "跟播状态")
    private String playStatus;

    /**
     * 总集数
     */
    @ApiModelProperty(value = "总集数")
    private Integer episodes;

    /**
     * 更新至{nowEpisodes}集
     */
    @ApiModelProperty(value = "更新至x集")
    private String nowEpisode;

    /**
     * 更新至{nowIssue}期
     */
    @ApiModelProperty(value = "更新至x期")
    private String nowIssue;

    /**
     * 更新频率
     */
    @ApiModelProperty(value = "更新频率")
    private String updateFrequency;

    /**
     * 专辑观看次数
     */
    @ApiModelProperty(value = "专辑观看次数")
    private String vv;

    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private String commentCnt;

    /**
     * 允许播放平台
     */
    @ApiModelProperty(value = "允许播放平台")
    private String playPlatform;

    /**
     * 允许下载平台
     */
    @ApiModelProperty(value = "允许下载平台")
    private Map<String, String> downloadPlatform;

    /**
     * 剧集列表（正片）
     */
    @ApiModelProperty(value = "剧集列表（正片）")
    private Set<VideoDto> positiveSeries;

    @ApiModelProperty(value = "剧集播放列表分页数据")
    private AlbumSeriesPlayListPageDto positiveSeriesPage;

    /**
     * 预告
     */
    @ApiModelProperty(value = "预告片集合")
    private List<VideoDto> preSeries;

    /**
     * 片段
     */
    @ApiModelProperty(value = "片段")
    private List<VideoDto> segments;

    /**
     * 相关系列；2016-07-14乐视视频美国版启用，取媒资关联专辑字段，内容以专辑形式呈现；设计用来替代segments
     */
    @ApiModelProperty(value = "相关系列, 取媒资关联专辑字段，内容以专辑形式呈现；设计用来替代segments")
    private List<BaseData> relatedSeries;

    /**
     * 剧集列表扩展（预告、抢先看等）
     */
    @ApiModelProperty(value = "剧集列表扩展（预告、抢先看等）")
    private Set<VideoDto> positiveAddSeries;

    /**
     * 周边视频；2016-07-14乐视视频美国版新增需求；专辑详情页展示选集（positiveSeries）、相关系列（segments）、周边视频（
     * attachingSeries）和相关推荐（relation）
     */
    @ApiModelProperty(value = "周边视频")
    private List<VideoDto> attachingSeries;

    @ApiModelProperty(value = "附加剧集播放列表分页数据")
    private AlbumSeriesPlayListPageDto attachingSeriesPage;

    /**
     * 演员信息
     */
    @ApiModelProperty(value = "演员信息")
    private List<Actor> actorInfo;

    /**
     * 主持人
     */
    @ApiModelProperty(value = "演员信息")
    private String compere;

    /**
     * 歌手
     */
    @ApiModelProperty(value = "歌手")
    private String singer;

    /**
     * 播出电视台名称
     */
    @ApiModelProperty(value = "播出电视台名称")
    private String playTvName;

    /**
     * 播出电视台id
     */
    @ApiModelProperty(value = "播出电视台id")
    private Integer playTvId;

    /**
     * （大陆版需求）是否展示成综艺样式（或，Lecon需求--是否按“剧集”展示）,1是 0否
     */
    @ApiModelProperty(value = "*是否展示成综艺样式（或，Lecon需求--是否按“剧集”展示）,1是 0否")
    private Integer varietyShow;

    /**
     * 根据当前专辑相关信息，通过推荐系统获取到的相关内容
     */
    @ApiModelProperty(value = "*根据当前专辑相关信息，通过推荐系统获取到的相关内容")
    private Object relation;

    /**
     * 根据当前专辑相关信息，通过推荐系统获取到的相关内容
     */
    @ApiModelProperty(value = "*人工维护相关专辑数量")
    private Integer relationAddCount;

    /**
     * 相关推荐，2016-07-14乐视视频美国版中启用，推荐数据，内容以专辑形式呈现；设计用来取代relation
     */
    @ApiModelProperty(value = "相关推荐，2016-07-14乐视视频美国版中启用，推荐数据，内容以专辑形式呈现；设计用来取代relation")
    private List<BaseData> recommendationSeries;

    /**
     * 内容分级id
     */
    @ApiModelProperty(value = "内容分级id")
    private String contentRatingId;

    /**
     * 内容分级描述
     */
    @ApiModelProperty(value = "内容分级描述")
    private String contentRatingValue;

    /**
     * 内容分级描述
     */
    @ApiModelProperty(value = "内容分级描述")
    private String contentRatingDesc;

    /**
     * 付费类型 1支持单点付费
     */
    @ApiModelProperty(value = "付费类型 1支持单点付费")
    private Integer payType;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间")
    private Integer expiredTime;

    /**
     * 单点剩余时间
     */
    @ApiModelProperty(value = "单点剩余时间")
    private String singleOrderLeftTime;

    /**
     * 单点：true:没够买或购买过期，false：购买没过期
     */
    @ApiModelProperty(value = "单点：true:没够买或购买过期，false：购买没过期")
    private Boolean expired;

    /**
     * 是否有TV版权，0--没有，1--有
     */
    @ApiModelProperty(value = "是否有TV版权，0--没有，1--有")
    private Integer tvCopyright;

    /**
     * 如果没有版权，需要返回数据来源，1--内网,2--外网
     */
    @ApiModelProperty(value = "如果没有版权，需要返回数据来源，1--内网,2--外网")
    private Integer src;

    /**
     * 一般去CMS中的配图pic1
     */
    @ApiModelProperty(value = "CMS中的配图")
    private String pic1;

    @ApiModelProperty(value = "全网专辑id")
    private String globalId;

    @ApiModelProperty(value = "内容提供商名称")
    private String cp; // cp名称

    @ApiModelProperty(value = "全网分类id")
    private String globalCid;// 全网分类

    /**
     * 第三方内容id
     */
    @ApiModelProperty(value = "第三方内容id")
    private String external_id;

    /**
     * 是否收费 1-收费 0-不收费
     */
    @ApiModelProperty(value = "是否收费 1-收费 0-不收费")
    private String ifCharge;

    // for tvod icon type
    /**
     * 收费的类型
     */
    @ApiModelProperty(value = "收费的类型")
    private Integer chargeType;

    /**
     * 桌面打洞兼容id
     */
    @ApiModelProperty(value = "桌面打洞兼容id")
    private String id;

    @ApiModelProperty(value = "角标类型")
    private String iconType;

    /**
     * 2016-07-29美国Le.com新增需求，在img基础上，再出一张大图，用于背景展示；取同专辑详情页中大背景图相同的图，便于客户端减少网络请求
     */
    @ApiModelProperty(value = "*新增需求，在img基础上，再出一张大图，用于背景展示；取同专辑详情页中大背景图相同的图，便于客户端减少网络请求; 相关推荐部分则为改版要求尺寸")
    private String bigImg;

    /**
     * 播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看
     * 5:码流收费,6--Lecom会员付费，7--Lecom无版权（有终端版权，但没有定价）；2016-08-11
     * Lecom需求添加，暂只用到1、6、7
     */
    @ApiModelProperty(value = "播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看 5:码流收费,6--Lecom会员付费，7--Lecom无版权（有终端版权，但没有定价）")
    private Integer playType;

    /**
     * 试看时长，单位-毫秒，2016-08-11 Lecom需求添加
     */
    @ApiModelProperty(value = "试看时长，单位-毫秒")
    private Long tryPlayTime;

    /**
     * 美国Le.com需求，未登陆或鉴权不过时返回当前视频所属套餐包信息
     */
    @ApiModelProperty(value = "美国Le.com需求，未登陆或鉴权不过时返回当前视频所属套餐包信息")
    private List<PackageInfoDto> packageInfo;

    /**
     * 是否可以领取试用 1:是 0:否
     */
    @ApiModelProperty(value = "是否可以领取试用 1:是 0:否")
    private String isCanTial;

    /**
     * 儿童锁信息
     */
    @ApiModelProperty(value = "儿童锁信息")
    private ChildLockDto childLock;

    /**
     * 专辑来源
     */
    @ApiModelProperty(value = "专辑来源")
    private String source;

    /**
     * 与播放相关的而外参数
     */
    @ApiModelProperty(value = "与播放相关的而外参数")
    private String external_play_id;

    /**
     * 分端付费参数集合
     */
    @ApiModelProperty(value = "分端付费参数集合")
    private List<ChargeInfoDto> chargeInfos;

    /**
     * 标签组: 文本中插入的{icontype}为图标占位符，例如 {score}－豆瓣评分，{rank}－TOP排名，{vcount}－播放次数
     */
    @ApiModelProperty(value = "标签组: 文本中插入的{icontype}为图标占位符，例如 {score}－豆瓣评分，{rank}－TOP排名，{vcount}－播放次数")
    private List<String> tags;

    private Integer rank;

    /**
     * 豆瓣评分
     */
    @ApiModelProperty(value = "豆瓣评分")
    private Float db_score;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIsCanTial() {
        return isCanTial;
    }

    public void setIsCanTial(String isCanTial) {
        this.isCanTial = isCanTial;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getGlobalCid() {
        return globalCid;
    }

    public void setGlobalCid(String globalCid) {
        this.globalCid = globalCid;
    }

    public String getIconType() {
        return this.iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getCp() {
        return this.cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
    }

    public String getSubCategoryId() {
        return this.subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public Boolean getIs_rec() {
        return this.is_rec;
    }

    public void setIs_rec(Boolean is_rec) {
        this.is_rec = is_rec;
    }

    @Override
    public int getDataType() {
        return DataConstant.DATA_TYPE_ALBUM;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Map<String, String> getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(Map<String, String> subCategory) {
        this.subCategory = subCategory;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAlbumTypeId() {
        return this.albumTypeId;
    }

    public void setAlbumTypeId(Integer albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public String getDefaultStream() {
        return this.defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public String getDefaultStreamName() {
        return this.defaultStreamName;
    }

    public void setDefaultStreamName(String defaultStreamName) {
        this.defaultStreamName = defaultStreamName;
    }

    public List<Stream> getStreams() {
        return this.streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }

    public String getFitAge() {
        return this.fitAge;
    }

    public void setFitAge(String fitAge) {
        this.fitAge = fitAge;
    }

    public String getRelationAlbumId() {
        return this.relationAlbumId;
    }

    public void setRelationAlbumId(String relationAlbumId) {
        this.relationAlbumId = relationAlbumId;
    }

    public Integer getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getNowEpisode() {
        return this.nowEpisode;
    }

    public void setNowEpisode(String nowEpisode) {
        this.nowEpisode = nowEpisode;
    }

    public String getNowIssue() {
        return this.nowIssue;
    }

    public void setNowIssue(String nowIssue) {
        this.nowIssue = nowIssue;
    }

    public String getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public Map<String, String> getDownloadPlatform() {
        return this.downloadPlatform;
    }

    public void setDownloadPlatform(Map<String, String> downloadPlatform) {
        this.downloadPlatform = downloadPlatform;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Boolean getPositive() {
        return this.positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public String getVv() {
        return this.vv;
    }

    public void setVv(String vv) {
        this.vv = vv;
    }

    public String getCommentCnt() {
        return this.commentCnt;
    }

    public void setCommentCnt(String commentCnt) {
        this.commentCnt = commentCnt;
    }

    public Set<VideoDto> getPositiveSeries() {
        return this.positiveSeries;
    }

    public void setPositiveSeries(Set<VideoDto> positiveSeries) {
        this.positiveSeries = positiveSeries;
    }

    public List<VideoDto> getPreSeries() {
        return this.preSeries;
    }

    public void setPreSeries(List<VideoDto> preSeries) {
        this.preSeries = preSeries;
    }

    public List<Actor> getActorInfo() {
        return this.actorInfo;
    }

    public void setActorInfo(List<Actor> actorInfo) {
        this.actorInfo = actorInfo;
    }

    public List<VideoDto> getSegments() {
        return this.segments;
    }

    public void setSegments(List<VideoDto> segments) {
        this.segments = segments;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSubCategoryName() {
        return this.subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getPlayTvName() {
        return this.playTvName;
    }

    public void setPlayTvName(String playTvName) {
        this.playTvName = playTvName;
    }

    public Integer getPlayTvId() {
        return this.playTvId;
    }

    public void setPlayTvId(Integer playTvId) {
        this.playTvId = playTvId;
    }

    public String getCompere() {
        return this.compere;
    }

    public void setCompere(String compere) {
        this.compere = compere;
    }

    public String getUpdateFrequency() {
        return this.updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getVarietyShow() {
        return this.varietyShow;
    }

    public void setVarietyShow(Integer varietyShow) {
        this.varietyShow = varietyShow;
    }

    public Integer getRelationAlbumCnt() {
        return this.relationAlbumCnt;
    }

    public void setRelationAlbumCnt(Integer relationAlbumCnt) {
        this.relationAlbumCnt = relationAlbumCnt;
    }

    public Integer getRelationAlbumType() {
        return this.relationAlbumType;
    }

    public void setRelationAlbumType(Integer relationAlbumType) {
        this.relationAlbumType = relationAlbumType;
    }

    public Object getRelation() {
        return this.relation;
    }

    public void setRelation(Object relation) {
        this.relation = relation;
    }

    public Integer getRelationAddCount() {
        return relationAddCount;
    }

    public void setRelationAddCount(Integer relationAddCount) {
        this.relationAddCount = relationAddCount;
    }

    public String getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getEnd() {
        return this.end;
    }

    public void setEnd(Boolean end) {
        this.end = end;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Boolean getCharge() {
        return this.charge;
    }

    public void setCharge(Boolean charge) {
        this.charge = charge;
    }

    public Set<VideoDto> getPositiveAddSeries() {
        return this.positiveAddSeries;
    }

    public void setPositiveAddSeries(Set<VideoDto> positiveAddSeries) {
        this.positiveAddSeries = positiveAddSeries;
    }

    public String getContentRatingId() {
        return this.contentRatingId;
    }

    public void setContentRatingId(String contentRatingId) {
        this.contentRatingId = contentRatingId;
    }

    public String getContentRatingDesc() {
        return this.contentRatingDesc;
    }

    public void setContentRatingDesc(String contentRatingDesc) {
        this.contentRatingDesc = contentRatingDesc;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getSingleOrderLeftTime() {
        return this.singleOrderLeftTime;
    }

    public void setSingleOrderLeftTime(String singleOrderLeftTime) {
        this.singleOrderLeftTime = singleOrderLeftTime;
    }

    public Boolean getExpired() {
        return this.expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public String getContentRatingValue() {
        return this.contentRatingValue;
    }

    public void setContentRatingValue(String contentRatingValue) {
        this.contentRatingValue = contentRatingValue;
    }

    public Integer getTvCopyright() {
        return this.tvCopyright;
    }

    public void setTvCopyright(Integer tvCopyright) {
        this.tvCopyright = tvCopyright;
    }

    public Integer getSrc() {
        return this.src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    public String getAlbumTypeName() {
        return albumTypeName;
    }

    public void setAlbumTypeName(String albumTypeName) {
        this.albumTypeName = albumTypeName;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public List<BaseData> getRelatedSeries() {
        return relatedSeries;
    }

    public void setRelatedSeries(List<BaseData> relatedSeries) {
        this.relatedSeries = relatedSeries;
    }

    public List<VideoDto> getAttachingSeries() {
        return attachingSeries;
    }

    public void setAttachingSeries(List<VideoDto> attachingSeries) {
        this.attachingSeries = attachingSeries;
    }

    public List<BaseData> getRecommendationSeries() {
        return recommendationSeries;
    }

    public void setRecommendationSeries(List<BaseData> recommendationSeries) {
        this.recommendationSeries = recommendationSeries;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Long getTryPlayTime() {
        return tryPlayTime;
    }

    public void setTryPlayTime(Long tryPlayTime) {
        this.tryPlayTime = tryPlayTime;
    }

    public List<PackageInfoDto> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<PackageInfoDto> packageInfo) {
        this.packageInfo = packageInfo;
    }

    public ChildLockDto getChildLock() {
        return childLock;
    }

    public void setChildLock(ChildLockDto childLock) {
        this.childLock = childLock;
    }

    public String getSubCateName() {
        return subCateName;
    }

    public void setSubCateName(String subCateName) {
        this.subCateName = subCateName;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public AlbumSeriesPlayListPageDto getPositiveSeriesPage() {
        return positiveSeriesPage;
    }

    public void setPositiveSeriesPage(AlbumSeriesPlayListPageDto positiveSeriesPage) {
        this.positiveSeriesPage = positiveSeriesPage;
    }

    public AlbumSeriesPlayListPageDto getAttachingSeriesPage() {
        return attachingSeriesPage;
    }

    public void setAttachingSeriesPage(AlbumSeriesPlayListPageDto attachingSeriesPage) {
        this.attachingSeriesPage = attachingSeriesPage;
    }

    public String getExternal_play_id() {
        return external_play_id;
    }

    public void setExternal_play_id(String external_play_id) {
        this.external_play_id = external_play_id;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public List<ChargeInfoDto> getChargeInfos() {
        return chargeInfos;
    }

    public void setChargeInfos(List<ChargeInfoDto> chargeInfos) {
        this.chargeInfos = chargeInfos;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Float getDb_score() {
        return db_score;
    }

    public void setDb_score(Float db_score) {
        this.db_score = db_score;
    }

    /**
     * 构建详情页标签组
     * @param albumMysqlTable
     * @param albumDto
     * @param commonParam
     * @return
     */
    public static List<String> buildTags(AlbumMysqlTable albumMysqlTable, AlbumDto albumDto, CommonParam commonParam) {
        List<String> tags = null;
        if (null != albumMysqlTable && null != albumDto) {
            tags = new ArrayList<String>();
            String tagString = null;
            if (StringUtil.isNotBlank(commonParam.getLangcode())) {
                commonParam.setLangcode(LocaleConstant.Langcode.ZH_CN);
            }

            Float score = albumDto.getDb_score(); // albumDto.getScore();
            if (null != score && score >= 6.0 && score < 10) { // 豆瓣评分
                tagString = MessageUtils.getMessage(VideoConstants.VIDEO_ALBUM_DETAIL_TAG_SCORE,
                        commonParam.getLangcode());
                tagString = String.format(tagString, String.valueOf(score));
                tags.add(tagString);
            }

            switch (albumMysqlTable.getCategory()) {
            case VideoConstants.Category.FILM: // 电影 上映时间的年份、语言/地区、影片类型*
                if (StringUtil.isNotBlank(albumMysqlTable.getTvFirstOnTime())) { // 年份
                    tagString = makeYear(albumMysqlTable.getTvFirstOnTime());
                    tags.add(tagString);
                }

                tagString = makeLanguageOrArea(albumMysqlTable.getLanguage_name(), albumMysqlTable.getArea_name());
                if (StringUtil.isNotBlank(tagString)) { // 语言/地区
                    tags.add(tagString);
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 影片类型*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }
                break;
            case VideoConstants.Category.TV: // 电视剧 地区、类型*
                tagString = makeLanguageOrArea(null, albumMysqlTable.getArea_name());
                if (StringUtil.isNotBlank(tagString)) { // 地区
                    tags.add(tagString);
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 类型*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }
                break;

            case VideoConstants.Category.CARTOON: // 动漫
                                                  // 配音/地区、影片分类*、专辑类型（TV版、OVA版、剧场版展示1个）
                tagString = makeLanguageOrArea(albumMysqlTable.getLanguage_name(), albumMysqlTable.getArea_name());
                if (StringUtil.isNotBlank(tagString)) { // 语言/地区
                    tags.add(tagString);
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 类型*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 3 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getAlbum_type_name())) {
                    tags.add(albumMysqlTable.getAlbum_type_name());
                }
                break;
            case VideoConstants.Category.VARIETY: // 综艺 地区，节目类型*
                tagString = makeLanguageOrArea(null, albumMysqlTable.getArea_name());
                if (StringUtil.isNotBlank(tagString)) { // 地区
                    tags.add(tagString);
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 类型*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }
                break;

            case VideoConstants.Category.TEACH: // 教育 地区，一级分类，二级分类，三级分类，功能属性*
                tagString = makeLanguageOrArea(null, albumMysqlTable.getArea_name());
                if (StringUtil.isNotBlank(tagString)) { // 地区
                    tags.add(tagString);
                }
                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 类型*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getTag())) { // 标签*
                    String[] cat_names = albumMysqlTable.getTag().split(" ");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }
                break;

            case VideoConstants.Category.DFILM: // 纪录片 上映时间的年份，语言/地区、专辑属性*
                if (StringUtil.isNotBlank(albumMysqlTable.getTvFirstOnTime())) { // 年份
                    tagString = makeYear(albumMysqlTable.getTvFirstOnTime());
                    tags.add(tagString);
                }

                tagString = makeLanguageOrArea(albumMysqlTable.getLanguage_name(), albumMysqlTable.getArea_name());
                if (StringUtil.isNotBlank(tagString)) { // 语言/地区
                    tags.add(tagString);
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 类型*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getTag())) { // 标签*
                    String[] cat_names = albumMysqlTable.getTag().split(" ");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }
                break;
            case VideoConstants.Category.PARENTING: // 亲子 语言，内容分类*，适合阶段
                tagString = makeLanguageOrArea(albumMysqlTable.getLanguage_name(), null);
                if (StringUtil.isNotBlank(tagString)) { // 语言
                    tags.add(tagString);
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getSub_category_name_all())) { // 内容分类*
                    String[] cat_names = albumMysqlTable.getSub_category_name_all().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 2 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }

                if (StringUtil.isNotBlank(albumMysqlTable.getFit_age())) { // 适合阶段
                    String[] cat_names = albumMysqlTable.getFit_age().split(",");
                    for (int i = 0, len = cat_names.length; i < len; i++) {
                        if (tags.size() < 4 && StringUtil.isNotBlank(cat_names[i].trim())) {
                            tags.add(cat_names[i]);
                        }
                    }
                }
                break;
            }

            if (null != albumDto.getRank() && albumDto.getRank() > 0) { // 热播标签
                tagString = MessageUtils.getMessage(VideoConstants.VIDEO_ALBUM_DETAIL_TAG_RANK,
                        commonParam.getLangcode());
                tagString = String.format(tagString, albumMysqlTable.getCategory_name(),
                        String.valueOf(albumDto.getRank()));
                tags.add(tagString);
            }

            if (StringUtil.isNotBlank(albumDto.getVv())) { // 播放量
                tagString = MessageUtils.getMessage(VideoConstants.VIDEO_ALBUM_DETAIL_TAG_VIEWCOUNT,
                        commonParam.getLangcode());
                String albumVv = albumDto.getVv();
                Long albumVvLong = StringUtil.toLong(albumVv, null);
                if (albumVvLong != null) {
                    albumVv = CountUtil.getOneDotString(albumVvLong);
                    tagString = String.format(tagString, albumVv);
                    tags.add(tagString);
                }
            }
        }
        return tags;
    }

    private static String makeYear(String timeStr) {
        String ret = null;
        Calendar tvFirstOnTimeCalendar = TimeUtil.parseCalendar(timeStr, TimeUtil.SIMPLE_DATE_FORMAT);
        if (null != tvFirstOnTimeCalendar) { // 年份
            ret = String.valueOf(tvFirstOnTimeCalendar.get(Calendar.YEAR));
        }
        return ret;
    }

    private static String makeLanguageOrArea(String language, String area) {
        String ret = null;
        if (StringUtil.isNotBlank(language)) {
            return language;
        }

        if (StringUtil.isNotBlank(area)) {
            return area;
        }
        return ret;
    }
}
