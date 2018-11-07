package com.letv.mas.caller.iptv.tvproxy.apicommon.constants;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsTpConstant;

import java.util.HashMap;
import java.util.Map;

public class SubjectConstant {

    /*
     * TV版内跳转专题类型
     */
    public final static int SUBJECT_TYPE_VIDEO = 0;// 视频专题
    public final static int SUBJECT_TYPE_ALBUM = 1;// 专辑专题
    public final static int SUBJECT_TYPE_LIVE = 2;// 直播专题聚合页
    public final static int SUBJECT_TYPE_LIVE_TOPIC_DETAIL = 3;// 直播专题详情页
    public final static int SUBJECT_TYPE_MULT_PACKAGE = 4; // 多专辑专题包
    public final static int SUBJECT_TYPE_TIMELINE = 5; // 时间轴专题
    public final static int SUBJECT_TYPE_HOTSPOT = 6; // 热点(多视频包)专题
    public final static int SUBJECT_TYPE_PRELIVE = 7; // 超前影院专题

    /**
     * 专题模板（CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_*）到专题跳转类型（SUBJECT_TYPE_*
     * ）的映射，
     * 目前仅针对多专辑专题包和时间轴
     */
    private final static Map<String, Integer> SUBJECT_TEMPLATE_TYPE_MAP = new HashMap<String, Integer>();

    static {
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_ALBUM, SUBJECT_TYPE_ALBUM);
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_VIDEO, SUBJECT_TYPE_VIDEO);
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_LIVE, SUBJECT_TYPE_LIVE);
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_MULT_PACKAGE,
                SUBJECT_TYPE_MULT_PACKAGE);
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_TIMELINE, SUBJECT_TYPE_TIMELINE);
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_HOTSPOT, SUBJECT_TYPE_HOTSPOT);
        SUBJECT_TEMPLATE_TYPE_MAP.put(CmsTpConstant.CMS_SUBJECT_TEMPLATE_VALUE_TV_PRELIVE, SUBJECT_TYPE_PRELIVE);
    }

    /**
     * 根据第三方接口返回的专题模板信息，返回服务端定义的专题跳转类型
     * @param subjectTemplate
     * @return
     */
    public static Integer getSubjectTypeFromTemplate(String subjectTemplate) {
        return SUBJECT_TEMPLATE_TYPE_MAP.get(subjectTemplate);
    }

    /**
     * 判断某种类型的专题包是否需要返回当前[专辑、视频或者直播包类型]的数据
     * @param subjectType
     * @param ptype
     * @return
     */
    public static boolean isContainPackageTypeBySubjectType(Integer subjectType, Integer ptype) {
        if (subjectType == null || ptype == null) {// 如果这两个字段为空，则返回false
            return false;
        }
        switch (subjectType) {// 专题类型
        case SUBJECT_TYPE_VIDEO:
            if (ptype == 2) {// 视频专题，取视频包数据
                return true;
            }
            break;
        case SUBJECT_TYPE_ALBUM:
            if (ptype == 1) {// 专辑专题，取专辑包数据
                return true;
            }
            break;
        case SUBJECT_TYPE_LIVE:
            if (ptype == 3) {// 直播专题，取直播包数据
                return true;
            }
            break;
        case SUBJECT_TYPE_MULT_PACKAGE:
            if (ptype == 1) {// 多专辑包专题，取专辑包数据
                return true;
            }
            break;
        case SUBJECT_TYPE_TIMELINE:
            if (ptype == 2) {// 时间轴专题，取视频包数据
                return true;
            }
            break;
        case SUBJECT_TYPE_HOTSPOT:
            if (ptype == 2) {// 热点专题，取视频包数据
                return true;
            }
            break;
        default:
            break;
        }
        return false;
    }
}
