package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 影片所在站点信息
 */
public class WebsiteTpResponse {

    private List<WebsiteDto> data;

    public WebsiteTpResponse(List<WebsiteDto> data) {
        this.data = data;
    }

    public List<WebsiteDto> getData() {
        return this.data;
    }

    public void setData(List<WebsiteDto> data) {
        this.data = data;
    }

    public static class WebsiteDto {
        private String aid;// leso的专辑id
        private Integer dataType;// 数据类型 1专辑、2视频、3明星
        private String site;// 站点拼音
        @Deprecated
        private String siteName;// 站点名称
        private String isTv;// 是否tv版
        @Deprecated
        private String icon;// logo
        private String vrsAid;// 如果vrs里有此专辑、vrsAid
        private String episodeStatus;
        private String episodes;// 总集数
        private String nowEpisodes;// 当前此站点更新到集数
        @Deprecated
        private String defaultIcon = WEB_SITE.QITA.icon;
        private String category;
        private String intro;
        private String isMobile;
        private String isPc;
        private String orderNum;
        private String playStreams;
        private String tophot;
        private String unIds;
        private String videoDeadlinkUrls;

        public String getAid() {
            return this.aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public Integer getDataType() {
            return this.dataType;
        }

        public void setDataType(Integer dataType) {
            this.dataType = dataType;
        }

        public String getSite() {
            return this.site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public String getSiteName() {
            return this.siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getIsTv() {
            return this.isTv;
        }

        public void setIsTv(String isTv) {
            this.isTv = isTv;
        }

        public String getVrsAid() {
            return this.vrsAid;
        }

        public void setVrsAid(String vrsAid) {
            this.vrsAid = vrsAid;
        }

        public String getEpisodeStatus() {
            return this.episodeStatus;
        }

        public void setEpisodeStatus(String episodeStatus) {
            this.episodeStatus = episodeStatus;
        }

        public String getEpisodes() {
            return this.episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

        public String getNowEpisodes() {
            return this.nowEpisodes;
        }

        public void setNowEpisodes(String nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDefaultIcon() {
            return this.defaultIcon;
        }

        public void setDefaultIcon(String defaultIcon) {
            this.defaultIcon = defaultIcon;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getIntro() {
            return this.intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getIsMobile() {
            return this.isMobile;
        }

        public void setIsMobile(String isMobile) {
            this.isMobile = isMobile;
        }

        public String getIsPc() {
            return this.isPc;
        }

        public void setIsPc(String isPc) {
            this.isPc = isPc;
        }

        public String getOrderNum() {
            return this.orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getPlayStreams() {
            return this.playStreams;
        }

        public void setPlayStreams(String playStreams) {
            this.playStreams = playStreams;
        }

        public String getTophot() {
            return this.tophot;
        }

        public void setTophot(String tophot) {
            this.tophot = tophot;
        }

        public String getUnIds() {
            return this.unIds;
        }

        public void setUnIds(String unIds) {
            this.unIds = unIds;
        }

        public String getVideoDeadlinkUrls() {
            return this.videoDeadlinkUrls;
        }

        public void setVideoDeadlinkUrls(String videoDeadlinkUrls) {
            this.videoDeadlinkUrls = videoDeadlinkUrls;
        }

    }

    public enum WEB_SITE {
        LETV("letv", "乐视网", "http://i0.letvimg.com/img/201311/06/logo/letv.png"),
        SOHU("sohu", "搜狐", "http://i3.letvimg.com/img/201311/06/logo/sohu.png"),
        QIYI("qiyi", "奇艺", "http://i3.letvimg.com/img/201311/06/logo/iqiyi.png"),
        IQIYI("iqiyi", "爱奇艺", "http://i3.letvimg.com/img/201311/06/logo/iqiyi.png"),
        XUNLEI("xunlei", "迅雷", "http://i2.letvimg.com/img/201311/06/logo/xunlei.png"),
        YOUKU("youku", "优酷", "http://i2.letvimg.com/img/201311/06/logo/youku.png"),
        TUDOU("tudou", "土豆", "http://i3.letvimg.com/img/201311/06/logo/tudou.png"),
        PPTV("pptv", "PPTV", "http://i3.letvimg.com/img/201311/06/logo/pptv.png"),
        FUNSHION("funshion", "风行网", "http://i0.letvimg.com/img/201311/06/logo/fengxing.png"),
        KU6("ku6", "酷6网", "http://i3.letvimg.com/img/201311/06/logo/ku6.png"),
        M1905("m1905", "电影网", "http://i1.letvimg.com/img/201311/06/logo/m1905.png"),
        QQ("qq", "腾讯视频", "http://i0.letvimg.com/img/201311/06/logo/qq.png"),
        W56("56", "56", "http://i1.letvimg.com/img/201311/06/logo/56.png"),
        SINA("sina", "新浪视频", "http://i2.letvimg.com/img/201311/06/logo/sina.png"),
        IFENG("ifeng", "凤凰网", "http://i0.letvimg.com/img/201311/06/logo/fenghuang.png"),
        KUMI("kumi", "酷米", "http://i2.letvimg.com/img/201311/06/logo/kumi.png"),
        BEVA("beva", "贝瓦", "http://i0.letvimg.com/img/201311/06/logo/beiwai.png"),
        W61("61", "淘米网", "http://i2.letvimg.com/img/201311/06/logo/taomi.png"),
        YINYUETAI("yinyuetai", "音悦台", "http://i1.letvimg.com/img/201311/06/logo/yingyuetai.png"),
        CNTV("cntv", "CNTV", "http://i1.letvimg.com/img/201311/06/logo/cntv.png"),
        PPS("pps", "PPS", "http://i0.letvimg.com/img/201311/06/logo/pps.png"),
        NET("nets", "网络", "http://i1.letvimg.com/img/201311/06/logo/wangluo.png"),
        IMGO("imgo", "芒果TV", "http://i1.letvimg.com/client/201404/25/g/g/TV.png"),
        ZJSTV("zjstv", "蓝天下", "http://i2.letvimg.com/img/201407/22/LTX.png"),
        BAOFENG("baofeng", "暴风", "http://i0.letvimg.com/img/201407/23/LESO/BF200.png"),
        CIBN("cibn", "CIBN高清影视",
                "http://i3.letvimg.com/lc06_iscms/201612/29/15/53/894bcf6a3d964e33923f514048aca6e8.png"),
        WASU("wasutv", "华数", "http://i0.letvimg.com/lc07_iscms/201701/22/16/56/55bd833893b04769afc6138eba20f81b.png"),
        QITA("qita", "其他", "http://i1.letvimg.com/img/201311/06/logo/default.png");
        private static final Map<String, WEB_SITE> map = new HashMap<String, WEB_SITE>();
        private final String pinYin;
        private final String name;
        private String icon;

        private WEB_SITE(String pinYin, String name_cn, String _icon) {
            this.pinYin = pinYin;
            this.name = name_cn;
            this.icon = _icon;
        }

        public String getPinYin() {
            return this.pinYin;
        }

        public String getName() {
            return this.name;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        static {
            map.put(LETV.pinYin, LETV);
            map.put(SOHU.pinYin, SOHU);
            map.put(QIYI.pinYin, QIYI);
            map.put(IQIYI.pinYin, IQIYI);
            map.put(XUNLEI.pinYin, XUNLEI);
            map.put(YOUKU.pinYin, YOUKU);
            map.put(TUDOU.pinYin, TUDOU);
            map.put(PPTV.pinYin, PPTV);
            map.put(FUNSHION.pinYin, FUNSHION);
            map.put(KU6.pinYin, KU6);
            map.put(M1905.pinYin, M1905);
            map.put(QQ.pinYin, QQ);
            map.put(W56.pinYin, W56);
            map.put(SINA.pinYin, SINA);
            map.put(IFENG.pinYin, IFENG);
            map.put(KUMI.pinYin, KUMI);
            map.put(BEVA.pinYin, BEVA);
            map.put(W61.pinYin, W61);
            map.put(YINYUETAI.pinYin, YINYUETAI);
            map.put(CNTV.pinYin, CNTV);
            map.put(WASU.pinYin, WASU);
            map.put(PPS.pinYin, PPS);
            map.put(NET.pinYin, NET);
            map.put(IMGO.pinYin, IMGO);
            map.put(ZJSTV.pinYin, ZJSTV);
            map.put(BAOFENG.pinYin, BAOFENG);
            map.put(QITA.pinYin, QITA);
        }

        public static WEB_SITE getCNNAME(String pinYin) {
            WEB_SITE webSite = map.get(pinYin);
            if (webSite != null) {
                return webSite;
            }
            if (LETV.pinYin.equals(pinYin)) {
                return LETV;
            } else if (SOHU.pinYin.equals(pinYin)) {
                return SOHU;
            } else if (QIYI.pinYin.equals(pinYin)) {
                return QIYI;
            } else if (IQIYI.pinYin.equals(pinYin)) {
                return IQIYI;
            } else if (XUNLEI.pinYin.equals(pinYin)) {
                return XUNLEI;
            } else if (YOUKU.pinYin.equals(pinYin)) {
                return YOUKU;
            } else if (TUDOU.pinYin.equals(pinYin)) {
                return TUDOU;
            } else if (PPTV.pinYin.equals(pinYin)) {
                return PPTV;
            } else if (FUNSHION.pinYin.equals(pinYin)) {
                return FUNSHION;
            } else if (KU6.pinYin.equals(pinYin)) {
                return KU6;
            } else if (M1905.pinYin.equals(pinYin)) {
                return M1905;
            } else if (QQ.pinYin.equals(pinYin)) {
                return QQ;
            } else if (W56.pinYin.equals(pinYin)) {
                return W56;
            } else if (SINA.pinYin.equals(pinYin)) {
                return SINA;
            } else if (IFENG.pinYin.equals(pinYin)) {
                return IFENG;
            } else if (KUMI.pinYin.equals(pinYin)) {
                return KUMI;
            } else if (BEVA.pinYin.equals(pinYin)) {
                return BEVA;
            } else if (W61.pinYin.equals(pinYin)) {
                return W61;
            } else if (YINYUETAI.pinYin.equals(pinYin)) {
                return YINYUETAI;
            } else if (CNTV.pinYin.equals(pinYin)) {
                return CNTV;
            } else if (WASU.pinYin.equals(pinYin)) {
                return WASU;
            } else if (PPS.pinYin.equals(pinYin)) {
                return PPS;
            } else if (IMGO.pinYin.equals(pinYin)) {
                return IMGO;
            } else if (ZJSTV.pinYin.equals(pinYin)) {
                return ZJSTV;
            } else if (BAOFENG.pinYin.equals(pinYin)) {
                return BAOFENG;
            } else if (CIBN.pinYin.equals(pinYin)) {
                return CIBN;
            }
            if (NET.pinYin.equals(pinYin)) {
                return NET;
            } else {
                return QITA;
            }

        }
    }

}
