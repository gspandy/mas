package com.letv.mas.caller.iptv.tvproxy.apicommon.constants.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SubjectConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.util.CollectionUtils;
import serving.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SearchResultDto extends BaseData {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String globalId;
    private Integer dataType;// 数据类型 1专辑、2视频、3明星
    private Integer subjectType;// 当dataType为专题时，该字段表示 哪种专题 视频专题、专辑专题等
    private String aid;// 专辑id，根据src的不同。可能是leso专辑id和vrs专辑id
    private String name;// 名称
    private String area;// 地区id
    private String areaName;// 地区名称
    private Integer category;// 一级类型id
    private String categoryName;// 一级类型名称
    private String subCategoryName;// 二级类型名称
    private String subCategory;// 二级类型
    // private String directory;// 导演
    // private String starring;// 主演
    private String albumNameList;
    private String videoNameList;

    private HashMap<String, String> directory;// 导演
    private String src;// 来源id.0 来自PTV；1来自VRS；2来自外网；3来自IPTV
    private String subSrc;// 来源名称sohu/qiyi等
    private String videoType;// 影片类型 1正片等
    private String videoTypeName;// 正片等
    private String starSrc;
    private String postS1;// 竖图1：150px*200px
    private String postS2;// 竖图2：120px*160px
    private String postS3;// 竖图3：96px*128px
    private String postH1;// 横图1：220px*145px
    private String postH2;// 横图2：130px*80px
    private String postH3;// 横图3
    private String vids;// 拥有视频ids
    private String url;// 默认播放地址
    private String episodes;// 总集数
    private String nowEpisodes;// 当前更新到集数
    private String isEnd;// 是否完结 0、1
    private String playStreams;// 码流 350 1000
    private String pushFlag;// 推送平台
    private String playCount;
    private String poster20;
    private String videoPic;
    private String viewPic;
    private String vid;
    private String aorder;
    private Map<String, String> playPlatform = new HashMap<String, String>();
    private Integer categoryId;// new vrs categoryId
    private String vType;// new vrs videoType
    private LiveInfo liveinfo;
    private String duration;
    private String releaseDate;
    private String rating;
    private Map<String, String> actor;
    private String description;
    private Map<String, String> images;
    private List<VideoList> videoList;
    private List<SearchWorksTp> works;
    private String pic_400_300;
    private String subTitle;
    private String albumSrc;
    private String mid;
    private String sid;
    private String isHomemade; // 是否自制
    private String ispay; // 是否付费
    // for tvod icon type search
    // private String payType; //付费类型
    // private Integer isCoupon; // 是否支持券

    private Map<String, String> payPlatform; // 支付平台
    private String letv_original_id;// 数据上报用到
    private String contentId;
    private String sourceId;
    private Integer isVerified;// 是否审核通过，0、未通过 1、通过
    private String externalId;// 第三方数据ID

    public SearchResultDto() {
        // TODO Auto-generated constructor stub
    }

    public SearchResultDto(ServingResult servingResult, CommonParam commonParam) {
        if (servingResult != null) {
            ResultDocInfo resultDocInfo = servingResult.getDoc_info();
            this.globalId = resultDocInfo.getGlobal_id();
            this.name = resultDocInfo.getName();
            if (!StringUtil.isBlank(resultDocInfo.getCategory())) {
                this.categoryId = Integer.parseInt(resultDocInfo.getCategory());
                this.category = Integer.parseInt(resultDocInfo.getCategory());
            }
            this.description = resultDocInfo.getDescription();
            this.categoryName = resultDocInfo.getCategory_name();
            this.letv_original_id = resultDocInfo.getLetv_original_id();

            if (resultDocInfo.getData_type() != null) {
                this.dataType = resultDocInfo.getData_type().getValue();
                if (servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_ALBUM
                        && resultDocInfo.getAlbum_attribute() != null) {
                    AlbumAttribute albumAttribute = resultDocInfo.getAlbum_attribute();
                    this.isHomemade = String.valueOf(albumAttribute.getIs_home_made());
                    this.ispay = String.valueOf(albumAttribute.getIs_pay());
                    // for tvod icon type search

                    if (!CollectionUtils.isEmpty(albumAttribute.getPay_platform())) {
                        this.payPlatform = new HashMap<String, String>();
                        for (IdAndName idAndName : albumAttribute.getPay_platform()) {
                            this.payPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (commonParam.getWcode().equals(LocaleConstant.Wcode.IN)) {
                        this.aid = resultDocInfo.getLetv_original_id();
                    } else {
                        this.aid = resultDocInfo.getId();
                    }
                    this.subTitle = albumAttribute.getSub_name();
                    this.area = albumAttribute.getArea();
                    this.areaName = albumAttribute.getArea_name();
                    this.subCategory = albumAttribute.getSub_category();
                    if (!StringUtil.isBlank(albumAttribute.getSub_category_name())) {
                        this.subCategoryName = albumAttribute.getSub_category_name();
                    } else {
                        this.subCategoryName = "";
                    }
                    this.src = String.valueOf(albumAttribute.getSrc());
                    this.duration = albumAttribute.getDuration();
                    this.releaseDate = String.valueOf(albumAttribute.getRelease_date());
                    this.rating = albumAttribute.getRating();
                    this.actor = new HashMap<String, String>();
                    if (!CollectionUtils.isEmpty(albumAttribute.getActor())) {
                        for (IdAndName idAndName : albumAttribute.getActor()) {
                            this.actor.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (!CollectionUtils.isEmpty(albumAttribute.getDirector())) {
                        this.directory = new HashMap<String, String>();
                        for (IdAndName idAndName : albumAttribute.getDirector()) {
                            this.directory.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    this.externalId = albumAttribute.getExternal_id();
                    if (!CollectionUtils.isEmpty(albumAttribute.getVideo_list())) {
                        this.videoList = new LinkedList<VideoList>();
                        VideoList temp = null;
                        StringBuilder stringBuilder = new StringBuilder();
                        for (VideoAttributeInAlbum videoAttributeInAlbum : albumAttribute.getVideo_list()) {
                            temp = new VideoList();
                            temp.setVid(videoAttributeInAlbum.getVid());
                            temp.setExternalId(videoAttributeInAlbum.getExternal_id());
                            temp.setUrl(videoAttributeInAlbum.getUrl());
                            temp.setSubName(videoAttributeInAlbum.getSub_name());
                            temp.setName(videoAttributeInAlbum.getName());
                            temp.setEpisodes(videoAttributeInAlbum.getEpisode());
                            temp.setAorder(videoAttributeInAlbum.getIn_album_order());
                            if (!CollectionUtils.isEmpty(videoAttributeInAlbum.getActor())) {
                                temp.setActor(new HashMap<String, String>());
                                for (IdAndName idAndName : videoAttributeInAlbum.getActor()) {
                                    temp.getActor().put(idAndName.getId(), idAndName.getName());
                                }
                            }
                            this.videoList.add(temp);
                            stringBuilder.append(videoAttributeInAlbum.getName() + ",");
                        }
                        this.videoNameList = stringBuilder.substring(0, stringBuilder.length() - 1);
                    }

                    this.images = albumAttribute.getImages();
                    this.playStreams = albumAttribute.getPlay_streams();
                    this.albumSrc = String.valueOf(albumAttribute.getSrc());
                    this.subSrc = albumAttribute.getSub_src();

                    this.vType = String.valueOf(albumAttribute.getVideo_type());
                    if ("180001".equals(this.vType)) {
                        this.videoType = "1";
                    } else if ("180005".equals(this.vType)) {
                        this.videoType = "5";
                    } else if ("180002".equals(this.vType)) {
                        this.videoType = "2";
                    } else {
                        this.videoType = this.vType;
                    }
                    this.videoTypeName = albumAttribute.getVideo_type_name();
                    if (this.videoTypeName == null) {
                        this.videoTypeName = "";
                    }
                    this.vids = albumAttribute.getVids();
                    this.isEnd = String.valueOf(albumAttribute.is_end);
                    this.episodes = String.valueOf(albumAttribute.getEpisodes());
                    this.nowEpisodes = albumAttribute.getNow_episode();

                    if (!CollectionUtils.isEmpty(albumAttribute.getPush_flag())) {
                        for (IdAndName idAndName : albumAttribute.getPush_flag()) {
                            this.playPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (this.playPlatform != null) {
                        if (this.playPlatform.keySet().contains(CommonConstants.TV_PLAY_PLAT_FROM)
                                && !"2".equals(this.src)) {
                            this.pushFlag = "5";
                        } else {
                            this.pushFlag = "1";
                        }
                    }

                    if (albumAttribute.getImages() != null) {
                        String pic = albumAttribute.getImages().get("300*400");
                        this.poster20 = albumAttribute.getImages().get("300*400");
                        this.viewPic = this.poster20;
                        this.postS1 = albumAttribute.getImages().get("150*200");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS1;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS1;
                        }
                        this.postS2 = albumAttribute.getImages().get("120*160");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS2;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS2;
                        }
                        this.postS3 = albumAttribute.getImages().get("96*128");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS3;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS3;
                        }
                        this.postH1 = albumAttribute.getImages().get("220*145");
                        this.postH2 = albumAttribute.getImages().get("130*80");
                        this.postH3 = albumAttribute.getImages().get("120*90");
                        if (this.dataType == 2 && pic != null && pic.indexOf("/thumb/") != -1) {
                            this.videoPic = pic.replaceAll("/thumb/[0-9]_300_400.jpg", "");
                        } else {
                            this.videoPic = null;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.viewPic;
                        }
                        this.pic_400_300 = albumAttribute.getImages().get("400*300");
                        if (this.poster20 == null) {
                            this.poster20 = albumAttribute.getImages().get("450*600");
                        }
                    }
                    if (this.poster20 != null) {
                        this.poster20 = this.poster20.replaceAll(" ", "%20");
                    }
                } else if (servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_VIDEO
                        && resultDocInfo.getVideo_attribute() != null) {
                    VideoAttribute videoAttribute = resultDocInfo.getVideo_attribute();

                    if (!CollectionUtils.isEmpty(videoAttribute.getPay_platform())) {
                        this.payPlatform = new HashMap<String, String>();
                        for (IdAndName idAndName : videoAttribute.getPay_platform()) {
                            this.payPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (!StringUtil.isBlank(videoAttribute.getAid())) {
                        this.aid = videoAttribute.getAid();
                    }
                    this.src = String.valueOf(videoAttribute.getSrc());
                    this.contentId = videoAttribute.getExternal_id();
                    this.subTitle = videoAttribute.getSub_name();
                    this.area = videoAttribute.getArea();
                    this.areaName = videoAttribute.getArea_name();
                    this.subCategory = videoAttribute.getSub_category();
                    if (!StringUtil.isBlank(videoAttribute.getSub_category_name())) {
                        this.subCategoryName = videoAttribute.getSub_category_name();
                    } else {
                        this.subCategoryName = "";
                    }
                    this.sourceId = videoAttribute.getSource();
                    this.src = String.valueOf(videoAttribute.getSrc());
                    this.duration = videoAttribute.getDuration();
                    this.releaseDate = videoAttribute.getRelease_date();
                    this.rating = videoAttribute.getRating();
                    this.url = videoAttribute.getUrl();
                    if (!CollectionUtils.isEmpty(videoAttribute.getActor())) {
                        this.actor = new HashMap<String, String>();
                        for (IdAndName idAndName : videoAttribute.getActor()) {
                            this.actor.put(idAndName.getId(), idAndName.getName());
                        }
                    }

                    if (!CollectionUtils.isEmpty(videoAttribute.getDirector())) {
                        this.directory = new HashMap<String, String>();
                        for (IdAndName idAndName : videoAttribute.getDirector()) {
                            this.directory.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    this.images = videoAttribute.getImages();
                    this.playStreams = videoAttribute.getPlay_streams();
                    this.mid = videoAttribute.getMid();

                    // src=2为外网视频, src=3的业务暂不清楚，保留原有代码逻辑
                    if ("3".equals(videoAttribute.getSrc()) || "2".equals(videoAttribute.getSrc())) {
                        this.url = videoAttribute.getUrl();
                    } else {
                        if (videoAttribute.getVid() != null) {
                            this.url = DataConstant.PC_PLAY_URL + videoAttribute.getVid() + ".html";
                        }
                    }
                    this.vType = videoAttribute.getVideo_type();
                    if ("180001".equals(this.vType)) {
                        this.videoType = "1";
                    } else if ("180005".equals(this.vType)) {
                        this.videoType = "5";
                    } else if ("180002".equals(this.vType)) {
                        this.videoType = "2";
                    } else {
                        this.videoType = this.vType;
                    }
                    this.videoTypeName = videoAttribute.getVideo_type_name();
                    if (this.videoTypeName == null) {
                        this.videoTypeName = "";
                    }

                    this.episodes = videoAttribute.getEpisode();
                    this.aorder = videoAttribute.getAorder();
                    this.vid = videoAttribute.getVid();

                    if (!CollectionUtils.isEmpty(videoAttribute.getPush_flag())) {
                        for (IdAndName idAndName : videoAttribute.getPush_flag()) {
                            this.playPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }

                    if (this.playPlatform != null) {
                        if (this.playPlatform.keySet().contains(CommonConstants.TV_PLAY_PLAT_FROM)
                                && !"2".equals(this.src)) {
                            this.pushFlag = "5";
                        } else {
                            this.pushFlag = "1";
                        }
                    }

                    if (videoAttribute.getImages() != null) {
                        String pic = videoAttribute.getImages().get("300*400");
                        this.poster20 = videoAttribute.getImages().get("300*400");
                        this.viewPic = this.poster20;
                        this.postS1 = videoAttribute.getImages().get("150*200");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS1;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS1;
                        }
                        this.postS2 = videoAttribute.getImages().get("120*160");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS2;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS2;
                        }
                        this.postS3 = videoAttribute.getImages().get("96*128");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS3;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS3;
                        }
                        this.postH1 = videoAttribute.getImages().get("220*145");
                        this.postH2 = videoAttribute.getImages().get("130*80");
                        this.postH3 = videoAttribute.getImages().get("120*90");
                        if (this.dataType == 2 && pic != null && pic.indexOf("/thumb/") != -1) {
                            this.videoPic = pic.replaceAll("/thumb/[0-9]_300_400.jpg", "");
                        } else {
                            this.videoPic = null;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.viewPic;
                        }
                        this.pic_400_300 = videoAttribute.getImages().get("400*300");
                    }
                    if (this.poster20 != null) {
                        this.poster20 = this.poster20.replaceAll(" ", "%20");
                    }

                } else if (servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_STAR
                        && resultDocInfo.getStar_attribute() != null) {
                    StarAttribute starAttribute = resultDocInfo.getStar_attribute();
                    this.area = starAttribute.getArea();
                    this.areaName = starAttribute.getArea_name();
                    this.images = starAttribute.getImage_prefix();
                    this.starSrc = starAttribute.getStar_src();
                    this.works = new LinkedList<SearchWorksTp>();
                    this.sid = String.valueOf(starAttribute.getLe_id());
                    if (!CollectionUtils.isEmpty(starAttribute.getWorks_detail_info())) {
                        SearchWorksTp temp = null;
                        for (StarWorkAttribute starWorkAttribute : starAttribute.getWorks_detail_info()) {
                            temp = new SearchWorksTp();
                            temp.setName(starWorkAttribute.getName());
                            this.works.add(temp);
                        }
                    }

                    if (starAttribute.getImage_prefix() != null) {
                        String pic = starAttribute.getImage_prefix().get("300*400");
                        this.poster20 = starAttribute.getImage_prefix().get("300*400");
                        this.viewPic = this.poster20;
                        this.postS1 = starAttribute.getImage_prefix().get("150*200");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS1;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS1;
                        }
                        this.postS2 = starAttribute.getImage_prefix().get("120*160");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS2;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS2;
                        }
                        this.postS3 = starAttribute.getImage_prefix().get("96*128");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS3;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS3;
                        }
                        this.postH1 = starAttribute.getImage_prefix().get("220*145");
                        this.postH2 = starAttribute.getImage_prefix().get("130*80");
                        this.postH3 = starAttribute.getImage_prefix().get("120*90");
                        if (this.dataType == 2 && pic != null && pic.indexOf("/thumb/") != -1) {
                            this.videoPic = pic.replaceAll("/thumb/[0-9]_300_400.jpg", "");
                        } else {
                            this.videoPic = null;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.viewPic;
                        }
                        this.pic_400_300 = starAttribute.getImage_prefix().get("400*300");
                    }
                    if (this.dataType != null && this.dataType == 3) {
                        if (this.poster20 == null) {
                            // this.poster20=starAttribute.getpos20;//搜索没有了
                            this.postS1 = starAttribute.getPost_s1();
                        }
                    }
                    if (this.poster20 != null) {
                        this.poster20 = this.poster20.replaceAll(" ", "%20");
                    }

                } else if (servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_SUBJECT
                        && resultDocInfo.getSubject_attribute() != null) {
                    SubjectAttribute subjectAttribute = resultDocInfo.getSubject_attribute();
                    this.aid = resultDocInfo.getId();
                    this.subjectType = SubjectConstant.getSubjectTypeFromTemplate(subjectAttribute.getTv_template());
                    this.images = subjectAttribute.getImages();
                    this.subCategory = subjectAttribute.getSub_category();
                    if (!CollectionUtils.isEmpty(subjectAttribute.getPush_flag())) {
                        for (IdAndName idAndName : subjectAttribute.getPush_flag()) {
                            this.playPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (this.playPlatform != null) {
                        if (this.playPlatform.keySet().contains(CommonConstants.TV_PLAY_PLAT_FROM)
                                && !"2".equals(this.src)) {
                            this.pushFlag = "5";
                        } else {
                            this.pushFlag = "1";
                        }
                    }

                    if (!CollectionUtils.isEmpty(subjectAttribute.getVideo_list())) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (VideoInSubject videoInSubject : subjectAttribute.getVideo_list()) {
                            stringBuilder.append(videoInSubject.getName() + ",");
                        }
                        this.videoNameList = stringBuilder.substring(0, stringBuilder.length() - 1);
                    }

                    if (!CollectionUtils.isEmpty(subjectAttribute.getAlbum_list())) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (AlbumInSubject albumInSubject : subjectAttribute.getAlbum_list()) {
                            stringBuilder.append(albumInSubject.getName() + ",");
                        }
                        this.albumNameList = stringBuilder.substring(0, stringBuilder.length() - 1);
                    }

                    if (subjectAttribute.getImages() != null) {
                        String pic = subjectAttribute.getImages().get("300*400");
                        this.poster20 = subjectAttribute.getImages().get("300*400");
                        this.viewPic = this.poster20;
                        this.postS1 = subjectAttribute.getImages().get("150*200");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS1;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS1;
                        }
                        this.postS2 = subjectAttribute.getImages().get("120*160");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS2;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS2;
                        }
                        this.postS3 = subjectAttribute.getImages().get("96*128");
                        if (this.viewPic == null) {
                            this.viewPic = this.postS3;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.postS3;
                        }
                        this.postH1 = subjectAttribute.getImages().get("220*145");
                        this.postH2 = subjectAttribute.getImages().get("130*80");
                        this.postH3 = subjectAttribute.getImages().get("120*90");
                        if (this.dataType == 2 && pic != null && pic.indexOf("/thumb/") != -1) {
                            this.videoPic = pic.replaceAll("/thumb/[0-9]_300_400.jpg", "");
                        } else {
                            this.videoPic = null;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = this.viewPic;
                        }
                        this.pic_400_300 = subjectAttribute.getImages().get("400*300");
                    }
                    if (this.poster20 != null) {
                        this.poster20 = this.poster20.replaceAll(" ", "%20");
                    }

                }
            }

        }
    }

    @Override
    public String toString() {
        return "SearchResultDto [dataType=" + this.dataType + ", aid=" + this.aid + ", name=" + this.name + ", area="
                + this.area + ", areaName=" + this.areaName + ", category=" + this.category + ", categoryName="
                + this.categoryName + ", subCategoryName=" + this.subCategoryName + ", subCategory=" + this.subCategory
                + ", albumNameList=" + this.albumNameList + ", videoNameList=" + this.videoNameList + ", directory="
                + this.directory + ", src=" + this.src + ", subSrc=" + this.subSrc + ", videoType=" + this.videoType
                + ", videoTypeName=" + this.videoTypeName + ", starSrc=" + this.starSrc + ", postS1=" + this.postS1
                + ", postS2=" + this.postS2 + ", postS3=" + this.postS3 + ", postH1=" + this.postH1 + ", postH2="
                + this.postH2 + ", postH3=" + this.postH3 + ", vids=" + this.vids + ", url=" + this.url + ", episodes="
                + this.episodes + ", nowEpisodes=" + this.nowEpisodes + ", isEnd=" + this.isEnd + ", playStreams="
                + this.playStreams + ", pushFlag=" + this.pushFlag + ", playCount=" + this.playCount + ", poster20="
                + this.poster20 + ", videoPic=" + this.videoPic + ", viewPic=" + this.viewPic + ", vid=" + this.vid
                + ", aorder=" + this.aorder + ", playPlatform=" + this.playPlatform + ", categoryId=" + this.categoryId
                + ", vType=" + this.vType + ", liveinfo=" + this.liveinfo + ", duration=" + this.duration
                + ", releaseDate=" + this.releaseDate + ", rating=" + this.rating + ", actor=" + this.actor
                + ", description=" + this.description + ", images=" + this.images + ", videoList=" + this.videoList
                + ", works=" + this.works + ", pic_400_300=" + this.pic_400_300 + ", subTitle=" + this.subTitle
                + ", albumSrc=" + this.albumSrc + ", mid=" + this.mid + "]";
    }

    public String getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContentId() {
        return this.contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubCategoryName() {
        return this.subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getAlbumNameList() {
        return this.albumNameList;
    }

    public void setAlbumNameList(String albumNameList) {
        this.albumNameList = albumNameList;
    }

    public String getVideoNameList() {
        return this.videoNameList;
    }

    public void setVideoNameList(String videoNameList) {
        this.videoNameList = videoNameList;
    }

    public HashMap<String, String> getDirectory() {
        return this.directory;
    }

    public void setDirectory(HashMap<String, String> directory) {
        this.directory = directory;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSubSrc() {
        return this.subSrc;
    }

    public void setSubSrc(String subSrc) {
        this.subSrc = subSrc;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getVideoType() {
        return this.videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public String getVideoTypeName() {
        return this.videoTypeName;
    }

    public void setVideoTypeName(String videoTypeName) {
        this.videoTypeName = videoTypeName;
    }

    public String getStarSrc() {
        return this.starSrc;
    }

    public void setStarSrc(String starSrc) {
        this.starSrc = starSrc;
    }

    public String getPostS1() {
        return this.postS1;
    }

    public void setPostS1(String postS1) {
        this.postS1 = postS1;
    }

    public String getPostS2() {
        return this.postS2;
    }

    public void setPostS2(String postS2) {
        this.postS2 = postS2;
    }

    public String getPostS3() {
        return this.postS3;
    }

    public void setPostS3(String postS3) {
        this.postS3 = postS3;
    }

    public String getPostH1() {
        return this.postH1;
    }

    public void setPostH1(String postH1) {
        this.postH1 = postH1;
    }

    public String getPostH2() {
        return this.postH2;
    }

    public void setPostH2(String postH2) {
        this.postH2 = postH2;
    }

    public String getPostH3() {
        return this.postH3;
    }

    public void setPostH3(String postH3) {
        this.postH3 = postH3;
    }

    public String getVids() {
        return this.vids;
    }

    public void setVids(String vids) {
        this.vids = vids;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public String getNowEpisodes() {
        return this.nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public String getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getPlayStreams() {
        return this.playStreams;
    }

    public void setPlayStreams(String playStreams) {
        this.playStreams = playStreams;
    }

    public String getPushFlag() {
        return this.pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getPlayCount() {
        return this.playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getPoster20() {
        return this.poster20;
    }

    public void setPoster20(String poster20) {
        this.poster20 = poster20;
    }

    public String getVideoPic() {
        return this.videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public String getViewPic() {
        return this.viewPic;
    }

    public void setViewPic(String viewPic) {
        this.viewPic = viewPic;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getAorder() {
        return this.aorder;
    }

    public void setAorder(String aorder) {
        this.aorder = aorder;
    }

    public Map<String, String> getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getvType() {
        return this.vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public LiveInfo getLiveinfo() {
        return this.liveinfo;
    }

    public void setLiveinfo(LiveInfo liveinfo) {
        this.liveinfo = liveinfo;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Map<String, String> getActor() {
        return this.actor;
    }

    public void setActor(Map<String, String> actor) {
        this.actor = actor;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getImages() {
        return this.images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
    }

    public List<VideoList> getVideoList() {
        return this.videoList;
    }

    public void setVideoList(List<VideoList> videoList) {
        this.videoList = videoList;
    }

    public List<SearchWorksTp> getWorks() {
        return this.works;
    }

    public void setWorks(List<SearchWorksTp> works) {
        this.works = works;
    }

    public String getPic_400_300() {
        return this.pic_400_300;
    }

    public void setPic_400_300(String pic_400_300) {
        this.pic_400_300 = pic_400_300;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getAlbumSrc() {
        return this.albumSrc;
    }

    public void setAlbumSrc(String albumSrc) {
        this.albumSrc = albumSrc;
    }

    public String getMid() {
        return this.mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getSid() {
        return this.sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIsHomemade() {
        return this.isHomemade;
    }

    public void setIsHomemade(String isHomemade) {
        this.isHomemade = isHomemade;
    }

    public String getIspay() {
        return this.ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public Map<String, String> getPayPlatform() {
        return this.payPlatform;
    }

    public void setPayPlatform(Map<String, String> payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getLetv_original_id() {
        return this.letv_original_id;
    }

    public void setLetv_original_id(String letv_original_id) {
        this.letv_original_id = letv_original_id;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public static class LiveInfo {
        private List<Hits> hits;
        private Integer num;

        public List<Hits> getHits() {
            return this.hits;
        }

        public void setHits(List<Hits> hits) {
            this.hits = hits;
        }

        public Integer getNum() {
            return this.num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public static class Hits {
            private String channel;// 频道名称：如，北京卫视",
            private String displayStartTime;// 开始时间，格式： 14:00",
            private String playDate;// 开始日期：格式：20140224",
            private String playNum;// 播放号：如，18;19;20",
            private String playUrl;// 播放地址：，如：http://live.letv.com/weishi/play/index.shtml?channel=bjws",
            private String startTime;// 开始时间，如： 1400,
            private String title;// 名称，如：大丈夫"

            public String getChannel() {
                return this.channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getDisplayStartTime() {
                return this.displayStartTime;
            }

            public void setDisplayStartTime(String displayStartTime) {
                this.displayStartTime = displayStartTime;
            }

            public String getPlayDate() {
                return this.playDate;
            }

            public void setPlayDate(String playDate) {
                this.playDate = playDate;
            }

            public String getPlayNum() {
                return this.playNum;
            }

            public void setPlayNum(String playNum) {
                this.playNum = playNum;
            }

            public String getPlayUrl() {
                return this.playUrl;
            }

            public void setPlayUrl(String playUrl) {
                this.playUrl = playUrl;
            }

            public String getStartTime() {
                return this.startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }

    public static class VideoList implements Serializable {
        private static final long serialVersionUID = 6778108839044857931L;
        private String vid;
        private String name;
        private String subName;
        private String url;
        private String aorder;
        private String episodes;
        private Map<String, String> actor;
        private String externalId;

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
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

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAorder() {
            return this.aorder;
        }

        public void setAorder(String aorder) {
            this.aorder = aorder;
        }

        public String getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

        public Map<String, String> getActor() {
            return this.actor;
        }

        public void setActor(Map<String, String> actor) {
            this.actor = actor;
        }
    }

    public static class SearchWorksTp implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -4755197849274977892L;
        private String aid;
        private String dataType;
        private String videoType;
        private String name;
        private String highLightName;
        private String englishName;
        private String subname;
        private String otherName;
        private String shortDesc;
        private String tag;
        private String rating;
        private String category;
        private String subCategory;
        private String categoryName;
        private String subCategoryName;
        private Map<String, String> downloadPlatform;
        private Map<String, String> pushFlag;
        private Map<String, String> payPlatform;
        private String isEnd;
        private String hasHeight;
        private String allowforeign;
        private String area;
        private String areaName;
        private String copyright;
        private String duration;
        private Long releaseDate;
        private String episodes;
        private String playStatus;
        private Map<String, String> directory;
        // private List<Map<String, String>> starring;
        private Map<String, String> images;
        private String subSrc;
        private String ispay;
        private String payProduct;
        private String relationid;
        private String isHomemade;
        private Long ctime;
        private String vids;
        private String nowEpisodes;
        private String playStreams;
        private String dayCount;
        private String nameJianpin;
        private String nameQuanpin;
        private String videoTypeName;
        private String src;
        private String language;
        private String videoPlayUrls;
        private Map<String, String> actor;
        private String url;
        private String tvid;
        private String playCount;

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getDataType() {
            return dataType;
        }

        public void setDataType(String dataType) {
            this.dataType = dataType;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHighLightName() {
            return highLightName;
        }

        public void setHighLightName(String highLightName) {
            this.highLightName = highLightName;
        }

        public String getEnglishName() {
            return englishName;
        }

        public void setEnglishName(String englishName) {
            this.englishName = englishName;
        }

        public String getSubname() {
            return subname;
        }

        public void setSubname(String subname) {
            this.subname = subname;
        }

        public String getOtherName() {
            return otherName;
        }

        public void setOtherName(String otherName) {
            this.otherName = otherName;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public Map<String, String> getDownloadPlatform() {
            return downloadPlatform;
        }

        public void setDownloadPlatform(Map<String, String> downloadPlatform) {
            this.downloadPlatform = downloadPlatform;
        }

        public Map<String, String> getPushFlag() {
            return pushFlag;
        }

        public void setPushFlag(Map<String, String> pushFlag) {
            this.pushFlag = pushFlag;
        }

        public Map<String, String> getPayPlatform() {
            return payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public String getIsEnd() {
            return isEnd;
        }

        public void setIsEnd(String isEnd) {
            this.isEnd = isEnd;
        }

        public String getHasHeight() {
            return hasHeight;
        }

        public void setHasHeight(String hasHeight) {
            this.hasHeight = hasHeight;
        }

        public String getAllowforeign() {
            return allowforeign;
        }

        public void setAllowforeign(String allowforeign) {
            this.allowforeign = allowforeign;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public Long getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(Long releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getEpisodes() {
            return episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

        public String getPlayStatus() {
            return playStatus;
        }

        public void setPlayStatus(String playStatus) {
            this.playStatus = playStatus;
        }

        public Map<String, String> getDirectory() {
            return directory;
        }

        public void setDirectory(Map<String, String> directory) {
            this.directory = directory;
        }

        /*
         * public List<Map<String, String>> getStarring() {
         * return starring;
         * }
         * public void setStarring(List<Map<String, String>> starring) {
         * this.starring = starring;
         * }
         */
        public Map<String, String> getImages() {
            return images;
        }

        public void setImages(Map<String, String> images) {
            this.images = images;
        }

        public String getSubSrc() {
            return subSrc;
        }

        public void setSubSrc(String subSrc) {
            this.subSrc = subSrc;
        }

        public String getIspay() {
            return ispay;
        }

        public void setIspay(String ispay) {
            this.ispay = ispay;
        }

        public String getPayProduct() {
            return payProduct;
        }

        public void setPayProduct(String payProduct) {
            this.payProduct = payProduct;
        }

        public String getRelationid() {
            return relationid;
        }

        public void setRelationid(String relationid) {
            this.relationid = relationid;
        }

        public String getIsHomemade() {
            return isHomemade;
        }

        public void setIsHomemade(String isHomemade) {
            this.isHomemade = isHomemade;
        }

        public Long getCtime() {
            return ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public String getVids() {
            return vids;
        }

        public void setVids(String vids) {
            this.vids = vids;
        }

        public String getNowEpisodes() {
            return nowEpisodes;
        }

        public void setNowEpisodes(String nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public String getPlayStreams() {
            return playStreams;
        }

        public void setPlayStreams(String playStreams) {
            this.playStreams = playStreams;
        }

        public String getDayCount() {
            return dayCount;
        }

        public void setDayCount(String dayCount) {
            this.dayCount = dayCount;
        }

        public String getNameJianpin() {
            return nameJianpin;
        }

        public void setNameJianpin(String nameJianpin) {
            this.nameJianpin = nameJianpin;
        }

        public String getNameQuanpin() {
            return nameQuanpin;
        }

        public void setNameQuanpin(String nameQuanpin) {
            this.nameQuanpin = nameQuanpin;
        }

        public String getVideoTypeName() {
            return videoTypeName;
        }

        public void setVideoTypeName(String videoTypeName) {
            this.videoTypeName = videoTypeName;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getVideoPlayUrls() {
            return videoPlayUrls;
        }

        public void setVideoPlayUrls(String videoPlayUrls) {
            this.videoPlayUrls = videoPlayUrls;
        }

        public Map<String, String> getActor() {
            return actor;
        }

        public void setActor(Map<String, String> actor) {
            this.actor = actor;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTvid() {
            return tvid;
        }

        public void setTvid(String tvid) {
            this.tvid = tvid;
        }

        public String getPlayCount() {
            return playCount;
        }

        public void setPlayCount(String playCount) {
            this.playCount = playCount;
        }

    }

}
