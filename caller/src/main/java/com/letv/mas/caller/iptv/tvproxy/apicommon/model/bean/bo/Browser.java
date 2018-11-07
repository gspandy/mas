package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;

import java.util.Map;

/**
 * 浏览器
 */
public class Browser extends BaseData {

    /**
     * 标题
     */
    private String name;

    /**
     * 副标题
     */
    private String subName;

    /**
     * 图片
     */
    private String img;

    /**
     * 地址
     */
    private String url;

    /**
     * 打开浏览器类型，1--打开外置浏览器，2--打开内置浏览器
     */
    private Integer browserType;

    /**
     * 跳转或打开页面的方式，0--不做任何跳转和响应，1--跳收银台
     */
    private Integer openType;

    /**
     * 存放url的map；key--0-默认的H5,1-超前院线需求中，非3D设备展示的H5,2-3D设备展示的url，其他值目前无业务意义
     */
    private Map<String, String> urlMap;

    private String benefitDeadline;
    /**
     * 外网播放的数据来源，1--内网,2--外网
     */
    private Integer src;

    /**
     * 外网播放数据来源网站，src=1时值为"www.letv.com"，src=2时取决来源网站
     */
    private String website;

    /**
     * 专辑id，不是必填字段
     */
    private String albumId;
    /**
     * 新文案字段，例如按钮文案
     */
    private String extendText;
    /**
     * 图片
     */
    private String extendImg;
    private String buttonImg;
    /**
     * 用来标记会员
     */
    private String vipFlag;

    /**
     * 套餐id，支持定向收银台
     */
    private Integer orderType;

    /**
     * 定向活动id
     */
    private String activityIds;

    /**
     * 活动位置信息，进入收银台时需要
     */
    private String position;

    /**
     * h5活动的活动id
     */
    private String id;

    private String CPS_no;
    private String code_no;
    private String vendor;

    public String getCPS_no() {
        return CPS_no;
    }

    public void setCPS_no(String CPS_no) {
        this.CPS_no = CPS_no;
    }

    public String getCode_no() {
        return code_no;
    }

    public void setCode_no(String code_no) {
        this.code_no = code_no;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getSrc() {
        return this.src;
    }

    public void setSrc(Integer src) {
        this.src = src;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getExtendText() {
        return this.extendText;
    }

    public void setExtendText(String extendText) {
        this.extendText = extendText;
    }

    public String getExtendImg() {
        return this.extendImg;
    }

    public void setExtendImg(String extendImg) {
        this.extendImg = extendImg;
    }

    public String getButtonImg() {
        return this.buttonImg;
    }

    public void setButtonImg(String buttonImg) {
        this.buttonImg = buttonImg;
    }

    public String getVipFlag() {
        return this.vipFlag;
    }

    public void setVipFlag(String vipFlag) {
        this.vipFlag = vipFlag;
    }

    public String getBenefitDeadline() {
        return this.benefitDeadline;
    }

    public void setBenefitDeadline(String benefitDeadline) {
        this.benefitDeadline = benefitDeadline;
    }

    @Override
    public int getDataType() {
        return DataConstant.DATA_TYPE_BROWSER;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getBrowserType() {
        return this.browserType;
    }

    public void setBrowserType(Integer browserType) {
        this.browserType = browserType;
    }

    public Integer getOpenType() {
        return this.openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Map<String, String> getUrlMap() {
        return this.urlMap;
    }

    public void setUrlMap(Map<String, String> urlMap) {
        this.urlMap = urlMap;
    }

    public Integer getOrderType() {
        return this.orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getActivityIds() {
        return this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
