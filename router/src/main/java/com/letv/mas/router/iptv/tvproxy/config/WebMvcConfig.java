package com.letv.mas.router.iptv.tvproxy.config;

import com.fasterxml.classmate.TypeResolver;
import com.letv.mas.router.iptv.tvproxy.interceptor.CheckLoginInterceptor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.*;
import static springfox.documentation.schema.AlternateTypeRules.*;

import java.lang.reflect.WildcardType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * refer: http://springfox.github.io/springfox/docs/current/
 */
@ComponentScan(basePackages = "com.letv.mas.router.iptv.tvproxy.*", excludeFilters = {@ComponentScan.Filter(Configuration.class)})
@Configuration
@EnableSwagger2
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter implements EnvironmentAware {

    @Autowired
    private TypeResolver typeResolver;

    @Autowired
    ZuulProperties zuulProperties;

    @Bean
    public CheckLoginInterceptor checkLoginInterceptor() {
        return new CheckLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkLoginInterceptor()).addPathPatterns(new String[]{"/i/auth/**"});
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/null", "/");
//        registry.addRedirectViewController("/null/swagger-resources", "/swagger-resources");
//        registry.addRedirectViewController("/null/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
//        registry.addRedirectViewController("/null/swagger-resources/configuration/security","/swagger-resources/configuration/security");
        super.addViewControllers(registry);
    }

    @ConditionalOnProperty(value = "iptv.doc.enabled", havingValue = "true", matchIfMissing = false)
    @Bean
    public Docket proxyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.letv.mas.router.iptv.tvproxy.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(500)
                                .message("内部服务器错误!")
                                .responseModel(new ModelRef("Error"))
                                .build()))
                .enableUrlTemplating(true);
//                .tags(new Tag("大屏微服务代理层", "接口文档"));
    }

    /**
     * 定制upsteam服务
     * http://host/swagger-resources
     *
     * @return
     */
    @ConditionalOnProperty(value = "iptv.doc.enabled", havingValue = "true", matchIfMissing = false)
    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            String name = null;
            for (SwaggerResource swaggerResource : resources) {
                if (StringUtils.isBlank(swaggerResource.getName())
                        || swaggerResource.getName().equals("default")) {
                    name = swaggerResource.getLocation();
                    if (StringUtils.isBlank(name)) {
                        name = swaggerResource.getUrl();
                    }
                    if (StringUtils.isBlank(name)) {
                        name = swaggerResource.getName();
                    }
                    swaggerResource.setName(name);
                }
            }
            zuulProperties.getRoutes().values().stream()
                    .forEach(route -> {
                        SwaggerResource swaggerResource = createResource(route.getServiceId(), route.getServiceId(), "2.0", route);
                        if (null != swaggerResource) {
                            resources.add(swaggerResource);
                        }
                    });
            return resources;
        };
    }

    /**
     * 定制UI界面
     *
     * @return
     */
    @ConditionalOnProperty(value = "iptv.doc.enabled", havingValue = "true", matchIfMissing = false)
    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("大屏/微服务/代理层网关")
                .description("接口文档")
                .version("1.0.0")
                .contact(new Contact("dengliwei", "http://omp.mas.letv.cn", "dengliwei@le.com"))
                .build();
    }

    private SwaggerResource createResource(String name, String location, String version,
                                           ZuulProperties.ZuulRoute route) {
        SwaggerResource swaggerResource = null;
        if (route.getPath().contains("api-docs")) {
            swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            if (StringUtils.isBlank(location)) {
                if (StringUtils.isNotBlank(route.getUrl())) {
                    String url = route.getUrl();
                    if (url.lastIndexOf("/") == url.length() - 1) {
                        url = url.substring(0, url.length() - 1);
                    }
                    location = route.getPath().replaceAll("/\\*\\*", "");
//                    if (url.startsWith("http")) {
//                        swaggerResource.setUrl(url);
//                    }
                }
            }
            swaggerResource.setName(location);
            swaggerResource.setLocation(location + "/v2/api-docs");
            swaggerResource.setSwaggerVersion(version);
        }
        return swaggerResource;
    }

    @Override
    public void setEnvironment(Environment environment) {
        // TODO
    }
}
