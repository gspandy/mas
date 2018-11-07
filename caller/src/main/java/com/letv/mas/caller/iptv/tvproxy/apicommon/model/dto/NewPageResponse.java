package com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto;

import java.util.Collection;

public class NewPageResponse<T> extends NewBaseResponse {

    private Integer count;// 总数
    private Integer pageCount;// 总页数
    private Integer currentPage;// 当前页数
    private Integer nextPage;// 下一页
    private Integer prePage;// 上一页
    private Integer pageSize;// 每页大小
    private Collection<T> data;// 数据列表

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public Integer getPrePage() {
        return prePage;
    }

    public void setPrePage(Integer prePage) {
        this.prePage = prePage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    /**
     * 用于计算上一页和下一页信息，在得知其他分页信息后调用
     */
    public void caculatePageInfo() {
        if (currentPage != null) {
            if (pageCount != null && currentPage < pageCount.intValue()) {
                nextPage = currentPage + 1;
            } else {
                nextPage = currentPage;
            }
            if (currentPage > 1) {
                prePage = currentPage - 1;
            } else {
                prePage = 1;
            }
        }
    }
}
