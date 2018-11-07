package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.NumberFormatUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 购买会员请求通用封装类，支持购买套餐和单点，使用支付宝、微信、拉卡拉、乐点4种支付渠道
 * @author yikun
 */
public class PurchaseVipCommonRequest {

    private final static Logger LOG = LoggerFactory.getLogger(ConsumeVipRequest.class);

    /**
     * 登录账户的用户名
     */
    private String username;

    /**
     * 用户中心id
     */
    private Long userid;

    /**
     * 订单号
     */
    private String corderid;

    /**
     * 消费类型，1--影片单点，2--套餐，3--直播单点
     */
    private Integer purchaseType;

    /**
     * 产品类型，1：单片，2：产品包
     */
    private Integer buyType;

    /**
     * 套餐包id，如 2-包月，3-包季，4-包半年，5-包年；
     * 注意，第三方接口定义中，productid与pid、videoid不共用，即购买套餐时productid有值，pid传0，videoid不传；
     * 单点时pid有值，productid传0，videoid不传；直播付费时，videoid有值，productid传0，pid传0；
     * 本Request类设计中保留该定义，与第三方接口定义；但使用时，仅使用productid，在构造函数中统一封装；
     */
    private Long productid;

    /**
     * 专辑或视频id，单点时使用
     */
    private Long pid;

    /**
     * 直播id；仅在直播情况下需传直播的标识，其他情况可不传
     */
    private Long videoid;

    /**
     * 支付渠道，如5-支付宝，24-微信等
     */
    private Integer paymentChannel;

    /**
     * 价格（单位：元，允许小数，套餐是为正数，单片时为两位小数形式数据）
     */
    private String price;

    /**
     * 会员类型，0--非会员，1--普通会员，9--VIP会员；TV版默认传9，单点时传0
     */
    private Integer svip;

    /**
     * 支付中心接口所需参数，传1
     */
    private String companyid = VipTpConstant.DEFAULT_COMPANYID;

    /**
     * 支付中心接口所需参数，TV端传111，PC端传112，移动端传130，M站传113，这里传111
     */
    private String deptid = VipTpConstant.DEFAULT_DEPTID;

    /**
     * 支付中心接口所需参数，传"http://yuanxian.letv.com/letv/newPay.ldo"
     */
    private String backurl = VipTpConstant.DEFAULT_BACKURL;

    /**
     * 支付中心接口所需参数，传"http://yuanxian.letv.com"
     */
    private String fronturl = VipTpConstant.DEFAULT_FRONTURL;

    /**
     * 商品数量，默认传1
     */
    private Integer productnum = VipTpConstant.DEFAULT_PRODUCTNUM;

    /**
     * 下单时参与的活动id,多个id用","分割
     */
    private String activityIds;

    /**
     * 代金券兑换码
     */
    private String couponCode;

    /**
     * 描述，产品名称、单片名称
     */
    private String productname;

    /**
     * 产品描述，一般同productname
     */
    private String productdesc;

    /**
     * apk version，当前TV版的版本，国光整改之后新增需求，0--letv，1--国广版，2--第三方SDK;
     */
    private Integer av;

    /**
     * 用户手机号
     */
    private String ext;

    /**
     * 设备mac
     */
    private String mac;

    /**
     * 客户端设备ip
     */
    private String ip;

    /**
     * 是否开通一键支付，paypal支付专用，0--不开通，1--开通；暂固定开通
     */
    private Integer bindingtype;

    /**
     * 是否传递配送地址，paypal支付专用，0--不传递，1--传递，需要填写收件人相关信息，暂固定不传递
     */
    private Integer addroverride = 0;

    /**
     * 设备终端类型，如"hisense"代码为0
     */
    private Integer subend;

    /**
     * 绑定ID，当使用快捷支付时，需要传绑定ID，会在参数校验中进行校验
     */
    private String bindid;

    /**
     * 充值卡卡号
     */
    private String cardNumber;

    /**
     * 机卡设备信息
     */
    private String deviceKey;

    public PurchaseVipCommonRequest() {
        super();
    }

    public PurchaseVipCommonRequest(String username, Long userid, String corderid, Integer purchaseType,
            Long productid, Integer paymentChannel, String price, String activityIds, String productname, Integer av,
            String mac) {
        super();
        this.username = username;
        this.userid = userid;
        this.corderid = corderid;
        this.purchaseType = purchaseType;
        this.productid = productid;
        this.paymentChannel = paymentChannel;
        this.price = price;
        this.activityIds = activityIds;
        this.productname = productname;
        this.av = av;
        this.mac = mac;

        this.init();
    }

    /**
     * 获取当前消费的套餐id或单片id；主要供日志中使用
     * @return
     */
    public String getActualProductId() {
        if (this.buyType != null && VipTpConstant.PRODUCT_TYPE_SINGLE_FILM == this.buyType) {
            return String.valueOf(this.pid);
        }
        return String.valueOf(this.productid);
    }

    /**
     * 调整参数
     */
    private void init() {
        if (StringUtils.isBlank(this.corderid)) {
            // 其中支付中心直接到院线下单
            this.corderid = VipTpConstant.DEFAULT_CORDERID;
        }

        if (this.paymentChannel != null && VipTpConstant.PAYMENT_CHANNEL_PAYPAL == this.paymentChannel) {
            this.bindingtype = 1;
        }

        if (this.purchaseType != null) {
            switch (this.purchaseType.intValue()) {
            case 1:
                // 影片单点
                this.buyType = VipTpConstant.PRODUCT_TYPE_SINGLE_FILM;
                this.pid = this.productid;
                this.productid = Long.valueOf(VipTpConstant.DEFAULT_PRODUCTID);
                this.svip = VipTpConstant.SVIP_NOT_NUMBER;
                break;
            case 2:
                // 套餐
                this.buyType = VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE;
                this.svip = VipTpConstant.SVIP_TV;
                this.pid = Long.valueOf(VipTpConstant.DEFAULT_PID);
                break;
            case 3:
                // 直播单点
                this.buyType = VipTpConstant.PRODUCT_TYPE_SINGLE_FILM;
                this.videoid = this.productid;
                this.productid = Long.valueOf(VipTpConstant.DEFAULT_PRODUCTID);
                this.svip = VipTpConstant.SVIP_NOT_NUMBER;
                break;
            default:
                break;
            }
        }
        // 活动ID中去除虚拟的一元支付ID，活动ID的数据结构形如：1,2,3
        if (StringUtils.isNotBlank(this.activityIds)) {
            if (this.activityIds.indexOf(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID) != -1) {
                this.activityIds = this.activityIds + ",";// 在字符串末尾加上逗号，便于替换时连逗号一起替换而不需要判断位置
                // 替换虚拟活动ID为空串
                this.activityIds = this.activityIds.replace(VipTpConstant.PAYMENT_ACTIVITY_ONE_FOR_MONTH_VIRTUALID
                        + ",", "");
                if (StringUtils.isNotBlank(this.activityIds)) {
                    // 去掉最后一个逗号
                    this.activityIds = this.activityIds.substring(0, this.activityIds.length() - 1);
                }
            }
            if (StringUtils.isNotBlank(this.activityIds)) {// 处理一些非法的活动ID序列
                String[] actids = this.activityIds.split(",");
                StringBuilder sb = new StringBuilder();
                for (String id : actids) {
                    if (StringUtils.isNotBlank(id) && !"null".equals(id)) {
                        sb.append(id).append(",");
                    }
                }
                if (sb != null && sb.length() > 0) {
                    this.activityIds = sb.substring(0, sb.length() - 1);
                } else {
                    this.activityIds = null;
                }
            }
        }
    }

    /**
     * 参数校验
     * @return
     *         <h1>TYPE</h1> int
     *         <h1>RESULT</h1>
     *         产品类型错误，支付渠道非法，单点影片不允许使用乐点支付，购买单点影片时价格不能为空，购买直播的直播id错误，校验通过
     */
    public int assertValid() {
        if (this.av != null && VipTpConstant.BROADCAST_APK_VERSION_TPSDK != this.av) {
            if (this.userid == null || this.userid < 0) {
                return VipMsgCodeConstant.REQUEST_USERINFO_ERROR;
            }
        }
        if ("*".equals(this.corderid)) {
            return VipMsgCodeConstant.VIP_VIP_PURCHASE_ORDER_REQUEST_CORDERID_ERROR;
        }
        if (this.buyType == null || VipTpConstantUtils.BUYTYPE.getBuytypeById(this.buyType) == null) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCT_TYPE_ERROR;
        }
        if (this.buyType == 2) {
            if (this.productid == null || this.productid < 1) {
                return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCTID_ERROR;
            }
        }
        if (this.paymentChannel == null || VipTpConstantUtils.PAYMENT_CHANNEL.getPaymentChannelById(this.paymentChannel) == null) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PAYMENTCHANNEL_ILLEGAL;
        }

        if (this.paymentChannel == VipTpConstant.PAYMENT_CHANNEL_YEEPAYBIND) {
            if (StringUtils.isBlank(this.bindid)) {
                return VipMsgCodeConstant.VIP_PURCHASE_VIP_REQUEST_QUICK_PAY_BINDID_EMPTY;
            }
        }
        // 单点影片不允许使用乐点支付
        if (VipTpConstant.PRODUCT_TYPE_SINGLE_FILM == this.buyType
                && VipTpConstant.PAYMENT_CHANNEL_LETVPOINT == this.paymentChannel) {
            return VipMsgCodeConstant.VIP_PURCHASE_VIP_REQUEST_SINGLE_PAYMENTCHANNEL_ERROR;
        }

        // 单点时必须传价格，价格是整形或double形式的字符串格式，但传向支付中心接口中的参数为int型
        if (VipTpConstant.PRODUCT_TYPE_SINGLE_FILM == this.buyType) {
            if (this.price == null || !NumberFormatUtil.isNumeric(this.price) || Double.parseDouble(this.price) < 0) {
                return VipMsgCodeConstant.VIP_PURCHASE_VIP_REQUEST_SINGLE_PRICE_ERROR;
            }

            /*
             * // Double形式的原价
             * Double originalPrice = null;
             * try {
             * originalPrice = Double.valueOf(this.price);
             * // 统一格式化为2位小数形式，主义有bug，小数的四舍五入会对结果造成一定干扰，比如传入1.111，会对实际价格1.11产生干扰
             * this.price =
             * NumberFormatUtil.getDecimalNubmerFromDouble(originalPrice,
             * NumberFormatUtil.COMMON_DECIMAL_PRICE_FORMAT);
             * } catch (NumberFormatException e) {
             * return false;
             * }
             * if (originalPrice.doubleValue() < 0) {
             * return false;
             * }
             */

            // this.price = String.valueOf(originalPrice.intValue());
        }

        // 如果是直播单点，则video需要特殊处理
        if (this.videoid != null) {
            String videoidStr = String.valueOf(this.videoid);
            if (videoidStr.length() > 17) {
                /*
                 * 根据Boss Wiki定义，videoid是将频道、赛事类型、年份、轮次、场次、类型（type）、count组合起来，
                 * 格式为00 000 0000 000 0000 00 000，分表表示频道、赛事类型、年份 、轮次 、 场次
                 * 、类型和count；
                 * 而从直播大厅获取的直播id仅为前面16位数据，需要在这里拼接上“类型”数据，这里拼接“01”（表示直播券），
                 * 得到下单使用的正确pid。
                 * 而针对支付中心接口中pid定义为Long型数据的问题，这里保证拼接后的videoid仍能正确转型为Long（最长19位），
                 * 故这里对videoid的字符串形式数据长队进行限制（不能超过17位）
                 */

                return VipMsgCodeConstant.VIP_ORDER_PAYCODE_REQUEST_VIDEOID_ERROR;
            } else {
                videoidStr += VipTpConstant.LIVE_DEFAULT_TYPE;
                try {
                    this.videoid = Long.parseLong(videoidStr);
                    // this.videoid = videoidStr;
                    this.pid = this.videoid;
                } catch (Exception e) {
                    return VipMsgCodeConstant.VIP_ORDER_PAYCODE_REQUEST_VIDEOID_ERROR;
                }
            }
        }

        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("corderid", this.corderid);
        parametersMap.put("userid", this.userid);
        // params.put("username", this.getUsername());
        // params.put("uname", this.getUsername());
        parametersMap.put("buyType", this.buyType);
        parametersMap.put("productid", this.productid);
        parametersMap.put("pid", this.pid);
        // parametersMap.put("productname", this.productname);
        // parametersMap.put("productdesc", this.productdesc);
        parametersMap.put("price", this.price);
        parametersMap.put("svip", this.svip);
        parametersMap.put("activityIds", this.activityIds);
        parametersMap.put("couponCode", this.couponCode);
        parametersMap.put("av", this.av);
        parametersMap.put("videoid", this.videoid);
        parametersMap.put("deviceid", this.mac);
        parametersMap.put("ip", this.ip);
        parametersMap.put("productnum", this.productnum);
        parametersMap.put("bindingtype", this.bindingtype);
        parametersMap.put("addroverride", this.addroverride);
        parametersMap.put("sign", this.getSign());
        parametersMap.put("deptid", this.deptid);
        parametersMap.put("companyid", this.companyid);
        parametersMap.put("backurl", this.backurl);
        parametersMap.put("fronturl", this.fronturl);
        parametersMap.put("subend", this.subend);
        parametersMap.put("bindid", this.bindid);
        parametersMap.put("cardNumber", StringUtils.trimToEmpty(this.cardNumber));
        parametersMap.put("mac", StringUtils.trimToEmpty(this.mac));
        parametersMap.put("deviceKey", StringUtils.trimToEmpty(this.deviceKey));
        return parametersMap;
    }

    public String getSign() {
        StringBuilder sign = new StringBuilder();
        // String secKey = "ce0806981627d00d4b96beb051a2b629";
        if (this.buyType != null && this.buyType == 1) {
            // 单片加密串
            sign.append("corderid=").append(this.corderid).append("&");
            sign.append("userid=").append(this.userid).append("&");
            sign.append("price=").append(this.price).append("&");
            sign.append("companyid=").append(1).append("&");
            sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
            sign.append("deptid=").append(this.deptid).append("&");
            sign.append("pid=").append(this.pid);
            // sign.append("productid=").append(this.productid);
        } else {
            // 套餐加密串
            sign.append("corderid=").append(this.corderid).append("&");
            sign.append("userid=").append(this.userid).append("&");
            sign.append("companyid=").append(1).append("&");
            sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
            sign.append("deptid=").append(this.deptid).append("&");
            sign.append("pid=").append(this.pid);
        }
        try {
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            LOG.error("ConsumeVipRequest_" + this.username + "_" + this.userid + "_" + this.corderid
                    + ": getSign error.", e);
        }
        return "";
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public Integer getPurchaseType() {
        return this.purchaseType;
    }

    public void setPurchaseType(Integer purchaseType) {
        this.purchaseType = purchaseType;
    }

    public Integer getBuyType() {
        return this.buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Long getProductid() {
        return this.productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getVideoid() {
        return this.videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getSvip() {
        return this.svip;
    }

    public void setSvip(Integer svip) {
        this.svip = svip;
    }

    public Integer getPaymentChannel() {
        return this.paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public Integer getProductnum() {
        return this.productnum;
    }

    public void setProductnum(Integer productnum) {
        this.productnum = productnum;
    }

    public String getActivityIds() {
        return this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public String getCompanyid() {
        return this.companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getDeptid() {
        return this.deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    public String getBackurl() {
        return this.backurl;
    }

    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }

    public String getFronturl() {
        return this.fronturl;
    }

    public void setFronturl(String fronturl) {
        this.fronturl = fronturl;
    }

    public String getProductname() {
        return this.productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdesc() {
        return this.productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public Integer getAv() {
        return this.av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getBindingtype() {
        return this.bindingtype;
    }

    public void setBindingtype(Integer bindingtype) {
        this.bindingtype = bindingtype;
    }

    public Integer getAddroverride() {
        return this.addroverride;
    }

    public void setAddroverride(Integer addroverride) {
        this.addroverride = addroverride;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getSubend() {
        return this.subend;
    }

    public void setSubend(Integer subend) {
        this.subend = subend;
    }

    public String getBindid() {
        return this.bindid;
    }

    public void setBindid(String bindid) {
        this.bindid = bindid;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

}
