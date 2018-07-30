package com.letv.mas.common.config;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.jersey.EurekaJerseyClientImpl;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.naming.ConfigurationException;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by leeco on 18/6/7.
 */
@Configuration
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnProperty(value = "server.ssl.enabled", havingValue = "true", matchIfMissing = false)
public class EurekaSslConfig {
    private static final Logger log = LoggerFactory.getLogger(EurekaSslConfig.class);

    public EurekaSslConfig() {
        super();
        log.info("load the eureka ssl configs...");
    }

    @Value("${eureka.client.service-url.defaultZone}")
    private String defaultZone;

    @Value("${server.ssl.key-store}")
    private String trustStore;

    @Value("${server.ssl.key-store-password}")
    private String trustStorePassword;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.application.dir}")
    private String applicationDir;

    @Autowired
    private EurekaClientConfig config;

//    @ConditionalOnBean(DiscoveryClient.DiscoveryClientOptionalArgs.class)
    @Primary
    @Bean
    public DiscoveryClient.DiscoveryClientOptionalArgs getTrustStoredEurekaClient(SSLContext sslContext) {
        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
        EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();

        builder.withClientName(applicationName);
        builder.withMaxTotalConnections(config.getEurekaServerTotalConnections());
        builder.withMaxConnectionsPerHost(config.getEurekaServerTotalConnectionsPerHost());
        if (defaultZone.startsWith("https://")) {
            builder.withCustomSSL(sslContext);
//            builder.withSystemSSLConfiguration();
        }

        args.setEurekaJerseyClient(builder.build());
        return args;
    }

    @Bean
    public SSLContext sslContext() throws Exception {
        if (null == trustStore || StringUtils.isEmpty(trustStore)) throw new ConfigurationException();
        ClassPathResource resource = new ClassPathResource(trustStore.replaceAll("classpath:", ""));
        InputStream inputStream = resource.getInputStream();
        File tempFile = File.createTempFile(resource.getFilename() + ".", ".tmp", new File(applicationDir));
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        log.info("initialize ssl context bean with keystore {} ", tempFile.getName());
        try {
            FileCopyUtils.copy(inputStream, fileOutputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }

//        System.setProperty("javax.net.ssl.keyStore", tempFile.getPath());
//        System.setProperty("javax.net.ssl.keyStorePassword", trustStorePassword);
//        System.setProperty("javax.net.ssl.trustStore", tempFile.getPath());
//        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);

        return new SSLContextBuilder()
                .loadTrustMaterial(
                        tempFile,
                        trustStorePassword.toCharArray()
                ).build();
    }
}
