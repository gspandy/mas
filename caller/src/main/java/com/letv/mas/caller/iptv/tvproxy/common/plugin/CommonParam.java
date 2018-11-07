package com.letv.mas.caller.iptv.tvproxy.common.plugin;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import com.letv.mas.caller.iptv.tvproxy.common.constant.LocaleConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.HttpCommonUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;


/**
 * 客户端传递公共参数
 */
@ApiModel(value="CommonParam", description="接口通用参数")
public class CommonParam implements Serializable {

    /**
     * 存在比较或不同对象存储
     */
    private static final long serialVersionUID = -2613332633304816145L;

    /**
     * 设备品牌，乐视自有为letv，其他第三方如TCL，ChangHong，Konka，Skyworth，haier等
     */
    @ApiModelProperty(value = "(公共参数) 设备品牌，乐视自有为letv，其他第三方如TCL，ChangHong，Konka，Skyworth，haier等", required = true, allowableValues = "letv")
    private String terminalBrand;
    /**
     * 设备系列，仅针对乐视自有版本有效，如S40、X50等
     */
    @ApiModelProperty(value = "(公共参数) 设备系列，仅针对乐视自有版本有效，如S40、X50等", required = true, allowableValues = "letv")
    private String terminalSeries;
    /**
     * 终端应用类型，如letv--乐视自有版，letv-common--通用版，及CIBN版等
     */
    @ApiModelProperty(value = "(公共参数) 应用名，如：letv,leso等，产品定义应用，升级后台备案", required = true, allowableValues = "media_cibn")
    private String terminalApplication;
    /**
     * 应用版本号
     */
    @ApiModelProperty(value = "(公共参数) 应用版本编号，升级后台定义，和appVersion一样写死在apk中。", required = true, allowableValues = "292")
    private String appCode;
    /**
     * 设备mac
     */
    @ApiModelProperty(value = "(公共参数) 电视网卡地址。tv上唯一标识，从设备获取, 有则必传", required = true, allowableValues = "")
    private String mac;
    /**
     * 设备devicekey
     */
    @ApiModelProperty(value = "(公共参数) 乐视自有设备暗码，标识设备。业务应用场景目前是机卡绑定，从设备中获取, 有则必传", required = true, allowableValues = "")
    private String deviceKey;
    /**
     * 客户端版本信息
     */
    @ApiModelProperty(value = "(公共参数) 客户端版本信息，如：2.10.1, 有则必传", required = true, allowableValues="2.10.1")
    private String appVersion;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "(公共参数) 用户在用户中心唯一标识, 有则必传", required = true, allowableValues = "")
    private String userId;
    /**
     * 用户token，将取代userToken
     */
    @ApiModelProperty(value = "(公共参数) 用户身份证明，用户登录成功后，以此从各接口获取数据。有则必传", required = true, allowableValues = "")
    private String token;
    /**
     * 设备类型，1 手机，2电视，3pad,4汽车。为不与业务参数重名，陆续通用参数
     * 添加p_前缀，wiki中定义，客户端包中
     */
    @ApiModelProperty(value = "(公共参数) 设备类型，1手机，2电视（自有），3pad，4汽车，5盒子（自有），6北美TV（非自有），7其它第三方。为不与业务参数重名，陆续通用参数，必传。为兼容老版本，未传默认为2！", required = true, allowableValues= "2,1,3,4,5,6,7")
    private Integer p_devType;
    /**
     * 客户端名称，通常传"android"
     */
    @ApiModelProperty(value = "(公共参数) 标识客户端是什么类型系统 ，如：android，ios", required = false, allowableValues = "android")
    private String client;
    /**
     * 渠道号
     */
    @ApiModelProperty(value = "(公共参数) 渠道，通用内置、store下载，bbs下载等，产品定义，后台备案，apk中写死", required = false, allowableValues = "01001001000")
    private String bsChannel;

    @ApiModelProperty(value = "(公共参数) 应用版本号，老的，暂时保留", required = false)
    private String installVersion;// 老的，暂时保留
    /**
     * 客户端Ip
     */
    @ApiModelProperty(value = "(公共参数) 客户端Ip", required = false)
    private String clientIp;// 暂时在这里写着，后面将方法从api层提到lib层

    /**
     * 用户名，将废弃
     */
    @ApiModelProperty(value = "(公共参数) 用户名，将废弃", required = false)
    private String username;

    /**
     * 登录时间，将废弃
     */
    @ApiModelProperty(value = "(公共参数) 登录时间，将废弃", required = false)
    private String loginTime;

    /**
     * 用户token，现使用token来代替
     */
    @Deprecated
    @ApiModelProperty(value = "(公共参数) 用户token，现使用token来代替", required = false)
    private String userToken;
    /**
     * 播放合作方，0--letv，1--CNTV，2--CIBN，3--WASU，null--未指定播控方、不受任何播控方控制
     */
    @ApiModelProperty(value = "(公共参数) 播放合作方，0--letv，1--CNTV，2--CIBN，3--WASU，null--未指定播控方、不受任何播控方控制, 有则必传", required = false, allowableValues = "0,1,2,3")
    private Integer broadcastId;
    /**
     * 区域代码hk/cn/us etc
     */
    @ApiModelProperty(value = "(公共参数) 用户身份证明，用户登录成功后，以此从各接口获取数据。有则必传", required = false, allowableValues = "")
    private String wcode;
    /**
     * 区域代码hk/cn/us etc
     */
    @ApiModelProperty(value = "(公共参数) 区域代码hk/cn/us etc", required = false)
    private String t_wcode;
    /**
     * 销售地，显示内容主要依赖此属性，目前用于定位默认设备所在地域，如：CN,HK,US,IN，值从设备系统中获取
     */
    @ApiModelProperty(value = "(公共参数) 设备销售地域，目前用于定位默认设备所在地域，如：CN,HK,US,IN，值从设备系统中获取, 有则必传", required = false, allowableValues = "CN,HK,US,IN")
    private String salesArea;

    /**
     * 用户选择地域，值从设备系统获取
     */
    @ApiModelProperty(value = "(公共参数) 用户选择地域，值从设备系统获取, 有则必传", required = false, allowableValues = "86")
    private String countryArea;

    /**
     * 系统语言 zh_cn,zh_hk,zh_tw,en_us etc
     */
    @ApiModelProperty(value = "(公共参数) 系统语言 zh_cn,zh_hk,zh_tw,en_us etc, 有则必传", required = false, allowableValues = "zh_cn,zh_hk,zh_tw,en_us")
    private String langcode;
    /**
     * 移动设备唯一标识，目前值为设备imei
     */
    @ApiModelProperty(value = "(公共参数) 移动设备唯一标识，目前值为设备imei", required = false)
    private String devId;

    /**
     * 2016-08-29,美国lecom需求新增，背景是，lecom尚未完成开发时，需要提供给rom一个预装版本，客户端使用原美国行货(
     * terminalApplication=letv_us)基础上，通过添加realTA为le得来表示作为le版本使用；服务端使用该参数解决升级问题，
     * 而其他接口还需出letv_us数据
     */
    @ApiModelProperty(value = "(公共参数) realTA为le得来表示作为北美le版本使用，realTA＝letv_us", required = false)
    private String realTA;

    /**
     * 业务编码
     */
    @ApiModelProperty(value = "(公共参数) bizCode", required = false)
    private String bizCode;

    /**
     * 请求来源的appid,如果 乐见打洞到万象，则appid传的是乐见的appid
     * @CommonConstants
     */
    @ApiModelProperty(value = "(公共参数) 请求来源的appid,如果 乐见打洞到万象，则appid传的是乐见的appid", required = false)
    private String displayAppId;

    /**
     * 请求来源的platformId
     * @CommonConstants
     */
    @ApiModelProperty(value = "(公共参数) 请求来源的platformId", required = false)
    private String displayPlatformId;

    /**
     * 默认为null-乐视，1-芒果
     */
    private String cpid;

    public String getDisplayAppId() {
        return displayAppId;
    }

    public void setDisplayAppId(String displayAppId) {
        this.displayAppId = displayAppId;
    }

    public String getDisplayPlatformId() {
        return displayPlatformId;
    }

    public void setDisplayPlatformId(String displayPlatformId) {
        this.displayPlatformId = displayPlatformId;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getAppVersion() {
        return this.appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * ui版本信息
     */
    @ApiModelProperty(value = "(公共参数) ui版本信息", required = false)
    private String uiType;

    public String getUiType() {
        return uiType;
    }

    public void setUiType(String uiType) {
        this.uiType = uiType;
    }

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
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
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserToken() {
        return StringUtils.isBlank(this.userToken) ? this.token : this.userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getClientIp() {
        // return this.clientIp;
        return this.getIP();
    }

    public String getClient() {
        return this.client;
    }

    public String getWcode() {
        if (LocaleConstant.Wcode.HK.equalsIgnoreCase(this.t_wcode)) { // 香港水货返回地区为大陆
            return LocaleConstant.Wcode.CN;
        }
        if (this.t_wcode != null && !"".equals(this.t_wcode)) {
            return this.t_wcode;
        }
        if (CommonConstants.getDefaultWcode() != null) {
            return CommonConstants.getDefaultWcode();
        }
        return this.wcode;
    }

    /**
     * 临时方案，保证获取客户端的wcode
     * @return
     */
    public String getWcodeTmp() {
        return this.wcode;
    }

    public String getT_wcode() {
        return this.t_wcode;
    }

    public void setT_wcode(String t_wcode) {
        this.t_wcode = t_wcode;
    }

    public void setWcode(String wcode) {
        this.wcode = wcode;
    }

    public String getLangcode() {
        if (TerminalUtil.isLetvUs(this.terminalApplication)) {
            if (!LocaleConstant.Langcode.EN_US.equals(this.langcode)
                    && !LocaleConstant.Langcode.ZH_CN.equals(this.langcode)
                    && !LocaleConstant.Langcode.ZH_HK.equals(this.langcode)
                    && !LocaleConstant.Langcode.ZH_TW.equals(this.langcode)) {
                this.langcode = LocaleConstant.Langcode.EN_US;
            }
        }
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    public String getTerminalBrand() {
        return this.terminalBrand;
    }

    public void setTerminalBrand(String terminalBrand) {
        this.terminalBrand = terminalBrand;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getBroadcastId() {
        if (!StringUtil.isBlank(this.wcode) && !LocaleConstant.Wcode.CN.equalsIgnoreCase(this.wcode)) {
            return CommonConstants.LETV;
        }
        return this.broadcastId;
    }

    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    public String getTerminalSeries() {
        return this.terminalSeries;
    }

    public void setTerminalSeries(String terminalSeries) {
        this.terminalSeries = terminalSeries;
    }

    public String getAppCode() {
        return this.appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getTerminalApplication() {
        return this.terminalApplication;
    }

    public void setTerminalApplication(String terminalApplication) {
        this.terminalApplication = terminalApplication;
    }

    public String getBsChannel() {
        return this.bsChannel;
    }

    public void setBsChannel(String bsChannel) {
        this.bsChannel = bsChannel;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = getIP();
    }

    /**
     * 获取请求的IP地址
     * @return
     */
    public static String getIP() {
        return getIP(HttpCommonUtil.getRequest());
    }

    public String getSalesArea() {
        return this.salesArea != null ? this.salesArea : CommonConstants.getDefaultWcode();
    }

    /**
     * 配置下发临时修改 20160818 ，等运龙再看
     * @return
     */
    public String getSalesAreaTmp() {
        return this.salesArea;
    }

    public void setSalesArea(String salesArea) {
        this.salesArea = salesArea;
    }

    public String getToken() {
        return StringUtils.isBlank(this.token) ? this.userToken : this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountryArea() {
        return countryArea;
    }

    public void setCountryArea(String countryArea) {
        this.countryArea = countryArea;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getInstallVersion() {
        return this.installVersion;
    }

    public void setInstallVersion(String installVersion) {
        this.installVersion = installVersion;
    }

    public String getRealTA() {
        return realTA;
    }

    public void setRealTA(String realTA) {
        this.realTA = realTA;
    }

    public Integer getP_devType() {
        if ((!CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE && null != this.p_devType && this.p_devType != 2)
                || (CommonConstants.VIP_DISTRIBUTED_PAYING_ENBABLE && null == this.p_devType)
                || !TerminalUtil.supportDistributedPaying(this)) { // 兼容老版本按自有tv类型
            this.p_devType = 2;
        }
        return p_devType;
    }

    public void setP_devType(Integer p_devType) {
        this.p_devType = p_devType;
    }

    /**
     * 获取请求的IP地址
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null) {
            String[] ips = ip.split(",");
            for (String i : ips) {
                if (!i.startsWith("10.") && !i.startsWith("172.16.") && !i.startsWith("127.")
                        && !i.startsWith("192.168.")) {
                    ip = i.trim();
                    break;
                }
            }
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-real-ip");
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip) || "127.0.0.1".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 接口缓存刷新标识：1-刷新
     */
    @ApiModelProperty(value = "(公共参数) 接口缓存刷新标识：1-刷新", required = false)
    private Integer flush = null;

    public Integer getFlush() {
        return flush;
    }

    public void setFlush(Integer flush) {
        this.flush = flush;
    }

    public boolean isFlush() {
      return null != this.flush && 1 == this.flush;
    }

    /**
     * 调试模式：1-简单，2-详细，3-cache、redis 0,null-关闭，默认；仅限测试及仿真环境可用
     */
    @ApiModelProperty(value = "(公共参数) 1-简单，2-详细，3-cache、redis 0,null-关闭，默认；仅限测试及仿真环境可用", required = false, allowableValues = "0,1,2")
    private Integer debug = null;

    public Integer getDebug() {
        return debug;
    }

    public void setDebug(Integer debug) {
        this.debug = debug;
    }


    /**
     * 调试模式: test-测试环境，pre-预发，prod、null-生产，默认
     */
    @ApiModelProperty(value = "(公共参数) 调试模式: test-测试环境，pre-预发，prod、null-生产，默认", required = false, allowableValues = "dev,pre,prod")
    private String p_workbench = "prod";

    public String getP_workbench() {
        return p_workbench;
    }

    public void setP_workbench(String p_workbench) {
        this.p_workbench = p_workbench;
    }

    private Integer isDync = null;

    public Integer getIsDync() {
        return isDync;
    }

    public void setIsDync(Integer isDync) {
        this.isDync = isDync;
    }

    /**
     * 设备签名
     */
    @ApiModelProperty(value = "(公共参数) 设备签名: 客户端配合植入，用于防盗链场景行为记录上报", required = false)
    private String devSign;

    public String getDevSign() {
        return devSign;
    }

    public void setDevSign(String devSign) {
        this.devSign = devSign;
    }


    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }
}
