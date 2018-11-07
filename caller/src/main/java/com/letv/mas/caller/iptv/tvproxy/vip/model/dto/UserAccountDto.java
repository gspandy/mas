package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

/**
 * 用户会员账户信息封装类
 * @author
 */
public class UserAccountDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 有效时间
     */
    private String validDate;

    /**
     * 乐点余额
     */
    private Integer letvPoint;

    /**
     * 用户头像
     */
    private String picture;

    /**
     * 显示昵称
     */
    private String displayName;

    /**
     * 用户是否已经领取过机卡时长，当前会员有效期是否包含机卡时长，0--非绑定，不包含，1--绑定，包含
     */
    private Integer isbind;

    /**
     * “机卡绑定”新增需求，鉴定当前用户是否领取过其他机器的绑定套餐;0--无效数据，1--已领取，2--未领取
     */
    private Integer hasBindOtherDevice;

    /**
     * “机卡绑定”新增需求，鉴定当前用户是否领取过当前机器的绑定套餐;0--无效数据，1--已领取，2--未领取
     */
    @Deprecated
    private Integer hasBindCurrentDevice;

    /**
     * vip类别，0--非会员，1--普通vip（PC会员或APP会员）,2--高级vip（TV会员）
     */
    private Integer isvip;

    /**
     * 高级会员的有效期，单位-毫秒 有数值，标明曾经是高级会员或当前是会员 无数值，标明从来都不是高级会员
     */
    private Long seniorendtime;

    /**
     * 影视vip类别，0--非会员，1--体育vip，2--过期体育vip
     */
    private Integer isSportVip;

    /**
     * 体育会员到期时间
     */
    private Long sportendtime;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getValidDate() {
        return this.validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getLetvPoint() {
        return this.letvPoint;
    }

    public void setLetvPoint(Integer letvPoint) {
        this.letvPoint = letvPoint;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIsbind() {
        return this.isbind;
    }

    public void setIsbind(Integer isbind) {
        this.isbind = isbind;
    }

    public Integer getHasBindOtherDevice() {
        return this.hasBindOtherDevice;
    }

    public void setHasBindOtherDevice(Integer hasBindOtherDevice) {
        this.hasBindOtherDevice = hasBindOtherDevice;
    }

    public Integer getHasBindCurrentDevice() {
        return this.hasBindCurrentDevice;
    }

    public void setHasBindCurrentDevice(Integer hasBindCurrentDevice) {
        this.hasBindCurrentDevice = hasBindCurrentDevice;
    }

    public Integer getIsvip() {
        return this.isvip;
    }

    public void setIsvip(Integer isvip) {
        this.isvip = isvip;
    }

    public Long getSeniorendtime() {
        return seniorendtime;
    }

    public void setSeniorendtime(Long seniorendtime) {
        this.seniorendtime = seniorendtime;
    }

    public Integer isSportVip() {
        return isSportVip;
    }

    public void setIsSportVip(Integer isSportVip) {
        this.isSportVip = isSportVip;
    }

    public Long getSportendtime() {
        return sportendtime;
    }

    public void setSportendtime(Long sportendtime) {
        this.sportendtime = sportendtime;
    }
}