package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageAlbum;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageRecord;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageStar;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageTopicDataTpResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainPageTopicDataDto extends BaseDto {
    private Integer albumCount; // 专辑数量
    private Integer videoCount; // 视频数量
    private Integer starCount; // 明星数量
    private List<Album> albums; // 专辑数据
    private List<Video> videos; // 视频数据
    private List<Star> stars; // 明星数据
    private Integer pageSize; // 客户端请求的分页大小
    private Integer currentPageCount; // 本页实际返回数量
    private Integer currentPage; // 客户端请求的当前页

    public MainPageTopicDataDto() {
    }

    public MainPageTopicDataDto(MainPageTopicDataTpResponse.TopicData topic) {
        if (topic != null) {
            this.albumCount = topic.getAlbum_count();
            this.videoCount = topic.getRecord_count();
            this.starCount = topic.getStar_count();
            this.albums = new LinkedList<Album>();
            if (topic.getAlbums() != null) {
                List<MainPageAlbum> topicAlbumList = topic.getAlbums();
                for (MainPageAlbum topicAlbum : topicAlbumList) {
                    this.albums.add(new Album(topicAlbum));
                }
            }
            this.videos = new LinkedList<Video>();
            if (topic.getRecords() != null) {
                List<MainPageRecord> topicRecordList = topic.getRecords();
                for (MainPageRecord topicRecord : topicRecordList) {
                    this.videos.add(new Video(topicRecord));
                }
            }
            this.stars = new LinkedList<Star>();
            if (topic.getStars() != null) {
                List<MainPageStar> topicStarList = topic.getStars();
                for (MainPageStar topicStar : topicStarList) {
                    this.stars.add(new Star(topicStar));
                }
            }
        }
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPageCount() {
        if (this.albums != null && this.albums.size() > 0) {
            return this.albums.size();
        } else if (this.videos != null && this.videos.size() > 0) {
            return this.videos.size();
        } else if (this.stars != null && this.stars.size() > 0) {
            return this.stars.size();
        }

        return 0;
    }

    public void setCurrentPageCount(Integer currentPageCount) {
        this.currentPageCount = currentPageCount;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getAlbumCount() {
        return this.albumCount;
    }

    public void setAlbumCount(Integer albumCount) {
        this.albumCount = albumCount;
    }

    public Integer getVideoCount() {
        return this.videoCount;
    }

    public void setVideoCount(Integer videoCount) {
        this.videoCount = videoCount;
    }

    public Integer getStarCount() {
        return this.starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public List<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public List<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Star> getStars() {
        return this.stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public static class Album {
        private String nowEpisode; // 专辑更新到第几集
        private Map<String, String> cover; // 封面图片
        private String description; // 专辑描述
        private String id; // 专辑id
        private String lastEpisode; // 专辑最近更新的记录
        private String name; // 专辑名称
        private String pushFlag; // 推送平台
        private String rating; // 评分
        private String src; // 视频来源 1:letv 2: 外网视频
        private String subSrc; // 视频来源，例如youku iqiyi
        private String subname; // 专辑子标题
        private String episode; // 专辑总集数
        private String url; // 播放地址
        private Integer dataType;// 数据类型 1专辑、2视频、3明星、4专题
        private String videoType;// 影片类型 ,例如180001代表正片
        private String isHomemade; // 是否自制
        private String ispay; // 是否付费
        private String payPlatform; // 支付平台
        private String letv_original_id;// 数据上报用到
        private String gid;

        public Album() {
        }

        public Album(MainPageAlbum lesoAlbum) {
            if (lesoAlbum != null) {
                this.nowEpisode = lesoAlbum.getAvail_records();
                this.cover = lesoAlbum.getCover();
                this.description = lesoAlbum.getDescription();
                this.id = lesoAlbum.getId();
                this.name = lesoAlbum.getName();
                this.pushFlag = lesoAlbum.getPush_flag();
                this.rating = lesoAlbum.getRating();
                this.src = lesoAlbum.getSrc();
                this.subSrc = lesoAlbum.getSub_src();
                this.subname = lesoAlbum.getSubname();
                this.episode = lesoAlbum.getTotal_records();
                this.url = lesoAlbum.getUrl();
                this.dataType = lesoAlbum.getDataType();
                this.videoType = lesoAlbum.getVideoType();
                this.isHomemade = lesoAlbum.getIsHomemade();
                this.ispay = lesoAlbum.getIspay();
                this.payPlatform = lesoAlbum.getPayPlatform();
                this.letv_original_id = lesoAlbum.getLetv_original_id();
                this.gid = lesoAlbum.getGlobal_id();
            }
        }

        public String getGid() {
            return this.gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getLetv_original_id() {
            return this.letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public Integer getDataType() {
            return this.dataType;
        }

        public void setDataType(Integer dataType) {
            this.dataType = dataType;
        }

        public String getVideoType() {
            return this.videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
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

        public String getPayPlatform() {
            return this.payPlatform;
        }

        public void setPayPlatform(String payPlatform) {
            this.payPlatform = payPlatform;
        }

        public String getNowEpisode() {
            return this.nowEpisode;
        }

        public void setNowEpisode(String nowEpisode) {
            this.nowEpisode = nowEpisode;
        }

        public Map<String, String> getCover() {
            return this.cover;
        }

        public void setCover(Map<String, String> cover) {
            this.cover = cover;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLastEpisode() {
            return this.lastEpisode;
        }

        public void setLastEpisode(String lastEpisode) {
            this.lastEpisode = lastEpisode;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getEpisode() {
            return this.episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public static class Video {
        private String id; // 视频id
        private String topic; // 对应的话题
        private String name; // 视频名称
        private String subname; // 视频子名称
        private String src; // 视频来源 1:letv 2:外网
        private String subSrc; // 视频子来源 例如 youku iqiyi
        private String pushFlag; // 视频推送平台
        private String rating; // 评分
        private Map<String, String> cover; // 图片
        private String url; // 播放地址
        private String description; // 描述
        private String letv_original_id;// 数据上报用到

        public Video() {
        }

        public Video(MainPageRecord record) {
            if (record != null) {
                this.id = record.getId();
                this.topic = record.getTopic();
                this.name = record.getName();
                this.subname = record.getSubname();
                this.src = record.getSrc();
                this.subSrc = record.getSub_src();
                this.pushFlag = record.getPush_flag();
                this.rating = record.getRating();
                this.cover = record.getCover();
                this.url = record.getUrl();
                this.description = record.getDescription();
                this.letv_original_id = record.getLetv_original_id();
            }
        }

        public String getLetv_original_id() {
            return this.letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTopic() {
            return this.topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSubname() {
            return this.subname;
        }

        public void setSubname(String subname) {
            this.subname = subname;
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

        public Map<String, String> getCover() {
            return this.cover;
        }

        public void setCover(Map<String, String> cover) {
            this.cover = cover;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Star {
        private String id; // 明星id
        private String name; // 明星姓名
        private Map<String, String> cover; // 图片
        private String letv_original_id;// 数据上报用到

        public Star() {
        }

        public Star(MainPageStar star) {
            if (star != null) {
                this.id = star.getId();
                this.name = star.getName();
                this.cover = star.getPoster();
                this.letv_original_id = star.getLetv_original_id();
            }
        }

        public String getLetv_original_id() {
            return this.letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
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

        public Map<String, String> getCover() {
            return this.cover;
        }

        public void setCover(Map<String, String> cover) {
            this.cover = cover;
        }

    }
}
