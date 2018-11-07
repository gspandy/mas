package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AlbumDetailTpResponse {
    private final static Logger LOG = LoggerFactory.getLogger(AlbumDetailTpResponse.class);

    private Long aid;// 专辑id，根据src的不同。可能是leso专辑id和vrs专辑id
    private String area;// 媒资地区id
    private String areaName;// 地区名称
    private Integer category;// 一级类型id
    private String categoryName;// 一级类型名称
    private Integer dataType; // 数据类型 1专辑、2视频、3明星
    private String dayCount;// 日访问量
    private String description;// 长描述
    private String shortDesc;// 短描述
    private String detailUrl; // 详情页的页面地址
    private List<AlbumDetailStarsPoster> directoriesPoster; // 导演信息
    private String directory;// 站外导演
    private String directoryName;// 导演
    private String duration; // 时长
    private String englishName; // 英文名称
    private String episodes;// 总集数
    private Integer nowEpisodes;// 当前更新到集数
    private String globalVids; // 专辑下视频列表
    private String intro;
    private String isEnd;// 是否完结 0:未完结 1:完结
    private String ispay; // 是否未付费影片 0:非付费 1:付费
    private String language; // 影片语言
    private String name;// 名称
    private String nameJianpin; // 名称的中文拼音缩写
    private String nameQuanpin; // 名称的中文拼音全拼
    private String otherName; // 其他名称
    private String panid; // 云盘数据的id
    private String playStreams; // 播放码流 例如：标清
    private String postS1; // 竖图图片地址
    private String postS2; // 竖图图片地址
    private String postS3; // 竖图图片地址
    private String pushFlag; // 播放平台 例如: 420001,420007
    private String qname; // 查询名称
    private String rating; // 评分
    private String releaseDate;// 上映时间
    private String src;// 来源id.0 来自PTV；1来自VRS；2来自外网；3来自IPTV
    private String subSrc;// 来源名称sohu/qiyi等
    private String subname;
    private String starring;
    private String starringName;// 主演
    private List<AlbumDetailStarsPoster> starsPoster;
    private String subCategory;// 二级类型
    private String subCategoryName;// 二级类型名称
    private String tag; // 专辑的标签
    private String taglist;
    private String url;// 默认播放地址
    private String videoType;// 影片类型 1正片等
    private String videoTypeName;// 正片等
    private String vids;// 拥有视频ids
    private String playCount;// 播放总数
    private String weekCount;// 周播放数
    private String firstPlayTime;
    private String styleName;
    private String vid;
    private Map<String, Map<String, String>> images1;// 新媒资图片
    private String images;// 新媒资图片
    private List<VideoDetail> videoList;// 拥有剧集
    private String playStatus;
    private String relationid;
    private String actorName;
    private String rcompany;
    private String recordCompany;
    private String actor;
    private String style;
    private String varietyShow;
    private String fitAge;
    private String contentRating;// 内容评级
    private List<AlbumDetailRelationAlbums> relationAlbums;
    private String letv_original_id;// 数据上报用到
    private String external_id;// 第三方ID

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getLetv_original_id() {
        return this.letv_original_id;
    }

    public void setLetv_original_id(String letv_original_id) {
        this.letv_original_id = letv_original_id;
    }

    public Long getAid() {
        return this.aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
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

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getDayCount() {
        return this.dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDetailUrl() {
        return this.detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public List<AlbumDetailStarsPoster> getDirectoriesPoster() {
        return this.directoriesPoster;
    }

    public void setDirectoriesPoster(List<AlbumDetailStarsPoster> directoriesPoster) {
        this.directoriesPoster = directoriesPoster;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
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

    public String getGlobalVids() {
        return this.globalVids;
    }

    public void setGlobalVids(String globalVids) {
        this.globalVids = globalVids;
    }

    public String getIntro() {
        return this.intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIsEnd() {
        return this.isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getIspay() {
        return this.ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameJianpin() {
        return this.nameJianpin;
    }

    public void setNameJianpin(String nameJianpin) {
        this.nameJianpin = nameJianpin;
    }

    public String getNameQuanpin() {
        return this.nameQuanpin;
    }

    public void setNameQuanpin(String nameQuanpin) {
        this.nameQuanpin = nameQuanpin;
    }

    public String getOtherName() {
        return this.otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getPanid() {
        return this.panid;
    }

    public void setPanid(String panid) {
        this.panid = panid;
    }

    public String getPlayStreams() {
        return this.playStreams;
    }

    public void setPlayStreams(String playStreams) {
        this.playStreams = playStreams;
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

    public String getPushFlag() {
        return this.pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getQname() {
        return this.qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getSubname() {
        return this.subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getStarringName() {
        return this.starringName;
    }

    public void setStarringName(String starringName) {
        this.starringName = starringName;
    }

    public List<AlbumDetailStarsPoster> getStarsPoster() {
        return this.starsPoster;
    }

    public void setStarsPoster(List<AlbumDetailStarsPoster> starsPoster) {
        this.starsPoster = starsPoster;
    }

    public String getSubCategory() {
        return this.subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategoryName() {
        return this.subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTaglist() {
        return this.taglist;
    }

    public void setTaglist(String taglist) {
        this.taglist = taglist;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getVids() {
        return this.vids;
    }

    public void setVids(String vids) {
        this.vids = vids;
    }

    public String getPlayCount() {
        return this.playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getWeekCount() {
        return this.weekCount;
    }

    public void setWeekCount(String weekCount) {
        this.weekCount = weekCount;
    }

    public String getFirstPlayTime() {
        return this.firstPlayTime;
    }

    public void setFirstPlayTime(String firstPlayTime) {
        this.firstPlayTime = firstPlayTime;
    }

    public String getStyleName() {
        return this.styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getVid() {
        return this.vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public Map<String, Map<String, String>> getImages1() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (StringUtil.isNotBlank(this.images)) {
                this.images1 = objectMapper.readValue(this.images,
                        new TypeReference<Map<String, Map<String, String>>>() {
                        });
            }
        } catch (Exception e) {
            LOG.warn("AlbumDetailTpResponse_getImages1_" + this.aid + "_[ " + this.images + " ]_" + e.getMessage());
        }
        return this.images1;
    }

    public void setImages1(Map<String, Map<String, String>> images1) {
        this.images1 = images1;
    }

    public String getImages() {
        return this.images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<VideoDetail> getVideoList() {
        return this.videoList;
    }

    public void setVideoList(List<VideoDetail> videoList) {
        this.videoList = videoList;
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

    public String getActorName() {
        return this.actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
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

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getStyle() {
        return this.style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getVarietyShow() {
        return this.varietyShow;
    }

    public void setVarietyShow(String varietyShow) {
        this.varietyShow = varietyShow;
    }

    public String getFitAge() {
        return this.fitAge;
    }

    public void setFitAge(String fitAge) {
        this.fitAge = fitAge;
    }

    public String getContentRating() {
        return this.contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public List<AlbumDetailRelationAlbums> getRelationAlbums() {
        return this.relationAlbums;
    }

    public void setRelationAlbums(List<AlbumDetailRelationAlbums> relationAlbums) {
        this.relationAlbums = relationAlbums;
    }

    /**
     * 搜索详情页明星信息
     */
    public static class AlbumDetailStarsPoster {
        private String id;
        private String name;
        private String poster;
        private String role;

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
     * 搜索详情页相关剧集信息
     */
    public static class AlbumDetailRelationAlbums {
        private String aid;
        private String name;

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
    }

    /**
     * 搜索详情页专辑下视频列表信息
     */
    public static class VideoDetail {
        private String aorder;
        private String actorName;
        private Integer dataType;
        private String name;
        private String url;
        private Long aid;
        private String allowDownload;
        private Integer category;
        private Integer categoryId;
        private String categoryName;
        private String ctime;
        private String dayCount;
        private Long duration;
        private Integer isDisplay;
        private Integer isHomemade;
        private String mtime;
        private String playCount;
        private String playStreams;
        private String pushFlag;
        private String newPushFlag;
        private String releaseDate;
        private String src;
        private String subName;
        private String tag;
        private String vid;
        private String videoPic;
        private String videoType;
        private String vType;
        private String videoTypeName;
        private String porder;
        private String channel;
        private String displayStartTime;
        private String playDate;
        private String playNum;
        private String playUrl;
        private String startTime;
        private String title;
        private String episodes;
        private String images;
        private Map<String, Map<String, String>> images1;
        private Map<String, String> image;
        private String mid;
        private String external_id;
        private JSONObject external_play_id;

        public JSONObject getExternal_play_id() {
            return external_play_id;
        }

        public void setExternal_play_id(JSONObject external_play_id) {
            this.external_play_id = external_play_id;
        }

        public String getExternal_id() {
            return external_id;
        }

        public void setExternal_id(String external_id) {
            this.external_id = external_id;
        }

        public String getActorName() {
            return this.actorName;
        }

        public void setActorName(String actorName) {
            this.actorName = actorName;
        }

        public String getImages() {
            return this.images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public Map<String, Map<String, String>> getImages1() {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                if (this.images != null && this.images.trim().length() > 0) {
                    this.images1 = objectMapper.readValue(this.images,
                            new TypeReference<Map<String, Map<String, String>>>() {
                            });
                }

            } catch (Exception e) {
                LOG.warn("getImages1_error_ images:" + images);
            }
            return this.images1;
        }

        public void setImages1(Map<String, Map<String, String>> images1) {
            this.images1 = images1;
        }

        public String getNewPushFlag() {
            return this.newPushFlag;
        }

        public void setNewPushFlag(String newPushFlag) {
            this.newPushFlag = newPushFlag;
        }

        public String getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

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

        public String getPorder() {
            return this.porder;
        }

        public void setPorder(String porder) {
            this.aorder = porder;
            this.porder = porder;
        }

        public String getAorder() {
            return this.aorder;
        }

        public void setAorder(String aorder) {
            this.aorder = aorder;
        }

        public Integer getDataType() {
            return this.dataType;
        }

        public void setDataType(Integer dataType) {
            this.dataType = dataType;
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

        public String getvType() {
            return this.vType;
        }

        public void setvType(String vType) {
            this.vType = vType;
        }

        public Long getAid() {
            return this.aid;
        }

        public void setAid(Long aid) {
            this.aid = aid;
        }

        public String getAllowDownload() {
            return this.allowDownload;
        }

        public void setAllowDownload(String allowDownload) {
            this.allowDownload = allowDownload;
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

        public String getCtime() {
            return this.ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getDayCount() {
            return this.dayCount;
        }

        public void setDayCount(String dayCount) {
            this.dayCount = dayCount;
        }

        public Long getDuration() {
            return this.duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public Integer getIsDisplay() {
            return this.isDisplay;
        }

        public void setIsDisplay(Integer isDisplay) {
            this.isDisplay = isDisplay;
        }

        public Integer getIsHomemade() {
            return this.isHomemade;
        }

        public void setIsHomemade(Integer isHomemade) {
            this.isHomemade = isHomemade;
        }

        public String getMtime() {
            return this.mtime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public String getPlayCount() {
            return this.playCount;
        }

        public void setPlayCount(String playCount) {
            this.playCount = playCount;
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
            this.newPushFlag = pushFlag;
            this.pushFlag = pushFlag;
        }

        public String getReleaseDate() {
            return this.releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getSrc() {
            return this.src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getSubName() {
            return this.subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
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

        public String getVideoTypeName() {
            return this.videoTypeName;
        }

        public void setVideoTypeName(String videoTypeName) {
            this.videoTypeName = videoTypeName;
        }

        public Integer getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Map<String, String> getImage() {
            return this.image;
        }

        public void setImage(Map<String, String> image) {
            this.image = image;
        }

        public String getMid() {
            return this.mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

    }

}
