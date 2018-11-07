package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐结果
 */
public class RecommendationTpResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String blockname;// 板块名称

    private Integer cms_num;// CMS的推荐总数

    private Integer num;// 推荐总数（num>=cms_num）

    /*
     * 板块唯一标识
     */
    private String block_id;

    /**
     * 限定推荐的视频类型
     */
    private String type;

    /**
     * 频道Id
     */
    private String cid;

    /**
     * 后台分桶测试标示
     */
    private String bucket;

    /**
     * 页面区域 rec_0001,rec_0002
     */
    private String area;

    /**
     * 本次推荐的事件id
     */
    private String reid;

    /**
     * 上报使用
     */
    private String fragId;

    private List<RecommendationTpResponseRec> rec;// 推荐结果的列表

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReid() {
        return reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }

    public Integer getCms_num() {
        return cms_num;
    }

    public void setCms_num(Integer cms_num) {
        this.cms_num = cms_num;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<RecommendationTpResponseRec> getRec() {
        return rec;
    }

    public void setRec(List<RecommendationTpResponseRec> rec) {
        this.rec = rec;
    }

    public String getBlock_id() {
        return block_id;
    }

    public void setBlock_id(String block_id) {
        this.block_id = block_id;
    }

    public String getFragId() {
        return fragId;
    }

    public void setFragId(String fragId) {
        this.fragId = fragId;
    }
}
