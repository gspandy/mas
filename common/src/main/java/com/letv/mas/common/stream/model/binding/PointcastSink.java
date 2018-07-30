package com.letv.mas.common.stream.model.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 点播入站通道绑定声明接口
 *
 * @author dalvikchen
 * @date 18/7/9
 */
public interface PointcastSink {
    String INPUT = "pointcast_input";

    @Input(INPUT)
    SubscribableChannel input();
}
