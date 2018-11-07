package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.SearchConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.StreamConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;
import serving.*;

import java.io.Serializable;
import java.util.*;

public class SearchLiveDto extends BaseDto {

    private static final long serialVersionUID = 5722345035494411769L;

    /**
     * 1：点播关联直播；
     * 2：轮播台、卫视台；
     * 3：体育频道；
     * 4：世界杯比赛信息；
     * 5：世界杯分组信息；
     * 6：音乐频道、娱乐频道
     */
    private Integer type;

    private String channel;

    private String sport;

    private String gameName;

    private String season;

    private String id;

    private String imgUrl;

    private String nameCn;

    private String nameEn;

    private String sourceId;

    private String liveUrl;

    private String categoryName;

    private String liveTypeName;

    private List<ProgramInfo> programInfos;

    private List<SearchLiveProgramDto> games;

    private List<Music> musicList; // 娱乐，音乐直播数据

    private String globalId;

    private Integer levelCategoryId; // 只有体育有

    private Integer levelSubcategoryId;// 只有体育有

    private List<Integer> splatids;

    private Map<String, String> defaultLogo;

    public SearchLiveDto() {
    }

    public SearchLiveDto(ServingResult servingResult, CommonParam commonParam) {
        if (servingResult != null && servingResult.getData_type() != null
                && servingResult.getData_type().getValue() == SearchConstant.SEARCH_DATA_TYPE_LIVE) {
            ResultDocInfo resultDocInfo = servingResult.getDoc_info();
            if (resultDocInfo != null) {
                this.id = resultDocInfo.getId();
                this.globalId = resultDocInfo.getGlobal_id();
                LiveAttribute liveAttribute = resultDocInfo.getLive_attribute();
                this.type = Integer.parseInt(String.valueOf(liveAttribute.getType()));
                // 6娱乐 2 轮播 卫视 3体育 5世界杯足球 4世界杯
                if (this.type == 6 && liveAttribute.getEnt_attribute() != null) {
                    this.liveTypeName = liveAttribute.getEnt_attribute().getLive_type_name();
                    this.imgUrl = liveAttribute.getEnt_attribute().getImage_url();
                    this.categoryName = liveAttribute.getEnt_attribute().getCategory_name();
                    this.channel = liveAttribute.getEnt_attribute().getChannel_name();
                    this.musicList = this.parseMusic(liveAttribute.getEnt_attribute());

                    // 美国地区的图片规则特殊处理
                    LiveEntAttribute liveEntAttribute = liveAttribute.getEnt_attribute();
                    if (commonParam.getSalesArea().equalsIgnoreCase("us") && liveEntAttribute != null
                            && !CollectionUtils.isEmpty(liveEntAttribute.getPlay_list())) {
                        Map<String, String> defaultLogo = liveEntAttribute.getPlay_list().get(0).getDefault_logo();
                        this.imgUrl = defaultLogo.get("pic2_960_540");
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic1_746_419");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic3_400_225");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic4_160_90");
                        }
                    }

                } else if (this.type == 2 && liveAttribute.getLunbo_attribute() != null) {
                    this.id = liveAttribute.getLunbo_attribute().getId();
                    this.liveTypeName = liveAttribute.getLunbo_attribute().getLive_type_name();
                    this.imgUrl = liveAttribute.getLunbo_attribute().getImage_url();
                    // 美国地区的图片规则特殊处理
                    if (commonParam.getSalesArea().equalsIgnoreCase("us")
                            && !CollectionUtils.isEmpty(liveAttribute.getLunbo_attribute().getDefault_logo())) {
                        Map<String, String> defaultLogo = liveAttribute.getLunbo_attribute().getDefault_logo();
                        this.imgUrl = defaultLogo.get("pic2_960_540");
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic1_746_419");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic3_400_225");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic4_160_90");
                        }
                    }
                    this.nameCn = liveAttribute.getLunbo_attribute().getName_cn();
                    this.nameEn = liveAttribute.getLunbo_attribute().getName_en();
                    this.sourceId = liveAttribute.getLunbo_attribute().getSource_id();
                    if (!CollectionUtils.isEmpty(liveAttribute.getLunbo_attribute().getPlay_games())) {
                        this.programInfos = new LinkedList<ProgramInfo>();
                        for (PlayGame playGame : liveAttribute.getLunbo_attribute().getPlay_games()) {
                            this.programInfos.add(new ProgramInfo(playGame));
                        }
                    }
                    if (!CollectionUtils.isEmpty(liveAttribute.getLunbo_attribute().getStreams())) {
                        for (StreamInfo streamInfo : liveAttribute.getLunbo_attribute().getStreams()) {
                            if (!StringUtil.isBlank(streamInfo.getStream_url())) {
                                this.liveUrl = streamInfo.getStream_url();
                                break;
                            }
                        }
                    }
                    this.splatids = liveAttribute.getLunbo_attribute().getSplatids();
                    this.defaultLogo = liveAttribute.getLunbo_attribute().getDefault_logo();
                } else if (this.type == 2 && liveAttribute.getWeishi_attribute() != null) {
                    this.id = liveAttribute.getWeishi_attribute().getId();
                    this.liveTypeName = liveAttribute.getWeishi_attribute().getLive_type_name();
                    this.imgUrl = liveAttribute.getWeishi_attribute().getImage_url();
                    // 美国地区的图片规则特殊处理
                    if (commonParam.getSalesArea().equalsIgnoreCase("us")
                            && !CollectionUtils.isEmpty(liveAttribute.getWeishi_attribute().getDefault_logo())) {
                        Map<String, String> defaultLogo = liveAttribute.getWeishi_attribute().getDefault_logo();
                        this.imgUrl = defaultLogo.get("pic2_960_540");
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic1_746_419");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic3_400_225");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic4_160_90");
                        }
                    }
                    this.nameCn = liveAttribute.getWeishi_attribute().getName_cn();
                    this.nameEn = liveAttribute.getWeishi_attribute().getName_en();
                    this.sourceId = liveAttribute.getWeishi_attribute().getSource_id();
                    if (!CollectionUtils.isEmpty(liveAttribute.getWeishi_attribute().getPlay_games())) {
                        this.programInfos = new LinkedList<ProgramInfo>();
                        for (PlayGame playGame : liveAttribute.getWeishi_attribute().getPlay_games()) {
                            this.programInfos.add(new ProgramInfo(playGame));
                        }
                    }
                    if (!CollectionUtils.isEmpty(liveAttribute.getWeishi_attribute().getStreams())) {
                        for (StreamInfo streamInfo : liveAttribute.getWeishi_attribute().getStreams()) {
                            if (!StringUtil.isBlank(streamInfo.getStream_url())) {
                                this.liveUrl = streamInfo.getStream_url();
                                break;
                            }
                        }
                    }
                    this.splatids = liveAttribute.getWeishi_attribute().getSplatids();
                    this.defaultLogo = liveAttribute.getWeishi_attribute().getDefault_logo();
                } else if (this.type == 3 && liveAttribute.getSports_attribute() != null) {
                    this.liveTypeName = liveAttribute.getSports_attribute().getLive_type_name();
                    this.imgUrl = liveAttribute.getSports_attribute().getImage_url();
                    this.gameName = liveAttribute.getSports_attribute().getLevel_subcategory();
                    this.levelCategoryId = liveAttribute.getSports_attribute().getLevel_category_id();
                    this.levelSubcategoryId = liveAttribute.getSports_attribute().getLevel_subcategory_id();
                    this.games = this.parseGames(liveAttribute.getSports_attribute());

                    // 美国地区的图片规则特殊处理
                    LiveSportsAttribute liveSportsAttribute = liveAttribute.getSports_attribute();
                    if (commonParam.getSalesArea().equalsIgnoreCase("us") && liveSportsAttribute != null
                            && !CollectionUtils.isEmpty(liveSportsAttribute.getGame_list())) {
                        Map<String, String> defaultLogo = liveSportsAttribute.getGame_list().get(0).getDefault_logo();
                        this.imgUrl = defaultLogo.get("pic2_960_540");
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic1_746_419");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic3_400_225");
                        }
                        if (!StringUtil.isBlank(this.imgUrl)) {
                            this.imgUrl = defaultLogo.get("pic4_160_90");
                        }
                    }
                }
            }
        }
    }

    private List<Music> parseMusic(LiveEntAttribute liveEntAttribute) {
        List<Music> liveList = new LinkedList<Music>();

        if (liveEntAttribute != null && !CollectionUtils.isEmpty(liveEntAttribute.getPlay_list())) {
            Music music = null;
            for (EntGame entGame : liveEntAttribute.getPlay_list()) {
                music = new Music();

                music.setBeginTime(entGame.getBegin_time());
                music.setEndTime(entGame.getEnd_time());
                music.setDt(entGame.getDt() + "");
                music.setGlobalId(entGame.getGlobal_id());
                music.setId(entGame.getId());
                if (!CollectionUtils.isEmpty(entGame.getOld_streams())) {
                    music.setLiveUrl(entGame.getOld_streams().get("live_url"));
                    music.setLiveUrlRate(entGame.getOld_streams().get("live_url_rate"));
                    music.setLiveUrl_1300(entGame.getOld_streams().get("live_url_1300"));
                    music.setLiveUrlRate_1300(entGame.getOld_streams().get("live_url_1300_rate"));
                    music.setLiveUrl_720p(entGame.getOld_streams().get("live_url_720p"));
                    music.setLiveUrlRate_720p(entGame.getOld_streams().get("live_url_720p_rate"));
                }
                music.setPlayDate(entGame.getPlay_date());
                music.setPlayUrl(entGame.getPlay_url());
                music.setPreVid(entGame.getPre_vid());
                music.setRecordingId(entGame.getRecording_id());
                music.setRelevanceStar(entGame.getRelevance_star());
                music.setStatus(entGame.getStatus());
                music.setTitle(entGame.getTitle());
                music.setVid(entGame.getVid());
                music.setPlatform(entGame.getPlayform());
                music.setPaySplatids(entGame.getPay_splatids());
                music.setSplatids(entGame.getSplatids());
                music.setDefaultLogo(entGame.getDefault_logo());
                music.setSuperStreams(this.parseSuperStream(entGame.getStreams()));
                music.setBeginTimeStamp(entGame.getBegin_timestamp());
                music.setEndTimeStamp(entGame.getEnd_timestamp());
                // 处理多视角,当前的直播放在多视角的中间位置
                if (music.getMultiProgram() != null) {
                    MultiProgramDto multiProgramDto = this.parseMultiProgram(entGame.getMulti_view_angle());
                    if (multiProgramDto != null && multiProgramDto.getBranches() != null
                            && multiProgramDto.getBranches().size() > 0) {
                        BranchDto branchDto = new BranchDto();
                        branchDto.setChannelId(music.getId());
                        branchDto.setChannelName(music.getTitle());
                        if (music.getDefaultLogo() != null) {
                            branchDto.setChannelPic(music.getDefaultLogo().get("pic1"));
                        }
                        branchDto.setStreams(music.getSuperStreams());
                        multiProgramDto.getBranches().add(multiProgramDto.getBranches().size() / 2, branchDto);
                        music.setMultiProgram(multiProgramDto);
                    }
                }
                liveList.add(music);
            }

        }
        return liveList;
    }

    public MultiProgramDto parseMultiProgram(MultiViewAngle multiViewAngle) {
        if (multiViewAngle != null && multiViewAngle.getBranches() != null && multiViewAngle.getBranches().size() > 0) {
            MultiProgramDto multiProgramDto = new MultiProgramDto();
            multiProgramDto.setBranchDesc(null);// 搜索没传
            List<BranchDto> branchList = new ArrayList<BranchDto>();
            multiProgramDto.setBranches(branchList);
            for (MultiViewAngleItem branchData : multiViewAngle.getBranches()) {
                BranchDto branchDto = new BranchDto();
                branchDto.setChannelId(branchData.getChannel_id());
                branchDto.setChannelName(branchData.getTitle());
                branchDto.setChannelEname(branchData.getChannel_ename());
                branchDto.setChannelPic(branchData.getView_image());
                branchDto.setStreams(this.parseSuperStream(branchData.getStreams()));
                branchList.add(branchDto);
            }
            return multiProgramDto;
        }
        return null;
    }

    private List<StreamDto> parseSuperStream(List<StreamInfo> streamInfoList) {
        List<StreamDto> superList = null;
        if (streamInfoList != null && streamInfoList.size() > 0) {
            superList = new LinkedList<StreamDto>();
            StreamDto info = null;
            for (StreamInfo stream : streamInfoList) {
                info = new StreamDto();
                String sCode = stream.getRate_type().replace("flv_", "");
                info.setRateType(stream.getRate_type());
                info.setStreamId(String.valueOf(stream.getStream_id()));
                info.setStreamName(stream.getStream_name());
                info.setStreamUrl(stream.getStream_url());
                info.setCode(sCode);
                info.setName(StreamConstants.STREAM_CODE_NAME_MAP.get(sCode));
                if (StringUtils.isNotBlank(info.getCode())) {
                    info.setOrder(StreamConstants.STREAM_CODE_SORT_VSLUE.get(info.getCode()));
                }
                superList.add(info);
            }
            if (superList != null) {
                Collections.sort(superList);
            }
        }

        return superList;
    }

    private List<SearchLiveProgramDto> parseGames(LiveSportsAttribute liveSportsAttribute) {
        List<SearchLiveProgramDto> gameDtoList = null;

        if (liveSportsAttribute != null && !CollectionUtils.isEmpty(liveSportsAttribute.getGame_list())) {
            SearchLiveProgramDto gameDto = null;
            gameDtoList = new ArrayList<SearchLiveProgramDto>();
            for (SportsGame sportsGame : liveSportsAttribute.getGame_list()) {
                gameDto = new SearchLiveProgramDto();
                gameDto.setDefaultLogo(sportsGame.getDefault_logo());
                gameDto.setBeginTime(String.valueOf(sportsGame.getBegin_timestamp()));
                gameDto.setDisplayEndTime(sportsGame.getDisplay_end_time());
                gameDto.setDisplayStartTime(sportsGame.getDisplay_start_time());
                gameDto.setEndTime(Integer.parseInt(String.valueOf(sportsGame.getEnd_time())));
                gameDto.setId(sportsGame.getId());
                gameDto.setPlayDate(sportsGame.getPlay_date());
                gameDto.setPlayUrl(sportsGame.getPlay_url());
                gameDto.setPreVid(sportsGame.getPre_vid());
                gameDto.setRecordingId(sportsGame.getRecording_id());
                gameDto.setStartTime(Integer.parseInt(String.valueOf(sportsGame.getStart_time())));
                gameDto.setTitle(sportsGame.getTitle());
                gameDto.setVid(sportsGame.getVid());
                // gameDto.setGroup(sportsGame.getg));
                gameDto.setGuestScore(sportsGame.getGuest_score());
                gameDto.setGuestTeam(sportsGame.getGuest_team());
                gameDto.setHomeScore(sportsGame.getHome_score());
                gameDto.setHomeTeam(sportsGame.getHome_team());
                gameDto.setIsVs(sportsGame.getIs_vs());
                // gameDto.setPlace(sportsGame.getpl);
                gameDto.setStage(String.valueOf(sportsGame.getStage()));
                gameDto.setGuestImgUrl(sportsGame.getGuest_image_url());
                gameDto.setHomeImgUrl(sportsGame.getHome_image_url());
                gameDto.setMatch(sportsGame.getMatch());
                gameDto.setPlatform(sportsGame.getPlatform());
                gameDto.setMatchStage(Integer.parseInt(String.valueOf(sportsGame.getMatch_stage())));
                gameDto.setMatchStartTime(sportsGame.getMatch_start_time());
                gameDto.setMatchEndTime(sportsGame.getMatch_end_time());
                gameDto.setBeginTimeStamp(sportsGame.getBegin_timestamp());
                gameDto.setEndTimeStamp(sportsGame.getEnd_timestamp());
                if (!CollectionUtils.isEmpty(sportsGame.getOld_streams())) {
                    gameDto.setLiveUrl(sportsGame.getOld_streams().get("live_url"));
                    gameDto.setLiveUrlRate(sportsGame.getOld_streams().get("live_url_rate"));
                    gameDto.setLiveUrl_1080p3m(sportsGame.getOld_streams().get("live_url_1080p3m"));
                    gameDto.setLiveUrlRate_1080p3m(sportsGame.getOld_streams().get("live_url_1080p3m_rate"));
                    gameDto.setLiveUrl_1300(sportsGame.getOld_streams().get("live_url_1300"));
                    gameDto.setLiveUrlRate_1300(sportsGame.getOld_streams().get("live_url_1300_rate"));
                    gameDto.setLiveUrl_720p(sportsGame.getOld_streams().get("live_url_720p"));
                    gameDto.setLiveUrlRate_720p(sportsGame.getOld_streams().get("live_url_720p_rate"));
                }
                gameDto.setSplatids(sportsGame.getSplatids());
                gameDto.setPaySplatids(sportsGame.getPay_splatids());
                gameDto.setBeginTimeStamp(sportsGame.getBegin_timestamp());
                gameDto.setEndTimeStamp(sportsGame.getEnd_timestamp());
                gameDtoList.add(gameDto);
            }
        }
        return gameDtoList;
    }

    public String getLiveUrl() {
        return this.liveUrl;
    }

    public void setLiveUrl(String liveUrl) {
        this.liveUrl = liveUrl;
    }

    public List<Integer> getSplatids() {
        return this.splatids;
    }

    public void setSplatids(List<Integer> splatids) {
        this.splatids = splatids;
    }

    public Map<String, String> getDefaultLogo() {
        return defaultLogo;
    }

    public void setDefaultLogo(Map<String, String> defaultLogo) {
        this.defaultLogo = defaultLogo;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNameCn() {
        return this.nameCn;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public String getNameEn() {
        return this.nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Integer getLevelCategoryId() {
        return this.levelCategoryId;
    }

    public void setLevelCategoryId(Integer levelCategoryId) {
        this.levelCategoryId = levelCategoryId;
    }

    public Integer getLevelSubcategoryId() {
        return this.levelSubcategoryId;
    }

    public void setLevelSubcategoryId(Integer levelSubcategoryId) {
        this.levelSubcategoryId = levelSubcategoryId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSport() {
        return this.sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getGameName() {
        return this.gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public List<SearchLiveProgramDto> getGames() {
        return this.games;
    }

    public void setGames(List<SearchLiveProgramDto> games) {
        this.games = games;
    }

    public static class SearchLiveProgramDto implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -6802154253285761838L;
        private String displayEndTime;
        private String displayStartTime;
        private Integer endTime;
        private String id;
        private String playDate;
        private String playUrl;
        private String preVid;
        private String recordingId;
        private Integer startTime;
        private String title;
        private String vid;
        private String group;
        private String guestScore;
        private String guestTeam;
        private String homeScore;
        private String homeTeam;
        private String isVs;
        private String place;
        private String stage;
        private String guestImgUrl;
        private String homeImgUrl;
        private String match;
        private String platform;
        private String beginTime;
        private String status;
        private Map<String, String> defaultLogo;
        private long beginTimeStamp;
        private long endTimeStamp;

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

        public Map<String, String> getDefaultLogo() {
            return this.defaultLogo;
        }

        public void setDefaultLogo(Map<String, String> defaultLogo) {
            this.defaultLogo = defaultLogo;
        }

        public String getBeginTime() {
            return this.beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        private Integer matchStage;
        private String matchStartTime;
        private String matchEndTime;

        private String liveUrl;
        private String liveUrlRate;
        private String liveUrl_1080p3m;
        private String liveUrlRate_1080p3m;
        private String liveUrl_1300;
        private String liveUrlRate_1300;
        private String liveUrl_720p;
        private String liveUrlRate_720p;
        private List<Integer> splatids;
        private List<Integer> paySplatids;

        public List<Integer> getSplatids() {
            return this.splatids;
        }

        public void setSplatids(List<Integer> splatids) {
            this.splatids = splatids;
        }

        public List<Integer> getPaySplatids() {
            return this.paySplatids;
        }

        public void setPaySplatids(List<Integer> paySplatids) {
            this.paySplatids = paySplatids;
        }

        public String getLiveUrl() {
            return this.liveUrl;
        }

        public void setLiveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
        }

        public String getLiveUrlRate() {
            return this.liveUrlRate;
        }

        public void setLiveUrlRate(String liveUrlRate) {
            this.liveUrlRate = liveUrlRate;
        }

        public String getLiveUrl_1080p3m() {
            return this.liveUrl_1080p3m;
        }

        public void setLiveUrl_1080p3m(String liveUrl_1080p3m) {
            this.liveUrl_1080p3m = liveUrl_1080p3m;
        }

        public String getLiveUrlRate_1080p3m() {
            return this.liveUrlRate_1080p3m;
        }

        public void setLiveUrlRate_1080p3m(String liveUrlRate_1080p3m) {
            this.liveUrlRate_1080p3m = liveUrlRate_1080p3m;
        }

        public String getLiveUrl_1300() {
            return this.liveUrl_1300;
        }

        public void setLiveUrl_1300(String liveUrl_1300) {
            this.liveUrl_1300 = liveUrl_1300;
        }

        public String getLiveUrlRate_1300() {
            return this.liveUrlRate_1300;
        }

        public void setLiveUrlRate_1300(String liveUrlRate_1300) {
            this.liveUrlRate_1300 = liveUrlRate_1300;
        }

        public String getLiveUrl_720p() {
            return this.liveUrl_720p;
        }

        public void setLiveUrl_720p(String liveUrl_720p) {
            this.liveUrl_720p = liveUrl_720p;
        }

        public String getLiveUrlRate_720p() {
            return this.liveUrlRate_720p;
        }

        public void setLiveUrlRate_720p(String liveUrlRate_720p) {
            this.liveUrlRate_720p = liveUrlRate_720p;
        }

        public Integer getMatchStage() {
            return this.matchStage;
        }

        public void setMatchStage(Integer matchStage) {
            this.matchStage = matchStage;
        }

        public String getMatchStartTime() {
            return this.matchStartTime;
        }

        public void setMatchStartTime(String matchStartTime) {
            this.matchStartTime = matchStartTime;
        }

        public String getMatchEndTime() {
            return this.matchEndTime;
        }

        public void setMatchEndTime(String matchEndTime) {
            this.matchEndTime = matchEndTime;
        }

        public String getGroup() {
            return this.group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getGuestScore() {
            return this.guestScore;
        }

        public void setGuestScore(String guestScore) {
            this.guestScore = guestScore;
        }

        public String getGuestTeam() {
            return this.guestTeam;
        }

        public void setGuestTeam(String guestTeam) {
            this.guestTeam = guestTeam;
        }

        public String getHomeScore() {
            return this.homeScore;
        }

        public void setHomeScore(String homeScore) {
            this.homeScore = homeScore;
        }

        public String getHomeTeam() {
            return this.homeTeam;
        }

        public void setHomeTeam(String homeTeam) {
            this.homeTeam = homeTeam;
        }

        public String getIsVs() {
            return this.isVs;
        }

        public void setIsVs(String isVs) {
            this.isVs = isVs;
        }

        public String getPlace() {
            return this.place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public String getStage() {
            return this.stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getDisplayEndTime() {
            return this.displayEndTime;
        }

        public void setDisplayEndTime(String displayEndTime) {
            this.displayEndTime = displayEndTime;
        }

        public String getDisplayStartTime() {
            return this.displayStartTime;
        }

        public void setDisplayStartTime(String displayStartTime) {
            this.displayStartTime = displayStartTime;
        }

        public Integer getEndTime() {
            return this.endTime;
        }

        public void setEndTime(Integer endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlayDate() {
            return this.playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public String getPlayUrl() {
            return this.playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getPreVid() {
            return this.preVid;
        }

        public void setPreVid(String preVid) {
            this.preVid = preVid;
        }

        public String getRecordingId() {
            return this.recordingId;
        }

        public void setRecordingId(String recordingId) {
            this.recordingId = recordingId;
        }

        public Integer getStartTime() {
            return this.startTime;
        }

        public void setStartTime(Integer startTime) {
            this.startTime = startTime;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getGuestImgUrl() {
            return this.guestImgUrl;
        }

        public void setGuestImgUrl(String guestImgUrl) {
            this.guestImgUrl = guestImgUrl;
        }

        public String getHomeImgUrl() {
            return this.homeImgUrl;
        }

        public void setHomeImgUrl(String homeImgUrl) {
            this.homeImgUrl = homeImgUrl;
        }

        public String getMatch() {
            return this.match;
        }

        public void setMatch(String match) {
            this.match = match;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }
    }

    public List<ProgramInfo> getProgramInfos() {
        return this.programInfos;
    }

    public void setProgramInfos(List<ProgramInfo> programInfos) {
        this.programInfos = programInfos;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLiveTypeName() {
        return this.liveTypeName;
    }

    public void setLiveTypeName(String liveTypeName) {
        this.liveTypeName = liveTypeName;
    }

    public List<Music> getMusicList() {
        return this.musicList;
    }

    public void setMusicList(List<Music> musicList) {
        this.musicList = musicList;
    }

    static class MultiProgramDto {
        private String branchDesc;
        private List<BranchDto> branches;

        public String getBranchDesc() {
            return this.branchDesc;
        }

        public void setBranchDesc(String branchDesc) {
            this.branchDesc = branchDesc;
        }

        public List<BranchDto> getBranches() {
            return this.branches;
        }

        public void setBranches(List<BranchDto> branches) {
            this.branches = branches;
        }
    }

    static class BranchDto {
        private String channelId;
        private String channelName;
        private String channelPic;
        private String channelEname;
        private List<StreamDto> streams;

        public String getChannelId() {
            return this.channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getChannelName() {
            return this.channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getChannelPic() {
            return this.channelPic;
        }

        public void setChannelPic(String channelPic) {
            this.channelPic = channelPic;
        }

        public String getChannelEname() {
            return this.channelEname;
        }

        public void setChannelEname(String channelEname) {
            this.channelEname = channelEname;
        }

        public List<StreamDto> getStreams() {
            return this.streams;
        }

        public void setStreams(List<StreamDto> streams) {
            this.streams = streams;
        }
    }

    public static class StreamDto implements Comparable<StreamDto> {
        private String streamId;// 流ID
        private String streamName;// 流名称
        private String rateType;// 码率类型，参考《码率类型词典编码》http://st.live.letv.com/live/code/00014.json
        private String streamUrl;// 对该客户端有效的直播流播放地址
        private String name;
        private String code;
        private Integer order; // 码流清晰度的顺序

        public Integer getOrder() {
            return this.order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getStreamId() {
            return this.streamId;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public String getStreamName() {
            return this.streamName;
        }

        public void setStreamName(String streamName) {
            this.streamName = streamName;
        }

        public String getRateType() {
            return this.rateType;
        }

        public void setRateType(String rateType) {
            this.rateType = rateType;
        }

        public String getStreamUrl() {
            return this.streamUrl;
        }

        public void setStreamUrl(String streamUrl) {
            this.streamUrl = streamUrl;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public int compareTo(StreamDto s) {
            Integer otherCodeValue = StreamConstants.STREAM_CODE_SORT_VSLUE.get(s.getCode());
            otherCodeValue = otherCodeValue == null ? 0 : otherCodeValue.intValue();
            Integer thisCodeValue = StreamConstants.STREAM_CODE_SORT_VSLUE.get(this.getCode());
            thisCodeValue = thisCodeValue == null ? 0 : thisCodeValue.intValue();
            if (otherCodeValue > thisCodeValue) {
                return 1;
            } else if (otherCodeValue < thisCodeValue) {
                return -1;
            }
            // 以上都规则都无法区分的数据，向后追加
            return 1;
        }

        @Override
        public String toString() {
            return "LiveChannelStream [streamId=" + this.streamId + ", streamName=" + this.streamName + ", rateType="
                    + this.rateType + ", streamUrl=" + this.streamUrl + "]";
        }

    }

    public static class Music {
        private String globalId; // 节目唯一标识（搜索用于记录点击日志，各端日志需记录）
        private String dt; // 数据类型（搜索用于记录点击日志，各端日志需记录
        private String playDate; // 播放日期
        private String relevanceStar; // 关联明星(optional,有些数据有，有些为空。例如：娱乐数据没有，音乐数据部分有)
        private String playUrl; // 播放地址(optional,有些数据有，有些为空。例如：娱乐数据没有，音乐数据部分有)
        private String status; // 播放状态
        private String beginTime; // 开始时间
        private String endTime; // 结束时间
        private long beginTimeStamp;
        private long endTimeStamp;
        private String recordingId; // 录制ID
        private String title; // 节目标题;
        private String preVid; // 预告视频id
        private String vid; // 视频ID;
        private String id; // 数据ID，主键
        private String platform; // 播放平台 PC TV等
        private String liveUrl; // 直播流地址
        private String liveUrlRate;
        private String liveUrl_1300;
        private String liveUrlRate_1300;
        private String liveUrl_720p;
        private String liveUrlRate_720p;
        private List<Integer> splatids;
        private List<Integer> paySplatids;
        private Map<String, String> defaultLogo;
        private MultiProgramDto multiProgram;
        private List<StreamDto> superStreams;

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

        public List<StreamDto> getSuperStreams() {
            return this.superStreams;
        }

        public void setSuperStreams(List<StreamDto> superStreams) {
            this.superStreams = superStreams;
        }

        public MultiProgramDto getMultiProgram() {
            return this.multiProgram;
        }

        public void setMultiProgram(MultiProgramDto multiProgram) {
            this.multiProgram = multiProgram;
        }

        public Map<String, String> getDefaultLogo() {
            return this.defaultLogo;
        }

        public void setDefaultLogo(Map<String, String> defaultLogo) {
            this.defaultLogo = defaultLogo;
        }

        public List<Integer> getSplatids() {
            return this.splatids;
        }

        public void setSplatids(List<Integer> splatids) {
            this.splatids = splatids;
        }

        public List<Integer> getPaySplatids() {
            return this.paySplatids;
        }

        public void setPaySplatids(List<Integer> paySplatids) {
            this.paySplatids = paySplatids;
        }

        public String getGlobalId() {
            return this.globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public String getDt() {
            return this.dt;
        }

        public void setDt(String dt) {
            this.dt = dt;
        }

        public String getPlayDate() {
            return this.playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public String getRelevanceStar() {
            return this.relevanceStar;
        }

        public void setRelevanceStar(String relevanceStar) {
            this.relevanceStar = relevanceStar;
        }

        public String getPlayUrl() {
            return this.playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBeginTime() {
            return this.beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return this.endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getRecordingId() {
            return this.recordingId;
        }

        public void setRecordingId(String recordingId) {
            this.recordingId = recordingId;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPreVid() {
            return this.preVid;
        }

        public void setPreVid(String preVid) {
            this.preVid = preVid;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getLiveUrl() {
            return this.liveUrl;
        }

        public void setLiveUrl(String liveUrl) {
            this.liveUrl = liveUrl;
        }

        public String getLiveUrlRate() {
            return this.liveUrlRate;
        }

        public void setLiveUrlRate(String liveUrlRate) {
            this.liveUrlRate = liveUrlRate;
        }

        public String getLiveUrl_1300() {
            return this.liveUrl_1300;
        }

        public void setLiveUrl_1300(String liveUrl_1300) {
            this.liveUrl_1300 = liveUrl_1300;
        }

        public String getLiveUrlRate_1300() {
            return this.liveUrlRate_1300;
        }

        public void setLiveUrlRate_1300(String liveUrlRate_1300) {
            this.liveUrlRate_1300 = liveUrlRate_1300;
        }

        public String getLiveUrl_720p() {
            return this.liveUrl_720p;
        }

        public void setLiveUrl_720p(String liveUrl_720p) {
            this.liveUrl_720p = liveUrl_720p;
        }

        public String getLiveUrlRate_720p() {
            return this.liveUrlRate_720p;
        }

        public void setLiveUrlRate_720p(String liveUrlRate_720p) {
            this.liveUrlRate_720p = liveUrlRate_720p;
        }

    }

    public static class ProgramInfo implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 3030793979235026748L;
        private String descriptionBack;
        private String descriptionFront;
        private String displayStartTime;
        private String duration;
        private String endTime;
        private String id;
        private String isJoin;
        private String language;
        private String playDate;
        private String playnum;
        private String startTime;
        private String title;
        private String total;
        private String year;
        private long startTimeStamp;
        private long endTimeStamp;

        public ProgramInfo() {
        }

        public ProgramInfo(PlayGame playGame) {
            this.displayStartTime = playGame.getDisplay_start_time();
            this.duration = playGame.getDuration() + "";
            this.id = playGame.getId();
            this.playDate = playGame.getPlay_date();
            this.title = playGame.getTitle();
            this.startTimeStamp = playGame.getBegin_timestamp();
            this.endTimeStamp = playGame.getEnd_timestamp();
        }

        public long getStartTimeStamp() {
            return startTimeStamp;
        }

        public void setStartTimeStamp(long startTimeStamp) {
            this.startTimeStamp = startTimeStamp;
        }

        public long getEndTimeStamp() {
            return endTimeStamp;
        }

        public void setEndTimeStamp(long endTimeStamp) {
            this.endTimeStamp = endTimeStamp;
        }

        public String getDescriptionBack() {
            return this.descriptionBack;
        }

        public void setDescriptionBack(String descriptionBack) {
            this.descriptionBack = descriptionBack;
        }

        public String getDescriptionFront() {
            return this.descriptionFront;
        }

        public void setDescriptionFront(String descriptionFront) {
            this.descriptionFront = descriptionFront;
        }

        public String getDisplayStartTime() {
            return this.displayStartTime;
        }

        public void setDisplayStartTime(String displayStartTime) {
            this.displayStartTime = displayStartTime;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getEndTime() {
            return this.endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsJoin() {
            return this.isJoin;
        }

        public void setIsJoin(String isJoin) {
            this.isJoin = isJoin;
        }

        public String getLanguage() {
            return this.language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getPlayDate() {
            return this.playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public String getPlaynum() {
            return this.playnum;
        }

        public void setPlaynum(String playnum) {
            this.playnum = playnum;
        }

        public String getStartTime() {
            return this.startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotal() {
            return this.total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getYear() {
            return this.year;
        }

        public void setYear(String year) {
            this.year = year;
        }

    }

}
