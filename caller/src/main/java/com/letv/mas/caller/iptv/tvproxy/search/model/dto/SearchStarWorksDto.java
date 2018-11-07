package com.letv.mas.caller.iptv.tvproxy.search.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.StarWorksTpResponse;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.search.response.StarWorksTpResponse.VideoList;

import java.util.List;

public class SearchStarWorksDto extends BaseDto {
    private static final long serialVersionUID = 2978364491133932120L;
    private Integer category;
    private String category_name;
    private String dataType;
    private String count;
    private StarWorks dataList;

    public Integer getCategory() {
        return this.category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getCount() {
        return this.count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public StarWorks getDataList() {
        return this.dataList;
    }

    public void setDataList(StarWorks dataList) {
        this.dataList = dataList;
    }

    public static class StarWorks {
        private List<VideoList> video_list;
        private List<StarWorksTpResponse.AlbumList> album_list;

        public List<VideoList> getVideo_list() {
            return this.video_list;
        }

        public void setVideo_list(List<VideoList> video_list) {
            this.video_list = video_list;
        }

        public List<StarWorksTpResponse.AlbumList> getAlbum_list() {
            return this.album_list;
        }

        public void setAlbum_list(List<StarWorksTpResponse.AlbumList> album_list) {
            this.album_list = album_list;
        }
    }
}
