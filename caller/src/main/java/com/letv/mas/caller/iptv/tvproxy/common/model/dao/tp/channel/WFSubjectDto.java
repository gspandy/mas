package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by maning on 2017/8/29.
 */
@ApiModel(value = "WFSubjectDto", description = "瀑布流专题")
public class WFSubjectDto {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private String id;

    /**
     * 专题名称
     */
    @ApiModelProperty(value = "专题名称")
    private String name;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String subName;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String tvPic;

    /**
     * 专题类型 暂不用
     */
    @ApiModelProperty(value = "专题类型")
    private Integer subjectType;

    /**
     * 专题发布名称
     */
    @ApiModelProperty(value = "专题发布名称")
    private String pubName;

    /**
     * 运营上报使用，由CMS接口返回
     */
    @ApiModelProperty(value = "运营上报使用，由CMS接口返回")
    private String cid;

    /**
     * 专题模块集合，第一个是banner模块，其余是内容模块
     */
    @ApiModelProperty(value = "专题模块集合，第一个是banner模块，其余是内容模块")
    private List<WFSubjectPackageDto> packageList;

    /**
     * 专题Id
     */
    @ApiModelProperty(value = "专题Id")
    private String subjectId;

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

    public String getTvPic() {
        return tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    public Integer getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<WFSubjectPackageDto> getPackageList() {
        return packageList;
    }

    public void setPackageList(List<WFSubjectPackageDto> packageList) {
        this.packageList = packageList;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public WFSubjectPackageDto getBanner() {
        WFSubjectPackageDto bannner = null;
        if (packageList != null && packageList.size() > 0) {
            bannner = packageList.get(0);
        }
        return bannner;
    }
}
