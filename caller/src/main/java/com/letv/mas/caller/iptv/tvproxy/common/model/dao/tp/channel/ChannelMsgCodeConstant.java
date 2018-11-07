package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;

public class ChannelMsgCodeConstant extends CommonMsgCodeConstant {

    /**
     * 专题模块公共参数校验
     */
    public final static int COMMON_REQUEST_SUCCESS = 0;// 校验成功

    /**
     * 专题模块参数检验
     */
    public final static int CHANNEL_GET_TJPACKAGE_REQUEST_SUBJECTID_EMPTY = 1000;// 特辑包ID为空
    public final static int CHANNEL_GET_TJPACKAGE_REQUEST_SUBJECT_TYPE_ERROR = 1001;// 特辑包类型错误
    public final static int CHANNEL_GET_SUBJECT_PACKAGE_REQUEST_PACKAGEIDS_EMPTY = 1002;// 获取视频包信息，视频包id为空

    /**
     * 超前院线参数校验错误码
     */
    public final static int CHANNEL_GET_PRELIVE_REQUEST_CONTENTID_EMPTY = 1000;// 内容ID为空

    /**
     * CMS板块参数校验错误码
     */
    public final static int CHANNEL_GET_CMSBLOCK_REQUEST_BLOCKID_EMPTY = 1000;// 板块ID为空
    /**
     * channelId为空的时候
     */
    public final static int CHANNEL_GET_CMSBLOCK_REQUEST_CONTAINERID_EMPTY = 1005;// 新容器ID为空
}
