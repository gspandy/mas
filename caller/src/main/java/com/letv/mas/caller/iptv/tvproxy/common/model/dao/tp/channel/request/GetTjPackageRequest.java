package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.request;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;
import org.apache.commons.lang.StringUtils;

/**
 * 获取特辑包信息请求封装类
 * @author liyunlong
 */
public class GetTjPackageRequest {

    /**
     * 特辑包ID
     */
    private String subjectId;

    /**
     * 播控方ID
     */
    private Integer broadcastId;

    public GetTjPackageRequest(String subjectId, Integer broadcastId) {
        this.subjectId = subjectId;
        this.broadcastId = broadcastId;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.subjectId)) {
            return ChannelMsgCodeConstant.CHANNEL_GET_TJPACKAGE_REQUEST_SUBJECTID_EMPTY;
        }
        return ChannelMsgCodeConstant.COMMON_REQUEST_SUCCESS;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

}
