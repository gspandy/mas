package com.letv.mas.caller.iptv.tvproxy.user.config;

import com.letv.mas.caller.iptv.tvproxy.user.annotation.Iptv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*@Configuration
@EnableSwagger2
@Iptv*/
public class Swagger2Config {

    @Bean
    public Docket user_docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("user")
                .apiInfo(new ApiInfoBuilder().title("[超级影视］代理层接口WIKI").version("V2.0.0").build()).useDefaultResponseMessages(false).select()
                .apis(RequestHandlerSelectors.basePackage("com.letv.mas.caller.iptv")).build()
                .pathMapping("/iptv/api/new/");
    }
}
