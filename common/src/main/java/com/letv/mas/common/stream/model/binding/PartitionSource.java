package com.letv.mas.common.stream.model.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 分区出站通道绑定声明接口
 *
 * @author dalvikchen
 * @date 18/7/9
 */
public interface PartitionSource {
    String OUTPUT = "partition_output";

    @Output(OUTPUT)
    MessageChannel output();
}
