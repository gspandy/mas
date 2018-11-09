package com.letv.mas.caller.iptv.tvproxy.apicommon.interceptor;

import com.letv.mas.caller.iptv.tvproxy.common.constant.CommonConstants;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Order(2)
@WebFilter(filterName = "broadcast", urlPatterns = "/iptv/api/*")
public class BroadcastFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        chain.doFilter(new BroadcastWrapperReqest(request), res);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

    class BroadcastWrapperReqest extends HttpServletRequestWrapper {
        private final static String BROADCAST_PARAM = "broadcastId";

        public BroadcastWrapperReqest(HttpServletRequest request) {
            super(request);
        }

        private String convertBroadcast(String broadcastId) {
            if (String.valueOf(CommonConstants.CIBN).equals(broadcastId)
                    || String.valueOf(CommonConstants.LETV).equals(broadcastId)) {
                return broadcastId;
            }
            return broadcastId == null ? null : "";
        }

        @Override
        public String getParameter(String name) {
            // TODO Auto-generated method stub
            if (BROADCAST_PARAM.equals(name)) {
                return convertBroadcast(super.getParameter(name));
            }
            return super.getParameter(name);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            // TODO Auto-generated method stub
            /**
             * J2EE规范,getParameterMap()方法必须返回不可修改的map。无法对原生态map进行修改
             * 不同容器对此方法的具体实现不同,重写此方法，保证返回的map符合规范，保证代码逻辑与容器无关
             */
            Map<String, String[]> targetMap = new HashMap<String, String[]>();
            Map<String, String[]> map = super.getParameterMap();
            if (map != null && map.size() > 0) {
                for (Map.Entry<String, String[]> entry : map.entrySet()) {
                    if (BROADCAST_PARAM.equals(entry.getKey())) {
                        String[] broadcastIds = entry.getValue();
                        if (broadcastIds != null && broadcastIds.length > 0) {
                            targetMap.put(entry.getKey(), new String[] { convertBroadcast(broadcastIds[0]) });
                            continue;
                        }
                    }
                    targetMap.put(entry.getKey(), entry.getValue());
                }
            }
            return Collections.unmodifiableMap(targetMap);
        }

        @Override
        public String[] getParameterValues(String name) {
            // TODO Auto-generated method stub
            if (BROADCAST_PARAM.equals(name)) {
                return new String[] { convertBroadcast(super.getParameter(name)) };
            }
            return super.getParameterValues(name);
        }

    }

}
