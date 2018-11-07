package com.letv.mas.caller.iptv.tvproxy.desk.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class DeskDto extends BaseDto {

    private static final long serialVersionUID = -1269141133941271617L;
    /*
     * 标题
     */
    private String title;
    /*
     * 副标题
     */
    private String subTitle;
    /*
     * 图片
     */
    private String img;
    /*
     * 小图
     */
    private String small_img;
    /*
     * ui桌面规范，应用自定义广播类型参数。如TV版定义调用TV版的action
     */
    private String action;
    /*
     * 模块类型，以应用内部模块划分定义，用以确定应用调用模块
     */
    private Integer type;
    /*
     * 桌面模式0点播桌面，1儿童桌面，2音乐桌面
     */
    private Integer model;

    /*
     * 模块值，根据模块类型中该模块下不同具体页面or功能，确定应用最终表现层
     */
    private DeskValueDto value;

    /*
     * 1桌面 2 乐搜 3 外部推送 0 第三方来源
     */
    private Integer resource;

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getModel() {
        return this.model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getResource() {
        return this.resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public DeskValueDto getValue() {
        return this.value;
    }

    public void setValue(DeskValueDto value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSmall_img() {
        return this.small_img;
    }

    public void setSmall_img(String small_img) {
        this.small_img = small_img;
    }

}
