package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SubjectConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.search.model.bean.bo.SearchPageResponse;
import org.springframework.util.CollectionUtils;
import serving.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 所有搜索数据类型都存放在此类中
 * @author chenjian
 */
public class SearchResultsDto {

    public static BaseData getResultDto(ServingResult servingResult, CommonParam commonParam) {
        if (servingResult != null) {
            DataType dataType = servingResult.getData_type();
            if (dataType != null) {
                int value = dataType.getValue();
                if (value == SearchConstant.SEARCH_DATA_TYPE_ALBUM) {
                    return new SearchAlbumDto(servingResult, commonParam);
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_VIDEO) {
                    return new SearchVideoDto(servingResult, commonParam);
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_SUBJECT) {
                    return new SearchSubjectDto(servingResult, commonParam);
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_STAR) {
                    return new SearchStarDto(servingResult, commonParam);
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_APP) {
                    return new SearchAppDto(servingResult, commonParam);
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_LIVE) {
                    ResultDocInfo resultDocInfo;
                    LiveAttribute liveAttribute;
                    if ((resultDocInfo = servingResult.getDoc_info()) != null
                            && (liveAttribute = resultDocInfo.getLive_attribute()) != null) {
                        int type = liveAttribute.getType();
                        // 6娱乐 2 轮播 卫视 3体育
                        if (type == 6) {
                            return new SearchLiveDto.SearchLiveMusicDto(servingResult, commonParam);
                        } else if (type == 2) {
                            return new SearchLiveDto.SearchLiveCarouselDto(servingResult, commonParam);
                        } else if (type == 3) {
                            return new SearchLiveDto.SearchLiveSportDto(servingResult, commonParam);
                        }
                    }
                    return null;
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_MUSICALBUM) {
                    return new SearchMusicAlbumDto(servingResult, commonParam);
                } else if (value == SearchConstant.SEARCH_DATA_TYPE_MUSICSINGLE) {
                    return new SearchMusicSingleDto(servingResult, commonParam);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static class SearchAlbumDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String subName;
        private String letv_original_id;
        private String albumId;
        private Integer categoryId;
        private String categoryName;
        private String subCategoryId;
        private String subCategoryName;
        private String area;
        private String poster;
        private String src;
        private String subSrc;
        private String isEnd;
        private String episodes;
        private String nowEpisodes;
        private String videoType;
        private int playMark;
        private int isHomemade;
        private int isPay;
        // for tvod icon type search
        // private String payType; //付费类型
        // private Integer isCoupon; // 是否支持券
        private Map<String, String> playPlatform = new HashMap<String, String>();;
        private Map<String, String> payPlatform = new HashMap<String, String>();;
        private String pushFlag;
        private Long releaseTime;
        private String actor;
        private String director;
        private String playStreams;
        private List<SearchVideoDto> videoList = new LinkedList<SearchVideoDto>();
        private String externalId;// 第三方数据ID

        public SearchAlbumDto() {

        }

        public SearchAlbumDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                this.albumId = resultDocInfo.getId();
                this.categoryId = Integer.parseInt(resultDocInfo.getCategory());
                this.categoryName = resultDocInfo.getCategory_name();
                AlbumAttribute albumAttribute = resultDocInfo.getAlbum_attribute();
                if (albumAttribute != null) {
                    this.src = String.valueOf(albumAttribute.getSrc());
                    this.subSrc = albumAttribute.getSub_src();
                    this.subCategoryId = albumAttribute.getSub_category();
                    this.subCategoryName = albumAttribute.getSub_category_name();
                    this.area = albumAttribute.getArea();
                    this.isEnd = String.valueOf(albumAttribute.getIs_end());

                    this.episodes = String.valueOf(albumAttribute.getEpisodes());
                    this.nowEpisodes = albumAttribute.getNow_episode();
                    // 对于期数为0的情况全部置空
                    if ("0".equals(this.episodes)) {
                        this.episodes = null;
                    }
                    if ("0".equals(this.nowEpisodes)) {
                        this.nowEpisodes = null;
                    }

                    this.videoType = String.valueOf(albumAttribute.getVideo_type());
                    this.playMark = albumAttribute.getPlay_mark();
                    this.isHomemade = albumAttribute.getIs_home_made();
                    this.isPay = albumAttribute.getIs_pay();
                    // for tvod icon type search

                    this.releaseTime = albumAttribute.getRelease_date() == 0 ? null : albumAttribute.getRelease_date();
                    this.subName = albumAttribute.getSub_name();
                    this.playStreams = albumAttribute.getPlay_streams();
                    this.externalId = albumAttribute.getExternal_id();
                    List<IdAndName> starringList = albumAttribute.getStarring();// starring是主演，不用actor
                    if (!CollectionUtils.isEmpty(starringList)) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : starringList) {
                            temp.append(idAndName.getName() + ",");
                        }
                        this.actor = temp.substring(0, temp.length() - 1);
                    }
                    List<IdAndName> directorList = albumAttribute.getDirector();
                    if (!CollectionUtils.isEmpty(directorList)) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : directorList) {
                            temp.append(idAndName.getName() + ",");
                        }
                        this.director = temp.substring(0, temp.length() - 1);
                    }

                    if (!CollectionUtils.isEmpty(albumAttribute.getPay_platform())) {
                        for (IdAndName idAndName : albumAttribute.getPay_platform()) {
                            this.payPlatform.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (!CollectionUtils.isEmpty(albumAttribute.getPush_flag())) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : albumAttribute.getPush_flag()) {
                            this.playPlatform.put(idAndName.getId(), idAndName.getName());
                            temp.append(idAndName.getId() + ",");
                        }
                        this.pushFlag = temp.toString();
                    }

                    if (!CollectionUtils.isEmpty(albumAttribute.getVideo_list())) {
                        SearchVideoDto searchVideoDto = null;
                        for (VideoAttributeInAlbum videoAttributeInAlbum : albumAttribute.getVideo_list()) {
                            searchVideoDto = new SearchVideoDto();
                            searchVideoDto.setName(videoAttributeInAlbum.getName());
                            searchVideoDto.setSubName(videoAttributeInAlbum.getSub_name());
                            this.videoList.add(searchVideoDto);
                        }
                    }

                    if (!CollectionUtils.isEmpty(albumAttribute.getImages())) {
                        this.poster = albumAttribute.getImages().get("300*400");
                        if (StringUtil.isBlank(poster)) {
                            this.poster = albumAttribute.getImages().get("150*200");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = albumAttribute.getImages().get("120*160");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = albumAttribute.getImages().get("96*128");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = albumAttribute.getImages().get("90*120");
                        }
                    }
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public List<SearchVideoDto> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<SearchVideoDto> videoList) {
            this.videoList = videoList;
        }

        public String getGlobalId() {
            return globalId;
        }

        public String getPushFlag() {
            return pushFlag;
        }

        public void setPushFlag(String pushFlag) {
            this.pushFlag = pushFlag;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getSubCategoryName() {
            return subCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            this.subCategoryName = subCategoryName;
        }

        public Long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(Long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getActor() {
            return actor;
        }

        public void setActor(String actor) {
            this.actor = actor;
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPlayStreams() {
            return playStreams;
        }

        public void setPlayStreams(String playStreams) {
            this.playStreams = playStreams;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public String getSubSrc() {
            return subSrc;
        }

        public void setSubSrc(String subSrc) {
            this.subSrc = subSrc;
        }

        public int getPlayMark() {
            return playMark;
        }

        public void setPlayMark(int playMark) {
            this.playMark = playMark;
        }

        public int getIsHomemade() {
            return isHomemade;
        }

        public void setIsHomemade(int isHomemade) {
            this.isHomemade = isHomemade;
        }

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }

        public Map<String, String> getPayPlatform() {
            return payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getIsEnd() {
            return isEnd;
        }

        public void setIsEnd(String isEnd) {
            this.isEnd = isEnd;
        }

        public String getEpisodes() {
            return episodes;
        }

        public void setEpisodes(String episodes) {
            this.episodes = episodes;
        }

        public String getNowEpisodes() {
            return nowEpisodes;
        }

        public void setNowEpisodes(String nowEpisodes) {
            this.nowEpisodes = nowEpisodes;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public Map<String, String> getPlayPlatform() {
            return playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }
    }

    public static class SearchVideoDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String letv_original_id;
        private String videoId;
        private String albumId;
        private String poster;
        private String src;
        private String duration;
        private Map<String, String> playPlatform = new HashMap<String, String>();
        private String url;
        private String subSrc;
        private Integer categoryId;
        private String categoryName;
        private String subCategoryId;
        private Long playCount;
        private Long releaseTime;
        private String pushFlag;
        private String subName;

        public SearchVideoDto() {

        }

        public SearchVideoDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                this.categoryId = StringUtil.toInteger(resultDocInfo.getCategory());
                this.categoryName = resultDocInfo.getCategory_name();
                VideoAttribute videoAttribute = resultDocInfo.getVideo_attribute();
                if (videoAttribute != null) {
                    this.src = String.valueOf(videoAttribute.getSrc());
                    this.subCategoryId = videoAttribute.sub_category;
                    this.albumId = videoAttribute.getAid();
                    this.duration = videoAttribute.getDuration();
                    this.videoId = videoAttribute.getVid();
                    this.url = videoAttribute.getUrl();
                    this.subSrc = videoAttribute.getSub_src();
                    if (!CollectionUtils.isEmpty(videoAttribute.getPush_flag())) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : videoAttribute.getPush_flag()) {
                            this.playPlatform.put(idAndName.getId(), idAndName.getName());
                            temp.append(idAndName.getId() + ",");
                        }
                        this.pushFlag = temp.toString();
                    }
                    this.playCount = videoAttribute.getPlay_count();
                    this.releaseTime = StringUtil.toLong(videoAttribute.getRelease_date());
                    Map<String, String> images = videoAttribute.getImages();
                    if (!CollectionUtils.isEmpty(images)) {
                        this.poster = images.get("1440*810");
                        if (StringUtil.isBlank(poster)) {
                            this.poster = images.get("960*640");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = images.get("400*225");
                        }
                    }
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getPushFlag() {
            return pushFlag;
        }

        public void setPushFlag(String pushFlag) {
            this.pushFlag = pushFlag;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public Long getPlayCount() {
            return playCount;
        }

        public void setPlayCount(Long playCount) {
            this.playCount = playCount;
        }

        public Long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(Long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSubSrc() {
            return subSrc;
        }

        public void setSubSrc(String subSrc) {
            this.subSrc = subSrc;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getVideoId() {
            return videoId;
        }

        public void setVideoId(String videoId) {
            this.videoId = videoId;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public Map<String, String> getPlayPlatform() {
            return playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }

    }

    public static class SearchSubjectDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String letv_original_id;
        private String subjectId;
        private String poster;
        private int subjectType;
        private Map<String, String> playPlatform = new HashMap<String, String>();
        private Integer categoryId;
        private String subCategoryId;
        private String pushFlag;
        private List<SearchVideoDto> videoList = new LinkedList<SearchVideoDto>();
        private List<SearchAlbumDto> albumList = new LinkedList<SearchAlbumDto>();

        public SearchSubjectDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                this.subjectId = resultDocInfo.getId();
                this.categoryId = StringUtil.toInteger(resultDocInfo.getCategory());
                SubjectAttribute subjectAttribute = resultDocInfo.getSubject_attribute();
                if (subjectAttribute != null) {
                    this.subjectType = SubjectConstant.getSubjectTypeFromTemplate(subjectAttribute.getTv_template());
                    this.subCategoryId = subjectAttribute.getSub_category();
                    if (!CollectionUtils.isEmpty(subjectAttribute.getAlbum_list())) {
                        for (AlbumInSubject albumInSubject : subjectAttribute.getAlbum_list()) {
                            SearchAlbumDto searchAlbumDto = new SearchAlbumDto();
                            searchAlbumDto.setName(albumInSubject.getName());
                            this.albumList.add(searchAlbumDto);
                        }
                    }
                    if (!CollectionUtils.isEmpty(subjectAttribute.getVideo_list())) {
                        for (VideoInSubject videoInSubject : subjectAttribute.getVideo_list()) {
                            SearchVideoDto searchVideoDto = new SearchVideoDto();
                            searchVideoDto.setName(videoInSubject.getName());
                            this.videoList.add(searchVideoDto);
                        }
                    }
                    if (!CollectionUtils.isEmpty(subjectAttribute.getPush_flag())) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : subjectAttribute.getPush_flag()) {
                            this.playPlatform.put(idAndName.getId(), idAndName.getName());
                            temp.append(idAndName.getId() + ",");
                        }
                        this.pushFlag = temp.toString();
                    }
                    if (!CollectionUtils.isEmpty(subjectAttribute.getImages())) {
                        this.poster = subjectAttribute.getImages().get("300*400");
                        if (StringUtil.isBlank(poster)) {
                            this.poster = subjectAttribute.getImages().get("150*200");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = subjectAttribute.getImages().get("120*160");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = subjectAttribute.getImages().get("96*128");
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = subjectAttribute.getImages().get("90*120");
                        }
                    }
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public List<SearchAlbumDto> getAlbumList() {
            return albumList;
        }

        public void setAlbumList(List<SearchAlbumDto> albumList) {
            this.albumList = albumList;
        }

        public List<SearchVideoDto> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<SearchVideoDto> videoList) {
            this.videoList = videoList;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getPushFlag() {
            return pushFlag;
        }

        public void setPushFlag(String pushFlag) {
            this.pushFlag = pushFlag;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public String getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(String subjectId) {
            this.subjectId = subjectId;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public int getSubjectType() {
            return subjectType;
        }

        public void setSubjectType(int subjectType) {
            this.subjectType = subjectType;
        }

        public Map<String, String> getPlayPlatform() {
            return playPlatform;
        }

        public void setPlayPlatform(Map<String, String> playPlatform) {
            this.playPlatform = playPlatform;
        }

    }

    public static class SearchStarDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String letv_original_id;
        private String starId;
        private String poster;
        private String areaName;
        private String birthday;
        private Map<String, String> professional = new HashMap<String, String>();
        private List<String> productions = new LinkedList<String>();

        public SearchStarDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                StarAttribute starAttribute = resultDocInfo.getStar_attribute();
                if (starAttribute != null) {
                    this.starId = String.valueOf(starAttribute.getLe_id());
                    this.areaName = starAttribute.getArea_name();
                    this.birthday = starAttribute.getBirthday();
                    if (!CollectionUtils.isEmpty(starAttribute.getProfessional())) {
                        for (IdAndName idAndName : starAttribute.getProfessional()) {
                            this.professional.put(idAndName.getId(), idAndName.getName());
                        }
                    }
                    if (!CollectionUtils.isEmpty(starAttribute.getWorks_detail_info())) {
                        for (StarWorkAttribute starWorkAttribute : starAttribute.getWorks_detail_info()) {
                            this.productions.add(starWorkAttribute.getName());
                        }
                    }

                    if (!CollectionUtils.isEmpty(starAttribute.getImage_prefix())) {
                        this.poster = starAttribute.getPost_s1();
                        if (StringUtil.isBlank(poster)) {
                            this.poster = starAttribute.getPost_s2();
                        }
                        if (StringUtil.isBlank(poster)) {
                            this.poster = starAttribute.getPost_s3();
                        }

                    }
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public List<String> getProductions() {
            return productions;
        }

        public void setProductions(List<String> productions) {
            this.productions = productions;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public Map<String, String> getProfessional() {
            return professional;
        }

        public void setProfessional(Map<String, String> professional) {
            this.professional = professional;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getStarId() {
            return starId;
        }

        public void setStarId(String starId) {
            this.starId = starId;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

    }

    public static class SearchAppDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String letv_original_id;
        private String packageName;
        private String poster;
        private String rating;
        private String categoryId;
        private int tvAppId;
        private String size;

        public SearchAppDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.categoryId = resultDocInfo.getCategory();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                AppAttribute appAttrubute = resultDocInfo.getApp_attribute();
                if (appAttrubute != null) {
                    this.packageName = appAttrubute.getPackage_name();
                    this.poster = appAttrubute.getIcon();
                    this.rating = appAttrubute.getRating();
                    this.tvAppId = appAttrubute.getTv_app_id();
                    this.size = appAttrubute.getShow_size();
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getTvAppId() {
            return tvAppId;
        }

        public void setTvAppId(int tvAppId) {
            this.tvAppId = tvAppId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }

    public static class SearchLiveDto {

        public static class SearchLiveSportDto extends BaseData {
            private static final long serialVersionUID = 1L;
            private String globalId;
            private String letv_original_id;
            private int type;
            private String id;
            private String poster;
            private String name;
            private String liveTypeName;
            private Integer levelCategoryId;// 一级分类
            private Integer levelSubcategoryId;// 二级分类
            private List<SportProgramDto> sportProgramDto = new LinkedList<SportProgramDto>();

            public SearchLiveSportDto(ServingResult servingResult, CommonParam commonParam) {
                ResultDocInfo resultDocInfo;
                if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                    if (resultDocInfo.getData_type() != null) {
                        this.setDataType(resultDocInfo.getData_type().getValue());
                    }
                    this.globalId = resultDocInfo.getGlobal_id();
                    this.name = resultDocInfo.getName();
                    this.letv_original_id = resultDocInfo.getLetv_original_id();
                    this.id = resultDocInfo.getId();
                    LiveAttribute liveAttribute = resultDocInfo.getLive_attribute();
                    if (liveAttribute != null) {
                        this.type = liveAttribute.getType();
                        LiveSportsAttribute liveSportsAttribute = liveAttribute.getSports_attribute();
                        if (liveSportsAttribute != null) {
                            this.name = liveSportsAttribute.getLevel_subcategory();
                            this.liveTypeName = liveSportsAttribute.getLive_type_name();
                            this.levelCategoryId = liveSportsAttribute.getLevel_category_id();
                            this.levelSubcategoryId = liveSportsAttribute.getLevel_subcategory_id();
                            if (!CollectionUtils.isEmpty(liveSportsAttribute.getGame_list())) {
                                // 背景图取第一条直播的图片
                                Map<String, String> images = liveSportsAttribute.getGame_list().get(0)
                                        .getDefault_logo();
                                if (!CollectionUtils.isEmpty(images)) {
                                    this.poster = images.get("pic2_960_540");
                                    if (StringUtil.isBlank(this.poster)) {
                                        this.poster = images.get("pic1_746_419");
                                    }
                                    if (StringUtil.isBlank(this.poster)) {
                                        this.poster = images.get("pic3_400_225");
                                    }
                                    if (StringUtil.isBlank(this.poster)) {
                                        this.poster = images.get("pic4_160_90");
                                    }
                                }
                                for (SportsGame sportsGame : liveSportsAttribute.getGame_list()) {
                                    this.sportProgramDto.add(new SportProgramDto(sportsGame));
                                }
                            }
                        }
                    }
                    BurrowUtil.buildBurrow(this, commonParam);
                }
            }

            public String getGlobalId() {
                return globalId;
            }

            public void setGlobalId(String globalId) {
                this.globalId = globalId;
            }

            public String getLetv_original_id() {
                return letv_original_id;
            }

            public void setLetv_original_id(String letv_original_id) {
                this.letv_original_id = letv_original_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getLiveTypeName() {
                return liveTypeName;
            }

            public void setLiveTypeName(String liveTypeName) {
                this.liveTypeName = liveTypeName;
            }

            public Integer getLevelCategoryId() {
                return levelCategoryId;
            }

            public void setLevelCategoryId(Integer levelCategoryId) {
                this.levelCategoryId = levelCategoryId;
            }

            public Integer getLevelSubcategoryId() {
                return levelSubcategoryId;
            }

            public void setLevelSubcategoryId(Integer levelSubcategoryId) {
                this.levelSubcategoryId = levelSubcategoryId;
            }

            public List<SportProgramDto> getSportProgramDto() {
                return sportProgramDto;
            }

            public void setSportProgramDto(List<SportProgramDto> sportProgramDto) {
                this.sportProgramDto = sportProgramDto;
            }

        }

        public static class SearchLiveMusicDto extends BaseData {
            private static final long serialVersionUID = 1L;
            private String globalId;
            private String letv_original_id;
            private int type;
            private String id;
            private String poster;
            private String name;
            private String categoryName;
            private String liveTypeName;
            private List<MusicProgramDto> musicProgramDto = new LinkedList<MusicProgramDto>();

            public SearchLiveMusicDto(ServingResult servingResult, CommonParam commonParam) {
                ResultDocInfo resultDocInfo;
                if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                    if (resultDocInfo.getData_type() != null) {
                        this.setDataType(resultDocInfo.getData_type().getValue());
                    }
                    this.globalId = resultDocInfo.getGlobal_id();
                    this.letv_original_id = resultDocInfo.getLetv_original_id();
                    this.id = resultDocInfo.getId();
                    LiveAttribute liveAttribute = resultDocInfo.getLive_attribute();
                    if (liveAttribute != null) {
                        this.type = liveAttribute.getType();
                        LiveEntAttribute liveEntAttribute = liveAttribute.getEnt_attribute();
                        if (liveEntAttribute != null) {
                            this.liveTypeName = liveEntAttribute.getLive_type_name();
                            this.categoryName = liveEntAttribute.getCategory_name();
                            if (!CollectionUtils.isEmpty(liveEntAttribute.getPlay_list())) {
                                // 背景图取第一条直播的图片
                                Map<String, String> images = liveEntAttribute.getPlay_list().get(0).getDefault_logo();
                                if (!CollectionUtils.isEmpty(images)) {
                                    this.poster = images.get("pic2_960_540");
                                    if (StringUtil.isBlank(this.poster)) {
                                        this.poster = images.get("pic1_746_419");
                                    }
                                    if (StringUtil.isBlank(this.poster)) {
                                        this.poster = images.get("pic3_400_225");
                                    }
                                    if (StringUtil.isBlank(this.poster)) {
                                        this.poster = images.get("pic4_160_90");
                                    }
                                }
                                for (EntGame entGame : liveEntAttribute.getPlay_list()) {
                                    this.musicProgramDto.add(new MusicProgramDto(entGame));
                                }
                                this.name = this.musicProgramDto.get(0).getTitle();
                            }
                        }
                    }
                    BurrowUtil.buildBurrow(this, commonParam);
                }
            }

            public String getGlobalId() {
                return globalId;
            }

            public void setGlobalId(String globalId) {
                this.globalId = globalId;
            }

            public String getLetv_original_id() {
                return letv_original_id;
            }

            public void setLetv_original_id(String letv_original_id) {
                this.letv_original_id = letv_original_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getLiveTypeName() {
                return liveTypeName;
            }

            public void setLiveTypeName(String liveTypeName) {
                this.liveTypeName = liveTypeName;
            }

            public List<MusicProgramDto> getMusicProgramDto() {
                return musicProgramDto;
            }

            public void setMusicProgramDto(List<MusicProgramDto> musicProgramDto) {
                this.musicProgramDto = musicProgramDto;
            }

        }

        public static class SearchLiveCarouselDto extends BaseData {
            private static final long serialVersionUID = -3678933893836620201L;
            private String globalId;
            private String letv_original_id;
            private int type;
            private String id;
            private String poster;
            private String tagUrl;
            private String name;
            private String sourceId;
            private String liveTypeName;
            private List<Integer> splatids;
            private List<CarouselProgramDto> carouselProgramDto = new LinkedList<CarouselProgramDto>();
            public int liveType;// burrow中用，这里为public型，区分轮播，卫视台
            public static int lunbo = 1;
            public static int weishi = 2;

            public SearchLiveCarouselDto(ServingResult servingResult, CommonParam commonParam) {
                ResultDocInfo resultDocInfo;
                if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                    if (resultDocInfo.getData_type() != null) {
                        this.setDataType(resultDocInfo.getData_type().getValue());
                    }
                    this.globalId = resultDocInfo.getGlobal_id();
                    this.name = resultDocInfo.getName();
                    this.letv_original_id = resultDocInfo.getLetv_original_id();
                    LiveAttribute liveAttribute = resultDocInfo.getLive_attribute();
                    if (liveAttribute != null) {
                        this.type = liveAttribute.getType();
                        LiveLunboAttribute liveLunboAttribute = liveAttribute.getLunbo_attribute();
                        LiveWeishiAttribute liveWeishiAttribute = liveAttribute.getWeishi_attribute();
                        if (liveLunboAttribute != null) {
                            this.id = liveLunboAttribute.getChannel_id();
                            this.tagUrl = liveLunboAttribute.getImage_url();
                            Map<String, String> defaultLogo = liveLunboAttribute.getDefault_logo();
                            if (!CollectionUtils.isEmpty(defaultLogo)) {
                                this.poster = defaultLogo.get("pic2_960_540");
                                if (StringUtil.isBlank(this.poster)) {
                                    this.poster = defaultLogo.get("pic1_746_419");
                                }
                                if (StringUtil.isBlank(this.poster)) {
                                    this.poster = defaultLogo.get("pic3_400_225");
                                }
                                if (StringUtil.isBlank(this.poster)) {
                                    this.poster = defaultLogo.get("pic4_160_90");
                                }
                            }
                            this.name = liveLunboAttribute.getName_cn();
                            this.liveTypeName = liveLunboAttribute.getLive_type_name();
                            this.sourceId = liveLunboAttribute.getSource_id();
                            this.splatids = liveLunboAttribute.getSplatids();
                            if (!CollectionUtils.isEmpty(liveLunboAttribute.getPlay_games())) {
                                for (PlayGame playGame : liveLunboAttribute.getPlay_games()) {
                                    this.carouselProgramDto.add(new CarouselProgramDto(playGame));
                                }
                            }
                            this.liveType = lunbo;
                        } else if (liveWeishiAttribute != null) {
                            this.id = liveWeishiAttribute.getChannel_id();
                            this.tagUrl = liveWeishiAttribute.getImage_url();
                            Map<String, String> defaultLogo = liveWeishiAttribute.getDefault_logo();
                            if (!CollectionUtils.isEmpty(defaultLogo)) {
                                this.poster = defaultLogo.get("pic2_960_540");
                                if (StringUtil.isBlank(this.poster)) {
                                    this.poster = defaultLogo.get("pic1_746_419");
                                }
                                if (StringUtil.isBlank(this.poster)) {
                                    this.poster = defaultLogo.get("pic3_400_225");
                                }
                                if (StringUtil.isBlank(this.poster)) {
                                    this.poster = defaultLogo.get("pic4_160_90");
                                }
                            }
                            this.name = liveWeishiAttribute.getName_cn();
                            this.liveTypeName = liveWeishiAttribute.getLive_type_name();
                            this.sourceId = liveWeishiAttribute.getSource_id();
                            this.splatids = liveWeishiAttribute.getSplatids();
                            if (!CollectionUtils.isEmpty(liveWeishiAttribute.getPlay_games())) {
                                for (PlayGame playGame : liveWeishiAttribute.getPlay_games()) {
                                    this.carouselProgramDto.add(new CarouselProgramDto(playGame));
                                }
                            }
                            this.liveType = weishi;
                        }
                    }
                    BurrowUtil.buildBurrow(this, commonParam);
                }

            }

            public String getTagUrl() {
                return tagUrl;
            }

            public void setTagUrl(String tagUrl) {
                this.tagUrl = tagUrl;
            }

            public String getGlobalId() {
                return globalId;
            }

            public void setGlobalId(String globalId) {
                this.globalId = globalId;
            }

            public String getLetv_original_id() {
                return letv_original_id;
            }

            public void setLetv_original_id(String letv_original_id) {
                this.letv_original_id = letv_original_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPoster() {
                return poster;
            }

            public void setPoster(String poster) {
                this.poster = poster;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSourceId() {
                return sourceId;
            }

            public void setSourceId(String sourceId) {
                this.sourceId = sourceId;
            }

            public String getLiveTypeName() {
                return liveTypeName;
            }

            public void setLiveTypeName(String liveTypeName) {
                this.liveTypeName = liveTypeName;
            }

            public List<Integer> getSplatids() {
                return splatids;
            }

            public void setSplatids(List<Integer> splatids) {
                this.splatids = splatids;
            }

            public List<CarouselProgramDto> getCarouselProgramDto() {
                return carouselProgramDto;
            }

            public void setCarouselProgramDto(List<CarouselProgramDto> carouselProgramDto) {
                this.carouselProgramDto = carouselProgramDto;
            }

        }

        /*
         * 音乐直播
         */
        public static class MusicProgramDto {
            private String dt; // 数据类型（搜索用于记录点击日志，各端日志需记录
            private long beginTimeStamp; // 开始时间
            private long endTimeStamp; // 结束时间
            private String recordingId; // 录制ID
            private String title; // 节目标题;
            private String preVid; // 预告视频id
            private String id; // 数据ID，主键

            public MusicProgramDto(EntGame entGame) {
                this.dt = String.valueOf(entGame.getDt());
                this.beginTimeStamp = entGame.getBegin_timestamp();
                this.endTimeStamp = entGame.getEnd_timestamp();
                this.recordingId = entGame.getRecording_id();
                this.title = entGame.getTitle();
                this.preVid = entGame.getPre_vid();
                this.id = entGame.getId();
            }

            public String getDt() {
                return dt;
            }

            public void setDt(String dt) {
                this.dt = dt;
            }

            public long getBeginTimeStamp() {
                return beginTimeStamp;
            }

            public void setBeginTimeStamp(long beginTimeStamp) {
                this.beginTimeStamp = beginTimeStamp;
            }

            public long getEndTimeStamp() {
                return endTimeStamp;
            }

            public void setEndTimeStamp(long endTimeStamp) {
                this.endTimeStamp = endTimeStamp;
            }

            public String getRecordingId() {
                return recordingId;
            }

            public void setRecordingId(String recordingId) {
                this.recordingId = recordingId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPreVid() {
                return preVid;
            }

            public void setPreVid(String preVid) {
                this.preVid = preVid;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

        }

        /*
         * 轮播 、卫视 直播
         */
        public static class CarouselProgramDto {
            private long beginTimeStamp;
            private String duration;
            private String id;
            private String title;

            public CarouselProgramDto(PlayGame playGame) {
                this.beginTimeStamp = playGame.getBegin_timestamp();
                this.duration = playGame.getDuration() + "";
                this.id = playGame.getId();
                this.title = playGame.getTitle();
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public long getBeginTimeStamp() {
                return beginTimeStamp;
            }

            public void setBeginTimeStamp(long beginTimeStamp) {
                this.beginTimeStamp = beginTimeStamp;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

        }

        /*
         * 体育直播
         */
        public static class SportProgramDto {
            private long beginTimeStamp;// 判断状态
            private long endTimeStamp;
            private String id;// 直播ID
            private String preVid;// 预告
            private String recordingId;// 回看转点播ID 直播中，ID 回看 如果recordingId为空，还是id
            // 展示字段
            private String title;
            private String guestScore;
            private String guestTeam;
            private String homeScore;
            private String homeTeam;
            private String isVs;
            private String guestImgUrl;
            private String homeImgUrl;

            public SportProgramDto(SportsGame sportsGame) {
                this.beginTimeStamp = sportsGame.getBegin_timestamp();
                this.endTimeStamp = sportsGame.getEnd_timestamp();
                this.id = sportsGame.getId();
                this.preVid = sportsGame.getPre_vid();
                this.recordingId = sportsGame.getRecording_id();
                this.title = sportsGame.getTitle();
                this.guestScore = sportsGame.getGuest_score();
                this.guestTeam = sportsGame.getGuest_team();
                this.homeScore = sportsGame.getHome_score();
                this.homeTeam = sportsGame.getHome_team();
                this.isVs = sportsGame.getIs_vs();
                this.guestImgUrl = sportsGame.getGuest_image_url();
                this.homeImgUrl = sportsGame.getHome_image_url();

            }

            public long getEndTimeStamp() {
                return endTimeStamp;
            }

            public void setEndTimeStamp(long endTimeStamp) {
                this.endTimeStamp = endTimeStamp;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public long getBeginTimeStamp() {
                return beginTimeStamp;
            }

            public void setBeginTimeStamp(long beginTimeStamp) {
                this.beginTimeStamp = beginTimeStamp;
            }

            public String getPreVid() {
                return preVid;
            }

            public void setPreVid(String preVid) {
                this.preVid = preVid;
            }

            public String getRecordingId() {
                return recordingId;
            }

            public void setRecordingId(String recordingId) {
                this.recordingId = recordingId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGuestScore() {
                return guestScore;
            }

            public void setGuestScore(String guestScore) {
                this.guestScore = guestScore;
            }

            public String getGuestTeam() {
                return guestTeam;
            }

            public void setGuestTeam(String guestTeam) {
                this.guestTeam = guestTeam;
            }

            public String getHomeScore() {
                return homeScore;
            }

            public void setHomeScore(String homeScore) {
                this.homeScore = homeScore;
            }

            public String getHomeTeam() {
                return homeTeam;
            }

            public void setHomeTeam(String homeTeam) {
                this.homeTeam = homeTeam;
            }

            public String getIsVs() {
                return isVs;
            }

            public void setIsVs(String isVs) {
                this.isVs = isVs;
            }

            public String getGuestImgUrl() {
                return guestImgUrl;
            }

            public void setGuestImgUrl(String guestImgUrl) {
                this.guestImgUrl = guestImgUrl;
            }

            public String getHomeImgUrl() {
                return homeImgUrl;
            }

            public void setHomeImgUrl(String homeImgUrl) {
                this.homeImgUrl = homeImgUrl;
            }

        }
    }

    public static class SearchMusicAlbumDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String letv_original_id;
        private String singers;
        private String poster;
        private String albumId;

        public SearchMusicAlbumDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                MusicAlbumAttribute musicAlbumAttribute = resultDocInfo.getMusic_album_attribute();
                if (musicAlbumAttribute != null) {
                    this.albumId = musicAlbumAttribute.getXiami_id();
                    if (!CollectionUtils.isEmpty(musicAlbumAttribute.getSinger_list())) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : musicAlbumAttribute.getSinger_list()) {
                            temp.append(idAndName.getName() + ",");
                        }
                        this.singers = temp.substring(0, temp.length() - 1);
                    }
                    Map<String, String> imagePrefix = musicAlbumAttribute.getImage_prefix();
                    if (!CollectionUtils.isEmpty(imagePrefix)) {
                        this.poster = imagePrefix.get("1_1");
                        if (!StringUtil.isBlank(poster)) {
                            this.poster = poster + "400_400.jpg";
                        }
                    }
                    if (StringUtil.isBlank(this.poster) && !CollectionUtils.isEmpty(musicAlbumAttribute.getImages())) {
                        this.poster = musicAlbumAttribute.getImages().get("400*400");
                    }
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public String getSingers() {
            return singers;
        }

        public void setSingers(String singers) {
            this.singers = singers;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

    }

    public static class SearchMusicSingleDto extends BaseData {
        private static final long serialVersionUID = 1L;
        private String globalId;
        private String name;
        private String letv_original_id;
        private String xiamiId;
        private String singers;
        private String poster;
        private String songId;

        public SearchMusicSingleDto(ServingResult servingResult, CommonParam commonParam) {
            ResultDocInfo resultDocInfo;
            if (servingResult != null && (resultDocInfo = servingResult.getDoc_info()) != null) {
                if (resultDocInfo.getData_type() != null) {
                    this.setDataType(resultDocInfo.getData_type().getValue());
                }
                this.globalId = resultDocInfo.getGlobal_id();
                this.name = resultDocInfo.getName();
                this.letv_original_id = resultDocInfo.getLetv_original_id();
                MusicSongAttribute musicSongAttribute = resultDocInfo.getMusic_song_attribute();
                if (musicSongAttribute != null) {
                    this.xiamiId = musicSongAttribute.getXiami_id();
                    this.songId = musicSongAttribute.getSong_id();
                    this.poster = musicSongAttribute.getImage_url();
                    if (!CollectionUtils.isEmpty(musicSongAttribute.getSinger_list())) {
                        StringBuilder temp = new StringBuilder();
                        for (IdAndName idAndName : musicSongAttribute.getSinger_list()) {
                            temp.append(idAndName.getName() + ",");
                        }
                        this.singers = temp.substring(0, temp.length() - 1);
                    }
                }
                BurrowUtil.buildBurrow(this, commonParam);
            }
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLetv_original_id() {
            return letv_original_id;
        }

        public void setLetv_original_id(String letv_original_id) {
            this.letv_original_id = letv_original_id;
        }

        public String getSongId() {
            return songId;
        }

        public void setSongId(String songId) {
            this.songId = songId;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getXiamiId() {
            return xiamiId;
        }

        public void setXiamiId(String xiamiId) {
            this.xiamiId = xiamiId;
        }

        public String getSingers() {
            return singers;
        }

        public void setSingers(String singers) {
            this.singers = singers;
        }

    }

    public static class VientianeDto {

        private int pageSize; // 每页记录数
        private int pageCount; // 总页数
        private int count; // 总记录数
        private int previousPage; // 上一页的页码
        private int currentPage; // 当前页的页码
        private int nextPage; // 下一页的页码

        private List<BaseData> list;
        private SearchPageResponse.DataReport dataReport;
        private List<SearchCategoryDto2> categoryList;// 万象搜索分类情况

        public List<SearchCategoryDto2> getCategoryList() {
            return categoryList;
        }

        public void setCategoryList(List<SearchCategoryDto2> categoryList) {
            this.categoryList = categoryList;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCount() {
            return count;
        }

        public void setPreviousPage(int previousPage) {
            this.previousPage = previousPage;
        }

        public void setNextPage(int nextPage) {
            this.nextPage = nextPage;
        }

        public void setCount(int count) {
            if (count <= 0) {
                return;
            }
            this.count = count;
            if (this.pageSize > 0) {
                this.pageCount = count / this.pageSize + ((count % this.pageSize == 0) ? 0 : 1);

                if (this.currentPage > this.pageCount) {
                    this.currentPage = this.pageCount;
                }
                if (this.currentPage <= 0) {
                    this.currentPage = 1;
                }
            }
        }

        public int getPageCount() {
            return this.pageCount < 0 ? 0 : this.pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getCurrentPage() {
            if (this.currentPage > this.pageCount) {
                this.currentPage = this.pageCount;
            }
            if (this.currentPage <= 0) {
                this.currentPage = 1;
            }
            return this.currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPreviousPage() {
            int prePage = this.currentPage - 1;
            if (prePage > this.pageCount) {
                prePage = this.pageCount;
            }
            if (prePage <= 0) {
                prePage = 1;
            }

            return prePage;
        }

        public int getNextPage() {
            int nextPage = this.currentPage + 1;
            if (nextPage > this.pageCount) {
                nextPage = this.pageCount;
            }
            if (nextPage <= 0) {
                nextPage = 1;
            }
            return nextPage;
        }

        public List<BaseData> getList() {
            return list;
        }

        public void setList(List<BaseData> list) {
            this.list = list;
        }

        public SearchPageResponse.DataReport getDataReport() {
            return dataReport;
        }

        public void setDataReport(SearchPageResponse.DataReport dataReport) {
            this.dataReport = dataReport;
        }

    }
}
