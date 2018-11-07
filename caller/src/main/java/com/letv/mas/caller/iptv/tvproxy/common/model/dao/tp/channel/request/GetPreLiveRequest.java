package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;
import org.apache.commons.lang.StringUtils;

/**
 * 获取超前影院详细数据请求封装类
 * @author liyunlong
 */
public class GetPreLiveRequest {

    /**
     * 内容id
     */
    private String contentid;

    /**
     * 用户id;
     */
    private String userid;

    /**
     * 子平台id
     */
    private String splatId;

    /**
     * 用户设备是否支持3D
     */
    private Integer support3D;

    /**
     * 播控方
     */
    private Integer broadcastId;

    /**
     * 终端品牌
     */
    private String terminalBrand;
    /**
     * 终端型号
     */
    private String terminalSeries;

    /**
     * 机卡绑定设备编号
     */
    private String deviceKey;

    /**
     * 客户端mac地址
     */
    private String mac;

    public GetPreLiveRequest(String contentid, String userid, String splatId, Integer broadcastId,
            String terminalBrand, String terminalSeries, String deviceKey, String mac, Integer support3D) {
        this.contentid = contentid;
        this.userid = userid;
        this.splatId = splatId;
        this.broadcastId = broadcastId;
        this.terminalBrand = terminalBrand;
        this.terminalSeries = terminalSeries;
        this.deviceKey = deviceKey;
        this.mac = mac;
        this.support3D = support3D;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("contentid", this.contentid);
        parametersMap.put("userid", this.userid);
        parametersMap.put("terminalBrand", this.terminalBrand);
        parametersMap.put("terminalSeries", this.terminalSeries);
        if (StringUtils.isBlank(this.deviceKey)) {// 做特殊处理，避免为空
            this.deviceKey = "";
        }
        parametersMap.put("deviceKey", this.deviceKey);
        parametersMap.put("mac", this.mac);

        return parametersMap;
    }

    /**
     * 参数校验
     * @return
     */

    public int assertValid() {
        if (StringUtils.isBlank(this.contentid)) {
            return ChannelMsgCodeConstant.CHANNEL_GET_PRELIVE_REQUEST_CONTENTID_EMPTY;
        } else if (StringUtils.isBlank(this.mac)) {
            return ChannelMsgCodeConstant.REQUEST_MAC_EMPTY;
        }
        /*
         * 参数修改为非必传，不需校验
         * if (StringUtils.isBlank(this.userid)) {
         * return ChannelMsgCodeConstant.REQUEST_USERINFO_ERROR;
         * }
         * if (StringUtils.isBlank(this.terminalBrand)) {
         * return ChannelMsgCodeConstant.REQUEST_DEVICEKEY_EMPTY;
         * }
         * if (StringUtils.isBlank(this.terminalSeries)) {
         * return ChannelMsgCodeConstant.REUQEST_DEVICEVERSION_EMPTY;
         * }
         */
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    public String getContentid() {
        return this.contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getSupport3D() {
        return this.support3D;
    }

    public void setSupport3D(Integer support3d) {
        this.support3D = support3d;
    }

    public String getSplatId() {
        return this.splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        if (StringUtils.isBlank(deviceKey)) {
            this.deviceKey = "";
        } else {
            this.deviceKey = deviceKey;
        }
    }

}
