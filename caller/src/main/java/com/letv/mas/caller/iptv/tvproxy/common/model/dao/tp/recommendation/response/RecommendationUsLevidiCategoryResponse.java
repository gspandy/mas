package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class RecommendationUsLevidiCategoryResponse implements Serializable {

    private static final long serialVersionUID = -466785824562349127L;

    private List<UsLevidi> data;

    public static class UsLevidi implements Serializable {

        private static final long serialVersionUID = -5356043744125556929L;

        private List<UsLevidiCategory> no_pic_content;

        private List<UsLevidiCategory> pic_category;

        public List<UsLevidiCategory> getNo_pic_content() {
            return no_pic_content;
        }

        public void setNo_pic_content(List<UsLevidiCategory> no_pic_content) {
            this.no_pic_content = no_pic_content;
        }

        public List<UsLevidiCategory> getPic_category() {
            return pic_category;
        }

        public void setPic_category(List<UsLevidiCategory> pic_category) {
            this.pic_category = pic_category;
        }

    }

    public static class UsLevidiCategory implements Serializable {

        private static final long serialVersionUID = -3591548660573356062L;

        private String cid;

        private String id;

        private String name;

        private String pic_url;

        private Map<String, String> multi_pic_urls;

        private Integer type;

        public Map<String, String> getMulti_pic_urls() {
            return multi_pic_urls;
        }

        public void setMulti_pic_urls(Map<String, String> multi_pic_urls) {
            this.multi_pic_urls = multi_pic_urls;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

    }

    public List<UsLevidi> getData() {
        return data;
    }

    public void setData(List<UsLevidi> data) {
        this.data = data;
    }

}
