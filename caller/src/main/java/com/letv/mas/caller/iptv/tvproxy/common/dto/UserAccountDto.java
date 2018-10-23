package com.letv.mas.caller.iptv.tvproxy.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value="UserAccountDto", description="用户信息")
public class UserAccountDto {
    @ApiModelProperty(value = "用户名")
    private String username;// 用户名

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "[兼容]用户id，同userId")
    private String uid;

    @ApiModelProperty(value = "包信息")
    private String packageType;

    @ApiModelProperty(value = "有效时间")
    private String validDate;

    @ApiModelProperty(value = "乐点余额")
    private Integer letvPoint;

    @ApiModelProperty(value = "[兼容]用户来源：1-cntv_xxx的用户名，默认为0")
    private Integer from = 0;

    @ApiModelProperty(value = "用户图像")
    private String picture;

    @ApiModelProperty(value = "用户显示名称")
    private String displayName;

    @ApiModelProperty(value = "[机卡绑定]是否包含本机时长：0-非绑定、不包含，1-绑定、包含")
    private Integer isbind;

    @ApiModelProperty(value = "[机卡绑定]新增需求，鉴定当前用户是否领取过其他机器的绑定套餐：0-无效数据，1-已领取，2-未领取")
    private Integer hasBindOtherDevice;

    @ApiModelProperty(value = "[机卡绑定]新增需求，鉴定当前用户是否领取过当前机器的绑定套餐：0-无效数据，1-已领取，2-未领取")
    private Integer hasBindCurrentDevice;

    @ApiModelProperty(value = "vip类别：0-非会员，1-普通vip（PC会员或APP会员），2-高级vip（TV会员）")
    private Integer isvip;

    @ApiModelProperty(value = "[兼容]体育会员有效期")
    private String sportsValidDate;

    @ApiModelProperty(value = "[兼容]体育会员标示：0-非vip，1-体育会员，2-过期体育会员")
    private Integer sportsVip;

    @ApiModelProperty(value = "用户显示名称")
    private List<VipInfoV2Dto> vipList; // 会员列表

    @ApiModelProperty(value = "［调试］参数签名")
    private String urlSign;

    public static final int VIP_NOT = 0; // 非会员
    public static final int VIP_ORDINARY = 1; // 普通会员
    public static final int VIP_SENIOR = 2; // 高级会员

    @ApiModel(value="VipInfoV2Dto", description="会员信息")
    public static class VipInfoV2Dto {
        @ApiModelProperty(value = "会员状态：0-会员不可用，1-会员可用状态")
        private Integer status;

        @ApiModelProperty(value = "权益id")
        private Integer productId;

        @ApiModelProperty(value = "会员id：1-体验会员，2-乐次元影视会员，3-超级影视会员，4-超级家庭会员，5-国广CIBN会员，6-华数会员，7-芒果会员，8-腾讯会员")
        private Integer vipId;

        @ApiModelProperty(value = "会员名称")
        private String name;

        @ApiModelProperty(value = "会员截止日期，时间戳")
        private Long endTime;

        @ApiModelProperty(value = "会员订阅起始时间")
        private Long createTime;

        public static final int STATUS_VIP_VALID = 1;// 会员可用

        public static final int STATUS_VIP_INVALID = 0;// 会员不可用

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getVipId() {
            return vipId;
        }

        public void setVipId(Integer vipId) {
            this.vipId = vipId;
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.uid = userId;
    }

    public String getUid() {
        return this.userId == null ? this.uid : this.userId;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPackageType() {
        return this.packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getValidDate() {
        return this.validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public Integer getLetvPoint() {
        return this.letvPoint;
    }

    public void setLetvPoint(Integer letvPoint) {
        this.letvPoint = letvPoint;
    }

    public Integer getFrom() {
        return this.from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getIsbind() {
        return this.isbind;
    }

    public void setIsbind(Integer isbind) {
        this.isbind = isbind;
    }

    public Integer getHasBindOtherDevice() {
        return this.hasBindOtherDevice;
    }

    public void setHasBindOtherDevice(Integer hasBindOtherDevice) {
        this.hasBindOtherDevice = hasBindOtherDevice;
    }

    public Integer getHasBindCurrentDevice() {
        return this.hasBindCurrentDevice;
    }

    public void setHasBindCurrentDevice(Integer hasBindCurrentDevice) {
        this.hasBindCurrentDevice = hasBindCurrentDevice;
    }

    public Integer getIsvip() {
        return this.isvip;
    }

    public void setIsvip(Integer isvip) {
        this.isvip = isvip;
    }

    public String getSportsValidDate() {
        return this.sportsValidDate;
    }

    public void setSportsValidDate(String sportsValidDate) {
        this.sportsValidDate = sportsValidDate;
    }

    public Integer getSportsVip() {
        return this.sportsVip;
    }

    public void setSportsVip(Integer sportsVip) {
        this.sportsVip = sportsVip;
    }

    public List<VipInfoV2Dto> getVipList() {
        return vipList;
    }

    public void setVipList(List<VipInfoV2Dto> vipList) {
        this.vipList = vipList;
    }

    public String getUrlSign() {
        return urlSign;
    }

    public void setUrlSign(String urlSign) {
        this.urlSign = urlSign;
    }
}
