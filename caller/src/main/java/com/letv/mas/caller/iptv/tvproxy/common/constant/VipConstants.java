package com.letv.mas.caller.iptv.tvproxy.common.constant;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.ConfigOperationUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class VipConstants {

    /**
     * 机卡绑定TV版参数deviceType，设备类型，电视传固定值1，手机传固定值2，盒子固定传3
     */
    // tv terminal type
    public static final int DEVICE_BIND_DEVICE_TYPE_TV = 1;
    // mobileterminal type
    public static final int DEVICE_BIND_DEVICE_TYPE_MOBILE = 2;
    // box terminal type
    public static final int DEVICE_BIND_DEVICE_TYPE_LETV_BOX = 3;

    //public static final String LEPAY_VIP_GETTIME_URL = ApplicationConstants.LEPAY_BASE_HOST+"/open_api/vip/m/get_time_new.json";

    /**
     * 机卡绑定需求第三方接口sign计算key
     */
    public static final String DEVICE_BIND_SIGN_KEY = "@*#&*&^&%##oihoi^&%*))@#(*$@$_)+|}{~?><:";

    /**
     * 美国版，Boss V2接口公共URI
     */
    /*public static final String BOSS_YUANXIAN_V2_DEVICE_BIND_BASE_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/v2/deviceBind.ldo?";*/

    /**
     * get user device bind info url
     */
    /*public final static String VIP_DEVICE_BIND_INFO_GET_URL = ApplicationConstants.BOSS_YUANXIAN_BASE_HOST
            + "/letv/deviceBind.ldo?";*/

    /**
     * api.zhifu接口通用sign key
     */
    public final static String BOSS_API_ZHIFU_SIGN_KEY = "16ec384124bcdfee2a90ecc9267373bc";

    /**
     * boss V2 机卡绑定业务--机卡状态，1-已上报，2-已同步（可领取），3-领取中，6已领取，9已退货
     */
    public static final int BOSS_DEVICE_BIND_V2_DEVICE_STATUS_2 = 2;
    public static final int BOSS_DEVICE_BIND_V2_DEVICE_STATUS_6 = 6;

    public static String GET_PACKAGE_INFO_BY_ID() {
        String baseUrl = ConfigOperationUtil.get(ApplicationConstants.BOSS_YUANXIAN_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/letv/vip2.ldo";
        }
        return null;
    }

    public static String getLepayVipGettimeUrl(){
        String baseUrl = ConfigOperationUtil.get(ApplicationConstants.LEPAY_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/open_api/vip/m/get_time_new.json";
        }
        return null;
    }


    public static String getBossYuanxianV2DeviceBindBaseUrl(){
        String baseUrl = ConfigOperationUtil.get(ApplicationConstants.BOSS_YUANXIAN_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/letv/v2/deviceBind.ldo?";
        }
        return null;
    }

    public static String getVipDeviceBindInfoGetUrl(){
        String baseUrl = ConfigOperationUtil.get(ApplicationConstants.BOSS_YUANXIAN_BASEURL);
        if(StringUtil.isNotBlank(baseUrl)){
            return baseUrl + "/letv/deviceBind.ldo?";
        }
        return null;
    }

    /**
     * 大陆会员类型：<p>
     * 影视会员 301<p>
     * 体育会员 302<p>
     * 音乐会员 303
     */
    public enum Type_Group_CN {
        TV(301), // 影视会员
        SPORT(302), // 体育会员
        MUSIC(303); // 音乐会员
        private Integer code;

        private Type_Group_CN(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static Type_Group_CN findType(Integer code) {
            for (Type_Group_CN typeGroup : Type_Group_CN.values()) {
                if (typeGroup.code.equals(code)) {
                    return typeGroup;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * Boss对应终端类型TerminalType<p>
     * 超级电视 141007<p>
     * 超级手机 141004
     */
    public enum BossTerminalType {
        TV(141007), // 超级电视
        SUPER_MOBILE(141004); // 超级手机
        private Integer code;

        private BossTerminalType(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static BossTerminalType findType(Integer code) {
            for (BossTerminalType terminalType : BossTerminalType.values()) {
                if (terminalType.code.equals(code)) {
                    return terminalType;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * bussinessId和signKey的对应值
     */
    public enum BussinessIdAndSignKey {
        LECOM(10013, "SnAeArgNqnxZ4CPLv4LWYBmHnhDe1scc"), // TV le
        LEVIDI(10019, "mbac0054676d9ebs178d22273f7c38d0"), // levidi
        CIBN(10031, "3effec38e0b12860b9113abcc60da273"), // cibn
        LEPAY_CIBN(4, "wq0euzrccodqjcjaihxngx0cwhdr49q9"), // lepay-cibn
        DEFAULT(10031, "3effec38e0b12860b9113abcc60da273");
        private Integer bussinessId;
        private String signKey;

        private BussinessIdAndSignKey(Integer bussinessId, String signKey) {
            this.bussinessId = bussinessId;
            this.signKey = signKey;
        }

        public static BussinessIdAndSignKey getBussinessIdAndSignKey(CommonParam commonParam) {
            if (commonParam != null) {
                if (TerminalCommonConstant.TERMINAL_APPLICATION_LE.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    return LECOM;
                } else if (TerminalCommonConstant.TERMINAL_APPLICATION_LEVIDI.equalsIgnoreCase(commonParam
                        .getTerminalApplication())) {
                    return LEVIDI;
                } else if (commonParam.getBroadcastId() != null
                        && commonParam.getBroadcastId().intValue() == CommonConstants.CIBN) {
                    return CIBN;
                }
            }
            return DEFAULT;
        }

        public String getSignKey() {
            return signKey;
        }

        public void setSignKey(String signKey) {
            this.signKey = signKey;
        }

        public Integer getBussinessId() {
            return bussinessId;
        }

        public void setBussinessId(Integer bussinessId) {
            this.bussinessId = bussinessId;
        }
    }

    /**
     * 大陆影视会员对应productId值
     */
    public enum PRODUCT_ID_CN {
        SUPERTV(9), // 超级影视会员
        MOBILE(1); // 乐次元
        private Integer code;

        private PRODUCT_ID_CN(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static PRODUCT_ID_CN findType(Integer code) {
            for (PRODUCT_ID_CN typeGroup : PRODUCT_ID_CN.values()) {
                if (typeGroup.code.equals(code)) {
                    return typeGroup;
                }
            }
            return null;
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * 商户VIP映射关系
     * 1-体验会员，2-乐次元影视会员，3-超级影视会员，4-超级家庭会员，5-国广CIBN会员，6-华数会员，7-芒果会员，8-腾讯会员
     */
    public enum LEPAY_VIP_TYPE {
        DEMO(1, ""),
        LE_MOVIE(2, ""),
        LE_TV(3, "511703141887278862336"),
        LE_HOME(4, "511803124110985523245056"),
        LE_CIBN(5, "511703141886101704704"),
        LE_HUASU(6, "511703141884168916992"),
        LE_MANGO(7, "511708211815898872758272"),
        LE_TENCENT(8, "511803124111241400426496"),

        //XXX_TT => 测试商户编号
        DEMO_(1, ""),
        LE_MOVIE_TT(2, ""),
        LE_TV_TT(3, "511703141887278862336"),
        LE_HOME_TT(4, "511801083399039277731840"),
        LE_CIBN_TT(5, "511487568308537788369744035181"),
        LE_HUASU_TT(6, "511487569155542382567151195224"),
        LE_MANGO_TT(7, "511708231837041023979520"),
        LE_TENCENT_TT(8, "511801313661615726465024");
        private Integer type;
        private String vendor;

        private LEPAY_VIP_TYPE(Integer type, String vendor) {
            this.type = type;
            this.vendor = vendor;
        }

        public static LEPAY_VIP_TYPE getVipByType(boolean test, Integer type) {
            for (LEPAY_VIP_TYPE vipType : LEPAY_VIP_TYPE.values()) {
                if (test) {
                    if ((vipType.name().lastIndexOf("_TT") > 0) && vipType.getType().intValue() == type.intValue()) {
                        return vipType;
                    }
                } else {
                    if ((vipType.name().lastIndexOf("_TT") == -1) && vipType.getType().intValue() == type.intValue()) {
                        return vipType;
                    }
                }
            }
            return DEMO;
        }

        public static LEPAY_VIP_TYPE getVipByVendor(boolean test, String vendor) {
            for (LEPAY_VIP_TYPE vipType : LEPAY_VIP_TYPE.values()) {
                if (test) {
                    if ((vipType.name().lastIndexOf("_TT") > 0) && vipType.getVendor().equals(vendor)) {
                        return vipType;
                    }
                } else {
                    if ((vipType.name().lastIndexOf("_TT") == -1) && vipType.getVendor().equals(vendor)) {
                        return vipType;
                    }
                }
            }
            return DEMO;
        }

        public static String getVipProducts(boolean test, String vendor) {
            StringBuilder sb = new StringBuilder();
            for (LEPAY_VIP_TYPE vipType : LEPAY_VIP_TYPE.values()) {
                if (test) {
                    if ((vipType.name().lastIndexOf("_TT") > 0)) {
                        if (null != vendor && !vipType.getVendor().equals(vendor)
                                || StringUtil.isBlank(vipType.getVendor())) {
                            continue;
                        }
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        if (vipType.name().indexOf("LE_MOVIE") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        } else if (vipType.name().indexOf("LE_TV") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.SUPERTV.getCode());
                        } else {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        }
                    }
                } else {
                    if ((vipType.name().lastIndexOf("_TT") == -1)) {
                        if (null != vendor && !vipType.getVendor().equals(vendor)
                                || StringUtil.isBlank(vipType.getVendor())) {
                            continue;
                        }
                        if (sb.length() > 0) {
                            sb.append(",");
                        }
                        if (vipType.name().indexOf("LE_MOVIE") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        } else if (vipType.name().indexOf("LE_TV") != -1) {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.SUPERTV.getCode());
                        } else {
                            sb.append(vipType.getVendor()).append(":").append(PRODUCT_ID_CN.MOBILE.getCode());
                        }
                    }
                }
            }
            return sb.toString();
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }
    }

    /**
     * Boss对应国家字典
     */
    public enum Country {
        US(1),
        CN(86),
        HK(852),
        IN(91),
        RU(7);
        private Integer code;

        private Country(Integer code) {
            this.code = code;
        }

        /*
         * find type by code
         */
        public static Country findType(Integer code) {
            for (Country country : Country.values()) {
                if (country.code.equals(code)) {
                    return country;
                }
            }
            return null;
        }

        /*
         * find type by code
         */
        public static Country findType(String wcode) {
            if (LocaleConstant.Wcode.US.equalsIgnoreCase(wcode)) {
                return US;
            } else if (LocaleConstant.Wcode.CN.equalsIgnoreCase(wcode)) {
                return CN;
            } else if (LocaleConstant.Wcode.HK.equalsIgnoreCase(wcode)) {
                return HK;
            } else if (LocaleConstant.Wcode.IN.equalsIgnoreCase(wcode)) {
                return IN;
            } else if (LocaleConstant.Wcode.RU.equalsIgnoreCase(wcode)) {
                return RU;
            }
            return CN; // 默认返回cn
        }

        public Integer getCode() {
            return this.code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code + "";
        }
    }

    /**
     * productId和vipId映射map
     */
    public static final Map<Integer, Integer> PRODUCT_ID_AND_VIP_ID_MAP = new HashMap<Integer, Integer>();

    /**
     * VIP账户名称集合
     */
    public static final String VIP_ACCOUNT_TYPE_NAMES[] = {"VIP_ACCOUNT_TYPE_DEMO",
            "VIP_ACCOUNT_TYPE_MOVIE",
            "VIP_ACCOUNT_TYPE_TV",
            "VIP_ACCOUNT_TYPE_HOME",
            "VIP_ACCOUNT_TYPE_CIBN",
            "VIP_ACCOUNT_TYPE_HUASU",
            "VIP_ACCOUNT_TYPE_MANGO",
            "VIP_ACCOUNT_TYPE_TENCENT"};

    static {

        PRODUCT_ID_AND_VIP_ID_MAP.put(PRODUCT_ID_CN.MOBILE.getCode(), 2);
        PRODUCT_ID_AND_VIP_ID_MAP.put(PRODUCT_ID_CN.SUPERTV.getCode(), 3);
    }

}