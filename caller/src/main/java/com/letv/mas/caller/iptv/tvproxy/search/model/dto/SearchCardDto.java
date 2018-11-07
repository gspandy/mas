package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.burrow.BurrowUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.constant.ChannelConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.SearchCardDataResponse;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.search.model.bean.bo.SearchPageResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.SearchCardDataResponse.SearchCardData.CardContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 返给客户端的内容
 */
public class SearchCardDto extends BaseDto {

    private static final long serialVersionUID = 2529253570475226108L;
    private String cardId;
    private String cardName;
    private List<Object> dataList;
    private SearchPageResponse.DataReport dataReport;
    private String module;

    public SearchCardDto() {

    }

    public SearchCardDto(SearchCardDataResponse.SearchCardData searchCardData, String fromWX, CommonParam commonParam) {
        this.cardId = searchCardData.getCard_id();
        this.cardName = searchCardData.getCard_name();
        this.module = searchCardData.getModule();
        this.dataReport = new SearchPageResponse.DataReport(searchCardData.getEid(), searchCardData.getExperiment_bucket_str(),
                searchCardData.getTrigger_str(), searchCardData.getArea(), searchCardData.getReid(),
                searchCardData.getBucket());
        // 万象首页请求解析特殊处理
        if ("1".equals(fromWX)) {
            this.dataList = this.getCardDtaListWX(searchCardData.getData_list(), commonParam);
        } else {
            this.dataList = this.getCardDtaList(searchCardData.getData_list(), commonParam);
        }
    }

    /**
     * 搜索桌面内容DTO转换, (搜索桌面，热词，乐搜改版首页都用该套解析规则）
     * @param list
     * @return
     */
    private List<Object> getCardDtaList(List<CardContent> list, CommonParam commonParam) {
        List<Object> dataList = null;
        if (!CollectionUtils.isEmpty(list)) {
            dataList = new LinkedList<Object>();
            for (CardContent cardContent : list) {
                CardContentDto cardContentDto = new CardContentDto();
                cardContentDto.setId(cardContent.getId());
                cardContentDto.setTitle(cardContent.getTitle());
                cardContentDto.setImg(cardContent.getPoster_st());
                cardContentDto.setCategoryId(cardContent.getCategory_id());
                cardContentDto.setCategory(cardContent.getCategory());
                cardContentDto.setSrc(cardContent.getSrc());
                // 目前不支持视频，但是为防止桌面推荐升级时出现升级问题，暂时给桌面指定了视频需要的字段
                cardContentDto.setVid(cardContent.getVid());
                cardContentDto.setVideoName(cardContent.getTitle());
                cardContentDto.setUrl(cardContent.getUrl());
                cardContentDto.setSubSrc("");
                cardContentDto.setSubTitle(cardContent.getSubtitle());
                cardContentDto.setPlatformPlay(cardContent.getPlatform_play());
                cardContentDto.setPayPlatform(cardContent.getPayPlatform());
                cardContentDto.setPlayMark(cardContent.getPlayMark());
                cardContentDto.setIsHomemade(cardContent.getIsHomemade());
                cardContentDto.setIsPay(cardContentDto.getIsPay());
                cardContentDto.setVideoType(cardContentDto.getVideoType());
                cardContentDto.setGlobal_id(cardContent.getGlobal_id());

                Integer dataType = cardContent.getData_type();
                if (dataType != null && dataType == 0) {
                    // 搜索单视频type=0,tv版视频为2
                    dataType = DataConstant.DATA_TYPE_VIDEO;
                    // 如果是单视频则传入单视频id
                    cardContentDto.setId(cardContent.getVid());
                } else if (SearchConstant.SEARCH_DATA_TYPE_LIVE == dataType) {
                    continue;// 老搜索桌面直播数据过滤掉
                }

                cardContentDto.setDataType(dataType);
                if (cardContent.getPlatform_play() != null) {
                    cardContentDto.setPushFlag(this.getMapSets(cardContent.getPlatform_play().keySet()));
                }

                String score = "";
                if (!"0.0".equals(cardContent.getRating()) && "1".equals(cardContent.getCategory_id())) {
                    // 只有电影展示评分
                    score = cardContent.getRating();
                }

                cardContentDto.setScore(score);
                cardContentDto.setNowEpisodes(this.setNowEpisode(cardContent.getCategory_id(), cardContent.getIsEnd(),
                        cardContent.getNowEpisodes(), commonParam));
                dataList.add(cardContentDto);
            }
        }
        return dataList;
    }

    /**
     * 万象桌面内容DTO转换
     * @param list
     * @return
     */
    private List<Object> getCardDtaListWX(List<CardContent> list, CommonParam commonParam) {
        List<Object> dataList = null;
        if (!CollectionUtils.isEmpty(list)) {
            dataList = new LinkedList<Object>();
            for (CardContent cardContent : list) {
                Integer dataType = cardContent.getData_type();
                if (dataType != null) {
                    if (dataType == SearchConstant.SEARCH_DATA_TYPE_ALBUM) {
                        // 版本号大于等于3.0.3 才放出华数数据
                        if (CommonConstants.WASU_NEW_NAME.equals(cardContent.getSub_src())) {
                            if (!StringUtil.isBlank(commonParam.getAppVersion())) {
                                int version = StringUtil.toInteger(commonParam.getAppVersion().replaceAll("\\.", ""));
                                if (version >= 303) {
                                    dataList.add(new AlbumCardDto(cardContent, commonParam));
                                }
                            }
                        } else {
                            dataList.add(new AlbumCardDto(cardContent, commonParam));
                        }

                    } else if (dataType == SearchConstant.SEARCH_DATA_TYPE_APP) {
                        dataList.add(new AppCardDto(cardContent, commonParam));
                    } else if (dataType == SearchConstant.SEARCH_DATA_TYPE_MUSICSINGLE) {
                        dataList.add(new MusicSingleCardDto(cardContent, commonParam));
                    } else if (dataType == SearchConstant.SEARCH_DATA_TYPE_LIVE) {
                        dataList.add(new LiveDto(cardContent, commonParam));
                    }
                }
            }
        }
        return dataList;
    }

    private String setNowEpisode(String categroy, String isEnd, String nowEpisodes, CommonParam commonParam) {
        String nowEpisodesTxt = "";
        if (StringUtils.isNotBlank(categroy) && !StringUtil.isBlank(nowEpisodes) && !"0".equals(nowEpisodes)) {
            Integer categroyId = Integer.valueOf(categroy);
            if (VideoConstants.Category.TV == categroyId || VideoConstants.Category.CARTOON == categroyId) {
                Object[] params = { nowEpisodes };
                if (isEnd != null && "0".equals(isEnd)) { // 未完结
                    nowEpisodesTxt = MessageUtils.getMessage(
                            ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL, commonParam.getLangcode(),
                            params);
                } else {
                    nowEpisodesTxt = MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_IS_END,
                            commonParam.getLangcode(), params);
                }
            } else if (VideoConstants.Category.VARIETY == categroyId) {
                if (nowEpisodes.length() == 8) {
                    Object[] params = { this.getFollowNum(nowEpisodes, "yyyy-MM-dd") };
                    nowEpisodesTxt = MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWISSUE_NORMAL,
                            commonParam.getLangcode(), params);
                } else {
                    Object[] params = { nowEpisodes };
                    if (isEnd != null && "0".equals(isEnd)) { // 未完结
                        nowEpisodesTxt = MessageUtils.getMessage(
                                ChannelConstants.CHANNEL_ALBUM_UPDATED_TO_NOWEPISODES_NORMAL,
                                commonParam.getLangcode(), params);
                    } else {
                        nowEpisodesTxt = MessageUtils.getMessage(ChannelConstants.CHANNEL_ALBUM_IS_END,
                                commonParam.getLangcode(), params);
                    }
                }
            }
        }
        return nowEpisodesTxt;
    }

    private String getFollowNum(String Num, String format) {
        try {
            if (Num == null) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df1 = new SimpleDateFormat(format);
            Date followDate = sdf.parse(Num.toString());
            return df1.format(followDate);
        } catch (ParseException e) {
        }
        return "";
    }

    private static String getMapSets(Set<String> set) {
        String reStr = "";
        if (set != null) {
            @SuppressWarnings("rawtypes")
            Iterator it = set.iterator();
            while (it.hasNext()) {
                reStr += "," + it.next();
            }
            if (StringUtils.isNotBlank(reStr)) {
                reStr = reStr.substring(1, reStr.length());
            }
        }
        return reStr;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public SearchPageResponse.DataReport getDataReport() {
        return this.dataReport;
    }

    public void setDataReport(SearchPageResponse.DataReport dataReport) {
        this.dataReport = dataReport;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return this.cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    public static class AlbumCardDto extends BaseData {
        private static final long serialVersionUID = 7287459307637521278L;
        private String aid;// 专辑ID
        private String name;// 专辑名称
        private String poster;// 海报图
        private String vid;
        private String categoryId;// 分类ID
        private String categoryName;// 分类名称
        private String subCategoryId;
        private String pushFlag;
        private String src;
        private String subSrc;
        private Map<String, String> payPlatform;
        private int playMark;
        private String isHomemade;
        private int isPay;
        private String videoType;
        private String globalId;
        private String externalId;
        private List<JSONObject> videoList;// 华数的视频ID

        public AlbumCardDto(CardContent cardContent, CommonParam commonParam) {
            this.setDataType(cardContent.getData_type());
            this.aid = cardContent.getId();
            this.name = cardContent.getTitle();
            this.poster = cardContent.getPoster_st();
            this.vid = cardContent.getVid();
            this.categoryId = cardContent.getCategory_id();
            this.categoryName = cardContent.getCategory();
            this.subCategoryId = cardContent.getSubCategory();
            if (!CollectionUtils.isEmpty(cardContent.getPlatform_play())) {
                this.pushFlag = getMapSets(cardContent.getPlatform_play().keySet());
            }
            this.src = cardContent.getSrc();
            this.subSrc = cardContent.getSub_src();
            this.payPlatform = cardContent.getPayPlatform();
            this.playMark = cardContent.getPlayMark();
            this.isHomemade = cardContent.getIsHomemade();
            this.isPay = cardContent.getIs_pay();
            this.videoType = cardContent.getVideo_type();
            this.globalId = cardContent.getGlobal_id();
            this.externalId = cardContent.getExternal_id();
            this.videoList = cardContent.getVideo_list();
            BurrowUtil.buildBurrow(this, commonParam);
        }

        public List<JSONObject> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<JSONObject> videoList) {
            this.videoList = videoList;
        }

        public String getExternalId() {
            return externalId;
        }

        public void setExternalId(String externalId) {
            this.externalId = externalId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubSrc() {
            return subSrc;
        }

        public void setSubSrc(String subSrc) {
            this.subSrc = subSrc;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getPushFlag() {
            return pushFlag;
        }

        public void setPushFlag(String pushFlag) {
            this.pushFlag = pushFlag;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public Map<String, String> getPayPlatform() {
            return payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public int getPlayMark() {
            return playMark;
        }

        public void setPlayMark(int playMark) {
            this.playMark = playMark;
        }

        public String getIsHomemade() {
            return isHomemade;
        }

        public void setIsHomemade(String isHomemade) {
            this.isHomemade = isHomemade;
        }

        public int getIsPay() {
            return isPay;
        }

        public void setIsPay(int isPay) {
            this.isPay = isPay;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

    }

    public static class AppCardDto extends BaseData {
        private static final long serialVersionUID = -664759016873779117L;
        private String name;
        private String poster;
        private String score;
        private String size;
        private String categoryId;
        private String tvAppId;
        private String packageName;
        private String globalId;

        public AppCardDto(CardContent cardContent, CommonParam commonParam) {
            this.setDataType(cardContent.getData_type());
            this.name = cardContent.getName();
            this.poster = cardContent.getIcon();
            this.score = cardContent.getRating();
            this.size = cardContent.getShow_size();
            this.categoryId = cardContent.getCategory();
            this.tvAppId = cardContent.getTv_app_id();
            this.packageName = cardContent.getPackage_name();
            this.globalId = cardContent.getGlobal_id();
            BurrowUtil.buildBurrow(this, commonParam);
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

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getTvAppId() {
            return tvAppId;
        }

        public void setTvAppId(String tvAppId) {
            this.tvAppId = tvAppId;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

    }

    public static class MusicSingleCardDto extends BaseData {
        private static final long serialVersionUID = 8409676218617894638L;
        private String name;
        private String poster;
        private String singerName;
        private String xiamiId;
        private String globalId;

        public MusicSingleCardDto(CardContent cardContent, CommonParam commonParam) {
            this.setDataType(cardContent.getData_type());
            this.name = cardContent.getName();
            if (!StringUtil.isBlank(cardContent.getPic_prefix())) {
                this.poster = cardContent.getPic_prefix() + "400_400.jpg";
            }
            this.singerName = cardContent.getSingerName();
            this.xiamiId = cardContent.getXiamiId();
            this.globalId = cardContent.getGlobal_id();
            BurrowUtil.buildBurrow(this, commonParam);
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

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getSingerName() {
            return singerName;
        }

        public void setSingerName(String singerName) {
            this.singerName = singerName;
        }

        public String getXiamiId() {
            return xiamiId;
        }

        public void setXiamiId(String xiamiId) {
            this.xiamiId = xiamiId;
        }

    }

    public static class LiveDto extends BaseData {
        private static final long serialVersionUID = 5917442063210674409L;
        private String liveId;
        private String recordId;// 回看ID
        private String name;
        private String poster;
        private long startTime;
        private long endTime;

        public LiveDto(CardContent cardContent, CommonParam commonParam) {
            this.liveId = cardContent.getLive_id();
            this.recordId = cardContent.getRecord_id();
            this.name = cardContent.getName();
            this.poster = cardContent.getPoster_st();
            this.startTime = cardContent.getStart_time();
            this.endTime = cardContent.getEnd_time();
            BurrowUtil.buildBurrow(this, commonParam);
        }

        public String getLiveId() {
            return liveId;
        }

        public void setLiveId(String liveId) {
            this.liveId = liveId;
        }

        public String getRecordId() {
            return recordId;
        }

        public void setRecordId(String recordId) {
            this.recordId = recordId;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

    }

    public static class CardContentDto {
        private static final long serialVersionUID = 1214667889401024566L;
        private String id;
        private String title;
        private String src;
        private Integer dataType;
        private String categoryId;
        private String category;
        private String img;
        private String url;
        private Map<String, String> platformPlay;
        private String action;
        /*
         * 模块类型，以应用内部模块划分定义，用以确定应用调用模块
         */
        private Integer type;
        /*
         * 桌面模式0点播桌面，1儿童桌面，2搜索桌面
         */
        private Integer model;

        private String pushFlag;
        private String vid;
        private String videoName;
        private String subSrc;
        /**
         * 副标题
         */
        private String subTitle;
        /**
         * 评分
         */
        private String score;
        /**
         * 更新到**集、期
         */
        private String nowEpisodes;
        private String global_id;

        private Map<String, String> payPlatform;
        private Integer playMark;
        private String isHomemade;
        private Integer isPay;
        private String videoType;

        public CardContentDto() {

        }

        public Integer getDataType() {
            return dataType;
        }

        public void setDataType(Integer dataType) {
            this.dataType = dataType;
        }

        public Map<String, String> getPlatformPlay() {
            return platformPlay;
        }

        public void setPlatformPlay(Map<String, String> platformPlay) {
            this.platformPlay = platformPlay;
        }

        public Map<String, String> getPayPlatform() {
            return payPlatform;
        }

        public void setPayPlatform(Map<String, String> payPlatform) {
            this.payPlatform = payPlatform;
        }

        public String getIsHomemade() {
            return isHomemade;
        }

        public void setIsHomemade(String isHomemade) {
            this.isHomemade = isHomemade;
        }

        public Integer getPlayMark() {
            return playMark;
        }

        public void setPlayMark(Integer playMark) {
            this.playMark = playMark;
        }

        public Integer getIsPay() {
            return isPay;
        }

        public void setIsPay(Integer isPay) {
            this.isPay = isPay;
        }

        public String getVideoType() {
            return videoType;
        }

        public void setVideoType(String videoType) {
            this.videoType = videoType;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
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

        public String getGlobal_id() {
            return this.global_id;
        }

        public void setGlobal_id(String global_id) {
            this.global_id = global_id;
        }

        public String getSubSrc() {
            return this.subSrc;
        }

        public void setSubSrc(String subSrc) {
            this.subSrc = subSrc;
        }

        public String getVideoName() {
            return this.videoName;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getPushFlag() {
            return this.pushFlag;
        }

        public void setPushFlag(String pushFlag) {
            this.pushFlag = pushFlag;
        }

        public String getAction() {
            return this.action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Integer getType() {
            return this.type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getModel() {
            return this.model;
        }

        public void setModel(Integer model) {
            this.model = model;
        }

        public String getCategory() {
            return this.category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getSrc() {
            return this.src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategoryId() {
            return this.categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
