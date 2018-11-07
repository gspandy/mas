package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取播放互动信息封装类
 * @author liyunlong
 */
public class GetVideoReactionTpResponse {
    /**
     * 状态码
     */
    private String code;

    /**
     * 数据主体
     */
    private List<VideoReactionInfo> data = new ArrayList<VideoReactionInfo>();

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<VideoReactionInfo> getData() {
        return this.data;
    }

    public void setData(List<VideoReactionInfo> data) {
        this.data = data;
    }

    public static class VideoReactionInfo {

        /**
         * 投票标题
         */
        private String title;

        /**
         * 投票副标题
         */
        private String subtitle;

        /**
         * 触发秒数（单位秒）
         */
        private Integer second;

        /**
         * 开始时间戳（秒）
         */
        private Integer lifestart;

        /**
         * 结束时间戳（秒）
         */
        private Integer lifeend;

        /**
         * 投票遵循规则
         */
        private String rule;

        /**
         * 投票id
         */
        private String vote_id;

        /**
         * 视频id
         */
        private String vid;

        /**
         * 视频pid
         */
        private String pid;

        /**
         * 投票类型
         */
        private Integer votetype;

        /**
         * 投票选项数据列表
         */
        private List<ReactionData> options;

        /**
         * 分享内容数据列表
         */
        private List<ReactionData> share;

        /**
         * 动态效果数据列表
         */
        private List<ReactionData> effect;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return this.subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Integer getSecond() {
            return this.second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public Integer getLifestart() {
            return this.lifestart;
        }

        public void setLifestart(Integer lifestart) {
            this.lifestart = lifestart;
        }

        public Integer getLifeend() {
            return this.lifeend;
        }

        public void setLifeend(Integer lifeend) {
            this.lifeend = lifeend;
        }

        public String getRule() {
            return this.rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getVote_id() {
            return this.vote_id;
        }

        public void setVote_id(String vote_id) {
            this.vote_id = vote_id;
        }

        public String getVid() {
            return this.vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getPid() {
            return this.pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Integer getVotetype() {
            return this.votetype;
        }

        public void setVotetype(Integer votetype) {
            this.votetype = votetype;
        }

        public List<ReactionData> getOptions() {
            return this.options;
        }

        public void setOptions(List<ReactionData> options) {
            this.options = options;
        }

        public List<ReactionData> getShare() {
            return this.share;
        }

        public void setShare(List<ReactionData> share) {
            this.share = share;
        }

        public List<ReactionData> getEffect() {
            return this.effect;
        }

        public void setEffect(List<ReactionData> effect) {
            this.effect = effect;
        }

        /**
         * 投票选项信息封装类
         * @author liyunlong
         */
        public static class ReactionData {
            /**
             * 选项id
             */
            private String option_id;

            /**
             * 标题
             */
            private String title;

            /**
             * 副标题
             */
            private String subtitle;

            /**
             * 备注
             */
            private String remarks;

            /**
             * 投票或者动态效果的图片地址
             */
            private String img;

            /**
             * 分享二维码地址
             */
            private String url;

            public String getOption_id() {
                return this.option_id;
            }

            public void setOption_id(String option_id) {
                this.option_id = option_id;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubtitle() {
                return this.subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getRemarks() {
                return this.remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getImg() {
                return this.img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

        }
    }
}
