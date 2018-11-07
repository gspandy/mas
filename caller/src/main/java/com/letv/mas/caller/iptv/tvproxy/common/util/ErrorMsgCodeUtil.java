package com.letv.mas.caller.iptv.tvproxy.common.util;

public class ErrorMsgCodeUtil {

    /**
     * 根据业务码和参数校验返回码，构造参数校验的详细错误码后缀，约定
     * <li>errorMsgCode在[1000,8999]为一般参数校验错误，在[9000,9999]为公共参数校验错误
     * <li>errorMsgCode在[9000,9999]时，businessCode修改为0，作为一个标志位，表示是公共参数校验错误
     * <p>
     * Examples:
     * <blockquote>
     * <pre>
     * VipUtil.parseErrorMsgCode(1,1000) returns "_1_1000"
     * VipUtil.parseErrorMsgCode(1,9000) returns "_0_9000"
     * </pre>
     * </blockquote>
     * @param businessCode
     *            业务码
     * @param errorMsgCode
     *            参数校验返回码
     * @return
     */
    public static String parseErrorMsgCode(int businessCode, int errorMsgCode) {
        // 业务码或者标志位非负，错误消息码区间在[1000,9999]
        if (errorMsgCode < 1000 || errorMsgCode > 9999) {
            return "";// 返回空字符串，即使跟未细分前的的错误码拼接，也不会出现找不到错误消息的情况
        }
        if (errorMsgCode > 8999) {// 参数校验码大于8999时表示是公共参数校验错误
            businessCode = 0;// 此时0作为一个标志位，表示是公共参数校验问题
        }
        return "_" + businessCode + "_" + errorMsgCode;
    }

    /**
     * 根据业务码和参数校验返回码，构造参数校验的详细错误码后缀，约定
     * <li>errorMsgCode在[1000,8999]为一般参数校验错误，在[9000,9999]为公共参数校验错误
     * <li>errorMsgCode在[9000,9999]时，businessCode修改为0，作为一个标志位，表示是公共参数校验错误
     * <p>
     * Examples:
     * <blockquote>
     * <pre>
     * VipUtil.parseErrorMsgCode(SCC001,1,1000) returns "SCC001_1_1000"
     * VipUtil.parseErrorMsgCode(SCC001,1,9000) returns "SPC031_0_9000"
     * </pre>
     * </blockquote>
     * @param errorCode
     *            错误码
     * @param businessCode
     *            业务码
     * @param validCode
     *            参数校验返回码
     * @return
     */
    public static String parseErrorMsgCode(String errorCode, int businessCode, int validCode) {
        // 业务码或者标志位非负，错误消息码区间在[1000,9999]
        if (validCode < 1000 || validCode > 9999) {
            return errorCode;// 返回空字符串，即使跟未细分前的的错误码拼接，也不会出现找不到错误消息的情况
        }
        if (validCode > 8999) {// 参数校验码大于8999时表示是公共参数校验错误
            businessCode = 0;// 此时0作为一个标志位，表示是公共参数校验问题
            errorCode = "SPC031";// SPC031表示参数校验失败，因为公共参数校验失败都是以SPC031为前缀的，所以此处需要替换
        }
        return errorCode + "_" + businessCode + "_" + validCode;
    }
}
