package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;

/**
 * 剧集播放列表分页数据
 * @author KevinYi
 */
@ApiModel(value = "AlbumSeriesPlayListPageDto", description = "剧集播放列表分页数据")
public class AlbumSeriesPlayListPageDto {

    /**
     * 所属专辑id
     */
    @ApiModelProperty(value = "所属专辑id")
    private String albumId;

    /**
     * 专辑是否正片专辑
     */
    @ApiModelProperty(value = "专辑是否正片专辑")
    private Integer albumPositive;

    /**
     * source
     * type，如正片、周边视频、推荐等，参见VideoConstants.ALBUM_VIDEO_PLAY_LIST_SOURCE_TYPE_*
     */
    @ApiModelProperty(value = "专辑类型，如0--所有剧集，1--正片剧集（选集），2--预告，3--周边视频，4--推荐")
    private Integer stype;

    /**
     * 当前第几页
     */
    @ApiModelProperty(value = "当前第几页")
    private Integer curPage;

    /**
     * 共几页
     */
    @ApiModelProperty(value = "共几页")
    private Integer totalPage;

    @ApiModelProperty(value = "分页大小")
    private Integer pageSize;

    /**
     * 剧集总数
     */
    @ApiModelProperty(value = "剧集总数")
    private Integer totalNum;

    /**
     * -1正序，1倒序
     */
    @ApiModelProperty(value = "剧集排序，-1正序，1倒序")
    private Integer orderType;

    /**
     * （大陆版需求）是否展示成综艺样式（或，Lecon需求--是否按“剧集”展示）,1-是,0-否
     */
    @ApiModelProperty(value = "（大陆版需求）是否展示成综艺样式（或，Lecon需求--是否按“剧集”展示）,1-是,0-否")
    private Integer varietyShow;

    /**
     * 本页剧集对应的标题（不同场景下的数据，有不同title)
     */
    @ApiModelProperty(value = "本页剧集对应的标题（不同场景下的数据，有不同title)")
    private String title;

    /**
     * Jumper集合
     */
    @ApiModelProperty(value = "Jumper集合")
    private Collection<BaseData> data;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public Integer getAlbumPositive() {
        return albumPositive;
    }

    public void setAlbumPositive(Integer albumPositive) {
        this.albumPositive = albumPositive;
    }

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getVarietyShow() {
        return varietyShow;
    }

    public void setVarietyShow(Integer varietyShow) {
        this.varietyShow = varietyShow;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Collection<BaseData> getData() {
        return data;
    }

    public void setData(Collection<BaseData> data) {
        this.data = data;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

}
