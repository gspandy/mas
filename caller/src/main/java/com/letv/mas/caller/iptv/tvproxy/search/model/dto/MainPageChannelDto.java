package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageAlbum;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageChannelsTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageStar;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainPageChannelDto extends BaseDto {
    private String channelId; // 频道id；
    private String cover; // 频道图片
    private String description; // 频道描述
    private String name; // 频道名称
    private String totalNum; // 频道内数据数量
    private String channelType; // 频道的英文类型标示
    private String viewTotal; // 观看量
    private List<Album> albums; // 频道下专辑
    private List<Star> stars;

    public MainPageChannelDto() {
    }

    public MainPageChannelDto(MainPageChannelsTpResponse.ChannelObject channel) {
        if (channel != null) {
            this.channelId = channel.getChid();
            this.cover = channel.getCover();
            this.description = channel.getDescription();
            this.name = channel.getName();
            this.totalNum = channel.getTotal();
            this.channelType = channel.getType();
            this.viewTotal = channel.getView_total();
            this.albums = new LinkedList<Album>();
            List<MainPageAlbum> albumList = channel.getAlbums();
            if (channel.getAlbums() != null) {
                for (MainPageAlbum album : albumList) {
                    this.albums.add(new Album(album));
                }
            }
            this.stars = new LinkedList<Star>();
            List<MainPageStar> starList = channel.getStars();
            if (starList != null) {
                for (MainPageStar star : starList) {
                    this.stars.add(new Star(star));
                }
            }

        }
    }

    public List<Star> getStars() {
        return stars;
    }

    public void setStars(List<Star> stars) {
        this.stars = stars;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public String getTotalNum() {
        return this.totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getViewTotal() {
        return this.viewTotal;
    }

    public void setViewTotal(String viewTotal) {
        this.viewTotal = viewTotal;
    }

    public List<Album> getAlbums() {
        return this.albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public static class Album {
        private Map<String, String> cover; // 封面图片
        private String name; // 专辑名称

        public Album() {
        }

        public Album(MainPageAlbum lesoAlbum) {
            if (lesoAlbum != null) {
                this.cover = lesoAlbum.getCover();
                this.name = lesoAlbum.getName();
            }
        }

        public Map<String, String> getCover() {
            return this.cover;
        }

        public void setCover(Map<String, String> cover) {
            this.cover = cover;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public static class Star {
        private String id; // 明星id
        private String name; // 明星姓名
        private Map<String, String> cover; // 图片

        public Star() {
        }

        public Star(MainPageStar star) {
            if (star != null) {
                this.id = star.getId();
                this.name = star.getName();
                this.cover = star.getPoster();
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

        public Map<String, String> getCover() {
            return this.cover;
        }

        public void setCover(Map<String, String> cover) {
            this.cover = cover;
        }

    }

}
