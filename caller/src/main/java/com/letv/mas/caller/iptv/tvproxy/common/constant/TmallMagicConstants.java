package com.letv.mas.caller.iptv.tvproxy.common.constant;

import java.util.ArrayList;
import java.util.List;

public class TmallMagicConstants {
    // 天猫盒子设备型号列表
    private static List<String> SERIES_LIST = new ArrayList<String>();

    public static boolean canTrust(String series) {
        return SERIES_LIST.contains(series);
    }

    static {
        // 恒颖超
        SERIES_LIST.add("BIJELA_HX7625");
        // 华为
        SERIES_LIST.add("EC6106V6U_pub_gdydx");
        SERIES_LIST.add("EC6106V6U_pub");
        SERIES_LIST.add("EC6106V6U_pub_20_zjzdx");
        SERIES_LIST.add("EC6106V6U_pub_30_zjzdx");
        SERIES_LIST.add("EC6106V6U_pub_ut_zjzdx");
        SERIES_LIST.add("MSG5100");
        SERIES_LIST.add("EC6108V21");
        SERIES_LIST.add("EC6108V2");
        SERIES_LIST.add("EC6106V6U_pub_sccdx");
        SERIES_LIST.add("EC6108V8");
        // 华数
        SERIES_LIST.add("WASU_A200_ZW");
        SERIES_LIST.add("WASU_L200_NK");
        SERIES_LIST.add("WASU_A100");
        SERIES_LIST.add("WASU_A200");
        SERIES_LIST.add("WASU_A200f");
        // 天敏
        SERIES_LIST.add("a31s_sdk");
        SERIES_LIST.add("10MOONS_D6Q");
        SERIES_LIST.add("10MOONS_D3");
        SERIES_LIST.add("10MOONS_390WQ");
        SERIES_LIST.add("10MOONS_ELF4");
        SERIES_LIST.add("D8");
        SERIES_LIST.add("LT390W");
        SERIES_LIST.add("LT380W");
        SERIES_LIST.add("LT390W_Duo");
        SERIES_LIST.add("LT380W_Duo");
        SERIES_LIST.add("TM5_Duo");
        SERIES_LIST.add("D6-Duo");
        SERIES_LIST.add("rk30sdk");
        SERIES_LIST.add("10MOONS_D8");
        SERIES_LIST.add("10moons_EYE");
        SERIES_LIST.add("10moons_A20");
        SERIES_LIST.add("g02ref");
        SERIES_LIST.add("10moons_ELF");
        SERIES_LIST.add("10MOONS_ELF3");
        SERIES_LIST.add("10MOONS_LT390WD");
        SERIES_LIST.add("10MOONS_D8G");
        SERIES_LIST.add("10MOONS_LT390W");
        SERIES_LIST.add("10MOONS_LT380W");
        SERIES_LIST.add("10MOONS_LT380WD");
        SERIES_LIST.add("10MOONS_TM5");
        SERIES_LIST.add("10MOONS_D6");
        // 三零凯天
        SERIES_LIST.add("MBX-f16ref");
        SERIES_LIST.add("KT-IH1223a");
        // RK
        SERIES_LIST.add("RK31SDK");
        SERIES_LIST.add("rk3028sdk");
        // MTC
        SERIES_LIST.add("MTC_TV01H");
        SERIES_LIST.add("MTC_TV01P");
        // 智屏
        SERIES_LIST.add("ZPOTT_BERLIN");
        SERIES_LIST.add("ZPOTT_18");
        // 黑客士
        SERIES_LIST.add("HEXER_DING");
        SERIES_LIST.add("smdk4x12");
        // 佳思
        SERIES_LIST.add("UTOOBOX_T20");
        // 佳的美
        SERIES_LIST.add("GADMEIBOX_G20");
        // TCL
        SERIES_LIST.add("TCL_CHOCOLATE");
        SERIES_LIST.add("TCL_TK8263_T");
        // 迈乐
        SERIES_LIST.add("M6LN");
        SERIES_LIST.add("A31SDK");
        SERIES_LIST.add("M8");
        SERIES_LIST.add("MagicBox_beta");
        SERIES_LIST.add("MELE_A100");
        SERIES_LIST.add("M6S");
        SERIES_LIST.add("SoftwinerEvb");
        SERIES_LIST.add("MELE_A200");
        SERIES_LIST.add("MELE_M1");
        SERIES_LIST.add("MELE_M5");
        SERIES_LIST.add("MELE_A200D");
        SERIES_LIST.add("MELE-Q");
        SERIES_LIST.add("MELE_X8");
        SERIES_LIST.add("MELE_M9");
        SERIES_LIST.add("M6L");
        // 天猫
        SERIES_LIST.add("TVRR");
        SERIES_LIST.add("MagicBox");
        // 方果
        SERIES_LIST.add("XEME_HYUNDAIA11");
        SERIES_LIST.add("XEME_ASIMAH201");
        // 海信
        SERIES_LIST.add("IP808H_57T1");
        // OPENBASE
        SERIES_LIST.add("OPENBASE_MT8658");
        SERIES_LIST.add("OPENBASE_ATM7029");
        SERIES_LIST.add("OPENBASE_MSD6A801");
        SERIES_LIST.add("OPENBASE_RK3188");
        SERIES_LIST.add("OPENBASE_RK3066");
        SERIES_LIST.add("OPENBASE_RK3028A");
        SERIES_LIST.add("OPENBASE_HI3716CV200");
        SERIES_LIST.add("OPENBASE_HI3716CV100");
        SERIES_LIST.add("OPENBASE_AML8726M8");
        SERIES_LIST.add("OPENBASE_AML8726MX");
        SERIES_LIST.add("OPENBASE_A31");
        SERIES_LIST.add("OPENBASE_A20");
        SERIES_LIST.add("OPENBASE_A10S");
        // 智造(美威视)
        SERIES_LIST.add("SMARTZ_N3");
        // 长虹
        SERIES_LIST.add("A231_MX_SCDX");
        // 美如画
        SERIES_LIST.add("Mygica_A11");
        SERIES_LIST.add("MYGICA_V8");
        SERIES_LIST.add("MyGica TvBoxMX");
        SERIES_LIST.add("MYGICA_A5");
        SERIES_LIST.add("MYGICA_ATV300");
        SERIES_LIST.add("MYGICA_A6");
        SERIES_LIST.add("MYGICA_ATV320");
        SERIES_LIST.add("MYGICA_R4");
        // 冠捷
        SERIES_LIST.add("G2SMNT");
        // Amlogic
        SERIES_LIST.add("K200");
        SERIES_LIST.add("DS5800_V2");
        SERIES_LIST.add("AMLOGIC_K200_44");
        SERIES_LIST.add("AMLOGIC_K200");
        SERIES_LIST.add("g18ref_yunos");
        // 九州
        SERIES_LIST.add("JIUZHOUM3_7098");
        SERIES_LIST.add("JIUZHOU_A21");
        // 共进电子
        SERIES_LIST.add("GOLDWEB_WITBOX1");
        // 天仙配
        SERIES_LIST.add("PEIBOX_K7");
        SERIES_LIST.add("PEIBOX_K6");
        SERIES_LIST.add("K6");
        // MBX
        SERIES_LIST.add("FMott_tao_088");
        // 忆典
        SERIES_LIST.add("IDER_CX921");
        SERIES_LIST.add("IDER_BBA22A");
        SERIES_LIST.add("IDER_BBA22B");
        SERIES_LIST.add("IDER_BBA22Z4");
        SERIES_LIST.add("IDER_Q7");
        SERIES_LIST.add("IDER_X1");
        SERIES_LIST.add("IDER_BBA22");
        // 糖豆科技
        SERIES_LIST.add("SD_T5A");
        SERIES_LIST.add("SD_T5B");
        SERIES_LIST.add("SD_T2B");
        SERIES_LIST.add("SD_T2A");
        SERIES_LIST.add("TD_T2B");
        SERIES_LIST.add("TD_T2A");
        SERIES_LIST.add("TD_T5B");
        SERIES_LIST.add("TD_T5A");
        // 九联科技
        SERIES_LIST.add("STREAM_H910");
        // 杰科
        SERIES_LIST.add("GIEC_R10");
        SERIES_LIST.add("GIEC_X6A");
        SERIES_LIST.add("R1");
        SERIES_LIST.add("Box1");
        SERIES_LIST.add("GIEC_BOX2");
        SERIES_LIST.add("GIEC_R9");
        // 闪亮
        SERIES_LIST.add("NAIYE-Q7");
        SERIES_LIST.add("TAIMEITE-A7");
        // 银河
        SERIES_LIST.add("YinHe-Y10");
        SERIES_LIST.add("FeiYue-Hi3716CV200ES-DevBoardTV");
        // 义方教育
        SERIES_LIST.add("EY820");
        // 海尔
        SERIES_LIST.add("D58LW7110");
        SERIES_LIST.add("D39LW7110");
        SERIES_LIST.add("D50LW7100-D");
        SERIES_LIST.add("D50LW7100");
        // 快播
        SERIES_LIST.add("QVOD_R820");
        SERIES_LIST.add("QVOD_R810S");
        SERIES_LIST.add("QVOD_R810");
        // 富士康
        SERIES_LIST.add("A2");
        // 开博尔
        SERIES_LIST.add("KBE_AW31S");
        SERIES_LIST.add("KBE_AW31S_M");
        SERIES_LIST.add("KBE_C3");
        SERIES_LIST.add("KBE_T2");
        SERIES_LIST.add("KBE_A1");
        SERIES_LIST.add("KBE_F3");
        SERIES_LIST.add("KBE_K610I");
        SERIES_LIST.add("KBE_F4");
        SERIES_LIST.add("KBE_T3");
        // 腾中电子
        SERIES_LIST.add("BENEVE_R2000");
        // 亿格瑞
        SERIES_LIST.add("MYHOME_TVBOX01");
        SERIES_LIST.add("HDTIMES_TVBOX01");
        SERIES_LIST.add("TOOFANS_TVBOX01");
        SERIES_LIST.add("EGREAT_TVBOX01");
        SERIES_LIST.add("EGREAT_Z8");
        SERIES_LIST.add("EGREAT_X1");
        // 晨芯时代
        SERIES_LIST.add("CX-921");
        // TV测试
        SERIES_LIST.add("HISI3718YUNOS");
        // 小霸王
        SERIES_LIST.add("SUBOR_G10");
        SERIES_LIST.add("WAIXING_ET50");
        SERIES_LIST.add("EGREAT_GAMEBOX01");
        SERIES_LIST.add("SUBOR_A30");
        // 英菲克
        SERIES_LIST.add("INPHIC_I9");
        SERIES_LIST.add("INPHIC_I6C");
        SERIES_LIST.add("INPHIC_I6H");
        SERIES_LIST.add("INPHIC_I6");
        // 金亚
        SERIES_LIST.add("HDE12522");
        SERIES_LIST.add("JY-HDE12521");
        // 迪优美特
        SERIES_LIST.add("DIYOMATE_X12");
        SERIES_LIST.add("XMATE-3188");
        SERIES_LIST.add("DIYOMATE_X16");
        SERIES_LIST.add("DIYOMATE_A20");
        SERIES_LIST.add("DIYOMATE_X3");
        SERIES_LIST.add("DIYOMATE_X7");
        SERIES_LIST.add("DIYOMATE_K7");
        SERIES_LIST.add("DIYOMATE_K6");
        SERIES_LIST.add("DIYOMATE_X6II");
        SERIES_LIST.add("XMATE_A10S");
        SERIES_LIST.add("XMATE_A20");
        SERIES_LIST.add("DIYOMATE_X9A");
        SERIES_LIST.add("DIYOMATE_X5");
        SERIES_LIST.add("HAIR_A20");
        SERIES_LIST.add("DIYOMATE_A10");
        // 飞越
        SERIES_LIST.add("FeiYue_DevBoard_3716M");
        SERIES_LIST.add("FeiYue-Hi3716CV200ES-DevBoard001");
        // 中兴
        SERIES_LIST.add("B760E");
        SERIES_LIST.add("B760D");
        // 扬州广电
        SERIES_LIST.add("YZCNC_AML");
        // 迈科
        SERIES_LIST.add("s_box");
        // 创维
        SERIES_LIST.add("Skyworth 8A12E730");
        SERIES_LIST.add("A801");
        SERIES_LIST.add("Skyworth 8A13 K1");
        SERIES_LIST.add("SKYWORTH_I71");
        // 亚旭
        SERIES_LIST.add("YUNPCBOX_AS");
        // 数源
        SERIES_LIST.add("Soyea-X60");
        // 海美迪
        SERIES_LIST.add("HIMEDIA_Q7");
        SERIES_LIST.add("HIMEDIA_Q2II");
        SERIES_LIST.add("HIMEDIA_HD600A");
        SERIES_LIST.add("Himedia-Q5");
        SERIES_LIST.add("HIMEDIA_Q2");
        SERIES_LIST.add("HIMEDIA_H7");
        SERIES_LIST.add("h1");
        SERIES_LIST.add("HIMEDIA_HD600AII");
        SERIES_LIST.add("HIMEDIA_H3");
        SERIES_LIST.add("HIMEDIA_Q12");
    }

}
