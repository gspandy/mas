package com.letv.mas.caller.iptv.tvproxy.common.constant;

import java.util.HashMap;
import java.util.Map;

public class DomainMapping {
    private static final Map<String, String> DOMAIN_MAPPING = new HashMap<String, String>();
    private static final String ITV = "api.itv.letv.com";
    private static final String STATIC = "static.itv.letv.com";
    private static final String IPTV = "api.iptv.letv.com";
    private static final String ST = "st.data.api.itv.letv.com";
    private static final String ST2 = "st2.data.api.itv.letv.com";
    private static final String G3_CN = "g3.letv.cn";
    private static final String G3_COM = "g3.letv.com";
    private static final String I0 = "i0.letvimg.com";
    private static final String I1 = "i1.letvimg.com";
    private static final String I2 = "i2.letvimg.com";
    private static final String I3 = "i3.letvimg.com";
    private static final String LIVE_GSLB = "live.gslb.letv.com";
    private static final String LIVE_CDN = "static.letvcdn.com";// 多字幕

    static {
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + ITV, "api.itv.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + IPTV, "api.itv.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + STATIC, "static.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + ST, "st.data.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + ST2, "st2.data.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + G3_CN, "g3cn.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + G3_COM, "g3com.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + I0, "i0.img.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + I1, "i1.img.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + I2, "i2.img.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + I3, "i3.img.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + LIVE_GSLB, "live.gslb.cp21.ott.cibntv.net");
        DOMAIN_MAPPING.put(CommonConstants.CIBN + "_" + LIVE_CDN, "static.cdn.cp21.ott.cibntv.net");
    }

    public static final String changeDomainByBraodcastId(String url, Integer broadcastId, String terminalApplication) {
        if (url == null || url.length() == 0 || broadcastId == null || broadcastId < 1) {
            return url;
        }
        if (broadcastId == CommonConstants.CIBN) {// &&
                                                  // "media_cibn".equals(terminalApplication)
            if (url.contains(ST2)) {
                return url.replace(ST2, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + ST2));
            }
            if (url.contains(ST)) {
                return url.replace(ST, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + ST));
            }
            if (url.contains(G3_CN)) {
                return url.replace(G3_CN, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + G3_CN));
            }
            if (url.contains(G3_COM)) {
                return url.replace(G3_COM, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + G3_COM));
            }
            if (url.contains(ITV)) {
                return url.replace(ITV, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + ITV));
            }
            if (url.contains(IPTV)) {
                return url.replace(IPTV, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + IPTV));
            }
            if (url.contains(STATIC)) {
                return url.replace(STATIC, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + STATIC));
            }
            if (url.contains(I0)) {
                return url.replace(I0, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + I0));
            }
            if (url.contains(I1)) {
                return url.replace(I1, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + I1));
            }
            if (url.contains(I2)) {
                return url.replace(I2, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + I2));
            }
            if (url.contains(I3)) {
                return url.replace(I3, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + I3));
            }
            if (url.contains(LIVE_GSLB)) {
                return url.replace(LIVE_GSLB, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + LIVE_GSLB));
            }
            if (url.contains(LIVE_CDN)) {
                return url.replace(LIVE_CDN, DOMAIN_MAPPING.get(CommonConstants.CIBN + "_" + LIVE_CDN));
            }
        }
        return url;
    }
}
