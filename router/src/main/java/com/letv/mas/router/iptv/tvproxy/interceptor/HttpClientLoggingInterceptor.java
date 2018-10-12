package com.letv.mas.router.iptv.tvproxy.interceptor;

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
            sb.append(this.getISO8601Timestamp(new Date())).append(splitSymbol)
                    .append(request.getURI()).append(splitSymbol)
                    .append(request.getMethod()).append(splitSymbol)
                    .append(response.getStatusCode()).append(splitSymbol)
                    .append(System.currentTimeMillis() - stime);
            LOGGER.info(sb.toString());
        }
        return response;
    }

    /**
     * 传入Data类型日期，返回字符串类型时间（ISO8601标准时间）
     *
     * @param date
     * @return
     */
    private String getISO8601Timestamp(Date date) {
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        df.setTimeZone(tz);
        String nowAsISO = df.format(date);
        return nowAsISO;
    }
}
