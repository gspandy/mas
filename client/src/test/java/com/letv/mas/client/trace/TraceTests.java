package com.letv.mas.client.trace;

import com.letv.mas.client.EurekaClientApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by wangsk on 2018/7/9.
 */
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.letv.mas.common.config", "com.letv.mas.client", "com.letv.mas.common.bus"})
@SpringBootTest(classes = EurekaClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,properties = {"spring.profiles.active:traceprod","local.server.port:8888","eureka.client.registry-fetch-interval-seconds:1"})
public class TraceTests {
    private static final Logger log = LoggerFactory.getLogger(TraceTests.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;
    private URL baseUrl;
    @LocalServerPort
    private int basePort;

    @Before
    public void bootstrap() throws Exception {
        String url = String.format("http://localhost:%d/", this.basePort);
        log.info("port is {}", this.basePort);
        this.baseUrl = new URL(url);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        try {
            Thread.sleep(10000);
        }catch (Exception e){

        }
    }

    /**
     * Hello world 测试
     */
   @Test
    public void testApi4TraceHelloWorld() {
        String result = this.restTemplate.getForObject(this.baseUrl + "trace/helloworld?name=test", String.class);
        assertThat(result).isEqualTo("hi welcome!");
    }

    /**
     * 测试访问外部http资源
     */
    @Test
    public void testApi4TraceHttpReq() {
        Map result = this.restTemplate.getForObject(this.baseUrl + "trace/terminalconfig?name=test", Map.class);
        assertThat(result != null).isEqualTo(true);
    }

    /**
     * 多层服务调用测试
     */
    @Test
    public void testApi4TraceInvokeDepth() {
        String result = this.restTemplate.getForObject(this.baseUrl + "trace/custom/invokeDepth?depth=10", String.class);
        assertThat(result != null).isEqualTo(true);
    }

    @Test
    public void testApi4Chain() {
        Map result = this.restTemplate.getForObject(this.baseUrl + "trace/invoke/chain", Map.class);
        assertThat(result != null).isEqualTo(true);
    }

    /**
     * 共用工作单元
     */
    @Test
    public void testApi4ContinueSpan() {
        Boolean result = this.restTemplate.getForObject(this.baseUrl + "trace/custom/continueSpan?name=continueSpan", Boolean.class);
        assertThat(result != null).isEqualTo(true);
    }

    /**
     * 自定义工作单元
     */
    @Test
    public void testApi4createSpan() {
        Boolean result = this.restTemplate.getForObject(this.baseUrl + "trace/custom/createSpan?name=createSpan", Boolean.class);
        assertThat(result != null).isEqualTo(true);
    }
    /**
     * 调试链消息丢失,功能测试类
     * 测测试丢消息需要独立压力测试.
     */
    @Test
    public void testApi4MissTrace() {
        String result = this.restTemplate.getForObject(this.baseUrl + "trace/custom/miss/trace?depth=3&name=testApi4MissTrace1", String.class);
        assertThat(result != null).isEqualTo(true);
    }
}