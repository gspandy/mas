package com.letv.mas.caller.iptv.tvproxy.video.authentication;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 播放鉴权(v2)实体类
 * Created by liudaojie on 2016/12/6.
 */
public class PlayAuthV2 {

    /**
     * 0：代表鉴权不通过
     * 1：代表鉴权通过
     * 2：代表鉴权通过，但设备未授权
     */
    private Integer status;

    /**
     * 请求失败时返回
     */
    private Integer errorCode;

    /**
     * 错误信息，请求失败（返回errorCode）时返回
     */
    private String msg;

    /**
     * 1:可试看范围内返回token（直播鉴权试看返回，点播鉴权试看不返回)
     * 2：轮播台，一直返回token
     */
    private String token;

    /**
     * 专辑、视频单点时剩余的可使用截止时间（只有点播才有返回值），单位毫秒
     */
    private Long playEndTime;

    /**
     * 调度提升qos级别需要返回uinfo
     */
    private String uinfo;

    /**
     * 参与url拼接的userId，该参数十分重要，登录状态下必须赋值
     */
    private String tokenUserId;

    // /**
    // * 是否支持单点
    // */
    // private Integer isPay;

    /**
     * 是否被封禁，0--否，1--是
     */
    private String isForbidden;

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
     * 鉴权状态:通过,但设备没有授权
     */
    public static final int STATUS_PASS_DEVICE_NOT_AUTH = 2;

    /**
     * 鉴权状态:通过
     */
    public static final int STATUS_PASS = 1;
    /**
     * 鉴权状态:不通过
     */
    public static final int STATUS_UNPASS = 0;

    /**
     * 用户被封禁
     */
    public static final String FORBIDDEN_TRUE = "1";

    /**
     * 用户未被封禁
     */
    public static final String FORBIDDEN_FALSE = "0";

    public PlayAuthV2() {

    }

    public PlayAuthV2(Integer status) {
        // super(status);
        this.status = status;
    }

    /**
     * 判断是否鉴权通过(包括设备授权和未授权的情况)
     * @return
     *         true:鉴权通过,设备未授权也认为鉴权通过
     *         false:鉴权不通过
     */
    public boolean isAuthenticationPass() {
        return this.status != null && (status == STATUS_PASS || status == STATUS_PASS_DEVICE_NOT_AUTH) ? true : false;
    }

    /**
     * 复制对象(深复制),将sourcePlayAuth中的属性全部复制到this中
     * @param sourcePlayAuth
     *            源鉴权bean
     */
    public void copyProperties(PlayAuthV2 sourcePlayAuth) {
        if (sourcePlayAuth != null) {
            this.setIsForbidden(sourcePlayAuth.getIsForbidden()); // 设置用户未被封禁
            this.setStatus(sourcePlayAuth.getStatus());
            if (sourcePlayAuth.getPlayEndTime() != null) {
                this.setPlayEndTime(sourcePlayAuth.getPlayEndTime());
            }
            this.setToken(sourcePlayAuth.getToken());
            this.setTokenUserId(sourcePlayAuth.getTokenUserId()); // userId从commonParam里获取
            this.setUinfo(sourcePlayAuth.getUinfo());
            this.setErrorCode(sourcePlayAuth.getErrorCode());
            this.setMsg(sourcePlayAuth.getMsg());
            this.setVipTypeInfo(sourcePlayAuth.getVipTypeInfo());
            this.setVipInfo(sourcePlayAuth.getVipInfo());
            this.setPackageInfo(sourcePlayAuth.getPackageInfo());

            /*
             * 设置VodInfo
             */
            if (sourcePlayAuth.getVodInfo() != null) {
                VodInfo vodInfoTp = sourcePlayAuth.getVodInfo();
                if (vodInfoTp != null) {
                    PlayAuthV2.VodInfo vodInfo = new PlayAuthV2.VodInfo();
                    this.setVodInfo(vodInfo);

                    vodInfo.setChargeTerminalPrice(vodInfoTp.getChargeTerminalPrice());
                    /*
                     * 设置定价信息
                     */
                    if (vodInfoTp.getCharge() != null) {
                        PlayAuthV2.VodChargeInfo vodChargeInfo = new PlayAuthV2.VodChargeInfo();
                        vodInfo.setCharge(vodChargeInfo);

                        VodChargeInfo vodChargeInfoTp = vodInfoTp.getCharge();
                        vodChargeInfo.setApp(vodChargeInfoTp.getApp());
                        vodChargeInfo.setChargeId(vodChargeInfoTp.getChargeId());
                        vodChargeInfo.setMemberDiscounts(vodChargeInfoTp.getMemberDiscounts());
                        vodChargeInfo.setMemberPrice(vodChargeInfoTp.getMemberPrice());
                        vodChargeInfo.setValidTime(vodChargeInfoTp.getValidTime());
                        vodChargeInfo.setChargeName(vodChargeInfoTp.getChargeName());
                    }
                    /*
                     * 设置付费信息
                     */
                    if (vodInfoTp.getVideo() != null) {
                        PlayAuthV2.VodChargeConfig vodChargeConfig = new PlayAuthV2.VodChargeConfig();
                        vodInfo.setVideo(vodChargeConfig);

                        VodChargeConfig vodChargeConfigTp = vodInfoTp.getVideo();
                        vodChargeConfig.setChargeRule(vodChargeConfigTp.getChargeRule());
                        vodChargeConfig.setChargeType(vodChargeConfigTp.getChargeType());
                        vodChargeConfig.setIsCharge(vodChargeConfigTp.getIsCharge());
                        vodChargeConfig.setIsSupportTicket(vodChargeConfigTp.getIsSupportTicket());
                    }
                }
            }
            /*
             * 设置试看信息
             */
            if (sourcePlayAuth.getTryInfo() != null) {
                VodTryPlayInfo vodTryPlayInfo = new VodTryPlayInfo();
                this.setTryInfo(vodTryPlayInfo);

                VodTryPlayInfo vodTryPlayInfoTp = sourcePlayAuth.getTryInfo();
                vodTryPlayInfo.setTryStartTime(vodTryPlayInfoTp.getTryStartTime());
                vodTryPlayInfo.setTryEndTime(vodTryPlayInfoTp.getTryEndTime());
                vodTryPlayInfo.setServerTime(vodTryPlayInfoTp.getServerTime());
            }
        }
    }

    /**
     * 专辑、视频对应的付费信息，仅专辑、视频使用
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
     */
    public static class VodChargeInfo {
        private String app; // 第三方产品ID(例如苹果)
        private Integer chargeId; // 定价id
        private Integer memberDiscounts; // 会员优惠 0：原价 1：会员半价
        private Integer validTime; // 有效天数
        private String chargeName; // 定价名称
        private String memberPrice; // 会员价

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

        public String getMemberPrice() {
            return memberPrice;
        }

        public void setMemberPrice(String memberPrice) {
            this.memberPrice = memberPrice;
        }
    }

    /**
     * 鉴权不通过时有试看信息;
     * 注意：试看信息只能代表该内容是否支持试看，以及能够试看的时间，用户端需要检查试看截止时间是否已过期
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public String getTokenUserId() {
        return tokenUserId;
    }

    public void setTokenUserId(String tokenUserId) {
        this.tokenUserId = tokenUserId;
    }

    // public Integer getIsPay() {
    // return isPay;
    // }
    //
    // public void setIsPay(Integer isPay) {
    // this.isPay = isPay;
    // }

    public String getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(String isForbidden) {
        this.isForbidden = isForbidden;
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

    public Long getPlayEndTime() {
        return playEndTime;
    }

    public void setPlayEndTime(Long playEndTime) {
        this.playEndTime = playEndTime;
    }
}
