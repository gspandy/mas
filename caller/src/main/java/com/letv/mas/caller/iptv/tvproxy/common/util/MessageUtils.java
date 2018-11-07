package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.MessageSourceSupport;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

@Component
public class MessageUtils {
    static Logger logger = Logger.getLogger(MessageUtils.class);

    private static ResourceBundleMessageSource messageSource;
    /**
     * 默认语言地域信息：汉语_中国
     */
    public static final String DEFAULT_FULL_LOCAL_ZH_CN = "zh_CN";

    /**
     * 默认地域语言环境--汉语简体
     */
    public static final Locale DEFAULT_LOCAL_ZH_CN = new Locale("zh", "CN");


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

    /**
     * 参数化消息获取，并填充参数
     * @param code
     * @param locale
     * @param messageParams
     * @return
     */
    public static String getMessage(String code, Locale locale, Object... codeMap) {
        String message = null;
        if (!StringUtils.isEmpty(code) && locale != null) {
            message = getMessage(code, locale.getLanguage() + "_" + locale.getCountry());
            if (StringUtils.isNotEmpty(message)) {
                message = MessageFormat.format(message, codeMap);
            }
            return message;
        }

        return null;
    }

    /**
     * 参数化消息获取，并填充参数
     * @param code
     * @param lang
     * @param codeMap
     * @return
     */
    public static String getMessage(String code, String lang, Object... codeMap) {
        if (StringUtils.isBlank(lang)) {// 默认中文
            lang = DEFAULT_FULL_LOCAL_ZH_CN;
        }
        String message = null;
        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(lang)) {
            message = getMessage(code, lang);
            if (StringUtils.isNotEmpty(message)) {
                message = MessageFormat.format(message, codeMap);
            }
            return message;
        }

        return null;
    }

    /**
     * 获取多语言消息，返回中文简体的消息
     * @param code
     *            消息的代码
     * @return
     */
    public static String getMessage(String code) {
        return getMessage(code, Locale.CHINA);
    }

    private static Map<String, Map<String, String>> i18nMap = new HashMap<String, Map<String, String>>();
    private static final String MESSAGE_I18N_TITLE = "Message_i18n_";

    private static final String[] REGION_CN_LOCALES = new String[] { "en_US", "zh_CN", "zh_HK", "hi_IN" };
    private static final String[] REGION_HK_LOCALES = new String[] { "en_US", "zh_CN", "zh_HK", "hi_IN" };
    private static final String[] REGION_US_LOCALES = new String[] { "en_US", "zh_CN", "zh_HK", "hi_IN" };
    private static final String[] REGION_LE_LOCALES = new String[] { "en_US", "zh_CN", "zh_HK", "hi_IN" };
    private static final String[] REGION_CIBN_LOCALES = new String[] { "en_US", "zh_CN", "zh_HK", "hi_IN" };
    private static final String[] REGION_WASU_LOCALES = new String[] { "zh_CN" };


    static {
        initMessageCache();
    }

    /**
     * 初始化Message_i18n国际化配置文件
     */
    public static void initMessageCache() {
        String dir = System.getProperty("conf.dir");
        if (dir == null || "".equals(dir)) {
            dir = "conf/";
        } else {
            if (!dir.endsWith("/")) {
                dir += "/";
            }
        }
        HashMap<String, String[]> LOCALE_MAP = new HashMap<String, String[]>();
        LOCALE_MAP.put("cn", REGION_CN_LOCALES);
        LOCALE_MAP.put("hk", REGION_HK_LOCALES);
        LOCALE_MAP.put("cibn", REGION_CIBN_LOCALES);
        LOCALE_MAP.put("us", REGION_US_LOCALES);
        LOCALE_MAP.put("wasu", REGION_WASU_LOCALES);
        LOCALE_MAP.put("le", REGION_LE_LOCALES);
        String region = null;
        try {
            region = PropertiesUtil.getInstance().getPropertiesByFilePath(dir + "region.properties")
                    .getProperty("region");
        } catch (Exception e) {
        }
        if (region == null) {
            region = "cn";
        }

        if (region.equals("native")) {
            initI18N(dir);
        } else {
            initI18N(region, LOCALE_MAP.get(region));
        }
    }

    private static void initI18N(String confDir) {
        File msgDir = new File(confDir);
        File[] msgFiles = msgDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // TODO Auto-generated method stub
                if (!StringUtils.isEmpty(name) && name.contains(MESSAGE_I18N_TITLE) && name.endsWith(".properties")) {
                    return true;
                }
                return false;
            }
        });

        if (msgFiles == null) {
            return;
        }

        Properties bundle;
        InputStream inputStream = null;
        try {
            for (File file : msgFiles) {
                bundle = new Properties();
                inputStream = new FileInputStream(file);
                bundle.load(inputStream);

                // Hashtable读取较慢，转存HashMap中
                Map proMap = new HashMap();
                proMap.putAll(bundle);

                String fileName = file.getName();
                String locale = fileName.substring(MESSAGE_I18N_TITLE.length(), fileName.indexOf(".properties"));
                i18nMap.put(locale, proMap);

                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            logger.warn("initI18N_error" + e.getMessage());
        }

        Set<String> keySet = i18nMap.keySet();
        StringBuilder sb = new StringBuilder("系统支持多语言[");
        for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
            sb.append(iterator.next()).append(",");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

    private static void initI18N(String region, String[] locales) {
        try {
            for (String locale : locales) {
                Properties bundle = PropertiesUtil.getInstance().getPropertiesByClassPath(
                        "/conf/" + region + "/" + MESSAGE_I18N_TITLE + locale + ".properties");

                // Hashtable读取较慢，转存HashMap中
                Map proMap = new HashMap();
                proMap.putAll(bundle);

                i18nMap.put(locale, proMap);
            }
        } catch (Exception e) {
            logger.warn("initI18N_ error" + e.getMessage());
        }

        Set<String> keySet = i18nMap.keySet();
        StringBuilder sb = new StringBuilder("系统支持多语言[");
        for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
            sb.append(iterator.next()).append(",");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }

}