package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.io.Serializable;

/**
 * 体育跳转配置项；
 * 分别定义点播、直播、专题的跳转方式；
 * 业务维度为：业务应用（如体育、音乐）--机型--内容（点播、直播、专题）--付费模式（付费、免费）--交互动作（跳TV版、仅提示文案、跳体育APP）
 * @author KevinYi
 */
public class LetvContentSportsJumpConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6364469731076391543L;

    /**
     * 跳转内容，videoPlay--点播，live--直播，subject--专题，下同
     */
    private LetvChargeSportsJumpAction videoPlay;

    /**
     * 直播跳转
     */
    private LetvChargeSportsJumpAction live;

    /**
     * 专题跳转
     */
    private LetvChargeSportsJumpAction subject;

    /**
     * app跳转标准
     */
    private JumpAppConfig jumpAppConf;

    public LetvChargeSportsJumpAction getVideoPlay() {
        return videoPlay;
    }

    public void setVideoPlay(LetvChargeSportsJumpAction videoPlay) {
        this.videoPlay = videoPlay;
    }

    public LetvChargeSportsJumpAction getLive() {
        return live;
    }

    public void setLive(LetvChargeSportsJumpAction live) {
        this.live = live;
    }

    public LetvChargeSportsJumpAction getSubject() {
        return subject;
    }

    public void setSubject(LetvChargeSportsJumpAction subject) {
        this.subject = subject;
    }

    public JumpAppConfig getJumpAppConf() {
        return jumpAppConf;
    }

    public void setJumpAppConf(JumpAppConfig jumpAppConf) {
        this.jumpAppConf = jumpAppConf;
    }

    /**
     * 业务内容跳转动作
     * @author KevinYi
     */
    public static class LetvChargeSportsJumpAction implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = -4538397275115070615L;

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

    }

}
