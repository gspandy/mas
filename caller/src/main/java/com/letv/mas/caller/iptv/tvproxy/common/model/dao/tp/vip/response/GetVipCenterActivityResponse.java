package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import java.io.Serializable;
import java.util.List;

public class GetVipCenterActivityResponse implements Serializable {

    private Integer status;// 响应码，0--成功，非0--失败
    private String status_desc;// 错误描述，成功是为空
    private List<VipCenterResultData> data;// 具体数据
    private String result_data;//

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_desc() {
        return this.status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }

    public List<VipCenterResultData> getData() {
        return this.data;
    }

    public void setData(List<VipCenterResultData> data) {
        this.data = data;
    }

    public String getResult_data() {
        return this.result_data;
    }

    public void setResult_data(String result_data) {
        this.result_data = result_data;
    }

    public static class VipCenterResultData {
        private String item_title;// 标题
        private Long item_time;// 活动上线时间
        private String item_desc;// 推荐位描述
        private Integer item_weight;// 权重，权重值 0-9 数值越大 权重越高
        private String user_type;// 用户类型
        private String time_left;// 会员起始时间
        private String time_right;// 会员到期时间
        private Integer go_type;// 跳转类型
        private JumpDataParam go_param;// 跳转所需参数字段
        private String pay_info;// BOSS有关的信息
        private String delivery_address;// 收货地址
        private String aid;// VIP center activity id

        public String getItem_title() {
            return this.item_title;
        }

        public void setItem_title(String item_title) {
            this.item_title = item_title;
        }

        public Long getItem_time() {
            return this.item_time;
        }

        public void setItem_time(Long item_time) {
            this.item_time = item_time;
        }

        public String getItem_desc() {
            return this.item_desc;
        }

        public void setItem_desc(String item_desc) {
            this.item_desc = item_desc;
        }

        public Integer getItem_weight() {
            return this.item_weight;
        }

        public void setItem_weight(Integer item_weight) {
            this.item_weight = item_weight;
        }

        public String getUser_type() {
            return this.user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getTime_left() {
            return this.time_left;
        }

        public void setTime_left(String time_left) {
            this.time_left = time_left;
        }

        public String getTime_right() {
            return this.time_right;
        }

        public void setTime_right(String time_right) {
            this.time_right = time_right;
        }

        public Integer getGo_type() {
            return this.go_type;
        }

        public void setGo_type(Integer go_type) {
            this.go_type = go_type;
        }

        public JumpDataParam getGo_param() {
            return this.go_param;
        }

        public void setGo_param(JumpDataParam go_param) {
            this.go_param = go_param;
        }

        public String getPay_info() {
            return this.pay_info;
        }

        public void setPay_info(String pay_info) {
            this.pay_info = pay_info;
        }

        public String getDelivery_address() {
            return this.delivery_address;
        }

        public void setDelivery_address(String delivery_address) {
            this.delivery_address = delivery_address;
        }

        public String getAid() {
            return this.aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public static class JumpDataParam {
            private String img;// 海报图
            private String url;// h5地址
            private Integer openType;// 展示h5后是否可以继续进入收银台，1--需要，2--不需要
            private String title;// 顶部触达位字段
            private String brief;// 简单描述
            private String logo;// 图片
            private String buttonText;// 顶部触达位字段
            private String albumId;// 专辑ID
            private String videoId;// 视频ID
            private String blockId;// CMS版块id
            private String subjectId;// 专题Id，CMS
            private Integer subjectType;// 子专题类型 0--视频专题 1--专辑专题 2--直播专题聚合页
                                        // 4--多专题包专题 5--时间轴专题 6--热点专题
            private String liveId;// 直播id
            private Integer channelId;// 频道Id
            private Integer subType;// 子类型，0--我的，1--首页，2--频道，3--直播大厅，4--专题，5--扩展，6--排行榜
            private String clickContent;// 按钮文案
            private String subtitle;// 副标题

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

            public Integer getOpenType() {
                return this.openType;
            }

            public void setOpenType(Integer openType) {
                this.openType = openType;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBrief() {
                return this.brief;
            }

            public void setBrief(String brief) {
                this.brief = brief;
            }

            public String getLogo() {
                return this.logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getButtonText() {
                return this.buttonText;
            }

            public void setButtonText(String buttonText) {
                this.buttonText = buttonText;
            }

            public String getAlbumId() {
                return this.albumId;
            }

            public void setAlbumId(String albumId) {
                this.albumId = albumId;
            }

            public String getVideoId() {
                return this.videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getBlockId() {
                return this.blockId;
            }

            public void setBlockId(String blockId) {
                this.blockId = blockId;
            }

            public String getSubjectId() {
                return this.subjectId;
            }

            public void setSubjectId(String subjectId) {
                this.subjectId = subjectId;
            }

            public Integer getSubjectType() {
                return this.subjectType;
            }

            public void setSubjectType(Integer subjectType) {
                this.subjectType = subjectType;
            }

            public String getLiveId() {
                return this.liveId;
            }

            public void setLiveId(String liveId) {
                this.liveId = liveId;
            }

            public Integer getChannelId() {
                return this.channelId;
            }

            public void setChannelId(Integer channelId) {
                this.channelId = channelId;
            }

            public Integer getSubType() {
                return this.subType;
            }

            public void setSubType(Integer subType) {
                this.subType = subType;
            }

            public String getClickContent() {
                return this.clickContent;
            }

            public void setClickContent(String clickContent) {
                this.clickContent = clickContent;
            }

            public String getSubtitle() {
                return this.subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

        }
    }
}
