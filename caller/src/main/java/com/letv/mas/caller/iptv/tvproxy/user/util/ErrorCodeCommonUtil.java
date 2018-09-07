package com.letv.mas.caller.iptv.tvproxy.user.util;

import com.letv.mas.caller.iptv.tvproxy.user.response.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.user.util.MessageUtils;

/**
 * 用户模块和会员模块通用常量类，提供两个模块共用的字段或方法，尽可能减少两个模块的同层直接依赖；
 * 如果某一字段或方法只在一个模块中被调用，则可将该字段或方法下移至该模块的Util类中。
 * @author yikun
 */
public class ErrorCodeCommonUtil {

    /**
     * 响应装填码
     */
    public static final int RESPONSE_FAILURE = 0;
    public static final int RESPONSE_SUCCESS = 1;


    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param errCode
     *            错误代码
     * @param langcode
     *            用户语言环境
     */
    public static BaseResponse setErrorResponse(BaseResponse response, String errCode, String langcode) {
        if (response != null) {
            response.setResultStatus(RESPONSE_FAILURE);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, langcode));
        }
        return response;
    }
}
