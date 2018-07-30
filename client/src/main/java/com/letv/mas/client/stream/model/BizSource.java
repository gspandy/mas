package com.letv.mas.client.stream.model;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Created by leeco on 18/7/11.
 */
public interface BizSource {
    String BROADCAST_OUTPUT = "mas-biz-broadcast-output";

    @Output(BROADCAST_OUTPUT)
    MessageChannel broadcastOutput();
}
