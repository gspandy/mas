package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.notification.model.dto.QrCodeDto;

import java.util.List;

/**
 * 播放时互动信息封装类
 * @author liyunlong
 */
public class VideoReactionDto {

    /**
     * 互动信息列表
     */
    private List<VideoReaction> reactionList;

    /**
     * 频道二维码信息
     */
    private QrCodeDto qrCodeDto;

    public List<VideoReaction> getReactionList() {
        return this.reactionList;
    }

    public void setReactionList(List<VideoReaction> reactionList) {
        this.reactionList = reactionList;
    }

    public QrCodeDto getQrCodeDto() {
        return this.qrCodeDto;
    }

    public void setQrCodeDto(QrCodeDto qrCodeDto) {
        this.qrCodeDto = qrCodeDto;
    }

    public static class VideoReaction extends BaseDto {

        /**
         * 投票标题
         */
        private String title;

        /**
         * 投票副标题
         */
        private String subTitle;

        /**
         * 触发秒数（单位秒）
         */
        private Integer second;

        /**
         * 扫码互动开始时间戳（秒）
         */
        private Integer lifeStart;

        /**
         * 扫码互动结束时间戳（秒）
         */
        private Integer lifeEnd;

        /**
         * 投票遵循规则
         */
        private String rule;

        /**
         * 投票id
         */
        private String voteId;

        /**
         * 视频id
         */
        private String vid;

        /**
         * 专辑pid
         */
        private String pid;

        /**
         * 投票类型
         */
        private Integer voteType;

        /**
         * 互动信息列表
         */
        private List<ReactionDto> reactonData;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return this.subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public Integer getSecond() {
            return this.second;
        }

        public void setSecond(Integer second) {
            this.second = second;
        }

        public Integer getLifeStart() {
            return this.lifeStart;
        }

        public void setLifeStart(Integer lifeStart) {
            this.lifeStart = lifeStart;
        }

        public Integer getLifeEnd() {
            return this.lifeEnd;
        }

        public void setLifeEnd(Integer lifeEnd) {
            this.lifeEnd = lifeEnd;
        }

        public String getRule() {
            return this.rule;
        }

        public void setRule(String rule) {
            this.rule = rule;
        }

        public String getVoteId() {
            return this.voteId;
        }

        public void setVoteId(String voteId) {
            this.voteId = voteId;
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

        public Integer getVoteType() {
            return this.voteType;
        }

        public void setVoteType(Integer voteType) {
            this.voteType = voteType;
        }

        public List<ReactionDto> getReactonData() {
            return this.reactonData;
        }

        public void setReactonData(List<ReactionDto> reactonData) {
            this.reactonData = reactonData;
        }

        /**
         * 投票选项信息封装类
         * @author liyunlong
         */
        public static class ReactionDto {
            /**
             * 互动信息类型，1--动态效果，2--投票选项，3--分享
             */
            private Integer type;
            /**
             * 选项id
             */
            private String optionId;

            /**
             * 标题
             */
            private String title;

            /**
             * 副标题
             */
            private String subTitle;

            /**
             * 备注
             */
            private String remarks;

            /**
             * 如果是投票或者动态效果，则是图片地址，如果是分享，则是分享二维码
             */
            private String imgUrl;

            public Integer getType() {
                return this.type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getOptionId() {
                return this.optionId;
            }

            public void setOptionId(String optionId) {
                this.optionId = optionId;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubTitle() {
                return this.subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getRemarks() {
                return this.remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getImgUrl() {
                return this.imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }
        }
    }
}
