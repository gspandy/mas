package com.letv.mas.caller.iptv.tvproxy.recommendation.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 推荐结果
 */
@ApiModel(value = "Recommendation", description = "推荐类")
public class Recommendation extends BaseDto implements Comparable<Recommendation> {

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private String orderNum;

    /**
     * 板块名称
     */
    @ApiModelProperty(value = "板块名称")
    private String blockName;

    /**
     * 板块类型
     */
    @ApiModelProperty(value = "板块类型")
    private String blockType;

    /**
     * 内容分类
     */
    @ApiModelProperty(value = "内容分类")
    private String categoryId;

    /**
     * 数据
     */
    private List<BaseData> dataList;

    @ApiModelProperty(value = "bucket")
    private String bucket;
    @ApiModelProperty(value = "area")
    private String area;
    @ApiModelProperty(value = "reid")
    private String reid;
    @ApiModelProperty(value = "type")
    private String type;
    @ApiModelProperty(value = "blockId")
    private String blockId;
    @ApiModelProperty(value = "rec_id")
    private String rec_id;
    @ApiModelProperty(value = "exp_var_id")
    private String exp_var_id;
    @ApiModelProperty(value = "versiontype")
    private String versiontype;
    @ApiModelProperty(value = "fragId")
    private String fragId;

    public String getFragId() {
        return fragId;
    }

    public void setFragId(String fragId) {
        this.fragId = fragId;
    }

    public String getRec_id() {
        return this.rec_id;
    }

    public void setRec_id(String rec_id) {
        this.rec_id = rec_id;
    }

    public String getExp_var_id() {
        return this.exp_var_id;
    }

    public void setExp_var_id(String exp_var_id) {
        this.exp_var_id = exp_var_id;
    }

    public String getVersiontype() {
        return this.versiontype;
    }

    public void setVersiontype(String versiontype) {
        this.versiontype = versiontype;
    }

    public String getBlockId() {
        return this.blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReid() {
        return this.reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBlockName() {
        return this.blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockType() {
        return this.blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<BaseData> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<BaseData> dataList) {
        this.dataList = dataList;
    }

    /**
     * 排序
     */
    @Override
    public int compareTo(Recommendation o) {
        int orderThis = Integer.valueOf(this.getOrderNum().substring(4));
        int orderO = Integer.valueOf(o.getOrderNum().substring(4));

        if (orderThis >= orderO) {
            return 1;
        } else {
            return -1;
        }
    }
}
