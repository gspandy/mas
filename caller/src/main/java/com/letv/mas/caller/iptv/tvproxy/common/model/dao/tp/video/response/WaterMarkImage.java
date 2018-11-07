package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@ApiModel(value = "WaterMarkImage", description = "水印图片信息")
public class WaterMarkImage implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = -1143847231334150295L;
    @ApiModelProperty(value = "水印持续时间")
    private String lasttime;// 水印持续时间
    @ApiModelProperty(value = "水印外链地址")
    private String link;// 水印外链地址
    @ApiModelProperty(value = "水印位置 1为左上角 2为右上角 3为左下角 4为右下角")
    private String position;// 水印位置 1为左上角 2为右上角 3为左下角 4为右下角
    @ApiModelProperty(value = "水印图片url，尺寸100")
    private String url100;// 水印图片url
    @ApiModelProperty(value = "水印图片url，尺寸30")
    private String url30;// 水印图片url
    @ApiModelProperty(value = "水印图片url，尺寸50")
    private String url50;// 水印图片url
    @ApiModelProperty(value = "水印图片url，尺寸80")
    private String url80;// 水印图片url

    public String getLasttime() {
        return this.lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUrl100() {
        return this.url100;
    }

    public void setUrl100(String url100) {
        this.url100 = url100;
    }

    public String getUrl30() {
        return this.url30;
    }

    public void setUrl30(String url30) {
        this.url30 = url30;
    }

    public String getUrl50() {
        return this.url50;
    }

    public void setUrl50(String url50) {
        this.url50 = url50;
    }

    public String getUrl80() {
        return this.url80;
    }

    public void setUrl80(String url80) {
        this.url80 = url80;
    }

}
