package com.letv.mas.caller.iptv.tvproxy.video.model.dto;


import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.TrailerVideoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.WaterMarkImage;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.ChildLockDto;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo.ResourceInfo;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PackageInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(value = "VideoDto", description = "视频详情")
public class VideoDto extends ResourceInfo implements Comparable {

    /**
     * 
     */
    private static final long serialVersionUID = 5620594281600581241L;

    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频id")
    private String videoId;

    /**
     * 内容分类（电影、电视剧）
     */
    @ApiModelProperty(value = "内容分类（电影、电视剧）")
    private Integer categoryId;

    /**
     * 视频所属专辑id
     */
    @ApiModelProperty(value = "视频所属专辑id")
    private String albumId;
    @ApiModelProperty(value = "视频所属专辑名")
    private String albumName;

    /**
     * 在专辑中顺序
     */
    @ApiModelProperty(value = "在专辑中顺序")
    private Integer orderInAlbum;

    /**
     * 是否正片
     */
    @ApiModelProperty(value = "是否正片")
    private Boolean positive;
    @ApiModelProperty(value = "视频类型id")
    private Integer videoTypeId;

    /**
     * 第几集、第几期
     */
    @ApiModelProperty(value = "第几集、第几期")
    private String episode;

    /**
     * 预告片类型 0 有效的预告片(0<跟播集数<3) 1 过期的预告片
     */
    @ApiModelProperty(value = "预告片类型 0 有效的预告片(0<跟播集数<3) 1 过期的预告片")
    private Integer preType;

    @ApiModelProperty(value = "图片")
    private String img; // 图片
    @ApiModelProperty(value = "图片尺寸")
    private String imgSize;// 图片尺寸

    @ApiModelProperty(value = "播放记录图片")
    private String playlogImg; // 播放记录图片

    /**
     * 视频名称
     */
    @ApiModelProperty(value = "视频名称")
    private String name;

    /**
     * 视频子名称
     */
    @ApiModelProperty(value = "视频子名称")
    private String subName;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String desc;

    /**
     * 视点图
     */
    @ApiModelProperty(value = "视点图")
    private String viewPic;

    /**
     * 观看次数
     */
    @ApiModelProperty(value = "观看次数")
    private String vv;

    /**
     * 时长，单位-毫秒
     */
    @ApiModelProperty(value = "时长，单位-毫秒")
    private Long duration;

    /**
     * 嘉宾
     */
    @ApiModelProperty(value = "嘉宾")
    private String guest;

    /**
     * 歌手
     */
    @ApiModelProperty(value = "歌手")
    private String singer;

    /**
     * 推送到TV版的时间
     */
    @ApiModelProperty(value = "推送到TV版的时间")
    private Long pushTVTime;

    @ApiModelProperty(value = "默认码流")
    private String defaultStream;
    @ApiModelProperty(value = "默认码流名")
    private String defaultStreamName;
    @ApiModelProperty(value = "默认码流列表")
    private List<Stream> streams;

    /**
     * 推送平台
     */
    @ApiModelProperty(value = "推送平台")
    private String playPlatform;

    /**
     * 是否收费
     */
    @ApiModelProperty(value = "是否收费")
    private String ifCharge;
    // for tvod icon type
    /**
     * 收费类型
     */
    @ApiModelProperty(value = "收费类型")
    private Integer chargeType;

    /**
     * 付费平台
     */
    @ApiModelProperty(value = "付费平台")
    private String payPlatForm;

    /**
     * 视频播放短地址
     */
    @ApiModelProperty(value = "视频播放短地址")
    private String storePath;

    /**
     * 播放/下载地址
     */
    @ApiModelProperty(value = "播放/下载地址")
    private String playUrl;

    /**
     * 备用播放地址1
     */
    @ApiModelProperty(value = "备用播放地址1")
    private String backUrl0;

    /**
     * 备用播放地址2
     */
    @ApiModelProperty(value = "备用播放地址2")
    private String backUrl1;

    /**
     * 备用播放地址3
     */
    @ApiModelProperty(value = "备用播放地址3")
    private String backUrl2;

    /**
     * 视频大小byte
     */
    @ApiModelProperty(value = "视频大小byte")
    private Long gsize;

    /**
     * 当前码流
     */
    @ApiModelProperty(value = "当前码流")
    private String currentStream;

    /**
     * 转码部门原始码流——客户端数据上报使用
     */
    @ApiModelProperty(value = "转码部门原始码流——客户端数据上报使用")
    private String currentStreamOriginal;

    /**
     * 播放码流的vtype值
     */
    @ApiModelProperty(value = "播放码流的vtype值")
    private Integer vtype;

    /**
     * 当前码流播放速度
     */
    @ApiModelProperty(value = "当前码流播放速度")
    private String currentStreamKps;

    /**
     * 当前免费码流 code :1300 name:高清
     */
    @ApiModelProperty(value = "当前码流播放速度")
    private Stream freeStream;

    /**
     * 媒资返回的md5值
     */
    @ApiModelProperty(value = "媒资返回的md5值")
    private String md5;
    @ApiModelProperty(value = "是否降码流")
    private Integer hasBelow;

    /**
     * 附属信息。 如已经向下兼容
     */
    @ApiModelProperty(value = "附属信息。 如已经向下兼容")
    private String tipMsg;

    /**
     * 播放时提示的标题
     */
    @ApiModelProperty(value = "播放时提示的标题")
    private String showName;

    /**
     * 片头时间 单位毫秒
     */
    @ApiModelProperty(value = "片头时间 单位毫秒")
    private Integer videoHeadTime;

    /**
     * 片尾时间,单位-毫秒
     */
    @ApiModelProperty(value = "片尾时间,单位-毫秒")
    private Integer videoTailTime;

    /**
     * 0不需要加水印,1需要加水印
     */
    @ApiModelProperty(value = "0不需要加水印,1需要加水印")
    private Integer needWaterMarking;

    /**
     * 播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看
     * 5:码流收费,6--Lecom会员付费，7--Lecom无版权（有终端版权，但没有定价）
     */
    @ApiModelProperty(value = "播放模式 1:正常播放 2:试看播放 3:350免费播放 4:院线试看 5:码流收费,6--Lecom会员付费，7--Lecom无版权（有终端版权，但没有定价）")
    private Integer playType;

    /**
     * 试看时长，单位-毫秒
     */
    @ApiModelProperty(value = "试看时长，单位-毫秒")
    private Long tryPlayTime;

    /**
     * 是否院线
     */
    @ApiModelProperty(value = "是否院线")
    private Long isYuanXian;
    @ApiModelProperty(value = "水印图片")
    private List<WaterMarkImage> WaterMarkImage;

    /**
     * 试看提示
     */
    @ApiModelProperty(value = "试看提示")
    private String tryPlayTipMsg;
    @ApiModelProperty(value = "drm标识id")
    private String drmFlagId;

    /**
     * DRM视频token文件地址
     */
    @ApiModelProperty(value = "DRM视频token文件地址")
    private String drmTokenUrl;

    /**
     * 声纹文件地址
     */
    @ApiModelProperty(value = "声纹文件地址")
    private String soundInkCodeUrl;

    /**
     * 剧集列表视频类型 0预告
     */
    @ApiModelProperty(value = "剧集列表视频类型 0预告")
    private Integer seriesType;

    /**
     * 单点付费
     */
    @ApiModelProperty(value = "单点付费")
    private String price;

    /**
     * 单点付费的现价
     */
    @ApiModelProperty(value = "单点付费的现价")
    private String curPrice;

    /**
     * 海报图，单点支付页面使用
     */
    @ApiModelProperty(value = "海报图，单点支付页面使用")
    private String poster;

    /**
     * 有效时长，大陆版单位-小时，Lecom版本单位-毫秒
     */
    @ApiModelProperty(value = "有效时长，大陆版单位-小时，Lecom版本单位-毫秒")
    private Integer expiredTime;

    /**
     * 付费类型 1支持单点付费
     */
    @ApiModelProperty(value = "付费类型")
    private Integer payType;

    @ApiModelProperty(value = "区域名称")
    private String areaName;
    @ApiModelProperty(value = "导演")
    private String director;

    /**
     * 是否是推荐
     */
    @ApiModelProperty(value = "是否是推荐")
    private Boolean is_rec;

    @ApiModelProperty(value = "视频播放地址集合")
    private Map<String, String> allPlayUrl;

    /**
     * 家长锁 0否，1是
     */
    @ApiModelProperty(value = "家长锁 0否，1是")
    private Integer isPlayLock;

    /**
     * 是否播放广告 0:no 1:yes
     */
    @ApiModelProperty(value = "是否播放广告 0:no 1:yes")
    private Integer playAd;

    /**
     * 是否VIP 1 是 0否
     */
    @ApiModelProperty(value = "是否VIP 1 是 0否")
    private Integer vip;

    /**
     * 非会员播放卡顿图片URL
     */
    @ApiModelProperty(value = "非会员播放卡顿图片URL")
    private String pauseImg;

    /**
     * 非会员播放卡顿默认图片URL
     */
    @ApiModelProperty(value = "非会员播放卡顿默认图片URL")
    private String pauseDefaultImg;

    /**
     * 是否展示暂停广告页面，0--不展示，1或null或其他值则展示；默认展示
     */
    @ApiModelProperty(value = "是否展示暂停广告页面，0--不展示，1或null或其他值则展示；默认展示")
    private Integer showPauseAdPage;

    /**
     * 是否展示浮层广告，null或0或其他值--都不展示，1--仅非会员展示广告，2--会员展示广告，3--全是展示
     */
    @ApiModelProperty(value = "是否展示浮层广告，null或0或其他值--都不展示，1--仅非会员展示广告，2--会员展示广告，3--全是展示")
    private Integer playFloatAd;

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
     * 视频来源网站
     */
    @ApiModelProperty(value = "视频来源网站")
    private String website;

    /**
     * 单视频通过网站进行播放的播放地址
     */
    @ApiModelProperty(value = "单视频通过网站进行播放的播放地址")
    private String webPlayUrl;

    /**
     * 字幕
     */
    @ApiModelProperty(value = "字幕")
    private List<PromptDto> subTitle;

    /**
     * 音轨列表
     */
    @ApiModelProperty(value = "音轨列表")
    private List<AudioTrackDto> audioTrackList;

    /**
     * 默认音轨
     */
    @ApiModelProperty(value = "默认音轨")
    private AudioTrackDto defaultAudioTrack;

    /**
     * 默认字幕
     */
    @ApiModelProperty(value = "默认字幕")
    private PromptDto defaultSubTitle;

    /**
     * 播放结束是否需要推荐短视频标志位，0--不需要，1--需要
     */
    @ApiModelProperty(value = "播放结束是否需要推荐短视频标志位，0--不需要，1--需要")
    private Integer needRecommendForEnd;

    /**
     * （大陆版需求）是否展示成综艺样式（或，Lecon需求--是否按“剧集”展示）；1是
     * 0否,媒资“是否综艺样式展示”进针对专辑维度设置，故这里的字段，一般继承自视频所属专辑，或特殊业务下赋值
     */
    @ApiModelProperty(value = "是否展示成综艺样式")
    private Integer varietyShow;

    /**
     * 栏目Id（多音轨多字幕）
     */
    @ApiModelProperty(value = "栏目Id（多音轨多字幕）")
    private String channelId;

    @ApiModelProperty(value = "图片地址1, pic1，pic2，pic3，pic4组成儿童答题答案")
    private String pic1;

    /**
     * @return
     */
    @ApiModelProperty(value = "是否弹幕")
    private Integer isDanmaku;

    /**
     * 投屏播放中兼容领先版业务，当前视频在所属专辑的剧集列表数据中的分页信息
     */
    @ApiModelProperty(value = "投屏播放中兼容领先版业务，当前视频在所属专辑的剧集列表数据中的分页信息")
    private Integer pageNum;

    @ApiModelProperty(value = "专辑版权信息，0/null--无版权，1--有版权")
    private Integer albumTvCopyright;// 专辑版权信息，0/null--无版权，1--有版权

    /**
     * 对于包含会员权益token信息的播放请求失效时长，单位-豪秒，0--永久有效，>0--固定秒数内有效，其他值--非法数据，默认永久有效
     */
    @ApiModelProperty(value = "对于包含会员权益token信息的播放请求失效时长，单位-豪秒，0--永久有效，>0--固定秒数内有效，其他值--非法数据，默认永久有效")
    private Long tokenExpireTime;

    @ApiModelProperty(value = "用户是否被封禁，0/null--没有，1--被封禁")
    private Integer userIsForbidden;// 用户是否被封禁，0/null--没有，1--被封禁

    @ApiModelProperty(value = "用户被封禁时的提示语")
    private String tipsMessage;// 用户被封禁时的提示语

    @ApiModelProperty(value = "二级分类集合")
    private Map<String, String> subCategory;

    @ApiModelProperty(value = "二级分类id")
    private String subCategoryId;

    // sarrs项目扩展字段
    @ApiModelProperty(value = "sarrs项目扩展字段")
    private String globalId;// sarrs globalId

    @ApiModelProperty(value = "媒资Id")
    private String mid;// 媒资Id

    /**
     * 第三方内容id
     */
    @ApiModelProperty(value = "第三方内容id")
    private String external_id;

    @ApiModelProperty(value = "全网分类id")
    private String globalCid;// 全网分类

    @ApiModelProperty(value = "数据源")
    private String source;// 数据源

    /**
     * 是否通过CDE播放
     */
    @ApiModelProperty(value = "是否通过CDE播放")
    private Boolean isThroughCDE;

    /**
     * 桌面打洞兼容id
     */
    @ApiModelProperty(value = "桌面打洞兼容id")
    private String id;

    @ApiModelProperty(value = "角标类型")
    private String iconType;

    /**
     * 美国Le.com需求，未登陆或鉴权不过时返回当前视频所属套餐包信息
     */
    @ApiModelProperty(value = "美国Le.com需求，未登陆或鉴权不过时返回当前视频所属套餐包信息")
    private List<PackageInfoDto> packageInfo;

    /**
     * 2016-08-22美国Le.com新增需求，在img基础上，再出一张1440*810大图，用于背景展示
     */
    @ApiModelProperty(value = "*新增需求，在img基础上，再出一张大图，用于背景展示；取同专辑详情页中大背景图相同的图，便于客户端减少网络请求")
    private String bigImg;

    /**
     * 内容分级id
     */
    @ApiModelProperty(value = "内容分级id")
    private String contentRatingId;

    /**
     * 内容分级描述
     */
    @ApiModelProperty(value = "内容码流值")
    private String contentRatingValue;

    /**
     * 内容分级描述
     */
    @ApiModelProperty(value = "内容分级描述")
    private String contentRatingDesc;

    /**
     * 是否支持试用 1:是 0:否
     */
    @ApiModelProperty(value = "是否支持试用 1:是 0:否")
    private String isCanTial;

    /**
     * Cooperation platform，合作平台id列表，多个id使用英文逗号","拼接
     */
    @ApiModelProperty(value = "合作平台id列表，多个id使用英文逗号\",\"拼接")
    private String coopPlatform;

    /**
     * 视频介质播放形式，目前特殊码流（3D系列、杜比系列）使用Mp4流播放，其他码流使用m3u8调度播放，目前仅返回“mediatype=mp4”和“
     * ext=m3u8”，客户端将其传递给CDE
     */
    @ApiModelProperty(value = "视频介质播放形式，目前特殊码流（3D系列、杜比系列）使用Mp4流播放，其他码流使用m3u8调度播放，目前仅返回“mediatype=mp4”和“ext=m3u8”，客户端将其传递给CDE")
    private String playMediaFormat;

    /**
     * 儿童锁信息
     */
    @ApiModelProperty(value = "儿童锁信息")
    private ChildLockDto childLock;

    /**
     * pic1，pic2，pic3，pic4组成儿童答题答案
     */
    @ApiModelProperty(value = "图片地址2")
    private String pic2;
    @ApiModelProperty(value = "图片地址3")
    private String pic3;
    @ApiModelProperty(value = "图片地址4")
    private String pic4;

    /**
     * 专辑是否正片,true-正片；false-非正片；2016-11-16lecom需求添加，播放下拉列表要做“综艺样式展示”需求，
     * 需要在播放接口获取专辑相关信息
     */
    @ApiModelProperty(value = "专辑是否正片,true-正片；false-非正片；2016-11-16lecom需求添加，播放下拉列表要做“综艺样式展示”需求，需要在播放接口获取专辑相关信息")
    private Boolean albumPositive;

    /**
     * 鉴权成功后，专辑、视频单点时剩余的可使用截止时间
     */
    @ApiModelProperty(value = "鉴权成功后，专辑、视频单点时剩余的可使用截止时间")
    private Long playEndTime;

    /**
     * 分端付费参数集合
     */
    @ApiModelProperty(value = "分端付费参数集合")
    private List<ChargeInfoDto> chargeInfos;

    @ApiModelProperty(value = "［调试］参数签名")
    private String urlSign;

    /**
     * 连播时cms的预告片信息
     */
    @ApiModelProperty(value = "连播时cms的预告片信息")
    private TrailerVideoDto trailer;

    @ApiModelProperty(value = "*浮动介绍")
    private String title;

    @ApiModelProperty(value = "广告VIP标识: 0-非会员，1-会员")
    private Integer isAdVip;

    public TrailerVideoDto getTrailer() {
        return trailer;
    }

    public void setTrailer(TrailerVideoDto trailer) {
        this.trailer = trailer;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Long getPlayEndTime() {
        return playEndTime;
    }

    public void setPlayEndTime(Long playEndTime) {
        this.playEndTime = playEndTime;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getIsCanTial() {
        return isCanTial;
    }

    public void setIsCanTial(String isCanTial) {
        this.isCanTial = isCanTial;
    }

    public String getContentRatingId() {
        return contentRatingId;
    }

    public void setContentRatingId(String contentRatingId) {
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

    public String getGlobalCid() {
        return globalCid;
    }

    public void setGlobalCid(String globalCid) {
        this.globalCid = globalCid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public Map<String, String> getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(Map<String, String> subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryId() {
        return this.subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Integer getIsDanmaku() {
        return this.isDanmaku;
    }

    public void setIsDanmaku(Integer isDanmaku) {
        this.isDanmaku = isDanmaku;
    }

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public Integer getVip() {
        return this.vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public Boolean getIs_rec() {
        return this.is_rec;
    }

    public void setIs_rec(Boolean is_rec) {
        this.is_rec = is_rec;
    }

    public String getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
    }

    public String getPayPlatForm() {
        return this.payPlatForm;
    }

    public void setPayPlatForm(String payPlatForm) {
        this.payPlatForm = payPlatForm;
    }

    public Stream getFreeStream() {
        return this.freeStream;
    }

    public void setFreeStream(Stream freeStream) {
        this.freeStream = freeStream;
    }

    @Override
    public int getDataType() {
        return DataConstant.DATA_TYPE_VIDEO;
    }

    public String getVideoId() {
        return this.videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Boolean getPositive() {
        return this.positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public Integer getVideoTypeId() {
        return this.videoTypeId;
    }

    public void setVideoTypeId(Integer videoTypeId) {
        this.videoTypeId = videoTypeId;
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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
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

    public String getStorePath() {
        return this.storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public String getPlayUrl() {
        return this.playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getBackUrl0() {
        return this.backUrl0;
    }

    public void setBackUrl0(String backUrl0) {
        this.backUrl0 = backUrl0;
    }

    public String getBackUrl1() {
        return this.backUrl1;
    }

    public void setBackUrl1(String backUrl1) {
        this.backUrl1 = backUrl1;
    }

    public String getBackUrl2() {
        return this.backUrl2;
    }

    public void setBackUrl2(String backUrl2) {
        this.backUrl2 = backUrl2;
    }

    public Long getGsize() {
        return this.gsize;
    }

    public void setGsize(Long gsize) {
        this.gsize = gsize;
    }

    public String getCurrentStream() {
        return this.currentStream;
    }

    public void setCurrentStream(String currentStream) {
        this.currentStream = currentStream;
    }

    public String getMd5() {
        return this.md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getHasBelow() {
        return this.hasBelow;
    }

    public void setHasBelow(Integer hasBelow) {
        this.hasBelow = hasBelow;
    }

    public String getTipMsg() {
        return this.tipMsg;
    }

    public void setTipMsg(String tipMsg) {
        this.tipMsg = tipMsg;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public Integer getVideoHeadTime() {
        return this.videoHeadTime;
    }

    public void setVideoHeadTime(Integer videoHeadTime) {
        this.videoHeadTime = videoHeadTime;
    }

    public Integer getVideoTailTime() {
        return this.videoTailTime;
    }

    public void setVideoTailTime(Integer videoTailTime) {
        this.videoTailTime = videoTailTime;
    }

    public Integer getNeedWaterMarking() {
        return this.needWaterMarking;
    }

    public void setNeedWaterMarking(Integer needWaterMarking) {
        this.needWaterMarking = needWaterMarking;
    }

    public Integer getPlayType() {
        return this.playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Long getTryPlayTime() {
        return this.tryPlayTime;
    }

    public void setTryPlayTime(Long tryPlayTime) {
        this.tryPlayTime = tryPlayTime;
    }

    public Long getIsYuanXian() {
        return this.isYuanXian;
    }

    public void setIsYuanXian(Long isYuanXian) {
        this.isYuanXian = isYuanXian;
    }

    public List<WaterMarkImage> getWaterMarkImage() {
        return this.WaterMarkImage;
    }

    public void setWaterMarkImage(List<WaterMarkImage> waterMarkImage) {
        this.WaterMarkImage = waterMarkImage;
    }

    public String getTryPlayTipMsg() {
        return this.tryPlayTipMsg;
    }

    public void setTryPlayTipMsg(String tryPlayTipMsg) {
        this.tryPlayTipMsg = tryPlayTipMsg;
    }

    public String getDrmFlagId() {
        return this.drmFlagId;
    }

    public void setDrmFlagId(String drmFlagId) {
        this.drmFlagId = drmFlagId;
    }

    public String getDrmTokenUrl() {
        return this.drmTokenUrl;
    }

    public void setDrmTokenUrl(String drmTokenUrl) {
        this.drmTokenUrl = drmTokenUrl;
    }

    public String getEpisode() {
        return this.episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public Integer getOrderInAlbum() {
        return this.orderInAlbum;
    }

    public void setOrderInAlbum(Integer orderInAlbum) {
        this.orderInAlbum = orderInAlbum;
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

    public String getCurrentStreamKps() {
        return this.currentStreamKps;
    }

    public void setCurrentStreamKps(String currentStreamKps) {
        this.currentStreamKps = currentStreamKps;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getVv() {
        return this.vv;
    }

    public void setVv(String vv) {
        this.vv = vv;
    }

    public String getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(String playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getSoundInkCodeUrl() {
        return this.soundInkCodeUrl;
    }

    public void setSoundInkCodeUrl(String soundInkCodeUrl) {
        this.soundInkCodeUrl = soundInkCodeUrl;
    }

    public Integer getSeriesType() {
        return this.seriesType;
    }

    public void setSeriesType(Integer seriesType) {
        this.seriesType = seriesType;
    }

    public Integer getPreType() {
        return this.preType;
    }

    public void setPreType(Integer preType) {
        this.preType = preType;
    }

    @Override
    public int compareTo(Object o) {
        VideoDto video = (VideoDto) o;

        if (this.varietyShow != null && this.varietyShow == 1) {// 若展示成综艺样式，倒序排列
            if (this.orderInAlbum != null && this.orderInAlbum < video.getOrderInAlbum()) {
                return 1;
            } else {
                return -1;
            }
        }

        if (this.categoryId != null
                && (this.categoryId == VideoConstants.Category.TV || this.categoryId == VideoConstants.Category.CARTOON || this.categoryId == VideoConstants.Category.TEACH)
                && this.orderInAlbum != null && this.orderInAlbum < video.getOrderInAlbum()) {
            return -1;
        } else {
            return 1;
        }
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurPrice() {
        return curPrice;
    }

    public void setCurPrice(String curPrice) {
        this.curPrice = curPrice;
    }

    public String getPoster() {
        return this.poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getExpiredTime() {
        return this.expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getPayType() {
        return this.payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Map<String, String> getAllPlayUrl() {
        return this.allPlayUrl;
    }

    public void setAllPlayUrl(Map<String, String> allPlayUrl) {
        this.allPlayUrl = allPlayUrl;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
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

    public String getCurrentStreamOriginal() {
        return this.currentStreamOriginal;
    }

    public void setCurrentStreamOriginal(String currentStreamOriginal) {
        this.currentStreamOriginal = currentStreamOriginal;
    }

    public Integer getVtype() {
        return this.vtype;
    }

    public void setVtype(Integer vtype) {
        this.vtype = vtype;
    }

    public Integer getPlayAd() {
        return this.playAd;
    }

    public void setPlayAd(Integer playAd) {
        this.playAd = playAd;
    }

    public String getPauseImg() {
        return this.pauseImg;
    }

    public void setPauseImg(String pauseImg) {
        this.pauseImg = pauseImg;
    }

    public String getPauseDefaultImg() {
        return this.pauseDefaultImg;
    }

    public void setPauseDefaultImg(String pauseDefaultImg) {
        this.pauseDefaultImg = pauseDefaultImg;
    }

    public Integer getShowPauseAdPage() {
        return this.showPauseAdPage;
    }

    public void setShowPauseAdPage(Integer showPauseAdPage) {
        this.showPauseAdPage = showPauseAdPage;
    }

    public Integer getPlayFloatAd() {
        return this.playFloatAd;
    }

    public void setPlayFloatAd(Integer playFloatAd) {
        this.playFloatAd = playFloatAd;
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

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebPlayUrl() {
        return this.webPlayUrl;
    }

    public void setWebPlayUrl(String webPlayUrl) {
        this.webPlayUrl = webPlayUrl;
    }

    public List<PromptDto> getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(List<PromptDto> subTitle) {
        this.subTitle = subTitle;
    }

    public List<AudioTrackDto> getAudioTrackList() {
        return this.audioTrackList;
    }

    public void setAudioTrackList(List<AudioTrackDto> audioTrackList) {
        this.audioTrackList = audioTrackList;
    }

    public AudioTrackDto getDefaultAudioTrack() {
        return this.defaultAudioTrack;
    }

    public void setDefaultAudioTrack(AudioTrackDto defaultAudioTrack) {
        this.defaultAudioTrack = defaultAudioTrack;
    }

    public PromptDto getDefaultSubTitle() {
        return this.defaultSubTitle;
    }

    public void setDefaultSubTitle(PromptDto defaultSubTitle) {
        this.defaultSubTitle = defaultSubTitle;
    }

    public Integer getNeedRecommendForEnd() {
        return this.needRecommendForEnd;
    }

    public void setNeedRecommendForEnd(Integer needRecommendForEnd) {
        this.needRecommendForEnd = needRecommendForEnd;
    }

    public Integer getVarietyShow() {
        return this.varietyShow;
    }

    public void setVarietyShow(Integer varietyShow) {
        this.varietyShow = varietyShow;
    }

    /**
     * 字幕对象信息封装类
     * @author liyunlong
     */
    @ApiModel(value = "PromptDto", description = "字幕对象信息封装类")
    public static class PromptDto {

        /**
         * 字幕类型
         */
        @ApiModelProperty(value = "字幕类型")
        private String subtitleType;
        /**
         * 字幕名称
         */
        @ApiModelProperty(value = "字幕名称")
        private String name;

        /**
         * 字幕地址
         */
        @ApiModelProperty(value = "字幕地址")
        private String url;

        public String getSubtitleType() {
            return this.subtitleType;
        }

        public void setSubtitleType(String subtitleType) {
            this.subtitleType = subtitleType;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    /**
     * 音轨信息封装类
     * @author liyunlong
     */
    @ApiModel(value = "AudioTrackDto", description = "音轨信息封装类")
    public static class AudioTrackDto {
        /**
         * 语种类型
         */
        @ApiModelProperty(value = "语种类型")
        private String langType;

        /**
         * 语种类型名称
         */
        @ApiModelProperty(value = "语种类型名称")
        private String langName;

        /**
         * 音质类型
         */
        @ApiModelProperty(value = "音质类型")
        private String kbpsType;

        /**
         * 码率名称
         */
        @ApiModelProperty(value = "码率名称")
        private String kbpsName;

        /**
         * 音轨id
         */
        @ApiModelProperty(value = "音轨id")
        private String audioId;

        /**
         * 音轨名称
         */
        @ApiModelProperty(value = "音轨名称")
        private String audioName;

        /**
         * 音质列表，一个语种，多个音质，所以一个语种中存放一个音质列表
         */
        @ApiModelProperty(value = "音质列表，一个语种，多个音质，所以一个语种中存放一个音质列表")
        private List<KbpsDto> kbpsTypeList;

        public String getLangType() {
            return this.langType;
        }

        public void setLangType(String langType) {
            this.langType = langType;
        }

        public String getLangName() {
            return this.langName;
        }

        public void setLangName(String langName) {
            this.langName = langName;
        }

        public String getKbpsType() {
            return this.kbpsType;
        }

        public void setKbpsType(String kbpsType) {
            this.kbpsType = kbpsType;
        }

        public String getKbpsName() {
            return this.kbpsName;
        }

        public void setKbpsName(String kbpsName) {
            this.kbpsName = kbpsName;
        }

        public String getAudioId() {
            return this.audioId;
        }

        public void setAudioId(String audioId) {
            this.audioId = audioId;
        }

        public String getAudioName() {
            return this.audioName;
        }

        public void setAudioName(String audioName) {
            this.audioName = audioName;
        }

        public List<KbpsDto> getKbpsTypeList() {
            return this.kbpsTypeList;
        }

        public void setKbpsTypeList(List<KbpsDto> kbpsTypeList) {
            this.kbpsTypeList = kbpsTypeList;
        }

        @ApiModel(value = "KbpsDto", description = "音质信息封装类")
        public static class KbpsDto extends BaseDto {
            /**
             * 音质类型
             */
            @ApiModelProperty(value = "音质类型")
            private String kpbsType;

            /**
             * 码率名称
             */
            @ApiModelProperty(value = "码率名称")
            private String kpbsName;

            /**
             * 音轨id
             */
            @ApiModelProperty(value = "音轨id")
            private String audioId;

            /**
             * 音轨名称
             */
            @ApiModelProperty(value = "音轨名称")
            private String audioName;

            public String getKpbsType() {
                return this.kpbsType;
            }

            public void setKpbsType(String kpbsType) {
                this.kpbsType = kpbsType;
            }

            public String getKpbsName() {
                return this.kpbsName;
            }

            public void setKpbsName(String kpbsName) {
                this.kpbsName = kpbsName;
            }

            public String getAudioId() {
                return this.audioId;
            }

            public void setAudioId(String audioId) {
                this.audioId = audioId;
            }

            public String getAudioName() {
                return this.audioName;
            }

            public void setAudioName(String audioName) {
                this.audioName = audioName;
            }
        }
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Long getTokenExpireTime() {
        return this.tokenExpireTime;
    }

    public void setTokenExpireTime(Long tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public Integer getAlbumTvCopyright() {
        return this.albumTvCopyright;
    }

    public void setAlbumTvCopyright(Integer albumTvCopyright) {
        this.albumTvCopyright = albumTvCopyright;
    }

    public Integer getUserIsForbidden() {
        return this.userIsForbidden;
    }

    public void setUserIsForbidden(Integer userIsForbidden) {
        this.userIsForbidden = userIsForbidden;
    }

    public String getTipsMessage() {
        return this.tipsMessage;
    }

    public void setTipsMessage(String tipsMessage) {
        this.tipsMessage = tipsMessage;
    }

    public Boolean getIsThroughCDE() {
        return this.isThroughCDE;
    }

    public void setIsThroughCDE(Boolean isThroughCDE) {
        this.isThroughCDE = isThroughCDE;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public List<PackageInfoDto> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<PackageInfoDto> packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public String getPlaylogImg() {
        return playlogImg;
    }

    public void setPlaylogImg(String playlogImg) {
        this.playlogImg = playlogImg;
    }

    public String getCoopPlatform() {
        return coopPlatform;
    }

    public void setCoopPlatform(String coopPlatform) {
        this.coopPlatform = coopPlatform;
    }

    public ChildLockDto getChildLock() {
        return childLock;
    }

    public void setChildLock(ChildLockDto childLock) {
        this.childLock = childLock;
    }

    public Boolean getAlbumPositive() {
        return albumPositive;
    }

    public void setAlbumPositive(Boolean albumPositive) {
        this.albumPositive = albumPositive;
    }

    public String getPlayMediaFormat() {
        return playMediaFormat;
    }

    public void setPlayMediaFormat(String playMediaFormat) {
        this.playMediaFormat = playMediaFormat;
    }

    public List<ChargeInfoDto> getChargeInfos() {
        return chargeInfos;
    }

    public void setChargeInfos(List<ChargeInfoDto> chargeInfos) {
        this.chargeInfos = chargeInfos;
    }

    public String getUrlSign() {
        return urlSign;
    }

    public void setUrlSign(String urlSign) {
        this.urlSign = urlSign;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIsAdVip() {
        return isAdVip;
    }

    public void setIsAdVip(Integer isAdVip) {
        this.isAdVip = isAdVip;
    }
}
