package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageAlbum;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPagePushAlbumsTpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainPageHeadDto extends BaseDto {
    private String description;
    private String name;
    private List<Album> albums;
    private String module;

    public MainPageHeadDto() {
    }

    public MainPageHeadDto(MainPagePushAlbumsTpResponse.MastHeadObject mastHead, String module) {
        if (mastHead != null) {
            this.description = mastHead.getDescription();
            this.name = mastHead.getName();
            this.albums = new ArrayList<Album>();
            this.module = module;
            if (mastHead.getAlbums() != null) {
                List<MainPageAlbum> albumList = mastHead.getAlbums();
                for (MainPageAlbum lesoAlbum : albumList) {
                    this.albums.add(new Album(lesoAlbum));
                }
            }
        }
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
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
        private String globalId;

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
                this.globalId = lesoAlbum.getGlobal_id();
            }
        }

        public String getGlobalId() {
            return this.globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
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

    }
}
