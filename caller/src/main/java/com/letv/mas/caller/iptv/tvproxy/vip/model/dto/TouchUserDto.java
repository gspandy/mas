package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;

/**
 * 手机校验DTO
 * @author yikun
 */
public class TouchUserDto extends BaseDto {

    private String img; // 图片
    private String title; // 标题
    private String content; // 内容
    private Integer status; // 状态 1为正常参加活动(int)
    private String button; // 按钮内容
    private String pushid; // 上报id
    private Integer jump_type;// 不跳转为0 专辑跳转为1 视频跳转2 H5跳转3（int）
    private String album_id;// 跳转返回值
    private String back_title;// 退出标题
    private String back_content;// 退出内容
    private String back_img;// 退出图片

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    public Integer getJump_type() {
        return jump_type;
    }

    public void setJump_type(Integer jump_type) {
        this.jump_type = jump_type;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getBack_title() {
        return back_title;
    }

    public void setBack_title(String back_title) {
        this.back_title = back_title;
    }

    public String getBack_content() {
        return back_content;
    }

    public void setBack_content(String back_content) {
        this.back_content = back_content;
    }

    public String getBack_img() {
        return back_img;
    }

    public void setBack_img(String back_img) {
        this.back_img = back_img;
    }

}
