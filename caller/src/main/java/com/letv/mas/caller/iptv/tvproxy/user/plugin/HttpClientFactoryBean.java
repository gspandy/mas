package com.letv.mas.caller.iptv.tvproxy.user.plugin;

import com.letv.mas.caller.iptv.tvproxy.user.config.RestConfig;
import com.letv.mas.caller.iptv.tvproxy.user.util.StringUtil;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Factory to create @{@link org.apache.http.client.HttpClient}
 */
public class HttpClientFactoryBean extends AbstractFactoryBean<HttpClient> {
    private static Logger logger = LoggerFactory.getLogger(HttpClientFactoryBean.class);

    @Autowired
    public RestConfig restConfig;

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }

    public HttpClientFactoryBean(RestConfig restConfig){
        super();
        this.restConfig = restConfig;
    }

    public HttpClientFactoryBean(){
        super();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    protected HttpClient createInstance() {
        if (restConfig == null) {
            HttpClients.custom().build();
        }
        int maxPerRoute = restConfig.getMaxConnections();
        int maxTotal = restConfig.getMaxTotalConnections();
        int connectTimeout = restConfig.getConnectionTimeout();
        int socketTimeout = restConfig.getTimeout();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        connManager.setDefaultMaxPerRoute(maxPerRoute);
        connManager.setMaxTotal(maxTotal);
        connManager.setValidateAfterInactivity(1000);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).build();

        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

                if (executionCount > 3) {
                    logger.error("******** HttpClient failed to send request, already retry 3 times!!! ********");
                    return false;
                }
                // retry when SocketException/NoHttpResponseException occurred
                if ((exception instanceof SocketException && "Connection reset".equals(exception.getMessage()))
                        || exception instanceof NoHttpResponseException) {
                    return true;
                }
                return false;
            }
        };

        ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // Honor 'keep-alive' header
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                // default 30s instead of default -1
                return 30 * 1000;
            }
        };
        /**
         * 增加httpClient的代理设置，从jvm启动参数里读取，测试环境使用
         */
        HttpHost host = null;
        Properties props = System.getProperties();
        if (props != null) {
            String proxy = props.getProperty("http.proxyHost", null);
            int port = StringUtil.toInteger(props.getProperty("http.proxyPort", "0"));
            if (StringUtil.isNotBlank(proxy) && port > 0) {
                host = new HttpHost(proxy, port);
                return HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
                        .setRetryHandler(retryHandler).setKeepAliveStrategy(keepAliveStrategy).setProxy(host).build();
            }
        }
        return HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler).setKeepAliveStrategy(keepAliveStrategy).build();
    }
}
