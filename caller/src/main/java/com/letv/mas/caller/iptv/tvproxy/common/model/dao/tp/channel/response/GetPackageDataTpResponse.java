package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.channel.response;

/**
 * 获取专题下视频包数据封装类
 * @author liyunlong
 */
public class GetPackageDataTpResponse {

    /**
     * 接口返回的状态码
     */
    private Integer code;
    /**
     * 接口返回数据
     */
    private SubjectTjPackageTpResponse data;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public SubjectTjPackageTpResponse getData() {
        return this.data;
    }

    public void setData(SubjectTjPackageTpResponse data) {
        this.data = data;
    }

}
