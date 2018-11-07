package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 查询当前登录用户是否有未领取的超级手机赠送机卡绑定TV会员请求响应封装类
 * @author
 * @since Mar 29, 2015
 */
public class GetPresentDeviceBindTpResponse {

    /**
     * 响应状态码，0--响应成功，1--响应失败；原响应中为int型，这里使用String接收
     */
    private String code;

    private PresentDeviceBindValuesTpResponse values;

    /**
     * 是否响应成功
     * @return
     */
    public boolean isSucceed() {
        if (!"0".equals(this.code) || this.values == null) {
            return false;
        }
        return true;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PresentDeviceBindValuesTpResponse getValues() {
        return this.values;
    }

    public void setValues(PresentDeviceBindValuesTpResponse values) {
        this.values = values;
    }

    /**
     * 未领取的超级手机赠送机卡绑定TV会员汇总信息
     * @author
     * @since Mar 29, 2015
     */
    public static class PresentDeviceBindValuesTpResponse {

        /**
         * 错误信息，code为1时有值
         */
        private String msg;

        private String userId;

        private String presentDeviceInfo;

        /**
         * 未领取的机卡时长列表
         */
        private List<PresentDeviceBindTpResponse> presentList;

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPresentDeviceInfo() {
            return this.presentDeviceInfo;
        }

        public void setPresentDeviceInfo(String presentDeviceInfo) {
            this.presentDeviceInfo = presentDeviceInfo;
        }

        public List<PresentDeviceBindTpResponse> getPresentList() {
            return this.presentList;
        }

        public void setPresentList(List<PresentDeviceBindTpResponse> presentList) {
            this.presentList = presentList;
        }

    }

    /**
     * 未领取的超级手机赠送机卡绑定TV会员具体信息
     * @author
     * @since Mar 29, 2015
     */
    public static class PresentDeviceBindTpResponse {

        /**
         * 当前未领取的机卡信息id
         */
        private String id;

        /**
         * 当前机卡信息状态，0--未领取
         */
        private String status;

        /**
         * 
         */
        private String userId;

        /**
         * 机卡时长类型，可参考VipTpConstant#SVIP_*
         */
        private String presentType;

        /**
         * 当前领取设备的mac（电视）或imei（手机）
         */
        private String presentDeviceInfo;

        /**
         * 机卡激活时间（手机上）
         */
        private Long createTime;

        /**
         * 
         */
        private String presentProductId;

        /**
         * 未领取时长
         */
        private Integer presentDuration;

        /**
         * 机卡失效时间
         */
        private Long presentExpiredTime;

        /**
         * 
         */
        private String deviceInfo;

        /**
         * 
         */
        private String presentTime;

        /**
         * 
         */
        private String presentDeviceKey;

        private Integer vipType;// 会员类型，1--乐次元，2--全屏，3--体育

        /**
         * 判断当前赠送套餐是否可领取
         * @return
         */
        public boolean isAvailable() {
            return "0".equals(this.status) && this.createTime != null && this.createTime > 0
                    && this.presentDuration != null && this.presentDuration > 0;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPresentType() {
            return this.presentType;
        }

        public void setPresentType(String presentType) {
            this.presentType = presentType;
        }

        public String getPresentDeviceInfo() {
            return this.presentDeviceInfo;
        }

        public void setPresentDeviceInfo(String presentDeviceInfo) {
            this.presentDeviceInfo = presentDeviceInfo;
        }

        public Long getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getPresentProductId() {
            return this.presentProductId;
        }

        public void setPresentProductId(String presentProductId) {
            this.presentProductId = presentProductId;
        }

        public Integer getPresentDuration() {
            return this.presentDuration;
        }

        public void setPresentDuration(Integer presentDuration) {
            this.presentDuration = presentDuration;
        }

        public Long getPresentExpiredTime() {
            return this.presentExpiredTime;
        }

        public void setPresentExpiredTime(Long presentExpiredTime) {
            this.presentExpiredTime = presentExpiredTime;
        }

        public String getDeviceInfo() {
            return this.deviceInfo;
        }

        public void setDeviceInfo(String deviceInfo) {
            this.deviceInfo = deviceInfo;
        }

        public String getPresentTime() {
            return this.presentTime;
        }

        public void setPresentTime(String presentTime) {
            this.presentTime = presentTime;
        }

        public String getPresentDeviceKey() {
            return this.presentDeviceKey;
        }

        public void setPresentDeviceKey(String presentDeviceKey) {
            this.presentDeviceKey = presentDeviceKey;
        }

        public Integer getVipType() {
            return this.vipType;
        }

        public void setVipType(Integer vipType) {
            this.vipType = vipType;
        }

    }
}
