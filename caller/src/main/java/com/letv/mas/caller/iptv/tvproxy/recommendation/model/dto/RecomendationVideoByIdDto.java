package com.letv.mas.caller.iptv.tvproxy.recommendation.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.response.RecommendByVideoResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.TimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "RecomendationVideoByIdDto", description = "以片搜片类")
public class RecomendationVideoByIdDto extends BaseData {
    /**
     *
     */
    private static final long serialVersionUID = -1212291726628157614L;
    @ApiModelProperty(value = "专辑ID")
    private Long id;// 专辑ID
    @ApiModelProperty(value = "小图")
    private String smallImage;
    @ApiModelProperty(value = "大图")
    private String bigImage;
    @ApiModelProperty(value = "专辑ID")
    private Long iptvAlbumId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "来源 1:乐视网 2:外网")
    private String src; // 1:乐视网 2:外网
    @ApiModelProperty(value = "平分显示 得分/人数")
    private String fraction;// 平分显示 得分/人数
    @ApiModelProperty(value = "平分")
    private Float rating;// 平分
    @ApiModelProperty(value = "平分人数")
    private Integer votes;// 平分人数
    @ApiModelProperty(value = "导演")
    private String director;// 导演
    @ApiModelProperty(value = "主演")
    private String actor;// 主演
    @ApiModelProperty(value = "区域")
    private String area;
    @ApiModelProperty(value = "releaseDate")
    private String releaseDate;
    @ApiModelProperty(value = "description")
    private String description;
    @ApiModelProperty(value = "是否是院线")
    private Integer isYuanXian;
    @ApiModelProperty(value = "播放码流")
    private String playStream;

    public RecomendationVideoByIdDto() {
    }

    public RecomendationVideoByIdDto(RecommendByVideoResponse.Rec rec) {
        if (rec != null) {
            this.id = rec.getAid();
            this.iptvAlbumId = rec.getAid();
            this.smallImage = rec.getPoster();
            this.bigImage = rec.getPoster();
            this.name = rec.getName();
            this.src = rec.getSrc();
        }
    }

    public RecomendationVideoByIdDto(AlbumMysqlTable album) {
        if (album != null) {
            this.id = album.getId();
            this.iptvAlbumId = album.getItv_album_id();
            this.name = album.getName_cn();
            String date = TimeUtil
                    .getDateString(TimeUtil.parseDate(album.getRelease_date(), TimeUtil.SHORT_DATE_FORMAT),
                            TimeUtil.SHORT_DATE_FORMAT);
            this.releaseDate = date == null ? "" : date;
            this.area = album.getArea_name();
            this.smallImage = album.getPic(120, 160);
            this.bigImage = album.getPic(300, 400);
            this.fraction = album.getScore() + "/10";
            this.rating = album.getScore();
            this.director = album.getDirectory();
            this.description = album.getDescription();
            this.isYuanXian = album.getIsyuanxian();
            this.playStream = album.getPlay_streams();
            this.actor = album.getActor_all();
            this.votes = 10;
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmallImage() {
        return this.smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return this.bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public Long getIptvAlbumId() {
        return this.iptvAlbumId;
    }

    public void setIptvAlbumId(Long iptvAlbumId) {
        this.iptvAlbumId = iptvAlbumId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFraction() {
        return this.fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public Float getRating() {
        return this.rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return this.votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsYuanXian() {
        return this.isYuanXian;
    }

    public void setIsYuanXian(Integer isYuanXian) {
        this.isYuanXian = isYuanXian;
    }

    public String getPlayStream() {
        return this.playStream;
    }

    public void setPlayStream(String playStream) {
        this.playStream = playStream;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
