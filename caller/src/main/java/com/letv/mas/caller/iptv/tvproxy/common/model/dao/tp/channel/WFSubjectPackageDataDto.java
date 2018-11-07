package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 新瀑布流（专辑、视频或直播）数据包内（专辑、视频或直播）数据元素信息封装类，对应一个专辑、视频或直播信息
 */
@ApiModel(value = "WFSubjectPackageDataDto", description = "新瀑布流（专辑、视频或直播）数据包内（专辑、视频或直播）数据元素信息封装类，对应一个专辑、视频或直播信息")
public class WFSubjectPackageDataDto {

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    /**
     * 介绍
     */
    @ApiModelProperty(value = "介绍")
    private List<String> content;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String tvPic;

    /**
     * icon
     */
    @ApiModelProperty(value = "icon")
    private Integer icon;

    /**
     * button
     */
    @ApiModelProperty(value = "button")
    private List<WFSubjectPackageDataDto> button;

    /**
     * 分类
     */
    @ApiModelProperty(value = "分类")
    private Integer categoryId;

    /**
     * 数据类型，1--视频，2--专辑
     */
    @ApiModelProperty(value = "数据类型，1--视频，2--专辑")
    private Integer dType;

    /**
     * 模块的模版类型
     */
    @ApiModelProperty(value = "模块的模版类型")
    private Integer mType;

    /**
     * 专辑、视频或直播id
     */
    @ApiModelProperty(value = "专辑、视频或直播id")
    private String id;

    /**
     * 父ID
     */
    @ApiModelProperty(value = "当类型是视频ID时,fId是专辑ID")
    private String fId;

    /**
     * 预留字段
     */
    @ApiModelProperty(value = "预留字段")
    private String extend;

    /**
     * 图片列表，瀑布流专题封面图在此列表
     */
    @ApiModelProperty(value = "图片列表")
    private Map<String, String> picList;

    public Map<String, String> getPicList() {
        return picList;
    }

    public void setPicList(Map<String, String> picList) {
        this.picList = picList;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getTvPic() {
        return tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public List<WFSubjectPackageDataDto> getButton() {
        return button;
    }

    public void setButton(List<WFSubjectPackageDataDto> button) {
        this.button = button;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getdType() {
        return dType;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(value = "跳转数据集合")
    private JumpData<?, ?> jump;

    @ApiModelProperty(value = "跳转类型")
    private int dataType;

    public JumpData<?, ?> getJump() {
        return jump;
    }

    public void setJump(JumpData<?, ?> jump) {
        this.jump = jump;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public static class JumpData<T, E> {
        /**
         * 跳转类型
         */
        @ApiModelProperty(value = "跳转类型")
        private Integer type;

        /**
         * 跳转信息体
         */
        @ApiModelProperty(value = "跳转信息体")
        private T Value;

        /**
         * 扩展对象，承接action
         */
        @ApiModelProperty(value = "扩展对象，承接action")
        private E extend;

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public T getValue() {
            return this.Value;
        }

        public void setValue(T value) {
            this.Value = value;
        }

        public E getExtend() {
            return extend;
        }

        public void setExtend(E extend) {
            this.extend = extend;
        }
    }
}