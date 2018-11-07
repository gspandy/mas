package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

/**
 * （专辑、视频或直播）数据包内（专辑、视频或直播）数据元素信息封装类，对应一个专辑、视频或直播信息
 * @author
 */
public class VideoContainerDto extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = -6034823458362958744L;

    /**
     * 专辑、视频或直播id
     */
    private String id;

    /**
     * 专辑id
     */
    private String albumId;

    /**
     * 专辑、视频或直播名称
     */
    private String name;

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
    private Integer categoryId;

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

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
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

    public int getDataType() {
        return DataConstant.DATA_TYPE_VIDEO;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
