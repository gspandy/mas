package interceptor;

import com.letv.mas.router.iptv.tvproxy.util.JwtTokenUtil;
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
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    ZuulProperties zuulProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isProtectedUrl(httpServletRequest)) {
                String token = httpServletRequest.getHeader("Authorization");
                if (null == token) {
                    token = "null";
                }
                if ("null".equals(token) || !jwtTokenUtil.validateToken(token, null)) {
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

        if (null != this.zuulProperties) {
            for (ZuulProperties.ZuulRoute route : this.zuulProperties.getRoutes().values()) {
                if (pathMatcher.match(route.getPath(), request.getServletPath())) {
                    ret = true;
                    break;
                }
            }
        }

        // ret = pathMatcher.match("/api/**", request.getServletPath());

        return ret;
    }
}
