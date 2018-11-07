package com.letv.mas.caller.iptv.tvproxy.desk.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

public class DeskValueDto extends BaseDto {

    /*
     * 模块值类型
     */
    private Integer Type;
    /*
     * 具体页面或路径值，对应唯一存在
     */
    private String Id;
    /*
     * 是否视频直接播放
     */
    private Boolean Isvideo;
    /*
     * 码流
     */
    private String Streamcode;
    /*
     * 码流名称
     */
    private String Streamname;
    /*
     * 视频名称
     */
    private String Videoname;
    /*
     * 专辑id
     */
    private String Albumid;
    /*
     * 频道id
     */
    private String Categoryid;

    private String sereis;

    public Integer getType() {
        return this.Type;
    }

    public void setType(Integer type) {
        this.Type = type;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public Boolean getIsvideo() {
        return this.Isvideo;
    }

    public void setIsvideo(Boolean isvideo) {
        this.Isvideo = isvideo;
    }

    public String getStreamcode() {
        return this.Streamcode;
    }

    public void setStreamcode(String streamcode) {
        this.Streamcode = streamcode;
    }

    public String getStreamname() {
        return this.Streamname;
    }

    public void setStreamname(String streamname) {
        this.Streamname = streamname;
    }

    public String getVideoname() {
        return this.Videoname;
    }

    public void setVideoname(String videoname) {
        this.Videoname = videoname;
    }

    public String getAlbumid() {
        return this.Albumid;
    }

    public void setAlbumid(String albumid) {
        this.Albumid = albumid;
    }

    public String getCategoryid() {
        return this.Categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.Categoryid = categoryid;
    }

    public String getSereis() {
        return this.sereis;
    }

    public void setSereis(String sereis) {
        this.sereis = sereis;
    }
}