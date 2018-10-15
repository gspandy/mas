package com.letv.mas.router.iptv.tvproxy.model.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应基类
 */
@ApiModel(value = "BaseResponseDto", description = "响应基类")
public class BaseResponseDto<T> {

    /**
     * 响应状态
     */
    @ApiModelProperty(value = "响应状态值：1-成功，0-失败")
    private Integer status = STATUS_OK;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码")
    private String code;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = " 错误信息")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(value = "返回数据")
    private T data;

    public static final Integer STATUS_OK = 1; // 响应状态:成功

    public static final Integer STATUS_ERROR = 0; // //响应状态:失败

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
