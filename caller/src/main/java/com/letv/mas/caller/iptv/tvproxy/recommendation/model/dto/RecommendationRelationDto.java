package com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecBaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "RecommendationRelationDto", description = "专辑相关内容推荐聚集类")
public class RecommendationRelationDto extends BaseData {
    /**
     *
     */
    private static final long serialVersionUID = 2236293334935382449L;
    @ApiModelProperty(value = "相关内容的名称")
    private String cName; // 相关内容的名称
    @ApiModelProperty(value = "相关内容的推荐集合")
    private List<RelationResource> resources;

    public String getcName() {
        return this.cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public List<RelationResource> getResources() {
        return this.resources;
    }

    public void setResources(List<RelationResource> resources) {
        this.resources = resources;
    }

    @ApiModel(value = "RelationResource", description = "专辑相关内容推荐类")
    public static class RelationResource extends BaseData {
        /**
         *
         */
        private static final long serialVersionUID = -2015232151939185736L;
        @ApiModelProperty(value = "ID")
        private Long id;
        @ApiModelProperty(value = "类型")
        private String type;
        @ApiModelProperty(value = "929x466 图片")
        private String img929x466;
        @ApiModelProperty(value = "400x300 图片")
        private String img400x300;
        @ApiModelProperty(value = "615x225 图片")
        private String img615x225;
        @ApiModelProperty(value = "标题")
        private String title;
        @ApiModelProperty(value = "副标题")
        private String subTitle;
        @ApiModelProperty(value = "简介")
        private String shortDesc;
        @ApiModelProperty(value = "pos")
        private String pos;
        @ApiModelProperty(value = "分类ID")
        private Integer categoryId;
        @ApiModelProperty(value = "专辑ID")
        private Long albumId;
        @ApiModelProperty(value = "episode")
        private Integer episode;
        @ApiModelProperty(value = "porder")
        private int porder;
        @ApiModelProperty(value = "url")
        private String url;
        @ApiModelProperty(value = "子分类ID")
        private String subCategoryId;

        public RelationResource() {
        }

        public RelationResource(RecBaseResponse.RecommendDetail rec) {
            if (rec != null) {
                this.id = rec.getPid();
                this.albumId = rec.getPid();
                this.title = rec.getPidname();
                this.subTitle = rec.getSubtitle();
                this.shortDesc = rec.getDescription();
                this.categoryId = rec.getCid();
                this.episode = rec.getEpisodes();
                this.img400x300 = rec.getPicurl();
                this.img615x225 = rec.getPicurl();
                this.subCategoryId = rec.getAlbum_sub_category_code();
            }
        }

        public Long getId() {
            return this.id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg929x466() {
            return this.img929x466;
        }

        public void setImg929x466(String img929x466) {
            this.img929x466 = img929x466;
        }

        public String getImg400x300() {
            return this.img400x300;
        }

        public void setImg400x300(String img400x300) {
            this.img400x300 = img400x300;
        }

        public String getImg615x225() {
            return this.img615x225;
        }

        public void setImg615x225(String img615x225) {
            this.img615x225 = img615x225;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getShortDesc() {
            return this.shortDesc;
        }

        public void setShortDesc(String shortDesc) {
            this.shortDesc = shortDesc;
        }

        public String getPos() {
            return this.pos;
        }

        public void setPos(String pos) {
            this.pos = pos;
        }

        public Integer getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Long getAlbumId() {
            return this.albumId;
        }

        public void setAlbumId(Long albumId) {
            this.albumId = albumId;
        }

        public Integer getEpisode() {
            return this.episode;
        }

        public void setEpisode(Integer episode) {
            this.episode = episode;
        }

        public int getPorder() {
            return this.porder;
        }

        public void setPorder(int porder) {
            this.porder = porder;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSubCategoryId() {
            return this.subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

    }

}
