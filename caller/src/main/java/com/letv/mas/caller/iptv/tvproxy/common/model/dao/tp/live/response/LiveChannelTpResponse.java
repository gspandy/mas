package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 直播部门频道数据封装类
 * @author KevinYi
 */
public class LiveChannelTpResponse implements Serializable {

    public static final String pic1_746_419 = "pic1_746_419";
    public static final String pic2_960_540 = "pic2_960_540";
    public static final String pic5_1920_1080 = "pic5_1920_1080";
    public static final String COPYRIGHT_YES = "1"; // 乐视有版权
    public static final String COPYRIGHT_NO = "0"; // 乐视无版权
    private String channelId;// 频道ID
    private String numericKeys;// 频道数字键
    private String channelEname;// 频道英文名称
    private String channelName;// 乐视频道中文名称
    private String cibnChannelName;// 国广频道中文名称，TV端产品应该显示这个名称
    private String beginTime;// 频道启用开始时间，如："2012-09-01 20:24:37"此字段可以用作频道的上线时间，前端根据此字段来判断频道是否是新频道。
    private String endTime;// 频道启用结束时间，为空表示频道一直启用
    private String channelClass;// 频道主题分类
                                // http://st.live.letv.com/live/code/00020.json
    private String satelliteTvType;// 卫视频道分类，参考词典《卫视分类编码》
                                   // http://api.live.letv.com/v1/dictionary/00037
    private String subLiveType;// 二级分类
    private String subLiveTypeName;// 二级分类名称
    private String belongBrand;// 频道所属品牌，参考《频道所属品牌信息获取接口》
                               // http://st.live.letv.com/live/channel/allBrands.json
    private Map<String, String> defaultLogo;// 频道默认LOGO
    private String demandId;// 需求方，参考词典《频道需求方编码》
                            // http://st.live.letv.com/live/code/00001.json
    private String signal;// 信号源，参考词典《信号源编码》http://st.live.letv.com/live/code/00002.json
    private String is3D;// 是否为3D频道（1是 0否）
    private String is4K;// 是否为4K频道（0 否 1 4K 2 伪4K）
    private String ch;// 渠道号
    private String orderNo;// 序号，客户端排序用
    private String isRecommend;// 是否推荐 （1 是 0 否）
    private String pcWatermarkUrl;// PC端台标地址
    private String watermarkUrl;// TV端台标地址
    private String cibnWatermarkUrl;// 国广TV端台标地址
    private String childLock;// 是否启用童锁 （1 是 0 否）
    private String copyright;// 频道版权（1 PC， 2 TV，4 手机 ）如 ： 1,2,4
    private String channelDesc;// 频道简介
    private List<TvStreamInfo> streams;// 频道流信息
    private List<LiveChannelProgramDetailTpResponse> programs; // 接推荐数据带过来的节目单
    private String postH3;// 频道LOGO竖图 120*90
    private String postOrigin;// 频道LOGO原图
    private String postS1;// 频道LOGO横图150*200
    private String postS2;// 频道LOGO横图120*160
    private String postS3;// 频道LOGO横图96*128
    private String postS4;// 频道LOGO横图150*150
    private String postS5;// 频道LOGO横图30*30
    private String isCopyRight; // 乐视是否有版权 0:无版权 1:有版权
    private String programSource; // 版权方来源
    private String splatid; // 频道上线各端的标识
    private String chatRoomNum;// 聊天室号
    private String isChat;// 是否为聊天室。1：是，其他：不是

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getNumericKeys() {
        return this.numericKeys;
    }

    public void setNumericKeys(String numericKeys) {
        this.numericKeys = numericKeys;
    }

    public String getChannelEname() {
        return this.channelEname;
    }

    public void setChannelEname(String channelEname) {
        this.channelEname = channelEname;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCibnChannelName() {
        return this.cibnChannelName;
    }

    public void setCibnChannelName(String cibnChannelName) {
        this.cibnChannelName = cibnChannelName;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChannelClass() {
        return this.channelClass;
    }

    public void setChannelClass(String channelClass) {
        this.channelClass = channelClass;
    }

    public String getSatelliteTvType() {
        return this.satelliteTvType;
    }

    public void setSatelliteTvType(String satelliteTvType) {
        this.satelliteTvType = satelliteTvType;
    }

    public String getSubLiveType() {
        return this.subLiveType;
    }

    public void setSubLiveType(String subLiveType) {
        this.subLiveType = subLiveType;
    }

    public String getSubLiveTypeName() {
        return this.subLiveTypeName;
    }

    public void setSubLiveTypeName(String subLiveTypeName) {
        this.subLiveTypeName = subLiveTypeName;
    }

    public String getBelongBrand() {
        return this.belongBrand;
    }

    public void setBelongBrand(String belongBrand) {
        this.belongBrand = belongBrand;
    }

    public Map<String, String> getDefaultLogo() {
        return this.defaultLogo;
    }

    public void setDefaultLogo(Map<String, String> defaultLogo) {
        this.defaultLogo = defaultLogo;
    }

    public String getDemandId() {
        return this.demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getSignal() {
        return this.signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getIs3D() {
        return this.is3D;
    }

    public void setIs3D(String is3d) {
        this.is3D = is3d;
    }

    public String getIs4K() {
        return this.is4K;
    }

    public void setIs4K(String is4k) {
        this.is4K = is4k;
    }

    public String getCh() {
        return this.ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public String getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getIsRecommend() {
        return this.isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getPcWatermarkUrl() {
        return this.pcWatermarkUrl;
    }

    public void setPcWatermarkUrl(String pcWatermarkUrl) {
        this.pcWatermarkUrl = pcWatermarkUrl;
    }

    public String getWatermarkUrl() {
        return this.watermarkUrl;
    }

    public void setWatermarkUrl(String watermarkUrl) {
        this.watermarkUrl = watermarkUrl;
    }

    public String getCibnWatermarkUrl() {
        return this.cibnWatermarkUrl;
    }

    public void setCibnWatermarkUrl(String cibnWatermarkUrl) {
        this.cibnWatermarkUrl = cibnWatermarkUrl;
    }

    public String getChildLock() {
        return this.childLock;
    }

    public void setChildLock(String childLock) {
        this.childLock = childLock;
    }

    public String getCopyright() {
        return this.copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getChannelDesc() {
        return this.channelDesc;
    }

    public void setChannelDesc(String channelDesc) {
        this.channelDesc = channelDesc;
    }

    public List<TvStreamInfo> getStreams() {
        return this.streams;
    }

    public void setStreams(List<TvStreamInfo> streams) {
        this.streams = streams;
    }

    public List<LiveChannelProgramDetailTpResponse> getPrograms() {
        return this.programs;
    }

    public void setPrograms(List<LiveChannelProgramDetailTpResponse> programs) {
        this.programs = programs;
    }

    public String getPostH3() {
        return this.postH3;
    }

    public void setPostH3(String postH3) {
        this.postH3 = postH3;
    }

    public String getPostOrigin() {
        return this.postOrigin;
    }

    public void setPostOrigin(String postOrigin) {
        this.postOrigin = postOrigin;
    }

    public String getPostS1() {
        return this.postS1;
    }

    public void setPostS1(String postS1) {
        this.postS1 = postS1;
    }

    public String getPostS2() {
        return this.postS2;
    }

    public void setPostS2(String postS2) {
        this.postS2 = postS2;
    }

    public String getPostS3() {
        return this.postS3;
    }

    public void setPostS3(String postS3) {
        this.postS3 = postS3;
    }

    public String getPostS4() {
        return this.postS4;
    }

    public void setPostS4(String postS4) {
        this.postS4 = postS4;
    }

    public String getPostS5() {
        return this.postS5;
    }

    public void setPostS5(String postS5) {
        this.postS5 = postS5;
    }

    public String getIsCopyRight() {
        return this.isCopyRight;
    }

    public void setIsCopyRight(String isCopyRight) {
        this.isCopyRight = isCopyRight;
    }

    public String getProgramSource() {
        return this.programSource;
    }

    public void setProgramSource(String programSource) {
        this.programSource = programSource;
    }

    public String getSplatid() {
        return this.splatid;
    }

    public void setSplatid(String splatid) {
        this.splatid = splatid;
    }

    public String getChatRoomNum() {
        return this.chatRoomNum;
    }

    public void setChatRoomNum(String chatRoomNum) {
        this.chatRoomNum = chatRoomNum;
    }

    public String getIsChat() {
        return this.isChat;
    }

    public void setIsChat(String isChat) {
        this.isChat = isChat;
    }

}
