package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

import java.util.List;

/**
 * 机卡绑定需求，鉴别该机器是否有未领取的机卡绑定套餐及套餐时长的结果封装，返回给客户端。
 * @author yikun
 * @since 2014-08-07
 */
public class DeviceBindDto extends BaseDto {

    /**
     * 自带机卡绑定套餐是否已激活，0--数据不可用，1--未激活，可领取；2--已激活，不可领取
     */
    private Integer isDeviceActive;

    /**
     * 自带机卡绑定时长，单位：月，仅在isDeviceActive为1时有效
     */
    private Integer bindMonths;

    /**
     * 机卡绑定显示优先级，1--优先显示自带机卡绑定，2--优先显示赠送机卡绑定
     */
    private String priority;

    /**
     * 机卡赠送时长列表
     */
    private List<PresentDeviceBindDto> presentDeviceBinds;

    /**
     * 机卡时长列表
     */
    private List<DeviceBind> deviceBinds;

    /**
     * 绑定时长单位，“天”，“月”，“年”等
     */
    private Integer bindDuration;

    private String bindDurationUnitType;

    public Integer getIsDeviceActive() {
        return this.isDeviceActive;
    }

    public void setIsDeviceActive(Integer isDeviceActive) {
        this.isDeviceActive = isDeviceActive;
    }

    public Integer getBindMonths() {
        return this.bindMonths;
    }

    public void setBindMonths(Integer bindMonths) {
        this.bindMonths = bindMonths;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<PresentDeviceBindDto> getPresentDeviceBinds() {
        return this.presentDeviceBinds;
    }

    public void setPresentDeviceBinds(List<PresentDeviceBindDto> presentDeviceBinds) {
        this.presentDeviceBinds = presentDeviceBinds;
    }

    public List<DeviceBind> getDeviceBinds() {
        return this.deviceBinds;
    }

    public void setDeviceBinds(List<DeviceBind> deviceBinds) {
        this.deviceBinds = deviceBinds;
    }

    public Integer getBindDuration() {
        return bindDuration;
    }

    public void setBindDuration(Integer bindDuration) {
        this.bindDuration = bindDuration;
    }

    public String getBindDurationUnitType() {
        return bindDurationUnitType;
    }

    public void setBindDurationUnitType(String bindDurationUnitType) {
        this.bindDurationUnitType = bindDurationUnitType;
    }

}
