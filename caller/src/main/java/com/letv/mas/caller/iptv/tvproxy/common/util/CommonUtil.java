/*

 * Pprun's Public Domain.
 */
package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Constant used in the utility method.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class CommonUtil {
    private final static Logger LOG = LoggerFactory.getLogger(CommonUtil.class);

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";
    private static final List<String> useCnCountryApp = new ArrayList<String>();
    static {
        useCnCountryApp.add(TerminalCommonConstant.TERMINAL_APPLICATION_LETV);
        useCnCountryApp.add(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US);
        useCnCountryApp.add(TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN);
        useCnCountryApp.add(TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON);
    }

    public static Boolean checkSig(Map<String, Object> params) {
        String sig = (String) params.get("sig");
        String md5sig = genSig(params);
        if (!md5sig.equalsIgnoreCase(sig)) {
            return false;
        }
        return true;
    }

    public static String genSig(Map<String, Object> params) {
        String sig = (String) params.get("sig");
        params.remove("sig");
        return getMd5Str(params, "itv12345678!@#$%^&*");
    }

    /**
     * 获得md5值
     * @param params
     * @return
     */
    public static String getMd5Str(Map<String, Object> params, String secrectKey) {
        List<String> listStr = new ArrayList<String>();
        String md5 = "";

        String key_value = "";
        Iterator<String> it = params.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = params.get(key) + "";
            listStr.add(key + "=" + value);
        }
        Collections.sort(listStr);
        for (String str : listStr) {
            key_value += str + "&";
        }
        String md5str = key_value.substring(0, key_value.length() - 1) + secrectKey;
        try {
            md5 = MessageDigestUtil.md5(md5str.getBytes("UTF-8"));
        } catch (Exception e) {
            LOG.error("getMd5Str_ error", e.getMessage(), e);
        }
        return md5;
    }

    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     * @param encryptText
     *            被签名的字符串
     * @param encryptKey
     *            密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);

        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    /**
     * 对url编码，不同于urlEncode的是会将空格编码成%20
     * @param encodeStr
     * @return
     */
    public static String rawUrlEncode(String encodeStr) {
        if (encodeStr == null || encodeStr == "") {
            return "";
        }
        String encode = "";
        try {
            encodeStr = encodeStr.replaceAll(" ", "%20");
            encode = URLEncoder.encode(encodeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return encode;
    }

    /**
     * 对url编码，此方法将空格编码成"+"
     * @param encodeStr
     * @return
     */
    public static String urlEncode(String encodeStr) {
        if (encodeStr == null || encodeStr == "") {
            return "";
        }
        String encode = "";
        try {
            encode = URLEncoder.encode(encodeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return encode;
    }

    /**
     * 对url编码，此方法将空格编码成"+"
     * @param decodeStr
     * @return
     */
    public static String urlDecode(String decodeStr) {
        if (decodeStr == null || decodeStr == "") {
            return "";
        }
        String decode = "";
        try {
            decode = URLDecoder.decode(decodeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
        return decode;
    }

    public static VipTpConstant.Country getCountryInfo(CommonParam commonParam) {
        if (useCnCountryApp.contains(commonParam.getTerminalApplication())) {
            return VipTpConstant.Country.CN;
        }
        String eara = StringUtils.isNotEmpty(commonParam.getSalesArea()) ? commonParam.getSalesArea() : commonParam
                .getWcode();
        return VipTpConstant.Country.findType(eara);
    }

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static String getCurSecTimestamp() {
        String timestamp = String.valueOf(new Date().getTime());
        int length = timestamp.length();
        if (length > 3) {
            return timestamp.substring(0, length - 3);
        } else {
            return "0";
        }
    }
}
