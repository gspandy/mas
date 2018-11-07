package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

public class VIPInfoDto {
    private String userid;
    private Long endtime; // 普通会员的有效期
    /**
     * 高级会员的有效期
     * 有数值，标明曾经是高级会员或当前是会员
     * 无数值，标明从来都不是高级会员
     */
    private Long seniorendtime;
    private Integer isvip;// vip类别，0：非会员，1：普通vip,2:高级vip
    private String tel; // 用户电话，“机卡绑定”新接口返回值
    private String email; // 用户邮箱，“机卡绑定”新接口返回值
    private Integer isbind; // 是否包含本机时长，0--非绑定，不包含，1--绑定，包含，“机卡绑定”新接口返回值
    private Long sportendtime;// 体育会员到期时间
    private Integer isSportVip; // 0:非vip 1:体育会员 2:过期体育会员

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

    public Long getSportendtime() {
        return this.sportendtime;
    }

    public void setSportendtime(Long sportendtime) {
        this.sportendtime = sportendtime;
    }

    public Integer getIsSportVip() {
        return this.isSportVip;
    }

    public void setIsSportVip(Integer isSportVip) {
        this.isSportVip = isSportVip;
    }

    @Override
    public String toString() {
        return "VIPInfoDto [userid=" + this.userid + ", endtime=" + this.endtime + ", seniorendtime="
                + this.seniorendtime + ", isvip=" + this.isvip + ", tel=" + this.tel + ", email=" + this.email
                + ", isbind=" + this.isbind + "]";
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

    public Integer getIsbind() {
        return this.isbind;
    }

    public void setIsbind(Integer isbind) {
        this.isbind = isbind;
    }

}
