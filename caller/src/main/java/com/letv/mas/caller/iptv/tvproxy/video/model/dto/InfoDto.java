package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import java.io.Serializable;

public class InfoDto implements Serializable {

    // 信息体
    private PicInfo picInfo;
    private PicExtend picExtend;

    public static class PicInfo {
        private String left;// 左上角
        private String right;// 右上角
        private int rt;// "1"时长；"2"得分;"0"没有
        private String top;
        private String bottom;

        public PicInfo() {
            this.left = "";// 专辑正片/非正片
            this.right = "";
            this.top = "";
            this.bottom = "";
            this.rt = 0;
        }

        public String getLeft() {
            return this.left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return this.right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getTop() {
            return this.top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getBottom() {
            return this.bottom;
        }

        public void setBottom(String bottom) {
            this.bottom = bottom;
        }

        public int getRt() {
            return this.rt;
        }

        public void setRt(int rt) {
            this.rt = rt;
        }

    }

    public static class PicExtend {
        private String left;
        private String right;
        private String top;
        private String bottom;

        public PicExtend() {
        }

        public String getTop() {
            return this.top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getLeft() {
            return this.left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public String getRight() {
            return this.right;
        }

        public void setRight(String right) {
            this.right = right;
        }

        public String getBottom() {
            return this.bottom;
        }

        public void setBottom(String bottom) {
            this.bottom = bottom;
        }

    }

    public PicInfo getPicInfo() {
        return this.picInfo;
    }

    public void setPicInfo(PicInfo picInfo) {
        this.picInfo = picInfo;
    }

    public PicExtend getPicExtend() {
        return this.picExtend;
    }

    public void setPicExtend(PicExtend picExtend) {
        this.picExtend = picExtend;
    }

}
