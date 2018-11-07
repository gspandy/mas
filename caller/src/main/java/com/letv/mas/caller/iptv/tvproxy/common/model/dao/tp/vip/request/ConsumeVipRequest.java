package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 充值VIP请求参数,用于支付宝和微信支付
 * @author dunhongqin
 */
public class ConsumeVipRequest {

    private final static Logger log = LoggerFactory.getLogger(ConsumeVipRequest.class);

    /**
     * 订单号
     */
    private String corderid;

    /**
     * 用户中心id
     */
    private Long userid;

    /**
     * 登录账户的用户名（非登录名）
     */
    private String username;

    /**
     * 价格（单位：元）
     */
    private Integer price;

    /**
     * 单片时，专辑或视频 id；或套餐包id，如 2-包月，3-包季，4-包半年，5-包年
     */
    private Integer productid;

    /**
     * 支付中心接口所需参数，默认传0
     */
    private Integer pid;

    /**
     * 商品数量，默认传1
     */
    private Integer productnum = VipTpConstant.DEFAULT_PRODUCTNUM;

    /**
     * 描述，产品名称、单片名称
     */
    private String productname;

    /**
     * 产品描述，一般同productname
     */
    private String productdesc;

    /**
     * 支付中心接口参数，默认传空
     */
    private String defaultbank = "";

    /**
     * 支付中心接口所需参数，TV端传111，移动端传130，这里传111
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
     * 支付中心接口所需参数，传1
     */
    private String companyid = VipTpConstant.DEFAULT_COMPANYID;

    /**
     * 产品类型，1：单片，2：产品包
     */
    private Integer buyType;

    /**
     * 会员类型，0--非会员，1--普通会员，9--VIP会员；TV版默认传9
     */
    private Integer svip;

    /**
     * 手机号
     */
    private String cardNumber;

    /**
     * 下单时参与的活动id,多个id用","分割
     */
    private String activityIds;

    /**
     * 要获取的支付宝二维码类型，1--网页（原来类型），2--图片（接口变化后的类型）；本参数暂仅在payType为阿里支付时有效；
     */
    private Integer payCodeType;

    /**
     * apk version，当前TV版的版本，国光整改之后新增需求，0--letv，1--国广版;
     */
    private Integer av;

    /**
     * 支付渠道，如5-支付宝，24-微信等
     */
    private Integer paymentChannel;

    /**
     * 设备mac
     */
    private String mac;

    /**
     * 客户端设备ip
     */
    private String ip;

    /**
     * 设备终端类型，如"hisense"代码为0
     */
    private Integer subend;

    public ConsumeVipRequest() {
        super();
    }

    public ConsumeVipRequest(String corderid, String username, Long userid, Integer price, Integer productid,
            Integer pid, String productname, String productdesc, Integer buyType, int svip, Integer paymentChannel,
            String mac) {
        super();
        this.corderid = corderid;
        this.username = username;
        this.userid = userid;
        this.price = price;
        this.productid = productid;
        this.pid = pid;
        this.productname = productname;
        this.productdesc = productdesc;
        this.buyType = buyType;
        this.svip = svip;
        this.paymentChannel = paymentChannel;
        this.mac = mac;
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("corderid", this.corderid);
        parametersMap.put("userid", this.userid);
        // params.put("username", this.getUsername());
        // params.put("uname", this.getUsername());
        parametersMap.put("price", this.getPrice());
        parametersMap.put("companyid", this.companyid);
        parametersMap.put("pid", this.pid);
        parametersMap.put("deptid", this.deptid);
        parametersMap.put("backurl", this.backurl);
        parametersMap.put("fronturl", this.fronturl);
        parametersMap.put("productid", this.productid);
        parametersMap.put("productnum", this.productnum);
        parametersMap.put("productname", this.productname);
        parametersMap.put("productdesc", this.productdesc);
        parametersMap.put("defaultbank", this.defaultbank);
        parametersMap.put("svip", this.svip);
        parametersMap.put("buyType", this.buyType);
        parametersMap.put("mobileNo", this.cardNumber);
        parametersMap.put("activityIds", this.getActivityIds());
        parametersMap.put("av", this.av);
        parametersMap.put("deviceid", this.mac);
        parametersMap.put("sign", this.getSign());
        parametersMap.put("subend", this.subend);
        return parametersMap;
    }

    public String getSign() {
        try {
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
            return MessageDigestUtil.md5(sign.toString().getBytes(CommonConstants.UTF8));
        } catch (Exception e) {
            log.error("ConsumeVipRequest_" + this.username + "_" + this.userid + "_" + this.corderid
                    + " [getSign error]", e);
        }
        return "";
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

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getProductid() {
        return this.productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getProductnum() {
        return this.productnum;
    }

    public void setProductnum(Integer productnum) {
        this.productnum = productnum;
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

    public String getDefaultbank() {
        return this.defaultbank;
    }

    public void setDefaultbank(String defaultbank) {
        this.defaultbank = defaultbank;
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

    public String getCompanyid() {
        return this.companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public Integer getBuyType() {
        return this.buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public Integer getSvip() {
        return this.svip;
    }

    public void setSvip(Integer svip) {
        this.svip = svip;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getActivityIds() {
        return this.activityIds == null ? "" : this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
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

    public Integer getPaymentChannel() {
        return this.paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

}
