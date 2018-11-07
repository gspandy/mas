package com.letv.mas.caller.iptv.tvproxy.search.model.bean.bo;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.SearchCategoryDto2;
import com.letv.mas.caller.iptv.tvproxy.search.model.dto.SearchLiveDto;

import java.util.ArrayList;
import java.util.List;

public class SearchPageResponse<T> extends BaseResponse {
    private int pageSize; // 每页记录数
    private int pageCount; // 总页数
    private int currentPage; // 当前页的页码
    private int count; // 总记录数
    private int previousPage; // 上一页的页码
    private int nextPage; // 下一页的页码
    private List<T> items = new ArrayList<T>(); // 当前页的数据
    private List<SearchCategoryDto2> category_list;// 分类情况
    private int live_count;
    private DataReport dataReport;
    private boolean isLegal = false;// 默认false 防止搜索有问题

    /**
     * 搜索部门的live_list是走的人工维护的数据
     * liveList作为搜索的一种数据类型，与专辑，视频，明星，专题等价的直播类型数据
     * liveList中包含live_list中的数据，后期会逐渐废弃掉live_list
     */
    private List<SearchLiveDto> liveList;

    public SearchPageResponse() {
    }

    public static class DataReport {
        private String eid;
        private String experimentId;
        private String isTrigger;
        private String area;
        private String reid;
        private String bucket;

        public DataReport(String eid, String experimentId, String isTrigger) {
            this.eid = eid;
            this.experimentId = experimentId;
            this.isTrigger = isTrigger;
        }

        public DataReport(String eid, String experimentId, String isTrigger, String area, String reid, String bucket) {
            this.eid = eid;
            this.experimentId = experimentId;
            this.isTrigger = isTrigger;
            this.area = area;
            this.reid = reid;
            this.bucket = bucket;
        }

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

        public String getEid() {
            return this.eid;
        }

        public void setEid(String eid) {
            this.eid = eid;
        }

        public String getExperimentId() {
            return this.experimentId;
        }

        public void setExperimentId(String experimentId) {
            this.experimentId = experimentId;
        }

        public String getIsTrigger() {
            return this.isTrigger;
        }

        public void setIsTrigger(String isTrigger) {
            this.isTrigger = isTrigger;
        }

    }

    public SearchPageResponse(List<T> items) {
        this.setItems(items);
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        if (count <= 0) {
            return;
        }
        this.count = count;
        if (this.pageSize > 0) {
            this.pageCount = count / this.pageSize + ((count % this.pageSize == 0) ? 0 : 1);

            if (this.currentPage > this.pageCount) {
                this.currentPage = this.pageCount;
            }
            if (this.currentPage <= 0) {
                this.currentPage = 1;
            }
        }
    }

    public int getLive_count() {
        return this.live_count;
    }

    public void setLive_count(int live_count) {
        this.live_count = live_count;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return this.pageCount < 0 ? 0 : this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getCurrentPage() {
        if (this.currentPage > this.pageCount) {
            this.currentPage = this.pageCount;
        }
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPreviousPage() {
        int prePage = this.currentPage - 1;
        if (prePage > this.pageCount) {
            prePage = this.pageCount;
        }
        if (prePage <= 0) {
            prePage = 1;
        }

        return prePage;
    }

    public int getNextPage() {
        int nextPage = this.currentPage + 1;
        if (nextPage > this.pageCount) {
            nextPage = this.pageCount;
        }
        if (nextPage <= 0) {
            nextPage = 1;
        }
        return nextPage;
    }

    public List<SearchLiveDto> getLiveList() {
        return this.liveList;
    }

    public void setLiveList(List<SearchLiveDto> liveList) {
        this.liveList = liveList;
    }

    public List<SearchCategoryDto2> getCategory_list() {
        return this.category_list;
    }

    public void setCategory_list(List<SearchCategoryDto2> category_list) {
        this.category_list = category_list;
    }

    public DataReport getDataReport() {
        return this.dataReport;
    }

    public void setDataReport(DataReport dataReport) {
        this.dataReport = dataReport;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public void setLegal(boolean isLegal) {
        this.isLegal = isLegal;
    }

}
