package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response;

import java.io.Serializable;
import java.util.List;

public class RecommendationLevidiResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2636219682073064098L;

    private String bucket;

    private String message;

    private String reid;

    private String status;

    private List<RecLevidi> data;

    private List<Group> groups; // 分组参数

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RecLevidi> getData() {
        return data;
    }

    public void setData(List<RecLevidi> data) {
        this.data = data;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public static class RecLevidi implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = 8394927725818043244L;

        private String section_name;

        private String cid;

        private String order;

        private RecommendationTpResponseLevidiRec data;

        private List<RecLevidi> units;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getSection_name() {
            return section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public RecommendationTpResponseLevidiRec getData() {
            return data;
        }

        public void setData(RecommendationTpResponseLevidiRec data) {
            this.data = data;
        }

        public List<RecLevidi> getUnits() {
            return units;
        }

        public void setUnits(List<RecLevidi> units) {
            this.units = units;
        }

    }

    public static class Group implements Serializable {
        private static final long serialVersionUID = -1558873560328458583L;

        private Integer count; // 数据量
        private Integer offset;// 起始位置
        private String publisher_id;// cp id

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public String getPublisher_id() {
            return publisher_id;
        }

        public void setPublisher_id(String publisher_id) {
            this.publisher_id = publisher_id;
        }

    }
}
