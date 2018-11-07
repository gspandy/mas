package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

public class CmsTpConstant {

    /*
     * 请求URL
     * http://wiki.letv.cn/pages/viewpage.action?pageId=20655240
     */
    public final static String REQUEST_URL = ApplicationUtils.get(ApplicationConstants.CMS_BLOCK_DATA_ID_GET_LAN);

    /*
     * CMS版块内容类型
     */
    public final static int CMS_BLOCKCONTENT_TYPE_VIDEO = 1;// 视频
    public final static int CMS_BLOCKCONTENT_TYPE_ALBUM = 2;// 专辑
    public final static int CMS_BLOCKCONTENT_TYPE_LIVE = 3;// 直播
    public final static int CMS_BLOCKCONTENT_TYPE_SUBJECT = 5;// 专题
    public final static int CMS_BLOCKCONTENT_TYPE_TVSTATION = 6;// 乐视台
    public final static int CMS_BLOCKCONTENT_TYPE_PAGE = 7;// 定制页面
    public final static int CMS_BLOCKCONTENT_TYPE_STAR = 9;// 明星
    public final static int CMS_BLOCKCONTENT_TYPE_APP = 13;// appId
    public final static int CMS_BLOCKCONTENT_TYPE_WEBSITE_VIDEO = 10;// 全网视频
    public final static int CMS_BLOCKCONTENT_TYPE_WEBSITE_ALBUM = 11;// 全网专辑
    public final static int CMS_BLOCKCONTENT_TYPE_PUBLISHER = 12;// 发布者id
    public final static int CMS_BLOCKCONTENT_TYPE_ADD_ON = 18;// addon
    /*
     * 专题类型
     */
    public final static int CMS_SUBJECT_TYPE_ALBUM = 1;// 专辑专题
    public final static int CMS_SUBJECT_TYPE_VIDEO = 2;// 视频专题
    public final static int CMS_SUBJECT_TYPE_LIVE = 3;// 直播专题

    /*
     * 浏览器类型，4--TV版内跳，5--TV版外跳
     */
    public final static String CMS_BROWSER_TYPE = "5";// 外置
    public final static String CMS_BROWSER_TYPE_BUILTIN = "4";// 内置

    /**
     * CMS定义的专题模板数据
     */
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_ALBUM = "tv_1";
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_VIDEO = "tv_2";
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_LIVE = "tv_3";
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_MULT_PACKAGE = "tv_4";
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_TIMELINE = "tv_5";
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_HOTSPOT = "tv_6";
    public final static String CMS_SUBJECT_TEMPLATE_VALUE_TV_PRELIVE = "tv_7";

    /**
     * 根据blockId获取板块数据
     * @param wcode
     * @return
     */
    public static String GET_CMS_BLOCK_BY_ID_URL = ApplicationConstants.CMS_STATIC_API_BASE_HOST + "/blockNew/get";
    public static String GET_CMS_LE_BLOCK_BY_ID_URL = ApplicationConstants.CMS_LE_API_BASE_HOST + "/blockNew/get";

    /**
     * 根据专题id获取专题信息URL
     */
    public final static String GET_CMS_SUBJECT_BY_ID_URL = ApplicationConstants.CMS_STATIC_API_BASE_HOST
            + "/cms/tj/getTjS";
    public final static String GET_CMS_LE_SUBJECT_BY_ID_URL = ApplicationConstants.CMS_LE_API_BASE_HOST
            + "/cms/tj/getTjS";

    /**
     * 根据包id获取包信息URL
     */
    public final static String GET_CMS_PACKAGE_BY_ID_URL = ApplicationConstants.CMS_STATIC_API_BASE_HOST
            + "/cms/tj/getPackageN";
    public final static String GET_CMS_LE_PACKAGE_BY_ID_URL = ApplicationConstants.CMS_LE_API_BASE_HOST
            + "/cms/tj/getPackageN";

    /**
     * 跟据样式id获取样式信息
     */
    public final static String GET_CMS_CONTENTSTYLE_BY_ID_URL = ApplicationConstants.CMS_STATIC_API_BASE_HOST
            + "/cms/outputStyle/getById";

    /**
     * 获取cms页面信息
     */
    public final static String GET_CMS_PAGE_BY_ID_URL = ApplicationConstants.CMS_STATIC_API_BASE_HOST
            + "/page_content_{pageId}.json";
    public final static String GET_CMS_LE_PAGE_BY_ID_URL = ApplicationConstants.CMS_LE_API_BASE_HOST
            + "/page_content_{pageId}.json";
    /**
     * 获取cms页面信息
     */
    public final static String GET_CMS_COURSE_BY_DATE_GENDER_URL = ApplicationConstants.CMS_STATIC_API_BASE_HOST
            + "/cms/course/list?begin={bdate}&end={edate}&age={age}";

}
