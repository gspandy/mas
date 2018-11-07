package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.channel.constants.ChannelConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "BlockContentDto", description = "CMS配置资源信息")
public class BlockContentDto extends ChannelData {

    /**
     * 版块名称
     */
    @ApiModelProperty(value = "版块名称")
    private String name;

    /**
     * 副标题名称
     */
    @ApiModelProperty(value = "副标题名称")
    private String subName;

    /**
     * 儿童课程表新加字段，0表示课间操，1表示课程
     */
    @ApiModelProperty(value = "儿童课程表新加字段，0表示课间操，1表示课程")
    private Integer type = ChannelConstants.CHANNEL_COURSE_TYPE_OFF_CLASS;

    /**
     * 背景图
     */
    @ApiModelProperty(value = "背景图")
    private String tvPic;

    /**
     * 板块数据内容
     */
    @ApiModelProperty(value = "板块数据内容")
    private List<BaseData> dataList;

    @ApiModelProperty(value = "板块id")
    private Integer id;

    /*
     * subblocks承载
     */
    @ApiModelProperty(value = "子板块数据内容")
    private List<BlockContentDto> dataPackageList;

    /**
     * "我的"页面第3列推荐的数据类型，0--活动，1--专辑
     */
    @ApiModelProperty(value = "推荐的数据类型: 0--活动，1--专辑, 2--微信公众号")
    private Integer recommendDataType;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTvPic() {
        return this.tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

    @Override
    public List<BaseData> getDataList() {
        return this.dataList;
    }

    @Override
    public void setDataList(List<BaseData> dataList) {
        this.dataList = dataList;
    }

    public List<BlockContentDto> getDataPackageList() {
        return this.dataPackageList;
    }

    public void setDataPackageList(List<BlockContentDto> dataPackageList) {
        this.dataPackageList = dataPackageList;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubName() {
        return this.subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRecommendDataType() {
        return this.recommendDataType;
    }

    public void setRecommendDataType(Integer recommendDataType) {
        this.recommendDataType = recommendDataType;
    }

}
