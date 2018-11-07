package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.request;

import java.util.HashMap;
import java.util.Map;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipMsgCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import org.apache.commons.lang.StringUtils;

public class LetvCardRechargeRequest {
    private String username;
    private String cardNumber;
    private String password;
    private String rechargeType;// 支付类型 乐卡(lk)
    private Integer payFlag;// 充值支付标志:1代表充值,2代表支付
    private String backurl;
    private String departmentId;// 部门ID
    private String orderCode;
    private String cardSecret;// 卡密
    private Long userId;
    private String terminalBrand;
    private String terminalSeries;
    private String mac;
    private String deviceKey;
    /**
     * 操作类型，1--充值，2--兑换，默认充值
     */
    private Integer operationType;

    /**
     * 此次兑换的可使用的最大乐点数
     */
    private Integer amount;

    private static final Map<String, Integer> brand_map = new HashMap<String, Integer>();
    private static final Map<String, Integer> series_map = new HashMap<String, Integer>();

    static {
        brand_map.put("hisense", 0);// 海信
        brand_map.put("haier", 1);// 海尔
        brand_map.put("skyworth", 2);// 创维
        brand_map.put("tcl", 3);// TCL
        brand_map.put("konka", 4);// 康佳
        brand_map.put("tongfang", 5);// 同方
        brand_map.put("panda", 9);// 熊猫
        brand_map.put("xiaomi", 10);// 小米
        brand_map.put("tpv", 11);// 冠捷
        brand_map.put("changhong", 8);// 长虹

        series_map.put("msm8960", 50);// X60
        series_map.put("letvx60", 50);// X60
        series_map.put("Android TV on MStar Amber3 S50".toLowerCase(), 51);// S50
        series_map.put("Android TV on MStar Amber3".toLowerCase(), 52);// S40
        series_map.put("Android TV on MStar Amber3 S40".toLowerCase(), 52);// S40
        series_map.put("epg", 53);// S10
        series_map.put("Hi3716C".toLowerCase(), 54);// ST1
        series_map.put("AMLOGIC8726MX".toLowerCase(), 55);// C1
        series_map.put("AMLOGIC8726MX_C1S".toLowerCase(), 56);// C1s
        series_map.put("AMLOGIC8726MX_C1S_UI_2".toLowerCase(), 56);// C1s
    }

    public LetvCardRechargeRequest() {
    }

    public LetvCardRechargeRequest(String username, String departmentId, String cardNumber, String password,
            String rechargeType, Integer payFlag) {
        this.username = username;
        this.departmentId = departmentId;
        this.cardNumber = cardNumber;
        this.password = password;
        this.rechargeType = rechargeType;
        this.payFlag = payFlag;
    }

    public LetvCardRechargeRequest(String username, String departmentId, String rechargeType, Integer payFlag,
            String cardSecret, String terminalBrand) {
        this.username = username;
        this.departmentId = departmentId;
        this.rechargeType = rechargeType;
        this.payFlag = payFlag;
        this.cardSecret = cardSecret;
        this.terminalBrand = terminalBrand;
    }

    public LetvCardRechargeRequest(String username, String departmentId, String rechargeType, Integer payFlag,
            String cardSecret, String terminalBrand, String terminalSeries, String mac, String deviceKey) {
        this.username = username;
        this.departmentId = departmentId;
        this.rechargeType = rechargeType;
        this.payFlag = payFlag;
        this.cardSecret = cardSecret;
        this.terminalBrand = terminalBrand;
        this.terminalSeries = terminalSeries;
        this.mac = mac;
        this.deviceKey = deviceKey;
    }

    public LetvCardRechargeRequest(String username, String departmentId, String cardNumber, String password,
            String rechargeType, Integer payFlag, String cardSecret) {
        this.username = username;
        this.departmentId = departmentId;
        this.cardNumber = cardNumber;
        this.password = password;
        this.rechargeType = rechargeType;
        this.payFlag = payFlag;
        this.cardSecret = cardSecret;
    }

    /**
     * 参数校验1，针对乐卡校验接口，只校验卡号是否为空
     * @return
     */
    public int assertValid() {
        if (StringUtils.isBlank(this.cardSecret)) {
            return VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_CARDSECRET_EMPTY;
        }
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    /**
     * 老接口参数校验逻辑
     * @return
     */
    public int assertValid4Old() {
        if ((this.userId == null || this.userId < 0) && StringUtils.isBlank(this.username)) {
            return CommonMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        return this.assertValid();
    }

    /**
     * 参数校验2,针对充值和兑换
     * @return
     */
    public int assertValid2() {
        if (this.userId == null || this.userId < 0) {
            return CommonMsgCodeConstant.REQUEST_USERINFO_ERROR;
        }
        if (this.operationType != null && this.operationType == 2) {// 兑换
            if (StringUtils.isBlank(this.cardSecret) && (this.amount == null || this.amount < 1)) {
                // 不传卡号和最大兑换乐点数，不允许兑换
                return VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_EXCHANGE_INFO_EMPTY;
            }
        } else {// 否则认为是充值，卡号不能为空
            if (StringUtils.isBlank(this.cardSecret)) {
                return VipMsgCodeConstant.VIP_LETV_CARD_REQUEST_CARDSECRET_EMPTY;
            }
        }
        return CommonMsgCodeConstant.REQUEST_SUCCESS;
    }

    @Override
    public String toString() {
        return "LetvCardRechargeRequest [username=" + this.username + ", cardNumber=" + this.cardNumber + ", password="
                + this.password + ", rechargeType=" + this.rechargeType + ", payFlag=" + this.payFlag + ", backurl="
                + this.backurl + ", departmentId=" + this.departmentId + ", orderCode=" + this.orderCode
                + ", cardSecret=" + this.cardSecret + "]";
    }

    public String getSubend() {
        Integer result = null;
        if (this.terminalSeries != null && this.terminalBrand != null && "letv".equalsIgnoreCase(this.terminalBrand)) {
            result = series_map.get(this.terminalSeries.toLowerCase());
        } else if (this.terminalBrand != null) {
            result = brand_map.get(this.terminalBrand.toLowerCase());
        }
        return result == null ? "" : result + "";
    }

    public Map<String, Object> getParametersMap() {
        Map<String, Object> parametersMap = new HashMap<String, Object>();

        parametersMap.put("buyType", 0);
        parametersMap.put("backurl", ApplicationConstants.BOSS_API_ZHIFU_BASE_HOST + "/tobuylepoint");
        parametersMap.put("deptid", VipTpConstant.DEFAULT_DEPTID);
        parametersMap.put("cardnumber", this.cardSecret);
        parametersMap.put("chargetype", 1);
        parametersMap.put("companyid", 1);
        parametersMap.put("corderid", 0);
        parametersMap.put("productdesc", "充值乐点");
        parametersMap.put("productname", "充值乐点");
        parametersMap.put("userid", StringUtils.trimToEmpty(String.valueOf(this.userId)));
        parametersMap.put("username", this.username);
        parametersMap.put("subend", this.getSubend());
        parametersMap.put("cardSecret", this.cardSecret);
        parametersMap.put("mac", this.mac);
        if (StringUtils.isBlank(this.deviceKey)) {
            this.deviceKey = "";
        }
        parametersMap.put("deviceKey", this.deviceKey);

        return parametersMap;
    }

    /**
     * @return the departmentId
     */
    public String getDepartmentId() {
        return this.departmentId;
    }

    /**
     * @param departmentId
     *            the departmentId to set
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the cardNumber
     */
    public String getCardNumber() {
        return this.cardNumber;
    }

    /**
     * @param cardNumber
     *            the cardNumber to set
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the rechargeType
     */
    public String getRechargeType() {
        return this.rechargeType;
    }

    /**
     * @param rechargeType
     *            the rechargeType to set
     */
    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    /**
     * @return the payFlag
     */
    public Integer getPayFlag() {
        return this.payFlag;
    }

    /**
     * @param payFlag
     *            the payFlag to set
     */
    public void setPayFlag(Integer payFlag) {
        this.payFlag = payFlag;
    }

    /**
     * @return the backurl
     */
    public String getBackurl() {
        return this.backurl;
    }

    /**
     * @param backurl
     *            the backurl to set
     */
    public void setBackurl(String backurl) {
        this.backurl = backurl;
    }

    public String getSignature(String orderCode) {
        return "";
        // return LetvPayCommon.md5ForLetvCardRecharge(orderCode,
        // this.departmentId, this.username);
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCardSecret() {
        return this.cardSecret;
    }

    public void setCardSecret(String cardSecret) {
        this.cardSecret = cardSecret;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public static Map<String, Integer> getBrandMap() {
        return brand_map;
    }

    public static Map<String, Integer> getSeriesMap() {
        return series_map;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public Integer getOperationType() {
        return this.operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
