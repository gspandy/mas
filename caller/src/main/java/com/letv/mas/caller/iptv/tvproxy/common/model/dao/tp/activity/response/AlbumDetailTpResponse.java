package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class AlbumDetailTpResponse {

    private Integer resultStatus;// 成功时为1
    private Integer status;// 成功时为1
    private AlbumDetail data;// 数据主体，key：推广位标识，value：推广位数据集合

    public Integer getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AlbumDetail getData() {
        return data;
    }

    public void setData(AlbumDetail data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.resultStatus == 1 && this.status == 1 && data != null;
    }

    public static class AlbumDetail {
        /**
         * 专辑id
         */
        private String albumId;

        /**
         * 专辑分类id
         */
        private Integer categoryId;
        /**
         * 专辑二级分类
         */
        private Map<String, String> subCategory;
        /**
         * 搜索接口字段兼容
         */
        private String subCategoryId;
        /**
         * 子分类(标签)名称
         */
        private String subCategoryName;

        /**
         * 子分类(标签)名称；Lecom项目使用，和subCategoryName同一意思，但因subCategoryName被多个项目使用，
         * 格式不一，
         * 且lecom项目中老版本使用，存在兼容问题，这里新加字段表示，后学迭代版本中删除
         */
        private String subCateName;

        /**
         * 标签（2.5详情页中“类型”）
         */
        private String tagName;

        /**
         * 是否正片
         */
        private Boolean positive;

        /**
         * 正片、预告片等类型id
         */
        private Integer albumTypeId;

        /**
         * 正片、预告片等类型名
         */
        private String albumTypeName;

        /**
         * 是否收费
         */
        private Boolean charge;

        /**
         * 从第几集/期开始收费
         */
        private String charge_episode;

        private Video video;

        public Video getVideo() {
            return video;
        }

        public void setVideo(Video video) {
            this.video = video;
        }

        /**
         * 剧集列表（正片）
         */
        private ArrayList<Video> positiveSeries;

        public String getCharge_episode() {
            return charge_episode;
        }

        public void setCharge_episode(String charge_episode) {
            this.charge_episode = charge_episode;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Map<String, String> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(Map<String, String> subCategory) {
            this.subCategory = subCategory;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public String getSubCateName() {
            return subCateName;
        }

        public void setSubCateName(String subCateName) {
            this.subCateName = subCateName;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public Boolean getPositive() {
            return positive;
        }

        public void setPositive(Boolean positive) {
            this.positive = positive;
        }

        public Integer getAlbumTypeId() {
            return albumTypeId;
        }

        public void setAlbumTypeId(Integer albumTypeId) {
            this.albumTypeId = albumTypeId;
        }

        public String getAlbumTypeName() {
            return albumTypeName;
        }

        public void setAlbumTypeName(String albumTypeName) {
            this.albumTypeName = albumTypeName;
        }

        public Boolean getCharge() {
            return charge;
        }

        public void setCharge(Boolean charge) {
            this.charge = charge;
        }

        public ArrayList<Video> getPositiveSeries() {
            return positiveSeries;
        }

        public void setPositiveSeries(ArrayList<Video> positiveSeries) {
            this.positiveSeries = positiveSeries;
        }
    }

    public static class Video {
        /**
         * 视频id
         */
        private String videoId;

        /**
         * 是否正片
         */
        private Boolean positive;

        /**
         * 第几集、第几期
         */
        private String episode;

        /**
         * 是否收费
         */
        private String ifCharge;

        public Boolean getPositive() {
            return positive;
        }

        public void setPositive(Boolean positive) {
            this.positive = positive;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getIfCharge() {
            return ifCharge;
        }

        public void setIfCharge(String ifCharge) {
            this.ifCharge = ifCharge;
        }
    }

}
