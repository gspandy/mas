package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.ButtonDto;

/**
 * 跳频道页按钮信息封装类
 * @author liyunlong
 */
public class ChannelButtonDto extends ButtonDto {

    public ChannelButtonDto(String title, Integer type, Integer browserType, Integer openType) {
        super(title, type, browserType, openType);
    }

    /**
     * 频道ID
     */
    private Integer channelId;
    /**
     * 菜单名称
     */
    private String name;
    private String channelName;

    /**
     * 父频道ID
     */
    private Integer parentChannelId;

    /**
     * 标题数据类型，标识频道是否为儿童、男人或女人频道模式，参见DataConstant.DATA_TYPE_*
     */
    private Integer titleDataType;

    /**
     * 标题icon，焦点图
     */
    private String img;

    /**
     * 标题icon，小图
     */
    private String titleIcon;

    /**
     * 标题背景色，颜色值对应频道墙
     */
    private String titleBgColor;

    /**
     * 标题焦点1，看点，二级标题
     */
    private String titleFocus1;

    /**
     * 标题焦点2，总集或部
     */
    private String titleFocus2;

    /**
     * 内容分类 ID
     */
    private Integer categoryId;

    /**
     * 数据获取URL
     */
    private String dataUrl;

    /**
     * 频道code
     */
    private String channelCode;

    /**
     * 专辑id
     */
    private Integer albumId;

    /**
     * 专题id
     */
    private Integer pid;

    /**
     * 默认码流
     */
    private String defaultStream;

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getParentChannelId() {
        return this.parentChannelId;
    }

    public void setParentChannelId(Integer parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    public Integer getTitleDataType() {
        return this.titleDataType;
    }

    public void setTitleDataType(Integer titleDataType) {
        this.titleDataType = titleDataType;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitleIcon() {
        return this.titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }

    public String getTitleBgColor() {
        return this.titleBgColor;
    }

    public void setTitleBgColor(String titleBgColor) {
        this.titleBgColor = titleBgColor;
    }

    public String getTitleFocus1() {
        return this.titleFocus1;
    }

    public void setTitleFocus1(String titleFocus1) {
        this.titleFocus1 = titleFocus1;
    }

    public String getTitleFocus2() {
        return this.titleFocus2;
    }

    public void setTitleFocus2(String titleFocus2) {
        this.titleFocus2 = titleFocus2;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDataUrl() {
        return this.dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getChannelCode() {
        return this.channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Integer getAlbumId() {
        return this.albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public Integer getPid() {
        return this.pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getDefaultStream() {
        return this.defaultStream;
    }

    public void setDefaultStream(String defaultStream) {
        this.defaultStream = defaultStream;
    }

}
