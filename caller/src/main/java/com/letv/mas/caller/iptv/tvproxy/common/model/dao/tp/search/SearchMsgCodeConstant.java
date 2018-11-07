package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;

public class SearchMsgCodeConstant extends CommonMsgCodeConstant {

    /**
     * 搜索接口参数校验错误码
     */
    public final static int SEARCH_GET_LESOVIDEO_STREAMADDR_REQUEST_URL_EMPTY = 1000;// 播放地址为空
    public final static int SEARCH_GET_LESOVIDEO_STREAMADDR_REQUEST_RESULTTYPE_ERROR = 1001;// 请求的流地址的类型错误
    public final static int SEARCH_GET_LESOVIDEO_STREAMADDR_REQUEST_RESOLUTION_ERROR = 1002;// 流地址的清晰度值错误
    public final static int SEARCH_GET_LESOVIDEO_STREAMADDR_REQUEST_RESOLUTION_PRIORITY_ERROR = 1003;// 码流流畅度排序类型值错误
    public final static int SEARCH_SEARCHVIDEO_BYID_REQUEST_CONDITION_EMPTY = 1004;// 搜索条件为空

}
