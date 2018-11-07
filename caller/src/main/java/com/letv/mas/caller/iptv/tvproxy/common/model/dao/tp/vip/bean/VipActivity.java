package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.util.List;

/**
 * 会员活动
 */
public class VipActivity {
    private Integer id; // 活动id
    private Integer vipPackageId; // 套餐id
    private String title; // 价格标题
    private Integer status; // ？？状态 1：未发布 3：已发布
    private Integer type;// 活动类型， 1--套餐优惠，2--支付方式优惠
    private String updateTime; // 每日更新时间
    private String beginTime; // 每日开始时间
    private String endTime;// 每日结束时间
    private String beginDate;// 开始日期
    private String endDate;// 结束日期
    private Copywritings copywritings; // 版权信息
    private String operator; // ？？？
    private String description; // 描述
    private String terminals; // 终端，如"141001,141002,141004,141006,141007"，多个以','号隔开
    private Integer availableForAutoPay; // ???
    private String detailUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipPackageId() {
        return vipPackageId;
    }

    public void setVipPackageId(Integer vipPackageId) {
        this.vipPackageId = vipPackageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Copywritings getCopywritings() {
        return copywritings;
    }

    public void setCopywritings(Copywritings copywritings) {
        this.copywritings = copywritings;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerminals() {
        return terminals;
    }

    public void setTerminals(String terminal) {
        this.terminals = terminal;
    }

    public Integer getAvailableForAutoPay() {
        return availableForAutoPay;
    }

    public void setAvailableForAutoPay(Integer availableForAutoPay) {
        this.availableForAutoPay = availableForAutoPay;
    }

    /**
     * 版权信息
     */
    public static class Copywritings {
        private String pic1; // 版权图片
        private String label1; // 版权描述1
        private String label2; // 版权描述2

        public String getPic1() {
            return pic1;
        }

        public void setPic1(String pic1) {
            this.pic1 = pic1;
        }

        public String getLabel1() {
            return label1;
        }

        public void setLabel1(String label1) {
            this.label1 = label1;
        }

        public String getLabel2() {
            return label2;
        }

        public void setLabel2(String label2) {
            this.label2 = label2;
        }
    }

    public static class Content {
        private Integer duration; // 时长
        private Integer durationId; // ???
        private Integer typeGroup; // ???
        private Integer durationType; // 时长类型 1:年 2:月 5:天
        private String productName; // ???
        private String durationName; // 时长类型：year month date
        private Integer country; // ???
        private Integer productId; // ??? =>会员id

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public Integer getDurationId() {
            return durationId;
        }

        public void setDurationId(Integer durationId) {
            this.durationId = durationId;
        }

        public Integer getTypeGroup() {
            return typeGroup;
        }

        public void setTypeGroup(Integer typeGroup) {
            this.typeGroup = typeGroup;
        }

        public Integer getDurationType() {
            return durationType;
        }

        public void setDurationType(Integer durationType) {
            this.durationType = durationType;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getDurationName() {
            return durationName;
        }

        public void setDurationName(String durationName) {
            this.durationName = durationName;
        }

        public Integer getCountry() {
            return country;
        }

        public void setCountry(Integer country) {
            this.country = country;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

}
