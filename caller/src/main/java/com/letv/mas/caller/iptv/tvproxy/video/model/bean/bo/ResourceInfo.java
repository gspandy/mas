package com.letv.mas.caller.iptv.tvproxy.video.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@ApiModel(value = "ResourceInfo", description = "资源信息")
public abstract class ResourceInfo extends BaseData {

    @ApiModelProperty(value = "推荐数据桶id")
    private String bucket;
    @ApiModelProperty(value = "推荐位标识id")
    private String areaRec;
    @ApiModelProperty(value = "推荐id, 数据上报使用")
    private String reid;
    @ApiModelProperty(value = "板块类型")
    private String blockType;

    /**
     * interface report parameter,distinguish the action type
     */
    @ApiModelProperty(value = "动作类型, 数据上报使用")
    private String action;

    /**
     * interface report parameter,distinguish the exposure type
     */
    @ApiModelProperty(value = "曝光类型, 数据上报使用")
    private String exposure;

    @ApiModelProperty(value = "图片集合")
    private Map<String, String> pic_urls;

    public String getBlockType() {
        return this.blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAreaRec() {
        return this.areaRec;
    }

    public void setAreaRec(String areaRec) {
        this.areaRec = areaRec;
    }

    public String getReid() {
        return this.reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExposure() {
        return this.exposure;
    }

    public void setExposure(String exposure) {
        this.exposure = exposure;
    }

    public Map<String, String> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(Map<String, String> pic_urls) {
        this.pic_urls = pic_urls;
    }

}
