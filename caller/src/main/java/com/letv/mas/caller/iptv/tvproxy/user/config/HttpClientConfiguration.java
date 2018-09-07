package com.letv.mas.caller.iptv.tvproxy.user.config;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import com.letv.mas.caller.iptv.tvproxy.user.plugin.HttpClientFactoryBean;
import com.letv.mas.caller.iptv.tvproxy.user.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
@Iptv
public class HttpClientConfiguration {

    private static final Logger logger = Logger.getLogger(HttpClientConfiguration.class);

    /*@Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }*/
    /*@Bean
    public ServletRegistrationBean getServlet(){

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();

        ServletRegistrationBean regBean = new ServletRegistrationBean(streamServlet);

        regBean.setLoadOnStartup(1);

        List mappingList = new ArrayList();

        mappingList.add("/hystrix.stream");

        regBean.setUrlMappings(mappingList);

        regBean.setName("hystrixServlet");

        return regBean;

    }*/

    @Bean
    RestConfig getRestConfig() {
        logger.info("RestConfig init ....");
        return new RestConfig();
    }

    @Bean
    @RefreshScope
    HttpClientUtil getHttpClientUtil(RestConfig config) {
        logger.info("HttpClientUtil init ...."+config);
        try {
            HttpClientFactoryBean httpClientFactoryBean = new HttpClientFactoryBean(config);
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClientFactoryBean.getObject());
            return new HttpClientUtil(httpRequestFactory);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new LetvCommonException();
        }
        return null;
    }
}
