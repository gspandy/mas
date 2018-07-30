package com.letv.mas.common.stream.model.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 消息确认出站通道绑定声明接口
 *
 * @author dalvikchen
 * @date 18/7/9
 */
public interface MessageConfirmSource {
    String OUTPUT = "message_confirm_output";

    @Output(OUTPUT)
    MessageChannel output();
}
