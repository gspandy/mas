package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LetvStreamCommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.bean.ChargeInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ChargeInfo;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.MmsDataUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.util.CollectionUtils;
import serving.IdAndName;
import serving.ResultDocInfo;
import serving.ServingResult;
import serving.VideoAttribute;

import java.util.HashMap;
import java.util.Map;

public class ResourceInfoDto extends AlbumInfoDto {
    private static final long serialVersionUID = 2674954255104409053L;
    private Integer resourceType;
    private String codeVersion;
    private Long vrsVideoinfoId;
    private String pic;
    private String streamName;
    private Boolean isAlbum = true;// 默认是专辑中的视频
    private Long vid;
    private String singer; // 歌手
    private String img; // 图片
    private String imgSize; // 图片尺寸
    private String bigImg; // 背景大图

    public ResourceInfoDto() {

    }

    public ResourceInfoDto(AlbumMysqlTable albumInfo, CommonParam commonParam) {
        super(albumInfo, commonParam);
    }

    public ResourceInfoDto(ServingResult servingResult, CommonParam commonParam) {
        if (servingResult != null && servingResult.getDoc_info() != null) {
            this.setDataType(DataConstant.DATA_TYPE_VIDEO);
            this.setVideoType(SearchConstant.VIDEO_TYPE_VIDEO);
            this.resourceType = 1;
            ResultDocInfo resultDocInfo = servingResult.getDoc_info();
            this.vid = Long.parseLong(resultDocInfo.getId());
            this.vrsVideoinfoId = Long.parseLong(resultDocInfo.getId());
            this.setDesc(resultDocInfo.getDescription());
            this.setName(resultDocInfo.getName());
            if (!StringUtil.isBlank(resultDocInfo.getCategory())) {
                this.setCategoryId(Integer.parseInt(resultDocInfo.getCategory()));
                this.setNewCategoryId(Integer.parseInt(resultDocInfo.getCategory()));
            }
            this.setCategoryName(resultDocInfo.getCategory_name());

            if (resultDocInfo.getVideo_attribute() != null) {
                VideoAttribute videoAttribute = resultDocInfo.getVideo_attribute();
                if (StringUtil.isBlank(videoAttribute.getAid()) || "0".equals(videoAttribute.getAid())) {
                    this.isAlbum = false;
                }
                if (!StringUtil.isBlank(videoAttribute.getAid())) {
                    this.setId(Long.parseLong(videoAttribute.getAid()));
                    this.setIptvAlbumId(Long.parseLong(videoAttribute.getAid()));
                }
                if (videoAttribute.getImages() != null) {
                    this.pic = videoAttribute.getImages().get("400*300");
                    this.setSmallImage(videoAttribute.getImages().get("120*160"));
                    this.setThumbnailImage(videoAttribute.getImages().get("120*160"));
                    this.setBigImage(videoAttribute.getImages().get("150*200"));
                    this.setImg_vertical_300x400(videoAttribute.getImages().get("300*400"));
                    this.setPic_400X300(videoAttribute.getImages().get("400*300"));
                    this.img = videoAttribute.getImages().get("400*300");
                    this.imgSize = "400*300";
                    this.bigImg = videoAttribute.getImages().get("1440*810");
                    Map<String, String> pics = new HashMap<String, String>();
                    pics.put(CommonConstants.PIXEL_400_225, videoAttribute.getImages().get("400*225"));
                    this.setPic_urls(pics);
                }
                this.setSubcategoryId(videoAttribute.getSub_category());
                this.setSubcategoryName(videoAttribute.getSub_category());
                if (!CollectionUtils.isEmpty(videoAttribute.getDirector())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (IdAndName idAndName : videoAttribute.getDirector()) {
                        stringBuilder.append(idAndName.getName() + ",");
                    }
                    this.setDirectory(stringBuilder.substring(0, stringBuilder.length() - 1));
                }

                if (!CollectionUtils.isEmpty(videoAttribute.getActor())) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (IdAndName idAndName : videoAttribute.getActor()) {
                        stringBuilder.append(idAndName.getName() + ",");
                    }
                    this.setStarring(stringBuilder.substring(0, stringBuilder.length() - 1));
                }

                if (StringUtil.isBlank(this.getStarring())) {
                    this.singer = MessageUtils.getMessage(SearchConstant.SEARCH_SEARCHRESULT_SINGER,
                            commonParam.getLangcode());
                } else {
                    this.singer = this.getStarring();
                }

                this.setReleaseDate(videoAttribute.getRelease_date());
                if (!StringUtil.isBlank(videoAttribute.getVideo_type())) {
                    this.setAlbumTypeId(Integer.parseInt(videoAttribute.getVideo_type()));
                }
                this.setAlbumTypeName(videoAttribute.getVideo_type_name());
                this.setRecordCompany(videoAttribute.getRecord_company());
                this.setStyleId(videoAttribute.getStyle());
                this.setArea(videoAttribute.getArea());
                this.setAreaName(videoAttribute.getArea_name());
                this.setTv_out(0);// 写死，搜索产品已确认不用了，默认为0不外跳 (0内部，1外跳）
                if (!StringUtil.isBlank(videoAttribute.getDuration())) {
                    this.setDuration(Integer.parseInt(videoAttribute.getDuration()));
                    this.setDurationStamp(this.getDuration() * 1000);
                }
                this.setIfCharge(this.isTvCopyRightOrPay(videoAttribute.getPay_platform(), true) + "");

                // for tvod icon type search
                // this.setChargeType();
                if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                    if (null != videoAttribute.getPay_detail()) {
                        ChargeInfo chargeInfo = MmsDataUtil.getChargeInfoFromPayDetail(commonParam.getP_devType(),
                                videoAttribute.getPay_detail());
                        if (null != chargeInfo) {
                            this.setIfCharge(chargeInfo.getIsCharge());
                            this.setChargeType(JumpUtil.getChargeType(chargeInfo));
                        }
                    }
                }
            }
        }
    }

    public ResourceInfoDto(VideoMysqlTable videoInfo, String langcode, CommonParam commonParam, String... tvPics) {
        if (videoInfo != null) {
            this.setDataType(DataConstant.DATA_TYPE_VIDEO);
            this.setVideoType(SearchConstant.VIDEO_TYPE_VIDEO);
            this.resourceType = 1;
            this.resourceType = 1;
            if (videoInfo.getPid() == null || videoInfo.getPid().longValue() == 0) {
                this.isAlbum = false;
            }

            this.vrsVideoinfoId = videoInfo.getVideo_info_id();
            this.vid = videoInfo.getId();
            if (StringUtil.isBlank(videoInfo.getStarring())) {
                this.singer = "未知歌手";// 暂时写死为中文
            } else {
                this.singer = videoInfo.getStarring();
            }

            this.setStreamName(LetvStreamCommonConstants.nameOf(this.codeVersion));
            if (tvPics != null && tvPics.length > 0) {
                this.pic = tvPics[0];
                if (StringUtil.isBlank(this.pic)) {
                    this.pic = videoInfo.getPic(400, 300);
                }
            } else {
                this.pic = videoInfo.getPic(400, 300);
            }
            this.setId(videoInfo.getPid());
            this.setIptvAlbumId(videoInfo.getItv_album_id());
            this.setName(videoInfo.getName_cn());
            this.setSubTitle(videoInfo.getSub_title());
            this.setCategoryId(videoInfo.getCategory());
            this.setNewCategoryId(videoInfo.getCategory());
            this.setCategoryName(videoInfo.getCategory_name());
            this.setSubcategoryId(videoInfo.getSub_category());
            this.setSubcategoryName(videoInfo.getSub_category_name());
            this.setDirectory(videoInfo.getDirectory());
            this.setStarring(videoInfo.getStarring());
            this.setReleaseDate(videoInfo.getRelease_date());
            this.setDuration(videoInfo.getDuration());
            if (this.getDuration() != null) {
                this.setDurationStamp(this.getDuration() * 1000);
            }
            this.setAlbumTypeId(videoInfo.getVideo_type());
            this.setAlbumTypeName(videoInfo.getVideo_type_name());
            this.setRecordCompany(videoInfo.getRecord_company());
            this.setStyleId(String.valueOf(videoInfo.getStyle()));
            this.setStyleName(videoInfo.getStyle_name());
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
            this.img = videoInfo.getPic(400, 300);
            this.imgSize = "400*300";
            this.bigImg = videoInfo.getPic(1440, 810);
            if (!StringUtil.isBlank(videoInfo.getPay_platform())
                    && MmsDataUtil.isSupportPayPlatform(videoInfo.getPay_platform(), commonParam.getP_devType())) {
                this.setIfCharge("1");
            } else {
                this.setIfCharge("0");
            }
            // for tvod icon type search
            Integer chargeType = JumpUtil.getChargeType(videoInfo.getPay_platform(), commonParam);
            if (null == chargeType) {
                chargeType = JumpUtil.getChargeType(videoInfo.getPay_platform());
            }
            this.setChargeType(chargeType);

            if (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE) {
                if (null != videoInfo.getPay_platform()) {
                    ChargeInfoDto chargeInfoDto = JumpUtil.genChargeInfoDto(commonParam.getP_devType(),
                            videoInfo.getPay_platform());
                    if (null != chargeInfoDto) {
                        this.setIfCharge(chargeInfoDto.getIsCharge());
                        this.setChargeType(JumpUtil.getChargeType(chargeInfoDto));
                    }
                }
            }
        }
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

    public Long getVrsVideoinfoId() {
        return this.vrsVideoinfoId;
    }

    public void setVrsVideoinfoId(Long vrsVideoinfoId) {
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

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
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

    @Override
    public String getBigImg() {
        return bigImg;
    }

    @Override
    public void setBigImg(String bigImg) {
        this.bigImg = bigImg;
    }
}
