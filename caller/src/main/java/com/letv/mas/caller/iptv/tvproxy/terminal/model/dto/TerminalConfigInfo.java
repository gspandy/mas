package com.letv.mas.caller.iptv.tvproxy.terminal.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.DanMuDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.HolidayDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.LesoContentSportsJumpConfig;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.LetvContentSportsJumpConfig;

import java.util.List;

/**
 * 终端配置数据封装类；
 * 2016-05-25，为适配客户端通用配置SDK设计，在原TerminalConfigDto基础上添加本类，以后需求扩展均在本类中进行
 * @author KevinYi
 */
public class TerminalConfigInfo extends BaseDto {

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

    /**
     * 服务端下发的TV版下导航数据，其内容决定下导航内容，其顺序决定下导航顺序
     */
    private List<String> tab_config;

    /**
     * 当前机型是否支持全景码流，1--支持，其他值（null或非1）--不支持
     */
    private Integer support360Stream;

    public DanMuDto getDanmu() {
        return danmu;
    }

    public void setDanmu(DanMuDto danmu) {
        this.danmu = danmu;
    }

    public HolidayDto getHoliday() {
        return holiday;
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

    public List<String> getTab_config() {
        return tab_config;
    }

    public void setTab_config(List<String> tab_config) {
        this.tab_config = tab_config;
    }

    public Integer getSupport360Stream() {
        return support360Stream;
    }

    public void setSupport360Stream(Integer support360Stream) {
        this.support360Stream = support360Stream;
    }

}
