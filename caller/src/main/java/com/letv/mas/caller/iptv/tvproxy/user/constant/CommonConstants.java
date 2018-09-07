package com.letv.mas.caller.iptv.tvproxy.user.constant;

import com.letv.mas.caller.iptv.tvproxy.user.util.ConfigOperationUtil;

public class CommonConstants {

    // 时间
    public static final int SECONDS_OF_1_MINUTE = 60; // 一分钟的秒数 60
    public static final int SECONDS_OF_5_MINUTE = 60 * 5;// 五分钟的秒数，300
    public static final int SECONDS_OF_10_MINUTE = 60 * 10;// 十分钟的秒数，600
    public static final int SECONDS_OF_15_MINUTE = 60 * 15;// 十五分钟的秒数，900
    public static final int SECONDS_OF_30_MINUTE = 60 * 30;// 三十分钟的秒数， 1800
    public static final int SECONDS_OF_1_HOUR = 60 * 60; // 一小时的秒数，3600
    public static final int SECONDS_OF_1_DAY = 60 * 60 * 24; // 一天的秒数，86400
    public static final int SECONDS_OF_1_MONTH = 60 * 60 * 24 * 30;// 一月的秒数
    public static final int SECONDS_OF_1_YEAR = 60 * 60 * 24 * 365;// 一年的秒数


    /**
     * 编码
     */
    public static final String UTF8 = "UTF-8";

    /**
     * tv版 platform值
     */
    public static final String TV_PLATFROM = "tv";

    /**
     * tv版 code
     */
    public static final String TV_PLATFROM_CODE = "420007";
    public static final String TV_PAY_CODE = "141007"; //TV自有版
    public static final String TV_BOX_PAY_CODE = "141011"; //盒子自有版
    public static final String TV_3RD_PAY_CODE = "141010"; //第三方市场

    /**
     * braodcastId的定义
     */
    public static final int LETV = 0;
    public static final int CNTV = 1;
    public static final int CIBN = 2;
    public static final int WASU = 3;

    /*public static final String DEFAULT_WCODE = ApplicationUtils.get(ApplicationConstants.IPTV_WCODE_DEFAULT_VALUE);*/

    public static final String getDefaultWcode(){
        return ConfigOperationUtil.get(ApplicationConstants.IPTV_WCODE_DEFAULT_VALUE);
    }

    /**
     * 分端付费总开关
     */
    public static final boolean VIP_DISTRIBUTED_PAYING_ENBABLE = true;

    /**
     * 分端付费平台映射关系
     */
    public enum PAY_PLATFORM_TYPE {
        TV(2, CommonConstants.TV_PAY_CODE),
        TV_BOX(5, CommonConstants.TV_BOX_PAY_CODE),
        TV_3RD(7, CommonConstants.TV_3RD_PAY_CODE);

        private Integer devType;
        private String platform;

        PAY_PLATFORM_TYPE(Integer devType, String platform) {
            this.devType = devType;
            this.platform = platform;
        }

        public Integer getDevType() {
            return this.devType;
        }

        public void setDevType(Integer devType) {
            this.devType = devType;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
           this.platform = platform;
        }

        public static PAY_PLATFORM_TYPE getByDevType(Integer devType) {
            if (2 == devType) {
                return TV;
            }
            if (5 == devType) {
                return TV_BOX;
            }
            if (7 == devType) {
                return TV_3RD;
            }
            return null;
        }

        public static PAY_PLATFORM_TYPE getByDevType(String devType) {
            if (null != devType) {
                return getByDevType(Integer.valueOf(devType));
            }
            return null;
        }

        public static PAY_PLATFORM_TYPE getByPlatform(String platform) {
            if (CommonConstants.TV_PAY_CODE.equals(platform)) {
                return TV;
            }
            if (CommonConstants.TV_BOX_PAY_CODE.equals(platform)) {
                return TV_BOX;
            }
            if (CommonConstants.TV_3RD_PAY_CODE.equals(platform)) {
                return TV_3RD;
            }
            return null;
        }

        public static String getAllPlatforms() {
            StringBuilder sb = new StringBuilder();

            for (PAY_PLATFORM_TYPE pay_platform_type : PAY_PLATFORM_TYPE.values()) {
                sb.append(pay_platform_type.getPlatform()).append(",");
            }

            if (sb.length() > 0) { //remove the last ","
                sb.replace(sb.length() - 1, sb.length(), "");
            }

            return  sb.toString();
        }
    }
}
