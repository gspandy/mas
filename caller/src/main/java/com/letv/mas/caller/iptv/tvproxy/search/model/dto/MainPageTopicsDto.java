package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageTopicsTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.MainPageTopicsTpResponse.TopicObject;
import java.util.LinkedList;
import java.util.List;

public class MainPageTopicsDto extends BaseDto {

    private String channelName;
    private List<LesoMainPageTopic> topicList;

    public MainPageTopicsDto() {
    }

    public MainPageTopicsDto(MainPageTopicsTpResponse.TopicConntent content) {
        if (content != null) {
            this.channelName = content.getChannel_name();
            List<MainPageTopicsTpResponse.TopicObject> topics = content.getTopic_list();
            if (topics != null) {
                this.topicList = new LinkedList<LesoMainPageTopic>();
                for (TopicObject topicObject : topics) {
                    this.topicList.add(new LesoMainPageTopic(topicObject));
                }
            }
        }
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public List<LesoMainPageTopic> getTopicList() {
        return this.topicList;
    }

    public void setTopicList(List<LesoMainPageTopic> topicList) {
        this.topicList = topicList;
    }

    public static class LesoMainPageTopic {
        private String code; // 话题代码
        private String description; // 话题描述
        private String format; // 话题下数据类型 例如:album mix
        private String name; // 话题名称
        private String photo; // 话题图片
        private String total; // 话题下视频数量
        private String displayType; // 展示类型 normal ranking

        public LesoMainPageTopic() {
        }

        public LesoMainPageTopic(TopicObject topic) {
            if (topic != null) {
                this.code = topic.getCode();
                this.description = topic.getDescription();
                this.format = topic.getFormat();
                this.name = topic.getName();
                this.photo = topic.getPhoto();
                this.total = topic.getTotal();
                this.displayType = topic.getDisplay_type();
            }
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getFormat() {
            return this.format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return this.photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTotal() {
            return this.total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getDisplayType() {
            return this.displayType;
        }

        public void setDisplayType(String displayType) {
            this.displayType = displayType;
        }

    }

}
