package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvTypeReference;
import com.letv.mas.caller.iptv.tvproxy.common.util.JsonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PlayRecordDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -1576395890267959986L;
    public String videoId;// 视频id
    public String videoName;// 视频名称
    public String albumId;// 专辑id
    public String albumName;// 专辑名称
    public String subtitle;// 副标题
    public Integer categoryId; // 视频类别id
    public Integer videoType;// 视频类型{1：单视频(电影)，2：多视频(连续剧、动漫、综艺)，0：其他}
    public Integer seriesNum;// 第几集
    public Integer follownum;// 跟播到第几集
    public Integer episodes;// 总集数
    public Boolean isEnd;// 是否播放完毕
    public Long playTime;// 上次播放时间点
    public String lastTime;// 最后更新时间
    public Long lastUpdateTime;// 最后更新时间，单位-毫秒
    public Long duration;// 总时长
    public Long nvid;// 下一集ID
    // 播放记录来源 1："网页", 2:"手机客户端", 3:"平板",4:"电视",5:"桌面客户端"
    public Integer source;
    public String from;// 来源，source是数字，from是数字的解释描述
    public String videoStatus;// 更新信息，针对电视剧、卡通、综艺类型的视频
    public int tv_out; // TV版外跳 0:TV版 1:外跳TV版
    public String url; // PC版播放地址
    public Long roleid = Long.valueOf(0);// 客户端要求返回roleid,正常模式为0;
    public Integer isPay; // 是否付费
    public Boolean isCopyright;// 是否在TV版有版权
    public Integer albumTvCopyright;// 专辑版权信息，0/null--无版权，1--有版权
    public String imgPic;// 默认缩略图

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSeriesNum() {
        return seriesNum;
    }

    public void setSeriesNum(Integer seriesNum) {
        this.seriesNum = seriesNum;
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

    public Boolean getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Long playTime) {
        this.playTime = playTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getNvid() {
        return nvid;
    }

    public void setNvid(Long nvid) {
        this.nvid = nvid;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getVideoStatus() {
        return videoStatus;
    }

    public void setVideoStatus(String videoStatus) {
        this.videoStatus = videoStatus;
    }

    public int getTv_out() {
        return tv_out;
    }

    public void setTv_out(int tv_out) {
        this.tv_out = tv_out;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Boolean getIsCopyright() {
        return isCopyright;
    }

    public void setIsCopyright(Boolean isCopyright) {
        this.isCopyright = isCopyright;
    }

    public Integer getAlbumTvCopyright() {
        return albumTvCopyright;
    }

    public void setAlbumTvCopyright(Integer albumTvCopyright) {
        this.albumTvCopyright = albumTvCopyright;
    }

    public String getImgPic() {
        return imgPic;
    }

    public void setImgPic(String imgPic) {
        this.imgPic = imgPic;
    }

    /**
     * 获取作品库播放记录图片
     * @param img
     *            图片集合
     * @param width
     *            图片宽
     * @param height
     *            图片高
     * @return
     */
    public String getPic(Map<String, String> img, Integer width, Integer height) {
        if (img == null) {
            return null;
        }
        String pic = img.get(width + "*" + height);
        if (StringUtil.isBlank(pic)) {
            String cmsImages = img.get("cmsImages"); // 图片有可能放在cmsImages里.类型为cmsImages:String[]
            if (StringUtil.isNotBlank(cmsImages)) {
                List<Map<String, String>> cmsImageMaps = JsonUtil.parse(cmsImages,
                        new LetvTypeReference<List<Map<String, String>>>() {
                        });
                if (cmsImageMaps != null && cmsImageMaps.size() > 0) {
                    for (Map<String, String> cmsImageMap : cmsImageMaps) {
                        pic = cmsImageMap.get(width + "*" + height);
                        if (StringUtil.isNotBlank(pic)) {
                            break;
                        }
                    }
                }
            }
        }
        return pic;
    }
}
