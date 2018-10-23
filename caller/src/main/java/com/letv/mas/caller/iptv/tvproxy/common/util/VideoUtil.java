package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ProductLineConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
