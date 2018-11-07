package com.letv.mas.caller.iptv.tvproxy.common.util;

public class PageUtil {

    public static PageEntry[] resolvePage(int oldPage, int oldPageSize, int newPageSize) {
        int startPage = (oldPage - 1) * oldPageSize / newPageSize + 1;
        int endPage = oldPage * oldPageSize / newPageSize;
        PageEntry[] pageEntry = new PageEntry[oldPageSize / newPageSize];
        for (int index = 0, i = startPage; i <= endPage; i++, index++) {
            pageEntry[index] = new PageEntry(i, newPageSize);
        }
        return pageEntry;
    }

    public static class PageEntry {
        private int page;
        private int pageSize;

        public PageEntry(int page, int pageSize) {
            this.page = page;
            this.pageSize = pageSize;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

    }

}
