package com.letv.mas.caller.iptv.tvproxy.terminal.controller;

import com.letv.mas.caller.iptv.tvproxy.common.constant.TerminalCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.ResultsDto;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.ServiceTermDto;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.TerminalAuthRequest;
import com.letv.mas.caller.iptv.tvproxy.terminal.model.dto.TerminalConfigDto;
import com.letv.mas.caller.iptv.tvproxy.terminal.service.TerminalLogUploadService;
import com.letv.mas.caller.iptv.tvproxy.terminal.service.TerminalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "/iptv/api/new/terminal", description = "终端配置相关")
@RestController
public class TerminalApiController {

    @Autowired
    TerminalService terminalService;

    @Autowired
    TerminalLogUploadService terminalLogUploadService;
    
    /**
     * @param terminalUuid
     *            服务端生成的唯一标示
     * @param identifyCode
     *            没用
     * @param clientUuid
     *            MD5值
     * @param terminalBrand
     *            应用类型
     * @param terminalUnique
     *            MAC地址
     * @param client
     *            客户端
     * @param installVersion
     *            安装版本
     * @param request
     * @return
     */
    @ApiOperation(value = "/terminal/terminalEnter", httpMethod = "GET")
    @RequestMapping(value = "/terminal/terminalEnter")
    public ResultsDto terminalEnter(
            @ApiParam(value = "服务端生成的唯一标示", required = false) @RequestParam(value = "terminalUuid", required = false) String terminalUuid,
            @ApiParam(value = "暂没用", required = false) @RequestParam(value = "identifyCode", required = false) String identifyCode,
            @ApiParam(value = "终端生成的唯一标示", required = false) @RequestParam("clientUuid") String clientUuid,
            @ApiParam(value = "终端品牌", required = false) @RequestParam("terminalBrand") String terminalBrand,
            @ApiParam(value = "终端序列号", required = false) @RequestParam(value = "terminalUnique", required = false) String terminalUnique,
            @ApiParam(value = "终端名称", required = false) @RequestParam("client") String client,
            @ApiParam(value = "审核类型", required = false, defaultValue = "1") @RequestParam(value = "auditType", required = false, defaultValue = "1") Integer auditType,
            @ApiParam(value = "安装版本", required = false) @RequestParam("installVersion") String installVersion,
            @ApiParam(value = "终端UI版本", required = false, defaultValue = "0") @RequestParam(value = "terminalUiVersion", required = false, defaultValue = "0") String terminalUiVersion,
            @ApiParam(value = "终端UI编码", required = false, defaultValue = "ui") @RequestParam(value = "terminalUiCode", required = false, defaultValue = "ui") String terminalUiCode,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        // 2016-08-29,美国lecom需求新增，背景是，lecom尚未完成开发时，需要提供给rom一个预装版本，
        // 客户端使用原美国行货(terminalApplication=letv_us)基础上，通过添加realTA为le得来表
        // 示作为le版本使用；服务端使用该参数解决升级问题，而其他接口还需出letv_us数据
        String terminalApplication = StringUtils.isNotEmpty(commonParam.getRealTA()) ? commonParam.getRealTA()
                : commonParam.getTerminalApplication();

        TerminalAuthRequest authRequest = new TerminalAuthRequest(terminalUuid, identifyCode, clientUuid,
                terminalBrand, commonParam.getTerminalSeries(), terminalApplication, terminalUnique, client,
                commonParam.getMac(), installVersion, commonParam.getClientIp(), String.valueOf(commonParam
                        .getBroadcastId()), commonParam.getLangcode(), terminalUiVersion, terminalUiCode,
                commonParam.getDevId());
        authRequest.setAuditType(auditType);
        authRequest.setWcode(commonParam.getT_wcode());
        authRequest.setAppCode(commonParam.getAppCode());
        authRequest.setBsChannel(commonParam.getBsChannel());
        ResultsDto result = terminalService.terminalAuth(authRequest, commonParam);

        if (result != null && result.getData() != null) {
            result.getData().put(TerminalCommonConstant.UPDATE_SWITCH_KEY, TerminalCommonConstant.UPDATE_SWITCH_ON);
        }
        return result;
    }

    private String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-real-ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 服务端下发配置信息
     * @param model
     *            1--接口、域名的配置下发，其他值--以前的下发逻辑
     * @param commonParam
     * @return
     */
    @ApiOperation(value = "服务端下发配置信息", httpMethod = "GET")
    @RequestMapping(value = "/terminal/config")
    public Response<TerminalConfigDto> config(
            @ApiParam(value = "设备类型", required = false) @RequestParam(value = "deviceModelType", required = false) String deviceModelType,
            @ApiParam(value = "配置下发模式", required = false, allowableValues = "1") @RequestParam(value = "model", required = false) Integer model,
            @ApiParam(value = "客户端版本号", required = false, hidden = true) @RequestParam(value = "versionCode", required = false) String versionCode,
            @ApiParam(value = "客户端版本", required = false, hidden = true) @RequestParam(value = "versionName", required = false) String versionName,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        commonParam.setAppCode(versionCode);// 这个接口客户端传的是versionCode
        commonParam.setAppVersion(versionName);// 这个接口客户端传的是versionName
        return terminalService.config(model, deviceModelType, commonParam);
    }

    @ApiOperation(value = "/terminal/serviceTerm/get", httpMethod = "GET")
    @RequestMapping(value = "/terminal/serviceTerm/get")
    public Response<ServiceTermDto> getServiceTerm(@RequestParam(value = "termType") Integer termType,
                                                   @ModelAttribute CommonParam commonParam) {
        return terminalService.getServiceTerm(termType, commonParam);
    }

    /**
     * 客户端日志上传接口,上传的结构体为json
     * @param type
     *            日志类型 由记录日志方定制
     * @return
     */
    // @ApiOperation(value = "客户端日志上传接口", httpMethod = "POST")
    // @RequestMapping(value = "/terminal/logUpload", method =
    // RequestMethod.POST)
    public Response<Object> logUpload(
            @ApiParam(value = "日志类型 由记录日志方定制", required = false) @RequestParam(value = "type", required = false) Integer type,
            @ApiParam(value = "是否是批量上传 0 否 1 是", required = false, defaultValue = "0") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            @ApiParam(value = "日志体,json结构,转化Map后size不能超过10个,单个及批量都遵守,总大小不能超过1KB", required = true) @RequestBody Map<String, Object> logs) {
        return terminalLogUploadService.uploadLog(type, model, commonParam, logs);
    }

    /**
     * 客户端日志上传接口
     * @param type
     *            日志类型 由记录日志方定制
     * @return
     */
    @ApiOperation(value = "客户端日志上传接口", httpMethod = "POST")
    @RequestMapping(value = "/terminal/logUpload", method = RequestMethod.POST)
    public Response<Object> logUploadV2(
            @ApiParam(value = "日志类型 由记录日志方定制", required = false) @RequestParam(value = "type", required = true) Integer type,
            @ApiParam(value = "是否是批量上传 0 否 1 是", required = false, defaultValue = "0") @RequestParam(value = "model", required = false, defaultValue = "0") Integer model,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam,
            @ApiParam(value = "日志体,json结构toString,转化Map后size不能超过20个,单个及批量都遵守,总大小不能超过5KB", required = true) @RequestParam(value = "logs", required = true) String logs) {
        return terminalLogUploadService.uploadLogV2(type, model, commonParam, logs);
    }
}
