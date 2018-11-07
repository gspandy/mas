package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Stream", description = "视频详情")
public class Stream implements Comparable<Stream>, Serializable {

    @ApiModelProperty(value = "码流名")
    private String name;
    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "能否播放——视频或者专辑使用")
    private String canPlay;// 能否播放——视频或者专辑使用
    @ApiModelProperty(value = "能否下载——视频或者专辑使用")
    private String canDown;// 能否下载——视频或者专辑使用
    @ApiModelProperty(value = "码率")
    private String kbps;
    @ApiModelProperty(value = "带宽")
    private String bandWidth;// 带宽
    @ApiModelProperty(value = "是否默认")
    private Boolean isDefault;// 是否默认
    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    @ApiModelProperty(value = "是否收费 0不收费，1收费")
    private Integer ifCharge;// 是否收费 0不收费，1收费
    @ApiModelProperty(value = "设置——直播码流列表")
    private List<Stream> liveStreams;// 设置——直播码流列表
    @ApiModelProperty(value = "设置——播放码流列表")
    private List<Stream> playStreams;// 设置——播放码流列表

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKbps() {
        return this.kbps;
    }

    public void setKbps(String kbps) {
        this.kbps = kbps;
    }

    public List<Stream> getLiveStreams() {
        return this.liveStreams;
    }

    public void setLiveStreams(List<Stream> liveStreams) {
        this.liveStreams = liveStreams;
    }

    public List<Stream> getPlayStreams() {
        return this.playStreams;
    }

    public void setPlayStreams(List<Stream> playStreams) {
        this.playStreams = playStreams;
    }

    public String getBandWidth() {
        return this.bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public String getCanPlay() {
        return this.canPlay;
    }

    public void setCanPlay(String canPlay) {
        this.canPlay = canPlay;
    }

    public String getCanDown() {
        return this.canDown;
    }

    public void setCanDown(String canDown) {
        this.canDown = canDown;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getIfCharge() {
        return this.ifCharge;
    }

    public void setIfCharge(Integer ifCharge) {
        this.ifCharge = ifCharge;
    }

    @Override
    public int compareTo(Stream tvStream) {
        // 高清码流靠前
        Integer otherCodeValueOrder = LetvStreamCommonConstants.STREAM_CODE_SORT_VSLUE.get(tvStream.getCode());
        Integer thisCodeValueOrder = LetvStreamCommonConstants.STREAM_CODE_SORT_VSLUE.get(this.getCode());

        int otherCodeValue = otherCodeValueOrder == null ? Integer.MAX_VALUE : otherCodeValueOrder.intValue();
        int thisCodeValue = thisCodeValueOrder == null ? Integer.MAX_VALUE : thisCodeValueOrder.intValue();
        if (otherCodeValue > thisCodeValue) {
            return 1;
        } else if (otherCodeValue < thisCodeValue) {
            return -1;
        }
        return 0;
    }
}
