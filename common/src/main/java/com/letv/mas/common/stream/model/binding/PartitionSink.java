package com.letv.mas.common.stream.model.binding;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 分区入站通道绑定声明接口
 *
 * @author dalvikchen
 * @date 18/7/9
 */
public interface PartitionSink {
    String INPUT = "partition_input";

    @Input(INPUT)
    SubscribableChannel input();
}
