package com.letv.mas.caller.stream.service.listener;

import com.letv.mas.common.bus.CustomApplicationEvent;
import com.letv.mas.common.stream.model.binding.MessageConfirmSource;
import com.letv.mas.common.stream.model.dto.ConfirmMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.bus.ConditionalOnBusEnabled;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.UUID;

/**
 * bus事件监听器
 *
 * @author dalvikchen
 * @date 18/5/28
 */
@ConditionalOnBusEnabled
@EnableBinding({MessageConfirmSource.class})
public class BusEventListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${server.port}")
    private String port;

    private ApplicationContext context;

    @Autowired
    private MessageConfirmSource messageConfirmSource;

    public BusEventListener(ApplicationContext context) {
        this.context = context;
    }

    @EventListener
    private void pubListener(CustomApplicationEvent event) {
        logger.info("Caller Bus Listener Received Message:" + event.getMessage() +
                " Application ID:" + context.getId());

        // 发送确认消息
        ConfirmMessage confirmMessage = new ConfirmMessage();
        confirmMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        confirmMessage.setMessageType("custom-application-event");
        confirmMessage.setReceiver(context.getId() + ":" + port);
        confirmMessage.setDate(new Date());
        messageConfirmSource.output().send(MessageBuilder.withPayload(confirmMessage).build());
    }
}
