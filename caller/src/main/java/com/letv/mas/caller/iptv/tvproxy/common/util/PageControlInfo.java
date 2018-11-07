package com.letv.mas.caller.iptv.tvproxy.common.util;

import java.util.ArrayList;
import java.util.List;

public class PageControlInfo<T> {

    private int pageSize; // 每页记录数
    private int pageCount; // 总页数
    private int currentPage; // 当前页的页码
    private int count; // 总记录数
    private int previousPage; // 上一页的页码
    private int nextPage; // 下一页的页码
    private List<T> items = new ArrayList<T>(); // 当前页的数据

    public PageControlInfo() {
    }

    public PageControlInfo(PageControl<T> pc) {
        if (pc != null) {
            if (pc.getList() != null) {
                this.setItems(pc.getList());
            }
            this.setCount(pc.getCount());
            this.setCurrentPage(pc.getCurrentPage());
            this.setNextPage(pc.getNext());
            this.setPageSize(pc.getPageSize());
            this.setPreviousPage(pc.getPrevious());
            this.setPageCount(pc.getPageCount());
        }
    }

    /**
     * 每页记录数
     * @return the pageSize
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * 每页记录数
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 总页数
     * @return the pageCount
     */
    public int getPageCount() {
        return this.pageCount;
    }

    /**
     * 总页数
     * @param pageCount
     *            the pageCount to set
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 当前页的页码
     * @return the currentPage
     */
    public int getCurrentPage() {
        return this.currentPage;
    }

    /**
     * 当前页的页码
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 总记录数
     * @return the count
     */
    public int getCount() {
        return this.count;
    }

    /**
     * 总记录数
     * @param count
     *            the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 上一页的页码
     * @return the previousPage
     */
    public int getPreviousPage() {
        return this.previousPage;
    }

    /**
     * 上一页的页码
     * @param previousPage
     *            the previousPage to set
     */
    public void setPreviousPage(int previousPage) {
        this.previousPage = previousPage;
    }

    /**
     * 下一页的页码
     * @return the nextPage
     */
    public int getNextPage() {
        return this.nextPage;
    }

    /**
     * 下一页的页码
     * @param nextPage
     *            the nextPage to set
     */
    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * 当前页的数据
     * @return the items
     */
    public List<T> getItems() {
        return this.items;
    }

    /**
     * 当前页的数据
     * @param items
     *            the items to set
     */
    public void setItems(List<T> items) {
        this.items = items;
    }
}
