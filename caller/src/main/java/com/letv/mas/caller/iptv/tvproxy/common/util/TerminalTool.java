package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TerminalTool {
    private static Set<String> series = new HashSet<String>();
    private static Set<String> box_series = new HashSet<String>();
    private static Set<String> c1_newcharge_series = new HashSet<String>();

    /**
     * 支持4K的设备系列，目前用于乐视自有设备
     */
    public static Set<String> SUPPORT_4K_DEVICES = new HashSet<String>();

    public static Set<String> NOT_SUPPORT_DB_DEVICES = new HashSet<String>();
    public static Set<String> SUPPORT_H265_DEVICES = new HashSet<String>();// 支持H265硬解机型
    static {
        series.add("AMLOGIC8726MX".toUpperCase());// LETV品牌 C1型号
        series.add("AMLOGIC8726MX_C1S".toUpperCase());// LETV品牌 C1型号
        series.add("Android TV on MStar Amber3".toUpperCase());// LETV品牌 x40型号
        series.add("MiBOX_iCNTV".toUpperCase());// 小米盒子
        series.add("IP840".toUpperCase());// 易视腾

        box_series.add("MiBOX_iCNTV".toUpperCase());// 小米盒子
        box_series.add("AMLOGIC8726MX".toUpperCase());// C1
        box_series.add("OTT-letv".toUpperCase());// c1
        box_series.add("AMLOGIC8726MX_C1S".toUpperCase());// C1s
        box_series.add("Hi3716C".toUpperCase());// T1
        box_series.add("Hi3716D".toUpperCase());// T1

        c1_newcharge_series.add("IP840");
        c1_newcharge_series.add("AMLOGIC8726MX");
        c1_newcharge_series.add("AMLOGIC8726MX_C1S");
        c1_newcharge_series.add("Android TV on MStar Amber3");
        c1_newcharge_series.add("MiBOX_iCNTV");

        // SUPPORT_4K_DEVICES.add("MStar Android TV");
        // SUPPORT_4K_DEVICES.add("C2_C2");
        // SUPPORT_4K_DEVICES.add("S255U");
        // SUPPORT_4K_DEVICES.add("Letv X50 Air");
        // SUPPORT_4K_DEVICES.add("Letv X3-55");
        // SUPPORT_4K_DEVICES.add("Letv Max3-65");// X3-65
        // SUPPORT_4K_DEVICES.add("LBA-010-CH");
        // SUPPORT_4K_DEVICES.add("Letv G1");
        // SUPPORT_4K_DEVICES.add("Letv U2");
        // SUPPORT_4K_DEVICES.add("Letv X3-50 UHD");
        // SUPPORT_4K_DEVICES.add("Letv X3-50");
        // SUPPORT_4K_DEVICES.add("Letv Max4-70");
        // SUPPORT_4K_DEVICES.add("X3-55 Pro");
        // SUPPORT_4K_DEVICES.add("Letv U3");
        // SUPPORT_4K_DEVICES.add("U3");
        // SUPPORT_4K_DEVICES.add("Letv X3-55 Pro");
        // SUPPORT_4K_DEVICES.add("Letv Max-120");
        // SUPPORT_4K_DEVICES.add("Letv Max3-120");
        // SUPPORT_4K_DEVICES.add("Letv uMax120");
        // SUPPORT_4K_DEVICES.add("Letv CES65");
        // SUPPORT_4K_DEVICES.add("Letv X3-65");
        // SUPPORT_4K_DEVICES.add("Letv Max4-65 Curved");
        // SUPPORT_4K_DEVICES.add("Letv X4-50");
        // SUPPORT_4K_DEVICES.add("Letv X4-40");
        // SUPPORT_4K_DEVICES.add("Letv X4-43");
        // SUPPORT_4K_DEVICES.add("Letv X4-49");
        // SUPPORT_4K_DEVICES.add("Letv X4-49 Pro");
        // SUPPORT_4K_DEVICES.add("Letv X4-55");
        // SUPPORT_4K_DEVICES.add("Letv X4-55 Curved");
        // SUPPORT_4K_DEVICES.add("Letv X4-65");
        // SUPPORT_4K_DEVICES.add("Letv X4-65 Curved");
        // SUPPORT_4K_DEVICES.add("uMAX85");
        // SUPPORT_4K_DEVICES.add("uMAX120S");
        // SUPPORT_4K_DEVICES.add("Letv X4-50 Pro");

        // NOT_SUPPORT_DB_DEVICES.add("Letv Max4-70");
        // NOT_SUPPORT_DB_DEVICES.add("Letv New C1S"); C1s放开杜比频道 wangshengkai
        // 20161101
        SUPPORT_H265_DEVICES.add("Letv S50 Air");// S50air - cibn
        SUPPORT_H265_DEVICES.add("Letv X50 Air");// X50air - cibn
        SUPPORT_H265_DEVICES.add("Letv S40 Air");// S40air - cibn
        SUPPORT_H265_DEVICES.add("MStar Android TV");// x50air
        SUPPORT_H265_DEVICES.add("MStar Android TV_S250F"); // s50air
        SUPPORT_H265_DEVICES.add("MStar Android TV_S240F"); // s40air
    }

    public static boolean isNotSupport4KBySeries(String series) {

        return !SUPPORT_4K_DEVICES.contains(series);
    }

    public static boolean isUseNewTerminalType(String seriesCode) {
        if (seriesCode == null) {
            return false;
        }
        seriesCode = seriesCode.toUpperCase();
        return series.contains(seriesCode);
    }

    public static boolean isBox(String seriesCode) {
        if (seriesCode == null) {
            return false;
        }
        return (box_series.contains(seriesCode.toUpperCase()));
    }

    public static boolean isSupport3DSeriesCode(String seriesCode) {
        if (seriesCode == null) {
            return true;
        }
        return !"Android TV on MStar Amber3".equalsIgnoreCase(seriesCode);
    }

    public static boolean isNotSupportDbSeriesCode(String seriesCode) {
        if (seriesCode == null) {
            return false;
        }
        return NOT_SUPPORT_DB_DEVICES.contains(seriesCode);
    }

    public static boolean useNewCharge(String seriesCode) {
        // if(seriesCode==null)return false;
        // if(c1_newcharge_series.contains(seriesCode)){
        // return true;
        // }
        return false;
    }

    /**
     * 判断设备是否支持4K
     * @param terminalSeries
     * @return
     *         true--支持；false--不支持
     */
    public static boolean isDeviceSupport4K(String terminalSeries) {
        return SUPPORT_4K_DEVICES.contains(terminalSeries);
    }

    /**
     * 从terminalSeries中更新4K设备支持列表；terminalSeries为null时不执行更新操作
     * @param terminalSeries
     * @return
     *         true--执行更新；false--未执行更新
     */
    public static boolean updateSupport4KDevices(Collection<String> terminalSeries) {
        if (terminalSeries != null) {
            HashSet<String> newData = new HashSet<String>();
            newData.addAll(terminalSeries);
            SUPPORT_4K_DEVICES = newData;
            return true;
        }
        return false;
    }

    /**
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @return
     */
    public static boolean support3D(String supportStream, String terminalSeries) {
        if (supportStream == null) {
            return true;
        }
        String[] supports = supportStream.split("_");
        if ((supports != null) && (supports.length == 3)) {
            return "1".equals(supports[0]);
        }
        return true;
    }

    /**
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @return
     */
    public static boolean supportDB(String supportStream, String terminalSeries) {
        if (supportStream != null) {
            String[] supports = supportStream.split("_");
            if ((supports != null) && (supports.length == 3)) {
                if (NOT_SUPPORT_DB_DEVICES.contains(terminalSeries)) {// 服务端备案
                    return false;
                } else {
                    return !"0".equals(supports[1]);
                }
                // if ("2".equals(supports[1])) {
                // return !NOT_SUPPORT_DB_DEVICES.contains(terminalSeries);//
                // 不知道也代表支持
                // } else {
                // return "1".equals(supports[1]);
                // }
            }
        }

        return true;
    }

    /**
     * @param supportStream
     *            设备是否支持某些特殊码流，格式：1_0_2，依次是3D，DB，4K，0--不支持，1--支持，2--客户端无法判断
     * @return
     */
    public static boolean support4K(String supportStream, String terminalSeries) {
        if (supportStream == null) {
            return true;
        }
        String[] supports = supportStream.split("_");
        if ((supports != null) && (supports.length == 3)) {
            if ("2".equals(supports[2])) {// 走备案逻辑
                return isDeviceSupport4K(terminalSeries);
            } else {
                return "1".equals(supports[2]);
            }
        }
        return true;
    }
}
