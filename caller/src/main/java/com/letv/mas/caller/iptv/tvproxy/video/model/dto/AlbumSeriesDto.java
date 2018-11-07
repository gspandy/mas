package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.VideoMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.MmsTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.VideoCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@XmlRootElement
public class AlbumSeriesDto extends InfoDto {
    private final Logger log = LoggerFactory.getLogger(AlbumSeriesDto.class);

    private String videoType = "video";
    private Long vrsVideoinfoId;// 视频id
    private Long iptvAlbumId;
    private Long pid;
    private Integer seriesNum;// 第几集、第几期
    private String viewpic;// 视频截图
    private String viewpic_400x300;
    private String videoName;// 视频名称
    private Integer duration;// 时长
    private String play_streams;
    private Integer categoryId;
    private Integer newCategoryId;// 新媒资的id
    private String albumName;
    private Boolean isAlbum = true;// 默认是专辑中的视频
    private String desc;// 描述
    private Integer positive;// 是否是正片
    private Integer tv_out;// 是否跳到站外
    private Long vid;// 视频id
    private String starring;// 歌手
    private String guest;// 嘉宾
    private String singer;// 歌手
    private Integer videoTypeId;
    private String subTitle;

    public AlbumSeriesDto() {

    }

    public AlbumSeriesDto(VideoMysqlTable v) {
        this.iptvAlbumId = v.getItv_album_id();
        this.vrsVideoinfoId = v.getVideo_info_id();
        this.duration = v.getDuration();
        this.seriesNum = v.getPorder();
        this.viewpic = this.getPic(v.getPic_all(), "120*90");
        this.videoName = v.getName_cn();
        if (v.getCategory() == MmsTpConstant.MMS_CATEGARY_MUSIC && v.getStarring_all() != null) {
            this.videoName = v.getStarring_all().replace(",", "") + " " + this.videoName;
        } else if (v.getCategory() == MmsTpConstant.MMS_CATEGARY_VARIETY && v.getSub_title() != null
                && !v.getSub_title().isEmpty()) {
            this.videoName = v.getPorder() + ":" + v.getSub_title();
        }
        if (this.getPic(v.getPic_all(), "400*300") != null
                && this.getPic(v.getPic_all(), "400*300").trim().length() > 0) {
            this.viewpic_400x300 = this.getPic(v.getPic_all(), "400*300");
        } else {
            this.viewpic_400x300 = v.getTrans_code_prefix() + "/thumb/2_400_300.jpg";
        }
        this.play_streams = v.getPlay_streams();
        this.categoryId = v.getCategory();
        this.newCategoryId = v.getCategory();
        this.tv_out = (v.getPushflag() != null && v.getPushflag() == 1) ? 0 : 1;
        // this.positive = ((this.categoryId != null && (this.categoryId ==
        // CategoryIdConstant.FILM_TYPE || this.categoryId ==
        // CategoryIdConstant.TV_PROGRAM_TYPE)) && (!"180001"
        // .equalsIgnoreCase(v.getVideo_type_all()))) ? 0 : 1;
        if (VideoCommonUtil.isPositive(2, this.categoryId, StringUtil.toInteger(v.getVideo_type_all(), 0), null)) {
            this.positive = 1;
        } else {
            this.positive = 0;
        }
        // this.positive = this.getAlbumType(this.categoryId,
        // v.getVideo_type_all());
        this.vid = v.getId();
        this.starring = (v.getStarring_all() != null) ? v.getStarring_all().replace(",", " ") : "";
        this.desc = v.getDescription();
        this.pid = v.getPid();
        this.guest = v.getGuest();
        this.videoTypeId = v.getVideo_type();
        this.subTitle = v.getSub_title();
    }

    /**
     * 获得专辑类型
     * @param categoryId
     * @param albumType
     * @return
     */
    private Integer getAlbumType(Integer categoryId, String albumType) {
        if (this.categoryId != null
                && (this.categoryId == MmsTpConstant.MMS_CATEGARY_FILM || this.categoryId == MmsTpConstant.MMS_CATEGARY_TV)) {// 电影电视剧
            CommonConstants.ALBUM_TYPE[] albumtypes = CommonConstants.ALBUM_TYPE.values();
            for (CommonConstants.ALBUM_TYPE type : albumtypes) {
                if (type.getMs_videotype().equalsIgnoreCase(albumType)) {
                    return type.getSelftype();
                }
            }
            return CommonConstants.ALBUM_TYPE.OTHER.getSelftype();// 其他
        } else {
            return CommonConstants.ALBUM_TYPE.ZHENGPIAN.getSelftype();// 正片
        }
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

    private String getPicByPrefix(String prefix, String picName) {
        String pic = prefix + "/";
        return pic;
    }

    public Long getVid() {
        return this.vid;
    }

    public void setVid(Long vid) {
        this.vid = vid;
    }

    public String getPlay_streams() {
        return this.play_streams;
    }

    public void setPlay_streams(String play_streams) {
        this.play_streams = play_streams;
    }

    public Long getVrsVideoinfoId() {
        return this.vrsVideoinfoId;
    }

    public void setVrsVideoinfoId(Long vrs_videoinfoId) {
        this.vrsVideoinfoId = vrs_videoinfoId;
    }

    public Long getIptvAlbumId() {
        return this.iptvAlbumId;
    }

    public Long getPid() {
        return this.pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public void setIptvAlbumId(Long iptvAlbumId) {
        this.iptvAlbumId = iptvAlbumId;
    }

    public Integer getSeriesNum() {
        return this.seriesNum;
    }

    public void setSeriesNum(Integer seriesNum) {
        this.seriesNum = seriesNum;
    }

    public String getViewpic() {
        return this.viewpic;
    }

    public void setViewpic(String viewpic) {
        this.viewpic = viewpic;
    }

    public String getVideoName() {
        return this.videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getViewpic_400x300() {
        return this.viewpic_400x300;
    }

    public void setViewpic_400x300(String viewpic_400x300) {
        this.viewpic_400x300 = viewpic_400x300;
    }

    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public Integer getPositive() {
        return this.positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getTv_out() {
        return this.tv_out;
    }

    public void setTv_out(Integer tv_out) {
        this.tv_out = tv_out;
    }

    public String getVideoType() {
        return this.videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

    public Boolean getIsAlbum() {
        return this.isAlbum;
    }

    public void setIsAlbum(Boolean isAlbum) {
        this.isAlbum = isAlbum;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStarring() {
        return this.starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public Integer getNewCategoryId() {
        return this.newCategoryId;
    }

    public void setNewCategoryId(Integer newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public String getGuest() {
        return this.guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getSinger() {
        return this.singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public Integer getVideoTypeId() {
        return this.videoTypeId;
    }

    public void setVideoTypeId(Integer videoTypeId) {
        this.videoTypeId = videoTypeId;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

}
