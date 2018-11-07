package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CacheContentConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.springframework.util.CollectionUtils;


/**
 * 对VipTpConstant中定义的第三方相关数据提供工具类方法的封装
 * @author yikun
 */
public class VipTpConstantUtils {

    /**
     * 设备品牌名称-编号映射，key--设备类型字符串，value--设备类型代码
     */
    private static final Map<String, Integer> terminalBrandMap = new HashMap<String, Integer>();

    /**
     * 乐视自有设备系列-编号映射，key--设备系列字符串，value--设备系列代码
     */
    private static final Map<String, Integer> terminalSeriesMap = new HashMap<String, Integer>();

    /**
     * 直播鉴权中，鉴定为已购买过直播的状态码列表
     */
    private static final List<String> checkLiveBoughtCodeList = new ArrayList<String>();

    /**
     * 适用于TV的订单类型，目前包括单点影片，套餐包，免费会员和直播
     */
    private final static List<String> orderTypesApplyToTv = new ArrayList<String>();

    /**
     * 支付渠道中，特殊支付渠道国广版到自由版的映射
     */
    private final static Map<String, String> paymentChannelCibnToLetvMap = new HashMap<String, String>();

    /**
     * 所有的支付渠道集合
     */
    private final static Set<Integer> paymentChannelSet = new HashSet<Integer>();

    /**
     * 所有的设备类型集合
     */
    private final static Set<Integer> deviceTypeSet = new HashSet<Integer>();

    static {
        // 以下key字段以乐视网TV版终端管理系统中“品牌-型号”数据为准，不完全包含
        terminalBrandMap.put("hisense", VipTpConstant.SUB_ORDER_FROM_0);// 海信
        terminalBrandMap.put("haier", VipTpConstant.SUB_ORDER_FROM_1);// 海尔
        terminalBrandMap.put("skyworth", VipTpConstant.SUB_ORDER_FROM_2);// 创维
        terminalBrandMap.put("tcl", VipTpConstant.ORDER_TYPE_3);// TCL
        terminalBrandMap.put("konka", VipTpConstant.SUB_ORDER_FROM_4);// 康佳
        terminalBrandMap.put("tongfang", VipTpConstant.SUB_ORDER_FROM_5);// 同方
        terminalBrandMap.put("changhong", VipTpConstant.SUB_ORDER_FROM_8);// 长虹
        terminalBrandMap.put("panda", VipTpConstant.SUB_ORDER_FROM_9);// 熊猫
        terminalBrandMap.put("xiaomi", VipTpConstant.SUB_ORDER_FROM_10);// 小米
        terminalBrandMap.put("tpv", VipTpConstant.SUB_ORDER_FROM_11);// 冠捷
        terminalBrandMap.put("wasu", VipTpConstant.SUB_ORDER_FROM_101);// 华数
        terminalBrandMap.put("duole", VipTpConstant.SUB_ORDER_FROM_102);// 多乐
        terminalBrandMap.put("vst", VipTpConstant.SUB_ORDER_FROM_103);// VST
        terminalBrandMap.put("wangshi", VipTpConstant.SUB_ORDER_FROM_104);// 网视
        terminalBrandMap.put("dangbei", VipTpConstant.SUB_ORDER_FROM_105);// 当贝
        terminalBrandMap.put("qipo", VipTpConstant.SUB_ORDER_FROM_106);// 奇珀
        terminalBrandMap.put("shafa", VipTpConstant.SUB_ORDER_FROM_107);// 沙发
        terminalBrandMap.put("aijia", VipTpConstant.SUB_ORDER_FROM_108);// 爱家

        terminalSeriesMap.put("msm8960", VipTpConstant.SUB_ORDER_FROM_X60);// X60
        terminalSeriesMap.put("letvx60", VipTpConstant.SUB_ORDER_FROM_X60);// X60
        terminalSeriesMap.put("Android TV on MStar Amber3 S50".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S50);// S50
        terminalSeriesMap.put("Android+TV+on+MStar+Amber3+S50".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S50);// S50，代码兼容设置
        terminalSeriesMap.put("Android TV on MStar Amber3 S40".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S40);// S40-UI2
        terminalSeriesMap.put("Android+TV+on+MStar+Amber3+S40".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S40); // S40-UI2，代码兼容设置
        terminalSeriesMap.put("epg", VipTpConstant.SUB_ORDER_FROM_S10);// S10
        terminalSeriesMap.put("Hi3716C".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_T1);// ST1_2.2
        terminalSeriesMap.put("AMLOGIC8726MX".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_C1);// C1
        terminalSeriesMap.put("AMLOGIC8726MX_UI_2".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_C1);// LETV-C1
        terminalSeriesMap.put("AMLOGIC8726MX_C1S".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_C1S);// C1S
        terminalSeriesMap.put("AMLOGIC8726MX_C1S_UI_2".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_C1S);// C1S
        terminalSeriesMap.put("AMLOGIC8726MX_T1S_UI20".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_T1S);// T1S_for_UI20
        terminalSeriesMap.put("AMLOGIC8726MX_NEWC1S".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_NEWC1S);// NEWC1S
        terminalSeriesMap.put("AMLOGIC8726MX_NEWC1S_hongkong".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_NEWC1S);// NEWC1S_hongkong
        terminalSeriesMap.put("C2_C2".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_C2);// C2
        terminalSeriesMap.put("LeTVX60_MAX70".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_MAX70);// Max70
        terminalSeriesMap.put("MStar Android TV_S250F".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S250F);// S250F
        terminalSeriesMap.put("MStar+Android+TV_S250F".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S250F); // S250F，代码兼容设置
        terminalSeriesMap.put("MStar Android TV_S250F_THTF".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S250F);// S250F_THTF
        terminalSeriesMap.put("MStar+Android+TV_S250F_THTF".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S250F); // S250F_THTF，代码兼容设置
        terminalSeriesMap.put("MStar Android TV".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S250U); // S250U
        terminalSeriesMap.put("MStar+Android+TV".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S250U); // S250U，代码兼容设置
        terminalSeriesMap.put("MStar Android TV_S240F".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S240F);// S40_Air
        terminalSeriesMap.put("MStar+Android+TV_S240F".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_S240F); // S40_Air
        terminalSeriesMap.put("Android TV on MStar Amber3".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_X40);// X40
        terminalSeriesMap.put("Android+TV+on+MStar+Amber3".toLowerCase(), VipTpConstant.SUB_ORDER_FROM_X40);// X40,，代码兼容设置

        checkLiveBoughtCodeList.add(VipTpConstant.CHECK_LIVE_SEASON_PACKAGE_NOT_START);
        checkLiveBoughtCodeList.add(VipTpConstant.CHECK_LIVE_TEAM_PACKAGE_NOT_START);
        checkLiveBoughtCodeList.add(VipTpConstant.CHECK_LIVE_SCREENING_TICKET_NOT_START);

        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_0));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_2));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_3));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_4));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_5));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_40));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_44));
        orderTypesApplyToTv.add(String.valueOf(VipTpConstant.ORDER_TYPE_1001));

        paymentChannelCibnToLetvMap.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_CIBN_ALI),
                String.valueOf(VipTpConstant.PAYMENT_CHANNEL_ALIPAY));
        paymentChannelCibnToLetvMap.put(String.valueOf(VipTpConstant.PAYMENT_CHANNEL_CIBN_WEIXIN),
                String.valueOf(VipTpConstant.PAYMENT_CHANNEL_WEIXIN));

        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_ALIPAY);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_TV_ONE_YUAN);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_ALI_TVBOX);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_CIBN_ALI);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_LAKALA);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_CIBN_WEIXIN);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_ALIPAY_DEPRECATED);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_WEIXIN);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_PHONEPAY_UNICOM);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_LETVPOINT);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_PHONEPAY_MOBILE);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_PHONEPAY_TELECOM);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_PAYPAL);
        paymentChannelSet.add(VipTpConstant.PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY);

        deviceTypeSet.add(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV);
        // 目前暂未支持Mobile
        // deviceTypeSet.add(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_MOBILE);
        deviceTypeSet.add(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_LETV_BOX);
    }

    /**
     * 根据terminalBrand获取子终端代码
     * @param terminalBrand
     * @return
     */
    public static Integer getSubendByTerminalBrand(String terminalBrand) {
        return terminalBrand == null ? null : terminalBrandMap.get(terminalBrand.toLowerCase());
    }

    /**
     * 根绝terminalSeries获取子终端代码
     * @param terminalSeries
     * @return
     */
    public static Integer getSubendByTerminalSeries(String terminalSeries) {
        return terminalSeries == null ? null : terminalSeriesMap.get(terminalSeries.toLowerCase());
    }

    /**
     * 根据直播鉴权代码，检验是否已经购买过
     * @param code
     * @return
     */
    public static boolean checkLiveHasBounghtByCode(String code) {
        return checkLiveBoughtCodeList.contains(code);
    }

    /**
     * 校验订单类型列表中对应订单是否都适用TV
     * @param types
     * @return
     */
    public static boolean isOrderTypesApplyToTv(Collection<String> types) {
        if (CollectionUtils.isEmpty(types)) {
            return true;
        }
        return orderTypesApplyToTv.containsAll(types);
    }

    public static String calculateMd5(String departmentId, String username) {
        // MD5(pid +username + Key)
        StringBuilder plain = new StringBuilder();
        plain.append(departmentId).append(username).append(VipTpConstant.PAY_CENTER_KEY);

        String md5 = null;
        try {
            md5 = MessageDigestUtil.md5(plain.toString().getBytes(Charset.forName(CommonConstants.UTF8)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return md5;
    }

    /**
     * 获取国广下特殊支付渠道到自由版的转换（目前为支付宝和微信）
     * @param cibnPaymentChannel
     * @return
     */
    public static String getPaymentChannelFromCibnToLetv(Integer cibnPaymentChannel) {
        return paymentChannelCibnToLetvMap.get(String.valueOf(cibnPaymentChannel));
    }

    /**
     * 购买的产品类型，暂支持单点和套餐
     * @author yikun
     */
    public enum BUYTYPE {
        SINGLE_FILM(VipTpConstant.PRODUCT_TYPE_SINGLE_FILM),
        SERVICE_PACKAGE(VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE),
        LIVE(VipTpConstant.PRODUCT_TYPE_COMSUMR_LIVE);

        private final int id;

        private BUYTYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static BUYTYPE getBuytypeById(int id) {
            switch (id) {
            case VipTpConstant.PRODUCT_TYPE_SINGLE_FILM:
                return SINGLE_FILM;
            case VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE:
                return SERVICE_PACKAGE;
            case VipTpConstant.PRODUCT_TYPE_COMSUMR_LIVE:
                return LIVE;
            default:
                return null;
            }
        }
    }

    /**
     * TV版当前启用的支付渠道
     * @author yikun
     */
    public enum PAYMENT_CHANNEL {
        ALI(VipTpConstant.PAYMENT_CHANNEL_ALIPAY, "ALI"),
        WEIXIN(VipTpConstant.PAYMENT_CHANNEL_WEIXIN, "WEIXIN"),
        LAKALA(VipTpConstant.PAYMENT_CHANNEL_LAKALA, "LAKALA"),
        LETVPOINT(VipTpConstant.PAYMENT_CHANNEL_LETVPOINT, "LETVPOINT"),
        CIBN_ALI(VipTpConstant.PAYMENT_CHANNEL_CIBN_ALI, "CIBN_ALI"),
        PAYPAL(VipTpConstant.PAYMENT_CHANNEL_PAYPAL, "PAYPAL"),
        ONE_KEY_PAY(VipTpConstant.PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY, "ONE_KEY_PAY"),
        YEEPAYUNBIND(VipTpConstant.PAYMENT_CHANNEL_YEEPAYUNBIND, "YEEPAYUNBIND"),
        YEEPAYBIND(VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND, "YEEPAYBIND"),
        DEFAULT(VipTpConstant.PAYMENT_CHANNEL_NONE, "DEFAULT");
        private final int id;
        private final String name;

        private PAYMENT_CHANNEL(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public static PAYMENT_CHANNEL getPaymentChannelById(int id) {
            switch (id) {
            case VipTpConstant.PAYMENT_CHANNEL_ALIPAY:
                return ALI;
            case VipTpConstant.PAYMENT_CHANNEL_WEIXIN:
                return WEIXIN;
            case VipTpConstant.PAYMENT_CHANNEL_LAKALA:
                return LAKALA;
            case VipTpConstant.PAYMENT_CHANNEL_LETVPOINT:
                return LETVPOINT;
            case VipTpConstant.PAYMENT_CHANNEL_CIBN_ALI:
                return CIBN_ALI;
            case VipTpConstant.PAYMENT_CHANNEL_PAYPAL:
                return PAYPAL;
            case VipTpConstant.PAYMENT_CHANNEL_ONE_KEY_QUICK_PAY:
                return ONE_KEY_PAY;
            case VipTpConstant.PAYMENT_CHANNEL_YEEPAYUNBIND:
                return YEEPAYUNBIND;
            case VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND:
                return YEEPAYBIND;
            case VipTpConstant.PAYMENT_CHANNEL_NONE:
                return DEFAULT;
            default:
                return null;
            }
        }
    }

    /**
     * TV版涉及的会员类型
     * @author yikun
     */
    public enum VIPTYPE {
        NOT_MEMBER(VipTpConstant.SVIP_NOT_NUMBER),
        TV_VIP(VipTpConstant.SVIP_TV);
        private final int id;

        private VIPTYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static VIPTYPE getVipTypeById(int id) {
            switch (id) {
            case VipTpConstant.SVIP_NOT_NUMBER:
                return NOT_MEMBER;
            case VipTpConstant.SVIP_TV:
                return TV_VIP;
            default:
                return null;
            }
            /*
             * if (VipTpConstant.SVIP_NOT_NUMBER == id) {
             * return NOT_MEMBER;
             * }
             * if (VipTpConstant.SVIP_TV == id) {
             * return TV_VIP;
             * }
             * return null;
             */
        }
    }

    /**
     * TV端代金券适用范围
     * @author yikun
     */
    public enum VOUCHER_APPLICATION_TYPE {
        TV(VipTpConstant.VOUCHER_APPLICATION_TYPE_TV),
        SINGLE_FILM(VipTpConstant.VOUCHER_APPLICATION_TYPE_SINGLE_FILM);

        private final int id;

        private VOUCHER_APPLICATION_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static VOUCHER_APPLICATION_TYPE getVoucherApplicationTypeById(int id) {
            switch (id) {
            case VipTpConstant.VOUCHER_APPLICATION_TYPE_TV:
                return TV;
            case VipTpConstant.VOUCHER_APPLICATION_TYPE_SINGLE_FILM:
                return SINGLE_FILM;
            default:
                return null;
            }
        }
    }

    /**
     * TV端机卡绑定的deviceType适用范围
     * @author yukun
     */
    public enum DEVICE_BIND_LETV_APPLICATION_TYPE {
        TV(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV),
        LETV_BOX(VipTpConstant.DEVICE_BIND_DEVICE_TYPE_LETV_BOX);

        private final int id;

        private DEVICE_BIND_LETV_APPLICATION_TYPE(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static DEVICE_BIND_LETV_APPLICATION_TYPE getDeviceTypeApplicationById(int id) {
            switch (id) {
            case VipTpConstant.DEVICE_BIND_DEVICE_TYPE_TV:
                return TV;
            case VipTpConstant.DEVICE_BIND_DEVICE_TYPE_LETV_BOX:
                return LETV_BOX;
            default:
                return null;
            }
        }
    }

    /**
     * 根据语言环境获取域名
     * @param url
     * @param locale
     * @return
     */
    private static String getLocaleURL(String url, Locale locale) {
        if (url != null && locale != null) {
            if ("HK".equalsIgnoreCase(locale.getCountry())) {
                return url.replace("http://", "http://hk.");
            }
            return url;
        }
        return null;
    }

    /**
     * 判断支付渠道是否合法
     * @param paymentChannel
     * @return
     */
    public static boolean paymentChannelIsLegal(Integer paymentChannel) {
        if (paymentChannel == null || paymentChannel < 0) {
            return false;
        }
        return paymentChannelSet.contains(paymentChannel);
    }

    /**
     * 校验设备类型是否是合法，合法返回true，不合法返回false
     * @param deviceType
     * @return
     */
    public static boolean deviceTypeIsLegal(Integer deviceType) {
        if (deviceType == null) {
            return false;
        }
        return deviceTypeSet.contains(deviceType);
    }

    public static String getPaymentChannelCacheKey(String productType, Integer subend) {
        StringBuilder key = new StringBuilder(CacheContentConstants.VIP_PAYMENT_CHANNEL_ORDER);
        if (subend == null || subend < 0) {// 按照老逻辑不变
            key.append("_").append(productType);
        } else {// 加上subend表示直播SDK收银台支付渠道
            if (subend == VipTpConstant.SUB_ORDER_FROM_101) {// 目前只支持华数的特殊定制
                key.append("_").append(VipTpConstant.SUB_ORDER_FROM_101).append("_").append(productType);
            } else {
                key.append("_").append(productType);
            }
        }
        return key.toString();
    }
}
