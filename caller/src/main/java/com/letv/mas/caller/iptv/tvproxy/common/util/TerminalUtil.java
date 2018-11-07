package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ProductLineConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TerminalUtil {

    public static final class TermianlApplication {
        private static final String LETV = "letv";
        private static final String LETV_US = "letv_us";
    }

    public static boolean isLetvUs(CommonParam commonParam) {
        return (commonParam != null)
                && (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equals(commonParam.getTerminalApplication()));
    }

    public static boolean isLetvUs(String termianlApplication) {
        return TerminalCommonConstant.TERMINAL_APPLICATION_LETV_US.equals(termianlApplication);
    }

    public static boolean isLetv(CommonParam commonParam) {
        return (commonParam != null)
                && (TerminalCommonConstant.TERMINAL_APPLICATION_LETV.equals(commonParam.getTerminalApplication()));
    }

    public static boolean isMediaCibn(CommonParam commonParam) {
        return (commonParam != null)
                && (TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN.equals(commonParam.getTerminalApplication()));
    }

    public static boolean isLetvHK(CommonParam commonParam) {
        return (commonParam != null)
                && (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_HK.equals(commonParam.getTerminalApplication()));
    }

    public static boolean isLetvCommon(CommonParam commonParam) {
        return (commonParam != null)
                && (TerminalCommonConstant.TERMINAL_APPLICATION_LETV_COMMON
                        .equals(commonParam.getTerminalApplication()));
    }

    public static Integer parseAppCode(String appCode) {
        Integer ac = null;
        try {
            ac = Integer.parseInt(appCode);
        } catch (Exception e) {
        }
        return ac;
    }

    public static boolean supportDistributedPaying(Object params) {
        boolean ret = false;
        String salesArea = null;
        Integer appCode = null;
        String terminalApplication = null;

        if (params instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) params;
            salesArea = StringUtils.trimToNull(request.getParameter("salesArea"));
            appCode = TerminalUtil.parseAppCode(request.getParameter("appCode"));
            terminalApplication = request.getParameter("terminalApplication");
            if (salesArea == null) {
                salesArea = request.getParameter("wcode");
            }
        } else if (params instanceof CommonParam) {
            CommonParam commonParam = (CommonParam) params;
            salesArea = StringUtils.trimToNull(commonParam.getSalesArea());
            appCode = TerminalUtil.parseAppCode(commonParam.getAppCode());
            terminalApplication = commonParam.getTerminalApplication();
            if (salesArea == null) {
                salesArea = commonParam.getWcode();
            }
        }

        if (((TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(terminalApplication) && appCode != null && appCode > 294)
                || (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(terminalApplication)
                        && appCode != null && appCode > 27) || (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE
                    .equalsIgnoreCase(terminalApplication)))
                && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(salesArea)) {
            ret = true;
        }
        return ret;
    }

    public static boolean isTvodScope(CommonParam commonParam) {
        boolean ret = false;
        String area = StringUtils.trimToNull(commonParam.getSalesArea());
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        if (area == null) {
            area = commonParam.getWcode();
        }
        if ((TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam.getTerminalApplication())
                && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area) && ac > 287)
                || (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area))
                || (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area))) {
            ret = true;
        }
        return ret;
    }

    public static boolean isSupportWFSubject(CommonParam commonParam) {
        // 上仿真线放开
        if (commonParam != null && commonParam.getAppCode() != null) {
            Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
            if (ac != null && ac.intValue() > 293) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否支持防盗链
     * @param commonParam
     * @return
     */
    public static boolean supportAntiReport(CommonParam commonParam) {
        boolean ret = false;

        Boolean switcher = ApplicationUtils.getBoolean(ApplicationConstants.IPTV_PLAY_ANTIREPORT_SWITCH);
        if (null == switcher || !switcher) {
            return ret;
        }

        String area = StringUtils.trimToNull(commonParam.getSalesArea());
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        if (area == null) {
            area = commonParam.getWcode();
        }
        if ((TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase(commonParam.getTerminalApplication())
                && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area) && ac > 304)
                ||
                // (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(commonParam.getTerminalApplication())
                // && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area)
                // && ac > 40) ||
                (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area) && ac >= 40)) {
            ret = true;
        }
        if (StringUtil.isBlank(commonParam.getDevSign())) {
            ret = false;
        }
        return ret;
    }

    public static boolean isSupportFairyTaleTown(CommonParam commonParam) {
        boolean ret = false;
        String area = StringUtils.trimToNull(commonParam.getSalesArea());
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        if (area == null) {
            area = commonParam.getWcode();
        }
        if (/*
             * (TerminalCommonConstant.TERMINAL_APPLICATION_CIBN.equalsIgnoreCase
             * (commonParam.getTerminalApplication())
             * && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area)
             * && ac > 303) ||
             */
        ((TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(commonParam.getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN
                .equalsIgnoreCase(area)) && ac > 33)
                || ((TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equalsIgnoreCase(commonParam
                        .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area)) && ac >= 40)) {
            ret = true;
        }
        return ret;
    }

    public static boolean supportJumpLeChildApp(CommonParam commonParam) {
        boolean ret = false;
        String area = StringUtils.trimToNull(commonParam.getSalesArea());
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        if (area == null) {
            area = commonParam.getWcode();
        }
        if (((TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN.equalsIgnoreCase(commonParam
                .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area)) && ac >= 305)) {
            ret = true;
        }
        return ret;
    }

    public static boolean supportMineThreeInOne(CommonParam commonParam) {
        boolean ret = false;
        String area = StringUtils.trimToNull(commonParam.getSalesArea());
        Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
        if (area == null) {
            area = commonParam.getWcode();
        }
        if (((TerminalCommonConstant.TERMINAL_APPLICATION_MEDIA_CIBN.equalsIgnoreCase(commonParam
                .getTerminalApplication()) && ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(area)) && ac >= 305)) {
            ret = true;
        }
        return ret;
    }
}
