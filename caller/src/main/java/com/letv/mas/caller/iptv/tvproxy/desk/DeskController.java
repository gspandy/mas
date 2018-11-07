package com.letv.mas.caller.iptv.tvproxy.desk;

import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.LetvUserVipCommonConstant;
import com.letv.mas.caller.iptv.tvproxy.common.model.dto.Response;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import com.letv.mas.caller.iptv.tvproxy.desk.model.dto.DeskListDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//import com.letv.itv.v2.api.data.CommonParam;

/**
 * @author jijianlong
 */
@RestController(value = "v2.DeskController")
public class DeskController {

    @Autowired
    DeskService deskService;

    /**
     * 桌面管理打洞数据接口
     * 目前只对接了model=1儿童桌面，其中儿童桌面数据来自两部分。
     * 1、打洞配置及第二页图标跳转来自TV服务端ITV_DESK_CONFIG表;
     * 2、 焦点图部分数据来自CMS;
     * @param channelId
     * @param model
     *            0:点播桌面（暂未对接过来）;1:儿童桌面;2:音乐桌面;
     * @param request
     * @return
     */
    @ApiOperation(value = "桌面管理打洞数据接口", notes = "目前只对接了model=1儿童桌面，其中儿童桌面数据来自两部分。\n"
            + "1、打洞配置及第二页图标跳转来自TV服务端ITV_DESK_CONFIG表;\n" + "2、焦点图部分数据来自CMS;", httpMethod = "GET")
    @RequestMapping("/desk/get")
    public Response<DeskListDto> GetDesk(@RequestParam(value = "channelId") Integer channelId,
                                         @RequestParam(value = "model") Integer model, HttpServletRequest request,
                                         @ModelAttribute CommonParam commonParam) {
        return deskService.GetDesk(channelId, model, commonParam,
                LetvUserVipCommonConstant.getLocale(request));
    }
}
