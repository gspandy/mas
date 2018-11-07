package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 超级手机赠送机卡时长信息封装类
 * @author
 * @since Mar 30, 2015
 */
public class PresentDeviceBindDto extends BaseDto {

    /**
     * 赠送机卡时长信息的id
     */

    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 手机端激活时间，格式为"yyyy-MM-dd"
     */
    private String activeTime;

    /**
     * 从当前日期算起的剩余可领取时长，单位：天
     */
    private String availableTime;

    // private String presentProductId;

    /**
     * 赠送时长的来源，如乐视手机 X1，乐视手机 X2等
     */
    private String presentProductName;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getAvailableTime() {
        return this.availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getPresentProductName() {
        return this.presentProductName;
    }

    public void setPresentProductName(String presentProductName) {
        this.presentProductName = presentProductName;
    }

}
