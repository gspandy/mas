package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.util.List;

public class FollowListTp {
    private Integer page; // 当前页
    private Integer count; // 总数
    private Integer pagecount; // 每页数量
    private List<FollowInfo> list;

    public static class FollowInfo {
        private String follow_id;
        private Long time;

        public String getFollow_id() {
            return follow_id;
        }

        public void setFollow_id(String follow_id) {
            this.follow_id = follow_id;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public List<FollowInfo> getList() {
        return list;
    }

    public void setList(List<FollowInfo> list) {
        this.list = list;
    }
}
