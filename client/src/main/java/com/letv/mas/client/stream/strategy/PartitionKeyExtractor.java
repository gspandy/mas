package com.letv.mas.client.stream.strategy;

import com.letv.mas.common.stream.model.dto.StreamMessage;
import org.springframework.cloud.stream.binder.PartitionKeyExtractorStrategy;
import org.springframework.messaging.Message;

/**
 * 分区key生成类
 * 可以根据payload中的某个字段，或者header中某个字段作为key
 * <p>
 * 该示例使用payload.type作为分区key
 * <p>
 *
 * @author dalvikchen
 * @date 18/6/12
 */
public class PartitionKeyExtractor implements PartitionKeyExtractorStrategy {
    @Override
    public Object extractKey(Message<?> message) {
        Object payload = message.getPayload();
        StreamMessage streamMessage = (StreamMessage) payload;
        System.out.println("PartitionKeyExtractor Message Key Extract:" + streamMessage.getPartition());
        return streamMessage.getPartition();
    }
}
