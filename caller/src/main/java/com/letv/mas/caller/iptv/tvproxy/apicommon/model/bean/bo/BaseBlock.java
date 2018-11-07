package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

/**
 * 这个bean用成一个功能性入口配置的通用类
 * 类中包含多个id考虑合并成一个
 */
public class BaseBlock extends BaseData {

    /**
     * 
     */
    private static final long serialVersionUID = 208684563607243988L;

    /**
     * 板块id
     */
    private String blockId;

    /**
     * 版块名称
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
     * 子标签id
     */
    private String labelIdToPlay;

    private int dataType;
    /**
     * 新文案字段，例如按钮文案
     */
    private String extendText;

    private String vipFlag;

    private String extendImg;
    private String buttonImg;
    /*
     * 模块值类型
     */
    private Integer type;

    /**
     * 子类型
     */
    private Integer subType;
    /*
     * 具体页面或路径值，对应唯一存在
     */
    private String Id;

    private String containerId;

    private String channelId;

    private String shortDesc;

    private String pic1;

    private String pic2;

    private String iconType;

    private String url;

    /**
     * 默认选中码流
     */
    private String defaultStream;
    private String defaultStreamName;

    /**
     * 第三方应用code
     */
    private String appCode;

    /**
     * 跳转第三方所需参数
     */
    private String appParam;

    /**
     * lecom套餐包id
     */
    private String packageId;

    /**
     * 乐见桌面发布会版本二屏插入固定数据走cms，需要返回源信息。
     * cms改版后该字段可删除
     */
    private String source;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppParam() {
        return appParam;
    }

    public void setAppParam(String appParam) {
        this.appParam = appParam;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDefaultStream() {
        return defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

    public String getDefaultStreamName() {
        return defaultStreamName;
    }

    public void setDefaultStreamName(String defaultStreamName) {
        this.defaultStreamName = defaultStreamName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
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

    public String getLabelIdToPlay() {
        return this.labelIdToPlay;
    }

    public void setLabelIdToPlay(String labelIdToPlay) {
        this.labelIdToPlay = labelIdToPlay;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getExtendText() {
        return this.extendText;
    }

    public void setExtendText(String extendText) {
        this.extendText = extendText;
    }

    public String getVipFlag() {
        return this.vipFlag;
    }

    public void setVipFlag(String vipFlag) {
        this.vipFlag = vipFlag;
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

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
