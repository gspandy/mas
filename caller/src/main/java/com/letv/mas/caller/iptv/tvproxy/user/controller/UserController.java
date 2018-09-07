package com.letv.mas.caller.iptv.tvproxy.user.controller;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.model.dto.UserAccountDto;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.user.response.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Iptv
@RequestMapping("${server.path}/user")
public class UserController extends BaseController {
    @ApiOperation(
            value = "获取会员信息(V2)",
            notes = "sig字段签名规则：md5(asort(k1=v1&k2=v2)SecrectKey)<br/>" +
                    "原版本：md5(ksort(deviceKey=xxx&mac=xxx&token=xxxx&userId=xxx)SecrectKey)<br/>" +
                    "支持防盗链版本：md5(ksort(appCode=xxx&deviceKey=xxx&devSign=xxxxxx&mac=xxx&token=xxxx&userId=xxx)SecrectKey)",
            httpMethod = "GET")
    @RequestMapping("/v2/account/getUserAccount")
    public Response<UserAccountDto> getUserAccount(
            @ApiParam(value = "机卡绑定设备类型，1-电视，2-手机传，3-盒子, 默认为1", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "签名, 规则见接口备注", required = true) @RequestParam(value = "sign", required = true) String sign,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return facadeService.getUserService().getUserAccount(deviceType,sign,commonParam);
    }

    @RequestMapping("/v2/account/test")
    public Response<UserAccountDto> test(
            @ApiParam(value = "机卡绑定设备类型，1-电视，2-手机传，3-盒子, 默认为1", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "签名, 规则见接口备注", required = true) @RequestParam(value = "sign", required = true) String sign,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return facadeService.getUserService().test(deviceType,sign,commonParam);
    }

    @RequestMapping("/v2/account/test2")
    public Response<UserAccountDto> test2(
            @ApiParam(value = "机卡绑定设备类型，1-电视，2-手机传，3-盒子, 默认为1", required = false, defaultValue = "1") @RequestParam(value = "deviceType", required = false, defaultValue = "1") Integer deviceType,
            @ApiParam(value = "签名, 规则见接口备注", required = true) @RequestParam(value = "sign", required = true) String sign,
            @ApiParam(value = "接口通用参数", required = true) @ModelAttribute CommonParam commonParam) {
        return facadeService.getUserService().test2(deviceType,sign,commonParam);
    }
}
