package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;

import java.util.List;

/**
 * 角色播单
 * @author jijianlong
 */
public class RolePlayListDto extends BaseDto {

    private Integer id;
    private String cid;
    private Long vid;
    private Integer fromtype;
    private String title;
    private String pic;
    private String subname;
    private String url;
    private String videoPic;
    private String categroy;
    private Long pid;
    private String videoStatus;
    private String category_url;
    private String subTitle;
    private String dataType;
    private Integer episode;
    /**
     * 分端付费参数集合
     */
    private List<ChargeInfoDto> chargeInfos;

    public List<ChargeInfoDto> getChargeInfos() {
        return chargeInfos;
    }

    public void setChargeInfos(List<ChargeInfoDto> chargeInfos) {
        this.chargeInfos = chargeInfos;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getVid() {
        return this.vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public Integer getFromtype() {
        return this.fromtype;
    }

    public void setFromtype(Integer fromtype) {
        this.fromtype = fromtype;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSubname() {
        return this.subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory_url() {
        return this.category_url;
    }

    public void setCategory_url(String category_url) {
        this.category_url = category_url;
    }

    public String getVideoPic() {
        return this.videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public String getCategroy() {
        return this.categroy;
    }

    public void setCategroy(String categroy) {
        this.categroy = categroy;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getVideoStatus() {
        return this.videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getEpisode() {
        return this.episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

}
