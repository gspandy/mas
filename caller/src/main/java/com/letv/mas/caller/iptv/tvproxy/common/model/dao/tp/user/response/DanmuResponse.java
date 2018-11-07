package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.user.response;

import java.io.Serializable;

public class DanmuResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4240364580830686586L;

    private String uid;

    private Double start;

    private String txt;

    private String color;

    private String font;

    private String type;

    private String x;

    private String y;

    private String position;

    private Long addtime;

    private Integer vip;

    private DanmuExtendResponse extend;

    /**
     * 兼容字段
     */
    @Deprecated
    private String content;

    @Deprecated
    private long playTime;

    @Deprecated
    private Integer role;

    @Deprecated
    private String nickname;

    @Deprecated
    private String pic;

    /**
     * 弹幕扩展信息
     * @author KevinYi
     */
    public static class DanmuExtendResponse implements Serializable {

        /**
         * 
         */
        private static final long serialVersionUID = 3995842746364234406L;

        private Integer role;

        private String nickname;

        private String picture;

        public Integer getRole() {
            return this.role;
        }

        public void setRole(Integer role) {
            this.role = role;
        }

        public String getNickname() {
            return this.nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPicture() {
            return this.picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Double getStart() {
        return this.start;
    }

    public void setStart(Double start) {
        this.start = start;
        this.playTime = Math.round(start);
    }

    public String getTxt() {
        return this.txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
        this.content = txt;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFont() {
        return this.font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getX() {
        return this.x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return this.y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getAddtime() {
        return this.addtime;
    }

    public void setAddtime(Long addtime) {
        this.addtime = addtime;
    }

    public Integer getVip() {
        return this.vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }

    public DanmuExtendResponse getExtend() {
        return this.extend;
    }

    public void setExtend(DanmuExtendResponse extend) {
        this.extend = extend;
        if (extend != null) {
            this.role = extend.getRole();
            this.nickname = extend.getNickname();
            this.pic = extend.getPicture();
        }
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getPlayTime() {
        return playTime;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
