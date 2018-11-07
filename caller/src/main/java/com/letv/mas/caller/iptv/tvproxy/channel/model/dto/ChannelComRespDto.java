package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

import java.util.List;

/**
 * @author Jalon
 *         会员频道改版，使用list—map样式
 */

public class ChannelComRespDto extends BaseData {

    /**
     * 
     */
    private static final long serialVersionUID = -7184149437500826361L;
    /**
     * 首屏运营list
     */
    private List<BaseData> fDataList;
    /**
     * 二屏运营list
     */
    private List<BaseData> sDataList;
    /**
     * 推荐色块url
     */
    private ChannelData recUrl;

    private List<BaseData> dataList;

    private String bgImg;

    public List<BaseData> getfDataList() {
        return fDataList;
    }

    public void setfDataList(List<BaseData> fDataList) {
        this.fDataList = fDataList;
    }

    public List<BaseData> getsDataList() {
        return sDataList;
    }

    public void setsDataList(List<BaseData> sDataList) {
        this.sDataList = sDataList;
    }

    public ChannelData getRecUrl() {
        return recUrl;
    }

    public void setRecUrl(ChannelData recUrl) {
        this.recUrl = recUrl;
    }

    public List<BaseData> getDataList() {
        return dataList;
    }

    public void setDataList(List<BaseData> dataList) {
        this.dataList = dataList;
    }

    public String getBgImg() {
        return bgImg;
    }

    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }

}
