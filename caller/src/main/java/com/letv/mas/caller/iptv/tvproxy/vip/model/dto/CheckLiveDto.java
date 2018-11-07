package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseDto;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response.CheckLiveTpResponse;

/**
 * 直播鉴权DTO，返回针对某一直播，当前用户是否有权播放；当用户有播放权限， 并且直播开始时，返回token信息，用于播放鉴权。
 * @author KevinYi
 */
public class CheckLiveDto {

    /**
     * 是否有直播的播放权限（是否购买过），0--未购买过，无播放权限，1--购买过，有播放权限，2--可以试看，3--试看结束
     */
    private Integer hasPlayPermission;

    /**
     * token信息，播放鉴权时使用；注意，该值不一定返回；当用户购买成功，但直播未开始时，该值可能不返回
     */
    private String token;

    /**
     * 直播token开始时间（单位为秒）
     */
    private Long prestart;

    /**
     * 直播状态，-1--获取直播状态失败，异常；1--未开始；2--进行中；3--已结束；4--延期；5--取消。
     */
    private Integer liveStatus;

    /**
     * 直播token结束时间
     */
    private Long preend;

    /**
     * 会员登录信息，播放鉴权使用；非必填，token有值时，本字段也不一定有值
     */
    private String uinfo;

    /**
     * 是否直播试看鉴权成功，true--是直播试看；false--正常鉴权
     */
    private Boolean isPre;

    /**
     * 服务器当前时间（单位为秒）
     */
    private Long curTime;

    /**
     * 鉴权通过所使用的券类型（1--直播券；3--赛季包；4--球队包）
     */
    private Integer ordertype;

    /**
     * 鉴权通过所使用的券类型是球队包时（ordertype=4）返回购买球队的名称
     */
    private String teamName;

    /**
     * 试看文案
     */
    private String tryPlayText;

    /**
     * 试看时间
     */
    private Integer tryPlayTime;

    /**
     * 试看剩余时间
     */
    private Integer tryPlayTimeLeft;

    /**
     * 参数校验失败返回的信息
     */
    private CheckLiveErrorDto error;

    /**
     * 鉴权失败返回的信息
     */
    private CheckLiveInfoDto info;

    /**
     * 直播鉴权时，重新获取播放流地址
     */
    private String playUrl;

    public Integer getHasPlayPermission() {
        return this.hasPlayPermission;
    }

    public void setHasPlayPermission(Integer hasPlayPermission) {
        this.hasPlayPermission = hasPlayPermission;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUinfo() {
        return this.uinfo;
    }

    public void setUinfo(String uinfo) {
        this.uinfo = uinfo;
    }

    public Long getPrestart() {
        return this.prestart;
    }

    public void setPrestart(Long prestart) {
        this.prestart = prestart;
    }

    public Integer getLiveStatus() {
        return this.liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Long getPreend() {
        return this.preend;
    }

    public void setPreend(Long preend) {
        this.preend = preend;
    }

    public Boolean getIsPre() {
        return this.isPre;
    }

    public void setIsPre(Boolean isPre) {
        this.isPre = isPre;
    }

    public Long getCurTime() {
        return this.curTime;
    }

    public void setCurTime(Long curTime) {
        this.curTime = curTime;
    }

    public Integer getOrdertype() {
        return this.ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTryPlayText() {
        return this.tryPlayText;
    }

    public void setTryPlayText(String tryPlayText) {
        this.tryPlayText = tryPlayText;
    }

    public Integer getTryPlayTime() {
        return this.tryPlayTime;
    }

    public void setTryPlayTime(Integer tryPlayTime) {
        this.tryPlayTime = tryPlayTime;
    }

    public Integer getTryPlayTimeLeft() {
        return this.tryPlayTimeLeft;
    }

    public void setTryPlayTimeLeft(Integer tryPlayTimeLeft) {
        this.tryPlayTimeLeft = tryPlayTimeLeft;
    }

    public CheckLiveErrorDto getError() {
        return this.error;
    }

    public void setError(CheckLiveErrorDto error) {
        this.error = error;
    }

    public CheckLiveInfoDto getInfo() {
        return this.info;
    }

    public void setInfo(CheckLiveInfoDto info) {
        this.info = info;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public static class CheckLiveErrorDto extends BaseDto {
        /**
         * 错误编码，1003--参数校验类，1002--直播信息不存在，1001--直播时间为空，1005--用户被封禁
         */
        private String code;

        /**
         * 错误描述
         */
        private String msg;

        public void setCheckLiveErrorValue(CheckLiveTpResponse.CheckLiveError error) {
            this.code = error.getCode();
            this.msg = error.getMsg();
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

    }

    public static class CheckLiveInfoDto extends CheckLiveErrorDto {

        /**
         * 直播券数量（1004错误时，必须返回券数量）
         */
        private Integer count;

        /**
         * 直播状态（只在错误码1004，1011，1012，1013必须返回）<br/>
         * -1--获取直播状态失败，异常；1--未开始；2--进行中；3--已结束；4--延期；5--取消。
         */
        private Integer liveStatus;

        /**
         * 队伍包信息（如果购买多包，则返回一个）
         */
        private String team;

        /**
         * 赛季信息
         */
        private String season;

        /**
         * 服务器时间（单位为秒）
         */
        private Long curTime;

        /**
         * 直播的付费类型（音乐类型，1004，直播鉴权未通过时返回）<br/>
         * 1--包年以上会员免费，2--包年以上或单点免费，3--会员免费，4--会员或单点免费，5--单点
         */
        private Integer payType;

        public void setCheckLiveInfoValue(CheckLiveTpResponse.CheckLiveInfo info) {
            super.code = info.getCode();
            super.msg = info.getMsg();
            this.count = info.getCount();
            this.curTime = info.getCurTime();
            this.liveStatus = info.getLiveStatus();
            this.payType = info.getPayType();
            this.season = info.getSeason();
            this.team = info.getTeam();
        }

        public Integer getCount() {
            return this.count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getLiveStatus() {
            return this.liveStatus;
        }

        public void setLiveStatus(Integer liveStatus) {
            this.liveStatus = liveStatus;
        }

        public String getTeam() {
            return this.team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getSeason() {
            return this.season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public Long getCurTime() {
            return this.curTime;
        }

        public void setCurTime(Long curTime) {
            this.curTime = curTime;
        }

        public Integer getPayType() {
            return this.payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
        }

    }

    /**
     * 根据试看开始、结束和服务器时间，初始化试看信息
     */
    public String initTryPlayInfo() {
        if (this.hasPlayPermission != 2) {// 不是试看，不用处理
            return " This is not try play, hasPlayPermission[" + this.hasPlayPermission + "]";
        }
        if (this.prestart != null && this.preend != null && this.curTime != null) {
            if (this.preend < this.prestart || this.preend < this.curTime) {
                // 时间非法，也不做处理
                return " The preend time is illegal, preend[" + this.preend + "],prestart[" + this.prestart
                        + "], curTime[" + this.curTime + "]";
            }
            Long tryTime = this.preend - this.prestart;
            Long tryTimeLeft = this.preend - this.curTime;
            Integer tryPlayTime = tryTime.intValue();
            Integer tryPlayTimeLeft = tryTimeLeft.intValue();
            this.tryPlayTime = tryPlayTime;// 试看时间
            this.tryPlayTimeLeft = tryPlayTimeLeft;// 试看剩余时间
            /*
             * int m = tryPlayTime / 60;
             * int s = tryPlayTime % 60;
             * StringBuilder sb = new StringBuilder();
             * if (m != 0) {
             * if (s != 0) {
             * sb.append("免费试看").append(m).append("分钟").append(s).append("秒");
             * } else {
             * sb.append("免费试看").append(m).append("分钟");
             * }
             * } else {
             * if (s != 0) {
             * sb.append("免费试看").append(s).append("秒");
             * }
             * }
             * this.tryPlayText = sb.toString();
             */
        }
        return " initTryPlayInfo success.";
    }
}
