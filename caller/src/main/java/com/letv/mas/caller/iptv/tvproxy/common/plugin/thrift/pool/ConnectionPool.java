package com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.pool;

import com.letv.mas.caller.iptv.tvproxy.common.plugin.thrift.AddressProvider;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionPool extends GenericObjectPool<Object> {

    private String socketType;

    public ConnectionPool(AddressProvider addressProvider, String socketType, int maxActive, int maxIdle, int minIdle,
                          long maxWait) throws Exception {
        /**
         * 池策略：最大连接数，最大等待时间，最大空闲数，最小空闲数由人工配置，
         * 最大连接数尽量=最大空闲数，最小空闲数尽量为0，以便清除无用线程
         * 其他参数写死：
         * GenericObjectPool.WHEN_EXHAUSTED_BLOCK：获取连接时阻塞超时时抛出异常
         * GenericObjectPool.DEFAULT_TEST_ON_BORROW
         * GenericObjectPool.DEFAULT_TEST_ON_RETURN
         * 60*1000，检测空闲线程60秒运行一次
         * 5，检测空闲线程运行一次检测 5条连接
         * 60*10*1000，空闲线程最小空闲时间10分钟，超过10分钟后会被检测线程 remove
         * GenericObjectPool.DEFAULT_TEST_WHILE_IDLE
         * GenericObjectPool.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS
         * GenericObjectPool.DEFAULT_LIFO 后入先出队列，保证不是所有的连接都在使用，及时被清除
         */
        super(new ConnectionFactory(addressProvider, socketType), maxActive, GenericObjectPool.WHEN_EXHAUSTED_BLOCK,
                maxWait, maxIdle, minIdle, GenericObjectPool.DEFAULT_TEST_ON_BORROW,
                GenericObjectPool.DEFAULT_TEST_ON_RETURN, 60 * 1000, 5, 60 * 10 * 1000,
                GenericObjectPool.DEFAULT_TEST_WHILE_IDLE,
                GenericObjectPool.DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, GenericObjectPool.DEFAULT_LIFO);
        this.socketType = socketType;
    }

    public String getSocketType() {
        return socketType;
    }

}
