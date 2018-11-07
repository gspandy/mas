package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto;

import java.io.Serializable;
import java.util.List;

public class LiveChannelDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 5105254202602060208L;
    public static final String BRANCH_TYPE_MULTI_PROGRAM = "1";
    public static final String BRANCH_TYPE_MULTI_LANGUAGE = "2";
    public static final String CHANNEL_SOURCE_REC = "1"; // 频道来源为推荐

    private String channelId; // 频道id
    private String channelName; // 频道名称
    private String channelPic; // 频道图片
    private String channelBigPic; // 频道大图
    private String isPay; // 是否付费 1付费 0:免费
    private String type; // 频道类型 2:直播 3:轮播 4:卫视台
    private String isLiveFromTV; // 直播是否来自电视台节目 1: 是 0: 不是
    private String splatId; // 客户端版权编号
    private String smallTitle; // 小标题(专题列表)
    private String watchFocus; // 看点(专题列表)
    private String isIgnoreLinkShell;// 是否migu 1：是， 其他：否
    private String streamSourceType; // 码流来源
    private String src; // 频道来源，cms,rec等

    private CpInfo cpInfo; // 第三方cp信息
    private LiveProgramDto cur; // 当前正在播放的节目信息
    private LiveProgramDto next; // 下一个节目信息
    private List<LiveStreamDto> streams; // 节目码流信息

    private Long recordingId; // 录播id
    private Integer weight; // 权重
    private String liveType; // 直播类型名称，例如 sports music other
    private String channelEname; // 频道的英文名称
    private String signal; // 直播数据源分类 2:卫视台 7:轮播台
    private String channelClass; // 轮播的分类id
    private String streamTips; // 盗播流提示
    private String numericKeys; // 台号
    private String branchType; // 多直播分支类型
    private LiveMultiProgramDto multiProgram;
    private List<LiveTagDto> leWords; // 乐词信息
    private String selfCopyRight; // 是否自有版权
    private String fav; // 是否收藏
    private String isDolby; // 是否是杜比直播
    private String isPanoramicView; // 是否是全景直播
    private String waterLogo; // 轮播台的水印角标
    private String isArtificialRecommend; // 是否是直播后台人工强推的频道
    private String orderNo;// 序号，客户端排序用

    private String haveProducts; // 是否有商品 1:有 0:没有
    private String bkid; // 商品版块id
    private String is3D;// 是否为3D频道（1是 0否）
    private String is4K;// 是否为4K频道（0 否 1 4K 2 伪4K）
    /**
     * 为了解决TVLive老版本展示上涉及到的政策风险，临时添加； 不允许做其他使用
     */
    @Deprecated
    private String categoryName;

    private String isDrm;// 是否是drm

    private String home;// 主场队
    private String guest;// 客场队
    private String homescore;// 主场得分
    private String guestscore;// 客场得分
    private String homeImgUrl;// 主场头像
    private String guestImgUrl;// 客场头像
    private Integer isVS;// 是否pk

    private String onlineNum; // 在线人数

    public String getIsDrm() {
        return isDrm;
    }

    public void setIsDrm(String isDrm) {
        this.isDrm = isDrm;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Deprecated
    public String getCategoryName() {
        return categoryName;
    }

    @Deprecated
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIs3D() {
        return is3D;
    }

    public void setIs3D(String is3d) {
        is3D = is3d;
    }

    public String getIs4K() {
        return is4K;
    }

    public void setIs4K(String is4k) {
        is4K = is4k;
    }

    public String getIsLiveFromTV() {
        return isLiveFromTV;
    }

    public void setIsLiveFromTV(String isLiveFromTV) {
        this.isLiveFromTV = isLiveFromTV;
    }

    public String getIsPanoramicView() {
        return isPanoramicView;
    }

    public void setIsPanoramicView(String isPanoramicView) {
        this.isPanoramicView = isPanoramicView;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBkid() {
        return bkid;
    }

    public void setBkid(String bkid) {
        this.bkid = bkid;
    }

    public String getHaveProducts() {
        return haveProducts;
    }

    public void setHaveProducts(String haveProducts) {
        this.haveProducts = haveProducts;
    }

    public String getIsArtificialRecommend() {
        return isArtificialRecommend;
    }

    public void setIsArtificialRecommend(String isArtificialRecommend) {
        this.isArtificialRecommend = isArtificialRecommend;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public String getWatchFocus() {
        return watchFocus;
    }

    public void setWatchFocus(String watchFocus) {
        this.watchFocus = watchFocus;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getWaterLogo() {
        return waterLogo;
    }

    public void setWaterLogo(String waterLogo) {
        this.waterLogo = waterLogo;
    }

    public String getIsDolby() {
        return isDolby;
    }

    public void setIsDolby(String isDolby) {
        this.isDolby = isDolby;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getSelfCopyRight() {
        return selfCopyRight;
    }

    public void setSelfCopyRight(String selfCopyRight) {
        this.selfCopyRight = selfCopyRight;
    }

    public LiveMultiProgramDto getMultiProgram() {
        return multiProgram;
    }

    public void setMultiProgram(LiveMultiProgramDto multiProgram) {
        this.multiProgram = multiProgram;
    }

    public String getNumericKeys() {
        return numericKeys;
    }

    public void setNumericKeys(String numericKeys) {
        this.numericKeys = numericKeys;
    }

    public String getStreamTips() {
        return streamTips;
    }

    public void setStreamTips(String streamTips) {
        this.streamTips = streamTips;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getChannelClass() {
        return channelClass;
    }

    public void setChannelClass(String channelClass) {
        this.channelClass = channelClass;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getChannelEname() {
        return channelEname;
    }

    public void setChannelEname(String channelEname) {
        this.channelEname = channelEname;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Long getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(Long recordingId) {
        this.recordingId = recordingId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelPic() {
        return channelPic;
    }

    public void setChannelPic(String channelPic) {
        this.channelPic = channelPic;
    }

    public String getChannelBigPic() {
        return channelBigPic;
    }

    public void setChannelBigPic(String channelBigPic) {
        this.channelBigPic = channelBigPic;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSplatId() {
        return splatId;
    }

    public void setSplatId(String splatId) {
        this.splatId = splatId;
    }

    public String getIsIgnoreLinkShell() {
        return isIgnoreLinkShell;
    }

    public void setIsIgnoreLinkShell(String isIgnoreLinkShell) {
        this.isIgnoreLinkShell = isIgnoreLinkShell;
    }

    public String getStreamSourceType() {
        return streamSourceType;
    }

    public void setStreamSourceType(String streamSourceType) {
        this.streamSourceType = streamSourceType;
    }

    public CpInfo getCpInfo() {
        return cpInfo;
    }

    public void setCpInfo(CpInfo cpInfo) {
        this.cpInfo = cpInfo;
    }

    public LiveProgramDto getCur() {
        return cur;
    }

    public void setCur(LiveProgramDto cur) {
        this.cur = cur;
    }

    public LiveProgramDto getNext() {
        return next;
    }

    public void setNext(LiveProgramDto next) {
        this.next = next;
    }

    public List<LiveStreamDto> getStreams() {
        return streams;
    }

    public void setStreams(List<LiveStreamDto> streams) {
        this.streams = streams;
    }

    public List<LiveTagDto> getLeWords() {
        return leWords;
    }

    public void setLeWords(List<LiveTagDto> leWords) {
        this.leWords = leWords;
    }

    public String getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(String onlineNum) {
        this.onlineNum = onlineNum;
    }

    public static class CpInfo {
        private String cpChannelId; // cp的频道id
        private String cpSource; // cp的来源方
        private String authAddress; // cp的调度认证

        public String getCpChannelId() {
            return cpChannelId;
        }

        public void setCpChannelId(String cpChannelId) {
            this.cpChannelId = cpChannelId;
        }

        public String getCpSource() {
            return cpSource;
        }

        public void setCpSource(String cpSource) {
            this.cpSource = cpSource;
        }

        public String getAuthAddress() {
            return authAddress;
        }

        public void setAuthAddress(String authAddress) {
            this.authAddress = authAddress;
        }

    }

    public static class LiveTagDto {
        private String id;
        private String name;
        private String tagIcon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTagIcon() {
            return tagIcon;
        }

        public void setTagIcon(String tagIcon) {
            this.tagIcon = tagIcon;
        }
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getHomescore() {
        return homescore;
    }

    public void setHomescore(String homescore) {
        this.homescore = homescore;
    }

    public String getGuestscore() {
        return guestscore;
    }

    public void setGuestscore(String guestscore) {
        this.guestscore = guestscore;
    }

    public String getHomeImgUrl() {
        return homeImgUrl;
    }

    public void setHomeImgUrl(String homeImgUrl) {
        this.homeImgUrl = homeImgUrl;
    }

    public String getGuestImgUrl() {
        return guestImgUrl;
    }

    public void setGuestImgUrl(String guestImgUrl) {
        this.guestImgUrl = guestImgUrl;
    }

    public Integer getIsVS() {
        return isVS;
    }

    public void setIsVS(Integer isVS) {
        this.isVS = isVS;
    }
}
