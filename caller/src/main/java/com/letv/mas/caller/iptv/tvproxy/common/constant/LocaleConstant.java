package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocaleConstant {
    /**
     ************************************************* 国家代号/区域值
     */
    public static final class Wcode {
        /** 中国大陆 */
        public static final String CN = "cn";

        /** 香港 */
        public static final String HK = "hk";

        /** 美国 */
        public static final String US = "us";

        /** 英国 */
        public static final String UK = "uk";

        /** 韩国 */
        public static final String KR = "kr";

        /** 日本 */
        public static final String JP = "jp";

        /** 俄国 */
        public static final String RU = "ru";

        /** 美国 */
        public static final String FR = "fr";

        /** 德国 */
        public static final String DE = "de";

        /** 意大利 */
        public static final String IT = "it";

        /** 西班牙 */
        public static final String ES = "es";

        /** 印度 */
        public static final String IN = "in";

        /** 其他 */
        public static final String OTHER = "other";
    }

    /**
     * 销售地
     * @author KevinYi
     */
    public static final class SalesArea {
        /** 中国大陆 */
        public static final String CN = "CN";

        /** 香港 */
        public static final String HK = "HK";

        /** 美国 */
        public static final String US = "US";

        /** 英国 */
        public static final String UK = "UK";

        /** 韩国 */
        public static final String KR = "KR";

        /** 日本 */
        public static final String JP = "JP";

        /** 俄国 */
        public static final String RU = "RU";

        /** 美国 */
        public static final String FR = "FR";

        /** 德国 */
        public static final String DE = "DE";

        /** 意大利 */
        public static final String IT = "it";

        /** 西班牙 */
        public static final String ES = "ES";

        /** 印度 */
        public static final String IN = "IN";

        /** 其他 */
        public static final String OTHER = "other";
    }

    /**
     ************************************************* 语言
     */
    public final class Langcode {
        /** 简体中文 */
        public static final String ZH_CN = "zh_cn";

        /** 繁体（香港） */
        public static final String ZH_HK = "zh_hk";

        /** 繁体（台湾） */
        public static final String ZH_TW = "zh_tw";

        /** 印度语 */
        public static final String HI_IN = "hi_in";// TODO 不确定，待定

        /** 美式英语 */
        public static final String EN_US = "en_us";
    }

    /** 系统默认地域(服务器所在的地区) */
    public static final String DEFAULT_WCODE;

    /** 默认语言 */
    public static final String DEFAULT_LANGUAGE;

    /** 支持语言的列表 */
    public static final List<String> LANGUAGE_LIST = new ArrayList<String>();

    /** 地域和对应默认语言的map */
    public static final Map<String, String> DEFAULT_LANGUAGE_MAP = new HashMap<String, String>();

    static {
        LANGUAGE_LIST.add(Langcode.ZH_CN);
        LANGUAGE_LIST.add(Langcode.ZH_HK);
        LANGUAGE_LIST.add(Langcode.EN_US);
        LANGUAGE_LIST.add(Langcode.HI_IN);

        DEFAULT_LANGUAGE_MAP.put(Wcode.CN, Langcode.ZH_CN);
        DEFAULT_LANGUAGE_MAP.put(Wcode.HK, Langcode.ZH_HK);
        DEFAULT_LANGUAGE_MAP.put(Wcode.US, Langcode.EN_US);
        DEFAULT_LANGUAGE_MAP.put(Wcode.IN, Langcode.HI_IN);
        DEFAULT_LANGUAGE_MAP.put(Wcode.OTHER, Langcode.ZH_CN);

        String configDefaultWcode = ApplicationUtils.get(ApplicationConstants.IPTV_WCODE_DEFAULT_VALUE);// 获取默认地域
        DEFAULT_WCODE = configDefaultWcode == null ? Wcode.CN : configDefaultWcode;
        DEFAULT_LANGUAGE = DEFAULT_LANGUAGE_MAP.get(DEFAULT_WCODE) == null ? Langcode.ZH_CN : DEFAULT_LANGUAGE_MAP
                .get(DEFAULT_WCODE);
    }
}
