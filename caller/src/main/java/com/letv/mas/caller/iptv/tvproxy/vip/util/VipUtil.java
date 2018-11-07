package com.letv.mas.caller.iptv.tvproxy.vip.util;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * 会员模块工具类
 * @author KevinYi
 */
public class VipUtil {

    /**
     * 将天数转换为月数，按每月conversionFactor天计算，并四舍五入
     * @param day
     * @param conversionFactor
     * @return
     *         conversionFactor为0时返回0
     */
    public static int parseDayToMonth(int days, int daysOfPerMonth) {
        if (daysOfPerMonth == 0) {
            return 0;
        }
        BigDecimal day = new BigDecimal(String.valueOf(days));
        BigDecimal divisor = new BigDecimal(String.valueOf(daysOfPerMonth));
        BigDecimal month = day.divide(divisor, 0, BigDecimal.ROUND_HALF_UP);

        return month.intValue();
    }

    /**
     * 根据业务码和参数校验返回码，构造参数校验的详细错误码后缀，约定
     * <li>errorMsgCode在[1000,8999]为一般参数校验错误，在[9000,9999]为公共参数校验错误
     * <li>errorMsgCode在[9000,9999]时，businessCode修改为0，作为一个标志位，表示是公共参数校验错误
     * <p>
     * Examples:
     * <blockquote><pre>
     * VipUtil.parseErrorMsgCode(1,1000) returns "_1_1000"
     * VipUtil.parseErrorMsgCode(1,9000) returns "_0_9000"
     * </pre></blockquote>
     * @param businessCode
     *            业务码
     * @param errorMsgCode
     *            参数校验返回码
     * @return
     */
    public static String parseErrorMsgCode(int businessCode, int errorMsgCode) {
        // 业务码或者标志位非负，错误消息码区间在[1000,9999]
        if (errorMsgCode < 1000 || errorMsgCode > 9999) {
            return "";// 返回空字符串，即使跟未细分前的的错误码拼接，也不会出现找不到错误消息的情况
        }
        if (errorMsgCode > 8999) {// 参数校验码大于8999时表示是公共参数校验错误
            businessCode = 0;// 此时0作为一个标志位，表示是公共参数校验问题
        }
        return "_" + businessCode + "_" + errorMsgCode;
    }

    /**
     * 字符串替换<p>
     * Examples:
     * <blockquote><pre>
     * VipUtil.replace4Sequence("1,2,3,4","2","") returns "1,3,4"
     * VipUtil.replace4Sequence("1,2,3,4","2","5") returns "1,5,3,4"
     * VipUtil.replace4Sequence("1","1","2") returns "2"
     * </pre></blockquote>
     * @param str
     *            原字符串，形如：1,2,3
     * @param target
     *            替换的目标
     * @param replacement
     *            替换的内容
     * @return
     */
    public static String replace4Sequence(String str, String target, String replacement) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            if (str.indexOf(target) == -1) {
                return str;
            } else {
                str = str + ",";
                if (StringUtils.isNotBlank(replacement)) {
                    replacement = replacement + ",";
                }
                str = str.replace(target + ",", replacement);
                if (StringUtils.isNotBlank(str)) {
                    return str.substring(0, str.length() - 1);
                } else {
                    return null;
                }
            }
        }
    }

    /**
     * 获取设备终端类型
     * @param terminalBrand
     *            终端品牌
     * @param terminalSeries
     *            终端系列号
     * @return
     */
    public static Integer getSubend(String terminalBrand, String terminalSeries) {
        Integer subend = null;
        if ("letv".equals(terminalBrand)) {// 乐视终端
            subend = VipTpConstantUtils.getSubendByTerminalSeries(terminalSeries);
        } else {// 第三方终端
            subend = VipTpConstantUtils.getSubendByTerminalBrand(terminalBrand);
        }
        // subend为空取默认值-1
        subend = subend == null ? VipTpConstant.SUB_ORDER_FROM_DEFAULT : subend;
        return subend;
    }

    public static String getDiscount(String currentPrice, String originPrice) {
        if (StringUtils.isBlank(currentPrice) || StringUtils.isBlank(originPrice)) {
            return null;
        }
        try {
            return String.format("%.1f", Float.parseFloat(currentPrice) * 10.0f / Float.parseFloat(originPrice));
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            return null;
        }
        /*
         * if (format != null && format.endsWith("0")) {
         * format = format.substring(0, format.indexOf("."));
         * }
         */
    }

    public static String encodeUid(String userId) {
        String encodeStr = "";
        try {
            if (StringUtil.isNotBlank(userId)) {
                String uid = (Long.parseLong(userId) << 2 | 3) + "";
                encodeStr = Base64.encodeBase64String(uid.getBytes());
            }
        } catch (Exception e) {
            // TODO
        }
        return encodeStr.replace("\n", "");
    }
}
