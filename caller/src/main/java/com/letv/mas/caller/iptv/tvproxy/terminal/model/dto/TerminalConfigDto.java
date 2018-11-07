package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.*;
import io.swagger.annotations.ApiModel;

import java.util.Map;

/**
 * 终端全局配置接口
 * TODO 需要细化
 */
@ApiModel(value = "DeviceConfig", description = "终端全局配置")
public class TerminalConfigDto extends BaseDto {

    private static final long serialVersionUID = 5202832746050451022L;
    /**
     * TV弹幕
     */
    private DanMuDto danmu;

    /**
     * 节日元素黑名单
     */
    private HolidayDto holiday;

    /**
     * TV版使用的体育跳转配置
     */
    private LetvContentSportsJumpConfig sports;

    /**
     * 乐搜使用的体育跳转配置
     */
    private LesoContentSportsJumpConfig lesoSports;

    private String ver;

    /**
     * 设备配置信息
     */
    private DeviceConfig deviceConfig;

    /**
     * 针对客户端通用SDK设计而进行的容错，要求这种格式格式
     */
    private Map<String, Object> configInfo;

    private Object appConfig;

    public DanMuDto getDanmu() {
        return this.danmu;
    }

    public void setDanmu(DanMuDto danmu) {
        this.danmu = danmu;
    }

    public HolidayDto getHoliday() {
        return this.holiday;
    }

    public void setHoliday(HolidayDto holiday) {
        this.holiday = holiday;
    }

    public LetvContentSportsJumpConfig getSports() {
        return sports;
    }

    public void setSports(LetvContentSportsJumpConfig sports) {
        this.sports = sports;
    }

    public LesoContentSportsJumpConfig getLesoSports() {
        return lesoSports;
    }

    public void setLesoSports(LesoContentSportsJumpConfig lesoSports) {
        this.lesoSports = lesoSports;
    }

    public Map<String, Object> getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(Map<String, Object> configInfo) {
        this.configInfo = configInfo;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public DeviceConfig getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(DeviceConfig deviceConfig) {
        this.deviceConfig = deviceConfig;
    }

    public Object getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(Object appConfig) {
        this.appConfig = appConfig;
    }
}
