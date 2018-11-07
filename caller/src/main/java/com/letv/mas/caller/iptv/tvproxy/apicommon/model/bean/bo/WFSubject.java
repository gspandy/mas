package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;

/**
 * 定制页面
 */
public class WFSubject extends BaseData {

    /**
     * 标题
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
     * 页面ID
     */
    private Integer pageId;

    public int getDataType() {
        return DataConstant.DATA_TYPE_WF_SUBJECT;
    }

    private JumpData<?, ?> WFJump;

    public JumpData<?, ?> getWFJump() {
        return WFJump;
    }

    public void setWFJump(JumpData<?, ?> jump) {
        this.WFJump = jump;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }
}
