package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.activity.response;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class ActivityBatchTpResponse {

    private String errno;// 错误码，成功时为10000
    private String errmsg;// 错误描述信息；只在error非10000的情况下出现
    private Map<String, List<ActivityData>> data;// 数据主体，key：推广位标识，value：推广位数据集合

    public String getErrno() {
        return errno;
    }

    public void setErrno(String errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Map<String, List<ActivityData>> getData() {
        return data;
    }

    public void setData(Map<String, List<ActivityData>> data) {
        this.data = data;
    }

    /**
     * error为10000且data不为空，则表示成功获取到活动数据
     * @return
     */
    public boolean isSuccess() {
        return "10000".equals(errno) && !CollectionUtils.isEmpty(data);
    }

    public boolean isNetSuccess() {
        return "10000".equals(errno);
    }

}
