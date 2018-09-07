package com.letv.mas.caller.iptv.tvproxy.user.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final String BLANK = "";

    public static String dealNull(String str) {
        return str != null ? str.trim() : BLANK;
    }

    private StringUtil() {
    }

    /**
     * 其它类的对象转换成String对象
     * 可以转换的类包括:{@link Integer},{@link Short},{@link Long},{@link Float},
     * {@link Double}, {@link Character},{@link Byte},{@link Boolean}等基础数据类型封装类,
     * 此方法相当于调用obj的toString()方法(异常catch自处理),所以其他类型请谨慎试用
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        try {
            return obj.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer toInteger(String s) {
        return toInteger(s, null);
    }

    public static Integer toInteger(String s, Integer defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Long toLong(String s) {
        return toLong(s, null);
    }

    public static Long toLong(String s, Long defaultValue) {
        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Float toFloat(String s) {
        return toFloat(s, null);
    }

    public static Float toFloat(String s, Float defaultValue) {
        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Double toDouble(String s, Double defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    static public String[] getStringToArray(String str, String delim) {
        StringTokenizer st = new StringTokenizer(str, delim);
        int count = st.countTokens();
        String[] strArr = new String[count];
        int i = 0;
        while (st.hasMoreTokens()) {
            strArr[i++] = st.nextToken();
        }
        return strArr;
    }

    static public Set<String> getStringToSet(String str, String delim) {
        StringTokenizer st = new StringTokenizer(str, delim);
        int count = st.countTokens();
        Set<String> temp = new HashSet<>();
        int i = 0;
        while (st.hasMoreTokens()) {
            temp.add(st.nextToken());
        }
        return temp;
    }

    static public String getArrayToString(String[] array, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                sb.append(delim).append(array[i]);
            } else {
                sb.append(array[i]);
            }
        }
        return sb.toString();
    }

    static public String getArrayToStringEx(Object[] array, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                sb.append(delim).append(String.valueOf(array[i]));
            } else {
                sb.append(String.valueOf(array[i]));
            }
        }
        return sb.toString();
    }

    static public String getListToString(List<String> list, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                sb.append(list.get(i));
            } else {
                sb.append(delim).append(list.get(i));
            }
        }
        return sb.toString();
    }

    static public <T> String getListToStringEx(List<T> list, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                sb.append(String.valueOf(list.get(i)));
            } else {
                sb.append(delim).append(String.valueOf(list.get(i)));
            }
        }
        return sb.toString();
    }

    public static long IPToLong(String ip) {
        long result = 0;
        String[] ip_feild = ip.split("\\.");
        try {
            for (int i = 0; i < ip_feild.length; i++) {
                result += Long.parseLong(ip_feild[i]) << (8 * (3 - i));
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 将字符串用MD5加密
     * @param password
     *            String
     * @return String
     */
    public static String EncodedByMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            return byte2string(md.digest());
        } catch (Exception e) {
            return password;
        }
    }

    /**
     * 二进制转字符串
     * @param b
     *            byte[]
     * @return String
     */
    public static String byte2string(byte[] b) {
        StringBuilder hs = new StringBuilder(100);
        for (int n = 0; n < b.length; n++) {
            hs.append(byte2fex(b[n]));
        }
        return hs.toString();
    }

    /**
     * 将byte转成16Fex
     * @param ib
     *            byte
     * @return String
     */
    public static String byte2fex(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    /**
     * 全部替换字符串里符合指定内容的为其它内容，与String类中不同，它使用的不是正则表达式的。
     * @param strOriginal,原字符串内容
     * @param strOld, 需要替换的内容
     * @param strNew, 用来替换的内容
     * @return String, 字符串替换后的内容
     */
    public static String replace(String strOriginal, String strOld, String strNew) {
        int i = 0;
        StringBuilder strBuffer = new StringBuilder(strOriginal);
        while ((i = strOriginal.indexOf(strOld, i)) >= 0) {
            strBuffer.delete(i, i + strOld.length());
            strBuffer.insert(i, strNew);
            i = i + strNew.length();
            strOriginal = strBuffer.toString();
        }
        return strOriginal;
    }

    public static String formatDbInStr(String str) {
        StringBuilder strBuffer = new StringBuilder();
        if (str != null) {
            String[] array = str.split(",");
            int j = 0;
            for (String s : array) {
                if (StringUtils.isNotBlank(s) && StringUtils.isNumeric(s)) {
                    if (j > 0) {
                        strBuffer.append(",");
                    }
                    strBuffer.append(s);
                    j++;
                }
            }
        }
        return strBuffer.toString();
    }

    /**
     * 返回某长度的字符串
     * @param str
     * @param length
     * @throws UnsupportedEncodingException
     */
    public static String getStringLen(String str, int length) throws UnsupportedEncodingException {
        // System.out.println(str.getBytes().length);
        int n = 0;
        int m = 0;
        for (int i = 0; i < str.length(); i++) {
            String s = "" + str.charAt(i);
            if (s.getBytes("gbk").length == 2) {
                n += 2;
            } else {
                n++;
            }
            if (n > length) {
                m = i;
                break;
            }
        }
        if (m == 0) {
            return str;
        } else {
            return str.substring(0, m);
        }

    }

    public static boolean includeChinese(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (isChinese(ch)) {
                return true;
            }
        }
        return false;

    }

    public static boolean isChinese(char ch) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }

        return false;
    }

    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static synchronized String createKey() {
        String key = String.valueOf(System.currentTimeMillis());
        try {
            Thread.currentThread();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            // do nothing
        }
        return key;
    }

    public static String toyyyyMMdd(String yyyy_MM_dd) {
        return yyyy_MM_dd.replace("-", "");
    }

    public static String toShortDateFormat(Integer yyyyMMdd) {
        String str = String.valueOf(yyyyMMdd);
        Calendar calendar = TimeUtil.parseCalendar(str, TimeUtil.SHORT_DATE_FORMAT_NO_DASH);
        String result = TimeUtil.getDateString(calendar, TimeUtil.SHORT_DATE_FORMAT);
        return result;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    // 过滤特殊字符
    public static String StringFilter(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？ ]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str1 == str2;
        }
        return str1.equals(str2);

    }

    public static String getSubStr(String str, int subNu, String replace) {
        if (str == null) {
            str = "";
        }
        int strLength = str.length();
        if (strLength >= subNu) {
            str = str.substring((strLength - subNu), strLength);
        } else {
            for (int i = strLength; i < subNu; i++) {
                str += replace;
            }
        }
        return str;
    }

    public static String getUUIDString(String tBrand, String tSeries, String tUnique, int subNu, String replace) {
        return MD5Util.md5(getSubStr(tBrand, subNu, replace) + getSubStr(tSeries, subNu, replace)
                + getSubStr(tUnique, subNu, replace));
    }

    public static String getMd5TerminalUuid(String tBrand, String tSeries, String tUnique) {
        String terminalUuid = "";
        if (StringUtils.isNotBlank(tBrand)) {
            terminalUuid += tBrand;
        }
        if (StringUtils.isNotBlank(tSeries)) {
            terminalUuid += "_" + tSeries;
        }
        if (StringUtils.isNotBlank(tUnique)) {
            terminalUuid += "_" + tUnique;
        }
        return MD5Util.md5(terminalUuid);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(value);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains(".")) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
