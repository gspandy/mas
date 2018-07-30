package com.letv.mas.router.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leeco on 18/7/26.
 */
@Configuration
public class TomcatConfig {
    private final static int MAX_KEEPALIVE_REQUESTS = 1000;
    private final static int CONNECTION_TIMEOUT = 2000;

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

        factory.addConnectorCustomizers(new TomcatConnectorCustomizerImpl());

        return factory;
    }

    class TomcatConnectorCustomizerImpl implements TomcatConnectorCustomizer {

        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            protocol.setMaxKeepAliveRequests(TomcatConfig.MAX_KEEPALIVE_REQUESTS);
            protocol.setConnectionTimeout(TomcatConfig.CONNECTION_TIMEOUT);
        }
    }
}
