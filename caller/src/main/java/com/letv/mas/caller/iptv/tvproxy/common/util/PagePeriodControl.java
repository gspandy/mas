package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.util.ArrayList;
import java.util.List;

public class PagePeriodControl<T> {

    private static int PAGESIZE = 20;
    /**
     * 每页对象数
     */
    private int pageSize = PAGESIZE;
    /**
     * 页数
     */
    private int pageCount = 0;
    /**
     * 对象数
     */
    private int count = 0;
    /**
     * 当前页
     */
    private int currentPage = 1;
    /**
     * 当前页开始的记录的位置
     */
    private int begin = 0;
    /**
     * 当前页结束的记录的位置
     */
    private int end = 0;
    /**
     * 结果
     */
    private T items;
    private String appendParams = "";
    /**
     * 当前快近所在页面
     */
    private int currentSkip = 1;
    private int skipSize = 5;

    public PagePeriodControl() {
    }

    public PagePeriodControl(int defaultPageSize) {
        this.pageSize = defaultPageSize;
    }

    public PagePeriodControl(int defaultPageSize, int page) {
        this.pageSize = defaultPageSize;
        this.currentPage = page;
    }

    public PagePeriodControl(int defaultPageSize, int page, int count) {
        this.pageSize = defaultPageSize;
        this.currentPage = page;
        setCount(count);
    }

    /**
     * 返回当前页
     * @return 当前页
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * 得到页数
     * @return　页数
     */
    public int getPageCount() {
        return this.pageCount;
    }

    /**
     * 得到每页记录条数
     * @return 每页记录条数
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 得到记录条数
     * @return 记录条数
     */
    public int getCount() {
        return this.count;
    }

    /**
     * 得到当前页的记录开始位置
     * @return 开始位置
     */
    public int getBegin() {
        return this.begin;
    }

    /**
     * 得到记录的结束位置
     * @return 结束位置
     */
    public int getEnd() {
        return this.end;
    }

    public T getItems() {

        return this.items;
    }

    public String getAppendParams() {
        return this.appendParams;
    }

    /**
     * 设置记录条数
     * @param totalCount
     *            int
     */
    public void setCount(int totalCount) {
        if (totalCount <= 0) {
            return;
        }
        this.count = totalCount;
        this.pageCount = totalCount / this.pageSize + ((totalCount % this.pageSize == 0) ? 0 : 1);
        if (this.currentPage > this.pageCount) {
            this.currentPage = this.pageCount;
        }
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }

        this.begin = (this.currentPage - 1) * this.pageSize;
        this.end = this.currentPage * this.pageSize;
        if (this.end >= totalCount) {
            this.end = totalCount;
        }

        this.currentSkip = (this.currentPage / this.skipSize) * this.skipSize + 1;
        if (this.currentPage % this.skipSize == 0) {
            this.currentSkip = this.currentSkip - this.skipSize;
        }

    }

    /**
     * 是否可以到第一页
     * @return boolean
     */
    public boolean getCanGoFirst() {
        return (this.currentPage > 1);
    }

    /**
     * 是否可以到前一页
     * @return boolean
     */
    public boolean getCanGoPrevious() {
        return (this.currentPage > 1);
    }

    /**
     * 是否可以到下一页
     * @return boolean
     */
    public boolean getCanGoNext() {
        return (this.currentPage < this.pageCount);
    }

    /**
     * 是否可以到最后一页
     * @return boolean
     */
    public boolean getCanGoLast() {
        return (this.currentPage < this.pageCount);
    }

    /**
     * 得到前一页页码
     * @return int
     */
    public int getPrevious() {
        if (this.currentPage > 1) {
            return this.currentPage - 1;
        } else {
            return 1;
        }
    }

    /**
     * 得到下一页页码
     * @return int
     */
    public int getNext() {
        if (this.currentPage < this.pageCount) {
            return this.currentPage + 1;
        } else {
            return this.pageCount;
        }
    }

    /**
     * 填充对象
     * @param objectList
     *            List
     */
    public void setAll(List<T> objectList) {
        if (objectList != null) {
            this.setCount(objectList.size());
        } else {
            this.setCount(0);
        }
    }

    /**
     * @param list
     *            List
     */
    public void setItems(T items) {
        this.items = items;
    }

    public void setAppendParams(String appendParams) {
        this.appendParams = appendParams;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 是否可以向前快进
     * @return boolean
     */
    public boolean getCanSkipPrevious() {
        return getSkipPrevious() > 0;
    }

    /**
     * 得到向前快近的页码
     * @return int
     */
    public int getSkipPrevious() {
        return this.currentSkip - this.skipSize;
    }

    /**
     * 是否可以向后快进
     * @return boolean
     */
    public boolean getCanSkipNext() {
        return (getSkipNext() <= this.pageCount);
    }

    /**
     * 得到向后快近的页码
     * @return int
     */
    public int getSkipNext() {
        return this.currentSkip + this.skipSize;
    }

    /**
     * 得到当前显示的页码
     * @return int[]
     */
    public int[] getCurrentSkipPageNumbers() {
        int n = this.skipSize;
        if (this.currentSkip + this.skipSize > this.pageCount) {
            n = this.pageCount - this.currentSkip + 1;
        }
        int[] Result = new int[n];
        for (int i = 0; i < n; i++) {
            Result[i] = this.currentSkip + i;
        }
        return Result;
    }

    public void reCount(int page, int count) {
        this.currentPage = page;
        this.setCount(count);
    }

    /**
     * @return the currentSkip
     */
    public int getCurrentSkip() {
        return this.currentSkip;
    }

    /**
     * @param currentSkip
     *            the currentSkip to set
     */
    public void setCurrentSkip(int currentSkip) {
        this.currentSkip = currentSkip;
    }

    /**
     * @return the skipSize
     */
    public int getSkipSize() {
        return this.skipSize;
    }

    /**
     * @param skipSize
     *            the skipSize to set
     */
    public void setSkipSize(int skipSize) {
        this.skipSize = skipSize;
    }

    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @param pageCount
     *            the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * @param begin
     *            the begin to set
     */
    public void setBegin(int begin) {
        this.begin = begin;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEnd(int end) {
        this.end = end;
    }

}
