package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageDigestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 手机和拉卡拉支付请求参数
 * @author dunhongqin
 */
public class PhonePayRequest {
    private final Logger log = LoggerFactory.getLogger(PhonePayRequest.class);

    /**
     * 支付通道类型，同paymentChannel；31代表联通，34代表移动，35代表电信
     */
    private String payTypeId;

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
     * 支付中心接口所需参数，传1
     */
    private String companyid = VipTpConstant.DEFAULT_COMPANYID;

    /**
     * 支付中心接口所需参数，TV端传111，移动端传130，这里传111
     */
    private String deptid = VipTpConstant.DEFAULT_DEPTID;

    /**
     * 单片时，专辑或视频 id；或套餐包id，如 2-包月 3-包季 4-包半年 5-包年
     */
    private String productid;

    /**
     * 支付中心接口所需参数，传"http://yuanxian.letv.com/letv/newPay.ldo"
     */
    private String backurl = VipTpConstant.DEFAULT_BACKURL;

    /**
     * 支付中心接口所需参数，传"http://yuanxian.letv.com"
     */
    private String fronturl = VipTpConstant.DEFAULT_FRONTURL;

    /**
     * 价格（单位：元）
     */
    private String price;

    /**
     * 消费类型，产品类型，1--单片，2--套餐，这里默认传2
     */
    private String buyType;

    /**
     * 会员类型，0--不是会员，1--普通VIP，9--高级VIP
     */
    private String svip = VipTpConstant.ORDER_FROM_TV2;

    /**
     * 传用户输入的手机号
     */
    private String ext;

    /**
     * 单片id，这里不用，默认传0
     */
    private String pid;

    /**
     * 是否是拉卡拉支付
     */
    private boolean isLakala;

    /**
     * 客户端ip
     */
    private String ip;

    /**
     * 产品名称，拉卡拉支付时使用
     */
    private String productname;

    /**
     * 产品描述，一般同产品名称，拉卡拉支付时使用
     */
    private String productdesc;

    /**
     * 下单时参与的活动id,多个id用","分割
     */
    private String activityIds;

    /**
     * 设备mac
     */
    private String mac;

    /**
     * apk version，当前TV版的版本，国光整改之后新增需求，0--letv，1--国广版;
     */
    private Integer av;

    /**
     * 设备终端类型，如"hisense"代码为0
     */
    private Integer subend;

    public PhonePayRequest() {
        super();
    }

    public PhonePayRequest(String payTypeId, String corderid, Long userid, String username, String productid,
            String price, String buyType, String ext, String pid, boolean isLakala, String ip, String productname,
            String productdesc, String mac) {

        this.payTypeId = payTypeId;
        this.corderid = corderid;
        this.userid = userid;
        this.username = username;
        this.productid = productid;
        this.price = price;
        this.buyType = buyType;
        this.ext = ext;
        this.pid = pid;
        this.isLakala = isLakala;
        this.ip = ip;
        this.productname = productname;
        this.productdesc = productdesc;
        this.mac = mac;
    }

    /**
     * 获取参数列表
     * @return
     */
    public Map<String, Object> getParameterMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("corderid", this.corderid);
        parametersMap.put("userid", this.userid);
        // parameterMap.put("username", this.username);
        parametersMap.put("companyid", this.companyid);
        parametersMap.put("deptid", this.deptid);
        parametersMap.put("productid", this.productid);
        parametersMap.put("backurl", this.backurl);
        parametersMap.put("fronturl", this.fronturl);
        parametersMap.put("price", this.price);
        parametersMap.put("buyType", this.buyType);
        parametersMap.put("svip", this.svip);
        parametersMap.put("ext", this.ext);
        parametersMap.put("ip", this.ip);
        parametersMap.put("productname", this.productname);
        parametersMap.put("productdesc", this.productdesc);
        parametersMap.put("payTypeId", this.payTypeId);
        parametersMap.put("productnum", 1);
        parametersMap.put("pid", 0);
        parametersMap.put("activityIds", this.getActivityIds());
        parametersMap.put("deviceid", this.mac);
        parametersMap.put("sign", this.getSign());
        parametersMap.put("subend", this.subend);
        return parametersMap;
    }

    /**
     * 支付中心公共加密函数
     * @param buyType
     * @param corderid
     * @param userid
     * @param price
     * @param companyid
     * @param deptid
     * @param productid
     * @param pid
     * @return
     */
    private String getSign() {
        try {
            StringBuffer sign = new StringBuffer();
            // String secKey = "ce0806981627d00d4b96beb051a2b629";
            if (this.buyType != null && this.buyType.equals("1")) {
                // 单片加密串
                sign.append("corderid=").append(this.corderid).append("&");
                sign.append("userid=").append(this.userid).append("&");
                sign.append("price=").append(this.price).append("&");
                sign.append("companyid=").append(1).append("&");
                sign.append(VipTpConstant.BOSS_API_ZHIFU_COMMON_SING_KEY).append("&");
                sign.append("deptid=").append(this.deptid).append("&");
                sign.append("pid=").append(this.pid);
                // sign.append("productid=").append(productid);
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
            log.error("getSign_ error", e.getMessage(), e);
            return "";
        }
    }

    public String getPayTypeId() {
        return this.payTypeId;
    }

    public void setPayTypeId(String payTypeId) {
        this.payTypeId = payTypeId;
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

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBuyType() {
        return this.buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }

    public String getSvip() {
        return this.svip;
    }

    public void setSvip(String svip) {
        this.svip = svip;
    }

    public String getExt() {
        return this.ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public boolean isLakala() {
        return this.isLakala;
    }

    public void setLakala(boolean isLakala) {
        this.isLakala = isLakala;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getActivityIds() {
        return this.activityIds == null ? "" : this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getAv() {
        return this.av;
    }

    public void setAv(Integer av) {
        this.av = av;
    }

    public Integer getSubend() {
        return this.subend;
    }

    public void setSubend(Integer subend) {
        this.subend = subend;
    }

}
