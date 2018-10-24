package config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
@ConditionalOnProperty(value = "iptv.doc.enabled", havingValue = "true", matchIfMissing = false)
@Configuration
@EnableSwagger2
public class Swagger2Config extends WebMvcConfigurerAdapter implements EnvironmentAware {

    @Autowired
    private TypeResolver typeResolver;

    @Autowired
    ZuulProperties zuulProperties;

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
     * @return
     */
//    @Primary
//    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            zuulProperties.getRoutes().values().stream()
                    .forEach(route ->
                            resources.add(createResource(route.getServiceId(), route.getServiceId(), "2.0", route)));
            return resources;
        };
    }

    /**
     * 定制UI界面
     * @return
     */
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
            swaggerResource.setLocation("/" + location + "/v2/api-docs");
            swaggerResource.setSwaggerVersion(version);
        }
        return swaggerResource;
    }

    @Override
    public void setEnvironment(Environment environment) {
        // TODO
    }
}
