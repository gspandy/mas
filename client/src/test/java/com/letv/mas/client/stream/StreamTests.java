package com.letv.mas.client.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 该测试用例使用联调环境测试
 * <p>
 * 需事先启动n个client和n个caller
 * 根据client和caller的数量来进行断言消息的接收数量
 * <p>
 * Created by dalvikchen on 18/7/11.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EurekaClientApplication.class, properties = {"spring.profiles.active:bootprod", "CLIENT_SERVER_PORT:18881", "local.server.port:18881", "CONFIG_SERVER_DOMAIN:http://config.mas.letv.cn", "BROADCAST_TOPIC:broadcast-topic-ut", "MULTICAST_TOPIC:multicast-topic-ut", "PARTITION_TOPIC:partition-topic-ut", "POINTCAST_TOPIC:pointcast-topic-ut", "CONFIRM_TOPIC:message-confirm-topic-ut", "BUS_TOPIC:springCloudBus-ut"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ConditionalOnProperty(value = "spring.cloud.stream.message.enabled", havingValue = "true", matchIfMissing = false)
@EnableBinding(MessageConfirmSink.class)
public class StreamTests {
    private static final Logger log = LoggerFactory.getLogger(StreamTests.class);

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
    public void contextLoads() {
        log.info("load the context ...");
    }

    @Test
    public void testBroadcast() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String content = "TestMsg:".concat(id);
        this.mockMvc.perform(get(this.baseUrl + "broadcast/msg")
                .param("content", content))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Broadcasting Message:" + content)));

        List<ConfirmMessage> confirmMessages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        MessageHandler broadcast = new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                ConfirmMessage payload = (ConfirmMessage) message.getPayload();
                StringBuilder sb = new StringBuilder();
                sb.append("\n======================");
                sb.append("\n消息被确认接收!确认信息：");
                sb.append("\n消息类型：" + payload.getMessageType());
                sb.append("\n确认方：" + payload.getReceiver());
                try {
                    sb.append("\n原始消息：" + objectMapper.writeValueAsString(payload.getOriginMessage()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                log.info(sb.toString());
                confirmMessages.add(payload);
                // 断言消息类型为broadcast
                assertThat(payload.getMessageType()).isEqualToIgnoringCase("broadcast");
                // 断言消息内容一致
                assertThat(payload.getOriginMessage().getContent()).isEqualToIgnoringCase(content);
            }
        };
        messageConfirmSink.input().subscribe(broadcast);
        Thread.sleep(10000L);
        messageConfirmSink.input().unsubscribe(broadcast);
        log.info("最终确认数量：" + confirmMessages.size());
        // 根据所有启动的服务实例数量断言,所有client以及所有caller，包含该测试用例启动的client实例
        assertThat(confirmMessages.size()).isEqualTo(clientCount + callerCount + 1);
    }

    @Test
    public void testMulticast() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String content = "TestMsg:".concat(id);
        this.mockMvc.perform(get(this.baseUrl + "multicast/msg")
                .param("content", content))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Multicasting Message:" + content)));

        List<ConfirmMessage> confirmMessages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler multicast = new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                ConfirmMessage payload = (ConfirmMessage) message.getPayload();
                StringBuilder sb = new StringBuilder();
                sb.append("\n======================");
                sb.append("\n消息被确认接收!确认信息：");
                sb.append("\n消息类型：" + payload.getMessageType());
                sb.append("\n确认方：" + payload.getReceiver());
                try {
                    sb.append("\n原始消息：" + objectMapper.writeValueAsString(payload.getOriginMessage()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                log.info(sb.toString());
                confirmMessages.add(payload);
                // 断言消息类型为multicast
                assertThat(payload.getMessageType()).isEqualToIgnoringCase("multicast");
                // 断言消息内容一致
                assertThat(payload.getOriginMessage().getContent()).isEqualToIgnoringCase(content);
            }
        };
        messageConfirmSink.input().subscribe(multicast);

        Thread.sleep(10000L);
        messageConfirmSink.input().unsubscribe(multicast);
        log.info("最终确认数量：" + confirmMessages.size());
        // 根据所有启动的服务实例数量断言，client组和caller组各一个
        assertThat(confirmMessages.size()).isEqualTo(1 + 1);
    }

    @Test
    public void testPartition() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String content = "TestMsg:".concat(id);
        this.mockMvc.perform(get(this.baseUrl + "partition/msg")
                .param("content", content)
                .param("type", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Partitioning Message:" + content + " Type:1")));

        this.mockMvc.perform(get(this.baseUrl + "partition/msg")
                .param("content", content)
                .param("type", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Partitioning Message:" + content + " Type:2")));

        List<ConfirmMessage> confirmMessages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler partition = new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                ConfirmMessage payload = (ConfirmMessage) message.getPayload();
                StringBuilder sb = new StringBuilder();
                sb.append("\n======================");
                sb.append("\n消息被确认接收!确认信息：");
                sb.append("\n消息类型：" + payload.getMessageType());
                sb.append("\n确认方：" + payload.getReceiver());
                try {
                    sb.append("\n原始消息：" + objectMapper.writeValueAsString(payload.getOriginMessage()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                log.info(sb.toString());
                confirmMessages.add(payload);
                // 断言消息类型为partition
                assertThat(payload.getMessageType()).isEqualToIgnoringCase("partition");
                // 断言消息内容一致
                assertThat(payload.getOriginMessage().getContent()).isEqualToIgnoringCase(content);
            }
        };
        messageConfirmSink.input().subscribe(partition);

        Thread.sleep(10000L);
        messageConfirmSink.input().unsubscribe(partition);
        log.info("最终确认数量：" + confirmMessages.size());
        HashSet<String> receivers = new HashSet<>();
        for (ConfirmMessage confirmMessage : confirmMessages) {
            receivers.add(confirmMessage.getReceiver());
        }

        // 断言消息确认数
        assertThat(confirmMessages.size()).isEqualTo(2);
        // 断言最终被不同的实例所消费的数量
        assertThat(receivers.size()).isEqualTo(confirmMessages.size());
    }

    @Test
    public void testPointcast() throws Exception {
        String id = UUID.randomUUID().toString().replace("-", "");
        String content = "TestMsg:".concat(id);
        this.mockMvc.perform(get(this.baseUrl + "pointcast/msg")
                .param("content", content)
                .param("partition", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pointcasting Message:" + content + " Partition:1")));

        this.mockMvc.perform(get(this.baseUrl + "pointcast/msg")
                .param("content", content)
                .param("partition", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pointcasting Message:" + content + " Partition:2")));

        List<ConfirmMessage> confirmMessages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHandler pointcast = new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                ConfirmMessage payload = (ConfirmMessage) message.getPayload();
                StringBuilder sb = new StringBuilder();
                sb.append("\n======================");
                sb.append("\n消息被确认接收!确认信息：");
                sb.append("\n消息类型：" + payload.getMessageType());
                sb.append("\n确认方：" + payload.getReceiver());
                try {
                    sb.append("\n原始消息：" + objectMapper.writeValueAsString(payload.getOriginMessage()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                log.info(sb.toString());
                confirmMessages.add(payload);
                // 断言消息类型为partition
                assertThat(payload.getMessageType()).isEqualToIgnoringCase("pointcast");
                // 断言消息内容一致
                assertThat(payload.getOriginMessage().getContent()).isEqualToIgnoringCase(content);
            }
        };
        messageConfirmSink.input().subscribe(pointcast);

        Thread.sleep(10000L);
        messageConfirmSink.input().unsubscribe(pointcast);
        log.info("最终确认数量：" + confirmMessages.size());
        HashSet<String> receivers = new HashSet<>();
        for (ConfirmMessage confirmMessage : confirmMessages) {
            receivers.add(confirmMessage.getReceiver());
        }

        // 断言消息确认数
        assertThat(confirmMessages.size()).isEqualTo(2);
        // 断言最终被同一的实例所消费的数量
        assertThat(receivers.size()).isEqualTo(1);
    }
}
