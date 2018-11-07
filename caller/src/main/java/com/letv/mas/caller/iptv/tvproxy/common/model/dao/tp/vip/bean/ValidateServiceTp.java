package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Boss V2版本会员统一鉴权接口响应类
 * @author kevinyi
 */
public class ValidateServiceTp implements Serializable {

    /**
     * 请求失败时返回
     */
    private Integer errorCode;

    /**
     * 错误信息，请求失败（返回errorCode）时返回
     */
    private String msg;

    /**
     * 请求成功时返回，1--鉴权通过，0--不通过
     */
    private Integer status;

    /**
     * token只在有权限或者可以试看的时候，才返回，用户端不要直接依赖于token，需要先检查是否鉴权通过，或“试看截止时间是否已过期”
     */
    private String token;

    /**
     * 调度提升qos级别需要返回uinfo，PC端为1，移动端为2，tv端为4
     */
    private String uinfo;

    /**
     * 专辑、视频单点时剩余的可使用截止时间（只有点播才有返回值）
     */
    private Long tvodRts;

    /**
     * 内容对应的会员ID（这些会员可看），如["62","64", "65", "66"],
     */
    private List<String> vipInfo;

    /**
     * 内容对应的会员ID，按类型归并（这些会员可看），如"102": [64, 65, 66]
     */
    private Map<String, List<String>> vipTypeInfo;

    /**
     * 内容对应的会员套餐ID（这些套餐可看），如 [21, 22, 23]
     */
    private List<Integer> packageInfo;

    /**
     * 专辑、视频对应的付费信息，仅专辑、视频使用
     */
    private VodInfo vodInfo;

    /**
     * 鉴权不通过时有试看信息；
     * 注意：试看信息只能代表该内容是否支持试看，以及能够试看的时间，用户端需要检查试看截止时间是否已过期
     */
    private VodTryPlayInfo tryInfo;

    /**
     * 分终端付费使用该字段获取视频专辑的付费信息
     */
    private ChargeInfo chargeInfo;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUinfo() {
        return uinfo;
    }

    public void setUinfo(String uinfo) {
        this.uinfo = uinfo;
    }

    public List<String> getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(List<String> vipInfo) {
        this.vipInfo = vipInfo;
    }

    public Map<String, List<String>> getVipTypeInfo() {
        return vipTypeInfo;
    }

    public void setVipTypeInfo(Map<String, List<String>> vipTypeInfo) {
        this.vipTypeInfo = vipTypeInfo;
    }

    public List<Integer> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<Integer> packageInfo) {
        this.packageInfo = packageInfo;
    }

    public VodInfo getVodInfo() {
        return vodInfo;
    }

    public void setVodInfo(VodInfo vodInfo) {
        this.vodInfo = vodInfo;
    }

    public VodTryPlayInfo getTryInfo() {
        return tryInfo;
    }

    public void setTryInfo(VodTryPlayInfo tryInfo) {
        this.tryInfo = tryInfo;
    }

    public Long getTvodRts() {
        return tvodRts;
    }

    public void setTvodRts(Long tvodRts) {
        this.tvodRts = tvodRts;
    }

    public ChargeInfo getChargeInfo() {
        return chargeInfo;
    }

    public void setChargeInfo(ChargeInfo chargeInfo) {
        this.chargeInfo = chargeInfo;
    }

    /**
     * 专辑、视频对应的付费信息，仅专辑、视频使用
     * @author kevinyi
     */
    public static class VodInfo implements Serializable {

        /**
         * 各终端价格信息，格式:终端类型 + ":" + 价格.如141001: 9.5, 141002: 9, 141003: 8
         * 终端类型定义见{@link com.letv.itv.v2.tp.vip.VipTpConstant.BossTerminalType}
         */
        private Map<String, String> chargeTerminalPrice;

        /**
         * 付费信息
         */
        private VodChargeConfig video;

        /**
         * 定价信息
         * @return
         */
        private VodChargeInfo charge;

        public Map<String, String> getChargeTerminalPrice() {
            return chargeTerminalPrice;
        }

        public void setChargeTerminalPrice(Map<String, String> chargeTerminalPrice) {
            this.chargeTerminalPrice = chargeTerminalPrice;
        }

        public VodChargeConfig getVideo() {
            return video;
        }

        public void setVideo(VodChargeConfig video) {
            this.video = video;
        }

        public VodChargeInfo getCharge() {
            return charge;
        }

        public void setCharge(VodChargeInfo charge) {
            this.charge = charge;
        }
    }

    /**
     * 付费信息
     * @author kevinyi
     */
    public static class VodChargeConfig implements Serializable {

        /**
         * 付费规则 1：专辑单视频付费（例如：电影），2：专辑多视频付费（例如：电视剧），3：视频付费，对应体育等没有专辑id
         */
        private Integer chargeRule;

        /**
         * 付费类型 0：点播 1：点播活包月 2：包月 3：免费但TV包月收费
         */
        private Integer chargeType;

        /**
         * 是否付费 0：免费，1：付费
         */
        private Integer isCharge;

        /**
         * 1：支持使用观影券，0：不支持使用观影券
         */
        private Integer isSupportTicket;

        public Integer getIsSupportTicket() {
            return isSupportTicket;
        }

        public void setIsSupportTicket(Integer isSupportTicket) {
            this.isSupportTicket = isSupportTicket;
        }

        public Integer getChargeRule() {
            return chargeRule;
        }

        public void setChargeRule(Integer chargeRule) {
            this.chargeRule = chargeRule;
        }

        public Integer getChargeType() {
            return chargeType;
        }

        public void setChargeType(Integer chargeType) {
            this.chargeType = chargeType;
        }

        public Integer getIsCharge() {
            return isCharge;
        }

        public void setIsCharge(Integer isCharge) {
            this.isCharge = isCharge;
        }

    }

    /**
     * 定价信息
     * @author liudaojie
     */
    public static class VodChargeInfo {
        private String app; // 第三方产品ID(例如苹果)
        private Integer chargeId; // 定价id
        private Integer memberDiscounts; // 会员优惠 0：原价 1：会员半价
        private Integer validTime; // 有效天数
        private String chargeName; // 定价名称

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public Integer getChargeId() {
            return chargeId;
        }

        public void setChargeId(Integer chargeId) {
            this.chargeId = chargeId;
        }

        public Integer getMemberDiscounts() {
            return memberDiscounts;
        }

        public void setMemberDiscounts(Integer memberDiscounts) {
            this.memberDiscounts = memberDiscounts;
        }

        public Integer getValidTime() {
            return validTime;
        }

        public void setValidTime(Integer validTime) {
            this.validTime = validTime;
        }

        public String getChargeName() {
            return chargeName;
        }

        public void setChargeName(String chargeName) {
            this.chargeName = chargeName;
        }
    }

    /**
     * 鉴权不通过时有试看信息;
     * 注意：试看信息只能代表该内容是否支持试看，以及能够试看的时间，用户端需要检查试看截止时间是否已过期
     * @author kevinyi
     */
    public static class VodTryPlayInfo implements Serializable {

        /**
         * 试看开始时间，单位毫秒
         */
        private Long tryStartTime;

        /**
         * 试看截止时间，单位毫秒
         */
        private Long tryEndTime;

        /**
         * 当前服务器时间，单位毫秒
         */
        private Long serverTime;

        public Long getTryStartTime() {
            return tryStartTime;
        }

        public void setTryStartTime(Long tryStartTime) {
            this.tryStartTime = tryStartTime;
        }

        public Long getTryEndTime() {
            return tryEndTime;
        }

        public void setTryEndTime(Long tryEndTime) {
            this.tryEndTime = tryEndTime;
        }

        public Long getServerTime() {
            return serverTime;
        }

        public void setServerTime(Long serverTime) {
            this.serverTime = serverTime;
        }

    }

    /**
     * 分终端付费使用该字段获取视频专辑的付费信息数据结构
     */
    public static class ChargeInfo implements Serializable {
        /**
         * 专辑付费名
         */
        private String name;
        /**
         * 专辑id
         */
        private String pid;
        /**
         * 状态 0:未发布 1： 已发布，2：定时发布
         */
        private String status;
        /**
         * 分端付费配置
         */
        private List<com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo> terminalCharges;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo> getTerminalCharges() {
            return terminalCharges;
        }

        public void setTerminalCharges(List<com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo> terminalCharges) {
            this.terminalCharges = terminalCharges;
        }
    }
}
