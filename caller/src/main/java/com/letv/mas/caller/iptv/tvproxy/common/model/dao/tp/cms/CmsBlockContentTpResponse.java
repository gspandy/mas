package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse.SubjectDataTpResponse;

/**
 * 版块内容
 */
public class CmsBlockContentTpResponse implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = -4241451285573704372L;

    private String id;

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

    private BlockContentAlbum album;// 专辑详情

    private CmsTpResponseExt extendJson; // 追加版块

    private Integer zidType;// 专题类型

    private String shortDesc;// 简介

    /**
     * 简介-20150914CMS字段拼错了，不是shortDesc
     */
    private String shorDesc;

    private String remark;// 备注
    private String pic1;// 背景图
    private String pic2;// 海报图
    private String androidUrl;
    /**
     * 专题包信息，用于解析专题包模板信息
     */
    private GetSubjectTpResponse.SubjectDataTpResponse kztInfo;

    /**
     * 我的页面会员相关板块的位置，
     * 1--激活会员服务，2--开通会员，3(目前没有我要续费)--我要续费，4--会员兑换，5--播放记录，6--收藏与追剧，
     * focus--旧的"我的"页面焦点图，title--标题数据类型，rec--“我的”第3列位置背景图
     */
    private String position;// 位置信息
    /**
     * 边看边买 ---------------------start--------------------
     */
    private String url;// M站商品url
    private String iosUrl; // 商品好评度
    private String startTime;// 商品出现时间
    private String endTime;// 商品持续时间
    private String mobilePic;// 商品详情图片1
    private String padPic;// 商品详情图片2
    private String extends_extendPicAll;// 商品详情页推荐图片
    private String priority;// 权重
    private String tag;
    private String extends_buttonscript;// 按钮文案
    private String extends_QRcodescript;// 二维码文案
    private String extends_cardscript1;// 弹出卡片文案1
    private String extends_cardscript2;// 弹出卡片文案2
    private String tagUrl;
    private Map<String, String> playPlatform;
    private Map<String, String> picList;

    public Map<String, String> getPlayPlatform() {
        return playPlatform;
    }

    public void setPlayPlatform(Map<String, String> playPlatform) {
        this.playPlatform = playPlatform;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 边看边买 ---------------------end------------------------
     */
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public String getIosUrl() {
        return this.iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMobilePic() {
        return this.mobilePic;
    }

    public void setMobilePic(String mobilePic) {
        this.mobilePic = mobilePic;
    }

    public String getPadPic() {
        return this.padPic;
    }

    public void setPadPic(String padPic) {
        this.padPic = padPic;
    }

    public String getExtends_extendPicAll() {
        return this.extends_extendPicAll;
    }

    public void setExtends_extendPicAll(String extends_extendPicAll) {
        this.extends_extendPicAll = extends_extendPicAll;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Integer getZidType() {
        return this.zidType;
    }

    public void setZidType(Integer zidType) {
        this.zidType = zidType;
    }

    public CmsTpResponseExt getExtendJson() {
        return this.extendJson;
    }

    public void setExtendJson(CmsTpResponseExt extendJson) {
        this.extendJson = extendJson;
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

    public String getExtends_buttonscript() {
        return extends_buttonscript;
    }

    public void setExtends_buttonscript(String extends_buttonscript) {
        this.extends_buttonscript = extends_buttonscript;
    }

    public String getExtends_QRcodescript() {
        return extends_QRcodescript;
    }

    public void setExtends_QRcodescript(String extends_QRcodescript) {
        this.extends_QRcodescript = extends_QRcodescript;
    }

    public String getExtends_cardscript1() {
        return extends_cardscript1;
    }

    public void setExtends_cardscript1(String extends_cardscript1) {
        this.extends_cardscript1 = extends_cardscript1;
    }

    public String getExtends_cardscript2() {
        return extends_cardscript2;
    }

    public void setExtends_cardscript2(String extends_cardscript2) {
        this.extends_cardscript2 = extends_cardscript2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 视频详情
     */
    public static class BlockContentVideo implements Serializable {

        /**
		 *
		 */
        private static final long serialVersionUID = 2541635334930252954L;

        private Integer pid;// 专辑ID

        private Integer cid;// 内容ID

        /**
         * 播放平台，用于判断是否TV有版权
         */
        private Map<String, String> playPlatform;

        private Map<String, String> payPlatform;

        private Map<String, String> subCategory;

        public Map<String, String> getPayPlatform() {
            return this.payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public Map<String, String> getSubCategory() {
            return subCategory;
        }

        public void setSubCategory(Map<String, String> subCategory) {
            this.subCategory = subCategory;
        }

        public Integer getPid() {
            return this.pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public Integer getCid() {
            return this.cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public Map<String, String> getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }

    }

    /**
     * 专辑详情
     */
    public static class BlockContentAlbum implements Serializable {

        /**
		 *
		 */
        private static final long serialVersionUID = -1507570650630230069L;

        private Integer cid;// 内容ID

        private Integer nowEpisodes;
        private Integer nowIssue;
        private Integer isEnd;
        /**
         * 播放平台，用于判断是否TV有版权
         */
        private Map<String, String> playPlatform;

        private Map<String, String> category;

        private Map<String, String> subCategory;

        private Map<String, String> payPlatform;

        public Map<String, String> getPayPlatform() {
            return this.payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public Map<String, String> getSubCategory() {
            return this.subCategory;
        }

        public void setSubCategory(Map<String, String> subCategory) {
            this.subCategory = subCategory;
        }

        public Map<String, String> getCategory() {
            return this.category;
        }

        public void setCategory(Map<String, String> category) {
            this.category = category;
        }

        public Integer getCid() {
            return this.cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public Map<String, String> getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }

        public Integer getNowEpisodes() {
            return this.nowEpisodes;
        }

        public void setNowEpisodes(Integer nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public Integer getIsEnd() {
            return this.isEnd;
        }

        public void setIsEnd(Integer isEnd) {
            this.isEnd = isEnd;
        }

        public Integer getNowIssue() {
            return this.nowIssue;
        }

        public void setNowIssue(Integer nowIssue) {
            this.nowIssue = nowIssue;
        }

    }

    /**
     * 追加版块数据
     */
    public static class CmsTpResponseExt implements Serializable {

        /**
		 *
		 */
        private static final long serialVersionUID = 3529105046460704511L;

        private String extendCid;

        /**
         * 自定义的内容ID，参见CMS后台
         */
        private String extendPage;

        private Map<String, String> extendPicAll;

        private String extendPid;

        private String extendRange;

        private String extendSubscript;

        private String extendZid;

        private Integer extendTvPageType;

        private CmsTpResponseExtTvDict extendTvDict;

        private String buttonscript;// 按钮文案
        @JsonProperty(value = "QRcodescript")
        private String QRcodescript;// 二维码文案，首字母为大写需要加个注解，不然底层解析获取不到数据
        private String cardscript1;// 弹出卡片文案1
        private String cardscript2;// 弹出卡片文案2

        private String extendRadio;

        private String extendCopyUrl;

        public String getExtendCopyUrl() {
            return extendCopyUrl;
        }

        public void setExtendCopyUrl(String extendCopyUrl) {
            this.extendCopyUrl = extendCopyUrl;
        }

        public String getExtendRadio() {
            return extendRadio;
        }

        public void setExtendRadio(String extendRadio) {
            this.extendRadio = extendRadio;
        }

        public String getExtendPage() {
            return this.extendPage;
        }

        public Integer getExtendTvPageType() {
            return extendTvPageType;
        }

        public void setExtendTvPageType(Integer extendTvPageType) {
            this.extendTvPageType = extendTvPageType;
        }

        public void setExtendPage(String extendPage) {
            this.extendPage = extendPage;
        }

        public String getExtendCid() {
            return this.extendCid;
        }

        public void setExtendCid(String extendCid) {
            this.extendCid = extendCid;
        }

        public Map<String, String> getExtendPicAll() {
            return this.extendPicAll;
        }

        public void setExtendPicAll(Map<String, String> extendPicAll) {
            this.extendPicAll = extendPicAll;
        }

        public String getExtendPid() {
            return this.extendPid;
        }

        public void setExtendPid(String extendPid) {
            this.extendPid = extendPid;
        }

        public String getExtendRange() {
            return this.extendRange;
        }

        public void setExtendRange(String extendRange) {
            this.extendRange = extendRange;
        }

        public String getExtendSubscript() {
            return this.extendSubscript;
        }

        public void setExtendSubscript(String extendSubscript) {
            this.extendSubscript = extendSubscript;
        }

        public String getExtendZid() {
            return this.extendZid;
        }

        public void setExtendZid(String extendZid) {
            this.extendZid = extendZid;
        }

        public CmsTpResponseExtTvDict getExtendTvDict() {
            return this.extendTvDict;
        }

        public void setExtendTvDict(CmsTpResponseExtTvDict extendTvDict) {
            this.extendTvDict = extendTvDict;
        }

        public String getButtonscript() {
            return buttonscript;
        }

        public void setButtonscript(String buttonscript) {
            this.buttonscript = buttonscript;
        }

        public String getQRcodescript() {
            return QRcodescript;
        }

        public void setQRcodescript(String qRcodescript) {
            this.QRcodescript = qRcodescript;
        }

        public String getCardscript1() {
            return cardscript1;
        }

        public void setCardscript1(String cardscript1) {
            this.cardscript1 = cardscript1;
        }

        public String getCardscript2() {
            return cardscript2;
        }

        public void setCardscript2(String cardscript2) {
            this.cardscript2 = cardscript2;
        }

    }

    /**
     * 自定义扩展
     * @author KevinYi
     */
    public static class CmsTpResponseExtTvDict implements Serializable {

        /**
		 *
		 */
        private static final long serialVersionUID = 8783177651944453609L;
        private String id;
        private Long mtime;
        private Long ctime;
        private String name;
        private String description;
        private Integer status;
        private String tid;
        private Integer type;
        private CmsTpResponseExtTvDictExtJson extendJson;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getMtime() {
            return this.mtime;
        }

        public void setMtime(Long mtime) {
            this.mtime = mtime;
        }

        public Long getCtime() {
            return this.ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getTid() {
            return this.tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public CmsTpResponseExtTvDictExtJson getExtendJson() {
            return this.extendJson;
        }

        public void setExtendJson(CmsTpResponseExtTvDictExtJson extendJson) {
            this.extendJson = extendJson;
        }

    }

    public static class CmsTpResponseExtTvDictExtJson implements Serializable {

        /**
		 *
		 */
        private static final long serialVersionUID = 8599695587545262432L;
        private Map<String, String> urls;

        public Map<String, String> getUrls() {
            return this.urls;
        }

        public void setUrls(Map<String, String> urls) {
            this.urls = urls;
        }

    }

    public String getBid() {
        return this.bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public SubjectDataTpResponse getKztInfo() {
        return this.kztInfo;
    }

    public void setKztInfo(SubjectDataTpResponse kztInfo) {
        this.kztInfo = kztInfo;
    }

    public String getShorDesc() {
        return this.shorDesc;
    }

    public void setShorDesc(String shorDesc) {
        this.shorDesc = shorDesc;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Map<String, String> getPicList() {
        return picList;
    }

    public void setPicList(Map<String, String> picList) {
        this.picList = picList;
    }
}
