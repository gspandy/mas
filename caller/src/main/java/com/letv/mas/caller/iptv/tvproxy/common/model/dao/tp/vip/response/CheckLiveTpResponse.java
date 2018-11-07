package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.response;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.VipTpConstantUtils;

/**
 * 直播鉴权第三方接口响应封装类
 * @author KevinYi
 */
public class CheckLiveTpResponse {

    /**
     * 响应状态吗取值返回
     */
    public static String STATUS_SUCCEED = VipTpConstant.TP_STATTUS_CODE_1;
    public static String STATUS_FAILURE = VipTpConstant.TP_STATTUS_CODE_0;

    private String status; // 返回结果，0--请求失败，1--请求成功，有播放权限
    private String token; // token信息，播放鉴权时使用；该值只有当用户已购买，且直播开始时返回
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
    private String uinfo; // 会员登录信息，播放鉴权使用；非必填，token有值时，本字段也不一定有值
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

    private CheckLiveError error; // 请求失败，status为0，返回错误信息，与info不回同时返回
    private CheckLiveInfo info; // 鉴权结果为无权播放，status为0，返回鉴权具体信息，与error不回同时返回

    /**
     * 是否响应失败
     * @return
     */
    public boolean getFailure() {
        return this.status == null || !STATUS_SUCCEED.equals(this.status) || this.error != null;
    }

    /**
     * 需要用券的判断条件依据：鉴权失败且失败原因是没有权限
     * @return
     */
    public boolean needActiveTicket() {
        // 鉴权接口返回失败且info中code为1004
        if (VipTpConstant.TP_STATTUS_CODE_0.equals(this.status) && this.error == null) {
            if (this.info != null && "1004".equals(this.info.getCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有播放直播的权限；
     * 判断标准为：响应状态码status为SUCCEED，或用户已经买过
     * @return
     */
    public boolean hasLivePlayPermission() {
        if (this.status == null) {
            return false;
        }
        if (STATUS_SUCCEED.equals(this.status)) {
            return true;
        }
        if (STATUS_FAILURE.equals(this.status) && this.error == null && this.info != null
                && VipTpConstantUtils.checkLiveHasBounghtByCode(this.info.getCode())) {
            // TODO 注意是否需要修改checkLiveHasBounghtByCode方式鉴权，改为仅鉴别1013的情况
            return true;
        }
        return false;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public CheckLiveError getError() {
        return this.error;
    }

    public void setError(CheckLiveError error) {
        this.error = error;
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

    public CheckLiveInfo getInfo() {
        return this.info;
    }

    public void setInfo(CheckLiveInfo info) {
        this.info = info;
    }

    /**
     * 请求失败时返回的错误信息
     * 返回内容有：
     * 1003--参数校验类失败
     * 1002--直播信息不存在
     * 1001--直播时间为空
     */
    public static class CheckLiveError {
        private String code; // 错误码
        private String msg; // 错误描述

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

    /**
     * 校验结果为无权播放时返回的鉴权信息
     * 返回内容有：
     * 1004--用户没有权限
     * 1011--赛季包鉴权成功，但直播未开始
     * 1012--球队包鉴权成功，但直播未开始
     * 1013--场次卷鉴权成功，但直播未开始
     * @author KevinYi
     */
    public static class CheckLiveInfo extends CheckLiveError {

        private Integer count; // 直播券数量
        /**
         * 直播状态（只在错误码1004，1011，1012，1013必须返回）<br/>
         * -1--获取直播状态失败，异常；1--未开始；2--进行中；3--已结束；4--延期；5--取消。
         */
        private Integer liveStatus;
        private String team; // 队伍包信息（如果购买多包，则返回一个）
        private String season; // 返回赛季信息
        /**
         * 服务器时间（单位为秒）
         */
        private Long curTime;

        /**
         * 直播的付费类型（音乐类型，1004，直播鉴权未通过时返回）<br/>
         * 1--包年以上会员免费，2--包年以上或单点免费，3--会员免费，4--会员或单点免费，5--单点
         */
        private Integer payType;

        public Integer getCount() {
            return this.count;
        }

        public void setCount(Integer count) {
            this.count = count;
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

        public Integer getLiveStatus() {
            return this.liveStatus;
        }

        public void setLiveStatus(Integer liveStatus) {
            this.liveStatus = liveStatus;
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
}
