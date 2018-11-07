package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PromotionDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Author：apple on 17/11/14 10:28
 * eMail：dengliwei@le.com
 */
@ApiModel(value = "AlbumDetailMoreDto", description = "专辑详情动态信息")
public class AlbumDetailMoreDto {

    @ApiModelProperty(value = "活动集合")
    List<PromotionDto> vipActivity;

    /**
     * 标签组: 文本中插入的{icontype}为图标占位符，例如 {score}－豆瓣评分，{rank}－TOP排名，{vcount}－播放次数
     */
    @ApiModelProperty(value = "标签组: 文本中插入的{icontype}为图标占位符，例如 {score}－豆瓣评分，{rank}－TOP排名，{vcount}－播放次数")
    private List<String> tags;

    /**
     * 根据当前专辑相关信息，通过推荐系统获取到的相关内容
     */
    @ApiModelProperty(value = "*根据当前专辑相关信息，通过推荐系统获取到的相关内容")
    private Object relation;

    /**
     * 人工维护相关专辑
     */
    @ApiModelProperty(value = "*人工维护相关专辑")
    private Object relationAdd;

    /**
     * 人工维护相关专辑数量
     */
    @ApiModelProperty(value = "*人工维护相关专辑数量")
    private Integer relationAddCount;

    /**
     * 收藏状态
     */
    @ApiModelProperty(value = "收藏状态")
    private Integer favStatus;

    public List<PromotionDto> getVipActivity() {
        return vipActivity;
    }

    public void setVipActivity(List<PromotionDto> vipActivity) {
        this.vipActivity = vipActivity;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Object getRelation() {
        return relation;
    }

    public void setRelation(Object relation) {
        this.relation = relation;
    }

    public Object getRelationAdd() {
        return relationAdd;
    }

    public void setRelationAdd(Object relationAdd) {
        this.relationAdd = relationAdd;
    }

    public Integer getRelationAddCount() {
        return relationAddCount;
    }

    public void setRelationAddCount(Integer relationAddCount) {
        this.relationAddCount = relationAddCount;
    }

    public Integer getFavStatus() {
        return favStatus;
    }

    public void setFavStatus(Integer favStatus) {
        this.favStatus = favStatus;
    }
}
