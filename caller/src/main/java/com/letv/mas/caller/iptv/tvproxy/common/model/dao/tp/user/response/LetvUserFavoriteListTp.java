package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response.GetSubjectTpResponse.SubjectDataTpResponse.TemplateJson;

import java.util.List;

/**
 * 获取专题收藏
 * @author yuehongmin
 */
public class LetvUserFavoriteListTp {

    private String code;
    private LetvUserFavoriteListData data;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LetvUserFavoriteListData getData() {
        return this.data;
    }

    public void setData(LetvUserFavoriteListData data) {
        this.data = data;
    }

    public static class LetvUserFavoriteListData {

        private Integer total;
        private Integer page;
        private Integer pagesize;
        private List<LetvUserFavorite> items;

        public Integer getPage() {
            return this.page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getPagesize() {
            return this.pagesize;
        }

        public void setPagesize(Integer pagesize) {
            this.pagesize = pagesize;
        }

        public List<LetvUserFavorite> getItems() {
            return this.items;
        }

        public void setItems(List<LetvUserFavorite> items) {
            this.items = items;
        }

        public Integer getTotal() {
            return this.total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }

    public static class LetvUserFavorite {

        private String favorite_id;
        private String favorite_type;
        private String zt_id;
        private Integer product;
        private String productName;
        private String title;
        private String sub_title;
        private Pic pic_all;
        private Integer category;
        private TemplateJson templateJson;
        private String playPlatform;
        private String global_id;

        // favorite_type=1【查询点播收藏】的字段
        private String channel_id; // 频道id
        private String video_id; // 视频id
        private String play_id; // 等同于album id(专辑id)
        private String episode; // 第几集
        private String create_time; // 创建时间，格式"1435202717"
        private PlayRecord play_record; // 播放记录
        private List<String> starring; // 明星
        private Integer offline; // 是否下线，0、未下线，1、已下线
        private String category_name;
        private String sub_category;
        private Integer is_end; // 是否完结，0、未完结，1、已完结
        private String platform_now_episodes;
        private String platform_now_video_id;
        private String platform_can_play; // 是否可以播放，0、可以播放，1、不可播放

        public String getGlobal_id() {
            return global_id;
        }

        public void setGlobal_id(String global_id) {
            this.global_id = global_id;
        }

        public Integer getProduct() {
            return this.product;
        }

        public String getProductName() {
            return this.productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setProduct(Integer product) {
            this.product = product;
        }

        public Pic getPic_all() {
            return this.pic_all;
        }

        public void setPic_all(Pic pic_all) {
            this.pic_all = pic_all;
        }

        public Integer getCategory() {
            return this.category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getFavorite_id() {
            return this.favorite_id;
        }

        public void setFavorite_id(String favorite_id) {
            this.favorite_id = favorite_id;
        }

        public String getFavorite_type() {
            return this.favorite_type;
        }

        public void setFavorite_type(String favorite_type) {
            this.favorite_type = favorite_type;
        }

        public String getZt_id() {
            return this.zt_id;
        }

        public void setZt_id(String zt_id) {
            this.zt_id = zt_id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSub_title() {
            return this.sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public TemplateJson getTemplateJson() {
            return this.templateJson;
        }

        public void setTemplateJson(TemplateJson templateJson) {
            this.templateJson = templateJson;
        }

        public String getPlayPlatform() {
            return this.playPlatform;
        }

        public void setPlayPlatform(String playPlatform) {
            this.playPlatform = playPlatform;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getPlay_id() {
            return play_id;
        }

        public void setPlay_id(String play_id) {
            this.play_id = play_id;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public PlayRecord getPlay_record() {
            return play_record;
        }

        public void setPlay_record(PlayRecord play_record) {
            this.play_record = play_record;
        }

        public List<String> getStarring() {
            return starring;
        }

        public void setStarring(List<String> starring) {
            this.starring = starring;
        }

        public Integer getOffline() {
            return offline;
        }

        public void setOffline(Integer offline) {
            this.offline = offline;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getSub_category() {
            return sub_category;
        }

        public void setSub_category(String sub_category) {
            this.sub_category = sub_category;
        }

        public Integer getIs_end() {
            return is_end;
        }

        public void setIs_end(Integer is_end) {
            this.is_end = is_end;
        }

        public String getPlatform_now_episodes() {
            return platform_now_episodes;
        }

        public void setPlatform_now_episodes(String platform_now_episodes) {
            this.platform_now_episodes = platform_now_episodes;
        }

        public String getPlatform_now_video_id() {
            return platform_now_video_id;
        }

        public void setPlatform_now_video_id(String platform_now_video_id) {
            this.platform_now_video_id = platform_now_video_id;
        }

        public String getPlatform_can_play() {
            return platform_can_play;
        }

        public void setPlatform_can_play(String platform_can_play) {
            this.platform_can_play = platform_can_play;
        }
    }

    public static class Pic {

        private String tvPic;
        private String focusMPic;
        private String focusPic;
        private String pictv_542;// 专题收藏使用的入口图，不是专题的海报图

        public String getTvPic() {
            return this.tvPic;
        }

        public void setTvPic(String tvPic) {
            this.tvPic = tvPic;
        }

        public String getFocusMPic() {
            return this.focusMPic;
        }

        public void setFocusMPic(String focusMPic) {
            this.focusMPic = focusMPic;
        }

        public String getFocusPic() {
            return this.focusPic;
        }

        public void setFocusPic(String focusPic) {
            this.focusPic = focusPic;
        }

        public String getPictv_542() {
            return this.pictv_542;
        }

        public void setPictv_542(String pictv_542) {
            this.pictv_542 = pictv_542;
        }

    }

    public static class PlayRecord {
        private String video_id;
        private String video_next_id;
        private String video_current_time;
        private String current_episode;

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getVideo_next_id() {
            return video_next_id;
        }

        public void setVideo_next_id(String video_next_id) {
            this.video_next_id = video_next_id;
        }

        public String getVideo_current_time() {
            return video_current_time;
        }

        public void setVideo_current_time(String video_current_time) {
            this.video_current_time = video_current_time;
        }

        public String getCurrent_episode() {
            return current_episode;
        }

        public void setCurrent_episode(String current_episode) {
            this.current_episode = current_episode;
        }
    }

}
