package com.letv.mas.router.iptv.tvproxy.interceptor;

import com.letv.mas.router.iptv.tvproxy.config.BizConfig;
import com.letv.mas.router.iptv.tvproxy.service.AuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by leeco on 18/10/16.
 */
@Component
public class ApiAuthFilter extends OncePerRequestFilter {
    private static final PathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private AuthService authService;

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private BizConfig bizConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isProtectedUrl(httpServletRequest)) {
                String token = httpServletRequest.getHeader("Authorization");
                if (null == token) {
                    token = "null";
                }
                if ("null".equals(token) || !authService.verifyToken(null, token)) {
                    throw new IllegalStateException("Invalid Token[" + token + "]");
                }
            }
        } catch (Exception e) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean isProtectedUrl(HttpServletRequest request) {
        boolean ret = false;
        String path = null;
        if (null != this.zuulProperties && null != bizConfig
                && null != bizConfig.getAuthUrls() && bizConfig.getAuthUrls().size() > 0) {
            for (ZuulProperties.ZuulRoute route : this.zuulProperties.getRoutes().values()) {
                path = bizConfig.getAuthUrls().get(route.getUrl());
                if (StringUtils.isBlank(path)) {
                    path = bizConfig.getAuthUrls().get(route.getServiceId());
                }
                if (pathMatcher.match(route.getPath(), request.getServletPath())) {
                    ret = StringUtils.isNotBlank(path) ? pathMatcher.match(path, request.getServletPath()) : false;
                    break;
                }
            }
        }

        return ret;
    }
}
