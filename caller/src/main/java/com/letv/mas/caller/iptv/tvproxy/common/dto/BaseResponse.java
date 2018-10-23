package com.letv.mas.caller.iptv.tvproxy.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应基类
 */
@ApiModel(value="BaseResponse", description="响应基类")
public abstract class BaseResponse {

    /**
     * 响应状态
     */
    @ApiModelProperty(value = "响应状态，同status")
    private Integer resultStatus = STATUS_OK;

    /**
     * 响应状态手机儿童
     */
    @ApiModelProperty(value = "请求状态，1-成功 0-失败")
    private Integer status = 1;
    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码")
    private String errCode;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = " 错误信息")
    private String errMsg;

    @ApiModelProperty(value = " 错误码")
    private String errorCode;

    @ApiModelProperty(value = " 错误信息")
    private String message;

    public static final Integer STATUS_OK = 1; // 响应状态:成功

    public static final Integer STATUS_ERROR = 0; // //响应状态:失败

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getResultStatus() {
        return this.resultStatus;
    }

    public void setResultStatus(Integer resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
        this.errorCode = errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        this.message = errMsg;
    }

}
