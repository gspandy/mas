package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1;

import java.io.Serializable;

/**
 * 跳转APP是否成功的判断标准
 * @author KevinYi
 */
public class JumpAppConfig implements Serializable {

    /**
     * 当前配置项的自定义id
     */
    private String id;

    /**
     * 检查app是否能够跳转的规则，1--包名（严格匹配）+版本号（大于等于）；2--包名（严格匹配）+安装路径（严格匹配）+apk文件名（
     * 正则匹配），TVLive使用该方式校验；null按1处理
     */
    private String checkRule;

    /**
     * APP包名，必须一致，如“com.letv.sports”
     */
    private String packageName;

    /**
     * 版本号，大于该版本号才支持特殊业务，如“100”， “1.0.0”（注，实际场景是int值，这里使用String类型，可直接字符串比较）
     */
    private String versionCode;

    /**
     * 跳转失败或者不符合跳转要求的提示文案
     */
    private String title;

    /**
     * app安装目录，checkRule=2时返回，严格匹配，如"/system/plugin/"
     */
    private String installDir;

    /**
     * app的apk包名，checkRule=2时返回，可以使用Java正则匹配的形式进行校验，如"*tvlive*.apk"
     */
    private String apkName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckRule() {
        return checkRule;
    }

    public void setCheckRule(String checkRule) {
        this.checkRule = checkRule;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstallDir() {
        return installDir;
    }

    public void setInstallDir(String installDir) {
        this.installDir = installDir;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

}
