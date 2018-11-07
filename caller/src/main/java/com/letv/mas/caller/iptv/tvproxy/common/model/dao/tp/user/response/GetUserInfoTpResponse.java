package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

/**
 * 获取用户基本信息请求第三方接口结果封装类
 * @author KevinYi
 */
public class GetUserInfoTpResponse {

    /**
     * 响应状态，1--成功，0--失败
     */
    private Integer status;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 信息
     */
    private String message;

    /**
     * 返回数据格式，一般为"json"
     */
    private String responseType;

    /**
     * 用户token
     */
    private String tv_token;

    /**
     * 用户具体信息
     */
    private UserInfoDetailTpResponse bean;

    /**
     * 判断是否响应成功
     * @return
     */
    public boolean isSuccess() {
        return this.status != null && 1 == this.status && this.bean != null;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseType() {
        return this.responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getTv_token() {
        return this.tv_token;
    }

    public void setTv_token(String tv_token) {
        this.tv_token = tv_token;
    }

    public UserInfoDetailTpResponse getBean() {
        return this.bean;
    }

    public void setBean(UserInfoDetailTpResponse bean) {
        this.bean = bean;
    }

    public static class UserInfoDetailTpResponse {

        /**
         * 用户中心uid
         */
        private String uid;

        /**
         * 用户名
         */
        private String username;

        /**
         * 用户状态，1--可以登录，0--禁止登陆
         */
        private String status;

        /**
         * 性别，0--保密，1--男，2--女
         */
        private String gender;

        /**
         * 
         */
        private String qq;

        /**
         * 注册IP
         */
        private String registIp;

        /**
         * 注册时间，""
         */
        private String registTime;

        /**
         * 最后修改时间
         */
        private String lastModifyTime;

        /**
         * 出生日期
         */
        private String birthday;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 
         */
        private String msn;

        /**
         * 注册来源 目前 www 或者 vip 或者 box 必需，一般为"my"
         */
        private String registService;

        /**
         * 邮件
         */
        private String email;

        /**
         * 手机
         */
        private String mobile;

        /**
         * 地址所在省份
         */
        private String province;

        /**
         * 地址所在城市
         */
        private String city;

        /**
         * 邮编
         */
        private String postCode;

        /**
         * 地址
         */
        private String address;

        /**
         * 
         */
        @Deprecated
        private String mac;

        /**
         * 头像图片url，一般为4幅不同尺寸图片，使用英文逗号分隔
         */
        private String picture;

        /**
         * 
         */
        private String name;

        /**
         * 上次更新密码时间，可转换成long的字符串，精确到秒
         */
        private String lastModifyPwdTime;

        /**
         * 
         */
        @Deprecated
        private String contactEmail;

        /**
         * 
         */
        @Deprecated
        private String delivery;
        /**
         * 
         */
        @Deprecated
        private String point;

        /**
         * 
         */
        @Deprecated
        private Integer level_id;

        /**
         * 
         */
        @Deprecated
        private Integer isvip;

        /**
         * 
         */
        private String education;

        /**
         * 
         */
        private String industry;

        /**
         * 
         */
        private String job;

        /**
         * 
         */
        private String income;

        /**
         * 
         */
        @Deprecated
        private String lastLoginTime;

        /**
         * 
         */
        @Deprecated
        private String lastLoginIp;

        public String getUid() {
            return this.uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGender() {
            return this.gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getQq() {
            return this.qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getRegistIp() {
            return this.registIp;
        }

        public void setRegistIp(String registIp) {
            this.registIp = registIp;
        }

        public String getRegistTime() {
            return this.registTime;
        }

        public void setRegistTime(String registTime) {
            this.registTime = registTime;
        }

        public String getLastModifyTime() {
            return this.lastModifyTime;
        }

        public void setLastModifyTime(String lastModifyTime) {
            this.lastModifyTime = lastModifyTime;
        }

        public String getBirthday() {
            return this.birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getNickname() {
            return this.nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMsn() {
            return this.msn;
        }

        public void setMsn(String msn) {
            this.msn = msn;
        }

        public String getRegistService() {
            return this.registService;
        }

        public void setRegistService(String registService) {
            this.registService = registService;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return this.mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince() {
            return this.province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return this.city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostCode() {
            return this.postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPicture() {
            return this.picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastModifyPwdTime() {
            return this.lastModifyPwdTime;
        }

        public void setLastModifyPwdTime(String lastModifyPwdTime) {
            this.lastModifyPwdTime = lastModifyPwdTime;
        }

        public String getPoint() {
            return this.point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getEducation() {
            return this.education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getIndustry() {
            return this.industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getJob() {
            return this.job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getIncome() {
            return this.income;
        }

        public void setIncome(String income) {
            this.income = income;
        }

    }
}
