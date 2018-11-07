package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by maning on 2017/8/29.
 */
@ApiModel(value = "WFSubjectPackageDto", description = "瀑布流各专题模块")
public class WFSubjectPackageDto {

    /**
     * 专题模块ID
     */
    @ApiModelProperty(value = "专题模块ID")
    private String id;

    /**
     * 模块类型
     */
    @ApiModelProperty(value = "模块类型")
    private Integer type;

    /**
     * 模块名
     */
    @ApiModelProperty(value = "模块名")
    private String name;

    /**
     * 专题包的排序，porder越小越靠前
     */
    @ApiModelProperty(value = "专题包的排序，porder越小越靠前")
    private Integer porder;

    /**
     * 特辑包类型，1-专辑包，2-视频包，3-直播包
     */
    @ApiModelProperty(value = "特辑包类型，1-专辑包，2-视频包，3-直播包")
    private Integer ptype;

    /**
     * 所属专题id
     */
    @ApiModelProperty(value = "所属专题id")
    private String fId;

    /**
     * 专辑包图片
     */
    @ApiModelProperty(value = "专辑包图片")
    private String packagePic;

    /**
     * 剧集包、视频包或直播包数据列表
     */
    @ApiModelProperty(value = "剧集包、视频包或直播包数据列表")
    private List<WFSubjectPackageDataDto> dataList;

    /**
     * 预留字段
     */
    @ApiModelProperty(value = "预留字段")
    private String extend;

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPorder() {
        return porder;
    }

    public void setPorder(Integer porder) {
        this.porder = porder;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getPackagePic() {
        return packagePic;
    }

    public void setPackagePic(String packagePic) {
        this.packagePic = packagePic;
    }

    public List<WFSubjectPackageDataDto> getDataList() {
        return dataList;
    }

    public void setDataList(List<WFSubjectPackageDataDto> dataList) {
        this.dataList = dataList;
    }

}
