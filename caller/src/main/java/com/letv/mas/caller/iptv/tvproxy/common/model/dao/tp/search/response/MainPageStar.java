package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.Map;

public class MainPageStar {
    private String id; // 明星id
    private String letv_original_id;// 数据上报用到
    private String name; // 明星姓名
    private String score;
    private Map<String, String> poster;

    public String getLetv_original_id() {
        return this.letv_original_id;
    }

    public void setLetv_original_id(String letv_original_id) {
        this.letv_original_id = letv_original_id;
    }

    public String getId() {
        return this.id;
    }

    public Map<String, String> getPoster() {
        return this.poster;
    }

    public void setPoster(Map<String, String> poster) {
        this.poster = poster;
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

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /*
     * public StarImage getPoster_s1() {
     * return poster_s1;
     * }
     * public void setPoster_s1(StarImage poster_s1) {
     * this.poster_s1 = poster_s1;
     * }
     * public StarImage getPoster_s2() {
     * return poster_s2;
     * }
     * public void setPoster_s2(StarImage poster_s2) {
     * this.poster_s2 = poster_s2;
     * }
     * public StarImage getPoster_s3() {
     * return poster_s3;
     * }
     * public void setPoster_s3(StarImage poster_s3) {
     * this.poster_s3 = poster_s3;
     * }
     */

    public static class StarImage {
        private String height; // 图片高度
        private String width; // 图片宽度
        private String url; // 图片URL

        public String getHeight() {
            return this.height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWidth() {
            return this.width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
