package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.PlayLogTp.PlayLogInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants.Category;
import com.letv.mas.caller.iptv.tvproxy.video.service.VideoService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import serving.AlbumAttribute;
import serving.ResultDocInfo;
import serving.VideoAttribute;
import serving.VideoAttributeInAlbum;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 播放记录列表dto
 * @author dunhongqin
 */
@ApiModel(value = "PlayLogListDto", description = "播放记录列表dto")
public class PlayLogListDto extends BaseDto {
    private static final long serialVersionUID = 4172238395759432185L;

    public static final int TRY_LOOK = 0;
    public static final int TRY_LOOK_COMPLETE = 1;

    @ApiModelProperty(value = "专辑名称")
    private String albumName;

    @ApiModelProperty(value = "视频名称")
    private String videoName;

    @ApiModelProperty(value = "专辑id")
    private Long pid;

    @ApiModelProperty(value = "TV2.5重构之后,返回专辑id")
    private Long iptvAlbumId;

    @ApiModelProperty(value = "TV2.5重构之后,返回视频id")
    private Long videoInfoId;

    @ApiModelProperty(value = "下一集视频id")
    private Long nextVideoInfoId;

    @ApiModelProperty(value = "评分显示 得分/人数")
    private String fraction;

    @ApiModelProperty(value = "评分")
    private Float rating;

    @ApiModelProperty(value = "评分人数")
    private Integer votes;

    @ApiModelProperty(value = "导演")
    private String director = "";

    @ApiModelProperty(value = "主演")
    private String actor = "";

    @ApiModelProperty(value = "地区")
    private String area = "";

    @ApiModelProperty(value = "发布日期")
    private String releaseDate;

    @ApiModelProperty(value = "描述")
    private String description = "";

    @ApiModelProperty(value = "第几集")
    private Integer seriesNum;

    @ApiModelProperty(value = "上次播放时间点")
    private Long playTime;

    @ApiModelProperty(value = "最后更新时间")
    private String lastTime;

    @ApiModelProperty(value = "今天，上周或更久以前")
    private Integer playTimeType;

    /**
     * 试看状态种类：
     * 试看中：0
     * 试看完：1
     */
    @ApiModelProperty(value = "试看状态种类：试看中-0，试看完-1")
    private Integer tryPlayType;

    @ApiModelProperty(value = "用户试看时长")
    private Long tryPlayTime;

    public Long getTryPlayTime() {
        return tryPlayTime;
    }

    public void setTryPlayTime(Long tryPlayTime) {
        this.tryPlayTime = tryPlayTime;
    }

    public Integer getTryPlayType() {
        return tryPlayType;
    }

    public void setTryPlayType(Integer tryPlayType) {
        this.tryPlayType = tryPlayType;
    }

    /**
     * 付费，角标信息
     */
    private ChargeInfoDto chargeInfo;

    /**
     * 最后更新时间，单位-毫秒
     */
    private Long lastUpdateTime;
    private Long duration;// 总时长
    private String imgPic;// 默认缩略图
    private String img_400X300;// 400X300图
    private String img_200X150;// 200X150图
    private String img_150X200;// 150x200图
    private String img_300X400;// 300x400图
    private String img_400X225;// 400X225图
    private String img_960X540;// 960x540图
    private String img_180X101;// 180x101图
    private String from;// 来源
    private Boolean isCopyright;// 是否在TV版有版权
    private Boolean isEnd;// 是否播放完毕
    private Integer follownum;// 跟播到第几集
    private Integer episodes;// 总集数
    private Integer videoType;// 视频类型{1：单视频(电影)，2：多视频(连续剧、动漫、综艺)，0：其他}
    private Integer categoryId; // 视频类别id，TV2.5重构后，与newCategoryId意义一致,兼容客户端
    private Integer newCategoryId; // 视频类别id
    private String url; // PC版播放地址
    private int tv_out; // TV版外跳 0:TV版 1:外跳TV版
    private Integer source; // 播放记录来源 1："网页", 2:"手机客户端", 3:"平板",
                            // 4:"电视",5:"桌面客户端"
    private Long nvid;// 下一集ID
    private String videoStatus;// 更新信息，针对电视剧、卡通、综艺类型的视频
    private Long roleid = Long.valueOf(0);// 客户端要求返回roleid,正常模式为0;
    private String subtitle;// 副标题
    private Integer albumTvCopyright;// 专辑版权信息，0/null--无版权，1--有版权

    private String aGlobalId; // 专辑globalId
    private String vGlobalId; // 视频globalId
    private String externalId; // 视频externalId
    private String mid; // 媒资id
    private Integer isPay; // 是否付费
    private String sourceType; // 来源, "youtube" etc.

    /**
     * 乐听项目需要用到的字段
     */
    private String radioId; // 电台id
    private String globalId; // 作品庫globalId
    private String radioName; // 电台名
    private String radioStartTime;// 节目开始时间，格式为"HH:mm"
    private String radioEndTime; // 节目结束时间，格式为"HH:mm"

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ChargeInfoDto getChargeInfo() {
        return chargeInfo;
    }

    public void setChargeInfo(ChargeInfoDto chargeInfo) {
        this.chargeInfo = chargeInfo;
    }

    public PlayLogListDto() {
    }

    /**
     * @param album
     *            专辑信息
     * @param video
     *            视频信息
     * @param playLog
     *            第三方接口返回的播放记录
     * @param isTV
     *            是否有TV播放版权
     */
    public PlayLogListDto(AlbumMysqlTable album, VideoMysqlTable video, PlayLogInfo playLog, boolean isTV,
                          CommonParam commonParam) {
        this.categoryId = 0;
        this.newCategoryId = 0;

        if (StringUtils.isNotBlank(playLog.getCid())) {
            this.categoryId = Integer.parseInt(playLog.getCid());
            this.newCategoryId = this.categoryId;
        }
        if (album != null) {
            this.pid = album.getId();
            this.iptvAlbumId = album.getId();
            this.tv_out = (album.getPushflag() != null && album.getPushflag() == 1) ? 0 : 1;
            this.img_200X150 = getAvailableImg(album, video, 200, 150);
            this.img_400X300 = getAvailableImg(album, video, 400, 300);
            this.img_150X200 = getAvailableImg(album, video, 150, 200);
            this.img_300X400 = getAvailableImg(album, video, 300, 400);
            this.img_400X225 = getAvailableImg(album, video, 400, 225);
            this.img_960X540 = getAvailableImg(album, video, 960, 540);
            this.img_180X101 = getAvailableImg(album, video, 180, 101);
            this.episodes = album.getEpisode();
            this.follownum = album.getFollownum();
            this.albumName = album.getName_cn();
            this.isCopyright = isTV;
            this.albumTvCopyright = 1;
            this.setChargeInfo(JumpUtil.genChargeInfoDto(commonParam.getP_devType(), album.getPay_platform()));
        } else {
            if ("258".compareTo(commonParam.getAppCode()) <= 0) {
                // 2.9.8版本之前，还是过滤专辑id，2.9.8版本之后，把pid都放开，用另一字段标识专辑是否有版权
                this.pid = playLog.getPid();
                this.iptvAlbumId = playLog.getPid();// client use this field
                this.albumTvCopyright = 0;
            }
        }
        this.isEnd = playLog.getIsend() != null && playLog.getIsend() == 1;

        if (this.newCategoryId == Category.FILM) {
            this.videoType = 1;
            this.follownum = 1;
        } else if (this.newCategoryId == Category.TV || this.newCategoryId == Category.CARTOON
                || this.newCategoryId == Category.VARIETY) {
            this.videoType = 2;
        } else {
            this.videoType = 0;
        }

        Integer nc = playLog.getNc();
        if (nc != null) {
            if (nc == 0) {
                nc = 1;
            }
            this.seriesNum = nc;
        }
        this.videoInfoId = playLog.getVid();
        this.videoName = playLog.getTitle();
        // 默认图处理（长视频显示专辑图片、短视频或者无专辑视频优先调用视频图，无图则调用用户中心）
        if (this.newCategoryId == Category.TV || this.newCategoryId == Category.FILM
                || this.newCategoryId == Category.PARENTING || this.newCategoryId == Category.DFILM
                || this.newCategoryId == Category.CARTOON || this.newCategoryId == Category.VARIETY) {
            this.imgPic = getAvailableImg(album, video, 400, 300);
        } else {
            this.imgPic = getAvailableImg(video, 400, 300);
        }
        // 纪录片 有专辑属性展示视频名
        if (this.newCategoryId == Category.DFILM && album != null) {
            this.subtitle = playLog.getTitle();
        }
        Long htime = playLog.getHtime();
        if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
            // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
            this.playTime = -1000L;
        } else {
            this.playTime = htime * 1000L;
        }

        this.duration = playLog.getVtime() * 1000L;
        this.from = this.getPlayLogFrom(playLog.getFrom());
        this.source = playLog.getFrom();
        if (playLog.getUtime() != null) {
            if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam.getTerminalApplication())) { // le.com传时间戳
                this.lastTime = String.valueOf(playLog.getUtime());
            } else {
                this.lastTime = TimeUtil
                        .getDateString(new Date(playLog.getUtime() * 1000), TimeUtil.SIMPLE_DATE_FORMAT);
            }
        }
        if (playLog.getUtime() != null) {
            this.lastUpdateTime = playLog.getUtime() * 1000;
        }

        // 2015-07-29添加一个过滤，过滤掉综艺的下一集id
        this.nvid = 0L;// 默认为0，表示没有下一集
        if (this.videoType == 2) {// videoType=2时，表示是电视剧，动漫或者综艺类型
            if (this.newCategoryId == Category.TV || this.newCategoryId == Category.CARTOON
                    || this.newCategoryId == Category.VARIETY) {
                // 只有是电视剧和卡通，才返回下一集id(2015-08-26，如果当前剧集数大于等于最新剧集数，就不返回下一集id)
                if (this.follownum != null && this.seriesNum != null
                        && this.seriesNum.intValue() < this.follownum.intValue()) {
                    this.nvid = playLog.getNvid();
                }
            }
        }
        this.url = DataConstant.PC_PLAY_URL + this.videoInfoId + ".html";

    }

    public PlayLogListDto(PlayLogInfo playLog, ResultDocInfo docInfo, AlbumAttribute albumAttribute,
            VideoAttributeInAlbum videoAttributeInAlbum, CommonParam commonParam) {
        if (docInfo != null && StringUtils.isNotBlank(docInfo.getCategory())) {
            this.categoryId = StringUtil.toInteger(docInfo.getCategory());
            this.newCategoryId = this.categoryId;
            this.aGlobalId = docInfo.getLetv_original_id();
            this.pid = Long.valueOf(docInfo.getId());
            this.iptvAlbumId = pid;
        }
        if (albumAttribute != null) {
            String playPlatform = VideoService.getSarrsPlayPlatForm(albumAttribute.getPush_flag());
            this.tv_out = (playPlatform != null && playPlatform.contains("420007")) ? 0 : 1;
            this.episodes = (int) albumAttribute.getEpisodes();
            this.albumName = albumAttribute.getName();
            this.imgPic = this.getPic(albumAttribute.getImages(), 640, 360);
            this.sourceType = albumAttribute.getSub_src();
        }

        if (videoAttributeInAlbum != null) {
            this.videoInfoId = StringUtil.toLong(videoAttributeInAlbum.getVid());
            this.videoName = videoAttributeInAlbum.getName();
            this.vGlobalId = videoAttributeInAlbum.getLetv_original_id();
            this.externalId = videoAttributeInAlbum.getExternal_id();
            this.mid = videoAttributeInAlbum.getMid();
            Short is_pay = videoAttributeInAlbum.getIs_pay();
            if (is_pay != null) {
                this.setIsPay(is_pay.intValue());
            }
            if (StringUtil.isNotBlank(videoAttributeInAlbum.getDuration())) {
                this.duration = StringUtil.toLong(videoAttributeInAlbum.getDuration() + "000", 0L);
            }
        }

        if (this.newCategoryId != null) {
            if (this.newCategoryId == Category.FILM) {
                this.videoType = 1;
            } else if (this.newCategoryId == Category.TV || this.newCategoryId == Category.CARTOON
                    || this.newCategoryId == Category.VARIETY) {
                this.videoType = 2;
            } else {
                this.videoType = 0;
            }
        } else {
            this.videoType = 0;
        }

        if (playLog != null) {
            Long htime = playLog.getHtime();
            if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
                // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
                this.playTime = -1000L;
            } else {
                this.playTime = htime * 1000L;
            }

            if (playLog.getUtime() != null) { // 上传时间
                this.lastTime = String.valueOf(playLog.getUtime());
            }
            if (playLog.getIsend() != null) {
                this.isEnd = playLog.getIsend() == 0 ? false : true;
            }
        }
        this.from = String.valueOf(UserTpConstant.PLAY_LOG_FROM.TV.getCode());
        this.nvid = 0L;// 默认为0，表示没有下一集
        this.url = DataConstant.PC_PLAY_URL + this.videoInfoId + ".html";
    }

    /**
     * 获取作品库播放记录图片
     * @param img
     *            图片集合
     * @param width
     *            图片宽
     * @param height
     *            图片高
     * @return
     */
    public String getPic(Map<String, String> img, Integer width, Integer height) {
        if (img == null) {
            return null;
        }
        String pic = img.get(width + "*" + height);
        if (StringUtil.isBlank(pic)) {
            String cmsImages = img.get("cmsImages"); // 图片有可能放在cmsImages里.类型为cmsImages:String[]
            if (StringUtil.isNotBlank(cmsImages)) {
                List<Map<String, String>> cmsImageMaps = JsonUtil.parse(cmsImages,
                        new LetvTypeReference<List<Map<String, String>>>() {
                        });
                if (cmsImageMaps != null && cmsImageMaps.size() > 0) {
                    for (Map<String, String> cmsImageMap : cmsImageMaps) {
                        pic = cmsImageMap.get(width + "*" + height);
                        if (StringUtil.isNotBlank(pic)) {
                            break;
                        }
                    }
                }
            }
        }
        return pic;
    }

    public PlayLogListDto(PlayLogInfo playLog, ResultDocInfo docInfo, VideoAttribute videoAttribute,
            CommonParam commonParam) {
        if (docInfo != null) {
            this.vGlobalId = docInfo.getLetv_original_id();
            this.categoryId = StringUtil.toInteger(docInfo.getCategory());
            this.newCategoryId = this.categoryId;
        }
        if (videoAttribute != null) {
            this.videoInfoId = StringUtil.toLong(videoAttribute.getVid());
            this.duration = StringUtil.toLong(videoAttribute.getDuration() + "000", 0L);
            this.externalId = videoAttribute.getExternal_id();
            this.videoName = videoAttribute.getName();
            this.tv_out = 0; // 详情借接口数据不全， 暂时写死为0
            this.mid = videoAttribute.getMid();
            this.episodes = videoAttribute.getEpisodes();
            this.aGlobalId = videoAttribute.getAlbum_original_id();
            this.pid = StringUtil.toLong(videoAttribute.getAid());
            this.iptvAlbumId = StringUtil.toLong(videoAttribute.getAid());
            this.sourceType = videoAttribute.getSource();
        }

        this.nvid = 0L;// 默认为0，表示没有下一集
        this.url = DataConstant.PC_PLAY_URL + this.videoInfoId + ".html";
        if ("youtube".equals(videoAttribute.getSource())) {
            if (videoAttribute.getImages() != null) {
                String pic = videoAttribute.getImages().get("1280*720");// youtube内容取1280*720尺寸的图片
                if (pic == null) {
                    pic = videoAttribute.getImages().get("320*180");// 取不到再取320*180尺寸的图片
                }
                this.imgPic = pic;
            }
        }
        if (this.newCategoryId != null) {
            if (this.newCategoryId == Category.FILM) {
                this.videoType = 1;
            } else if (this.newCategoryId == Category.TV || this.newCategoryId == Category.CARTOON
                    || this.newCategoryId == Category.VARIETY) {
                this.videoType = 2;
            } else {
                this.videoType = 0;
            }
        } else {
            this.videoType = 0;
        }

        if (playLog != null) {
            Long htime = playLog.getHtime();
            if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
                // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
                this.playTime = -1000L;
            } else {
                this.playTime = htime * 1000L;
            }

            if (playLog.getUtime() != null) { // 上传时间
                this.lastTime = String.valueOf(playLog.getUtime());
            }
            if (playLog.getIsend() != null) {
                this.isEnd = playLog.getIsend() == 0 ? false : true;
            }
            this.from = String.valueOf(playLog.getFrom());
            this.nvid = playLog.getNvid();
        }
    }

    public PlayLogListDto(AlbumMysqlTable album, PlayLogInfo playLog, boolean isTV) {
        this.categoryId = 0;
        this.newCategoryId = 0;

        if (StringUtils.isNotBlank(playLog.getCid())) {
            this.categoryId = Integer.parseInt(playLog.getCid());
            this.newCategoryId = this.categoryId;
        }
        if (album != null) {
            this.pid = album.getId();
            this.iptvAlbumId = album.getId();
            this.tv_out = (album.getPushflag() != null && album.getPushflag() == 1) ? 0 : 1;
            this.img_200X150 = getAvailableImg(album, 200, 150);
            this.img_400X300 = getAvailableImg(album, 400, 300);
            this.img_150X200 = getAvailableImg(album, 150, 200);
            this.img_300X400 = getAvailableImg(album, 300, 400);
            this.img_400X225 = getAvailableImg(album, 400, 225);
            this.img_960X540 = getAvailableImg(album, 960, 540);
            this.img_180X101 = getAvailableImg(album, 180, 101);
            this.episodes = album.getEpisode();
            this.follownum = album.getFollownum();
            this.albumName = album.getName_cn();
            this.isCopyright = isTV;
        }
        this.isEnd = playLog.getIsend() != null && playLog.getIsend() == 1;

        if (this.newCategoryId == Category.FILM) {
            this.videoType = 1;
            this.follownum = 1;
        } else if (this.newCategoryId == Category.TV || this.newCategoryId == Category.CARTOON
                || this.newCategoryId == Category.VARIETY) {
            this.videoType = 2;
        } else {
            this.videoType = 0;
        }

        Integer nc = playLog.getNc();
        if (nc != null) {
            if (nc == 0) {
                nc = 1;
            }
            this.seriesNum = nc;
        }
        this.videoInfoId = playLog.getVid();
        this.videoName = playLog.getTitle();
        // 默认图处理（长视频显示专辑图片、短视频或者无专辑视频显示用户中心）
        if (this.newCategoryId == Category.TV || this.newCategoryId == Category.FILM
                || this.newCategoryId == Category.PARENTING || this.newCategoryId == Category.DFILM
                || this.newCategoryId == Category.CARTOON || this.newCategoryId == Category.VARIETY) {
            this.imgPic = getAvailableImg(album, 400, 300);
        } else {
            this.imgPic = playLog.getImg();
        }
        // 纪录片 有专辑属性展示视频名
        if (this.newCategoryId == Category.DFILM && album != null) {
            this.subtitle = playLog.getTitle();
        }
        Long htime = playLog.getHtime();
        if (htime == null || htime < 0 || (htime > Integer.MAX_VALUE / 1000)) {
            // 2015-09-22播放记录bug修改，针对非法数据，直接设置为播放结束
            this.playTime = -1000L;
        } else {
            this.playTime = htime * 1000L;
        }

        this.duration = playLog.getVtime() * 1000L;
        this.from = this.getPlayLogFrom(playLog.getFrom());
        this.source = playLog.getFrom();
        this.lastTime = TimeUtil.getDateString(new Date(playLog.getUtime() * 1000), TimeUtil.SIMPLE_DATE_FORMAT);
        // 2015-07-29添加一个过滤，过滤掉综艺的下一集id
        this.nvid = 0L;// 默认为0，表示没有下一集
        if (this.videoType == 2) {// videoType=2时，表示是电视剧，动漫或者综艺类型
            if (this.newCategoryId == Category.TV || this.newCategoryId == Category.CARTOON) {
                // 只有是电视剧和卡通，才返回下一集id(2015-08-26，如果当前剧集数大于等于最新剧集数，就不返回下一集id)
                if (this.follownum != null && this.seriesNum != null
                        && this.seriesNum.intValue() < this.follownum.intValue()) {
                    this.nvid = playLog.getNvid();
                }
            }
        }
        this.url = DataConstant.PC_PLAY_URL + this.videoInfoId + ".html";
    }

    // public PlayLogListDto(RadioProgramme radioProgramme) {
    // this.radioId = radioProgramme.getId();
    // this.globalId = radioProgramme.getLetvOriginalId();
    // this.radioName = radioProgramme.getName();
    // this.radioStartTime = radioProgramme.getStartTime();
    // this.radioEndTime = radioProgramme.getEndTime();
    // }

    /**
     * 获取播放记录背景缩略图，优先从album中取，取不到则从video中取
     * @param album
     *            专辑
     * @param video
     *            视频
     * @param width
     *            图片尺寸，宽
     * @param hight
     *            图片尺寸，高
     * @return
     */
    public static String getAvailableImg(AlbumMysqlTable album, VideoMysqlTable video, Integer width, Integer hight) {
        String img = null;
        if (album != null) {
            img = album.getPic(width, hight);
        }
        if (StringUtils.isBlank(img) && video != null) {
            img = video.getPic(width, hight);
        }
        return img;
    }

    /**
     * 获取播放记录背景缩略图，从video中取
     * @param video
     *            视频
     * @param width
     *            图片尺寸，宽
     * @param hight
     *            图片尺寸，高
     * @return
     */
    public static String getAvailableImg(VideoMysqlTable video, Integer width, Integer hight) {
        String img = null;
        if (StringUtils.isBlank(img) && video != null) {
            img = video.getPic(width, hight);
        }
        return img;
    }

    public static String getAvailableImg(AlbumMysqlTable album, Integer width, Integer hight) {
        String img = null;
        if (album != null) {
            img = album.getPic(width, hight);
        }
        return img;
    }

    public Integer getNewCategoryId() {
        return this.newCategoryId;
    }

    public void setNewCategoryId(Integer newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTv_out() {
        return this.tv_out;
    }

    public void setTv_out(int tv_out) {
        this.tv_out = tv_out;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    private String getPlayLogFrom(Integer from) {
        if (from == null) {
            return "";
        }
        String[] s = { "", "网页", "手机客户端", "平板", "电视", "桌面客户端", "超级汽车" };
        if (from > -1 && from < s.length) {
            return s[from];
        }
        return "";
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Long getIptvAlbumId() {
        return this.iptvAlbumId;
    }

    public void setIptvAlbumId(Long iptvAlbumId) {
        this.iptvAlbumId = iptvAlbumId;
    }

    public Long getVideoInfoId() {
        return this.videoInfoId;
    }

    public void setVideoInfoId(Long videoInfoId) {
        this.videoInfoId = videoInfoId;
    }

    public String getFraction() {
        return this.fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public Float getRating() {
        return this.rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return this.votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeriesNum() {
        return this.seriesNum;
    }

    public void setSeriesNum(Integer seriesNum) {
        this.seriesNum = seriesNum;
    }

    public Long getPlayTime() {
        return this.playTime;
    }

    public void setPlayTime(Long playTime) {
        this.playTime = playTime;
    }

    public String getLastTime() {
        return this.lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getImgPic() {
        return this.imgPic;
    }

    public void setImgPic(String imgPic) {
        this.imgPic = imgPic;
    }

    public String getImg_400X300() {
        return this.img_400X300;
    }

    public void setImg_400X300(String img_400x300) {
        this.img_400X300 = img_400x300;
    }

    public String getImg_200X150() {
        return this.img_200X150;
    }

    public void setImg_200X150(String img_200x150) {
        this.img_200X150 = img_200x150;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getIsCopyright() {
        return this.isCopyright;
    }

    public void setIsCopyright(Boolean isCopyright) {
        this.isCopyright = isCopyright;
    }

    public Boolean getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Long getNextVideoInfoId() {
        return this.nextVideoInfoId;
    }

    public void setNextVideoInfoId(Long nextVideoInfoId) {
        this.nextVideoInfoId = nextVideoInfoId;
    }

    public Integer getFollownum() {
        return this.follownum;
    }

    public void setFollownum(Integer follownum) {
        this.follownum = follownum;
    }

    public Integer getVideoType() {
        return this.videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getImg_300X400() {
        return this.img_300X400;
    }

    public void setImg_300X400(String img_300x400) {
        this.img_300X400 = img_300x400;
    }

    public String getImg_150X200() {
        return this.img_150X200;
    }

    public void setImg_150X200(String img_150x200) {
        this.img_150X200 = img_150x200;
    }

    public String getImg_400X225() {
        return img_400X225;
    }

    public void setImg_400X225(String img_400X225) {
        this.img_400X225 = img_400X225;
    }

    public String getImg_960X540() {
        return img_960X540;
    }

    public void setImg_960X540(String img_960X540) {
        this.img_960X540 = img_960X540;
    }

    public String getImg_180X101() {
        return img_180X101;
    }

    public void setImg_180X101(String img_180X101) {
        this.img_180X101 = img_180X101;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getNvid() {
        return this.nvid;
    }

    public void setNvid(Long nvid) {
        this.nvid = nvid;
    }

    public String getVideoStatus() {
        return this.videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public Long getRoleid() {
        return this.roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Integer getAlbumTvCopyright() {
        return this.albumTvCopyright;
    }

    public void setAlbumTvCopyright(Integer albumTvCopyright) {
        this.albumTvCopyright = albumTvCopyright;
    }

    public void setvGlobalId(String vGlobalId) {
        this.vGlobalId = vGlobalId;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Integer getIsPay() {
        return this.isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }

    public String getRadioStartTime() {
        return radioStartTime;
    }

    public void setRadioStartTime(String radioStartTime) {
        this.radioStartTime = radioStartTime;
    }

    public String getRadioEndTime() {
        return radioEndTime;
    }

    public void setRadioEndTime(String radioEndTime) {
        this.radioEndTime = radioEndTime;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getaGlobalId() {
        return aGlobalId;
    }

    public void setaGlobalId(String aGlobalId) {
        this.aGlobalId = aGlobalId;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getRadioId() {
        return radioId;
    }

    public void setRadioId(String radioId) {
        this.radioId = radioId;
    }

    public String getvGlobalId() {
        return vGlobalId;
    }

    public Integer getPlayTimeType() {
        return playTimeType;
    }

    public void setPlayTimeType(Integer playTimeType) {
        this.playTimeType = playTimeType;
    }
}
