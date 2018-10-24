package com.letv.mas.router.iptv.tvproxy.model.dto;

import java.io.Serializable;

/**
 * Created by leeco on 18/10/8.
 */
public class DingRobotDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = -2900274680917431452L;

    /**
     * 消息类型:text | link | markdown
     */
    private String msgtype;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * [text]类型支持，被@人的手机号，多个用逗号分割
     * [markdown]类型支持，被@人的手机号(在content内容里要有@手机号)
     */
    private String atMobiles;

    /**
     * [text]类型支持，@所有人时:true,否则为:false
     */
    private boolean isAtAll;

    /**
     * [link]类型支持，点击消息跳转的URL
     */
    private String link;

    /**
     * [link]类型支持，图片URL
     */
    private String picUrl;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(String atMobiles) {
        this.atMobiles = atMobiles;
    }

    public boolean isAtAll() {
        return isAtAll;
    }

    public void setAtAll(boolean isAtAll) {
        this.isAtAll = isAtAll;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
