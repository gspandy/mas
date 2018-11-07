package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.CategoryConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.AlbumDetailTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import com.letv.mas.caller.iptv.tvproxy.search.constant.SearchTpConstant;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.WebsiteTpResponse.WEB_SITE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AlbumDetailDto {
    private final Logger log = LoggerFactory.getLogger(AlbumDetailDto.class);
    private final static String ORIGINAL_AUTHOR = "WebSiteAlbumDTO.orginal.author"; // 原作多语言key
    private final static String DIRECTOR = "WebSiteAlbumDTO.director"; // 导演多语言key
    private final static String LEADING_ROLE = "WebSiteAlbumDTO.leading.role"; // 主演多语言key
    private final static String VOICE_ROLE = "WebSiteAlbumDTO.voice.role"; // 声优多语言key

    private String siteName;// 站点名称
    private Long aid;// 专辑id，根据src的不同。可能是leso专辑id和vrs专辑id
    private String name;// 名称
    private String area;// 地区名称 --本为是id，客户端显示为地区名称、做兼容
    private Integer categoryId;// new vrs categoryId
    private String categoryName;// 一级类型名称
    private String subCategoryName;// 二级类型名称
    private String directory;// 导演
    private String starring;// 主演
    private String src;// 来源id.0 来自PTV；1来自VRS；2来自外网；3来自IPTV
    private String subSrc;// 来源名称sohu/qiyi等
    private String vType;// new vrs 视频类型
    private String videoTypeName;// 视频类型 正片等
    private String episodes;// 总集数
    private Integer nowEpisodes;// 当前更新到集数
    private String description;// 长描述
    private String releaseDate;// 上映时间
    private String playCount;// 播放总数
    private String poster20; // 大图
    private String pushFlag; // 推送平台 420007 420001等
    private String rating; // 评分
    private String playStatus;
    private String relationid;
    private String otherName;
    private int relationAlbums; // 相关专辑，有多少相关系列专辑
    private List<AlbumDetailStarInfoDto> actorInfo; // 演员信息
    private String rcompany;
    private String recordCompany;
    private String duration; // 影片时长
    private String tag; // 专辑标签
    private String actorName; // 动漫声优
    private String fitAge; // 适合年龄
    private String englishName;
    private String contentRating; // 内容评级
    private List<AlbumDetailVideoInfoDto> videoList;// 拥有剧集
    private String letv_original_id;// 数据上报用到
    private String msg;// 提示信息
    private String externalId;// 第三方数据源ID
    private String subCategory;

    public AlbumDetailDto() {
    }

    public AlbumDetailDto(AlbumDetailTpResponse detailResp, CommonParam commonParam) {
        if (detailResp != null) {
            if (commonParam.getBroadcastId() != null && CommonConstants.CIBN == commonParam.getBroadcastId()) {
                this.msg = MessageUtils.getMessage("LESO_ALBUM_DETAIL_NO_PLAY", commonParam.getLangcode());
            }
            this.aid = detailResp.getAid();
            this.externalId = detailResp.getExternal_id();
            this.subCategory = detailResp.getSubCategory();
            this.name = detailResp.getName();
            // 防止专辑名称为空
            if (this.name == null) {
                this.name = detailResp.getQname();
            }
            this.area = detailResp.getAreaName();
            this.categoryId = detailResp.getCategory();
            this.categoryName = detailResp.getCategoryName();
            if (this.categoryName == null || "null".equalsIgnoreCase(this.categoryName)) {
                this.categoryName = "";
            }
            this.subCategoryName = detailResp.getSubCategoryName();
            this.src = detailResp.getSrc();
            this.subSrc = detailResp.getSubSrc();
            if (!SearchTpConstant.LESO_SRC_WEB.equals(this.src)) {
                this.siteName = WEB_SITE.LETV.getName();
                if (this.subSrc == null) {
                    this.subSrc = WEB_SITE.LETV.getPinYin();
                }
            } else {
                this.siteName = WEB_SITE.getCNNAME(this.subSrc).getName();
                // 外网视频的subCategoryName是";"分隔，保持客户端兼容性，内网和外网统一用","分隔
                if (this.subCategoryName != null) {
                    this.subCategoryName = this.subCategoryName.replaceAll(";", ",");
                }
            }
            this.releaseDate = detailResp.getReleaseDate();
            try {
                if (this.releaseDate != null && !SearchTpConstant.LESO_SRC_WEB.equals(this.src)) {
                    Date date = TimeUtil.parseDate(this.releaseDate, "yyyy-MM-dd");
                    if (date != null) {
                        this.releaseDate = date.getTime() + "";
                    } else {
                        date = TimeUtil.parseDate(this.releaseDate, "yyyy-MM");
                        if (date != null) {
                            this.releaseDate = date.getTime() + "";
                        } else {
                            date = TimeUtil.parseDate(this.releaseDate, "yyyy");
                            if (date != null) {
                                this.releaseDate = date.getTime() + "";
                            } else {
                                this.releaseDate = null;
                            }
                        }
                    }
                    if (this.releaseDate != null) {
                        this.releaseDate = this.releaseDate.replaceAll("-", "");
                    }
                }
            } catch (Exception e) {
                this.releaseDate = null;
            }
            this.vType = detailResp.getVideoType();
            this.playStatus = detailResp.getPlayStatus();
            this.englishName = detailResp.getEnglishName();
            this.otherName = detailResp.getOtherName();
            this.tag = detailResp.getTag();
            this.rating = detailResp.getRating();
            this.actorName = detailResp.getActorName();
            this.rcompany = detailResp.getRcompany();
            this.relationid = detailResp.getRelationid();
            this.duration = detailResp.getDuration();
            this.videoTypeName = detailResp.getVideoTypeName();
            this.episodes = detailResp.getEpisodes();
            this.nowEpisodes = detailResp.getNowEpisodes();
            if (SearchTpConstant.LESO_SRC_WEB.equals(detailResp.getSrc())) {
                this.starring = detailResp.getStarring();
            } else {
                this.starring = detailResp.getStarringName();
            }
            this.directory = detailResp.getDirectoryName();
            this.area = detailResp.getAreaName();
            this.description = detailResp.getDescription();
            this.recordCompany = detailResp.getRecordCompany();
            if (detailResp.getRelationAlbums() != null) {
                this.relationAlbums = detailResp.getRelationAlbums().size();
            }
            this.playCount = detailResp.getPlayCount();
            this.actorInfo = this.parseActorInfo(detailResp, commonParam.getLangcode());
            this.videoList = this.parseVideoList(detailResp);
            if (detailResp.getImages1() != null) {
                for (Map<String, String> m : detailResp.getImages1().values()) {
                    if (m != null) {
                        String img = m.get("300*400");
                        if (img != null) {
                            this.poster20 = img;
                        }
                        img = m.get("150*200");
                        if (this.poster20 == null) {
                            this.poster20 = img;
                        }
                        img = m.get("120*160");
                        if (this.poster20 == null) {
                            this.poster20 = img;
                        }
                        img = m.get("96*128");
                        if (this.poster20 == null) {
                            this.poster20 = img;
                        }
                        if (this.poster20 == null) {
                            this.poster20 = img;
                        }
                    }
                }
            }
            if (SearchTpConstant.LESO_SRC_WEB.equals(this.src)) {
                if (detailResp.getPostS2() != null && detailResp.getPostS2().trim().length() > 0
                        && this.poster20 == null) {
                    this.poster20 = detailResp.getPostS2();
                } else if (detailResp.getPostS1() != null && detailResp.getPostS1().trim().length() > 0) {
                    this.poster20 = detailResp.getPostS1();
                } else {
                    if (this.poster20 == null) {
                        this.poster20 = detailResp.getPostS3();
                    }
                }
            }
            if (this.nowEpisodes == null) {
                this.nowEpisodes = 0;
            }
            if (this.starring != null) {
                this.starring = this.starring.replaceAll(";", ",");
            }
            if (this.directory != null) {
                this.directory = this.directory.replaceAll(";", ",");
            }
            if (this.poster20 != null) {
                this.poster20 = this.poster20.replaceAll(" ", "%20");
            }
            this.pushFlag = detailResp.getPushFlag();

            if (detailResp.getFitAge() != null) {
                this.fitAge = detailResp.getFitAge().replaceAll("511001", "6岁以下").replaceAll("511002", "6-12岁")
                        .replaceAll("511003", "12-18岁").replaceAll("511004", "18岁以上").replaceAll("512001", "备孕期")
                        .replaceAll("512002", "孕育期").replaceAll("512003", "0-3岁").replaceAll("512004", "4-6岁");
            }
            this.letv_original_id = detailResp.getLetv_original_id();
        }
    }

    private List<AlbumDetailStarInfoDto> parseActorInfo(AlbumDetailTpResponse detailResp, String langcode) {
        List<AlbumDetailStarInfoDto> tmpList = new LinkedList<AlbumDetailStarInfoDto>();
        if (detailResp.getDirectoriesPoster() != null && detailResp.getDirectoriesPoster().size() > 0) {
            AlbumDetailTpResponse.AlbumDetailStarsPoster directory = detailResp.getDirectoriesPoster().get(0);
            if (detailResp.getCategory() != null && detailResp.getCategory() == CategoryConstants.CARTOON) {
                // directory.setRole("原作");
                directory.setRole(MessageUtils.getMessage(ORIGINAL_AUTHOR, langcode));
            } else {
                // directory.setRole("导演");
                directory.setRole(MessageUtils.getMessage(DIRECTOR, langcode));
            }

            tmpList.add(new AlbumDetailStarInfoDto(directory));
        }
        if (detailResp.getStarsPoster() != null && detailResp.getStarsPoster().size() > 0) {
            for (int i = 0; i < detailResp.getStarsPoster().size(); i++) {
                AlbumDetailTpResponse.AlbumDetailStarsPoster star = detailResp.getStarsPoster().get(i);

                if (detailResp.getCategory() != null && detailResp.getCategory() == CategoryConstants.CARTOON) {
                    // star.setRole("声优");
                    star.setRole(MessageUtils.getMessage(VOICE_ROLE, langcode));
                } else {
                    // star.setRole("主演");
                    star.setRole(MessageUtils.getMessage(LEADING_ROLE, langcode));
                }

                tmpList.add(new AlbumDetailStarInfoDto(star));
            }
        }

        return tmpList;
    }

    private List<AlbumDetailVideoInfoDto> parseVideoList(AlbumDetailTpResponse detailResp) {
        List<AlbumDetailTpResponse.VideoDetail> tpList = detailResp.getVideoList();
        List<AlbumDetailVideoInfoDto> tmpList = null;
        if (tpList != null) {
            tmpList = new LinkedList<AlbumDetailVideoInfoDto>();
            if (SearchTpConstant.LESO_SRC_VRS.equals(this.getSrc()) && this.getCategoryId() != null
                    && this.getCategoryId() == CategoryConstants.TV
                    && String.valueOf(VideoConstants.VideoType.ZHENG_PIAN).equals(this.vType)) { // 内网,电视剧,正片
                for (AlbumDetailTpResponse.VideoDetail video : tpList) {
                    try {
                        int aorder = 0;
                        if (!StringUtil.isBlank(video.getAorder())) {
                            aorder = Integer.parseInt(video.getAorder());
                        }
                        int nowEpisodes = 0;
                        if (this.getNowEpisodes() != null) {
                            nowEpisodes = this.getNowEpisodes();
                        }
                        if (!(String.valueOf(VideoConstants.VideoType.YU_GAO_PIAN).equals(video.getVideoType()) && aorder <= nowEpisodes)) {
                            tmpList.add(new AlbumDetailVideoInfoDto(video, detailResp.getSrc()));
                        }
                    } catch (Exception e) {
                        log.error("parseVideoList_error", e.getMessage(), e);
                    }
                }
            } else if (SearchTpConstant.LESO_SRC_WEB.equals(this.getSrc())
                    && "nets".equalsIgnoreCase(detailResp.getSubSrc())) {
                // subSrc=nets为云盘数据 url为分享码，为兼容TV版和乐搜老版本,云盘数据做特殊逻辑处理,
                // url需要拼接一个固定地址。
                String fixUrl = ApplicationUtils.get(ApplicationConstants.YUNPAN_VIDEO_DATA_GET);
                for (AlbumDetailTpResponse.VideoDetail video : tpList) {
                    if (video != null && video.getUrl() != null) {
                        video.setUrl(fixUrl + video.getUrl());
                        tmpList.add(new AlbumDetailVideoInfoDto(video, detailResp.getSrc()));
                    }
                }

            } else {
                for (AlbumDetailTpResponse.VideoDetail video : tpList) {
                    tmpList.add(new AlbumDetailVideoInfoDto(video, detailResp.getSrc()));
                }
            }
        }
        return tmpList;

    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Long getAid() {
        return this.aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLetv_original_id() {
        return this.letv_original_id;
    }

    public void setLetv_original_id(String letv_original_id) {
        this.letv_original_id = letv_original_id;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getvType() {
        return this.vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getVideoTypeName() {
        return this.videoTypeName;
    }

    public void setVideoTypeName(String videoTypeName) {
        this.videoTypeName = videoTypeName;
    }

    public String getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(String episodes) {
        this.episodes = episodes;
    }

    public Integer getNowEpisodes() {
        return this.nowEpisodes;
    }

    public void setNowEpisodes(Integer nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getPushFlag() {
        return this.pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPlayStatus() {
        return this.playStatus;
    }

    public void setPlayStatus(String playStatus) {
        this.playStatus = playStatus;
    }

    public String getRelationid() {
        return this.relationid;
    }

    public void setRelationid(String relationid) {
        this.relationid = relationid;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public int getRelationAlbums() {
        return this.relationAlbums;
    }

    public void setRelationAlbums(int relationAlbums) {
        this.relationAlbums = relationAlbums;
    }

    public List<AlbumDetailStarInfoDto> getActorInfo() {
        return this.actorInfo;
    }

    public void setActorInfo(List<AlbumDetailStarInfoDto> actorInfo) {
        this.actorInfo = actorInfo;
    }

    public String getRcompany() {
        return this.rcompany;
    }

    public void setRcompany(String rcompany) {
        this.rcompany = rcompany;
    }

    public String getRecordCompany() {
        return this.recordCompany;
    }

    public void setRecordCompany(String recordCompany) {
        this.recordCompany = recordCompany;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getActorName() {
        return this.actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getFitAge() {
        return this.fitAge;
    }

    public void setFitAge(String fitAge) {
        this.fitAge = fitAge;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getContentRating() {
        return this.contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public List<AlbumDetailVideoInfoDto> getVideoList() {
        return this.videoList;
    }

    public void setVideoList(List<AlbumDetailVideoInfoDto> videoList) {
        this.videoList = videoList;
    }

    /**
     * 演员信息
     */
    public static class AlbumDetailStarInfoDto {
        private String id;
        private String name;
        private String poster;
        private String role;

        public AlbumDetailStarInfoDto() {
        }

        public AlbumDetailStarInfoDto(AlbumDetailTpResponse.AlbumDetailStarsPoster star) {
            if (star != null) {
                this.id = star.getId();
                this.name = star.getName();
                this.poster = star.getPoster();
                this.role = star.getRole();
            }
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPoster() {
            return this.poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getRole() {
            return this.role;
        }

        public void setRole(String role) {
            this.role = role;
        }

    }

    /**
     * 专辑详情页的视频列表
     */
    public static class AlbumDetailVideoInfoDto {

        private String episodes; // 当前集数
        private String aorder; // 在专辑中的顺序
        private String name; // 视频名称
        private String tag; // 视频标签
        private String videoPic; // 视频图片
        private String videoType; // 视频类型
        private Long duration; // 时长
        private String url; // 播放地址
        private String newPushFlag; // 推送平台 420007等
        private String vid; // 视频id
        private String releaseDate; // 上映日期
        private Map<String, String> image; // 视频图片
        private String src; // 来源
        private String vType; // 视频类型
        private String subName; // 综艺的看点
        private String actorName; // 演员名称
        private String mid; // 媒资id
        private String externalId;// 第三方数据源ID
        private JSONObject externalPlayId;// 华数的视频ID

        public AlbumDetailVideoInfoDto() {
        }

        public AlbumDetailVideoInfoDto(AlbumDetailTpResponse.VideoDetail video, String src) {
            if (video != null) {
                this.externalPlayId = video.getExternal_play_id();
                this.externalId = video.getExternal_id();
                this.episodes = video.getEpisodes();
                this.aorder = video.getAorder();
                this.name = video.getName();
                this.tag = video.getTag();
                this.videoPic = video.getVideoPic();
                this.vType = video.getVideoType();
                this.duration = video.getDuration();
                if (StringUtil.isNotBlank(video.getUrl())) {
                    if (StringUtil.isNotBlank(ApplicationConstants.WEBSITE_WWW_LETV_COM)
                            && StringUtil.isNotBlank(ApplicationConstants.WEBSITE_WWW_LE_COM)) {
                        this.url = video.getUrl().replaceAll(ApplicationConstants.WEBSITE_WWW_LETV_COM,
                                ApplicationConstants.WEBSITE_WWW_LE_COM);
                    }
                }
                this.newPushFlag = video.getNewPushFlag();
                this.vid = video.getVid();
                this.releaseDate = video.getReleaseDate();
                this.src = src;
                this.subName = video.getSubName();
                this.actorName = video.getActorName();
                this.mid = video.getMid();

                if (String.valueOf(VideoConstants.VideoType.ZHENG_PIAN).equals(video.getVideoType())
                        || SearchTpConstant.LESO_SRC_WEB.equals(this.src)) { // 正片或者外网视频
                    this.videoType = "1"; // 老版本逻辑;暂不清楚原因
                } else {
                    this.videoType = "2";
                }

                if (video.getCategory() != null
                        && (video.getCategory() == CategoryConstants.TV || video.getCategory() == CategoryConstants.CARTOON)) {
                    // 电视剧和动漫频道的正片，数据量较大，而且客户端不需要图片，将图片强行置空
                    if (String.valueOf(VideoConstants.VideoType.ZHENG_PIAN).equals(video.getvType())) {
                        this.image = null;
                    } else {
                        if (video.getImages1() != null) {
                            Map m = new HashMap<String, String>();

                            if (video.getImages1().get("ar43") != null) {
                                Map mtemp = video.getImages1().get("ar43");
                                m.putAll(mtemp);
                            }
                            this.image = m;
                        }
                    }
                } else {
                    if (video.getImages1() != null) {
                        Map m = new HashMap<String, String>();

                        if (video.getImages1().get("ar43") != null) {
                            Map mtemp = video.getImages1().get("ar43");
                            m.putAll(mtemp);
                        }
                        this.image = m;
                    }
                }

            }
        }

        public JSONObject getExternalPlayId() {
            return externalPlayId;
        }

        public void setExternalPlayId(JSONObject externalPlayId) {
            this.externalPlayId = externalPlayId;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

        public String getAorder() {
            return this.aorder;
        }

        public void setAorder(String aorder) {
            this.aorder = aorder;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getVideoPic() {
            return this.videoPic;
        }

        public void setVideoPic(String videoPic) {
            this.videoPic = videoPic;
        }

        public String getVideoType() {
            return this.videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public Long getDuration() {
            return this.duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getNewPushFlag() {
            return this.newPushFlag;
        }

        public void setNewPushFlag(String newPushFlag) {
            this.newPushFlag = newPushFlag;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getReleaseDate() {
            return this.releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public Map<String, String> getImage() {
            return this.image;
        }

        public void setImage(Map<String, String> image) {
            this.image = image;
        }

        public String getSrc() {
            return this.src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getvType() {
            return this.vType;
        }

        public void setvType(String vType) {
            this.vType = vType;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getActorName() {
            return this.actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public String getMid() {
            return this.mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

    }

}
