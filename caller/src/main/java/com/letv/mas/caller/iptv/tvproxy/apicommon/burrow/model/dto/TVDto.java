package com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil.BurrowTVResource;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.io.Serializable;
import java.util.Map;

public class TVDto {

    public static class TVAlbumBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = 8979849656167456840L;
        private Integer resource;// @BurrowTVResource
        private String albumId;
        private Integer src;
        private Integer tvCopyright;
        private Integer categoryId;
        private Integer subCategoryId;
        private Integer ifCharge;
        private Integer id;
        private Integer model;

        public Integer getResource() {
            return resource;
        }

        public void setResource(BurrowTVResource resource) {
            this.resource = resource.getResource();
        }

        public Integer getModel() {
            return model;
        }

        public void setModel(Integer model) {
            this.model = model;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public Integer getSrc() {
            return src;
        }

        public void setSrc(Integer src) {
            this.src = src;
        }

        public Integer getTvCopyright() {
            return tvCopyright;
        }

        public void setTvCopyright(Integer tvCopyright) {
            this.tvCopyright = tvCopyright;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(Integer subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public Integer getIfCharge() {
            return ifCharge;
        }

        public void setIfCharge(Integer ifCharge) {
            this.ifCharge = ifCharge;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public static class TVVideoBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -2096930453249043858L;
        private String videoId;
        private Integer resource;// @BurrowTVResource
        private String albumId;
        private String name;
        private String subName;
        private Integer categoryId;
        private String globalCid;
        private String subCategoryId;
        private Integer tvCopyright;
        private String webPlayUrl;
        private String website;
        private String external_id;
        private String source;
        private String ifCharge;
        private String defaultStream;
        private String defaultStreamName;
        private String globalId;
        private String id;
        private Integer model;

        public Integer getModel() {
            return model;
        }

        public void setModel(Integer model) {
            this.model = model;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public Integer getResource() {
            return resource;
        }

        public void setResource(BurrowTVResource resource) {
            this.resource = resource.getResource();
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getGlobalCid() {
            return globalCid;
        }

        public void setGlobalCid(String globalCid) {
            this.globalCid = globalCid;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public Integer getTvCopyright() {
            return tvCopyright;
        }

        public void setTvCopyright(Integer tvCopyright) {
            this.tvCopyright = tvCopyright;
        }

        public String getWebPlayUrl() {
            return webPlayUrl;
        }

        public void setWebPlayUrl(String webPlayUrl) {
            this.webPlayUrl = webPlayUrl;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getExternal_id() {
            return external_id;
        }

        public void setExternal_id(String external_id) {
            this.external_id = external_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getIfCharge() {
            return ifCharge;
        }

        public void setIfCharge(String ifCharge) {
            this.ifCharge = ifCharge;
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

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public static class TVSubjectBurrowDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -1843078685005405217L;
        private String subjectId;
        private Integer subjectType;
        private Integer categoryId;
        private String subCategoryId;
        private String id;
        private Integer type;
        private Integer resource;// @BurrowTVResource
        private Integer model;

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public Integer getResource() {
            return resource;
        }

        public void setResource(BurrowTVResource resource) {
            this.resource = resource.getResource();
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(Integer subjectType) {
            this.subjectType = subjectType;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getModel() {
            return model;
        }

        public void setModel(Integer model) {
            this.model = model;
        }

    }

    public static class TVChannelDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -7004196790253081872L;
        private int channelId;
        private int pageId;
        private String dataUrl;
        private String cpCategoryId;
        private String cpId;
        private String globalId;

        public int getChannelId() {
            return channelId;
        }

        public void setChannelId(int channelId) {
            this.channelId = channelId;
        }

        public int getPageId() {
            return pageId;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }

        public String getDataUrl() {
            return dataUrl;
        }

        public void setDataUrl(String dataUrl) {
            this.dataUrl = dataUrl;
        }

        public String getCpCategoryId() {
            return cpCategoryId;
        }

        public void setCpCategoryId(String cpCategoryId) {
            this.cpCategoryId = cpCategoryId;
        }

        public String getCpId() {
            return cpId;
        }

        public void setCpId(String cpId) {
            this.cpId = cpId;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }
    }

    public static class TVBrowseDto extends BaseDto implements Serializable {
        private static final long serialVersionUID = -7229193297421048770L;
        private int browserType;
        private int openType;
        private String url;
        private Map<String, String> urlMap;
        private int src;
        private String website;
        private String albumId;
        private String name;

        public int getBrowserType() {
            return browserType;
        }

        public void setBrowserType(int browserType) {
            this.browserType = browserType;
        }

        public int getOpenType() {
            return openType;
        }

        public void setOpenType(int openType) {
            this.openType = openType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, String> getUrlMap() {
            return urlMap;
        }

        public void setUrlMap(Map<String, String> urlMap) {
            this.urlMap = urlMap;
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class TVPreLive extends BaseDto implements Serializable {
        private static final long serialVersionUID = 7417125319712646679L;
        private String cinemaId;

        public String getCinemaId() {
            return cinemaId;
        }

        public void setCinemaId(String cinemaId) {
            this.cinemaId = cinemaId;
        }
    }

    public static class TVContainer extends BaseDto implements Serializable {
        private static final long serialVersionUID = 4659577039923444343L;
        private String containerId;

        public String getContainerId() {
            return containerId;
        }

        public void setContainerId(String containerId) {
            this.containerId = containerId;
        }
    }

    public static class TVLive extends BaseDto implements Serializable {
        private static final long serialVersionUID = 3023539737001642564L;
        private String liveId;
        private String categoryId;
        private boolean isFree;
        private int sourceType;

        public String getLiveId() {
            return liveId;
        }

        public void setLiveId(String liveId) {
            this.liveId = liveId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public boolean isFree() {
            return isFree;
        }

        public void setFree(boolean isFree) {
            this.isFree = isFree;
        }

        public int getSourceType() {
            return sourceType;
        }

        public void setSourceType(int sourceType) {
            this.sourceType = sourceType;
        }
    }

    public static class TVPage extends BaseDto implements Serializable {
        private static final long serialVersionUID = 4771957327606228830L;
        private int pageId;

        public int getPageId() {
            return pageId;
        }

        public void setPageId(int pageId) {
            this.pageId = pageId;
        }
    }

    public static class TVStar extends BaseDto implements Serializable {
        private static final long serialVersionUID = -4702063910097065435L;
        private String starId;

        public String getStarId() {
            return starId;
        }

        public void setStarId(String starId) {
            this.starId = starId;
        }
    }
}
