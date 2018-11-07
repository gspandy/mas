package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 特辑包里的专题包数据列表
 * @author liyunlong
 */
public class SubjectTjPackageTpResponse implements Serializable {

    private static final long serialVersionUID = -4663913406617046411L;

    /**
     * 专辑包ID
     */
    private String id;

    /**
     * json域
     */
    private Map<String, String> jsonMap;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer porder;

    /**
     * 特辑包类型，1：专辑包，2：视频包，3：直播包
     */
    private Integer ptype;

    /**
     *
     */
    private Integer showSubscript;

    /**
     * 特辑ID
     */
    private String tjId;

    /**
     * 专题包、视频包、直播包的图片
     */
    private String packagePic;

    /**
     * 专题包里视频数据列表
     */
    private List<SubjectTjPackageDataContent> dataList;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getJsonMap() {
        return this.jsonMap;
    }

    public void setJsonMap(Map<String, String> jsonMap) {
        this.jsonMap = jsonMap;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPorder() {
        return this.porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getPtype() {
        return this.ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getShowSubscript() {
        return this.showSubscript;
    }

    public void setShowSubscript(Integer showSubscript) {
        this.showSubscript = showSubscript;
    }

    public String getTjId() {
        return this.tjId;
    }

    public void setTjId(String tjId) {
        this.tjId = tjId;
    }

    public String getPackagePic() {
        return this.packagePic;
    }

    public void setPackagePic(String packagePic) {
        this.packagePic = packagePic;
    }

    public List<SubjectTjPackageDataContent> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<SubjectTjPackageDataContent> dataList) {
        this.dataList = dataList;
    }

    /**
     * 专辑包|视频包|直播包里视频数据列表，取三者字段的并集
     * @author liyunlong
     */
    public static class SubjectTjPackageDataContent implements Serializable {
        private static final long serialVersionUID = -4111021976199311685L;

        /**
         * 专辑|视频|直播 ID
         */
        private String id;

        /**
         * 直播图片
         */
        private String pic21;

        /**
         * 直播图片
         */
        private String pic43;

        /**
         *
         */
        private String rid;

        /**
         * 直播名称
         */
        private String rname;

        /**
         * 直播顺序
         */
        private Integer rorder;

        /**
         * 专辑类型
         */
        private Map<String, String> albumType;

        /**
         * 分类
         */
        private Map<String, String> category;

        /**
         * 删除标志
         */
        private Integer deleted;

        /**
         * 字符串类型，不能是整型，CMS有时会返回非数字
         */
        private String episode;

        /**
         * 是否结束
         */
        private Integer isEnd;

        /**
         * 是否是自制内容
         */
        private Integer isHomemade;

        /**
         * 下载平台
         */
        private Map<String, String> downloadPlatform;

        /**
         * 时长
         */
        private Long duration;

        /**
        *
        */
        private Integer issue;

        /**
         * 媒资ID
         */
        private String mid;

        /**
         * 中文名称
         */
        private String nameCn;

        /**
         *
         */
        private Integer nowEpisodes;

        /**
         *
         */
        private Integer nowIssue;

        /**
        *
        */
        private String officialUrl;

        /**
         * 专辑包中专辑的所有图片集合
         */
        private Map<String, String> picCollections;

        /**
         *
         */
        private Map<String, String> platformNowEpisodesNum;

        /**
         *
         */
        private Map<String, String> platformVideoInfo;

        /**
         *
         */
        private Map<String, String> platformVideoNum;

        /**
         *
         */
        private String relationId;

        /**
         * 主演
         */
        private Map<String, String> starring;

        /**
         * 子分类
         */
        private Map<String, String> subCategory;

        /**
         * 副标题
         */
        private String subTitle;

        /**
         * 视频包中视频所有的图片集合
         */
        private Map<String, String> picAll;

        /**
         * 专辑ID
         */
        private String pid;

        /**
         * 推送平台，ptype=1时，map类型；ptype=2时，map类型；ptype=3时，字符串类型，多个平台用逗号隔开
         */
        private Object playPlatform;

        /**
         * 歌唱者
         */
        private String singer;

        /**
         * 视频类型
         */
        private Map<String, String> videoType;

        /**
         * 投票数
         */
        private Long voteNum;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic21() {
            return this.pic21;
        }

        public void setPic21(String pic21) {
            this.pic21 = pic21;
        }

        public String getPic43() {
            return this.pic43;
        }

        public void setPic43(String pic43) {
            this.pic43 = pic43;
        }

        public String getRid() {
            return this.rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getRname() {
            return this.rname;
        }

        public void setRname(String rname) {
            this.rname = rname;
        }

        public Integer getRorder() {
            return this.rorder;
        }

        public void setRorder(Integer rorder) {
            this.rorder = rorder;
        }

        public Map<String, String> getAlbumType() {
            return this.albumType;
        }

        public void setAlbumType(Map<String, String> albumType) {
            this.albumType = albumType;
        }

        public Map<String, String> getCategory() {
            return this.category;
        }

        public void setCategory(Map<String, String> category) {
            this.category = category;
        }

        public Integer getDeleted() {
            return this.deleted;
        }

        public void setDeleted(Integer deleted) {
            this.deleted = deleted;
        }

        public String getEpisode() {
            return this.episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public Integer getIsEnd() {
            return this.isEnd;
        }

        public void setIsEnd(Integer isEnd) {
            this.isEnd = isEnd;
        }

        public Integer getIsHomemade() {
            return this.isHomemade;
        }

        public void setIsHomemade(Integer isHomemade) {
            this.isHomemade = isHomemade;
        }

        public Map<String, String> getDownloadPlatform() {
            return this.downloadPlatform;
        }

        public void setDownloadPlatform(Map<String, String> downloadPlatform) {
            this.downloadPlatform = downloadPlatform;
        }

        public Long getDuration() {
            return this.duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public Integer getIssue() {
            return this.issue;
        }

        public void setIssue(Integer issue) {
            this.issue = issue;
        }

        public String getMid() {
            return this.mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getNameCn() {
            return this.nameCn;
        }

        public void setNameCn(String nameCn) {
            this.nameCn = nameCn;
        }

        public Integer getNowEpisodes() {
            return this.nowEpisodes;
        }

        public void setNowEpisodes(Integer nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public Integer getNowIssue() {
            return this.nowIssue;
        }

        public void setNowIssue(Integer nowIssue) {
            this.nowIssue = nowIssue;
        }

        public String getOfficialUrl() {
            return this.officialUrl;
        }

        public void setOfficialUrl(String officialUrl) {
            this.officialUrl = officialUrl;
        }

        public Map<String, String> getPicCollections() {
            return this.picCollections;
        }

        public void setPicCollections(Map<String, String> picCollections) {
            this.picCollections = picCollections;
        }

        public Map<String, String> getPlatformNowEpisodesNum() {
            return this.platformNowEpisodesNum;
        }

        public void setPlatformNowEpisodesNum(Map<String, String> platformNowEpisodesNum) {
            this.platformNowEpisodesNum = platformNowEpisodesNum;
        }

        public Map<String, String> getPlatformVideoInfo() {
            return this.platformVideoInfo;
        }

        public void setPlatformVideoInfo(Map<String, String> platformVideoInfo) {
            this.platformVideoInfo = platformVideoInfo;
        }

        public Map<String, String> getPlatformVideoNum() {
            return this.platformVideoNum;
        }

        public void setPlatformVideoNum(Map<String, String> platformVideoNum) {
            this.platformVideoNum = platformVideoNum;
        }

        public String getRelationId() {
            return this.relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public Map<String, String> getStarring() {
            return this.starring;
        }

        public void setStarring(Map<String, String> starring) {
            this.starring = starring;
        }

        public Map<String, String> getSubCategory() {
            return this.subCategory;
        }

        public void setSubCategory(Map<String, String> subCategory) {
            this.subCategory = subCategory;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public Map<String, String> getPicAll() {
            return this.picAll;
        }

        public void setPicAll(Map<String, String> picAll) {
            this.picAll = picAll;
        }

        public String getPid() {
            return this.pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Object getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(Object playPlatform) {
            this.playPlatform = playPlatform;
        }

        public String getSinger() {
            return this.singer;
        }

        public void setSinger(String singer) {
            this.singer = singer;
        }

        public Map<String, String> getVideoType() {
            return this.videoType;
        }

        public void setVideoType(Map<String, String> videoType) {
            this.videoType = videoType;
        }

        public Long getVoteNum() {
            return this.voteNum;
        }

        public void setVoteNum(Long voteNum) {
            this.voteNum = voteNum;
        }

    }

}
