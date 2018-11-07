package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * （专辑、视频或直播）数据包内（专辑、视频或直播）数据元素信息封装类，对应一个专辑、视频或直播信息
 * @author KevinYi
 */
public class SubjectPackageDataDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1671101172118589726L;

    /**
     * 专辑、视频或直播id
     */
    private String id;

    /**
     * 专辑、视频或直播名称
     */
    private String name;

    /**
     * 分端付费参数集合
     */
    private List<ChargeInfoDto> chargeInfos;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 图片
     */
    private String tvPic;

    /**
     * 分类
     */
    private String categoryId;

    /**
     * 数据类型，1--专辑，2--视频，4--直播
     */
    private Integer dataType;

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

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTvPic() {
        return this.tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDataType() {
        return this.dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public List<ChargeInfoDto> getChargeInfos() {
        return chargeInfos;
    }

    public void setChargeInfos(List<ChargeInfoDto> chargeInfos) {
        this.chargeInfos = chargeInfos;
    }
}
