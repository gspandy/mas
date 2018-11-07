package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 外网站点剧集实体
 */
public class AlbumDetailWebsiteTpResponse {
    private final static Logger LOG = LoggerFactory.getLogger(AlbumDetailWebsiteTpResponse.class);

    private List<AlbumDetailWebSiteDto> data;

    public AlbumDetailWebsiteTpResponse(List<AlbumDetailWebSiteDto> data) {
        this.data = data;
    }

    public List<AlbumDetailWebSiteDto> getData() {
        return this.data;
    }

    public void setData(List<AlbumDetailWebSiteDto> data) {
        this.data = data;
    }

    public static class AlbumDetailWebSiteDto {
        private Long aid;// leso的专辑id
        private Integer dataType;// 数据类型 1专辑、2视频、3明星
        private String site;// 站点名称
        private Integer isTv;// 是否tv版
        private Integer nowEpisodes;// 当前此站点更新到集数
        private Long vrsAid;// 如果vrs里有此专辑、vrsAid
        private Integer episodeStatus;
        private Integer episodes;// 总集数
        private List<VideoDetailWebsite> videoList;
        private String isEnd;// 是否完结 0、1
        private String letv_original_id;// 数据上报用到
        private String external_id;// 第三方数据源ID

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

        public Integer getDataType() {
            return this.dataType;
        }

        public void setDataType(Integer dataType) {
            this.dataType = dataType;
        }

        public String getSite() {
            return this.site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public Integer getIsTv() {
            return this.isTv;
        }

        public void setIsTv(Integer isTv) {
            this.isTv = isTv;
        }

        public Integer getNowEpisodes() {
            return this.nowEpisodes;
        }

        public void setNowEpisodes(Integer nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public Long getVrsAid() {
            return this.vrsAid;
        }

        public void setVrsAid(Long vrsAid) {
            this.vrsAid = vrsAid;
        }

        public Integer getEpisodeStatus() {
            return this.episodeStatus;
        }

        public void setEpisodeStatus(Integer episodeStatus) {
            this.episodeStatus = episodeStatus;
        }

        public Integer getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(Integer episodes) {
            this.episodes = episodes;
        }

        public List<VideoDetailWebsite> getVideoList() {
            return this.videoList;
        }

        public void setVideoList(List<VideoDetailWebsite> videoList) {
            this.videoList = videoList;
        }

        public String getIsEnd() {
            return this.episodeStatus + "";
        }
    }

    public static class VideoDetailWebsite {
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
        private JSONObject external_play_id;// 华数的视频ID

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
                LOG.error("getImages1_error", e.getMessage(), e);
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
            if (this.pushFlag != null && this.pushFlag.contains(CommonConstants.TV_PLAY_PLAT_FROM)) {
                return "5";
            }
            return "1";
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
