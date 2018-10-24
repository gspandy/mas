package com.letv.mas.caller.iptv.tvproxy.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
public class MessageUtils {
    static Logger logger = Logger.getLogger(MessageUtils.class);

    private static ResourceBundleMessageSource messageSource;
    /**
     * 默认语言地域信息：汉语_中国
     */
    public static final String DEFAULT_FULL_LOCAL_ZH_CN = "zh_CN";

    /*static {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setCacheSeconds(-1);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasename("i18n/messages");
    }*/

    public static void init(ResourceBundleMessageSource messageSource){
        MessageUtils.messageSource = messageSource;
    }



    /**
     * 获取多语言消息
     *
     * @param code   消息的代码
     * @param locale 请求所处环境的locale
     * @return
     */
    public static String getMessage(String code, Locale locale) {
        String message = "";
        try {
            message = messageSource.getMessage(code, null, Locale.CHINA);
        } catch (Exception e) {
            logger.error(e.fillInStackTrace());
        }
        return message;
    }

    /**
     * @param code 获取多语言信息的key
     * @param lang 语言环境 eg:zh_CN
     * @return
     */
    public static String getMessage(String code, String lang) {
        String temp[] = getMessageKey(lang);
        if (temp == null) {
            return null;
        }
        Locale locale = new Locale(temp[0], temp[1]);
        return getMessage(code, locale);
    }

    public static String[] getMessageKey(String lang) {
        if (StringUtils.isBlank(lang)) {// 默认中文
            lang = DEFAULT_FULL_LOCAL_ZH_CN;
        }
        try {
            if (lang != null && lang.indexOf("-") > 0) {
                lang = lang.replaceAll("-", "_");
            }
            if (lang != null && lang.indexOf("_") > 0) {
                return new String[]{lang.substring(0, lang.indexOf("_")).toLowerCase(), lang.substring(lang.indexOf("_") + 1).toUpperCase()};
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}