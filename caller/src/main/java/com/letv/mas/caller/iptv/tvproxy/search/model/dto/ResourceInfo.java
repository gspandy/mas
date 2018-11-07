package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ResourceInfo extends AlbumInfoDto {
    private final Logger log = LoggerFactory.getLogger(ResourceInfo.class);

    private Integer resourceType;
    private String codeVersion;
    private Integer vrsVideoinfoId;
    private String pic;
    private String streamName;
    private Boolean isAlbum = true;// 默认是专辑中的视频
    private Long vid;
    private String singer;
    /**
     * 推荐相关参数,用于统计==start
     */
    private String bucket;
    private String areaRec;
    private String reid;
    private String is_rec;
    private CommonParam commonParam;

    /**
     * 推荐相关参数,用于统计==end
     */
    public ResourceInfo() {
    }

    public ResourceInfo(AlbumMysqlTable itv_Album, CommonParam commonParam) {
        super(itv_Album, commonParam);
        this.commonParam = commonParam;
    }

    @Deprecated
    public ResourceInfo(VideoMysqlTable videoInfo) {
        if (videoInfo != null) {
            this.setVideoType("video");
            this.resourceType = 1;
            if (videoInfo.getPid() == null || videoInfo.getPid().longValue() == 0) {
                this.isAlbum = false;
            }
            this.vrsVideoinfoId = StringUtil.toInteger(videoInfo.getVideo_info_id() + "", 0);
            this.vid = videoInfo.getId();
            this.setStreamName(LetvStreamCommonConstants.nameOf(this.codeVersion));
            this.pic = videoInfo.getPic(400, 300);
            this.setId(videoInfo.getPid());
            this.setIptvAlbumId(videoInfo.getItv_album_id());
            this.setName(videoInfo.getName_cn());
            this.setCategoryId(videoInfo.getCategory_all());
            this.setNewCategoryId(videoInfo.getCategory());
            this.setCategoryName(videoInfo.getCategory_name_all());
            this.setSubcategoryId(videoInfo.getSub_category_all());
            this.setSubcategoryName(videoInfo.getSub_category_name_all());
            this.setDirectory(videoInfo.getDirectory_all());
            this.setStarring(videoInfo.getStarring_all());
            this.setReleaseDate(videoInfo.getRelease_date());
            this.setAlbumTypeId(videoInfo.getVideo_type());
            this.setAlbumTypeName(videoInfo.getVideo_type_name());
            this.setRecordCompany(videoInfo.getRecord_company());
            this.setStyleId(videoInfo.getStyle_all());
            this.setStyleName(videoInfo.getStyle_all_name());
            this.setFirstPlayTime(videoInfo.getFirst_play_time());
            this.setArea(videoInfo.getArea());
            this.setAreaName(videoInfo.getArea_name());
            this.setSmallImage(videoInfo.getPic(120, 160));
            this.setThumbnailImage(videoInfo.getPic(120, 160));
            this.setBigImage(videoInfo.getPic(150, 200));
            this.setImg_vertical_300x400(videoInfo.getPic(300, 400));
            this.setTv_out(videoInfo.isTV() ? 0 : 1);
            this.setPic_400X300(videoInfo.getPic(400, 300));
            this.setDesc(videoInfo.getDescription());
            this.singer = videoInfo.getSinger();
            if (this.singer != null) {
                this.singer = this.singer.replaceAll(",", " ").trim();
            }
            // for tvod icon type search
            if (!StringUtil.isBlank(videoInfo.getPay_platform()) && videoInfo.getPay_platform().contains("141007")) {
                this.setIfCharge("1");
            } else {
                this.setIfCharge("0");
            }
            Integer chargeType = JumpUtil.getChargeType(videoInfo.getPay_platform(), this.commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(videoInfo.getPay_platform());
            }
            this.setChargeType(chargeType);
        }
    }

    /*
     * public ResourceInfo(IptvVideoInfo videoInfo) {
     * if (videoInfo != null) {
     * this.setVideoType("video");
     * this.resourceType = 1;
     * if (videoInfo.getPid() == null
     * || videoInfo.getPid().longValue() == 0) {
     * this.isAlbum = false;
     * }
     * this.vrsVideoinfoId = StringUtil.toInteger(
     * videoInfo.getVideo_info_id() + "", 0);
     * this.vid = videoInfo.getId();
     * this.setStreamName(StreamConstants.nameOf(this.codeVersion));
     * this.pic = videoInfo.getPic(400, 300);
     * this.setId(videoInfo.getPid());
     * this.setIptvAlbumId(videoInfo.getItv_album_id());
     * this.setName(videoInfo.getName_cn());
     * this.setCategoryId(videoInfo.getCategory_all());
     * this.setNewCategoryId(videoInfo.getCategory());
     * this.setCategoryName(videoInfo.getCategory_name_all());
     * this.setSubcategoryId(videoInfo.getSub_category_all());
     * this.setSubcategoryName(videoInfo.getSub_category_name_all());
     * this.setDirectory(videoInfo.getDirectory_all());
     * this.setStarring(videoInfo.getStarring_all());
     * this.setReleaseDate(videoInfo.getRelease_date());
     * this.setAlbumTypeId(videoInfo.getVideo_type());
     * this.setAlbumTypeName(videoInfo.getVideo_type_name());
     * this.setRecordCompany(videoInfo.getRecord_company());
     * this.setStyleId(videoInfo.getStyle_all());
     * this.setStyleName(videoInfo.getStyle_all_name());
     * this.setFirstPlayTime(videoInfo.getFirst_play_time());
     * this.setArea(videoInfo.getArea());
     * this.setAreaName(videoInfo.getArea_name());
     * this.setSmallImage(videoInfo.getPic(120, 160));
     * this.setThumbnailImage(videoInfo.getPic(120, 160));
     * this.setBigImage(videoInfo.getPic(150, 200));
     * this.setImg_vertical_300x400(videoInfo.getPic(300, 400));
     * this.setTv_out(videoInfo.isTV() ? 0 : 1);
     * this.setPic_400X300(videoInfo.getPic(400, 300));
     * this.setDesc(videoInfo.getDescription());
     * this.singer = videoInfo.getSinger();
     * if (this.singer != null) {
     * this.singer = this.singer.replaceAll(",", " ").trim();
     * }
     * }
     * }
     */

    public ResourceInfo(SearchData v) {
        if (v != null) {
            this.setVrsVideoinfoId(v.getVideo_info_id().intValue());
            if (this.getPic(v.getPic_collections(), "300*400") != null
                    && this.getPic(v.getPic_collections(), "300*400").trim().length() > 0) {
                this.setImg_vertical_300x400(this.getPic(v.getPic_collections(), "300*400"));
            } else {
                this.setImg_vertical_300x400(v.getTrans_code_prefix() + "/thumb/2_300_400.jpg");
            }
            this.setDesc(v.getDescription());
            if (this.getPic(v.getPic_collections(), "400*300") != null
                    && this.getPic(v.getPic_collections(), "400*300").trim().length() > 0) {
                this.setPic_400X300(this.getPic(v.getPic_collections(), "400*300"));
                this.setPic(this.getPic(v.getPic_collections(), "400*300"));
            } else {
                this.setPic_400X300(v.getTrans_code_prefix() + "/thumb/2_400_300.jpg");
                this.setPic(v.getTrans_code_prefix() + "/thumb/2_400_300.jpg");
            }
            this.setName(v.getName_cn());
            this.setResourceType(1);
            this.setCategoryId(v.getCategory());
            // 2016-03-17重构，去除转换老频道到新频道的逻辑，需要测试--CategoryIdConstants.getNewCategory(v.getCategory()))
            this.setNewCategoryId(v.getCategory());
            this.setCategoryName(v.getCategory_name());
            this.setStarring(v.getStarring());
            this.setVideoType("video");
            this.setSubcategoryId(v.getSub_category());
            this.setSubcategoryName(v.getSub_category_name());
            this.setIptvAlbumId(v.getItv_album_id());
            this.setId(v.getId());
            this.resourceType = 1;
            if (v.getPid() == null || v.getPid().longValue() == 0) {
                this.isAlbum = false;
            }
        }
    }

    public String getBucket() {
        return this.bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAreaRec() {
        return this.areaRec;
    }

    public void setAreaRec(String areaRec) {
        this.areaRec = areaRec;
    }

    public String getReid() {
        return this.reid;
    }

    public void setReid(String reid) {
        this.reid = reid;
    }

    public String getIs_rec() {
        return this.is_rec;
    }

    public void setIs_rec(String is_rec) {
        this.is_rec = is_rec;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    private String getPic(String pic_colloctions, String picName) {
        String pic = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> picMap = mapper.readValue(pic_colloctions, Map.class);
            pic = picMap.get(picName);
        } catch (Exception e) {
            log.warn("getPic_ error", e.getMessage(), e);
        }
        return pic;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public Integer getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getCodeVersion() {
        return this.codeVersion;
    }

    public void setCodeVersion(String codeVersion) {
        this.codeVersion = codeVersion;
    }

    public Integer getVrsVideoinfoId() {
        return this.vrsVideoinfoId;
    }

    public void setVrsVideoinfoId(Integer vrsVideoinfoId) {
        this.vrsVideoinfoId = vrsVideoinfoId;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Boolean getIsAlbum() {
        return this.isAlbum;
    }

    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    public Long getVid() {
        return this.vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    @Override
    public String toString() {
        return "ResourceInfo [resourceType=" + this.resourceType + ", codeVersion=" + this.codeVersion
                + ", vrsVideoinfoId=" + this.vrsVideoinfoId + ", pic=" + this.pic + ", streamName=" + this.streamName
                + "]";
    }

}
