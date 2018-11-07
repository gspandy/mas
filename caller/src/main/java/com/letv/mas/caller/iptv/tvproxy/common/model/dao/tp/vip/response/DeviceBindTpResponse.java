package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.util.List;

/**
 * 机卡绑定需求，鉴别该机器是否有未领取的机卡绑定套餐（调用设备绑定查询接口）的请求返回结果封装类
 * @author yikun
 * @since 2014-08-07
 */
public class DeviceBindTpResponse {

    private Integer code; // 状态，0--成功，1--失败
    private DeviceBindConent values; // 返回结果具体内容

    public DeviceBindTpResponse() {
        super();
    }

    public DeviceBindTpResponse(Integer code, DeviceBindConent values) {
        super();
        this.code = code;
        this.values = values;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DeviceBindConent getValues() {
        return this.values;
    }

    public void setValues(DeviceBindConent values) {
        this.values = values;
    }

    public static class DeviceBindConent {
        private String deviceKey; // 设备唯一标识
        private Boolean isDeviceActive; // 设备是否激活，true--已激活；false--未激活
        private String bindUserid; // 设备绑定的用户ID，isDeviceActive为true时有值
        private Integer bindtime; // 机卡绑定时长，单位为天
        private String bindUname; // 用户昵称，如"likefish007"
        private String bindEmail; // 用户邮箱
        private String bindPhone; // 用户手机号
        private Long activetime; // 激活时间，精确到毫秒
        private String msg; // code为1时返回失败原因
        private List<DeviceInfo> items;// 列表

        public DeviceBindConent() {
            super();
        }

        public DeviceBindConent(String deviceKey, Boolean isDeviceActive, String bindUserid, Integer bindtime,
                String bindUname, String bindEmail, String bindPhone, Long activetime) {
            super();
            this.deviceKey = deviceKey;
            this.isDeviceActive = isDeviceActive;
            this.bindUserid = bindUserid;
            this.bindtime = bindtime;
            this.bindUname = bindUname;
            this.bindEmail = bindEmail;
            this.bindPhone = bindPhone;
            this.activetime = activetime;
        }

        public String getDeviceKey() {
            return this.deviceKey;
        }

        public void setDeviceKey(String deviceKey) {
            this.deviceKey = deviceKey;
        }

        public Boolean getIsDeviceActive() {
            return this.isDeviceActive;
        }

        public void setIsDeviceActive(Boolean isDeviceActive) {
            this.isDeviceActive = isDeviceActive;
        }

        public String getBindUserid() {
            return this.bindUserid;
        }

        public void setBindUserid(String bindUserid) {
            this.bindUserid = bindUserid;
        }

        public Integer getBindtime() {
            return this.bindtime;
        }

        public void setBindtime(Integer bindtime) {
            this.bindtime = bindtime;
        }

        public String getBindUname() {
            return this.bindUname;
        }

        public void setBindUname(String bindUname) {
            this.bindUname = bindUname;
        }

        public String getBindEmail() {
            return this.bindEmail;
        }

        public void setBindEmail(String bindEmail) {
            this.bindEmail = bindEmail;
        }

        public String getBindPhone() {
            return this.bindPhone;
        }

        public void setBindPhone(String bindPhone) {
            this.bindPhone = bindPhone;
        }

        public Long getActivetime() {
            return this.activetime;
        }

        public void setActivetime(Long activetime) {
            this.activetime = activetime;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<DeviceInfo> getItems() {
            return this.items;
        }

        public void setItems(List<DeviceInfo> items) {
            this.items = items;
        }

        public static class DeviceInfo {
            private Integer vipType;// 会员类型，1--乐次元，2--全屏，3--体育
            private String vipTypeName;// 会员类型名称
            private Integer hasAuth;// 是否有操作权限 1是 0否
            private Boolean isActived;// 乐次元是否已经激活
            private Integer bindDuration;// 乐次元绑定时长 单位为天
            private Integer cardDuration;// 乐次元兑换时长，单位为天
            private String vipEndTime;// 机卡会员结束时间
            private Long activeTime; // 乐次元激活时间 有值为long 无值为0
            private Integer status; // 1.未领取、2.已领取、3.已退货

            public Integer getVipType() {
                return this.vipType;
            }

            public void setVipType(Integer vipType) {
                this.vipType = vipType;
            }

            public String getVipTypeName() {
                return this.vipTypeName;
            }

            public void setVipTypeName(String vipTypeName) {
                this.vipTypeName = vipTypeName;
            }

            public Integer getHasAuth() {
                return this.hasAuth;
            }

            public void setHasAuth(Integer hasAuth) {
                this.hasAuth = hasAuth;
            }

            public Boolean getIsActived() {
                return this.isActived;
            }

            public void setIsActived(Boolean isActived) {
                this.isActived = isActived;
            }

            public Integer getBindDuration() {
                return this.bindDuration;
            }

            public void setBindDuration(Integer bindDuration) {
                this.bindDuration = bindDuration;
            }

            public Integer getCardDuration() {
                return this.cardDuration;
            }

            public void setCardDuration(Integer cardDuration) {
                this.cardDuration = cardDuration;
            }

            public String getVipEndTime() {
                return this.vipEndTime;
            }

            public void setVipEndTime(String vipEndTime) {
                this.vipEndTime = vipEndTime;
            }

            public Long getActiveTime() {
                return this.activeTime;
            }

            public void setActiveTime(Long activeTime) {
                this.activeTime = activeTime;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

        }

    }
}
