package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUrmActivityResponse implements Serializable {
    /**
     * urm activity data list
     */
    private List<UrmActivityData> messages;
    /**
     * request id
     */
    private String reqId;

    /**
     * report info
     */
    private UrmActivityReport reporting;

    public List<UrmActivityData> getMessages() {
        return messages;
    }

    public void setMessages(List<UrmActivityData> messages) {
        this.messages = messages;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public UrmActivityReport getReporting() {
        return reporting;
    }

    public void setReporting(UrmActivityReport reporting) {
        this.reporting = reporting;
    }

    public static class UrmActivityData implements Serializable {
        /**
         * 
         */
        private static final long serialVersionUID = -6026161910048243716L;
        private Integer is_controlled_by_algo;
        /**
         * priority of activity
         */
        private Integer priority;
        /**
         * activity info and jump info
         */
        private UrmActivityInfo tv_info;
        /**
         * the activity show info
         */
        // private UrmShowInfo show_info;
        private Map<String, String> show_info;

        public Integer getPriority() {
            return priority;
        }

        public void setPriority(Integer priority) {
            this.priority = priority;
        }

        public UrmActivityInfo getTv_info() {
            return tv_info;
        }

        public Integer getIs_controlled_by_algo() {
            return is_controlled_by_algo;
        }

        public void setIs_controlled_by_algo(Integer is_controlled_by_algo) {
            this.is_controlled_by_algo = is_controlled_by_algo;
        }

        public void setTv_info(UrmActivityInfo tv_info) {
            this.tv_info = tv_info;
        }

        public Map<String, String> getShow_info() {
            return show_info;
        }

        public void setShow_info(Map<String, String> show_info) {
            this.show_info = show_info;
        }

        public static class UrmActivityInfo implements Serializable {
            private Integer jump_type;
            private String jump_to;
            private String delivery_address;
            private ConfigInfo jump_info;

            public Integer getJump_type() {
                return jump_type;
            }

            public void setJump_type(Integer jump_type) {
                this.jump_type = jump_type;
            }

            public String getDelivery_address() {
                return delivery_address;
            }

            public void setDelivery_address(String delivery_address) {
                this.delivery_address = delivery_address;
            }

            public ConfigInfo getJump_info() {
                return jump_info;
            }

            public void setJump_info(ConfigInfo jump_info) {
                this.jump_info = jump_info;
            }

            public String getJump_to() {
                return jump_to;
            }

            public void setJump_to(String jump_to) {
                this.jump_to = jump_to;
            }

            public static class ConfigInfo implements Serializable {
                /**
                 * 
                 */
                private static final long serialVersionUID = 4700534733406831496L;
                private String activity_package_id;
                private BossActivityData activity_package;
                private String videoId;
                private String url;
                private Integer openType;
                private Integer interactive;
                private String blockId;
                private String freeActivityId;// 免费活动id
                private String dunning_image;// 催还款图片
                private String dunning_desc;// 催还款描述
                private String dunning_button;// 催还款按钮文案
                private Long productid;// BOSS活动id
                private Integer pay_monthtype;// 套餐id
                private String pric;// 价格
                private Long start_time;// 会员服务开始时间
                private Long free_end_time;// 会员服务结束时间
                private Long required_pay_time;// 还款截止日
                @JsonProperty(value = "EndTime")
                private Integer EndTime;// 还款到期时间
                private String item_id;
                private String category_id;
                private String have_mmsid;
                private String jump_type;
                private String albumId;

                // Tv版
                private String subjectId;
                private String subjectType;

                // 体育
                private String channelId;
                private String channelName;
                private String programId;
                private String fromTag;
                private String gameId;
                private String gameName;
                private String topicId;

                private String id;
                private String contentId;

                @JsonProperty("CPS_no")
                private String CPS_no;
                private String code_no;

                public String getCPS_no() {
                    return CPS_no;
                }

                public void setCPS_no(String CPS_no) {
                    this.CPS_no = CPS_no;
                }

                public String getCode_no() {
                    return code_no;
                }

                public void setCode_no(String code_no) {
                    this.code_no = code_no;
                }

                public String getSubjectId() {
                    return subjectId;
                }

                public void setSubjectId(String subjectId) {
                    this.subjectId = subjectId;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getSubjectType() {
                    return subjectType;
                }

                public void setSubjectType(String subjectType) {
                    this.subjectType = subjectType;
                }

                public String getAlbumId() {
                    return albumId;
                }

                public void setAlbumId(String albumId) {
                    this.albumId = albumId;
                }

                public String getFromTag() {
                    return fromTag;
                }

                public void setFromTag(String fromTag) {
                    this.fromTag = fromTag;
                }

                public String getActivity_package_id() {
                    return activity_package_id;
                }

                public void setActivity_package_id(String activity_package_id) {
                    this.activity_package_id = activity_package_id;
                }

                public BossActivityData getActivity_package() {
                    return activity_package;
                }

                public void setActivity_package(BossActivityData activity_package) {
                    this.activity_package = activity_package;
                }

                public String getVideoId() {
                    return videoId;
                }

                public void setVideoId(String videoId) {
                    this.videoId = videoId;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public Integer getOpenType() {
                    return openType;
                }

                public void setOpenType(Integer openType) {
                    this.openType = openType;
                }

                public Integer getInteractive() {
                    return interactive;
                }

                public void setInteractive(Integer interactive) {
                    this.interactive = interactive;
                }

                public String getBlockId() {
                    return blockId;
                }

                public void setBlockId(String blockId) {
                    this.blockId = blockId;
                }

                public String getItem_id() {
                    return item_id;
                }

                public void setItem_id(String item_id) {
                    this.item_id = item_id;
                }

                public String getCategory_id() {
                    return category_id;
                }

                public void setCategory_id(String category_id) {
                    this.category_id = category_id;
                }

                public String getHave_mmsid() {
                    return have_mmsid;
                }

                public void setHave_mmsid(String have_mmsid) {
                    this.have_mmsid = have_mmsid;
                }

                public String getJump_type() {
                    return jump_type;
                }

                public void setJump_type(String jump_type) {
                    this.jump_type = jump_type;
                }

                public String getChannelName() {
                    return channelName;
                }

                public void setChannelName(String channelName) {
                    this.channelName = channelName;
                }

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public String getProgramId() {
                    return programId;
                }

                public void setProgramId(String programId) {
                    this.programId = programId;
                }

                public String getGameId() {
                    return gameId;
                }

                public void setGameId(String gameId) {
                    this.gameId = gameId;
                }

                public String getGameName() {
                    return gameName;
                }

                public void setGameName(String gameName) {
                    this.gameName = gameName;
                }

                public String getTopicId() {
                    return topicId;
                }

                public void setTopicId(String topicId) {
                    this.topicId = topicId;
                }

                public String getContentId() {
                    return contentId;
                }

                public void setContentId(String contentId) {
                    this.contentId = contentId;
                }

                public String getFreeActivityId() {
                    return freeActivityId;
                }

                public void setFreeActivityId(String freeActivityId) {
                    this.freeActivityId = freeActivityId;
                }

                public String getDunning_image() {
                    return dunning_image;
                }

                public void setDunning_image(String dunning_image) {
                    this.dunning_image = dunning_image;
                }

                public String getDunning_desc() {
                    return dunning_desc;
                }

                public void setDunning_desc(String dunning_desc) {
                    this.dunning_desc = dunning_desc;
                }

                public String getDunning_button() {
                    return dunning_button;
                }

                public void setDunning_button(String dunning_button) {
                    this.dunning_button = dunning_button;
                }

                public Long getProductid() {
                    return productid;
                }

                public void setProductid(Long productid) {
                    this.productid = productid;
                }

                public Integer getPay_monthtype() {
                    return pay_monthtype;
                }

                public void setPay_monthtype(Integer pay_monthtype) {
                    this.pay_monthtype = pay_monthtype;
                }

                public String getPric() {
                    return pric;
                }

                public void setPric(String pric) {
                    this.pric = pric;
                }

                public Long getStart_time() {
                    return start_time;
                }

                public void setStart_time(Long start_time) {
                    this.start_time = start_time;
                }

                public Long getFree_end_time() {
                    return free_end_time;
                }

                public void setFree_end_time(Long free_end_time) {
                    this.free_end_time = free_end_time;
                }

                public Long getRequired_pay_time() {
                    return required_pay_time;
                }

                public void setRequired_pay_time(Long required_pay_time) {
                    this.required_pay_time = required_pay_time;
                }

                public Integer getEndTime() {
                    return EndTime;
                }

                public void setEndTime(Integer endTime) {
                    this.EndTime = endTime;
                }

            }
        }
    }

    public static class UrmActivityReport {
        private String campaign_id;// URM活动id
        private String touch_message_id;// 上报字段
        private String touch_spot_id;// URM触达位id

        public String getCampaign_id() {
            return campaign_id;
        }

        public void setCampaign_id(String campaign_id) {
            this.campaign_id = campaign_id;
        }

        public String getTouch_message_id() {
            return touch_message_id;
        }

        public void setTouch_message_id(String touch_message_id) {
            this.touch_message_id = touch_message_id;
        }

        public String getTouch_spot_id() {
            return touch_spot_id;
        }

        public void setTouch_spot_id(String touch_spot_id) {
            this.touch_spot_id = touch_spot_id;
        }

    }
}
