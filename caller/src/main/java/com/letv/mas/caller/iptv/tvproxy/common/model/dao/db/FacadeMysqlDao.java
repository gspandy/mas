package com.letv.mas.caller.iptv.tvproxy.common.model.dao.db;

import javax.annotation.Resource;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MysqlDao门面类
 */
@Component(value = "v2.FacadeMysqlDao")
public class FacadeMysqlDao {

    @Resource
    private ChannelDataMysqlDao channelDataMysqlDao;

    @Resource
    private ChannelDataExtendMysqlDao channelDataExtendMysqlDao;

    @Resource
    private ChannelMysqlDao channelMysqlDao;

    @Resource
    private DeskConfigMysqlDao deskConfigMysqlDao;

    @Resource(name = "v2.ContainerConfMysqlDao")
    private ContainerConfMysqlDao containerConfMysqlDao;

    @Resource(name = "v2.SearchConditionMysqlDao")
    private SearchConditionMysqlDao searchConditionMysqlDao;

    @Resource(name = "v2.VideoMysqlDao")
    private VideoMysqlDao videoMysqlDao;

    @Resource(name = "v2.AlbumMysqlDao")
    private AlbumMysqlDao albumMysqlDao;

    @Resource(name = "v2.ChannelDataRecommendationBlockMysqlDao")
    private ChannelDataRecommendationBlockMysqlDao channelDataRecommendationBlockMysqlDao;

    @Resource(name = "v2.ChannelCmsChannelMysqlDao")
    private ChannelCmsChannelMysqlDao channelCmsChannelMysqlDao;
    @Resource(name = "v2.MusicNavMysqlDao")
    private MusicNavMysqlDao musicNavMysqlDao;

    @Resource(name = "v2.LanguageDictionaryMysqlDao")
    private LanguageDictionaryMysqlDao languageDictionaryMysqlDao;

    @Resource(name = "v2.TpTerminalMysqlDao")
    private TpTerminalMysqlDao tpTerminalMysqlDao;

    @Resource(name = "v2.TpVersionMysqlDao")
    private TpVersionMysqlDao tpVersionMysqlDao;

    @Resource(name = "v2.QrCodeMysqlDao")
    private QrCodeMysqlDao qrCodeMysqlDao;
    @Resource(name = "v2.StatRankWeekDataDao")
    private StatRankWeekDataDao statRankWeekDataDao;
    @Resource(name = "v2.MmsHotAlbumMonitorMysqlDao")
    private MmsHotAlbumMonitorMysqlDao mmsHotAlbumMonitorMysqlDao;

    /**
     * 排行榜数据访问DAO
     */
    @Resource(name = "chartsMysqlDao")
    private ChartsMysqlDao chartsMysqlDao;

    @Autowired
    private LogMysqlDao logMysqlDao;
    @Autowired
    private LogCacheMysqlDao logCacheMysqlDao;
    @Autowired
    private MenuMysqlDao menuMysqlDao;
    @Autowired
    private AdvertisementMysqlDao advertisementMysqlDao;
    @Autowired
    private DictionnaryMysqlDao dictionnaryMysqlDao;
    @Autowired
    private UserMysqlDao userMysqlDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private PlatformDao platformDao;
    @Autowired
    private SeriesDao seriesDao;
    @Autowired
    private VersionDao versionDao;
    @Autowired
    private BroadcastWhiteListMysqlDao broadcastWhiteListMysqlDao;
    @Autowired
    private CibnAuditMysqlDao cibnAuditMysqlDao;
    @Autowired
    private AuditAlbumPushDao auditAlbumPushDao;
    @Resource(name = "terminalConfigMysqlDao")
    private TerminalConfigMysqlDao terminalConfigMysqlDao;

    @Resource(name = "specialVideoMysqlDao")
    private SpecialVideoMysqlDao specialVideoMysqlDao;

    public StatRankWeekDataDao getStatRankWeekDataDao() {
        return this.statRankWeekDataDao;
    }

    public QrCodeMysqlDao getQrCodeMysqlDao() {
        return this.qrCodeMysqlDao;
    }

    public MusicNavMysqlDao getMusicNavMysqlDao() {
        return this.musicNavMysqlDao;
    }

    public void setMusicNavMysqlDao(MusicNavMysqlDao musicNavMysqlDao) {
        this.musicNavMysqlDao = musicNavMysqlDao;
    }

    public ChannelDataMysqlDao getChannelDataMysqlDao() {
        return this.channelDataMysqlDao;
    }

    public ChannelMysqlDao getChannelMysqlDao() {
        return this.channelMysqlDao;
    }

    public SearchConditionMysqlDao getSearchConditionMysqlDao() {
        return this.searchConditionMysqlDao;
    }

    public VideoMysqlDao getVideoMysqlDao() {
        return this.videoMysqlDao;
    }

    public AlbumMysqlDao getAlbumMysqlDao() {
        return this.albumMysqlDao;
    }

    public ChannelDataRecommendationBlockMysqlDao getChannelDataRecommendationBlockMysqlDao() {
        return this.channelDataRecommendationBlockMysqlDao;
    }

    public ChannelCmsChannelMysqlDao getChannelCmsChannelMysqlDao() {
        return this.channelCmsChannelMysqlDao;
    }

    public LanguageDictionaryMysqlDao getLanguageDictionaryMysqlDao() {
        return this.languageDictionaryMysqlDao;
    }

    public TpTerminalMysqlDao getTpTerminalMysqlDao() {
        return this.tpTerminalMysqlDao;
    }

    public TpVersionMysqlDao getTpVersionMysqlDao() {
        return this.tpVersionMysqlDao;
    }

    public ChartsMysqlDao getChartsMysqlDao() {
        return this.chartsMysqlDao;
    }

    public DeskConfigMysqlDao getDeskConfigMysqlDao() {
        return this.deskConfigMysqlDao;
    }

    public ChannelDataExtendMysqlDao getChannelDataExtendMysqlDao() {
        return this.channelDataExtendMysqlDao;
    }

    public LogMysqlDao getLogMysqlDao() {
        return this.logMysqlDao;
    }

    public LogCacheMysqlDao getLogCacheMysqlDao() {
        return logCacheMysqlDao;
    }

    public MenuMysqlDao getMenuMysqlDao() {
        return this.menuMysqlDao;
    }

    public AdvertisementMysqlDao getAdvertisementMysqlDao() {
        return this.advertisementMysqlDao;
    }

    public DictionnaryMysqlDao getDictionnaryMysqlDao() {
        return this.dictionnaryMysqlDao;
    }

    public UserMysqlDao getUserMysqlDao() {
        return this.userMysqlDao;
    }

    public BrandDao getBrandDao() {
        return this.brandDao;
    }

    public PlatformDao getPlatformDao() {
        return this.platformDao;
    }

    public SeriesDao getSeriesDao() {
        return this.seriesDao;
    }

    public VersionDao getVersionDao() {
        return this.versionDao;
    }

    public BroadcastWhiteListMysqlDao getBroadcastWhiteListMysqlDao() {
        return this.broadcastWhiteListMysqlDao;
    }

    public CibnAuditMysqlDao getCibnAuditMysqlDao() {
        return cibnAuditMysqlDao;
    }

    public AuditAlbumPushDao getAuditAlbumPushDao() {
        return auditAlbumPushDao;
    }

    public ContainerConfMysqlDao getContainerConfMysqlDao() {
        return this.containerConfMysqlDao;
    }

    public MmsHotAlbumMonitorMysqlDao getMmsHotAlbumMonitorMysqlDao() {
        return this.mmsHotAlbumMonitorMysqlDao;
    }

    public void setMmsHotAlbumMonitorMysqlDao(MmsHotAlbumMonitorMysqlDao mmsHotAlbumMonitorMysqlDao) {
        this.mmsHotAlbumMonitorMysqlDao = mmsHotAlbumMonitorMysqlDao;
    }

    public TerminalConfigMysqlDao getTerminalConfigMysqlDao() {
        return terminalConfigMysqlDao;
    }

    public SpecialVideoMysqlDao getSpecialVideoMysqlDao() {
        return specialVideoMysqlDao;
    }
}
