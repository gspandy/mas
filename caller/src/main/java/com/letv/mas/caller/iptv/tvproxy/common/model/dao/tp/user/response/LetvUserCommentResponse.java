package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;

/**
 * 视频或专辑评论Response
 * @author liudaojie
 */
public class LetvUserCommentResponse {
    private String result;// "200":成功
    private Integer total; // 评论总数
    private Integer replayTotal; // 评论回复总数
    private CommentInfo[] data;// 按时间排列的数据
    private String rule; // ？
    private CommentInfo[] authData;// 当前登录用户自己发的 但是未经审核的评论
    private CommentInfo[] topData;// 置顶评论
    private CommentInfo[] godData;// //热评

    /**
     * 评论信息
     */
    public static class CommentInfo {
        private String _id;// 用户中心id
        private String content;// 评论内容
        private String vtime; // 格式化的时间，这个字段不建议用。最好使用下面的时间戳
        private Long ctime; // 发评论的时间戳
        private String city;
        private String cmtType; // 评论类型cmt：普通评论,img:图片评论,vote:投票评论
        private Integer replaynum;// 回复数
        private Long ip;
        private Integer like;// 评论点赞数
        private String img;
        private Object[] imgPack;
        private Long htime;// 发评论时所对应的视频的时间点
        private String pid; // 专辑id
        private String xid; // 视频id
        private Integer cid; // category id
        private Reply[] replys;// 评论回复列表
        private String voteFlag; // 投票标志
        private Source source;
        private Object[] vote;
        private Integer votenum; // 投票数
        private User user;
        private Boolean isLike;
        private Boolean isVoted;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCmtType() {
            return cmtType;
        }

        public void setCmtType(String cmtType) {
            this.cmtType = cmtType;
        }

        public Integer getReplaynum() {
            return replaynum;
        }

        public void setReplaynum(Integer replaynum) {
            this.replaynum = replaynum;
        }

        public Long getIp() {
            return ip;
        }

        public void setIp(Long ip) {
            this.ip = ip;
        }

        public Integer getLike() {
            return like;
        }

        public void setLike(Integer like) {
            this.like = like;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Object[] getImgPack() {
            return imgPack;
        }

        public void setImgPack(Object[] imgPack) {
            this.imgPack = imgPack;
        }

        public Long getHtime() {
            return htime;
        }

        public void setHtime(Long htime) {
            this.htime = htime;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getXid() {
            return xid;
        }

        public void setXid(String xid) {
            this.xid = xid;
        }

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public Reply[] getReplys() {
            return replys;
        }

        public void setReplys(Reply[] replys) {
            this.replys = replys;
        }

        public String getVoteFlag() {
            return voteFlag;
        }

        public void setVoteFlag(String voteFlag) {
            this.voteFlag = voteFlag;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public Object[] getVote() {
            return vote;
        }

        public void setVote(Object[] vote) {
            this.vote = vote;
        }

        public Integer getVotenum() {
            return votenum;
        }

        public void setVotenum(Integer votenum) {
            this.votenum = votenum;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Boolean getIsLike() {
            return isLike;
        }

        public void setIsLike(Boolean isLike) {
            this.isLike = isLike;
        }

        public Boolean getIsVoted() {
            return isVoted;
        }

        public void setIsVoted(Boolean isVoted) {
            this.isVoted = isVoted;
        }
    }

    /**
     * 评论回复信息
     */
    public static class Reply {
        private String _id;// 用户中心id
        private String commentid; // 评论id
        private String content;// 评论回复内容
        private String vtime; // 格式化的时间，这个字段不建议用。最好使用下面的时间戳
        private Long ctime; // 发评论的时间戳
        private String city;
        private String cmtType; // 评论类型cmt：普通评论,img:图片评论,vote:投票评论
        private String ip;
        private Integer like;// 评论点赞数
        private String pid; // 专辑id
        private String xid; // 视频id
        private Reply[] reply;// 评论回复的回复列表。这个字段为空的时候
                              // 代表这条回复是对评论的回复；不为空的时候代表这条回复是对回复的回复
        private Source source;// 来源
        private User user;
        private Boolean isLike;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCmtType() {
            return cmtType;
        }

        public void setCmtType(String cmtType) {
            this.cmtType = cmtType;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getLike() {
            return like;
        }

        public void setLike(Integer like) {
            this.like = like;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getXid() {
            return xid;
        }

        public void setXid(String xid) {
            this.xid = xid;
        }

        public Reply[] getReply() {
            return reply;
        }

        public void setReply(Reply[] reply) {
            this.reply = reply;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Boolean getIsLike() {
            return isLike;
        }

        public void setIsLike(Boolean isLike) {
            this.isLike = isLike;
        }
    }

    public boolean isSucceed() {
        if (StringUtil.isNotBlank(this.result) && "200".equals(this.result)) {
            return true;
        }
        return false;
    }

    /**
     * 请求来源，见{@link UserTpConstant.COMMIT_SOURCE}
     */
    public static class Source {
        Integer id;
        String detail;
    }

    public static class User {
        private Long uid;
        private String ssouid;
        private String username;
        private String photo;
        private Object[] cooperation;

        public Long getUid() {
            return uid;
        }

        public void setUid(Long uid) {
            this.uid = uid;
        }

        public String getSsouid() {
            return ssouid;
        }

        public void setSsouid(String ssouid) {
            this.ssouid = ssouid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Object[] getCooperation() {
            return cooperation;
        }

        public void setCooperation(Object[] cooperation) {
            this.cooperation = cooperation;
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getReplayTotal() {
        return replayTotal;
    }

    public void setReplayTotal(Integer replayTotal) {
        this.replayTotal = replayTotal;
    }

    public CommentInfo[] getData() {
        return data;
    }

    public void setData(CommentInfo[] data) {
        this.data = data;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public CommentInfo[] getAuthData() {
        return authData;
    }

    public void setAuthData(CommentInfo[] authData) {
        this.authData = authData;
    }

    public CommentInfo[] getTopData() {
        return topData;
    }

    public void setTopData(CommentInfo[] topData) {
        this.topData = topData;
    }

    public CommentInfo[] getGodData() {
        return godData;
    }

    public void setGodData(CommentInfo[] godData) {
        this.godData = godData;
    }
}
