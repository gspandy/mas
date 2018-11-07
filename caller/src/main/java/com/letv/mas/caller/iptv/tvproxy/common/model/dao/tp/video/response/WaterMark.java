package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.List;

public class WaterMark {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8476707546053007516L;

    private String cid;
    private List<WaterMarkImage> imgs;
    private String offset;
    private String pid;

    public String getCid() {
        return this.cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<WaterMarkImage> getImgs() {
        return this.imgs;
    }

    public void setImgs(List<WaterMarkImage> imgs) {
        this.imgs = imgs;
    }

    public String getOffset() {
        return this.offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
