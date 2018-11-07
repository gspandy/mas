package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SubjectConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response.LetvUserFavoriteListTp.LetvUserFavorite;
import org.apache.commons.lang.StringUtils;

/**
 * 专题收藏列表
 * @author yuehongmin
 */
public class FavoriteListDto extends BaseDto {

    private String favoriteId;
    private String favoriteType;
    private String ztId;
    private String albumId; // 专辑id
    private Integer subjectType;// 0--视频专题,1--专辑专题,3--直播专题,4--多专辑专题包,5--时间轴专题,6--热点专题,7--瀑布流专题
    private Integer product;
    private String productName;
    private String title;
    private String subTitle;
    private String pic;
    private String category;
    private Integer offline;// 专题是否下线，0/null--没下线，1--下线
    private String videoStatus; // 视频状态（预告片、正在更新、已完结）
    private Integer follownum;// 跟播到第几集
    private Integer episodes;// 总集数
    private String cid; // 频道category id
    private String globalid; // 作品库globalId
    private Float score; // 评分

    /**
     * 乐听项目需要用到的字段
     */
    // private String radioId; // 电台id
    // private String radioName; // 电台名
    // private String radioStartTime;// 节目开始时间，格式为"HH:mm"
    // private String radioEndTime; // 节目结束时间，格式为"HH:mm"
    private String sourceType; // 来源,值为:"youtube","eros".
    private String iconType; // 角标

    public FavoriteListDto() {
    }

    public FavoriteListDto(LetvUserFavorite fav) {
        this.favoriteId = fav.getFavorite_id();
        this.favoriteType = fav.getFavorite_type();
        this.ztId = fav.getZt_id();
        this.product = fav.getProduct();
        this.productName = fav.getProductName();
        this.title = fav.getTitle();
        this.subTitle = fav.getSub_title();
        if (fav.getPic_all() != null) {
            this.pic = fav.getPic_all().getPictv_542();
        }
        GetSubjectTpResponse.SubjectDataTpResponse.TemplateJson templateJson = fav.getTemplateJson();
        if (templateJson != null) {
            String key = templateJson.getTvTemplate();
            if (StringUtils.isNotBlank(key)) {
                this.subjectType = SubjectConstant.getSubjectTypeFromTemplate(key);
            }
        }
        String playPlatForm = fav.getPlayPlatform();
        if (playPlatForm != null && playPlatForm.indexOf("420007") > -1) {
            this.offline = 0;
        } else {
            this.offline = 1;
        }
    }

    // public FavoriteListDto(RadioProgramme radioProgramme) {
    // this.radioId = radioProgramme.getId();
    // this.globalid = radioProgramme.getLetvOriginalId();
    // this.radioName = radioProgramme.getName();
    // this.radioStartTime = radioProgramme.getStartTime();
    // this.radioEndTime = radioProgramme.getEndTime();
    // }

    public String getFavoriteId() {
        return this.favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getFavoriteType() {
        return this.favoriteType;
    }

    public void setFavoriteType(String favoriteType) {
        this.favoriteType = favoriteType;
    }

    public String getZtId() {
        return this.ztId;
    }

    public void setZtId(String ztId) {
        this.ztId = ztId;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public Integer getProduct() {
        return this.product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getOffline() {
        return this.offline;
    }

    public void setOffline(Integer offline) {
        this.offline = offline;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public Integer getFollownum() {
        return follownum;
    }

    public void setFollownum(Integer follownum) {
        this.follownum = follownum;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGlobalid() {
        return globalid;
    }

    public void setGlobalid(String globalid) {
        this.globalid = globalid;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

}
