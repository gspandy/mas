package com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RecommendationMaybeLikeDto", description = "猜你喜欢推荐类")
public class RecommendationMaybeLikeDto extends BaseData {
    /**
     *
     */
    private static final long serialVersionUID = -2286834844340127171L;
    @ApiModelProperty(value = "pushFlag")
    private String pushFlag;
    @ApiModelProperty(value = "categoryName")
    private String categoryName;
    @ApiModelProperty(value = "poster20")
    private String poster20;
    @ApiModelProperty(value = "model")
    private String model;
    @ApiModelProperty(value = "name")
    private String name;
    @ApiModelProperty(value = "aid")
    private String aid;
    @ApiModelProperty(value = "src")
    private String src;
    @ApiModelProperty(value = "subTitle")
    private String subTitle;
    @ApiModelProperty(value = "img400x300")
    private String img400x300;
    @ApiModelProperty(value = "img")
    private String img;
    @ApiModelProperty(value = "dataType")
    private Integer dataType;
    @ApiModelProperty(value = "vid")
    private Long vid;
    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;
    @ApiModelProperty(value = "来源")
    private String score;
    @ApiModelProperty(value = "nowEpisodes")
    private String nowEpisodes;
    @ApiModelProperty(value = "子分类ID")
    private String subCategoryId;
    @ApiModelProperty(value = "globalId")
    private String globalId;

    public RecommendationMaybeLikeDto() {
    }

    public RecommendationMaybeLikeDto(AlbumMysqlTable album) {
        if (album != null) {
            this.aid = album.getId() + "";
            this.pushFlag = album.getPlay_platform();
            this.categoryName = album.getCategory_name();
            this.poster20 = album.getPic(300, 400);
            this.model = "search";
            this.name = album.getName_cn();
            this.subTitle = album.getSub_title();
            this.src = "1"; // 内网数据
            this.img400x300 = album.getPic(400, 300);
        }
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getScore() {
        return this.score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNowEpisodes() {
        return this.nowEpisodes;
    }

    public void setNowEpisodes(String nowEpisodes) {
        this.nowEpisodes = nowEpisodes;
    }

    public String getSubCategoryId() {
        return this.subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public Long getVid() {
        return this.vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    @Override
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getPushFlag() {
        return this.pushFlag;
    }

    public void setPushFlag(String pushFlag) {
        this.pushFlag = pushFlag;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPoster20() {
        return this.poster20;
    }

    public void setPoster20(String poster20) {
        this.poster20 = poster20;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getImg400x300() {
        return this.img400x300;
    }

    public void setImg400x300(String img400x300) {
        this.img400x300 = img400x300;
    }

}
