package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Actor", description = "演员信息")
public class Actor {

    /**
     * 演员id；2015-10-21，有原来的明星id升级为乐词id
     */
    @ApiModelProperty(value = "演员id")
    private String id;

    /**
     * 演员姓名
     */
    @ApiModelProperty(value = "演员姓名")
    private String name;

    /**
     * 演员角色
     */
    @ApiModelProperty(value = "演员角色: 0-主演, 1-导演, 2-编剧, 3-制片人, 4-主持人")
    private Integer role;//

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String img;

    /**
     * 头像尺寸
     */
    @ApiModelProperty(value = "头像尺寸")
    private String imgSize;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

}
