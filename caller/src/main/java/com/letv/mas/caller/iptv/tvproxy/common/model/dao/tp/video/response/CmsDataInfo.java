package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse.SubjectDataTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse.*;
import java.util.Map;

public class CmsDataInfo {
    private static final long serialVersionUID = -7602714073735082783L;

    private String androidUrl;
    private String iosUrl;
    private String shorDesc;
    private String tagUrl;
    private String tag;
    private String url;
    private Map<String, String> playPlatform;
    private String position;

    private String content;// 版块内容对应的ID（专辑ID或视频ID）

    private String bid; // 所属板块id

    private String pushflag;// 推送平台

    private String title;// 版块内容标题

    private String subTitle;// 副标题（看点）

    private String tvPic;// 图片地址

    private Integer type;// 版块内容类型：1：视频，2：专辑，3：直播Code，4：用户ID，5：小专题ID，6：轮播台ID

    private String skipPage;// 跳频道页

    /**
     * URL跳转类型，1--移动外跳，2--移动内跳，3--小专题地址，4--TV内跳，5--TV外跳
     */
    private String skipType;

    private String skipUrl;// 跳转URL

    private String tvUrl;// 跳浏览器
    private String nameCn;// 视频名字
    private BlockContentVideo video;// 视频详情

    public BlockContentVideo getVideo() {
        return this.video;
    }

    public void setVideo(BlockContentVideo video) {
        this.video = video;
    }

    public BlockContentAlbum getAlbum() {
        return this.album;
    }

    public void setAlbum(BlockContentAlbum album) {
        this.album = album;
    }

    private BlockContentAlbum album;// 专辑详情

    private CmsTpResponseExt extendJson; // 追加版块

    private Integer zidType;// 专题类型

    private String shortDesc;// 简介
    private String remark;// 备注
    private String pic1;// 背景图
    private String pic2;// 海报图
    /**
     * 专题包信息，用于解析专题包模板信息
     */
    private SubjectDataTpResponse kztInfo;

    public Boolean isVideo() {
        if (this.video != null) {
            return true;
        }
        return false;
    }

    public Boolean isAlbum() {
        if (this.album != null) {
            return true;
        }
        return false;
    }

    public String getAndroidUrl() {
        return this.androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getIosUrl() {
        return this.iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getShorDesc() {
        return this.shorDesc;
    }

    public void setShorDesc(String shorDesc) {
        this.shorDesc = shorDesc;
    }

    public String getTagUrl() {
        return this.tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getPlayPlatform() {
        return this.playPlatform;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBid() {
        return this.bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getPushflag() {
        return this.pushflag;
    }

    public void setPushflag(String pushflag) {
        this.pushflag = pushflag;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTvPic() {
        return this.tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSkipPage() {
        return this.skipPage;
    }

    public void setSkipPage(String skipPage) {
        this.skipPage = skipPage;
    }

    public String getSkipType() {
        return this.skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getSkipUrl() {
        return this.skipUrl;
    }

    public void setSkipUrl(String skipUrl) {
        this.skipUrl = skipUrl;
    }

    public String getTvUrl() {
        return this.tvUrl;
    }

    public void setTvUrl(String tvUrl) {
        this.tvUrl = tvUrl;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public CmsTpResponseExt getExtendJson() {
        return this.extendJson;
    }

    public void setExtendJson(CmsTpResponseExt extendJson) {
        this.extendJson = extendJson;
    }

    public Integer getZidType() {
        return this.zidType;
    }

    public void setZidType(Integer zidType) {
        this.zidType = zidType;
    }

    public String getShortDesc() {
        return this.shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPic1() {
        return this.pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return this.pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public SubjectDataTpResponse getKztInfo() {
        return this.kztInfo;
    }

    public void setKztInfo(SubjectDataTpResponse kztInfo) {
        this.kztInfo = kztInfo;
    }

}
