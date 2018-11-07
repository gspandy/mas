package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.request;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;

public class GetCourseRequest {

    /**
     * CMS板块id
     */
    private Integer channelId;
    private String age;
    private String gender;
    private String week;

    public GetCourseRequest(Integer channelId, String age, String gender) {
        this.channelId = channelId;
        this.age = age;
        this.gender = gender;
    }

    public GetCourseRequest(Integer channelId, String age, String gender, String week) {
        this.channelId = channelId;
        this.age = age;
        this.gender = gender;
        this.week = week;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (this.channelId == null) {
            return ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_BLOCKID_EMPTY;
        }
        /*
         * if (StringUtils.isBlank(this.age)) {
         * return ChannelMsgCodeConstant.CHANNEL_GET_CMSBLOCK_REQUEST_AGE_EMPTY;
         * }
         */
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPreFixLog() {
        return "[channelId=" + this.channelId + ", age=" + this.age + ", gender=" + this.gender + "]";
    }

    public String getWeek() {
        return this.week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}
