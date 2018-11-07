package com.letv.mas.caller.iptv.tvproxy.common.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;


public class StringTool {

    private StringTool() {
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
     * @param  strOriginal,原字符串内容
     * @param  strOld, 需要替换的内容
     * @param  strNew, 用来替换的内容
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

    //
    //
    // public static void main(String[] args) {
    // System.out.println(StringTool.EncodedByMD5("1"));
    // try {
    // System.out.println(getStringLen("撒打发a%^&sdf3s撒打发士大夫撒阿斯顿飞洒的发生的",16));
    // } catch (UnsupportedEncodingException e) {
    // e.printStackTrace();
    // }
    // }
}
