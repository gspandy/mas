package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonAutoDetect
@JsonPropertyOrder({ "mmsid", "imgprefix", "info" })
public class MmsInfo {
    private Long mmsid;
    private String imgprefix;// 视频截图
    private List<MmsFile> infos;
    private Object subtitle;// 字幕，key：字幕类型，value：分发后的存储地址
    private Object defSubTrack;// 编辑勾选的默认字幕，key：字幕类型，value：分发后的存储地址
    private Object defAudioTrack;// 编辑勾选的默认音轨，key：（语种_音质码率），value：音轨id

    public List<MmsFile> getInfos() {
        return this.infos;
    }

    public void setInfos(List<MmsFile> infos) {
        this.infos = infos;
    }

    public Long getMmsid() {
        return this.mmsid;
    }

    public void setMmsid(Long mmsid) {
        this.mmsid = mmsid;
    }

    public String getImgprefix() {
        return this.imgprefix;
    }

    public void setImgprefix(String imgprefix) {
        this.imgprefix = imgprefix;
    }

    public Object getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(Object subtitle) {
        this.subtitle = subtitle;
    }

    public Object getDefSubTrack() {
        return this.defSubTrack;
    }

    public void setDefSubTrack(Object defSubTrack) {
        this.defSubTrack = defSubTrack;
    }

    public Object getDefAudioTrack() {
        return this.defAudioTrack;
    }

    public void setDefAudioTrack(Object defAudioTrack) {
        this.defAudioTrack = defAudioTrack;
    }

}
