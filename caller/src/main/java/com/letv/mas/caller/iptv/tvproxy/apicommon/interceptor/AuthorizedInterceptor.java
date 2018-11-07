package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.DataConstant;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ErrorCodeConstants;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.JumpUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.constants.ResponseUtil;
import com.letv.mas.caller.iptv.tvproxy.apicommon.model.bean.bo.BaseData;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp.static1.CommonResponse;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.TerminalUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * API层HTTP参数拦截器，用于访问权限验证
 */
public class AuthorizedInterceptor extends HandlerInterceptorAdapter {

    Log logger = LogFactory.getLog(AuthorizedInterceptor.class);
    private final static Map<String, Integer> upgradeMap = new HashMap<String, Integer>();

    static {
        upgradeMap.put(
                "/iptv/api/new/video/album/detailandseries/more.json?terminalApplication=media_cibn&salesArea=cn", 297);
        upgradeMap.put("/iptv/api/new/video/album/detailandseries/more?terminalApplication=media_cibn&salesArea=cn",
                297);
        // upgradeMap.put("/iptv/api/new/video/play/get.json?terminalApplication=media_cibn",
        // 288);
        // upgradeMap.put("/iptv/api/new/video/play/get.json?terminalApplication=child_cibn",
        // 10);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        AuthorizedInterceptorAnnotation annotation = method.getMethodAnnotation(AuthorizedInterceptorAnnotation.class);
        if (annotation != null) {
            if (judgeUpgrade(request, response, handler)) {
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

    private boolean judgeUpgrade(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean ret = false;
        HandlerMethod method = (HandlerMethod) handler;
        String url = request.getRequestURI();
        Integer appCode = TerminalUtil.parseAppCode(request.getParameter("appCode"));
        String terminalApplication = request.getParameter("terminalApplication");
        String salesArea = StringUtils.trimToNull(request.getParameter("salesArea"));
        if (salesArea == null) {
            salesArea = request.getParameter("wcode");
        }
        if (url.indexOf("/iptv/api/new/video/play/get") == 0) {
            url += "?terminalApplication=" + terminalApplication;
        } else {
            url += "?terminalApplication=" + terminalApplication + "&salesArea=" + salesArea;
        }
        if (upgradeMap.containsKey(url)) {
            if (upgradeMap.get(url).intValue() > appCode.intValue()) {
                try {
                    // ErrorCodeConstant.throwLetvCommonException(ErrorCodeConstants.COMMON_SERVICE_UPGRADE,
                    // ResponseUtil.getLocale(request));
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(buildUpgradeInfoJO(request, 1));
                    ret = true;
                } catch (Exception e) {

                }
            }
        }
        return ret;
    }

    private String buildUpgradeInfoJO(HttpServletRequest request, int type) throws Exception {
        String ret = null;
        CommonResponse<BaseData> commonResponse = new CommonResponse<BaseData>();
        switch (type) {
        case 0: // 自升级
            // TODO
            break;
        case 1:
        default: // 外跳应用商店
            BaseData baseData = new BaseData();
            baseData.setDataType(DataConstant.DATA_TYPE_EXT_APP);
            commonResponse.setErrCode(ErrorCodeConstants.COMMON_SERVICE_UPGRADE);
            commonResponse.setErrMsg(MessageUtils.getMessage(ErrorCodeConstants.COMMON_SERVICE_UPGRADE,
                    ResponseUtil.getLocale(request)));
            commonResponse.setData(JumpUtil.buildJumpToLESTORE(baseData, null));
            ret = JSONObject.toJSONString(commonResponse);
            break;
        }
        return ret;
    }
}
