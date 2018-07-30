package com.letv.mas.common.stream.model.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 消息确认入站通道
 *
 * @author dalvikchen
 * @date 18/7/11
 */
public interface MessageConfirmSink {
    String INPUT = "message_confirm_input";

    @Input(INPUT)
    SubscribableChannel input();
}
