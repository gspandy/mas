package com.letv.mas.caller.iptv.tvproxy.vip.model.dto;

import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;

import java.util.Map;

/**
 * TV版每部定向弹窗配置信息封装类
 * @author KevinYi
 */
public class PilotDto extends BaseData {

    /**
     * 触达位置，popup--定向弹窗，top--顶部，bottom--下浮层
     */
    private String position;

    /**
     * 活动id，客户端用于记录活动当天是否弹出过或者弹出了几次
     */
    private String id;

    /**
     * 用户状态，0--全部用户，1--0天<会员到期时间<=10天，2--10天<到期时间<=1个月；3--1个月<到期时间<=2个月；
     * 4--2个月<到期时间<=3个月；5--过期会员用户；6--一直非会员用户
     */
    private Integer userStatus;

    /**
     * 引导频次，0--暂不引导；1--限一天引导一次，定向弹窗类型活动不能为空，为空则无法弹出定向弹窗
     */
    private Integer rate;

    /**
     * 跳转收银台要默认选中的全屏影视会员套餐id，2--包月，2--包季，4--包半年，5--包年
     */
    private Integer orderType;

    /**
     * 到期提示文案
     */
    private String expireTips;

    /**
     * 会员特权文案，一般简介或者描述就用此字段返给客户端
     */
    private String privilegeTips;

    /**
     * 会员价值感汇报图片链接1
     */
    private String reportImage1;

    /**
     * 会员价值感汇报显示字1
     */
    private String reportContent1;

    /**
     * 会员价值感汇报图片链接2
     */
    private String reportImage2;

    /**
     * 会员价值感汇报显示字2
     */
    private String reportContent2;

    /**
     * 重磅内容预告内容图片链接
     */
    private String trailerImage;

    /**
     * 重磅内容预告内容
     */
    private String trailerContent;

    /**
     * 活动配置活动icon图片链接
     */
    private String activityImage;

    /**
     * 活动配置活动内容，不能有值，否则将弹出另一个样式
     */
    private String activityContent;

    /**
     * 内容/活动海报
     */
    private String contentImage;

    /**
     * 按钮文案
     */
    private String buttonContent;

    /**
     * 链接至收银台
     */
    private String cashierUrl;

    /**
     * 页面跳转方式，0--无响应，1--直接跳收银台，2--先跳H5再跳收银台，3--只跳H5，4--跳超前院线，定制业务，5--跳919活动页，
     * 内容是一个板块，里面全是H5
     */
    private Integer openType;

    /**
     * 自定义参数列表，供根据openType跳转时传关键参数给客户端，key--参数字段名，value--参数值
     */
    private Map<String, String> paramMap;
    /**
     * 触达小图 默认为空
     */
    private String smallImage;
    /**
     * 触达标题
     */
    private String title;
    /**
     * 触达上报id
     */
    private String pushId;
    /**
     * 触达退出标题
     */
    private String backTitle;
    /**
     * 触达退出内容
     */
    private String backContent;
    /**
     * 触达退出图片
     */
    private String backImage;

    /**
     * 顶部位置触达信息，和定向弹窗是同一个数据对象
     */
    private BaseData topData;

    private Integer subjectType;// 专题类型

    private String activityIds;// BOSS活动id，用于下单使用

    private String img;// 图片字段

    private String blockId;

    private String from;

    /**
     * 跳转类型
     */
    private Integer type;

    private String campaignId;// URM后台的活动id
    private String touchMessageId;// 上报字段
    private String touchSpotId;// URM后台的触达位id
    private String containerId;// 跳芈月传容器
    private Integer interactive;// 支持交互h5

    private String CPS_no;
    private String code_no;
    private String vendor;

    public String getCPS_no() {
        return CPS_no;
    }

    public void setCPS_no(String CPS_no) {
        this.CPS_no = CPS_no;
    }

    public String getCode_no() {
        return code_no;
    }

    public void setCode_no(String code_no) {
        this.code_no = code_no;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSmallImage() {
        return this.smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushId() {
        return this.pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getBackTitle() {
        return this.backTitle;
    }

    public void setBackTitle(String backTitle) {
        this.backTitle = backTitle;
    }

    public String getBackContent() {
        return this.backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public String getBackImage() {
        return this.backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getRate() {
        return this.rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getExpireTips() {
        return this.expireTips;
    }

    public void setExpireTips(String expireTips) {
        this.expireTips = expireTips;
    }

    public String getPrivilegeTips() {
        return this.privilegeTips;
    }

    public void setPrivilegeTips(String privilegeTips) {
        this.privilegeTips = privilegeTips;
    }

    public String getReportImage1() {
        return this.reportImage1;
    }

    public void setReportImage1(String reportImage1) {
        this.reportImage1 = reportImage1;
    }

    public String getReportContent1() {
        return this.reportContent1;
    }

    public void setReportContent1(String reportContent1) {
        this.reportContent1 = reportContent1;
    }

    public String getReportImage2() {
        return this.reportImage2;
    }

    public void setReportImage2(String reportImage2) {
        this.reportImage2 = reportImage2;
    }

    public String getReportContent2() {
        return this.reportContent2;
    }

    public void setReportContent2(String reportContent2) {
        this.reportContent2 = reportContent2;
    }

    public String getTrailerImage() {
        return this.trailerImage;
    }

    public void setTrailerImage(String trailerImage) {
        this.trailerImage = trailerImage;
    }

    public String getTrailerContent() {
        return this.trailerContent;
    }

    public void setTrailerContent(String trailerContent) {
        this.trailerContent = trailerContent;
    }

    public String getActivityImage() {
        return this.activityImage;
    }

    public void setActivityImage(String activityImage) {
        this.activityImage = activityImage;
    }

    public String getActivityContent() {
        return this.activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getContentImage() {
        return this.contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getButtonContent() {
        return this.buttonContent;
    }

    public void setButtonContent(String buttonContent) {
        this.buttonContent = buttonContent;
    }

    public String getCashierUrl() {
        return this.cashierUrl;
    }

    public void setCashierUrl(String cashierUrl) {
        this.cashierUrl = cashierUrl;
    }

    public Integer getOrderType() {
        return this.orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getOpenType() {
        return this.openType;
    }

    public void setOpenType(Integer openType) {
        this.openType = openType;
    }

    public Map<String, String> getParamMap() {
        return this.paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public BaseData getTopData() {
        return this.topData;
    }

    public void setTopData(BaseData topData) {
        this.topData = topData;
    }

    public Integer getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    public String getActivityIds() {
        return this.activityIds;
    }

    public void setActivityIds(String activityIds) {
        this.activityIds = activityIds;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getTouchMessageId() {
        return touchMessageId;
    }

    public void setTouchMessageId(String touchMessageId) {
        this.touchMessageId = touchMessageId;
    }

    public String getTouchSpotId() {
        return touchSpotId;
    }

    public void setTouchSpotId(String touchSpotId) {
        this.touchSpotId = touchSpotId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public Integer getInteractive() {
        return interactive;
    }

    public void setInteractive(Integer interactive) {
        this.interactive = interactive;
    }

}
