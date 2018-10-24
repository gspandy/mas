package com.letv.mas.router.iptv.tvproxy.interceptor;

import com.letv.mas.router.iptv.tvproxy.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by leeco on 18/10/9.
 */
public class HttpClientLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        long stime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        String splitSymbol = "|";
        ClientHttpResponse response = clientHttpRequestExecution.execute(request, body);
        if (LOGGER.isInfoEnabled()) {
            sb.append(TimeUtil.getISO8601Timestamp(new Date())).append(splitSymbol)
                    .append(request.getURI()).append(splitSymbol)
                    .append(request.getMethod()).append(splitSymbol)
                    .append(response.getStatusCode()).append(splitSymbol)
                    .append(System.currentTimeMillis() - stime);
            LOGGER.info(sb.toString());
        }
        return response;
    }


}
