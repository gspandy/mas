package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * 用户模块和会员模块通用常量类，提供两个模块共用的字段或方法，尽可能减少两个模块的同层直接依赖；
 * 如果某一字段或方法只在一个模块中被调用，则可将该字段或方法下移至该模块的Util类中。
 * @author yikun
 */
public class LetvUserVipCommonConstant {

    /**
     * 响应装填码
     */
    public static final int RESPONSE_FAILURE = 0;
    public static final int RESPONSE_SUCCESS = 1;

    /**
     * 默认的字符串中数据分隔符
     */
    public final static String COMMON_SPILT_SEPARATOR = ",";

    /**
     * 通用连字符"_"
     */
    public static final String COMMON_HYPHEN = "_";

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param errCode
     *            错误代码
     * @param locale
     *            用户语言环境
     */
    public static BaseResponse setErrorResponse(BaseResponse response, String errCode, Locale locale) {
        if (response != null) {
            response.setResultStatus(RESPONSE_FAILURE);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, locale));
        }
        return response;
    }

    /**
     * 设置接口处理失败时的返回值，其中错误信息的国际化文案，由errMsgCode指定
     * @param response
     * @param errCode
     * @param errMsgCode
     * @param locale
     * @return
     */
    public static BaseResponse setErrorResponse(BaseResponse response, String errCode, String errMsgCode, Locale locale) {
        if (response != null) {
            response.setResultStatus(RESPONSE_FAILURE);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errMsgCode, locale));
        }
        return response;
    }

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param resultStatus
     *            自定义响应状态码
     * @param errCode
     *            错误代码
     * @param locale
     *            用户语言环境
     */
    public static BaseResponse setErrorResponse(BaseResponse response, Integer resultStatus, String errCode,
            Locale locale) {
        if (response != null) {
            response.setResultStatus(resultStatus);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, locale));
        }
        return response;
    }

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param errCode
     *            错误代码
     * @param locale
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

    /**
     * 设置接口处理失败时的返回值，其中错误信息的国际化文案，由errMsgCode指定
     * @param response
     * @param errCode
     * @param errMsgCode
     * @param locale
     * @return
     */
    public static BaseResponse setErrorResponse(BaseResponse response, String errCode, String errMsgCode,
            String langcode) {
        if (response != null) {
            response.setResultStatus(RESPONSE_FAILURE);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errMsgCode, langcode));
        }
        return response;
    }

    /**
     * 设置接口处理失败时的返回值
     * @param response
     *            返回的BaseResponse
     * @param resultStatus
     *            自定义响应状态码
     * @param errCode
     *            错误代码
     * @param locale
     *            用户语言环境
     */
    public static BaseResponse setErrorResponse(BaseResponse response, Integer resultStatus, String errCode,
            String langcode) {
        if (response != null) {
            response.setResultStatus(resultStatus);
            response.setErrCode(errCode);
            response.setErrMsg(MessageUtils.getMessage(errCode, langcode));
        }
        return response;
    }

    /**
     * 判断userid是否合法；
     * 判断条件为，1.Long型；2.非空；3.大于0
     * @param userid
     * @return
     */
    public static boolean isUseridLegal(Long userid) {
        return userid != null && userid > 0;
    }

    /**
     * 获取请求的IP地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        return HttpUtil.getIP(request);
    }

    /**
     * 从request信息中获取语言-地域信息封装类Locale
     * @param request
     * @return
     */
    public static Locale getLocale(HttpServletRequest request) {
        if (request == null) {
            return MessageUtils.DEFAULT_LOCAL_ZH_CN;
        }

        String langcode = request.getParameter(ResponseUtil.CLIENT_LANGUAGE);
        String wcode = request.getParameter(ResponseUtil.CLIENT_COUNTRY);
        return getLocale(langcode, wcode);
        // return MutilLanguageConstants.getLocale(request);
    }

    /**
     * 根据语言langcode和地域wcode获取语言-地域信息封装类Locale
     * @param langcode
     * @param wcode
     * @return
     */
    public static Locale getLocale(String langcode, String wcode) {

        try {
            if (langcode != null && langcode.indexOf("-") > 0) {
                langcode = langcode.replaceAll("-", "_");
            }
            if (langcode != null && langcode.indexOf("_") > 0) {
                String language = langcode.substring(0, langcode.indexOf("_")).toLowerCase();
                String country = langcode.substring(langcode.indexOf("_") + 1).toUpperCase();
                return new Locale(language, country);
            } else {
                return new Locale(langcode.toLowerCase(), wcode.toUpperCase());
            }
        } catch (Exception e) {
        }

        return new Locale("zh", "CN");
    }

    /**
     * 获取语言_地域信息字符串，各位为"语言_地域"，如"zh_CN"
     * @param locale
     * @return
     */
    public static String getLocalString(Locale locale) {
        if (locale == null) {
            return MessageUtils.DEFAULT_FULL_LOCAL_ZH_CN;
        }
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    /**
     * 从request中获取语言-地域信息字符串，获取结果不一定合法，受传参影响
     * @param request
     * @return
     */
    public static String getLocalString(HttpServletRequest request) {
        if (request == null) {
            return MessageUtils.DEFAULT_FULL_LOCAL_ZH_CN;
        }

        String langcode = request.getParameter(ResponseUtil.CLIENT_LANGUAGE);
        if (StringUtils.isEmpty(langcode)) {
            return MessageUtils.DEFAULT_FULL_LOCAL_ZH_CN;
        }
        if (langcode.indexOf("-") > 0) {
            langcode = langcode.replaceAll("-", "_");
        }
        if (langcode.indexOf("_") > 0) {
            return langcode.substring(0, langcode.indexOf("_")).toLowerCase()
                    + langcode.substring(langcode.indexOf("_") + 1).toUpperCase();
        } else {
            String wcode = request.getParameter(ResponseUtil.CLIENT_COUNTRY);
            return langcode.toLowerCase() + wcode.toUpperCase();
        }
    }
}
