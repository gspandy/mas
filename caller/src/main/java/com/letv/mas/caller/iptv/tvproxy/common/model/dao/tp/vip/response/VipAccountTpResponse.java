package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

/**
 * 会员账户信息请求响应封装类
 * @author KevinYi
 */
public class VipAccountTpResponse {

    /**
     * 用户id
     */
    private String userid;

    /**
     * 普通会员的有效期，单位-毫秒
     */
    private Long endtime;

    /**
     * 高级会员的有效期，单位-毫秒 有数值，标明曾经是高级会员或当前是会员 无数值，标明从来都不是高级会员
     */
    private Long seniorendtime;

    /**
     * vip类别，0：非会员，1：普通vip,2:高级vip
     */
    private Integer isvip;

    /**
     * 是否包含本机时长，0--非绑定，不包含，1--绑定
     */
    private Integer isbind;

    /**
     * 用户电话
     */
    private String tel;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 体育会员到期时间
     */
    private Long sportendtime;

    /**
     * 影视vip类别，0--非会员，1--体育vip，2--过期体育vip
     */
    private Integer isSportVip;

    /**
     * 影视vip类别，0--非会员，1--乐次元影视vip，2--超级影视vip，3--过期vip
     */
    private Head head;

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    private Integer isMovievip;

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public Long getSeniorendtime() {
        return this.seniorendtime;
    }

    public void setSeniorendtime(Long seniorendtime) {
        this.seniorendtime = seniorendtime;
    }

    public Integer getIsvip() {
        return this.isvip;
    }

    public void setIsvip(Integer isvip) {
        this.isvip = isvip;
    }

    public Integer getIsbind() {
        return this.isbind;
    }

    public void setIsbind(Integer isbind) {
        this.isbind = isbind;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSportendtime() {
        return this.sportendtime;
    }

    public void setSportendtime(Long sportendtime) {
        this.sportendtime = sportendtime;
    }

    public Integer isSportVip() {
        return this.isSportVip;
    }

    public void setIsSportVip(Integer isSportVip) {
        this.isSportVip = isSportVip;
    }

    public Integer isMovievip() {
        return isMovievip;
    }

    public void setIsMovievip(Integer isMovievip) {
        this.isMovievip = isMovievip;
    }

    public static class Head {

        private String msg;

        private Integer ret;

        public String getMsg() {
            return msg;
        }

        public Integer getRet() {
            return ret;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setRet(Integer ret) {
            this.ret = ret;
        }

    }

}