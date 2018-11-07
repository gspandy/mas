package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.NumberFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单点支付请求参数封装类
 * @author yikun
 * @since 2014-08-26
 */
public class OrderPayCodeRequest {
    private final Logger log = LoggerFactory.getLogger(OrderPayCodeRequest.class);

    /**
     * 传0(会员订单号，支付中心内部请求生成并返给请求方)
     */
    private String corderid;

    /**
     * 用户ID
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 传1
     */
    private String companyid = VipTpConstant.DEFAULT_COMPANYID;

    /**
     * TV端传111
     */
    private String deptid = VipTpConstant.DEFAULT_DEPTID;

    /**
     * 传0
     */
    private String productid;

    /**
     * 商品数量，默认传1
     */
    private Integer productnum = VipTpConstant.DEFAULT_PRODUCTNUM;

    /**
     * 传http://yuanxian.letv.com/letv/newPay.ldo
     */
    private String backurl = VipTpConstant.DEFAULT_BACKURL;

    /**
     * 传http://yuanxian.letv.com
     */
    private String fronturl = VipTpConstant.DEFAULT_FRONTURL;

    /**
     * 价格，单位：元；这里定义成String类型，为兼容客户端的数据，而支付中心接口要求使用int型，故接口调用时需要转型为int
     */
    private String price;

    /**
     * 产品类型，1--单片，2--套餐，这里传1
     */
    private Integer buyType;

    /**
     * 视频或专辑ID
     */
    private Long pid;

    /**
     * 会员类型，0--非会员，1--普通会员，2--高级会员，这里传0
     */
    private Integer svip;

    /**
     * 支付中心接口参数，校验签名
     */
    private String sign;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 支付二维码类型，1--支付宝网页，2--支付宝图片，3--微信，4--拉卡拉
     */
    private Integer payCodeType;

    /**
     * 设备mac
     */
    private String mac;

    /**
     * apk version，当前TV版的版本，国光整改之后新增需求，0--letv，1--国广版
     */
    private Integer av;

    /**
     * 代金券兑换码
     */
    private String couponCode;

    /**
     * 活动id列表，使用英文逗号拼接
     */
    private String activityIds;

    /**
     * 直播id；传直播id时，pid比传且与直播id取值一样
     */
    private Long videoid;

    /**
     * 客户端设备ip
     */
    private String ip;

    /**
     * 设备终端类型，如"hisense"代码为0
     */
    private Integer subend;

    public OrderPayCodeRequest() {
        super();
    }

    public OrderPayCodeRequest(String corderid, String username, Long userid, String productid, String price,
            Integer buyType, Long pid, Integer svip, String productName, Integer payCodeType, String mac) {
        super();
        this.corderid = corderid;
        this.username = username;
        this.userid = userid;
        this.productid = productid;
        this.price = price;
        this.buyType = buyType;
        this.pid = pid;
        this.svip = svip;
        this.productName = productName;
        this.payCodeType = payCodeType;
        this.mac = mac;
    }

    /**
     * 参数校验
     * @return
     *         <h1>TYPE</h1> int
     *         <h1>RESULT</h1>
     *         产品id为空，产品价格错误，支付渠道非法，购买直播的直播id错误，校验通过
     */
    public int assertValid() {
        if (this.pid == null || this.pid < 0) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PRODUCTID_ERROR;
        }
        if (this.price == null || !NumberFormatUtil.isNumeric(this.price) || Double.parseDouble(this.price) < 0) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_AMOUNT_ERROR;
        }
        if (this.payCodeType == null || this.payCodeType < 1 || this.payCodeType > 4) {
            return VipMsgCodeConstant.VIP_PURCHASE_ORDER_REQUEST_PAYMENTCHANNEL_ILLEGAL;
        }
        if (this.videoid != null) {
            if (this.videoid.longValue() != this.pid.longValue()) {
                return VipMsgCodeConstant.VIP_VIP_PURCHASE_ORDER_REQUEST_PID_VIDEOID_ERROR;
            }
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
            }
        }

        return VipMsgCodeConstant.REQUEST_SUCCESS;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>(18);

        parametersMap.put("corderid", this.corderid);
        parametersMap.put("userid", this.userid);
        parametersMap.put("username", this.username);
        parametersMap.put("companyid", this.companyid);
        parametersMap.put("deptid", this.deptid);
        parametersMap.put("productid", this.productid);
        parametersMap.put("productnum", this.productnum);
        parametersMap.put("backurl", this.backurl);
        parametersMap.put("fronturl", this.fronturl);
        parametersMap.put("price", this.price);
        parametersMap.put("buyType", this.buyType);
        parametersMap.put("pid", this.pid);
        parametersMap.put("svip", this.svip);
        parametersMap.put("av", this.av);
        parametersMap.put("couponCode", this.couponCode);
        parametersMap.put("activityIds", this.activityIds);
        parametersMap.put("videoid", this.videoid);
        parametersMap.put("deviceid", this.mac);
        parametersMap.put("sign", this.getSign());
        parametersMap.put("subend", this.subend);
        return parametersMap;
    }

    public String getSign() {
        if (this.buyType == null) {
            return "";
        }
        try {
            StringBuffer signBuf = new StringBuffer();
            switch (this.buyType) {
            case VipTpConstant.PRODUCT_TYPE_SINGLE_FILM:
                // 购买单片
                signBuf.append("corderid=").append(this.corderid).append("&");
                signBuf.append("userid=").append(this.userid).append("&");
                signBuf.append("price=").append(this.price).append("&");
                signBuf.append("companyid=").append(this.companyid).append("&");
                signBuf.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
                signBuf.append("deptid=").append(this.deptid).append("&");
                signBuf.append("pid=").append(this.pid);
                this.sign = MessageDigestUtil.md5(signBuf.toString().getBytes(CommonConstants.UTF8));
                break;
            case VipTpConstant.PRODUCT_TYPE_SERVICE_PACKAGE:
                // 购买套餐
                signBuf.append("corderid=").append(this.corderid).append("&");
                signBuf.append("userid=").append(this.userid).append("&");
                signBuf.append("companyid=").append(this.companyid).append("&");
                signBuf.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
                signBuf.append("deptid=").append(this.deptid).append("&");
                signBuf.append("pid=").append(this.pid);
                this.sign = MessageDigestUtil.md5(signBuf.toString().getBytes(CommonConstants.UTF8));
                break;
            default:
                this.sign = "";
                break;
            }
            return this.sign;
        } catch (Exception e) {
            log.error("getSign_error", e.getMessage(), e);
        }
        return this.sign;
    }

    public String getCorderid() {
        return this.corderid;
    }

    public void setCorderid(String corderid) {
        this.corderid = corderid;
    }

    public Long getUserid() {
        return this.userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getProductid() {
        return this.productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Integer getProductnum() {
        return this.productnum;
    }

    public void setProductnum(Integer productnum) {
        this.productnum = productnum;
    }

    public String getBackurl() {
        return this.backurl;
    }

    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getBuyType() {
        return this.buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSvip() {
        return this.svip;
    }

    public void setSvip(Integer svip) {
        this.svip = svip;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPayCodeType() {
        return this.payCodeType;
    }

    public void setPayCodeType(Integer payCodeType) {
        this.payCodeType = payCodeType;
    }

    public Integer getAv() {
        return this.av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getActivityIds() {
        return this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public Long getVideoid() {
        return this.videoid;
    }

    public void setVideoid(Long videoid) {
        this.videoid = videoid;
    }

    public String getMac() {
        return this.mac;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getSubend() {
        return this.subend;
    }

    public void setSubend(Integer subend) {
        this.subend = subend;
    }

}
