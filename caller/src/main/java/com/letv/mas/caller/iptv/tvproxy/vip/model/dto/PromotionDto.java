package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "PromotionDto", description = "观星活动类")
public class PromotionDto extends BaseData {
    private static final long serialVersionUID = -4508041989961580434L;

    @ApiModelProperty(value = "错误码，成功时为10000")
    private String errno;// 错误码，成功时为10000

    @ApiModelProperty(value = "推广位置")
    private String position;// 推广位置

    @ApiModelProperty(value = "活动失效时间")
    private Long activity_timeout;// 活动失效时间

    @ApiModelProperty(value = "当前时间")
    private Long nowTime;// 当前时间

    @ApiModelProperty(value = "推广位活动ID")
    private String activity_id;// 推广位活动ID

    @ApiModelProperty(value = "推广位活动频次")
    private Integer rate;// 推广位活动频次

    @ApiModelProperty(value = "本次请求id,观星分配")
    private String reqid;// 本次请求id,观星分配

    @ApiModelProperty(value = "本次交互所属会话id")
    private String sessionid;// 本次交互所属会话id

    @ApiModelProperty(value = "投放的素材id")
    private String creativeid;// 投放的素材id

    /**
     * 运营位展示字段（图片、文案、按钮）
     */
    @ApiModelProperty(value = "图片地址")
    private String img;// 图片地址
    @ApiModelProperty(value = "视频地址")
    private String video;// 视频地址
    @ApiModelProperty(value = "标题")
    private String title;// 标题
    @ApiModelProperty(value = "副标题")
    private String subTitle;// 副标题
    @ApiModelProperty(value = "按钮列表")
    private List<ButtonDto> buttons;// 按钮列表
    @ApiModelProperty(value = "支持一个位置上多个素材，譬如首页")
    private List<PromotionDto> promotions;

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * 上报信息
     */
    @ApiModelProperty(value = "上报信息")
    private ReportData report;

    @ApiModelProperty(value = "展示相关的配置参数")
    private ShowConfig config;

    public List<PromotionDto> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionDto> promotions) {
        this.promotions = promotions;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getActivity_timeout() {
        return activity_timeout;
    }

    public void setActivity_timeout(Long activity_timeout) {
        this.activity_timeout = activity_timeout;
    }

    public Long getNowTime() {
        return nowTime;
    }

    public void setNowTime(Long nowTime) {
        this.nowTime = nowTime;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<ButtonDto> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonDto> buttons) {
        this.buttons = buttons;
    }

    public ReportData getReport() {
        return report;
    }

    public void setReport(ReportData report) {
        this.report = report;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getCreativeid() {
        return creativeid;
    }

    public void setCreativeid(String creativeid) {
        this.creativeid = creativeid;
    }

    public ShowConfig getShowConfig() {
        return config;
    }

    public void setShowConfig(ShowConfig config) {
        this.config = config;
    }

    @ApiModel(value = "ReportData", description = "观星活动上报类")
    public static class ReportData {

        @ApiModelProperty(value = "观星活动id")
        private String promoId;// 观星系统定义的活动id
        @ApiModelProperty(value = "观星活动请求id")
        private String reqid;// 观星系统定义的请求id
        @ApiModelProperty(value = "观星活动推广位标识")
        private String posid;// 观星系统定义的推广位标识
        @ApiModelProperty(value = " 秒针监测路径")
        private ReportAddressData miaozhen;// 秒针监测路径
        @ApiModelProperty(value = " admaster监测路径")
        private ReportAddressData admaster;// admaster监测路径

        public String getPromoId() {
            return promoId;
        }

        public void setPromoId(String promoId) {
            this.promoId = promoId;
        }

        public String getPosid() {
            return posid;
        }

        public void setPosid(String posid) {
            this.posid = posid;
        }

        public String getReqid() {
            return reqid;
        }

        public void setReqid(String reqid) {
            this.reqid = reqid;
        }

        public ReportAddressData getMiaozhen() {
            return miaozhen;
        }

        public void setMiaozhen(ReportAddressData miaozhen) {
            this.miaozhen = miaozhen;
        }

        public ReportAddressData getAdmaster() {
            return admaster;
        }

        public void setAdmaster(ReportAddressData admaster) {
            this.admaster = admaster;
        }
    }

    @ApiModel(value = "ShowConfig", description = "展示相关的配置类")
    public static class ShowConfig {
        @ApiModelProperty(value = "显示时间")
        private Integer displayTime;

        @ApiModelProperty(value = "显示间隔")
        private Integer intervalTime;

        @ApiModelProperty(value = "显示频次")
        private Integer times;

        public Integer getDisplayTime() {
            return displayTime;
        }

        public void setDisplayTime(Integer displayTime) {
            this.displayTime = displayTime;
        }

        public Integer getIntervalTime() {
            return intervalTime;
        }

        public void setIntervalTime(Integer intervalTime) {
            this.intervalTime = intervalTime;
        }

        public Integer getTimes() {
            return times;
        }

        public void setTimes(Integer times) {
            this.times = times;
        }
    }

    @ApiModel(value = "ReportAddressData", description = "展示上报路径信息类")
    public static class ReportAddressData {
        @ApiModelProperty(value = "点击事件")
        private String click;
        @ApiModelProperty(value = "点击方法？")
        private String imp;

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getImp() {
            return imp;
        }

        public void setImp(String imp) {
            this.imp = imp;
        }
    }
}
