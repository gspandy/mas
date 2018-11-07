package com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo;

/**
 * jump扩展对象
 * @author jijianlong
 */
public class Extension extends BaseData {

    private static final long serialVersionUID = -3491204528769609484L;

    private String action;

    private Integer isParse = 0;// 是否解析value 0 不解析 1 解析

    private Integer model;

    private Integer launchMode; // default:0-广播
    // ，1-隐式startActivity(必传action)，2-显式startActivity(必传className)

    private String resource;

    private String typeString;
    // 游戏包名
    private String appPackageName;

    private String storePackageName;

    private String className;

    // 跳转目标应用 名称
    private String appName;

    // 跳转对象最小支持版本
    private Integer minVersionCode;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getIsParse() {
        return isParse;
    }

    public void setIsParse(Integer isParse) {
        this.isParse = isParse;
    }

    public Integer getLaunchMode() {
        return launchMode;
    }

    public void setLaunchMode(Integer launchMode) {
        this.launchMode = launchMode;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getStorePackageName() {
        return storePackageName;
    }

    public void setStorePackageName(String storePackageName) {
        this.storePackageName = storePackageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getMinVersionCode() {
        return minVersionCode;
    }

    public void setMinVersionCode(Integer minVersionCode) {
        this.minVersionCode = minVersionCode;
    }

}