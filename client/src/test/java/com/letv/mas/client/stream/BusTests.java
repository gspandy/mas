package com.letv.mas.client.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letv.mas.client.EurekaClientApplication;
import com.letv.mas.common.stream.model.binding.MessageConfirmSink;
import com.letv.mas.common.stream.model.dto.ConfirmMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by dalvikchen on 18/7/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientApplication.class, properties = {"spring.profiles.active:bootprod", "CLIENT_SERVER_PORT:18882", "local.server.port:18882", "CONFIG_SERVER_DOMAIN:http://config.mas.letv.cn", "CONFIRM_TOPIC:message-confirm-topic-ut", "BUS_TOPIC:springCloudBus-ut"}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(MessageConfirmSink.class)
public class BusTests {
    private static final Logger log = LoggerFactory.getLogger(BusTests.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestRestTemplate restTemplate;

    private MockMvc mockMvc;

    @LocalServerPort
    private int basePort;

    private URL baseUrl;

    // 运行的client实例数量
    private int clientCount = 2;
    // 运行的caller实例数量
    private int callerCount = 2;

    @Autowired
    private MessageConfirmSink messageConfirmSink;

    @Before
    public void bootstrap() throws Exception {
        String url = String.format("http://localhost:%d/", this.basePort);
        log.info("port is {}", this.basePort);
        this.baseUrl = new URL(url);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testBroadcastBusEvent() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String content = "TestBroadcastBusEvent";
        this.mockMvc.perform(post(this.baseUrl + "bus/pub")
                .param("msg", content))
                .andExpect(status().isOk());

        List<ConfirmMessage> confirmMessages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler messageHandler = message -> {
            ConfirmMessage payload = (ConfirmMessage) message.getPayload();
            StringBuilder sb = new StringBuilder();
            sb.append("\n======================");
            sb.append("\n事件被确认接收!确认信息：");
            sb.append("\n消息类型：" + payload.getMessageType());
            sb.append("\n确认方：" + payload.getReceiver());
            log.info(sb.toString());
            confirmMessages.add(payload);
            // 断言消息类型为custom-application-event
            assertThat(payload.getMessageType()).isEqualToIgnoringCase("custom-application-event");
        };
        messageConfirmSink.input().subscribe(messageHandler);

        Thread.sleep(10000L);
        messageConfirmSink.input().unsubscribe(messageHandler);
        log.info("最终确认数量：" + confirmMessages.size());
        // 根据所有启动的服务实例数量断言,所有client以及所有caller，包含该测试用例启动的client实例
        assertThat(confirmMessages.size()).isEqualTo(callerCount);
    }

    @Test
    public void testAssignBusEvent() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String content = "TestAssignBusEvent";
        this.mockMvc.perform(post(this.baseUrl + "bus/pub")
                .param("msg", content)
                .param("destination", "letv-mas-caller-busstream:zipkin,stream,pointcast,partition,multicast,eureka,confirm,bus,broadcast,bootprod:0"))
                .andExpect(status().isOk());

        List<ConfirmMessage> confirmMessages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler messageHandler = message -> {
            ConfirmMessage payload = (ConfirmMessage) message.getPayload();
            StringBuilder sb = new StringBuilder();
            sb.append("\n======================");
            sb.append("\n事件被确认接收!确认信息：");
            sb.append("\n消息类型：" + payload.getMessageType());
            sb.append("\n确认方：" + payload.getReceiver());
            log.info(sb.toString());
            confirmMessages.add(payload);
            // 断言消息类型为custom-application-event
            assertThat(payload.getMessageType()).isEqualToIgnoringCase("custom-application-event");
        };
        messageConfirmSink.input().subscribe(messageHandler);

        Thread.sleep(10000L);
        messageConfirmSink.input().unsubscribe(messageHandler);
        log.info("最终确认数量：" + confirmMessages.size());
        // 根据所有启动的服务实例数量断言,所有client以及所有caller，包含该测试用例启动的client实例
        assertThat(confirmMessages.size()).isEqualTo(1);
    }
}
