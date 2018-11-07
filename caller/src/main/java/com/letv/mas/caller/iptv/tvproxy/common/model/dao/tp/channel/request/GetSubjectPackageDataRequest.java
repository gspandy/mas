package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.request;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelMsgCodeConstant;
import org.apache.commons.lang.StringUtils;

/**
 * 获取专题包下的视频数据请求封装类
 * @author liyunlong
 */
public class GetSubjectPackageDataRequest {

    /**
     * 专题包id
     */
    private String subjectId;

    /**
     * 视频id，多个id之间用逗号隔开
     */
    private String packageIds;

    /**
     * 播控方id
     */
    private Integer broadcastId;

    public GetSubjectPackageDataRequest(String subjectId, String packageIds, Integer broadcastId) {
        this.subjectId = subjectId;
        this.packageIds = packageIds;
        this.broadcastId = broadcastId;
    }

    /**
     * 参数校验
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.packageIds)) {
            return ChannelMsgCodeConstant.CHANNEL_GET_SUBJECT_PACKAGE_REQUEST_PACKAGEIDS_EMPTY;
        }
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPackageIds() {
        return this.packageIds;
    }

    public void setPackageIds(String packageIds) {
        this.packageIds = packageIds;
    }

    public Integer getBroadcastId() {
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

}
