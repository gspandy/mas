package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.io.Serializable;
import java.util.Map;

/**
 * 乐搜专用业务美容跳转体育配置项；
 * 对比SportsJumpConfig设计，LesoSportsJumpConfig业务维度为：
 * 业务应用（如体育、音乐）--机型--内容（点播、直播、专题）--版权（有TV版权、无TV版权）--付费模式（付费、免费）（--直播状态（未开始、直播中、
 * 回看、已结束））--交互动作（跳TV版、仅提示文案
 * 、跳体育APP）
 * @author KevinYi
 */
public class LesoContentSportsJumpConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3873454160815254374L;

    private LesoCopyrightSportsJumpAction videoPlay;

    private LesoLiveStatusSportsJumpAction live;

    private LesoCopyrightSportsJumpAction subject;

    /**
     * 默认app跳转标准，当交互动作是跳转到指定app（actionType=2）时使用
     */
    private JumpAppConfig jumpAppConf;

    /**
     * app跳转配置列表，key--app跳转配置key；value--app跳转配置；
     * 本Map供交互动作是跳转app列表时使用，跳转app列表指定多个app跳转配置id，均从本Map中获取详细配置项
     */
    private Map<String, JumpAppConfig> jumpAppConfMap;

    public LesoCopyrightSportsJumpAction getVideoPlay() {
        return videoPlay;
    }

    public void setVideoPlay(LesoCopyrightSportsJumpAction videoPlay) {
        this.videoPlay = videoPlay;
    }

    public LesoLiveStatusSportsJumpAction getLive() {
        return live;
    }

    public void setLive(LesoLiveStatusSportsJumpAction live) {
        this.live = live;
    }

    public LesoCopyrightSportsJumpAction getSubject() {
        return subject;
    }

    public void setSubject(LesoCopyrightSportsJumpAction subject) {
        this.subject = subject;
    }

    public JumpAppConfig getJumpAppConf() {
        return jumpAppConf;
    }

    public void setJumpAppConf(JumpAppConfig jumpAppConf) {
        this.jumpAppConf = jumpAppConf;
    }

    public Map<String, JumpAppConfig> getJumpAppConfMap() {
        return jumpAppConfMap;
    }

    public void setJumpAppConfMap(Map<String, JumpAppConfig> jumpAppConfMap) {
        this.jumpAppConfMap = jumpAppConfMap;
    }

    /**
     * 乐搜点播和专题版权维度跳转配置，分为tvCopyright--内网数据且有TV版权，letvCopyright--内网数据但无TV版权，
     * noCopyright--外网数据，无内网版权
     * @author KevinYi
     */
    public static class LesoCopyrightSportsJumpAction implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -5008372475290490197L;

        /**
         * 内网数据且有TV版权
         */
        private LesoChargeSportsJumpAction tvCopyright;

        /**
         * 内网数据但无TV版权
         */
        private LesoChargeSportsJumpAction letvCopyright;

        /**
         * 外网数据，无内网版权
         */
        private LesoChargeSportsJumpAction noCopyright;

        public LesoChargeSportsJumpAction getTvCopyright() {
            return tvCopyright;
        }

        public void setTvCopyright(LesoChargeSportsJumpAction tvCopyright) {
            this.tvCopyright = tvCopyright;
        }

        public LesoChargeSportsJumpAction getLetvCopyright() {
            return letvCopyright;
        }

        public void setLetvCopyright(LesoChargeSportsJumpAction letvCopyright) {
            this.letvCopyright = letvCopyright;
        }

        public LesoChargeSportsJumpAction getNoCopyright() {
            return noCopyright;
        }

        public void setNoCopyright(LesoChargeSportsJumpAction noCopyright) {
            this.noCopyright = noCopyright;
        }

    }

    /**
     * 乐搜点播或专题付费模式维度跳转动作，分为付费、免费和默认，一般默认和免费保持一致
     * @author KevinYi
     */
    public static class LesoChargeSportsJumpAction implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 2159067470441100629L;

        /**
         * 付费内容跳转方式，0--TV版播放，1--仅提示文案，2--跳转APP，3--跳APP列表（从APP列表中依次选择跳转，
         * 跳转失败的转向下一个app跳转），4--在当前应用打开，走当前应用默认逻辑，5--打开内置浏览器，下同
         */
        private String pay;

        /**
         * 免费内容跳转方式；
         */
        private String free;

        /**
         * 默认跳转方式，当无法判断内容付费类型时使用
         */
        private String def;

        /**
         * 当跳转类型时1时，显示的文案；这里没有像jumpAppConf单独提出作为公共字段，考虑到定向运营需求
         */
        private String title;

        /**
         * 跳转app列表配置map，当交互动作是3--跳APP列表时使用；
         * key--取值是付费策略字符串，"pay"、"free"、"def"；value--JumpAppConfig的id列表，
         * 多个id由英文逗号拼接，具体配置可从LesoContentSportsJumpConfig.jumpAppConfMap中按id查询
         */
        private Map<String, String> jumpAppsMap;

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getDef() {
            return def;
        }

        public void setDef(String def) {
            this.def = def;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Map<String, String> getJumpAppsMap() {
            return jumpAppsMap;
        }

        public void setJumpAppsMap(Map<String, String> jumpAppsMap) {
            this.jumpAppsMap = jumpAppsMap;
        }

    }

    /**
     * 乐搜直播状态维度跳转配置，分为预告、直播中、集锦、回看、结束
     * @author KevinYi
     */
    public static class LesoLiveStatusSportsJumpAction implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -4137840753849882001L;

        /**
         * 预告，0--TV版播放，1--仅提示文案，2--跳转APP，3--跳APP列表（从APP列表中依次选择跳转，跳转失败的转向下一个app跳转
         * ），下同
         */
        private LesoCopyrightSportsJumpAction preview;

        /**
         * 直播中
         */
        private LesoCopyrightSportsJumpAction onLive;

        /**
         * 集锦
         */
        private LesoCopyrightSportsJumpAction highlight;

        /**
         * 回看
         */
        private LesoCopyrightSportsJumpAction replay;

        public LesoCopyrightSportsJumpAction getPreview() {
            return preview;
        }

        public void setPreview(LesoCopyrightSportsJumpAction preview) {
            this.preview = preview;
        }

        public LesoCopyrightSportsJumpAction getOnLive() {
            return onLive;
        }

        public void setOnLive(LesoCopyrightSportsJumpAction onLive) {
            this.onLive = onLive;
        }

        public LesoCopyrightSportsJumpAction getHighlight() {
            return highlight;
        }

        public void setHighlight(LesoCopyrightSportsJumpAction highlight) {
            this.highlight = highlight;
        }

        public LesoCopyrightSportsJumpAction getReplay() {
            return replay;
        }

        public void setReplay(LesoCopyrightSportsJumpAction replay) {
            this.replay = replay;
        }

    }

}
