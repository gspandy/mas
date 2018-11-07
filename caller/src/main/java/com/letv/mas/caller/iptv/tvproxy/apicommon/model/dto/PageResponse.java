package com.letv.mas.caller.iptv.tvproxy.apicommon.model.dto;

import com.letv.mas.caller.iptv.tvproxy.common.model.dto.BaseResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Map;

/**
 * 分页响应类
 * @param <T>
 */
@XmlRootElement
@ApiModel(value = "PageResponse", description = "分页响应")
public class PageResponse<T> extends BaseResponse {

    /**
     * 总数
     */
    @ApiModelProperty(value = "总数")
    private Integer totalCount;

    /**
     * 当前索引
     */
    @ApiModelProperty(value = "当前索引")
    private Integer currentIndex;

    /**
     * 下一页索引
     */
    @ApiModelProperty(value = "下一页索引")
    private Integer nextIndex;

    /**
     * 数据列表
     */
    @ApiModelProperty(value = "数据列表")
    private Collection<T> data;

    /**
     * 补充数据
     */
    @ApiModelProperty(value = "补充数据")
    private Map<String, Object> plus;

    public PageResponse() {
    }

    public PageResponse(Collection<T> data) {
        this.data = data;
    }

    public PageResponse(Collection<T> data, Map<String, Object> plus) {
        this.data = data;
        this.plus = plus;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Integer getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(Integer nextIndex) {
        this.nextIndex = nextIndex;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public Map<String, Object> getPlus() {
        return plus;
    }

    public void setPlus(Map<String, Object> plus) {
        this.plus = plus;
    }
}
