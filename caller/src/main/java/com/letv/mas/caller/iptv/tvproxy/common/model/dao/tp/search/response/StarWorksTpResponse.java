package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.util.CollectionUtils;

import serving.AlbumAttribute;
import serving.IdAndName;
import serving.ResultDocInfo;
import serving.ServingResult;
import serving.VideoAttribute;

public class StarWorksTpResponse {

    private List<VideoList> video_list;
    private List<AlbumList> album_list;

    public List<VideoList> getVideo_list() {
        return this.video_list;
    }

    public void setVideo_list(List<VideoList> video_list) {
        this.video_list = video_list;
    }

    public List<AlbumList> getAlbum_list() {
        return this.album_list;
    }

    public void setAlbum_list(List<AlbumList> album_list) {
        this.album_list = album_list;
    }

    public static class VideoList {
        private String category;
        private String duration;
        private String vid;
        private String mid;
        private String name;
        private Map<String, String> payPlatform;
        private Map<String, String> images;
        private Map<String, String> pushFlag;
        private String url;

        public VideoList(ServingResult servingResult) {
            if (servingResult != null) {
                ResultDocInfo resultDocInfo = servingResult.getDoc_info();
                this.name = resultDocInfo.getName();
                if (!StringUtil.isBlank(resultDocInfo.getCategory())) {
                    this.category = resultDocInfo.getCategory();
                }
                if (resultDocInfo.getVideo_attribute() != null) {
                    VideoAttribute videoAttribute = resultDocInfo.getVideo_attribute();
                    this.duration = videoAttribute.getDuration();
                    this.vid = videoAttribute.getVid();
                    this.mid = videoAttribute.getMid();
                    if (!CollectionUtils.isEmpty(videoAttribute.getPay_platform())) {
                        this.payPlatform = new HashMap<String, String>();
                        for (IdAndName idAndName : videoAttribute.getPay_platform()) {
                            this.payPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    this.images = videoAttribute.getImages();
                    if (!CollectionUtils.isEmpty(videoAttribute.getPush_flag())) {
                        this.pushFlag = new HashMap<String, String>();
                        for (IdAndName idAndName : videoAttribute.getPush_flag()) {
                            this.pushFlag.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    this.url = videoAttribute.getUrl();
                }
            }
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getMid() {
            return this.mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getPayPlatform() {
            return this.payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public Map<String, String> getImages() {
            return this.images;
        }

        public void setImages(Map<String, String> images) {
            this.images = images;
        }

        public Map<String, String> getPushFlag() {
            return this.pushFlag;
        }

        public void setPushFlag(Map<String, String> pushFlag) {
            this.pushFlag = pushFlag;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public static class AlbumList {
        private String aid;
        private String videoType;
        private String name;
        private Map<String, String> pushFlag;
        private Map<String, String> images;
        private String src;

        public AlbumList(ServingResult servingResult) {
            if (servingResult != null) {
                ResultDocInfo resultDocInfo = servingResult.getDoc_info();
                this.name = resultDocInfo.getName();
                if (resultDocInfo.getAlbum_attribute() != null) {
                    AlbumAttribute albumAttribute = resultDocInfo.getAlbum_attribute();
                    // this.aid = albumAttribute.getAid();
                    this.aid = resultDocInfo.getId();
                    this.videoType = String.valueOf(albumAttribute.getVideo_type());
                    if (!CollectionUtils.isEmpty(albumAttribute.getPush_flag())) {
                        this.pushFlag = new HashMap<String, String>();
                        for (IdAndName idAndName : albumAttribute.getPush_flag()) {
                            this.pushFlag.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    this.images = albumAttribute.getImages();
                    this.src = String.valueOf(albumAttribute.getSrc());
                }
            }

        }

        public String getAid() {
            return this.aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getVideoType() {
            return this.videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getPushFlag() {
            return this.pushFlag;
        }

        public void setPushFlag(Map<String, String> pushFlag) {
            this.pushFlag = pushFlag;
        }

        public Map<String, String> getImages() {
            return this.images;
        }

        public void setImages(Map<String, String> images) {
            this.images = images;
        }

        public String getSrc() {
            return this.src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

    }
}
