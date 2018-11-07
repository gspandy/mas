package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

/**
 * 新型电视剧推广容器
 * @author jijianlong
 */
public class Container extends BaseData {

    /**
     *
     */
    private static final long serialVersionUID = -3899560950331753429L;

    /**
     * 容器id
     */
    private String containerId;

    /**
     * 标题名称
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

    private int dataType;
    /**
     * 子标签id
     */
    private String labelIdToPlay;

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

    public String getLabelIdToPlay() {
        return this.labelIdToPlay;
    }

    public void setLabelIdToPlay(String labelIdToPlay) {
        this.labelIdToPlay = labelIdToPlay;
    }

    public String getContainerId() {
        return this.containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

}
