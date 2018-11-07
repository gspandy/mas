package com.letv.mas.caller.iptv.tvproxy.channel.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;

/**
 * 内容包
 */
public class ContentPackageDto extends BaseData {
    private static final long serialVersionUID = 5256908997179398018L;
    private Integer ctype; // 内容类型
    private String name;
    private String subName;
    private Long id;
    private Long albumId;
    private Long videoId;
    private String img;
    private String imgSize;
    private Integer categoryId;
    private Float score; // 评分

    public ContentPackageDto(AlbumMysqlTable album, CommonParam commonParam) {
        this.ctype = VipTpConstant.ContentType.ALBUM.getCode();
        this.setDataType(DataConstant.DATA_TYPE_ALBUM);
        this.id = album.getId();
        this.albumId = album.getId();
        this.name = album.getName_cn();
        this.subName = album.getSub_title();
        this.categoryId = album.getCategory();
        this.imgSize = "300*400";
        this.img = album.getPic(300, 400);
        this.setScore(album.getScore());
    }

    public ContentPackageDto(VideoMysqlTable video, CommonParam commonParam) {
        this.ctype = VipTpConstant.ContentType.VIDEO.getCode();
        this.setDataType(DataConstant.DATA_TYPE_VIDEO);
        this.id = video.getId();
        this.videoId = video.getId();
        this.albumId = video.getItv_album_id();
        this.name = video.getName_cn();
        this.subName = video.getSub_title();
        this.imgSize = "400*300";
        this.img = video.getPic(400, 300);
        this.setScore(video.getScore());
    }

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
