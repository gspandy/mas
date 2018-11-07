package com.letv.mas.caller.iptv.tvproxy.live.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.IconConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LiveConstants;

/**
 * 直播
 */
public class Live extends BaseData {

    /**
     * 直播ID
     */
    private String liveId;

    /**
     * 名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subName;

    /**
     * 图片
     */
    private String img;

    /**
     * 直播类别
     */
    private String categoryId;

    /**
     * 是否付费 false：付费 true：免费
     * 2016-04-12，客户端直播model采用LiveProject设计，这里需要兼容
     */
    private boolean isFree = true;

    private String iconType = IconConstants.ICON_TYPE_LIVE;

    /**
     * 直播资源类型，1--普通直播，2--轮播，3--卫视台，默认普通直播
     */
    private Integer sourceType = LiveConstants.LIVE_SOURCE_TYPE_LIVE;

    private Integer state;// 1--未开始，2--进行中，4--回看

    public String getIconType() {
        if (!this.isFree && this.iconType != null) {
            this.iconType = IconConstants.ICON_TYPE_VIP;
        }
        return this.iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    @Override
    public int getDataType() {
        return DataConstant.DATA_TYPE_LIVE;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLiveId() {
        return this.liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean getIsFree() {
        return this.isFree;
    }

    public void setIsFree(boolean isFree) {
        this.isFree = isFree;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

}
