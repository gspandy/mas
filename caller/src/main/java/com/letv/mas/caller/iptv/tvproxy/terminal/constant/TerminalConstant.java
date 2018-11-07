package com.letv.mas.caller.iptv.tvproxy.terminal.constant;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 终端模块常量类
 * @author KevinYi
 */
public class TerminalConstant {

    /**
     * 支持4K设备系列更新配置；更新间隔为5分钟
     */
    public final static long SUPPORT_4K_TERMINAL_SERIES_UPDATE_INTERVAL = 300000L; // 4k设备列表更新间隔，单位-毫秒
    public static long SUPPORT_4K_TERMINAL_SERIES_LASTUPDATE_TIME = 0L; // 上次更新时间，单位-毫秒
    public final static Lock SUPPORT_4K_TERMINAL_SERIES_UPDATE_Lock = new ReentrantLock(); // 用于定时更新

    /**
     * 跳转体育APP添加业务，跳转方式，0--TV版播放，1--仅提示文案，2--跳转APP
     */
    public static final String SPORTS_JUMP_ACTION_TV = "0";
    public static final String SPORTS_JUMP_ACTION_TIPS = "1";
    public static final String SPORTS_JUMP_ACTION_APP = "2";

    /**
     * 终端应用编码
     */
    public static final String TERMINAL_APPLICATION_LEVIDI = "levidi_gl";
    public static final String TERMINAL_APPLICATION_LESO = "letv_search";// 乐搜
    public static final String TERMINAL_APPLICATION_HK = "letv_hk";// 乐视视频香港版
    public static final String TERMINAL_APPLICATION_US = "letv_us";// 乐视视频美国版
    public final static String TERMINAL_APPLICATION_LEVIEW = "leview"; // 乐见终端型号
    public static final String TERMINAL_APPLICATION_LETV = "letv";
    public static final String TERMINAL_APPLICATION_LETV_MOBILETV = "letv_mobileTV";
    public final static String TERMINAL_APPLICATION_CIBN = "media_cibn";// cibn终端型号
    public final static String TERMINAL_APPLICATION_CHILD_CIBN = "child_cibn";// 儿童cibn终端型号
    public static final String TERMINAL_APPLICATION_TPSDK = "letv_live_sdk";// 直播sdk终端型号
    public static final String TERMINAL_APPLICATION_LECHILD_FISH = "lechild_fish"; // 小鱼在家合作版的
    public final static String TERMINAL_APPLICATION_LECHILD_DESK = "lekids_launcher"; // 儿童桌面终端型号
    public final static String TERMINAL_APPLICATION_LECHILD_MOBILE = "lechild_mobile"; // 儿童手机版终端型号

    /**
     * 体育app跳转配置文件中(map类型数据结构)key值分隔符，英文逗号
     */
    public static final String SPORTS_JUMP_CONFIG_KEY_SEPARATOR = ",";

    /**
     * 第三方应用编号
     */
    public static final String THIRD_PARTY_SHOP = "0";// 应用商店
    public static final String THIRD_PARTY_GOLIVE = "1";// golive应用
    public static final String THIRD_PARTY_LESTORE_SUBJECT = "2";// 应用商店专题
    public static final String THIRD_PARTY_GAMECENTER = "3";// 游戏中心
    public static final String THIRD_PARTY_CIBN = "4";// 国广app
    public static final String THIRD_PARTY_WASU = "5";// 华硕app

    /**
     * 服务类条款类型，1--Privacy Policy, 2--Terms of Service
     */
    public static final int SERVICE_TERM_TYPE_PRIVACY_POLICY = 1;
    public static final int SERVICE_TERM_TYPE_SERVICE_TERMS = 2;

    public static final String SERVICE_TERM_NAME_KEY_PRIVACY_POLICY = "PRIVACY_POLICY";
    public static final String SERVICE_TERM_NAME_KEY_SERVICE_TERMS = "SERVICE_TERMS";

    public static final Integer SERVICE_TERM_CONTENT_TYPE_PRIVACY_POLICY = StringUtil.toInteger(
            ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_SERVICE_TERM_CONTENT_TYPE_PRIVACY_POLICY), null);
    public static final Integer SERVICE_TERM_CONTENT_TYPE_SERVICE_TERMS = StringUtil.toInteger(
            ApplicationUtils.get(ApplicationConstants.IPTV_TERMINAL_SERVICE_TERM_CONTENT_TYPE_SERVICE_TERMS), null);

    /**
     * 服务类条款
     * @author KevinYi
     */
    public enum SerciceTermType {
        PRIVACY_POLICY(SERVICE_TERM_TYPE_PRIVACY_POLICY, SERVICE_TERM_NAME_KEY_PRIVACY_POLICY,
                SERVICE_TERM_CONTENT_TYPE_PRIVACY_POLICY),
        SERVICE_TERMS(SERVICE_TERM_TYPE_SERVICE_TERMS, SERVICE_TERM_NAME_KEY_SERVICE_TERMS,
                SERVICE_TERM_CONTENT_TYPE_SERVICE_TERMS);

        private Integer termType;
        private String termNameKey;
        private Integer contentType;

        private SerciceTermType(Integer termType, String termNameKey, Integer contentType) {
            this.termType = termType;
            this.termNameKey = termNameKey;
            this.contentType = contentType;
        }

        public static SerciceTermType getSerciceTermTypeByType(Integer termType) {
            SerciceTermType type = null;
            if (termType != null) {
                switch (termType) {
                case SERVICE_TERM_TYPE_PRIVACY_POLICY:
                    type = PRIVACY_POLICY;
                    break;
                case SERVICE_TERM_TYPE_SERVICE_TERMS:
                    type = SERVICE_TERMS;
                    break;
                default:
                    break;
                }
            }
            return type;
        }

        public Integer getTermType() {
            return termType;
        }

        public void setTermType(Integer termType) {
            this.termType = termType;
        }

        public String getTermNameKey() {
            return termNameKey;
        }

        public void setTermNameKey(String termNameKey) {
            this.termNameKey = termNameKey;
        }

        public Integer getContentType() {
            return contentType;
        }

        public void setContentType(Integer contentType) {
            this.contentType = contentType;
        }

    }
}
