package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.io.Serializable;
import java.util.List;

public class RecommendationUsLevidiPublisherResponse implements Serializable {

    private static final long serialVersionUID = 1327577351896960330L;
    private List<UsLevidiPublisher> data;

    public static class UsLevidiPublisher implements Serializable {

        private static final long serialVersionUID = 4567497577630047371L;

        private String key;

        private String type;

        private UsLevidiContent data;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public UsLevidiContent getData() {
            return data;
        }

        public void setData(UsLevidiContent data) {
            this.data = data;
        }

    }

    public static class UsLevidiContent implements Serializable {

        private static final long serialVersionUID = -7192972387644418705L;

        private String channel_id;

        private String pic_url;

        private String name;

        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public List<UsLevidiPublisher> getData() {
        return data;
    }

    public void setData(List<UsLevidiPublisher> data) {
        this.data = data;
    }

}
