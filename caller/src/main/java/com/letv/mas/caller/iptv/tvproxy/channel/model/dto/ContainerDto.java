package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

import java.util.List;

public class ContainerDto extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = -1893981284880184261L;
    /*
     * 背景图
     */
    private String bgImg;
    /*
     * 广告图
     */
    private String adImg;
    /*
     * 播放区
     */
    private List<BaseData> playDataList;
    /**
     * 直播列表
     */
    private List<BaseData> liveDataList;
    /*
     * 焦点运营区
     */
    private List<BaseData> focusDataList;
    /*
     * 会员相关数据，主要配置收银台、H5等提高会员转化率的数据，当此部分数据不为空时，此部分数据将代替原focusDataList
     */
    private List<BaseData> vipAboutList;
    /*
     * 右屏统一容器
     */
    private List<ChannelData> modelDataList;

    public String getBgImg() {
        return this.bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    public String getAdImg() {
        return this.adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public List<BaseData> getPlayDataList() {
        return playDataList;
    }

    public void setPlayDataList(List<BaseData> playDataList) {
        this.playDataList = playDataList;
    }

    public List<BaseData> getFocusDataList() {
        return this.focusDataList;
    }

    public void setFocusDataList(List<BaseData> focusDataList) {
        this.focusDataList = focusDataList;
    }

    public List<ChannelData> getModelDataList() {
        return this.modelDataList;
    }

    public void setModelDataList(List<ChannelData> modelDataList) {
        this.modelDataList = modelDataList;
    }

    public List<BaseData> getVipAboutList() {
        return this.vipAboutList;
    }

    public void setVipAboutList(List<BaseData> vipAboutList) {
        this.vipAboutList = vipAboutList;
    }

    public List<BaseData> getLiveDataList() {
        return liveDataList;
    }

    public void setLiveDataList(List<BaseData> liveDataList) {
        this.liveDataList = liveDataList;
    }

}
