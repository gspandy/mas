package com.letv.mas.caller.iptv.tvproxy.search;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CategoryIdConstant;
import com.letv.mas.caller.iptv.tvproxy.common.controller.BaseController;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.CommonParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import serving.GenericServingResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${server.path}/v2/search")
public class SearchController extends BaseController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "复式检索(多条件搜索) 从搜索组根据条件检索专辑信息", httpMethod = "GET")
    //@HttpResponseInterceptorAnnotation(headers = { "Cache-Control:max-age=" + CommonConstants.SECONDS_OF_5_MINUTE })
    @RequestMapping(value = "/searchTypes")
    public GenericServingResponse searchTypes(
            @ApiParam(value = "页码", required = false, defaultValue = "1") @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "页长", required = false, defaultValue = "45") @RequestParam(value = "pageSize", required = false, defaultValue = "45") Integer pageSize,
            @ApiParam(value = "分类ID", required = false) @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @ApiParam(value = "搜索多条件内容(如：地区 T:V / 3:253 注多个以;分隔)", required = false) @RequestParam(value = "searchContent", required = false) String searchContent,
            @ApiParam(value = "搜索数据类型 参照CategoryConstant的SEARCH_DATA_TYPE", required = false) @RequestParam(value = "dt", required = false, defaultValue = "1,2") String dt,
            @ApiParam(value = "分类(频道新id)", required = false) @RequestParam(value = "newCategoryId", required = false) String newCategoryId,
            @ApiParam(value = "搜索关键词", required = false) @RequestParam(value = "wd", required = false) String word,
            @ApiParam(value = "pushChild", required = false) @RequestParam(value = "pushChild", required = false, defaultValue = "0") String pushChild,
            @ApiParam(value = "用户ID", required = false) @RequestParam(value = "uid", required = false) String uid,
            @ApiParam(value = "ID", required = false) @RequestParam(value = "id", required = false) String id,
            @ApiParam(value = "splatid", required = false) @RequestParam(value = "splatid", required = false) String splatid,
            @ApiParam(value = "频道ID", required = false) @RequestParam(value = "channelId", required = false) Integer channelId,
            @ApiParam(value = "leIds", required = false) @RequestParam(value = "leIds", required = false) String leIds,
            @ApiParam(value = "productId", required = false) @RequestParam(value = "productId", required = false) String productId,
            @ApiParam(value = "repo_type", required = false) @RequestParam(value = "repo_type", required = false) String repo_type,
            @ApiParam(value = "通用参数列表", required = false) @ModelAttribute CommonParam commonParam,
            HttpServletRequest request) {
        String newCid = newCategoryId;
        if (StringUtil.isBlank(newCid)) {
            Integer cid = CategoryIdConstant.getNewCategory(categoryId);
            if (cid != null) {
                newCid = cid.toString();
            }
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 45;
        }
        String from = "tv_02";
        String ph = "420007" + ",-121";// 检索出大陆 即可
        String pcp = null; // 控制检索数据是否需要通过如cibn等牌照方的申通
        if (2 == commonParam.getBroadcastId()) { // CIBN版本pcp为2
            pcp = "2";
        }

        return searchService.searchTypes2(from, dt, page, pageSize, word, newCid, ph,
                searchContent, id, splatid, channelId, leIds, productId, pushChild, repo_type, pcp, commonParam);

    }
}

