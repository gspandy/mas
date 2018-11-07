package com.letv.mas.caller.iptv.tvproxy.user.model.dto;

import java.util.List;

public class UserCommentDto {
    private Integer totalCount;// 评论总数
    private Integer currentIndex; // 当前页数
    private Integer nextIndex;// 下一页数
    private Integer pageSize;// 每页数据量
    private List<CommentInfo> commentList;// 按时间排列的数据
    private List<CommentInfo> hotCommentList;// 按时间排列的数据

    public void setPageInfo(Integer totalCount, Integer currentIndex, Integer pageSize) {
        this.totalCount = totalCount;
        this.currentIndex = currentIndex;
        this.pageSize = pageSize;
        if (pageSize != null && totalCount != null && currentIndex != null) {
            if (pageSize * currentIndex < totalCount) {
                this.nextIndex = currentIndex + 1;
            } else {
                this.nextIndex = 1;
            }
        }
    }

    /**
     * 评论信息
     */
    public static class CommentInfo {
        private String id;// 用户中心id
        private String content;// 评论内容
        private String vtime; // 格式化的时间，这个字段不建议用。最好使用下面的时间戳
        private Long ctime; // 发评论的时间戳
        private Integer like;// 评论点赞数
        private UserInfo user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getVtime() {
            return vtime;
        }

        public void setVtime(String vtime) {
            this.vtime = vtime;
        }

        public Long getCtime() {
            return ctime;
        }

        public void setCtime(Long ctime) {
            this.ctime = ctime;
        }

        public Integer getLike() {
            return like;
        }

        public void setLike(Integer like) {
            this.like = like;
        }

        public UserInfo getUser() {
            return user;
        }

        public void setUser(UserInfo user) {
            this.user = user;
        }
    }

    public static class UserInfo {
        private Long uid;
        private String username;
        private String picture;

        public Long getUid() {
            return uid;
        }

        public void setUid(Long uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }
    }

    public List<CommentInfo> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentInfo> commentList) {
        this.commentList = commentList;
    }

    public List<CommentInfo> getHotCommentList() {
        return hotCommentList;
    }

    public void setHotCommentList(List<CommentInfo> hotCommentList) {
        this.hotCommentList = hotCommentList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Integer getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(Integer nextIndex) {
        this.nextIndex = nextIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
