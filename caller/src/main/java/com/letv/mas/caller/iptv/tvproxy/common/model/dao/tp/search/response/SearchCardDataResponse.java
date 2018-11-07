package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 具体卡片内容的返回
 */
public class SearchCardDataResponse {
    private String update;
    private List<SearchCardData> card_data_list;
    private String card_count;
    private String update_list;
    private String card_total_count;

    public String getUpdate() {
        return this.update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<SearchCardData> getCard_data_list() {
        return this.card_data_list;
    }

    public void setCard_data_list(List<SearchCardData> card_data_list) {
        this.card_data_list = card_data_list;
    }

    public String getCard_count() {
        return this.card_count;
    }

    public void setCard_count(String card_count) {
        this.card_count = card_count;
    }

    public String getUpdate_list() {
        return this.update_list;
    }

    public void setUpdate_list(String update_list) {
        this.update_list = update_list;
    }

    public String getCard_total_count() {
        return this.card_total_count;
    }

    public void setCard_total_count(String card_total_count) {
        this.card_total_count = card_total_count;
    }

    public static class SearchCardData {
        private String card_id;
        private String card_name;
        private List<CardContent> data_list;
        private String experiment_bucket_str;
        private String eid;
        private String trigger_str;
        private String module;
        private String area;
        private String reid;
        private String bucket;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getReid() {
            return reid;
        }

        public void setReid(String reid) {
            this.reid = reid;
        }

        public String getBucket() {
            return bucket;
        }

        public void setBucket(String bucket) {
            this.bucket = bucket;
        }

        public String getModule() {
            return this.module;
        }

        public void setModule(String module) {
            this.module = module;
        }

        public String getExperiment_bucket_str() {
            return this.experiment_bucket_str;
        }

        public void setExperiment_bucket_str(String experiment_bucket_str) {
            this.experiment_bucket_str = experiment_bucket_str;
        }

        public String getEid() {
            return this.eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getTrigger_str() {
            return this.trigger_str;
        }

        public void setTrigger_str(String trigger_str) {
            this.trigger_str = trigger_str;
        }

        public String getCard_name() {
            return this.card_name;
        }

        public void setCard_name(String card_name) {
            this.card_name = card_name;
        }

        public String getCard_id() {
            return this.card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public List<CardContent> getData_list() {
            return this.data_list;
        }

        public void setData_list(List<CardContent> data_list) {
            this.data_list = data_list;
        }

        public static class CardContent {
            private String id;
            private String title;
            private String subtitle;
            private String src;
            private String sub_src;
            private String category_id;
            private String category;
            private String subCategory;
            private int data_type;
            private String poster_st;
            private String url;
            private String vid;
            private String global_id;
            private Map<String, String> platform_play;

            private String isEnd;
            private String nowEpisodes;
            private String rating;
            private Map<String, String> payPlatform;
            private int playMark;
            private String isHomemade;

            private String name;
            private String singerName;
            private String le_mobile_app_id;
            private String xiamiId;
            private String pic_prefix;

            private String categoryName;
            private String tv_app_id;
            private String m_app_id;
            private String package_name;
            private String icon;
            private int is_pay;
            private String video_type;
            private String show_size;
            private String external_id;
            private List<JSONObject> video_list;

            public List<JSONObject> getVideo_list() {
                return video_list;
            }

            public void setVideo_list(List<JSONObject> video_list) {
                this.video_list = video_list;
            }

            private long start_time;
            private long end_time;
            private String live_id;
            private String record_id;

            public String getRecord_id() {
                return record_id;
            }

            public void setRecord_id(String record_id) {
                this.record_id = record_id;
            }

            public String getLive_id() {
                return live_id;
            }

            public void setLive_id(String live_id) {
                this.live_id = live_id;
            }

            public long getStart_time() {
                return start_time;
            }

            public void setStart_time(long start_time) {
                this.start_time = start_time;
            }

            public long getEnd_time() {
                return end_time;
            }

            public void setEnd_time(long end_time) {
                this.end_time = end_time;
            }

            public String getExternal_id() {
                return external_id;
            }

            public void setExternal_id(String external_id) {
                this.external_id = external_id;
            }

            public String getSub_src() {
                return sub_src;
            }

            public void setSub_src(String sub_src) {
                this.sub_src = sub_src;
            }

            public String getSubCategory() {
                return subCategory;
            }

            public void setSubCategory(String subCategory) {
                this.subCategory = subCategory;
            }

            public String getShow_size() {
                return show_size;
            }

            public void setShow_size(String show_size) {
                this.show_size = show_size;
            }

            public String getVideo_type() {
                return video_type;
            }

            public void setVideo_type(String video_type) {
                this.video_type = video_type;
            }

            public int getIs_pay() {
                return is_pay;
            }

            public void setIs_pay(int is_pay) {
                this.is_pay = is_pay;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getTv_app_id() {
                return tv_app_id;
            }

            public void setTv_app_id(String tv_app_id) {
                this.tv_app_id = tv_app_id;
            }

            public String getM_app_id() {
                return m_app_id;
            }

            public void setM_app_id(String m_app_id) {
                this.m_app_id = m_app_id;
            }

            public String getPackage_name() {
                return package_name;
            }

            public void setPackage_name(String package_name) {
                this.package_name = package_name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSingerName() {
                return singerName;
            }

            public void setSingerName(String singerName) {
                this.singerName = singerName;
            }

            public String getLe_mobile_app_id() {
                return le_mobile_app_id;
            }

            public void setLe_mobile_app_id(String le_mobile_app_id) {
                this.le_mobile_app_id = le_mobile_app_id;
            }

            public String getXiamiId() {
                return xiamiId;
            }

            public void setXiamiId(String xiamiId) {
                this.xiamiId = xiamiId;
            }

            public String getPic_prefix() {
                return pic_prefix;
            }

            public void setPic_prefix(String pic_prefix) {
                this.pic_prefix = pic_prefix;
            }

            public Map<String, String> getPayPlatform() {
                return payPlatform;
            }

            public void setPayPlatform(Map<String, String> payPlatform) {
                this.payPlatform = payPlatform;
            }

            public int getPlayMark() {
                return playMark;
            }

            public void setPlayMark(int playMark) {
                this.playMark = playMark;
            }

            public String getIsHomemade() {
                return isHomemade;
            }

            public void setIsHomemade(String isHomemade) {
                this.isHomemade = isHomemade;
            }

            public String getIsEnd() {
                return this.isEnd;
            }

            public void setIsEnd(String isEnd) {
                this.isEnd = isEnd;
            }

            public String getNowEpisodes() {
                return this.nowEpisodes;
            }

            public void setNowEpisodes(String nowEpisodes) {
                this.nowEpisodes = nowEpisodes;
            }

            public String getRating() {
                return this.rating;
            }

            public void setRating(String rating) {
                this.rating = rating;
            }

            public String getGlobal_id() {
                return this.global_id;
            }

            public void setGlobal_id(String global_id) {
                this.global_id = global_id;
            }

            public String getCategory() {
                return this.category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getSrc() {
                return this.src;
            }

            public void setSrc(String src) {
                this.src = src;
            }

            public int getData_type() {
                return data_type;
            }

            public void setData_type(int data_type) {
                this.data_type = data_type;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPoster_st() {
                return this.poster_st;
            }

            public void setPoster_st(String poster_st) {
                this.poster_st = poster_st;
            }

            public String getCategory_id() {
                return this.category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getId() {
                return this.id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Map<String, String> getPlatform_play() {
                return this.platform_play;
            }

            public void setPlatform_play(Map<String, String> platform_play) {
                this.platform_play = platform_play;
            }

            public String getUrl() {
                return this.url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSubtitle() {
                return this.subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getVid() {
                return this.vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

        }

    }

}
