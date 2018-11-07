package com.letv.mas.caller.iptv.tvproxy.common.model.dao.tp;

import com.letv.mas.caller.iptv.tvproxy.common.constant.ApplicationConstants;
import com.letv.mas.caller.iptv.tvproxy.common.util.ApplicationUtils;
import com.letv.mas.caller.iptv.tvproxy.common.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Author：apple on 18/3/12 17:07
 * eMail：dengliwei@le.com
 */
public class HockRestTemplate extends RestTemplate {

    /**
     * 上游接口黑名单
     */
    private final static Map<String, String> uriBlackList = new HashMap<String, String>();

    static {
        loadUriBlackList();
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> urlVariables) {
        ResponseEntity<T> response = checkUriBlackList(url, responseType);

        if (null == response) {
            response = super.getForEntity(url, responseType, urlVariables);
        }

        return response;
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... urlVariables) {
        ResponseEntity<T> response = checkUriBlackList(url, responseType);

        if (null == response) {
            response = super.getForEntity(url, responseType, urlVariables);
        }

        return response;
    }

    @Override
    public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) {
        ResponseEntity<T> response = checkUriBlackList(url.toString(), responseType);

        if (null == response) {
            response = super.getForEntity(url, responseType);
        }

        return response;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
            Map<String, ?> uriVariables) throws RestClientException {
        ResponseEntity<T> response = checkUriBlackList(url, responseType);

        if (null == response) {
            response = super.postForEntity(url, request, responseType, uriVariables);
        }

        return response;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(URI url, Object request, Class<T> responseType)
            throws RestClientException {
        ResponseEntity<T> response = checkUriBlackList(url.toString(), responseType);

        if (null == response) {
            response = super.postForEntity(url, request, responseType);
        }

        return response;
    }

    @Override
    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
            Object... uriVariables) throws RestClientException {
        ResponseEntity<T> response = checkUriBlackList(url, responseType);

        if (null == response) {
            response = super.postForEntity(url, request, responseType, uriVariables);
        }

        return response;
    }

    private static <T> ResponseEntity<T> checkUriBlackList(String url, Class<T> responseType) {
        ResponseEntity response = null;
        if (StringUtil.isNotBlank(url)) {
            try {
                URL uri = new URL(url);
                String tagUrl = uri.getHost() + uri.getPath();
                if (uriBlackList.containsKey(tagUrl)) {
                    response = new ResponseEntity<String>(uriBlackList.get(tagUrl), HttpStatus.NO_CONTENT);
                }
            } catch (Exception e) {

            }
        }

        return response;
    }

    private static void loadUriBlackList() {
        String uriStr = ApplicationUtils.get(ApplicationConstants.IPTV_APP_3RD_URI_BLACKLIST);
        if (StringUtil.isNotBlank(uriStr)) {
            String[] uriArr = StringUtil.getStringToArray(uriStr, ",");
            for (String uri : uriArr) {
                uriBlackList.put(uri, "\"{\"code\":0,\"msg\":\"hock down!\"}");
            }
        }
    }
}
