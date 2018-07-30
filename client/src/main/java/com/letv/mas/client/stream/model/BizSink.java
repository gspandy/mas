package com.letv.mas.client.stream.model;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.MessageChannel;

/**
 * Created by leeco on 18/7/11.
 */
public interface BizSink {
    String BROADCAST_INPUT = "mas-biz-broadcast-input";

    @Input(BROADCAST_INPUT)
    MessageChannel broadcastInput();
}
