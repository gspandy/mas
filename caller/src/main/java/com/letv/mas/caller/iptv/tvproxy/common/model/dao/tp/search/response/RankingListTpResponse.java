package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 排行榜返回实体
 */
public class RankingListTpResponse implements Serializable {

    private static final long serialVersionUID = 2241432580696338761L;
    private List<RankingList> rankingList;

    public List<RankingList> getRankingList() {
        return this.rankingList;
    }

    public void setRankingList(List<RankingList> rankingList) {
        this.rankingList = rankingList;
    }

    public RankingListTpResponse() {
    }

    public RankingListTpResponse(List<RankingList> rankingList) {
        this.rankingList = rankingList;
    }

    public static class RankingList implements Serializable {
        private static final long serialVersionUID = -200315759780531189L;
        @JsonProperty("id")
        private Long vrsAlbumId;
        @JsonProperty("name")
        private String name;
        @JsonProperty("picurl")
        private String imageUrl;
        @JsonProperty("playcount")
        private Long playCount;
        @JsonProperty("rating")
        private Float rating;
        @JsonProperty("url")
        private String webUrl;
        @JsonProperty("actor")
        private String actor;
        @JsonProperty("shortActor")
        private String shortActor;
        @JsonProperty("area")
        private String area;
        @JsonProperty("subcategory")
        private String subcategory;
        @JsonProperty("director")
        private String director;
        @JsonProperty("shortDirector")
        private String shortDirector;

        private String pushflag;
        private Integer categoryId;
        private Long iptvAlbumId;

        private Map<String, String> playPlatform;

        public Map<String, String> getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPushflag() {
            return this.pushflag;
        }

        public void setPushflag(String pushflag) {
            this.pushflag = pushflag;
        }

        public String getDirector() {
            return this.director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getShortDirector() {
            return this.shortDirector;
        }

        public void setShortDirector(String shortDirector) {
            this.shortDirector = shortDirector;
        }

        public String getArea() {
            return this.area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getSubcategory() {
            return this.subcategory;
        }

        public void setSubcategory(String subcategory) {
            this.subcategory = subcategory;
        }

        public String getShortActor() {
            return this.shortActor;
        }

        public void setShortActor(String shortActor) {
            this.shortActor = shortActor;
        }

        public String getActor() {
            return this.actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public Long getIptvAlbumId() {
            return this.iptvAlbumId;
        }

        public void setIptvAlbumId(Long iptvAlbumId) {
            this.iptvAlbumId = iptvAlbumId;
        }

        @JsonIgnore
        public Long getVrsAlbumId() {
            return this.vrsAlbumId;
        }

        public void setVrsAlbumId(Long vrsAlbumId) {
            this.vrsAlbumId = vrsAlbumId;
        }

        @JsonIgnore
        public String getImageUrl() {
            return this.imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        @JsonIgnore
        public Long getPlayCount() {
            return this.playCount;
        }

        public void setPlayCount(Long playCount) {
            this.playCount = playCount;
        }

        public Float getRating() {
            return this.rating;
        }

        public void setRating(Float rating) {
            this.rating = rating;
        }

        @JsonIgnore
        public String getWebUrl() {
            return this.webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public Integer getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }
    }

}
