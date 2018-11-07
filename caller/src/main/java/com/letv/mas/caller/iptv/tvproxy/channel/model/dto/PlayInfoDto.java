package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.video.model.dto.Stream;

import java.util.List;

public class PlayInfoDto extends BaseDto {

    /**
     * 播放内容类型，1—教学视频,2--915发布会，3--924走红毯，4--928正式直播
     */
    private Integer type;

    /**
     * 视频类型，0—电影，1—直播
     */
    private Integer vtype;

    /**
     * 视频内容类型，0—预告的内容，1--正式上映的内容
     */
    private Integer ctype;

    /**
     *
     */
    private String streamlist;

    /**
     * 是2D或者3D类型，1--2D直播，2--3D直播
     */
    private Integer livetype;

    /**
     * 是否播放广告，0-不播放，1-播放，默认不播放
     */
    private Integer playAd = 0;

    /**
     * 直播状态，1--未开始, 2--直播中 ,3--回看, 4--已结束
     */
    private Integer liveStatus;

    /**
     * 播放按钮是否可点，0--不可点，1--可点，默认不可点
     */
    private Integer enable = 0;

    /**
     * 是否免费 0—否，1--是
     */
    private Integer isFree;

    /**
     * 0-未付费或者无权限，1-已付费或者有权限
     */
    private Integer payStatus;

    /**
     * 价格
     */
    private String price;

    /**
     * 会员价格
     */
    private String vipPrice;

    /**
     * 普通价格
     */
    private String commonPrice;

    /**
     * 背景图片
     */
    private String bgImg;

    /**
     * 场次,用于购买直播时传的产品id
     */
    private String screenings;

    /**
     * 唯一标识，直播id或者教学视频id
     */
    private String playid;

    /**
     * 直播标题
     */
    private String liveName;

    /**
     * 直播开始时间，单位毫秒
     */
    private String startTime;

    /**
     * 直播时间，普通日期时间格式
     */
    private String liveTime;

    /**
     * 直播结束时间，单位毫秒
     */
    private String endTime;

    /**
     * 直播结束时间，日期时间格式
     */
    private String liveEndTime;

    /**
     * 播放地址
     */
    private String playUrl;

    /**
     * 按钮文案，直播时显示进入直播室，教学视频时显示视频标题
     */
    private String title;

    /**
     * token信息，播放鉴权时使用；注意，该值不一定返回；当用户购买成功，但直播未开始时，该值可能不返回
     */
    private String token;

    /**
     * 会员登录信息，播放鉴权使用；非必填，token有值时，本字段也不一定有值
     */
    private String uinfo;

    /**
     * 需要提示版权信息
     */
    private Integer needCopyright = 0;

    /**
     * 版权声明
     */
    private String copyrightText;

    /**
     * 是否需要展示水印，0--不需要，1--需要
     */
    private Integer needWaterMark = 0;

    /**
     * 展示水印时间，单位毫秒
     */
    private Long showTime;

    /**
     * 隐藏水印时间，单位毫秒
     */
    private Long hiddenTime;

    /**
     * 平台id
     */
    private String splatId;

    /**
     * 当前播放码流编码，对应liveStreams或videoStreams中第一个元素的属性
     */
    private String playStreamCode;

    /**
     * 是否是3D码流，0--不是，1--是3D
     */
    private Integer is3D;

    /**
     * 直播码流列表
     */
    private List<TvStreamInfoDto> liveStreams;

    /**
     * 点播码流列表
     */
    private List<Stream> videoStreams;

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVtype() {
        return this.vtype;
    }

    public void setVtype(Integer vtype) {
        this.vtype = vtype;
    }

    public Integer getCtype() {
        return this.ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
    }

    public String getStreamlist() {
        return this.streamlist;
    }

    public void setStreamlist(String streamlist) {
        this.streamlist = streamlist;
    }

    public Integer getLivetype() {
        return this.livetype;
    }

    public void setLivetype(Integer livetype) {
        this.livetype = livetype;
    }

    public Integer getPlayAd() {
        return this.playAd;
    }

    public void setPlayAd(Integer playAd) {
        this.playAd = playAd;
    }

    public Integer getLiveStatus() {
        return this.liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Integer getEnable() {
        return this.enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getIsFree() {
        return this.isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getPayStatus() {
        return this.payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVipPrice() {
        return this.vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getCommonPrice() {
        return this.commonPrice;
    }

    public void setCommonPrice(String commonPrice) {
        this.commonPrice = commonPrice;
    }

    public String getBgImg() {
        return this.bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getScreenings() {
        return this.screenings;
    }

    public void setScreenings(String screenings) {
        this.screenings = screenings;
    }

    public String getPlayid() {
        return this.playid;
    }

    public void setPlayid(String playid) {
        this.playid = playid;
    }

    public String getLiveName() {
        return this.liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLiveTime() {
        return this.liveTime;
    }

    public void setLiveTime(String liveTime) {
        this.liveTime = liveTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLiveEndTime() {
        return this.liveEndTime;
    }

    public void setLiveEndTime(String liveEndTime) {
        this.liveEndTime = liveEndTime;
    }

    public String getPlayUrl() {
        return this.playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUinfo() {
        return this.uinfo;
    }

    public void setUinfo(String uinfo) {
        this.uinfo = uinfo;
    }

    public Integer getNeedCopyright() {
        return this.needCopyright;
    }

    public void setNeedCopyright(Integer needCopyright) {
        this.needCopyright = needCopyright;
    }

    public String getCopyrightText() {
        return this.copyrightText;
    }

    public void setCopyrightText(String copyrightText) {
        this.copyrightText = copyrightText;
    }

    public Integer getNeedWaterMark() {
        return this.needWaterMark;
    }

    public void setNeedWaterMark(Integer needWaterMark) {
        this.needWaterMark = needWaterMark;
    }

    public Long getShowTime() {
        return this.showTime;
    }

    public void setShowTime(Long showTime) {
        this.showTime = showTime;
    }

    public Long getHiddenTime() {
        return this.hiddenTime;
    }

    public void setHiddenTime(Long hiddenTime) {
        this.hiddenTime = hiddenTime;
    }

    public String getSplatId() {
        return this.splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public List<TvStreamInfoDto> getLiveStreams() {
        return this.liveStreams;
    }

    public void setLiveStreams(List<TvStreamInfoDto> liveStreams) {
        this.liveStreams = liveStreams;
    }

    public List<Stream> getVideoStreams() {
        return this.videoStreams;
    }

    public void setVideoStreams(List<Stream> videoStreams) {
        this.videoStreams = videoStreams;
    }

    public String getPlayStreamCode() {
        return this.playStreamCode;
    }

    public void setPlayStreamCode(String playStreamCode) {
        this.playStreamCode = playStreamCode;
    }

    public Integer getIs3D() {
        return this.is3D;
    }

    public void setIs3D(Integer is3d) {
        this.is3D = is3d;
    }

}
