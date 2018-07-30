package com.letv.mas.client.stream.strategy;

import org.springframework.cloud.stream.binder.PartitionSelectorStrategy;
import scala.Int;

/**
 * 简单分区选择类
 * 该类主要通过分区key和分区数量进行分区的选择
 * 未配置分区选择类或者分区选择表达式的情况下，默认分区选择策略为：
 * hashCode(key) % partitionCount
 * <p>
 * Created by dalvikchen on 18/6/14.
 */
public class PartitionSelector implements PartitionSelectorStrategy {
    @Override
    public int selectPartition(Object key, int partitionCount) {
        /**
         * 这里作为一个例子，将所有的分区都配置为第1个分区，则所有的消息最终都会同一个消费者获取。
         * 生产环境可以根据需要配置相应的分区选择策略，或者使用默认的分区选择策略。
         */
        return (int) key;
    }
}
