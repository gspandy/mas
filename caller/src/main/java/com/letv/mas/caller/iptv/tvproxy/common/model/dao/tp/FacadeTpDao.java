package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.ActivityTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.alarm.AlarmTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cashier.CashierVpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.ChannelTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.cms.CmsTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.live.LiveTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.recommendation.RecommendationTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.report.ActReportDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.SearchTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.StaticTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.terminal.TerminalTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.threescreen.ThreeScreenTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.DanmuTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.StatTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.UserTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.*;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpDao;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpDaoV2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TpDao门面类
 */
@Component(value = "v2.FacadeTpDao")
public class FacadeTpDao {

    @Resource
    private VipTpDao vipTpDao;

    @Resource
    private VipTpDaoV2 vipTpDaoV2;

    @Resource
    private UserTpDao userTpDao;

    @Resource
    private CmsTpDao cmsTpDao;

    @Resource
    private RecommendationTpDao recommendationTpDao;

    @Resource(name = "v2.StatTpDao")
    private StatTpDao statTpDao;

    @Resource(name = "v2.VideoTpDao")
    private VideoTpDao videoTpDao;

    @Resource
    private SearchTpDao searchTpDao;

    @Resource(name = "v2.GoLiveMovieTpDao")
    private GoLiveMovieTpDao goLiveMovieTpDao;

    @Resource(name = "v2.SoundInkTpDao")
    private SoundInkTpDao soundInkTpDao;

    @Resource(name = "v2.TerminalTpDao")
    private TerminalTpDao terminalTpDao;

    @Resource(name = "v2.LiveTpDao")
    private LiveTpDao liveTpDao;

    @Resource(name = "v2.ChannelTpDao")
    private ChannelTpDao channelTpDao;

    @Resource(name = "v2.ThreeScreenTpDao")
    private ThreeScreenTpDao threeScreenTpDao;

    @Resource(name = "v2.DictTpDao")
    private DictTpDao dictTpDao;

    @Resource(name = "v2.DanmuTpDao")
    private DanmuTpDao danmuTpDao;

    @Resource
    private CommonVideoTpDao commonVideoTpDao;

    @Resource(name = "v2.AlarmTpDao")
    private AlarmTpDao alarmTpDao;

    @Resource
    private StaticTpDao staticTpDao;

    @Resource
    private ActivityTpDao activityTpDao;

    @Resource
    private CashierVpDao cashierVpDao;

    @Resource
    private ActReportDao actReportDao;

    public VipTpDao getVipTpDao() {
        return this.vipTpDao;
    }

    public VipTpDaoV2 getVipTpDaoV2() {
        return this.vipTpDaoV2;
    }

    public UserTpDao getUserTpDao() {
        return this.userTpDao;
    }

    public CmsTpDao getCmsTpDao() {
        return this.cmsTpDao;
    }

    public RecommendationTpDao getRecommendationTpDao() {
        return this.recommendationTpDao;
    }

    public StatTpDao getStatTpDao() {
        return this.statTpDao;
    }

    public VideoTpDao getVideoTpDao() {
        return this.videoTpDao;
    }

    public SearchTpDao getSearchTpDao() {
        return this.searchTpDao;
    }

    public SoundInkTpDao getSoundInkTpDao() {
        return this.soundInkTpDao;
    }

    public LiveTpDao getLiveTpDao() {
        return this.liveTpDao;
    }

    public ChannelTpDao getChannelTpDao() {
        return this.channelTpDao;
    }

    public ThreeScreenTpDao getThreeScreenTpDao() {
        return this.threeScreenTpDao;
    }

    public DictTpDao getDictTpDao() {
        return this.dictTpDao;
    }

    public StaticTpDao getStaticTpDao() {
        return this.staticTpDao;
    }

    public GoLiveMovieTpDao getGoLiveMovieTpDao() {
        return this.goLiveMovieTpDao;
    }

    public TerminalTpDao getTerminalTpDao() {
        return this.terminalTpDao;
    }

    public CommonVideoTpDao getCommonVideoTpDao() {
        return this.commonVideoTpDao;
    }

    public AlarmTpDao getAlarmTpDao() {
        return alarmTpDao;
    }

    public DanmuTpDao getDanmuTpDao() {
        return this.danmuTpDao;
    }

    public ActivityTpDao getActivityTpDao() {
        return activityTpDao;
    }

    public CashierVpDao getCashierVpDao() {
        return cashierVpDao;
    }

    public ActReportDao getActReportDao() {
        return actReportDao;
    }
}
