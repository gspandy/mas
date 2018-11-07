package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;

/**
 * 乐搜新首页频道下话题列表
 */
public class MainPageTopicsTpResponse {
    private String status; // 接口调用状态 0:成功
    private String message;
    private TopicConntent content;

    public boolean success() {
        return "0".equals(status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TopicConntent getContent() {
        return content;
    }

    public void setContent(TopicConntent content) {
        this.content = content;
    }

    public static class TopicConntent {
        private String channel_name; // 频道名称
        private List<TopicObject> topic_list;

        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public List<TopicObject> getTopic_list() {
            return topic_list;
        }

        public void setTopic_list(List<TopicObject> topic_list) {
            this.topic_list = topic_list;
        }
    }

    public static class TopicObject {
        private String code; // 话题代码
        private String description; // 话题描述
        private String format; // 话题下数据类型 例如:album mix
        private String name; // 话题名称
        private String photo; // 话题图片
        private String total; // 话题下视频数量
        private String display_type; // 话题展示类型 normal ranking

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getDisplay_type() {
            return display_type;
        }

        public void setDisplay_type(String display_type) {
            this.display_type = display_type;
        }
    }
}
