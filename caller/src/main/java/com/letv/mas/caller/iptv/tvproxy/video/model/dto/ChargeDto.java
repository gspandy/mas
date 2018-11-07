package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

public class ChargeDto {

    /**
     * 单点付费
     */
    private String price;

    /**
     * 付费类型 1支持单点付费
     */
    private Integer payType;

    /**
     * 海报图，单点支付页面使用
     */
    private String poster;

    /**
     * 有效时长
     */
    private Integer expiredTime;

    private String areaName;
    private String director;

    /**
     * 当前免费码流 code :1300 name:高清
     */
    private Stream freeStream;

    private Integer userTicketSize;// 直播券数量

    /**
     * 球队包提示：您已购买了XX球队包，直播还未开始，请耐心等待
     */
    private String team;// 队伍包信息（如果购买多包，则返回一个）

    /**
     * 赛季包提示：您已购买了XX赛季包，直播还未开始，请耐心等待
     */
    private String season;// 返回赛季信息

    /**
     * -1 获取直播状态失败，异常
     * 1 未开始
     * 2 进行中
     * 3 已结束
     * 4 延期——不使用
     * 5 取消——不使用
     */
    private Integer liveStatus;

    /**
     * 1003 参数校验类
     * 1002 直播信息不存在
     * 1001 直播时间为空
     * 1004 用户没有权限
     * 1011 赛季包鉴权成功，但直播未开始
     * 1012 球队包鉴权成功，但直播未开始
     * 1013 场次卷鉴权成功，但直播未开始
     */
    private String code;

    private String msg;

    /**
     * 鉴权通过所使用的卷类型（1: 直播卷 3:赛季包 4：球队包）
     */
    private Integer ordertype;

    /**
     * 鉴权通过所使用的卷类型是球队包时(ordertype = 4)返回购买球队的名称
     */
    private String teamName;

    private String status;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Stream getFreeStream() {
        return freeStream;
    }

    public void setFreeStream(Stream freeStream) {
        this.freeStream = freeStream;
    }

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Integer getUserTicketSize() {
        return userTicketSize;
    }

    public void setUserTicketSize(Integer userTicketSize) {
        this.userTicketSize = userTicketSize;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
