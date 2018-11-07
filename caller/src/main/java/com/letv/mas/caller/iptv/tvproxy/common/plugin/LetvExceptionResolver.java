package com.letv.mas.caller.iptv.tvproxy.common.plugin;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ErrorCodeConstant;
import com.letv.mas.caller.iptv.tvproxy.common.util.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LetvExceptionResolver implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        String langCode = request.getParameter("langcode");

        /*
         * 这里设置默认值，防止调用MessageUtils.getMessage(String code, String lang)返回null
         * 而导致调用URLEncoder.encode(String s, String enc)抛出NullPointException
         */
        if (StringUtils.isBlank(langCode)) {
            langCode = MessageUtils.DEFAULT_FULL_LOCAL_ZH_CN;
        }

        String errorCode = "-100";
        String message = "";
        try {
            message = URLEncoder.encode(MessageUtils.getMessage("SYSTEM_ERROR", langCode), "UTF-8");
            if (StringUtils.isEmpty(message)) {
                message = URLEncoder.encode("服务异常请重试！", "UTF-8");
            }
        } catch (UnsupportedEncodingException e1) {
            this.logger.error(ex.getMessage(), ex);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        String path = request.getPathInfo();
        String extension = this.getExtension(path);

        if (ex instanceof LetvCommonException) {
            LetvCommonException exception = (LetvCommonException) ex;
            errorCode = exception.getErrorCode();
            try {
                message = URLEncoder.encode(exception.getMessage(), "UTF-8");
                // 针对1201的特殊处理，1201使用自己的错误码
                if (!StringUtils.equals(ErrorCodeConstant.LETV_LEKA_RECHARGE_ERROR, errorCode)) {
                    message = URLEncoder.encode(MessageUtils.getMessage(errorCode, langCode), "UTF-8");
                    if (StringUtils.isEmpty(message)) {
                        message = URLEncoder.encode(exception.getMessage(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                this.logger.error(ex.getMessage(), ex);
            }
        }
        ModelAndView mv = new ModelAndView();
        String errorURL = "/iptv/api/v2/errorHandler" + extension + "?path=" + path + "&resultStatus=0" + "&httpStatusCode="
                + HttpStatus.BAD_REQUEST.value() + "&message=" + message + "&errorCode=" + errorCode + "&exception="
                + ex;
        mv.setView(new InternalResourceView(errorURL));
        if (ex instanceof LetvCommonException) {
            this.logger.warn(ex.getMessage());
        } else {
            this.logger.error(ex.getMessage(), ex);
        }
        return mv;
    }

    private String getExtension(String path) {
        if (path != null && path.length() > 0) {
            if (path.indexOf(".") != -1) {
                return path.substring(path.indexOf("."));
            }
        }
        return "";
    }
}
