package com.letv.mas.client.demo;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by leeco on 18/6/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ManagerRegisterTests {
    private static final Logger log = LoggerFactory.getLogger(ManagerRegisterTests.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;

    @LocalServerPort
    private int basePort;

    private URL baseUrl;

    @Before
    public void bootstrap() throws Exception {
        String url = String.format("http://localhost:%d/", this.basePort);
        log.info("port is {}", this.basePort);
        this.baseUrl = new URL(url);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void contextLoads() {
        log.info("load the context ...");
    }

    @Test
    public void testApi4HelloWithRest() {
        String result = this.restTemplate.getForObject(this.baseUrl + "hi?name=test", String.class);
        assertThat(result).isEqualTo("hi test,i am from tz-1:8901");
    }

    @Test
    public void testApi4HelloWithMvc() throws Exception {
        this.mockMvc.perform(get(this.baseUrl + "hi?name=test")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("hi test,i am from")));
    }
}
