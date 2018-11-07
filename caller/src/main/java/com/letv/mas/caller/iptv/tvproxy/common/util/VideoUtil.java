package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ProductLineConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VideoUtil {
    private static final Logger log = LoggerFactory.getLogger(VideoUtil.class);
    /**
     * 获得子平台ID
     *
     */
    public static int getSplatId(Integer model, CommonParam commonParam) {
        int splatId = 0;

        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            // 投屏播放特殊分配
            splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_TV_TOUPING;
        } else if (VideoConstants.CIBN_BSCHANNEL_OPERATORS.equalsIgnoreCase(commonParam.getBsChannel())) {
            splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_CIBN_OPERATORS_PC;// 国广版本第三方合作播放
        } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LE
                .equalsIgnoreCase(commonParam.getTerminalApplication())) {

            // splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA;
            // TV le使用推荐升级方式，线上有多个版本，这里针对不同集成了不同版本CDE的TV Le版本下发不同splatid
            int appCodeInt = StringUtil.toInteger(commonParam.getAppCode(), 0);
            if (appCodeInt >= VideoConstants.PLAY_HAS_NEW_CDE_LE_VERSION) {
                splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_TV_LECOM_US;
            } else {
                splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA;
            }
        } else if (ProductLineConstants.WCODE.LETV_CN.equalsIgnoreCase(commonParam.getWcode())) {
            if (ProductLineConstants.LETV_COMMON.equalsIgnoreCase(commonParam.getTerminalApplication())) {
                splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_COMMON;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_FISH.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_FISH;
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_CHILD_APP.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
                Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
                String terminalSeries = StringUtil.dealNull(commonParam.getTerminalSeries());
//                if (VideoConstants.LETV_SERIES_TV_SET.contains(commonParam.getTerminalSeries())) {
                if (!VideoConstants.LETV_SERIES_BOX_SET.contains(terminalSeries.toUpperCase())) {
                    splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV;
                    } else if (ac >= 40) {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV;
                    } else {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_TV;
                    }
                } else {
                    splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX;
                    } else if (ac >= 40) {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX;
                    } else {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_BOX;
                    }
                }
            } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LECHILD_ALONE.equalsIgnoreCase(commonParam
                    .getTerminalApplication())) {
//                Integer ac = TerminalUtil.parseAppCode(commonParam.getAppCode());
                String terminalSeries = StringUtil.dealNull(commonParam.getTerminalSeries());
//                if (VideoConstants.LETV_SERIES_TV_SET.contains(commonParam.getTerminalSeries())) {
                if (!VideoConstants.LETV_SERIES_BOX_SET.contains(terminalSeries.toUpperCase())) {
                    splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_TV;
                } else {
                    splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_LECHILD_BOX;
                }
            } else {
                String terminalSeries = StringUtil.dealNull(commonParam.getTerminalSeries());
//                if (VideoConstants.LETV_SERIES_TV_SET.contains(commonParam.getTerminalSeries())) {
                if (!VideoConstants.LETV_SERIES_BOX_SET.contains(terminalSeries.toUpperCase())) {
                    splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_TV;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_TV_NEW;
                    }
                } else {
                    splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_BOX;
                    if (TerminalUtil.supportAntiReport(commonParam)) {
                        splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_BOX_NEW;
                    }
                }
            }
        } else {
            splatId = VideoConstants.CDE_VIDEO_PLAY_SPLATID_LETV_OVERSEA;
        }

        return splatId;
    }

    /**
     * 可多机器登录userId白名单
     */
    public static Set<String> MUTIL_MACHINE_LOGIN_USERID_WHITE_SET = new HashSet<String>();


    /**
     * 获取专辑更新期数
     * @param Num
     * @param format1
     * @param format2
     * @param format3
     * @return
     */
    public static String getFollowNum(Integer Num, String format1, String format2, String format3) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df = new SimpleDateFormat(format1);
            SimpleDateFormat df1 = new SimpleDateFormat(format2);
            SimpleDateFormat df2 = new SimpleDateFormat(format3);
            Date followDate = sdf.parse(Num.toString());
            Date nowDate = new Date();
            String followStr = null;
            String nowStr = null;
            String followNum = "";
            followStr = df.format(followDate);
            nowStr = df.format(nowDate);
            if (Integer.parseInt(followStr) < Integer.parseInt(nowStr)) {
                followNum = df1.format(followDate);
            } else {
                followNum = df2.format(followDate);
            }
            return followNum;
        } catch (ParseException e) {
            log.warn("getFollowNum error", e.getMessage(), e);
        }
        return "";
    }

    /**
     * mac白名单
     */
    private static Set<String> NO_AREA_PLAY_RESTRICT_MAC_SET = new HashSet<String>();


    public static void clearMacWhiteList() {
        NO_AREA_PLAY_RESTRICT_MAC_SET = null;
    }
}
