package com.letv.mas.caller.iptv.tvproxy.video.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.video.response.UserPlayAuth;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.vip.bean.ValidateServiceTp.VodTryPlayInfo;
import com.letv.mas.caller.iptv.tvproxy.vip.model.dto.PackageInfoDto;


import java.util.List;

/**
 * 美国Lecom鉴权结果，相比大陆版鉴权结果上有区别
 * @author KevinYi
 */
public class UserPlayAuth4LecomUS extends UserPlayAuth {

    /**
     * 鉴权不通过时有试看信息；
     * 注意：试看信息只能代表该内容是否支持试看，以及能够试看的时间，用户端需要检查试看截止时间是否已过期
     */
    private VodTryPlayInfo tryInfo;

    /**
     * 未登陆或鉴权不过时返回当前视频所属套餐包信息
     */
    private List<PackageInfoDto> packageInfo;
    /**
     * 是否可以领取试用
     */
    private String isCanTial;

    public UserPlayAuth4LecomUS() {
        super();
    }

    public UserPlayAuth4LecomUS(Integer status) {
        super(status);
    }

    public String getIsCanTial() {
        return isCanTial;
    }

    public void setIsCanTial(String isCanTial) {
        this.isCanTial = isCanTial;
    }

    public VodTryPlayInfo getTryInfo() {
        return tryInfo;
    }

    public void setTryInfo(VodTryPlayInfo tryInfo) {
        this.tryInfo = tryInfo;
    }

    public List<PackageInfoDto> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<PackageInfoDto> packageInfo) {
        this.packageInfo = packageInfo;
    }

}
