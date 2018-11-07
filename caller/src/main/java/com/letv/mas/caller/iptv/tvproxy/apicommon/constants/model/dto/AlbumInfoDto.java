package com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.springframework.util.CollectionUtils;
import serving.AlbumAttribute;
import serving.IdAndName;
import serving.ResultDocInfo;
import serving.ServingResult;

import java.util.*;

/**
 * 搜检锁通用返回DTO
 * @author dunhongqin
 */
public class AlbumInfoDto extends BaseData {
    private String videoType = SearchConstant.VIDEO_TYPE_ALBUM;
    private String name;
    private Long iptvAlbumId;
    private Long id;// 专辑id
    private String smallImage;
    private String thumbnailImage;
    private String bigImage;
    private String img_vertical_300x400; // 300x400图片
    private String categoryName; // 分类名称
    private Integer categoryId; // 分类ID
    private String subcategoryId; // 子分类ID
    private String subcategoryName; // 子分类名称
    private String directory; // 导演
    private String starring; // 主演
    private String releaseDate; // 上映年份
    private Integer albumTypeId;
    private String albumTypeName;
    private String recordCompany;
    private String styleName;
    private String styleId;
    private String firstPlayTime;
    private String areaName;// 地区
    private String area;// 地区
    private String tvId;
    private String tvName;
    private Integer positive;// 是否是正片
    private Integer tv_out;// 是否跳到站外
    private String desc;
    private String pic_400X300;
    private Integer episodes;// 总集数
    private String vv = "0";// 专辑观看次数
    private Integer newCategoryId;// 分类id
    private String tag;
    private Float score;
    private String subTitle;
    private Integer follownum; // 跟播级数
    private Integer duration; // 视频时长
    private long durationStamp;
    private boolean isEnd;
    private String watchFocus;
    private Object images;
    private String iconType;// 角标类型
    private Integer ifLesetPlayList = Integer.valueOf(0); // 乐视儿童--播单中是否存在该专辑
                                                          // 0不存在 1存在

    private String ifCharge;// 是否收费
    // for tvod icon type
    private Integer chargeType; // 收费类型

    private Integer payType;// 购买类型
    private String img; // 图片
    private String imgSize; // 图片尺寸
    private String bigImg; // le背景大图
    private String defaultStream; // 默认码流
    private String defaultStreamName; // 默认码流名称
    private Map<String, String> pic_urls; // 图片集合

    /**
     * 专题类型，仅当当前资源表示专题时才使用，老数据为6--横向列表，7--竖向列表；
     */
    private String subjectType;

    /**
     * 2015-07-21修改为最新专题模板，参见SubjectConstant.SUBJECT_TYPE_*
     */
    private Integer templateType;

    public AlbumInfoDto() {

    }

    public AlbumInfoDto(ServingResult servingResult, CommonParam commonParam) {
        if (servingResult != null && servingResult.getDoc_info() != null) {
            this.setDataType(DataConstant.DATA_TYPE_ALBUM);
            ResultDocInfo resultDocInfo = servingResult.getDoc_info();
            this.id = StringUtil.toLong(resultDocInfo.getId());
            this.iptvAlbumId = StringUtil.toLong(resultDocInfo.getId());
            this.defaultStream = defaultStream;
            this.defaultStreamName = defaultStreamName;
            this.name = resultDocInfo.getName();
            if (!StringUtil.isBlank(resultDocInfo.getCategory())) {
                this.categoryId = Integer.parseInt(resultDocInfo.getCategory());
                this.newCategoryId = Integer.parseInt(resultDocInfo.getCategory());
            }
            this.categoryName = resultDocInfo.getCategory_name();
            this.desc = resultDocInfo.getDescription();
            if (resultDocInfo.getAlbum_attribute() != null) {
                AlbumAttribute albumAttribute = resultDocInfo.getAlbum_attribute();
                this.subcategoryId = albumAttribute.getSub_category();
                this.subcategoryName = albumAttribute.getSub_category_name();
                if (!CollectionUtils.isEmpty(albumAttribute.getDirector())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (IdAndName idAndName : albumAttribute.getDirector()) {
                        stringBuilder.append(idAndName.getName() + ",");
                    }
                    this.directory = stringBuilder.substring(0, stringBuilder.length() - 1);
                }
                if (!CollectionUtils.isEmpty(albumAttribute.getStarring())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (IdAndName idAndName : albumAttribute.getStarring()) {
                        stringBuilder.append(idAndName.getName() + ",");
                    }
                    this.starring = stringBuilder.substring(0, stringBuilder.length() - 1);
                }
                this.albumTypeId = albumAttribute.getVideo_type();
                this.albumTypeName = albumAttribute.getVideo_type_name();
                this.recordCompany = albumAttribute.getRcompany();// 没有找到该字段，rcompany
                                                                  // 搜索端也要删除
                this.styleId = albumAttribute.getStyle();
                this.styleName = this.area = albumAttribute.getArea();
                this.areaName = albumAttribute.getArea_name();
                this.tvId = albumAttribute.getTvid();
                this.tvName = albumAttribute.getTv_title();
                if (albumAttribute.getImages() != null) {
                    this.smallImage = albumAttribute.getImages().get("120*160");
                    this.thumbnailImage = albumAttribute.getImages().get("120*160");
                    this.bigImage = albumAttribute.getImages().get("150*200");
                    this.img_vertical_300x400 = albumAttribute.getImages().get("300*400");
                    this.pic_400X300 = albumAttribute.getImages().get("400*300");
                    this.img = albumAttribute.getImages().get("300*400");
                    this.imgSize = "300*400";
                    this.bigImg = albumAttribute.getImages().get("1440*810");
                    Map<String, String> pics = new HashMap<String, String>();
                    pics.put(CommonConstants.PIXEL_300_400, this.img);
                    this.setPic_urls(pics);
                }
                this.tv_out = 0;// 写死，搜索产品已确认不用了，默认为0不外跳 (0内部，1外跳） 客户端确认不调用
                if (VideoCommonUtil.isPositive(2, this.categoryId, this.albumTypeId, null)) {
                    this.positive = 1;
                } else {
                    this.positive = 0;
                }
                this.releaseDate = albumAttribute.getRelease_date() + "";
                if (!StringUtil.isBlank(albumAttribute.getRating())) {
                    this.score = Float.parseFloat(albumAttribute.getRating());
                }
                this.subTitle = albumAttribute.getSub_name();
                this.episodes = albumAttribute.getEpisodes() + 1 - 1;
                if (!StringUtil.isBlank(albumAttribute.getNow_episode())) {
                    this.follownum = Integer.parseInt(albumAttribute.getNow_episode());
                }
                if (!StringUtil.isBlank(albumAttribute.getDuration())) {
                    this.duration = Integer.parseInt(albumAttribute.getDuration());
                    this.durationStamp = this.duration * 1000;
                }
                this.isEnd = albumAttribute.getIs_end() == 1 ? true : false;
                String strTag = "";
                String str4k = "";
                String str3d = "";
                String strdj = "";

                if (albumAttribute.getPlay_streams() != null) {
                    if (albumAttribute.getPlay_streams().indexOf("4k") != -1) {
                        str4k = "4k";
                    }

                    if (albumAttribute.getPlay_streams().indexOf("3d") != -1) {
                        str3d += "3d";
                    }
                }

                if ("20001".equals(albumAttribute.getCopyright_type())) {
                    strdj = "独家";
                }

                if (str4k.trim().length() > 0) {
                    strTag = str4k;
                }

                if (str3d.trim().length() > 0) {
                    if (strTag.trim().length() > 0) {
                        strTag = strTag + "," + str3d;
                    } else {
                        strTag = str3d;
                    }
                }

                if (strdj.trim().length() > 0) {
                    if (strTag.trim().length() > 0) {
                        strTag = strTag + "," + strdj;
                    } else {
                        strTag = strdj;
                    }
                }
                this.tag = strTag;
                this.ifCharge = this.isTvCopyRightOrPay(albumAttribute.getPay_platform(), true) + "";
                // for tvod icon type search
                // this.chargeType = "";

                if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                    if (null != albumAttribute.getPay_detail()) {
                        ChargeInfo chargeInfo = MmsDataUtil.getChargeInfoFromPayDetail(commonParam.getP_devType(),
                                albumAttribute.getPay_detail());
                        if (null != chargeInfo) {
                            this.ifCharge = chargeInfo.getIsCharge();
                            this.chargeType = JumpUtil.getChargeType(chargeInfo);
                        }
                    }
                }
            }

        }

    }

    public AlbumInfoDto(AlbumMysqlTable albumInfo, CommonParam commonParam) {
        if (albumInfo != null) {
            this.setDataType(DataConstant.DATA_TYPE_ALBUM);
            this.id = albumInfo.getId();
            this.iptvAlbumId = albumInfo.getItv_album_id();
            if (this.iptvAlbumId == null) {
                this.iptvAlbumId = albumInfo.getId();
            }
            this.name = albumInfo.getName_cn();
            this.categoryId = albumInfo.getCategory();
            this.newCategoryId = albumInfo.getCategory();
            this.categoryName = albumInfo.getCategory_name();
            this.subcategoryId = albumInfo.getSub_category();
            this.subcategoryName = albumInfo.getSub_category_name();
            this.directory = albumInfo.getDirectory();
            this.starring = albumInfo.getStarring();
            try {
                if (albumInfo.getRelease_date() != null) {
                    this.releaseDate = TimeUtil.getDateString(new Date(Long.parseLong(albumInfo.getRelease_date())),
                            TimeUtil.SHORT_DATE_FORMAT);
                }
            } catch (Exception e) {
            }
            this.albumTypeId = albumInfo.getAlbum_type();
            this.albumTypeName = albumInfo.getAlbum_type_name();
            this.recordCompany = albumInfo.getRecord_company();
            this.styleId = albumInfo.getStyle();
            this.styleName = albumInfo.getStyle_name();
            this.firstPlayTime = albumInfo.getFirst_play_time();
            this.area = albumInfo.getArea();
            this.areaName = albumInfo.getArea_name();
            this.tvId = albumInfo.getPlay_tv();
            this.tvName = albumInfo.getPlay_tv_name();
            this.smallImage = albumInfo.getPic(120, 160);
            this.thumbnailImage = albumInfo.getPic(120, 160);
            this.bigImage = albumInfo.getPic(150, 200);
            this.bigImg = albumInfo.getPic(1440, 810); // 背景大图
            this.img_vertical_300x400 = albumInfo.getPic(300, 400);
            this.tv_out = (albumInfo.getPushflag() != null && albumInfo.getPushflag() == 1) ? 0 : 1;
            this.pic_400X300 = albumInfo.getPic(400, 300);
            this.img = albumInfo.getPic(300, 400);
            this.imgSize = "300*400";
            this.desc = albumInfo.getDescription();
            if (VideoCommonUtil.isPositive(2, this.categoryId, this.albumTypeId, null)) {
                this.positive = 1;
            } else {
                this.positive = 0;
            }
            if (!StringUtil.isBlank(albumInfo.getPay_platform())
                    && MmsDataUtil.isSupportPayPlatform(albumInfo.getPay_platform(), commonParam.getP_devType())) {
                this.ifCharge = "1";
            } else {
                this.ifCharge = "0";
            }
            // for tvod icon type
            Integer chargeType = JumpUtil.getChargeType(albumInfo.getPay_platform(), commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(albumInfo.getPay_platform());
            }
            this.chargeType = chargeType;

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                if (null != albumInfo.getPay_platform()) {
                    ChargeInfoDto chargeInfoDto = JumpUtil.genChargeInfoDto(commonParam.getP_devType(),
                            albumInfo.getPay_platform());
                    if (null != chargeInfoDto) {
                        this.ifCharge = chargeInfoDto.getIsCharge();
                        this.chargeType = JumpUtil.getChargeType(chargeInfoDto);
                    }
                }
            }

            this.newCategoryId = albumInfo.getCategory();
            this.releaseDate = albumInfo.getRelease_date();

            this.score = albumInfo.getScore();
            this.subTitle = albumInfo.getSub_title();
            this.episodes = albumInfo.getEpisode();
            this.follownum = albumInfo.getFollownum();
            this.duration = albumInfo.getDuration();
            if (this.duration != null) {
                this.durationStamp = this.duration * 1000;
            }
            this.isEnd = albumInfo.getIs_end() == 1 ? true : false;

            String strTag = "";
            String str4k = "";
            String str3d = "";
            String strdj = "";

            if (albumInfo.getPlay_streams() != null) {
                if (albumInfo.getPlay_streams().indexOf("4k") != -1) {
                    str4k = "4k";
                }

                if (albumInfo.getPlay_streams().indexOf("3d") != -1) {
                    str3d += "3d";
                }
            }

            if (albumInfo.getCopyright_type() != null) {
                if (albumInfo.getCopyright_type() == 20001) {
                    strdj = "独家";
                }
            }

            if (str4k.trim().length() > 0) {
                strTag = str4k;
            }

            if (str3d.trim().length() > 0) {
                if (strTag.trim().length() > 0) {
                    strTag = strTag + "," + str3d;
                } else {
                    strTag = str3d;
                }
            }

            if (strdj.trim().length() > 0) {
                if (strTag.trim().length() > 0) {
                    strTag = strTag + "," + strdj;
                } else {
                    strTag = strdj;
                }
            }

            this.tag = strTag;

        }
    }

    public AlbumInfoDto(SearchData searchData) {
        if (searchData != null) {
            this.id = searchData.getId();
            this.iptvAlbumId = searchData.getItv_album_id();
            this.name = searchData.getName_cn();
            this.categoryId = searchData.getCategory();
            // 2016-03-17重构，去除转换老频道到新频道的逻辑，需要测试--CategoryIdConstants.getNewCategory(searchData.getCategory());
            this.newCategoryId = searchData.getCategory();
            this.categoryName = searchData.getCategory_name();
            this.subcategoryId = searchData.getSub_category();
            this.subcategoryName = searchData.getSub_category_name();
            this.directory = searchData.getDirectory();
            this.starring = searchData.getStarring();
            this.releaseDate = searchData.getRelease_date() == null ? "" : searchData.getRelease_date();
            this.score = searchData.getScore();
            this.subTitle = searchData.getSub_title();
            this.episodes = searchData.getEpisode();
            this.follownum = searchData.getFollownum();
            this.duration = searchData.getDuration();
            if (this.duration != null) {
                this.durationStamp = this.duration * 1000;
            }
            this.albumTypeId = StringUtil.toInteger(searchData.getAlbum_type(), 0);
            this.albumTypeName = searchData.getAlbum_type_name();
            this.recordCompany = searchData.getRecord_company();
            this.styleId = searchData.getStyle();
            this.styleName = searchData.getStyle_name();
            this.firstPlayTime = searchData.getFirst_play_time();
            this.area = searchData.getArea();
            this.areaName = searchData.getArea_name();
            this.tvId = searchData.getPlay_tv();
            this.tvName = searchData.getPlay_tv_name();
            this.smallImage = searchData.getPic(120, 160);
            this.thumbnailImage = searchData.getPic(120, 160);
            this.bigImage = searchData.getPic(150, 200);
            this.img_vertical_300x400 = searchData.getPic(300, 400);
            this.pic_400X300 = searchData.getPic(400, 300);
            this.bigImg = searchData.getPic(1440, 810);
            this.img = searchData.getPic(300, 400);
            this.imgSize = "300*400";
            this.desc = searchData.getDescription();
            this.tv_out = (searchData.getPushflag() != null && searchData.getPushflag() == 1) ? 0 : 1;
            if (VideoCommonUtil.isPositive(2, this.categoryId, this.albumTypeId, null)) {
                this.positive = 1;
            } else {
                this.positive = 0;
            }

            if (searchData.getPlayPlatform() != null && !StringUtil.isBlank(searchData.getPlayPlatform().get("141007"))) {
                this.ifCharge = "1";
            } else {
                this.ifCharge = "0";
            }

            String strTag = "";
            String str4k = "";
            String str3d = "";
            String strdj = "";

            if (searchData.getPlay_streams() != null) {
                if (searchData.getPlay_streams().indexOf("4k") != -1) {
                    str4k = "4k";
                }

                if (searchData.getPlay_streams().indexOf("3d") != -1) {
                    str3d += "3d";
                }
            }

            if (searchData.getCopyright_type() != null) {
                if (searchData.getCopyright_type() == 20001) {
                    strdj = "独家";
                }
            }

            if (str4k.trim().length() > 0) {
                strTag = str4k;
            }

            if (str3d.trim().length() > 0) {
                if (strTag.trim().length() > 0) {
                    strTag = strTag + "," + str3d;
                } else {
                    strTag = str3d;
                }
            }

            if (strdj.trim().length() > 0) {
                if (strTag.trim().length() > 0) {
                    strTag = strTag + "," + strdj;
                } else {
                    strTag = strdj;
                }
            }
            this.tag = strTag;
            if (searchData.getIs_end() != null) {
                this.isEnd = searchData.getIs_end() == 1;
            }

            if (searchData.getData_type() != null
                    && SearchConstant.SEARCH_DATA_TYPE_SUBJECT == searchData.getData_type().intValue()) {
                this.tag = searchData.getTag();
                this.newCategoryId = searchData.getCategory();
                this.images = searchData.getImages();
                if (this.images != null) {
                    HashMap<String, String> imageMap = (HashMap<String, String>) this.images;
                    this.pic_400X300 = imageMap.get("400*300");
                }
                this.videoType = "subject";
                if ("1".equals(searchData.getSubjectType())) {
                    this.subjectType = SearchConstant.SUBJECT_TYPE_HORIZONTAL;
                } else {
                    this.subjectType = SearchConstant.SUBJECT_TYPE_VERTICAL;
                }

                this.templateType = searchData.getTemplateType();
            }
        }
    }

    /**
     * 专辑视频是否有版权
     */
    // TODO recommendationService 已有该方法
    public Integer isTvCopyRightOrPay(List<IdAndName> play_platform, boolean checkPay) {
        if (play_platform == null || play_platform.size() <= 0) {
            return 0;
        } else {
            Iterator<?> it = play_platform.iterator();
            while (it.hasNext()) {
                IdAndName platform = (IdAndName) it.next();
                if (platform != null) {
                    if (checkPay) {
                        if (CommonConstants.TV_PAY_CODE.equals(platform.getId())) {
                            return 1;
                        }
                    } else {
                        if (CommonConstants.TV_PLATFROM_CODE.equals(platform.getId())) {
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public String getWatchFocus() {
        return this.watchFocus;
    }

    public void setWatchFocus(String watchFocus) {
        this.watchFocus = watchFocus;
    }

    public boolean isEnd() {
        return this.isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFollownum() {
        return this.follownum;
    }

    public void setFollownum(Integer follownum) {
        this.follownum = follownum;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Float getScore() {
        return this.score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic_400X300() {
        return this.pic_400X300;
    }

    public void setPic_400X300(String pic_400x300) {
        this.pic_400X300 = pic_400x300;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIptvAlbumId() {
        return this.iptvAlbumId;
    }

    public void setIptvAlbumId(Long iptvAlbumId) {
        this.iptvAlbumId = iptvAlbumId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmallImage() {
        return this.smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getThumbnailImage() {
        return this.thumbnailImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getBigImage() {
        return this.bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public String getImg_vertical_300x400() {
        return this.img_vertical_300x400;
    }

    public void setImg_vertical_300x400(String img_vertical_300x400) {
        this.img_vertical_300x400 = img_vertical_300x400;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSubcategoryId() {
        return this.subcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public String getSubcategoryName() {
        return this.subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public Integer getAlbumTypeId() {
        return this.albumTypeId;
    }

    public void setAlbumTypeId(Integer albumTypeId) {
        this.albumTypeId = albumTypeId;
    }

    public String getAlbumTypeName() {
        return this.albumTypeName;
    }

    public void setAlbumTypeName(String albumTypeName) {
        this.albumTypeName = albumTypeName;
    }

    public String getRecordCompany() {
        return this.recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
    }

    public String getStyleName() {
        return this.styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getFirstPlayTime() {
        return this.firstPlayTime;
    }

    public void setFirstPlayTime(String firstPlayTime) {
        this.firstPlayTime = firstPlayTime;
    }

    public String getStyleId() {
        return this.styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getTvId() {
        return this.tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public String getTvName() {
        return this.tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getVideoType() {
        return this.videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public Integer getPositive() {
        return this.positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getTv_out() {
        return this.tv_out;
    }

    public void setTv_out(Integer tv_out) {
        this.tv_out = tv_out;
    }

    public Integer getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getVv() {
        return this.vv;
    }

    public void setVv(String vv) {
        this.vv = vv;
    }

    public Integer getNewCategoryId() {
        return this.newCategoryId;
    }

    public void setNewCategoryId(Integer newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public Object getImages() {
        return this.images;
    }

    public void setImages(Object images) {
        this.images = images;
    }

    public String getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public Integer getIfLesetPlayList() {
        return this.ifLesetPlayList;
    }

    public void setIfLesetPlayList(Integer ifLesetPlayList) {
        this.ifLesetPlayList = ifLesetPlayList;
    }

    public String getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(String ifCharge) {
        this.ifCharge = ifCharge;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public String getBigImg() {
        return bigImg;
    }

    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }

    public long getDurationStamp() {
        return durationStamp;
    }

    public void setDurationStamp(long durationStamp) {
        this.durationStamp = durationStamp;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getDefaultStream() {
        return defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public String getDefaultStreamName() {
        return defaultStreamName;
    }

    public void setDefaultStreamName(String defaultStreamName) {
        this.defaultStreamName = defaultStreamName;
    }

    public Map<String, String> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(Map<String, String> pic_urls) {
        this.pic_urls = pic_urls;
    }

}
