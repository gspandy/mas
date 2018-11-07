package com.letv.mas.caller.iptv.tvproxy.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 数字格式化工具类
 * @author KevinYi
 */
public class NumberFormatUtil {

    private static final Log LOG = LogFactory.getLog(NumberFormatUtil.class);

    /**
     * 通用的十进制价格格式，保留两位小数
     */
    public static final String COMMON_DECIMAL_PRICE_FORMAT = "###0.00";

    /**
     * 通用的日期（完整格式年除外）和时间格式，保留完整两位数形式
     */
    public static final String COMMON_DATE_AND_TIME_FORMAT = "#0";

    /**
     * 全部数字类型的正则表达式，包含正负数、小数
     */
    public static final String FULL_NUMBER_REGEX = "^[+-]?\\d+(.\\d+)?$";

    /**
     * 全部数字类型的正则表达式编译形式
     */
    private static final Pattern FULL_NUMBER_REGEX_PATTERN = Pattern.compile(FULL_NUMBER_REGEX);

    /**
     * 对double数据进行格式化，得到数字字符串
     * @param doubleNum
     * @param format
     * @return
     */
    public static String getDecimalNubmerFromDouble(double doubleNum, String format) {
        String result = null;

        try {
            DecimalFormat decimalFormat = new DecimalFormat(format);
            result = decimalFormat.format(doubleNum);
        } catch (Exception e) {
            LOG.error("NumberFormatUtil#getDecimalNubmerFromDouble get error with number [" + doubleNum
                    + "] and format [" + format + "]:", e);
        }

        return result;
    }

    /**
     * 对int数据进行格式化，得到数字字符串
     * @param num
     * @param format
     * @return
     */
    public static String getDecimalNubmerFromInt(int num, String format) {
        String result = null;

        try {
            DecimalFormat decimalFormat = new DecimalFormat(format);
            result = decimalFormat.format(num);
        } catch (Exception e) {
            LOG.error("NumberFormatUtil#getDecimalNubmerFromDouble get error with number [" + num + "] and format ["
                    + format + "]:", e);
        }

        return result;
    }

    /**
     * 判断字符串toValidateStr是否可以转换成数字类型（支持正负数、浮点数）
     * @param toValidateStr
     * @return
     */
    public static boolean isNumeric(String toValidateStr) {
        if (StringUtils.isBlank(toValidateStr)) {
            return false;
        }
        return FULL_NUMBER_REGEX_PATTERN.matcher(toValidateStr).matches();
    }

}
