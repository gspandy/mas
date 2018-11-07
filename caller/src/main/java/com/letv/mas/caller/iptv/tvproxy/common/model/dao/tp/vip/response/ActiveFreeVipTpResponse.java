package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActiveFreeVipTpResponse implements Serializable {

    private Integer status;// 状态码

    private String desc;// 接口状态描述

    private ActiveFreeVipData data;// 获取规则与声明

    private ActiveFreeVipData details;// 接口响应内容

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ActiveFreeVipData getData() {
        return data;
    }

    public void setData(ActiveFreeVipData data) {
        this.data = data;
    }

    public ActiveFreeVipData getDetails() {
        return details;
    }

    public void setDetails(ActiveFreeVipData details) {
        this.details = details;
    }

    public static class ActiveFreeVipData {
        private Long free_end_time; // 白条到期日期

        private String limit_day; // 可以赊账的天数

        private Long required_pay_time; // 要求必须还款的日期

        @JsonProperty(value = "EndTime")
        private Integer EndTime;// 还款到期时间

        private String pay_image; // 支付图片

        private String pay_button; // 激活成功页按钮文案&还款首页按钮文案

        private String pay_desc; // 还款首页的描述文案

        private String pay_productid; // BOSS活动id

        private Integer pay_monthtype;// 套餐id

        private String pay_days; // 开通会员天数

        private String pay_pric; // 开通套餐价格

        private String indeximge;// 首页图片

        private String indexbutton;// 首页激活按钮文案

        private String rule;// 活动规则内容

        @JsonProperty(value = "Disclaimer")
        private String Disclaimer;// 免责声明

        public Long getFree_end_time() {
            return free_end_time;
        }

        public void setFree_end_time(Long free_end_time) {
            this.free_end_time = free_end_time;
        }

        public String getLimit_day() {
            return limit_day;
        }

        public void setLimit_day(String limit_day) {
            this.limit_day = limit_day;
        }

        public Long getRequired_pay_time() {
            return required_pay_time;
        }

        public void setRequired_pay_time(Long required_pay_time) {
            this.required_pay_time = required_pay_time;
        }

        public Integer getEndTime() {
            return EndTime;
        }

        public void setEndTime(Integer endTime) {
            this.EndTime = endTime;
        }

        public String getPay_image() {
            return pay_image;
        }

        public void setPay_image(String pay_image) {
            this.pay_image = pay_image;
        }

        public String getPay_button() {
            return pay_button;
        }

        public void setPay_button(String pay_button) {
            this.pay_button = pay_button;
        }

        public String getPay_desc() {
            return pay_desc;
        }

        public void setPay_desc(String pay_desc) {
            this.pay_desc = pay_desc;
        }

        public String getPay_productid() {
            return pay_productid;
        }

        public void setPay_productid(String pay_productid) {
            this.pay_productid = pay_productid;
        }

        public Integer getPay_monthtype() {
            return pay_monthtype;
        }

        public void setPay_monthtype(Integer pay_monthtype) {
            this.pay_monthtype = pay_monthtype;
        }

        public String getPay_days() {
            return pay_days;
        }

        public void setPay_days(String pay_days) {
            this.pay_days = pay_days;
        }

        public String getPay_pric() {
            return pay_pric;
        }

        public void setPay_pric(String pay_pric) {
            this.pay_pric = pay_pric;
        }

        public String getIndeximge() {
            return indeximge;
        }

        public void setIndeximge(String indeximge) {
            this.indeximge = indeximge;
        }

        public String getIndexbutton() {
            return indexbutton;
        }

        public void setIndexbutton(String indexbutton) {
            this.indexbutton = indexbutton;
        }

        public String getRule() {
            return rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getDisclaimer() {
            return Disclaimer;
        }

        public void setDisclaimer(String disclaimer) {
            Disclaimer = disclaimer;
        }

    }

}
