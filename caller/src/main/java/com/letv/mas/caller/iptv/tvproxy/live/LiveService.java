package com.letv.mas.caller.iptv.tvproxy.live;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse.CmsPageTpResponseData.CmsPageTpResponseFrag;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LiveProjectPermissionResponseTp.PermissionInfo;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.LivePeopleCountResponse.LivePeopleCount;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.common.service.BaseService;
import com.letv.mas.caller.iptv.tvproxy.common.constant.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsBlockContentTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsPageTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpConstants;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto.LiveCategoryDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto.LiveChannelDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.dto.LiveIndexPageDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.request.GetLiveListRequest;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.response.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.LivePackage;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveProgram;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.LiveRoomChannel;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.live.TvStreamInfoDto;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.common.util.*;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveProject;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveProjectDto;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveSDK;
import com.letv.mas.caller.iptv.tvproxy.live.model.dto.LiveSDKDto;
import com.letv.mas.caller.iptv.tvproxy.video.constants.VideoConstants;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.CheckLiveDto;
import com.letv.mas.caller.iptv.tvproxy.vip.service.VipMetadataService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

@Service(value = "v2.LiveService")
public class LiveService extends BaseService {

    private final static Logger LOG = LoggerFactory.getLogger(LiveService.class);

    private static LiveIndexPageDto liveIndexPageDto = null;
    private static Long LASTER_UPDATE_TIME = 0L;
    private static long UPDATE_TIME_INTERVAL = 3 * 60 * 1000;

    @Autowired
    VipMetadataService vipMetadataService;

    /**
     * LIVE频道 —— 节目单列表，直播大厅
     */
    public LiveDto getLiveList(GetLiveListRequest request, CommonParam commonParam) {
        String logPrefix = "getLiveChannel_" + request.getChannelId() + "_" + request.getQueryType() + "_"
                + request.getLiveIds();

        LiveDto live = null;
        if (LiveConstants.LIVE_LIST_QUERY_TYPE_LIVE_ROOM.equals(request.getQueryType())) {
            // 查询直播大厅数据
            // refresh为1时从tp中取数据，并强刷入内存，为0时依次从内存、缓存、tp中读数据
            LiveDto wholeLiveDto = null;
            if (request.getRefresh() == 0) {
                wholeLiveDto = LiveUtil
                        .getLiveFromMemory(request.getBroadcastId(), request.getChannelId(), commonParam);
                if (wholeLiveDto == null) {
                    LOG.info(logPrefix + " : can't find live in memory,try to get from redis");
                    wholeLiveDto = this.getLiveDtoFromCache(request.getChannelId(), commonParam);
                    LiveUtil.setLiveToMemory(request.getBroadcastId(), request.getChannelId(), commonParam,
                            wholeLiveDto);
                }
                if (wholeLiveDto != null) {
                    wholeLiveDto.setFlushMode(0);
                }
            } else {
                // 强刷内存数据，直接读取缓存数据，定时任务或监控线程会更新
                wholeLiveDto = this.getLiveDtoFromCache(request.getChannelId(), commonParam);
                LiveUtil.setLiveToMemory(request.getBroadcastId(), request.getChannelId(), commonParam, wholeLiveDto);
                if (wholeLiveDto != null) {
                    wholeLiveDto.setFlushMode(1);
                }
                LOG.info(logPrefix + " : get live from Live Room and refresh the memory");
            }

            /*
             * 2015-09-23，李宇春演唱会特殊需求，需要在不同设备上过滤数据；因为定时刷出来的直播数据，仅考虑了播控方、splatId，
             * 而没有考虑设备信息，所以无法在缓存的直播大厅数据中修改，而是在最后返回时才筛选；
             * 需要考虑直接操作缓存数据是否影响其他设备的展示
             */
            String liveRoomFilterKey = LiveUtil.buildLiveRoomFilterKey(request.getTerminalSeries(),
                    request.getBroadcastId(), request.getSplatId());
            String liveRoomFilterLives = null;
            if (LiveConstants.LIVE_ROOM_FILTER_DEVICE_LIVE_MAP != null) {
                liveRoomFilterLives = StringUtils.trimToNull(LiveConstants.LIVE_ROOM_FILTER_DEVICE_LIVE_MAP
                        .get(liveRoomFilterKey));
            }

            if (liveRoomFilterLives != null) {
                // 需要过滤则重新构造LiveDto
                live = new LiveDto();

                if (wholeLiveDto != null) {
                    live.setFlushMode(wholeLiveDto.getFlushMode());
                    live.setInterval(wholeLiveDto.getInterval());

                    List<LiveProgram> programs = wholeLiveDto.getPrograms();
                    List<LiveProgram> filteredPrograms = new LinkedList<LiveProgram>();

                    if (!CollectionUtils.isEmpty(programs)) {
                        for (LiveProgram program : programs) {
                            if (program != null
                                    && !liveRoomFilterLives.contains(LiveUtil.buildLiveRoomFilterLiveKey(program
                                            .getId()))) {
                                // program不为空且直播id不在过滤列表中的才可以展示
                                filteredPrograms.add(program);
                            }
                        }
                    }

                    live.setPrograms(filteredPrograms);
                }
            } else {
                // 不需要过滤则直接返回
                live = wholeLiveDto;
            }
        } else if (LiveConstants.LIVE_LIST_QUERY_TYPE_MULTI.equals(request.getQueryType())) {
            // 查询单条或若干条数据
            if (StringUtils.isNotBlank(request.getLiveIds())) {
                String[] liveIdArray = request.getLiveIds().split(",");
                List<LiveProgram> liveList = null;

                if (liveIdArray != null && liveIdArray.length > 0) {
                    liveList = new ArrayList<LiveProgram>();
                    int liveNum = liveIdArray.length;
                    String[] ids = new String[liveNum];
                    for (int i = 0; i < liveNum; i++) {
                        ids[i] = LiveUtil.buildLiveKey(liveIdArray[i], request.getBroadcastId(), request.getSplatId());
                    }

                    Map<String, LiveProgram> liveProgramMap = this.facadeCacheDao.getLiveCacheDao()
                            .getLiveProgramByLiveIds(ids);
                    if (!CollectionUtils.isEmpty(liveProgramMap)) {
                        for (Entry<String, LiveProgram> liveEntry : liveProgramMap.entrySet()) {
                            if (liveEntry.getValue() != null) {
                                liveList.add(liveEntry.getValue());
                            }
                        }
                    }
                }

                if (CollectionUtils.isEmpty(liveList)) {
                    LOG.info(logPrefix + " : can't get multi lives");
                }
                // 没拿到数据也返回，这里不返回错误码
                live = new LiveDto();
                live.setPrograms(liveList);
                live.setFlushMode(1);
                // 从缓存中读取数据刷新时间间隔
                live.setInterval(this
                        .getLiveListRealTimeFreshInterval(LiveConstants.LIVE_LIST_REAL_TIME_DATA_REFRESH_INTERVAL_DEFAULT));
            }
        }
        return live;
    }

    /**
     * 
     */
    public Response<LiveIndexPageDto> getLiveIndexPageDto(CommonParam commonParam) {
        this.updateLiveIndexInfo(commonParam);
        return new Response<LiveIndexPageDto>(liveIndexPageDto);
    }

    public void updateLiveIndexInfo(CommonParam commonParam) {
        if (System.currentTimeMillis() - LASTER_UPDATE_TIME > UPDATE_TIME_INTERVAL) {
            class Run implements Runnable {
                private CommonParam commonParam;

                public Run(final CommonParam commonParam) {
                    this.commonParam = commonParam;
                }

                @Override
                public void run() {
                    LiveIndexPageDto liveIndexPageDto = getLiveIndexInfo(commonParam);
                    synchronized (liveIndexPageDto) {
                        if (liveIndexPageDto != null) {
                            LiveService.liveIndexPageDto = liveIndexPageDto;
                        }
                    }
                }
            }
            Thread t = new Thread(new Run(commonParam));
            t.start();
            LASTER_UPDATE_TIME = System.currentTimeMillis();
        }
    }

    private LiveIndexPageDto getLiveIndexInfo(CommonParam commonParam) {
        LiveHomePageResponse liveHomePageResponse = this.facadeTpDao.getLiveTpDao().getLiveHomePage(commonParam); // 获取live首页数据
        LiveIndexPageDto liveIndexPageDto = null;
        if (liveHomePageResponse != null && liveHomePageResponse.getData() != null) {
            liveIndexPageDto = liveHomePageResponse.getData();
            List<LiveCategoryDto> categoryList = new LinkedList<LiveCategoryDto>();
            List<LiveCategoryDto> categoryDtoList = liveIndexPageDto.getCategoryList();
            for (LiveCategoryDto liveCategoryDto : categoryDtoList) {
                String categoryId = liveCategoryDto.getCategoryId();
                List<LiveChannelDto> liveChannelDtoList = liveCategoryDto.getChannelList();
                for (LiveChannelDto liveChannelDto : liveChannelDtoList) {
                    liveChannelDto.setStreams(null); // 屏蔽码流信息
                    liveChannelDto.setMultiProgram(null); // 屏蔽
                    liveChannelDto.setLeWords(null);// 屏蔽乐词信息
                    if ("001".equals(categoryId)) { // 直播列表添加在线人数
                        String channelId = liveChannelDto.getChannelId();
                        String channelName = liveChannelDto.getChannelName();
                        String type = liveChannelDto.getType();
                        LivePeopleCountResponse livePeopleCountResponse = this.facadeTpDao.getLiveTpDao()
                                .getLivePeopleCount(channelId, channelName, type, commonParam);
                        if (livePeopleCountResponse != null && livePeopleCountResponse.getData() != null) {
                            LivePeopleCount livePeopleCount = livePeopleCountResponse.getData();
                            if (livePeopleCount != null) {
                                liveChannelDto.setOnlineNum(livePeopleCount.getOnlineNum());
                            }
                        }
                    }
                }
                if (!"003".equals(categoryId)) { // 过滤掉推荐
                    categoryList.add(liveCategoryDto);// 处理完的category加入新的categoryList
                }
            }
            liveIndexPageDto.setCategoryList(categoryList);// 设置新的categoryList
        }
        return liveIndexPageDto;
    }

    public Response<String> getLiveChatRoomAddr(String chatroomId) {
        Response<String> response = new Response<String>();
        LiveChatRoomHistoryResponse liveChatRoomHistory = this.facadeTpDao.getLiveTpDao().getLiveChatRoomHistory(
                chatroomId);
        if (liveChatRoomHistory != null) {
            response.setData(liveChatRoomHistory.getServerAddr());
        } else {
            response.setResultStatus(0);
        }
        return response;
    }

    /**
     * 从redis中获得节目单列表
     * @param channelId
     * @return
     */
    public LiveDto getLiveDtoFromCache(Integer channelId, CommonParam commonParam) {
        String logPrefix = "getLiveDtoFromCache_" + commonParam.getBroadcastId() + "_" + channelId;

        // 默认取播控为0，频道id为797的数据
        if (commonParam.getBroadcastId() == null || CommonConstants.CIBN != commonParam.getBroadcastId()) {
            commonParam.setBroadcastId(0);
        }
        if (channelId == null) {
            channelId = 797;
        }
        String broadcastKey = String.valueOf(commonParam.getBroadcastId());
        // 美国行货key值包含langCode,并且美国行货key:E_LiveDto--0-en_us-797多一个横线
        if (TerminalUtil.isLetvUs(commonParam)) {
            broadcastKey = "-" + commonParam.getBroadcastId() + "-" + commonParam.getLangcode();
        }
        LiveDto live = this.facadeCacheDao.getLiveCacheDao().getLiveRoomLiveDto(broadcastKey, channelId);

        return live;
    }

    /**
     * 向redis中放节目单列表
     * @param broadcastId
     * @param channelId
     * @param liveDto
     */
    public void setLiveDtoToCache(Integer broadcastId, Integer channelId, LiveDto liveDto) {
        String logPrefix = "setLiveDtoToCache_" + broadcastId + "_" + channelId;
        // 默认放播控为0，频道id为797的数据
        if (broadcastId == null || CommonConstants.CIBN != broadcastId) {
            broadcastId = 0;
        }
        if (channelId == null) {
            channelId = 797;
        }

        this.facadeCacheDao.getLiveCacheDao().setLiveRoomLiveDto(broadcastId, channelId, liveDto);
    }

    /**
     * 获得节目单列表
     * @param size
     * @param channelId
     * @param wcode
     * @param langCode
     * @return
     */
    public List<LiveProgram> getSortedLivePrograms(Integer size, Integer channelId, String wcode, String langCode,
            Integer broadcastId, String splatid) {
        Map<String, LiveProgram> programMap = this.getLivePrograms(size, wcode, langCode, broadcastId, splatid,
                LiveConstants.LIVE_LIST);
        Set<LiveProgram> livePrograms = new TreeSet<LiveProgram>(programMap.values());

        List<LiveProgram> liveList = new ArrayList<LiveProgram>();
        liveList.addAll(livePrograms);

        return this.subLiveList(liveList, size);
    }

    /**
     * 将直播截取为固定长度，要求保留所有直播中
     */
    private List<LiveProgram> subLiveList(List<LiveProgram> list, int size) {
        List<LiveProgram> result = new ArrayList<LiveProgram>();// 支持序列化
        if (list == null) {
            return result;
        }
        int count = 0;// 记录非预告的直播数量
        long now = System.currentTimeMillis();
        long point = now + 1000 * 60 * 60 * 24;// 当前时间之后24小时的预告节目也需要被截取，所以在这里，point=now+24小时
        for (LiveProgram l : list) {
            try {
                // 记录非预告的直播数量
                if (Long.parseLong(l.getStartTime()) < point) {
                    count++;
                }
            } catch (Exception e) {
                LOG.error("subLiveList err!");
            }

        }
        // 最后返回的结果，如果比size大，那么，截取其中的size条记录，但是，不是从第一条截取，要求保留所有的直播
        if (size < count) {
            result.addAll(list.subList(count - size, count));
        } else if (size >= count && size < list.size()) {
            result.addAll(list.subList(0, size));
        } else {
            result.addAll(list);
        }
        return result;
    }

    /**
     * 获得直播时间 语言 key
     * @param startTime
     * @return
     */
    private static String getLiveDate(String startTime, String langCode) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        try {
            startDate = sdf.parse(startTime);
        } catch (ParseException e) {
            LOG.error("getLiveDate error", e.getMessage(), e);
        }
        String startTimeStr = TimeUtil.getDateString(startDate, TimeUtil.DATE_MM_DD_FORMAT1);
        String langKey = getLiveDateLangKey(startTimeStr);

        String date = null;

        date = MessageUtils.getMessage(langKey, langCode);
        if (StringUtils.isEmpty(date)) {
            date = TimeUtil.getDateString(startDate, TimeUtil.DATE_MM_DD_FORMAT1);
        }

        return date;
    }

    private static String getLiveDateLangKey(String date) {
        String dateLangKey = null;
        Calendar ttomorrow = TimeUtil.startOfDayTomorrow();
        ttomorrow.add(Calendar.DAY_OF_MONTH, 1);
        if (TimeUtil.getDateString(TimeUtil.truncateDay(Calendar.getInstance()), TimeUtil.DATE_MM_DD_FORMAT1)
                .equalsIgnoreCase(date)) {
            dateLangKey = LiveConstants.LIVE_LIVETIME_TODAY;// 今天
        } else if (TimeUtil.getDateString(TimeUtil.startOfDayTomorrow(), TimeUtil.DATE_MM_DD_FORMAT1).equalsIgnoreCase(
                date)) {
            dateLangKey = LiveConstants.LIVE_LIVETIME_TOMORROW;// 明天
        } else if (TimeUtil.getDateString(ttomorrow, TimeUtil.DATE_MM_DD_FORMAT1).equalsIgnoreCase(date)) {
            dateLangKey = LiveConstants.LIVE_LIVETIME_T_TOMORROW;// 后天
        } else if (TimeUtil.getDateString(TimeUtil.startOfDayYesterday(), TimeUtil.DATE_MM_DD_FORMAT1)
                .equalsIgnoreCase(date)) {
            dateLangKey = LiveConstants.LIVE_LIVETIME_YESTODAY;// 昨天
        } else if (TimeUtil.getDateString(TimeUtil.backOneDay(TimeUtil.startOfDayYesterday()),
                TimeUtil.DATE_MM_DD_FORMAT1).equalsIgnoreCase(date)) {
            dateLangKey = LiveConstants.LIVE_LIVETIME_Y_YESTODAY;// 前天
        }

        return dateLangKey;
    }

    public List<TvStreamInfoDto> tp2StreamInfo(String liveId, String selectId, String langCode, Integer broadcastId,
                                               String splatid) {
        List<TvStreamInfoDto> tvStreamInfo = new ArrayList<TvStreamInfoDto>();
        if (selectId == null || selectId.length() < 1) {
            return tvStreamInfo;
        }
        List<TvStreamInfo> tvStreamTP = this.facadeTpDao.getLiveTpDao().getTvStreamResponseTp(selectId, splatid);
        tvStreamInfo = this.parseAndSortLiveStreams(tvStreamTP, splatid, liveId, broadcastId, langCode);

        return tvStreamInfo;
    }

    private static String getPk(String home, String guest, String score) {
        return StringUtils.trimToEmpty(home) + StringUtils.trimToEmpty(score) + StringUtils.trimToEmpty(guest);
    }

    private static String getScore(String homeScore, String guestScore) {
        homeScore = homeScore == null ? "" : homeScore;
        guestScore = guestScore == null ? "" : guestScore;
        return " " + homeScore + "-" + guestScore + " ";
    }

    private static String getMatchStage(String sportsSubType, String matchStage) {
        return sportsSubType + matchStage;
    }

    private static Long getComplextDuration(String beginTime, String endTime) {
        return TimeUtil.string2timestamp(endTime) - TimeUtil.string2timestamp(beginTime);
    }

    /**
     * 获取直播专题列表
     * @param zid
     * @param userId
     * @return
     */
    public LiveProjectDto getliveProjects(String zid, String userId, int broadcastId, String splatid,
                                          boolean cacheEnable, String mac, String deviceKey, CommonParam commonParam) {
        LiveProjectDto result = new LiveProjectDto();
        result.setSplatId(splatid);
        // 获取cms配置的直播专题数据
        result = this.getCMSConfig(result, zid, broadcastId, commonParam);
        // 获取静态直播数据
        result = this.mergeLiveProjects(result, broadcastId);
        // 获取动态权限数据
        result = this.mergePermissionProjects(result, userId, mac, deviceKey);
        // 排序
        // Collections.sort(result.getProjects());
        // 刷新冗余数据以及size
        // result.fresh();
        return result;
    }

    /**
     * 获取直播专题列表
     * @param zid
     * @param userId
     * @return
     */
    public LiveProjectDto getliveProjectsV2(String zid, String userId, int broadcastId, String splatid,
            boolean cacheEnable, String mac, String deviceKey, CommonParam commonParam) {
        LiveProjectDto result = new LiveProjectDto();
        result.setSplatId(splatid);
        // 获取cms配置的直播专题数据
        result = this.getCMSConfig(result, zid, broadcastId, commonParam);
        // 获取静态直播数据
        result = this.mergeLiveProjects(result, broadcastId);
        // 获取动态权限数据
        result = this.mergePermissionProjectsV2(result, commonParam);
        // 排序
        // Collections.sort(result.getProjects());
        // 刷新冗余数据以及size
        // result.fresh();
        return result;
    }

    private void pro2Cache(Map<String, LiveProgram> programMap, int broadcastId, String splatid) {
        Map<String, LiveProgram> obj = new HashMap<String, LiveProgram>();
        if (programMap != null && programMap.size() > 0) {
            for (Entry<String, LiveProgram> entry : programMap.entrySet()) {
                obj.put(LiveUtil.buildLiveKey(entry.getKey(), broadcastId, splatid), entry.getValue());
            }

            this.facadeCacheDao.getLiveCacheDao().setLiveProgramsToCache2(obj);
        }
    }

    private LiveProject proSingleFromCache(String liveid, String splatId, CommonParam commonParam) {
        LiveProgram p = this.facadeCacheDao.getLiveCacheDao()
                .getLiveData(liveid, splatId, commonParam.getBroadcastId());
        LiveProject res = new LiveProject(commonParam);
        if (p != null) {
            res.setLiveInfo(p);
        }
        return res;
    }

    private Map<String, LiveProgram> prosFromCache(String liveKeys, int broadcastId, String splatid) {
        Map<String, LiveProgram> programMap = null;
        String[] ids = liveKeys.split(",");

        Map<String, LiveProgram> lives = this.facadeCacheDao.getLiveCacheDao().getLiveProgramByLiveIds(ids);
        if (!CollectionUtils.isEmpty(lives)) {
            programMap = new HashMap<String, LiveProgram>();
            for (Entry<String, LiveProgram> liveEntry : lives.entrySet()) {
                LiveProgram live = liveEntry.getValue();
                if (live != null) {
                    programMap.put(live.getId(), live);
                }
            }
        }
        // 如果所有的key都没有匹配到直播，那么我们默认为这是程序第一次加载，因为cms配置的所有直播都是可以搜索到的
        if (programMap == null || programMap.size() == 0) {
            programMap = this.getLivePrograms(100, "cn", "zh_cn", broadcastId, splatid, LiveConstants.LIVE_PORJECT);
            this.pro2Cache(programMap, broadcastId, splatid);
        }

        return programMap;
    }

    /**
     * 合并权限信息(V2)
     * @return
     */
    public LiveProjectDto mergePermissionProjectsV2(LiveProjectDto dto, CommonParam commonParam) {
        List<LiveProject> lps = dto.getProjects();
        /** 如果用户是登陆的，并且直播专题里面配置了付费的直播，那么，必须要进行鉴权 */
        if (dto.showPayIds() != null && dto.showPayIds().length() > 0) {
            StringBuilder logPrefix = new StringBuilder("mergePermissionProjectsV2_").append(commonParam.getMac())
                    .append("_").append(commonParam.getDeviceKey()).append("_").append(commonParam.getUserId())
                    .append("_").append(dto.showPayIds());
            Map<String, LivePackage> livePackages = this.facadeTpDao.getVipTpDaoV2().getLivePackage(dto.showPayIds(),
                    logPrefix.toString(), commonParam);
            if (livePackages != null && livePackages.size() > 0) {
                for (LiveProject l : lps) {
                    LivePackage livePackage = livePackages.get(l.getScreenings());
                    if (livePackage != null) {
                        l.setPrice(livePackage.getPrice() == null ? 0 : livePackage.getPrice());
                        l.setVipPrice((livePackage.getVipPriceSet() == null || null == livePackage.getVipPriceSet()
                                .getVipPrice()) ? 0 : livePackage.getVipPriceSet().getVipPrice());
                        l.setPayType(5); // TODO:
                                         // 1--包年以上会员免费，2--包年以上或单点免费，3--会员免费，4--会员或单点免费，5--单点
                        if (livePackage.getStatus() != null) { // TODO:
                                                               // v2接口和User不关联，无法鉴权，需要额外调用鉴权接口!!!
                            l.setPayStatus(livePackage.getStatus());
                            l.setTvPrice((livePackage.getVipPriceSet() == null || null == livePackage.getVipPriceSet()
                                    .getVipPrice()) ? 0 : livePackage.getVipPriceSet().getVipPrice());
                        }
                        l.setVipPayId(livePackage.getId());
                    } else {
                        l.setFree(true);
                    }
                }
            }
        }
        return dto;
    }

    /**
     * 合并权限信息
     * @return
     */
    public LiveProjectDto mergePermissionProjects(LiveProjectDto dto, String userId, String mac, String deviceKey) {
        List<LiveProject> lps = dto.getProjects();
        /** 如果用户是登陆的，并且直播专题里面配置了付费的直播，那么，必须要进行鉴权 */
        if (dto.showPayIds() != null && dto.showPayIds().length() > 0) {
            Map<String, PermissionInfo> perm = this.facadeTpDao.getLiveTpDao().getPermission(dto.showPayIds(), userId,
                    mac, deviceKey);
            if (perm != null && perm.size() > 0) {
                for (LiveProject l : lps) {
                    PermissionInfo p = perm.get(l.getScreenings());
                    if (p != null) {
                        l.setPrice(Double.parseDouble(p.getPrice() == null ? "0" : p.getPrice()));
                        l.setVipPrice(Double.parseDouble(p.getVipprice() == null ? "0" : p.getVipprice()));
                        l.setPayType(p.getPayType());
                        if (p.getStatus() != null) {
                            l.setPayStatus(Integer.parseInt(p.getStatus()));
                            l.setTvPrice(Double.parseDouble(p.getFinalprice() == null ? "0" : p.getFinalprice()));
                        }
                    }
                }
            }
        }
        return dto;
    }

    /**
     * 合并权限信息
     * @return
     */
    public LiveProjectDto mergePermissionByMac(LiveProjectDto dto, String mac) {
        List<LiveProject> lps = dto.getProjects();
        /** mac的用户鉴权是，无需登录，直接鉴权 */
        if (dto.showPayIds() != null && dto.showPayIds().length() > 0) {
            Map<String, PermissionInfo> perm = this.facadeTpDao.getLiveTpDao()
                    .getPermissionByMac(dto.showPayIds(), mac);
            if (perm != null && perm.size() > 0) {
                for (LiveProject l : lps) {
                    PermissionInfo p = perm.get(l.getScreenings());
                    if (p != null) {
                        l.setPrice(Double.parseDouble(p.getPrice() == null ? "0" : p.getPrice()));
                        l.setVipPrice(Double.parseDouble(p.getVipprice() == null ? "0" : p.getVipprice()));
                        l.setPayStatus(Integer.parseInt(p.getStatus() == null ? "0" : p.getStatus()));
                    }
                }
            }
        }
        return dto;
    }

    /**
     * 合并价格信息
     * @return
     */
    public LiveProjectDto mergePriceProjects(LiveProjectDto dto, String mac, String deviceKey) {
        /** 获取价格 ，只获取付费节目的价格 */
        List<LiveProject> lps = dto.getProjects();
        if (dto.showPayIds() != null && dto.showPayIds().length() > 0) {
            Map<String, PermissionInfo> perm = this.facadeTpDao.getLiveTpDao().getPriceInfo(dto.showPayIds(), mac,
                    deviceKey);
            if (perm != null && perm.size() > 0) {
                for (LiveProject l : lps) {
                    PermissionInfo p = perm.get(l.getScreenings());
                    if (p != null) {
                        l.setPrice(Double.parseDouble(p.getPrice()));
                        l.setVipPrice(Double.parseDouble(p.getVipprice()));
                    }
                }
            }
        }
        return dto;
    }

    public LiveProjectDto getCMSConfig(LiveProjectDto result, String zid, int broadcastId, CommonParam commonParam) {
        List<LiveProject> lps = result.getProjects();
        // 获取cms配置的直播专题
        LiveProjectResponseTp projects = this.facadeTpDao.getLiveTpDao().getLiveProjectsNew(zid, commonParam);

        if (projects != null && projects.getData().getTjPackage().getDataList() != null
                && projects.getData().getTjPackage().getDataList().size() > 0) {
            result.setTitle(projects.getData().getName());
            result.setBgImg(projects.getData().getTvPic());
            LiveProject templp = null;
            for (LiveProjectResponseTp.ProjectInfo projectInfo : projects.getData().getTjPackage().getDataList()) {
                if (projectInfo.getRid() != null) {
                    templp = new LiveProject(commonParam);
                    // 获取cms配置的直播专题
                    templp.setCMSLiveInfo(projectInfo);
                    lps.add(templp);
                    // 记录直播id
                    result.buildLiveKeys(projectInfo.getRid(), broadcastId);
                }
            }
        }
        return result;
    }

    /**
     * 获取直播专题的静态数据，包括cms配置数据，直播大厅数据，和价格数据
     * @return
     */
    public LiveProjectDto mergeLiveProjects(LiveProjectDto result, int broadcastId) {
        List<LiveProject> lps = new ArrayList<LiveProject>();
        if (result.getProjects() != null && result.getProjects().size() > 0) {
            // 获取直播大厅列表
            Map<String, LiveProgram> programMap = this.prosFromCache(result.showLiveKeys(), broadcastId,
                    result.getSplatId());
            for (LiveProject p : result.getProjects()) {
                LiveProgram program = programMap.get(p.getId());
                // 如果cms配置的直播专题，在直播大厅没有找到，我们不会再前台显示
                if (program == null) {
                    continue;
                } else {
                    // 获取直播大厅的数据
                    p.setLiveInfo(program);
                }
                lps.add(p);
                // 记录需要付费的场次
                result.buildPayIds(p.getScreenings());
            }
            result.setProjects(lps);
        }
        return result;
    }

    public Map<String, LiveProgram> getLivePrograms(int size, String wcode, String langCode, int broadcastId,
            String splatid, String module) {

        Map<String, LiveProgram> livePrograms = new HashMap<String, LiveProgram>();

        List<LiveRoomChannel> liveRooms = this.getLiveRoomChannelList();
        for (LiveRoomChannel liveRoom : liveRooms) {
            if (liveRoom != null && StringUtils.isNotEmpty(liveRoom.getEname()) && liveRoom.getCategoryId() != null) {
                livePrograms.putAll(this.getLiveProgramsByChannel(liveRoom, splatid, module, langCode, broadcastId));
            }
        }

        return livePrograms;
    }

    /**
     * 根据频道获取直播数据
     * @param splatid
     * @param module
     * @param langCode
     * @param broadcastId
     * @return
     */
    public Map<String, LiveProgram> getLiveProgramsByChannel(LiveRoomChannel liveRoom, String splatid, String module,
            String langCode, Integer broadcastId) {
        Map<String, LiveProgram> livePrograms = null;

        if (broadcastId == null) {
            broadcastId = 0;
        }
        if (liveRoom != null) {// 默认体育频道
            String beginDate = TimeUtil.getDateString(TimeUtil.getDateFromDate(new Date(), -3),
                    TimeUtil.SHORT_DATE_FORMAT_NO_DASH);// 3天以前
            livePrograms = new HashMap<String, LiveProgram>();
            LiveProjResponseTp tpLive = this.facadeTpDao.getLiveTpDao().getLivePrograms(liveRoom.getEname(), beginDate,
                    broadcastId, splatid);
            if (tpLive != null && !CollectionUtils.isEmpty(tpLive.getRows())) {
                if (LiveConstants.LIVE_LIST.equals(module)) {
                    for (LiveProjectTp liveResponseTp : tpLive.getRows()) {
                        if (!StringUtils.isEmpty(liveResponseTp.getScreenings())) {// 直播大厅需要过滤掉付费的
                            continue;
                        }
                        LiveProgram liveDto = this.parseLiveProgramFromTp(liveResponseTp, liveRoom, langCode,
                                broadcastId, splatid);
                        if (liveDto != null) {
                            livePrograms.put(liveDto.getId(), liveDto);
                        }
                    }

                    // 体育频道页内的直播列表更新缓存
                    if (StringUtils.equals(LiveTpConstants.LIVE_ROOM_CHANNEL_SPORT, liveRoom.getEname())) {// 体育直播更新缓存
                        if (!CollectionUtils.isEmpty(livePrograms)) {// 直播数据不为空，缓存数据30天
                            Set<LiveProgram> sportPrograms = new TreeSet<LiveProgram>(livePrograms.values());
                            List<LiveProgram> liveList = new ArrayList<LiveProgram>();
                            liveList.addAll(sportPrograms);
                            this.facadeCacheDao.getLiveCacheDao().setSportsLivePrograms(broadcastId, liveList);
                            LOG.info("getLivePrograms LiveService : set cache[" + broadcastId + ": " + liveList.size()
                                    + "]");
                        } else {// 数据为空，则删除缓存
                            this.facadeCacheDao.getLiveCacheDao().deleteSportsLivePrograms(broadcastId);
                            LOG.info("getLivePrograms LiveService : delete cache[: " + broadcastId + "]");
                        }
                    }
                } else {// 非直播大厅就不需要过滤
                    for (LiveProjectTp liveResponseTp : tpLive.getRows()) {
                        LiveProgram liveDto = this.parseLiveProgramFromTp(liveResponseTp, liveRoom, langCode,
                                broadcastId, splatid);
                        if (liveDto != null) {
                            livePrograms.put(liveDto.getId(), liveDto);
                        }
                    }
                }
            } else if (StringUtils.equals(LiveTpConstants.LIVE_ROOM_CHANNEL_SPORT, liveRoom.getEname())) {
                // 删除体育的缓存数据
                this.facadeCacheDao.getLiveCacheDao().deleteSportsLivePrograms(broadcastId);
                LOG.info("getLivePrograms LiveService 2: delete cache[: " + broadcastId + "]");
            }
        }

        return livePrograms;
    }

    /**
     * @param id
     * @param model
     * @param sourceType
     * @param imei
     *            投屏播放业务中投屏端imei
     * @param commonParam
     * @return
     */
    public Response<LiveProject> getLiveById(String id, Integer model, Integer sourceType, Long timestamp, String sig,
            String imei, String streamCode, CommonParam commonParam) {
        String logPrefix = "getLiveById_" + id;
        Response<LiveProject> response = new Response<LiveProject>();
        LiveProject res = null;

        Integer broadcastId = commonParam.getBroadcastId();
        if (broadcastId == null) {
            broadcastId = CommonConstants.LETV;
            commonParam.setBroadcastId(broadcastId);
        }
        String errorCode = null;
        if (model != null && VideoConstants.PLAY_MODEL_TOUPING == model) {
            String splatid = LiveTpConstants.LIVE_SPLITID_TOUPING;
            if (StringUtils.isEmpty(id) || timestamp == null || StringUtils.isEmpty(sig)) {
                errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
                LOG.error(logPrefix + "_" + timestamp + "_" + sig + ": CSC003");
            } else if (!this.checkLiveChannelSignature(id, timestamp, sig)) {
                errorCode = ErrorCodeConstants.LIVE_CHANNEL_SIG_ILLEGAL;
            } else {
                logPrefix = logPrefix + "_" + sourceType + "_" + imei + "_" + commonParam.getUserId();
                // 直播投屏
                if (sourceType != null) {
                    String cacheKey = LiveUtil.buildToupingLiveChannelKey(id, sourceType, splatid);
                    res = this.cacheTemplate.get(cacheKey, LiveProject.class);
                    if (res == null) {
                        // 缓存未命中就刷第三方实时数据
                        LiveProgram liveProgram = null; // 从直播第三放接口解析出来的直播数据
                        switch (sourceType.intValue()) {
                        case VideoConstants.PLAY_TOUPING_SOURCE_TYPE_LIVE:
                            // 普通直播，这里也不走TV版权缓存，而是仅走投屏缓存(10min)
                            LiveProjResponseTp liveResponse = this.facadeTpDao.getLiveTpDao().getMultiLivePrograms(
                                    splatid, id);
                            if (liveResponse != null && liveResponse.getRows() != null
                                    && liveResponse.getRows().size() > 0) {
                                LiveProjectTp liveTp = liveResponse.getRows().get(0);

                                // 版权校验
                                String liveSplaitids = liveTp == null ? null : liveTp.getSplatid();
                                if (isSplatidHasCopyright(liveSplaitids, splatid)) {
                                    liveProgram = this.parseLiveProgramFromTp(liveTp, null, commonParam.getLangcode(),
                                            commonParam.getBroadcastId(), LiveTpConstants.LIVE_SPLITID_TOUPING);
                                } else {
                                    errorCode = ErrorCodeConstants.LIVE_CHANNEL_PROGRAM_NO_COPYRIGHT;
                                }
                            } else {
                                errorCode = ErrorCodeConstants.LIVE_CHANNEL_PROGRAM_NULL;
                            }
                            break;
                        case VideoConstants.PLAY_TOUPING_SOURCE_TYPE_LUNBO:
                        case VideoConstants.PLAY_TOUPING_SOURCE_TYPE_WEISHI:
                            // 轮播或卫视
                            LiveSingleDataCommonTpResponse<LiveChannelTpResponse> liveChannelTpResponse = this.facadeTpDao
                                    .getLiveTpDao().getLiveChannel(id, splatid, "100");
                            if (liveChannelTpResponse != null && liveChannelTpResponse.getData() != null) {
                                if (isSplatidHasCopyright(liveChannelTpResponse.getData().getSplatid(), splatid)) {
                                    liveProgram = this.parseLiveProgramFromLiveChannelTp(
                                            liveChannelTpResponse.getData(), LiveTpConstants.LIVE_SPLITID_TOUPING,
                                            commonParam.getBroadcastId(), commonParam.getLangcode());
                                } else {
                                    errorCode = ErrorCodeConstants.LIVE_CHANNEL_PROGRAM_NO_COPYRIGHT;
                                }
                            } else {
                                errorCode = ErrorCodeConstants.LIVE_CHANNEL_PROGRAM_NULL;
                            }
                            break;
                        default:
                            errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
                            break;
                        }

                        if (liveProgram != null) {
                            res = new LiveProject(commonParam);
                            res.setLiveInfo(liveProgram);

                            this.cacheTemplate.set(cacheKey, res, CommonConstants.SECONDS_OF_10_MINUTE);
                            LOG.error(logPrefix + ": get data from tp to fill cache");
                        } else if (errorCode == null) {
                            errorCode = ErrorCodeConstants.LIVE_CHANNEL_PROGRAM_NULL;
                            LOG.error(logPrefix + ": no data from cache and tp");
                        }
                    }
                } else {
                    errorCode = ErrorCodeConstants.COMMON_ILLEGAL_PARAMETER;
                }
            }

            if (errorCode != null) {
                ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errorCode, commonParam.getLangcode());
            } else {
                // 投屏直播不选码流，去掉码流列表
                List<TvStreamInfoDto> tvStreamInfo = res.getTvStreamInfo(); // 拿出来的流已经是按照分辨率从高到低排好序的
                TvStreamInfoDto finalStream = null;
                if (!CollectionUtils.isEmpty(tvStreamInfo)) {
                    // 码流码率-url map，key--码率，如720p等，value--对应码率的播放地址
                    Map<String, TvStreamInfoDto> streamCodeUrlMap = new HashMap<String, TvStreamInfoDto>();
                    for (TvStreamInfoDto stream : tvStreamInfo) {
                        streamCodeUrlMap.put(stream.getCode(), stream);
                    }
                    String[] filtedStreamCodes = LetvStreamCommonConstants.LIVE_TOUPING_STREAM_FILTERS.split("#");

                    for (String filtedStreamCode : filtedStreamCodes) {
                        finalStream = streamCodeUrlMap.get(filtedStreamCode);
                        if (finalStream != null) {
                            break;
                        }
                    }
                    if (finalStream == null) {
                        finalStream = tvStreamInfo.get(0);
                    }

                    // 直播鉴权
                    if (sourceType != null && VideoConstants.PLAY_TOUPING_SOURCE_TYPE_LIVE == sourceType
                            && StringUtils.isNotEmpty(res.getScreenings())
                            && StringUtils.isNotEmpty(commonParam.getUserId())) {
                        // 普通直播设置了付费信息
                        commonParam.setMac(imei); // 已手机信息结合用户信息去鉴权
                        Response<CheckLiveDto> checkLiveResponse = vipMetadataService
                                .checkLive(id, res.getScreenings(), finalStream.getStreamId(), 0, "4",
                                        res.getSelectId(), null, commonParam);
                        if (checkLiveResponse != null && checkLiveResponse.getData() != null) {
                            CheckLiveDto checkLive = checkLiveResponse.getData();
                            StringBuilder liveUrlBuilder = new StringBuilder(finalStream.getLiveUrl());
                            liveUrlBuilder.append("&token=").append(StringUtils.trimToEmpty(checkLive.getToken()))
                                    .append("&uinfo=").append(StringUtils.trimToEmpty(checkLive.getUinfo()))
                                    .append("&uid=").append(StringUtils.trimToEmpty(commonParam.getUserId()));
                            finalStream.setLiveUrl(liveUrlBuilder.toString());
                        }
                    }
                }
                if (finalStream != null) {
                    List<TvStreamInfoDto> newTvStreamInfo = new ArrayList<TvStreamInfoDto>();
                    newTvStreamInfo.add(finalStream);
                    res.setTvStreamInfo(newTvStreamInfo);
                    res.setPlayUrl(finalStream.getLiveUrl());
                    res.setDefaultStreamCode(finalStream.getCode());
                }

                response.setData(res);
            }
        } else {
            // TV版播放
            res = this.proSingleFromCache(id, LiveTpConstants.LIVE_SPLITID_TV_LIVE_ROOM, commonParam);
            if (res != null) {
                if (!StringUtils.isEmpty(res.getScreenings())) {
                    Map<String, PermissionInfo> perm = this.facadeTpDao.getLiveTpDao().getPermission(
                            res.getScreenings(), commonParam.getUserId(), commonParam.getMac(),
                            commonParam.getDeviceKey());
                    if (perm != null) {
                        PermissionInfo p = perm.get(res.getScreenings());
                        if (p != null) {
                            res.setPrice(Double.parseDouble(p.getPrice() == null ? "0" : p.getPrice()));
                            res.setVipPrice(Double.parseDouble(p.getVipprice() == null ? "0" : p.getVipprice()));
                            res.setPayType(p.getPayType());
                            if (p.getStatus() != null) {
                                res.setPayStatus(Integer.parseInt(p.getStatus()));
                                res.setTvPrice(Double.parseDouble(p.getFinalprice() == null ? "0" : p.getFinalprice()));
                            }
                        }
                    }
                } else {
                    res.setFree(true);
                }
                if (StringUtil.isNotBlank(streamCode) && res.getTvStreamInfo() != null
                        && res.getTvStreamInfo().size() > 0) {
                    for (TvStreamInfoDto info : res.getTvStreamInfo()) {
                        if (streamCode.equalsIgnoreCase(info.getCode())) {
                            res.setPlayUrl(info.getLiveUrl());
                            res.setDefaultStreamCode(streamCode);
                            break;
                        }
                    }
                }
                response.setData(res);
            } else {
                errorCode = ErrorCodeConstants.LIVE_CHANNEL_PROGRAM_NULL;
                ErrorCodeCommonUtil.setErrorResponse(response, errorCode, errorCode, commonParam.getLangcode());
            }
        }

        return response;
    }

    public LiveSDKDto getCmsLiveList(String cmsPageId, String mac, Integer broadcastId, String splatid, boolean b) {
        LiveSDKDto result = new LiveSDKDto();
        result.setSplatId(splatid);
        // 获取cms配置的直播专题数据
        result = this.getCMSPageLiveConfig(result, cmsPageId, broadcastId);
        // 获取静态直播数据
        result = this.mergeSDKProjects(result, broadcastId);
        // 获取动态权限数据
        result = this.mergePermissionByMac(result, mac);
        return result;
    }

    private LiveSDKDto getCMSPageLiveConfig(LiveSDKDto result, String cmsPageId, int broadcastId) {
        List<LiveSDK> lps = result.getProjects();
        List<CmsBlockContentTpResponse> blockContents = null;
        CommonParam commonParam = new CommonParam();
        commonParam.setLangcode(LocaleConstant.Langcode.ZH_CN);
        CmsPageTpResponse cmsPageTpResponse = this.facadeTpDao.getCmsTpDao().getCmsPage(cmsPageId, commonParam);
        if (cmsPageTpResponse != null) {
            if (cmsPageTpResponse.getData() != null) {
                if ((cmsPageTpResponse.getData().getFrags() != null)
                        && (cmsPageTpResponse.getData().getFrags().size() > 0)) {
                    // frag0位置一定会出现
                    CmsPageTpResponseFrag frag = cmsPageTpResponse.getData().getFrags().get(0);
                    if (frag != null) {
                        blockContents = frag.getBlockContents();
                    }

                }

                // 如果frag1位置不为空，聚合页背景图一定存在在该节点
                if ((cmsPageTpResponse.getData().getFrags() != null)
                        && (cmsPageTpResponse.getData().getFrags().size() > 1)) {
                    // frag0位置一定会出现
                    CmsPageTpResponseFrag imgfrag = cmsPageTpResponse.getData().getFrags().get(1);
                    if (imgfrag != null) {
                        List<CmsBlockContentTpResponse> imgBlock = imgfrag.getBlockContents();
                        if (imgBlock != null && imgBlock.size() > 0 && imgBlock.get(0) != null
                                && imgBlock.get(0).getPic1() != null) {
                            result.setBgImg(imgBlock.get(0).getPic1());
                        }
                    }

                }
            }
        }
        if (blockContents != null) {
            LiveSDK templp = null;
            for (CmsBlockContentTpResponse blockContent : blockContents) {
                if (blockContent.getContent() != null) {
                    templp = new LiveSDK(blockContent.getContent(), blockContent.getTitle(),
                            blockContent.getSubTitle(), blockContent.getPic2(), blockContent.getPic1());
                    lps.add(templp);
                    // 记录直播id
                    result.buildLiveKeys(blockContent.getContent(), broadcastId);
                }
            }
        }
        return result;
    }

    /**
     * 从直播大厅第三方接口返回的单条数据liveResponseTp中解析出直播信息
     * @param liveResponseTp
     * @param langCode
     * @return
     */
    private LiveProgram parseLiveProgramFromTp(LiveProjectTp liveResponseTp, LiveRoomChannel liveRoom, String langCode,
            Integer broadcastId, String splatid) {
        LiveProgram liveProgram = null;

        if (liveResponseTp != null) {

            liveProgram = new LiveProgram();
            String liveId = liveResponseTp.getId();
            liveProgram.setId(liveId);

            liveProgram.setState(liveResponseTp.getStatus() == 3 ? 4 : liveResponseTp.getStatus());
            liveProgram.setStartTime(TimeUtil.string2timestamp(liveResponseTp.getBeginTime()).toString());
            liveProgram.setEndTime(TimeUtil.string2timestamp(liveResponseTp.getEndTime()).toString());
            liveProgram.setStateName(MessageUtils.getMessage(LiveConstants.STATE_MAP.get(liveProgram.getState()),
                    langCode));

            liveProgram.setDuration(getComplextDuration(liveResponseTp.getBeginTime(), liveResponseTp.getEndTime()));
            liveProgram.setLiveContentDesc(liveResponseTp.getDescription());
            liveProgram.setVid(liveResponseTp.getVid() != null ? liveResponseTp.getVid().toString() : null);
            liveProgram.setAblumId(liveResponseTp.getPid() != null ? liveResponseTp.getPid().toString() : null);
            liveProgram.setRecordingId(liveResponseTp.getRecordingId());
            liveProgram.setViewPic(liveResponseTp.getViewPic());
            // liveProgram.setChatRoomNum(liveResponseTp.getChatRoomNum());
            if (liveRoom != null) {
                liveProgram.setEname(liveRoom.getEname());
                liveProgram.setCid(String.valueOf(liveRoom.getCategoryId()));
            }
            liveProgram.setCh(liveResponseTp.getCh());
            liveProgram.setDate(getLiveDate(liveResponseTp.getBeginTime(), langCode));
            liveProgram.setType(liveResponseTp.getLevel2());
            liveProgram.setSelectId(liveResponseTp.getSelectId());
            liveProgram.setTvStreamInfo(this.tp2StreamInfo(liveId, liveResponseTp.getSelectId(), langCode, broadcastId,
                    splatid));
            liveProgram.setPreVideoId(liveResponseTp.getPreVID());
            liveProgram.setBackgroundImgUrl(StringUtils.isEmpty(liveResponseTp.getTvBackgroudPic()) ? liveResponseTp
                    .getViewPic() : liveResponseTp.getTvBackgroudPic());
            liveProgram.setScreenings(liveResponseTp.getScreenings());
            liveProgram.setMusicV2Screenings(liveResponseTp.getMusicV2Screenings());
            liveProgram.setIsPay(liveResponseTp.getIsPay());
            if (!CollectionUtils.isEmpty(liveProgram.getTvStreamInfo())) {
                liveProgram.setPlayUrl(liveProgram.getTvStreamInfo().get(0).getLiveUrl());
                String defaultStreamCode = liveProgram.getTvStreamInfo().get(0).getCode();
                liveProgram.setvType(defaultStreamCode);
                liveProgram.setDefaultStreamCode(defaultStreamCode);
            }

            if (LiveTpConstants.CHATROOM_SWITCH != null && LiveTpConstants.CHATROOM_SWITCH
                    && liveResponseTp.getIsChat() != null && liveResponseTp.getIsChat() == 1) {
                // 直播后台设置了开启聊天室，才返回聊天室信息
                liveProgram.setChatRoomNum(liveResponseTp.getChatRoomNum());
                if (StringUtils.isNotEmpty(liveResponseTp.getChatRoomNum()) && liveResponseTp.getIsDanmaku() != null) {
                    liveProgram.setIsDanmaku(liveResponseTp.getIsDanmaku());
                } else {
                    // 无数据则设置不支持弹幕
                    liveProgram.setIsDanmaku(0);
                }
            } else {
                liveProgram.setIsDanmaku(0);
            }
            liveProgram.setRecordingId(liveResponseTp.getRecordingId());
            if (StringUtil.isNotBlank(liveResponseTp.getPlayBackStartTime())
                    && liveResponseTp.getPlayBackStartTime().equals(liveResponseTp.getPlayBackEndTime())) {
                // 回看开始时间等于回看结束时间，则展示"暂不支持回看"
                liveProgram.setReplayText(MessageUtils.getMessage(LiveTpConstants.LIVE_REPLAY_UNSUPPORT, langCode));
            } else {// 还是以前的逻辑
                String replayStartTime = StringUtils.isNotEmpty(liveResponseTp.getPlayBackStartTime()) ? liveResponseTp
                        .getPlayBackStartTime() : liveResponseTp.getEndTime();
                Calendar playCalendar = null;
                if (StringUtils.isNotEmpty(replayStartTime)) {
                    // 如果直播后台配置了直播回看开始时间，则以配置为准
                    playCalendar = TimeUtil.parseCalendar(replayStartTime, TimeUtil.SIMPLE_DATE_FORMAT);
                    if (playCalendar != null) {
                        liveProgram.setReplayTime(String.valueOf(playCalendar.getTimeInMillis()));
                    }
                }

                Calendar playEndCalendar = null;
                if (StringUtils.isNotEmpty(liveResponseTp.getPlayBackEndTime())) {
                    playEndCalendar = TimeUtil.parseCalendar(liveResponseTp.getPlayBackEndTime(),
                            TimeUtil.SIMPLE_DATE_FORMAT);
                    liveProgram.setReplayEndTime(String.valueOf(playEndCalendar.getTimeInMillis())); // 运算有位溢出风险，暂不处理
                }

                // If replay time is over the year,should use other format
                String format = TimeUtil.DATE_MM_DD_FORMAT1;
                if (playEndCalendar != null) {
                    int endYear = playEndCalendar.get(Calendar.YEAR);
                    int beginYear = 0;
                    int nowYear = 0;
                    Calendar now = Calendar.getInstance();
                    nowYear = now.get(Calendar.YEAR);
                    if (playCalendar != null) {
                        beginYear = playCalendar.get(Calendar.YEAR);
                    } else {
                        beginYear = nowYear;
                    }
                    if (endYear > beginYear || beginYear != nowYear) {
                        // 结束时间和开始时间跨年或者开始时间年份不等于当前年份，也要展示年份
                        format = TimeUtil.SIMPLE_DATE_HOUR_MINUTE_FORMAT_FOR_LIVE;
                    }
                }

                // 设置回看文案，有回看开始和结束时间，展示“{回看开始}--{结束时间}”；无回看开始时间但有回看结束时间，展示“截止至{结束时间}”，无回看结束时间，展示“当前可回看”
                if (playEndCalendar == null) {
                    // 无回看结束时间，展示“当前可回看”
                    liveProgram.setReplayText(MessageUtils
                            .getMessage(LiveTpConstants.LIVE_REPLAY_NO_END_TIME, langCode));
                } else if (playCalendar == null) {
                    // 无回看开始时间但有回看结束时间，展示“截止至{结束时间}”
                    liveProgram.setReplayText(MessageUtils.getMessage(LiveTpConstants.LIVE_REPLAY_NO_START_END_TIME,
                            langCode, TimeUtil.getDateString(playEndCalendar, format)));
                } else {
                    // 有回看开始和结束时间，展示“{回看开始}--{结束时间}”
                    liveProgram.setReplayText(MessageUtils.getMessage(LiveTpConstants.LIVE_REPLAY_START_END_TIME,
                            langCode, TimeUtil.getDateString(playCalendar, format),
                            TimeUtil.getDateString(playEndCalendar, format)));
                }
            }

            if (liveRoom != null && StringUtils.equals(LiveTpConstants.LIVE_ROOM_CHANNEL_SPORT, liveRoom.getEname())) {
                liveProgram.setScore(getScore(liveResponseTp.getHomescore(), liveResponseTp.getGuestscore()));
                if (liveResponseTp.getIsVS() != null
                        && LiveConstants.LIVE_SPORT_NAME_SHOW_STYLE_1 == liveResponseTp.getIsVS()) {
                    liveProgram.setLiveName(getPk(liveResponseTp.getHome(), liveResponseTp.getGuest(),
                            liveProgram.getScore()));
                    liveProgram
                            .setPk(getPk(liveResponseTp.getHome(), liveResponseTp.getGuest(), liveProgram.getScore()));
                } else {
                    liveProgram.setLiveName(liveResponseTp.getTitle());
                    liveProgram.setPk(liveResponseTp.getTitle());
                }
                liveProgram.setMatchStage(getMatchStage(liveResponseTp.getLevel2(), liveResponseTp.getMatch()));
                liveProgram.setSportsType(liveResponseTp.getLevel1());
                liveProgram.setSportsSubType(liveResponseTp.getLevel2());
                liveProgram.setSeason(liveResponseTp.getSeason());
                liveProgram.setHome(liveResponseTp.getHome());
                liveProgram.setGuest(liveResponseTp.getGuest());
            } else {
                liveProgram.setLiveName(liveResponseTp.getTitle());
                liveProgram.setType(liveResponseTp.getLevel2());
            }
        }

        return liveProgram;
    }

    /**
     * 获取直播大厅（单条或若干条）实时数据刷新时间间隔，当前策略为从缓存中获取，为null时使用默认值defaultVal
     * @param defaultVal
     * @return
     */
    private Long getLiveListRealTimeFreshInterval(Long defaultVal) {
        Long interval = this.facadeCacheDao.getLiveCacheDao().getLiveRoomRefreshTime();
        if (interval == null) {
            interval = defaultVal;
        }

        return interval;
    }

    /**
     * 获取直播Sdk的静态数据，包括cms配置数据，直播大厅数据，和价格数据
     * @return
     */
    public LiveSDKDto mergeSDKProjects(LiveSDKDto result, int broadcastId) {
        List<LiveSDK> lps = new ArrayList<LiveSDK>();
        if (result.getProjects() != null && result.getProjects().size() > 0) {
            Map<String, LiveProgram> programMap = this.prosFromCache(result.showLiveKeys(), broadcastId,
                    result.getSplatId());
            for (LiveSDK s : result.getProjects()) {
                LiveProgram program = programMap.get(s.getId());
                // 如果cms配置的直播专题，在直播大厅没有找到，我们不会再前台显示
                if (program == null) {
                    continue;
                }
                // 获取直播大厅的数据
                s.setLiveInfo(program);
                lps.add(s);
                // 记录需要付费的场次
                result.buildPayIds(s.getScreenings());
            }

            result.setProjects(lps);
        }
        return result;
    }

    /**
     * 合并权限信息
     * @return
     */
    public LiveSDKDto mergePermissionByMac(LiveSDKDto dto, String mac) {
        List<LiveSDK> lps = dto.getProjects();
        /** mac的用户鉴权是，无需登录，直接鉴权 */
        if (dto.showPayIds() != null && dto.showPayIds().length() > 0) {
            Map<String, PermissionInfo> perm = this.facadeTpDao.getLiveTpDao()
                    .getPermissionByMac(dto.showPayIds(), mac);
            if (perm != null && perm.size() > 0) {
                for (LiveSDK l : lps) {
                    PermissionInfo p = perm.get(l.getScreenings());
                    if (p != null) {
                        l.setPrice(Double.parseDouble(p.getPrice() == null ? "0" : p.getPrice()));
                        l.setVipPrice(Double.parseDouble(p.getVipprice() == null ? "0" : p.getVipprice()));
                        l.setPayStatus(Integer.parseInt(p.getStatus() == null ? "0" : p.getStatus()));
                    }
                }
            }
        }
        return dto;
    }

    /**
     * 投屏直播签名校验；
     * 签名规则sig = md5{liveId={liveId}&timestamp={timestamp}{secrectKey}}，和点播签名类似
     * @param liveId
     * @param timestamp
     * @param sig
     * @return
     */
    private boolean checkLiveChannelSignature(String liveId, Long timestamp, String sig) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("liveId", liveId);

        params.put("timestamp", timestamp);
        params.put("sig", sig);
        return CommonUtil.checkSig(params);
    }

    /**
     * 从直播频道中解析数据，暂使用LiveProgram封装，2016-01-13投屏需求
     * @param liveChannelTpResponse
     * @param splatid
     * @param broadcastId
     * @param langCode
     * @return
     */
    private LiveProgram parseLiveProgramFromLiveChannelTp(LiveChannelTpResponse liveChannelTpResponse, String splatid,
            Integer broadcastId, String langCode) {
        LiveProgram liveProgram = null;
        if (liveChannelTpResponse != null) {
            liveProgram = new LiveProgram();
            String liveId = liveChannelTpResponse.getChannelId();
            liveProgram.setId(liveId);
            liveProgram.setState(LiveConstants.LIVE);
            liveProgram.setStateName(MessageUtils.getMessage(LiveConstants.STATE_MAP.get(liveProgram.getState()),
                    langCode));

            // 2016-02-02，投屏业务遇到客户端30分钟切节目bug，服务端兼容，修改直播状态为直播中，修改直播结束时间距当前时间1个月
            liveProgram.setStartTime(String.valueOf(System.currentTimeMillis()));
            liveProgram.setEndTime(String.valueOf(System.currentTimeMillis() + 60 * 60 * 24 * 30 * 1000L));

            liveProgram.setLiveContentDesc(liveChannelTpResponse.getChannelDesc());

            if (liveChannelTpResponse.getDefaultLogo() != null) {
                liveProgram.setViewPic(liveChannelTpResponse.getDefaultLogo().get(liveChannelTpResponse.pic1_746_419));
                if (StringUtils.trimToNull(liveProgram.getViewPic()) == null) {
                    liveProgram.setViewPic(liveChannelTpResponse.getDefaultLogo().get(
                            liveChannelTpResponse.pic2_960_540));
                }
            } else {
                liveProgram.setViewPic(liveChannelTpResponse.getPostOrigin());
            }

            liveProgram.setChatRoomNum(liveChannelTpResponse.getChatRoomNum());
            liveProgram.setEname(liveChannelTpResponse.getChannelEname());
            liveProgram.setCh(liveChannelTpResponse.getCh());
            if (liveChannelTpResponse.getBeginTime() != null) {
                liveProgram.setDate(getLiveDate(liveChannelTpResponse.getBeginTime(), langCode));
            }
            liveProgram.setTvStreamInfo(this.parseAndSortLiveStreams(liveChannelTpResponse.getStreams(), splatid,
                    liveId, broadcastId, langCode));
            liveProgram.setIsPay(0);
            if (!CollectionUtils.isEmpty(liveProgram.getTvStreamInfo())) {
                liveProgram.setPlayUrl(liveProgram.getTvStreamInfo().get(0).getLiveUrl());
                String defaultStreamCode = liveProgram.getTvStreamInfo().get(0).getCode();
                liveProgram.setvType(defaultStreamCode);
                liveProgram.setDefaultStreamCode(defaultStreamCode);
            }
            liveProgram.setLiveName(liveChannelTpResponse.getChannelName());
        }
        return liveProgram;
    }

    /**
     * 从直播部门接口返回的流列表中解析数据
     * @param streams
     * @param splatid
     * @param liveId
     * @param broadcastId
     * @param langCode
     * @return
     */
    private List<TvStreamInfoDto> parseAndSortLiveStreams(List<TvStreamInfo> streams, String splatid, String liveId,
            Integer broadcastId, String langCode) {
        List<TvStreamInfoDto> sortedStreams = new ArrayList<TvStreamInfoDto>();

        if (!CollectionUtils.isEmpty(streams)) {
            for (TvStreamInfo tvStream : streams) {
                TvStreamInfoDto tvdto = new TvStreamInfoDto();
                String sCode = tvStream.getRateType().replace("flv_", "");

                // 点播码流1000转800后，直播仍使用1000码流，客户端使用800作为点播和直播的全局码流设置，会导致800默认起播匹配码流失败，这里服务端先兼容
                if (LetvStreamCommonConstants.CODE_NAME_1000.equals(sCode)) {
                    sCode = LetvStreamCommonConstants.CODE_NAME_800;
                }

                tvdto.setCode(sCode);
                tvdto.setLiveUrl(
                        DomainMapping.changeDomainByBraodcastId(tvStream.getStreamUrl(), broadcastId, "media_cibn"),
                        splatid, liveId);
                tvdto.setStreamId(tvStream.getStreamId());
                tvdto.setvType(tvStream.getRateType());
                if (StringUtils.isNotEmpty(MessageUtils.getMessage(
                        LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(sCode), langCode))) {
                    tvdto.setStreamName(MessageUtils.getMessage(
                            LetvStreamCommonConstants.STREAM_CODE_NAME_MAP.get(sCode), langCode));
                } else {
                    tvdto.setStreamName(LetvStreamCommonConstants.STREAM_CODE_FILTER_MAP.get(sCode));
                }

                sortedStreams.add(tvdto);
            }
            Collections.sort(sortedStreams);
        }

        return sortedStreams;
    }

    /**
     * 获取可用的直播大厅类型数据
     * @return
     */
    private List<LiveRoomChannel> getLiveRoomChannelList() {
        List<LiveRoomChannel> liveRoomChannelList = this.facadeCacheDao.getLiveCacheDao().getLiveRoomChannelList();

        if (liveRoomChannelList == null) {
            // 缓存未命中则读取配置，并更新缓存；数据格式为LiveRoomChannel.ename:categoryId;LiveRoomChannel.ename:categoryId;...;LiveRoomChannel.ename:categoryId
            String liveRoomChannelConfig = this.facadeTpDao.getStaticTpDao().getIptvStaticFileContentByUrl(
                    IptvStaticConstant.LIVE_ROOM_CHANNEL_CONTENT_URL);
            if (StringUtils.isNotBlank(liveRoomChannelConfig)) {
                String[] liveRoomChannelArray = liveRoomChannelConfig.split(";");
                if (ArrayUtils.isNotEmpty(liveRoomChannelArray)) {
                    liveRoomChannelList = new ArrayList<LiveRoomChannel>();
                    for (String liveRoomChannelEntry : liveRoomChannelArray) {
                        if (StringUtils.isNotBlank(liveRoomChannelEntry)) {
                            String[] liveRoomChannelKVs = liveRoomChannelEntry.split(":");
                            if (liveRoomChannelKVs != null && liveRoomChannelKVs.length == 2
                                    && StringUtils.isNotBlank(liveRoomChannelKVs[0])
                                    && StringUtil.toInteger(liveRoomChannelKVs[1]) != null) {
                                liveRoomChannelList.add(new LiveRoomChannel(liveRoomChannelKVs[0], StringUtil
                                        .toInteger(liveRoomChannelKVs[1])));
                            }
                        }
                    }
                    if (liveRoomChannelList.size() > 0) {
                        this.facadeCacheDao.getLiveCacheDao().setLiveRoomChannelList(liveRoomChannelList);
                    }
                }
            }
        }
        return liveRoomChannelList;
    }

    /**
     * 对外提供更新接口直播大厅过滤数据的配置规则
     * @param liveRoomFilterConfig
     * @return
     */
    public static boolean updateLiveRoomFilterConfig(Map<String, String> liveRoomFilterConfig, String logPrefix) {
        if (liveRoomFilterConfig == null) {
            LOG.info(logPrefix + "config null");
            return false;
        } else {
            LiveConstants.LIVE_ROOM_FILTER_DEVICE_LIVE_MAP.clear();
            LiveConstants.LIVE_ROOM_FILTER_DEVICE_LIVE_MAP.putAll(liveRoomFilterConfig);
            LOG.info(logPrefix + "config size " + liveRoomFilterConfig.size());

            return true;
        }
    }

    /**
     * 判断直播（或轮播、卫视，以targetSplatid表示）是否有版权（是否包含在splatids中）
     * @param splatids
     * @param targetSplatid
     * @return
     */
    private boolean isSplatidHasCopyright(String splatids, String targetSplatid) {
        boolean result = false;
        if (StringUtils.isNotEmpty(splatids) && StringUtils.isNotEmpty(targetSplatid)) {
            String[] splatidArray = StringUtils.split(splatids, ",");
            if (splatidArray != null) {
                result = Arrays.asList(splatidArray).contains(targetSplatid);
            }
        }
        return result;
    }
}
