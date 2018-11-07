package com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendationNavigationTpResponseRec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RecommendationChildTagDTO", description = "儿童推荐类")
public class RecommendationChildTagDTO {
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "图片")
    private String tvPic;

    public RecommendationChildTagDTO(RecommendationNavigationTpResponseRec rec) {
        this.title = rec.getTitle();
        this.content = rec.getContent();
        this.tvPic = rec.getTvPic();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTvPic() {
        return this.tvPic;
    }

    public void setTvPic(String tvPic) {
        this.tvPic = tvPic;
    }

}
