package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.text.NumberFormat;

/**
 * 计数相关
 * @author maning
 */
public class CountUtil {

    /**
     * 一位小数的Format
     */
    private static final NumberFormat formatOneDot;

    static {
        formatOneDot = NumberFormat.getInstance();
        formatOneDot.setGroupingUsed(false);
        formatOneDot.setMaximumFractionDigits(1);
        formatOneDot.setMinimumFractionDigits(0);
    }

    /**
     * 万以下显示具体数字，保留小数点后一位；万以上，亿以下，显示“ X.X 万—— XXXX.X 万”亿以上显示“ X.X 亿”，：例如：3.5万
     * ，20.3万，2.3亿，遵循四舍五入原则。
     * @param number
     * @return
     */
    public static String getOneDotString(long number) {
        if (number < 10000) {
            return String.valueOf(number);
        } else if (number < 100000000) {
            return formatOneDot.format(number / 10000f) + "万";
        } else {
            return formatOneDot.format(number / 100000000f) + "亿";
        }
    }

}
