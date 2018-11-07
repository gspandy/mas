package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.channel.model.dto.Channel;

/**
 * 定制页面
 */
public class Page extends Channel {

    /**
     * 
     */
    private static final long serialVersionUID = -4347492328935847225L;

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
        return DataConstant.DATA_TYPE_PAGE;
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
