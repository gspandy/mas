package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;

public class RecommendationTpConstant {

    /*
     * 请求URL
     * http://doc.letv.cn/do/view/Main/RecInterface
     * http://wiki.letv.cn/pages/viewpage.action?pageId=20655240
     */
    public final static String REQUEST_URL_PARAM_PT = "0004";// 请求参数pt

    /*
     * CMS版块内容类型
     */
    public final static int CMS_BLOCKCONTENT_TYPE_VIDEO = 1;// 视频
    public final static int CMS_BLOCKCONTENT_TYPE_ALBUM = 2;// 专辑
    public final static int CMS_BLOCKCONTENT_TYPE_LIVE = 3;// 直播
    public final static int CMS_BLOCKCONTENT_TYPE_SUBJECT = 5;// 专题
    public final static int CMS_BLOCKCONTENT_TYPE_TVSTATION = 6;// 乐视台

    /*
     * 推荐数据类型
     */
    public final static int RECOMMENDATION_DATA_TYPE_ALBUM = 1;// 专辑
    public final static int RECOMMENDATION_DATA_TYPE_VIDEO = 0;// 视频

    /*
     * 推荐专辑是否已完结
     */
    public final static int RECOMMENDATION_ALBUM_END_YES = 1;// 已完结
    public final static int RECOMMENDATION_ALBUM_END_NO = 0;// 未完结

    /**
     * 退出播放时，用于缓存推荐数据的key前缀，用于和categoryId拼接
     */
    public final static String RECOMMENDATION_PREFIX = "RECO_0001_";

    /**
     * 推荐接口中，TV端用到的部分页面区域
     */
    public final static String RECOMMENDATION_AREA_RELATED_REC = "rec_0001"; // 相关推荐
    public final static String RECOMMENDATION_AREA_TV_MUSIC = "rec_0020"; // TV音乐台推荐
    public final static String RECOMMENDATION_AREA_PLAY_END = "rec_0017"; // 播放结束的推荐，推荐接口参数
    public final static String RECOMMENDATION_AREA_ALBUM_DP_REC = "rec_0010"; // 专辑详情页REC相关推荐
    public final static String RECOMMENDATION_AREA_ALBUM_DP_VRS = "vrs_0010"; // 专辑详情页VRS相关推荐

    /**
     * interface report and exposure type values
     */
    public static final String RECOMMEND_ACTION = "17";// report action type
    public static final String RECOMMEND_EXPOSURE = "19";// exposure type

    /**
     * 儿童参你喜欢页面id
     */
    public static final String REC_MAYBELIKE_CHILD_PAGEID = "page_cms1003271356";

    /**
     * 获取推荐URL
     * @return
     */
    public static String getRecURL(CommonParam commonParam) {
        if (commonParam != null
                && TerminalCommonConstant.TERMINAL_APPLICATION_LE
                        .equalsIgnoreCase(commonParam.getTerminalApplication())) {
            return ApplicationConstants.REC_BASE_HOST + "/tv_le/?";
        } else {
            if (null != commonParam && "test".equals(commonParam.getP_workbench())) {
                return ApplicationConstants.REC_BASE_HOST + "/tvTest/?";
            } else {
                return ApplicationConstants.REC_BASE_HOST + "/tv/?";
            }
        }
    }

    /**
     * 全网推荐接口(推荐内网+外网数据)
     * @param langcode
     * @return
     */
    public static String getWebRecURL(String langcode) {
        return ApplicationUtils.get(ApplicationConstants.RECOMMEND_WEB_URL);
    }

    public static String getLevidiRecURL() {
        return ApplicationUtils.get(ApplicationConstants.LEVIDI_RECOMMENDATION_BASEURL) + "/oversea_sarrs?";
    }

    /**
     * TV首页推荐以及频道首页推荐以及猜你喜欢调用的推荐URL
     */
    public final static String[] REC_URL = new String[] { "pt=0004&random=1418188520666&pageid=page_0006",
            "pt=0004&random=1418188527264&pageid=page_0001", "pt=0004&random=1418188531447&pageid=page_0003",
            "pt=0004&random=1418188531943&pageid=page_0004", "pt=0004&random=1418188532010&pageid=page_0009",
            "pt=0004&random=1418188532412&pageid=page_0008", "pt=0004&random=1418188532548&pageid=page_0014",
            "pt=0004&random=1418188532636&pageid=page_0013", "pt=0004&random=1418188532878&pageid=page_0012",
            "pt=0004&random=1418188533150&pageid=page_0010", "pt=0004&random=1418188541854&pageid=page_0017",
            "pt=0004&random=1418188541903&pageid=page_0015", "pt=0004&random=1418188542263&pageid=page_0011",
            "pt=0004&random=1418188545889&pageid=page_0005", "pt=0004&random=1418188546021&pageid=page_0016",
            "pt=0004&random=1418188546120&pageid=page_0002", "pt=0004&random=1418188546500&pageid=page_0022",
            "pt=0004&random=1418188546856&pageid=page_0023", "pt=0004&random=1418188532412&pageid=page_0007",
            "pt=0004&random=1418188532412&pageid=page_0024",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=2&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=1&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=5&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=11&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=3&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=9&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=16&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=34&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=22&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=20&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=14&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=23&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=4&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0002&num=20&cid=0&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0002&num=20&lc=C80E7725B30D_42149908" };

    /**
     * TV首页推荐以及频道首页推荐以及猜你喜欢调用的推荐URL
     */
    public final static String[] REC_URL_HK = new String[] { "pt=0004&random=1418188546021&pageid=page_0001",
            "pt=0004&random=1418188546021&pageid=page_0002", "pt=0004&random=1418188546021&pageid=page_0003",
            "pt=0004&random=1418188546021&pageid=page_0004", "pt=0004&random=1418188546021&pageid=page_0005",
            "pt=0004&random=1418188546021&pageid=page_0006", "pt=0004&random=1418188546021&pageid=page_0007",
            "pt=0004&random=1418188546021&pageid=page_0008", "pt=0004&random=1418188546021&pageid=page_0009",
            "pt=0004&random=1418188546021&pageid=page_0010", "pt=0004&random=1418188546021&pageid=page_0011",
            "pt=0004&random=1418188546021&pageid=page_0012", "pt=0004&random=1418188546021&pageid=page_0013",
            "pt=0004&random=1418188546021&pageid=page_0014", "pt=0004&random=1418188546021&pageid=page_0015",
            "pt=0004&random=1418188546021&pageid=page_0016",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=2&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=1&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=5&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=11&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=3&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=9&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=16&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=34&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=22&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=20&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=14&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=23&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0009&num=45&cid=4&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0002&num=20&cid=0&lc=C80E7725B30D_42149908",
            "uid=42149908&pt=0004&area=rec_0002&num=20&lc=C80E7725B30D_42149908" };

}
