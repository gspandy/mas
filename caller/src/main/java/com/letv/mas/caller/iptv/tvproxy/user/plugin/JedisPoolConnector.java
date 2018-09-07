package com.letv.mas.caller.iptv.tvproxy.user.plugin;


import com.letv.mas.caller.iptv.tvproxy.user.config.JedisConfig;
import com.letv.mas.caller.iptv.tvproxy.user.util.StringUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Redis连接池连接器，用于代替JedisPoolUtil
 * @author KevinYi
 */
/*@org.springframework.cloud.context.config.annotation.RefreshScope
@Lazy*/
//@Component
public class JedisPoolConnector {

    public static final int POOL_TYPE_MASTER = 0;
    public static final int POOL_TYPE_SLAVE = 1;
    public static boolean CHECK_EXCEPTION_ENABLE = false; // 连接池异常自动恢复开关

    private static int MASTER_CHECK_EXCEPTION_COUNT = 10;
    private static int SLAVE_CHECK_EXCEPTION_COUNT = 10;
    private static final long MASTER_CHECK_EXCEPTION_INTERVAL = 5 * 60 * 1000;// 主池检查时间间隔5分钟
    private static final long SLAVE_CHECK_EXCEPTION_INTERVAL = 5 * 60 * 1000;// 从池检查时间间隔5分钟

    private Logger logger = Logger.getLogger(JedisPoolConnector.class);

    private JedisPool slavepool = null;
    private JedisPool masterpool = null;

    private MsJedisPoolConfig slaveJedisPoolConfig = null;
    private MsJedisPoolConfig masterJedisPoolConfig = null;

    private volatile AtomicInteger masterPoolCheckCount;
    private volatile AtomicInteger slavePoolCheckCount;
    private volatile AtomicLong  masterPoolStartTime;
    private volatile AtomicLong  slavePoolStartTime;

    //@Autowired
    private JedisConfig jedisConfig;

    public JedisPoolConnector(JedisConfig jedisConfig) {
        this.jedisConfig = jedisConfig;
        init();
    }

    public void init() {
        initConfig();
        logger.info("初始化reids连接池完成. " + ((masterpool == null || slavepool == null) ? "异常" : "正常"));
    }

    /**
     * 获取一个 非切片客户端链接
     */
    public Jedis getMasterJedis() {
        Jedis jedis = null;
        try {
            jedis = masterpool.getResource();
        } catch (Exception e) {
            logger.error("pool.getResource() error!", e);
            if (!checkPoolIsAlive(POOL_TYPE_MASTER)) {
                restartMaster();
            }
        }
        return jedis;
    }

    /**
     * 获取一个 非切片客户端链接
     */
    public Jedis getSlaveJedis() {
        Jedis jedis = null;
        try {
            jedis = slavepool.getResource();
        } catch (Exception e) {
            logger.error("pool.getResource() error!", e);
            if (!checkPoolIsAlive(POOL_TYPE_SLAVE)) {
                restartSlave();
            }
        }
        return jedis;
    }

    public int getSlavePoolNumActive() {
        if (slavepool != null) {
            return slavepool.getNumActive();
        }
        return -1;
    }

    public int getSlavePoolNumWaiters() {
        if (slavepool != null) {
            return slavepool.getNumWaiters();
        }
        return -1;
    }

    public synchronized void restartMaster() {
        if (!masterpool.isClosed()) {
            masterpool.destroy();
            initMaster();
        }
    }

    public synchronized void restartSlave() {
        if (!slavepool.isClosed()) {
            slavepool.destroy();
            initSlave();
        }
    }

    public boolean checkPoolIsAlive(int poolType) {
        boolean isAlived = true;

        if (CHECK_EXCEPTION_ENABLE) {
            if (poolType == POOL_TYPE_MASTER) {
                if (this.masterPoolStartTime.get() == 0) { // 检查点开始计数
                    this.masterPoolStartTime.set(System.currentTimeMillis());
                }

                if ((System.currentTimeMillis() - this.masterPoolStartTime.get()) < MASTER_CHECK_EXCEPTION_INTERVAL) {
                    this.masterPoolCheckCount.incrementAndGet();
                } else {
                    if (this.masterPoolCheckCount.get() < MASTER_CHECK_EXCEPTION_COUNT) { // 超过检查间隔，计数器归0
                        this.masterPoolCheckCount.set(0);
                    } else {
                        isAlived = false;
                        logger.info("redis(" + poolType + ") is died.");
                    }
                }
            } else if (poolType == POOL_TYPE_SLAVE) {
                if (this.slavePoolStartTime.get() == 0) { // 检查点开始计数
                    this.slavePoolStartTime.set(System.currentTimeMillis());
                }

                if ((System.currentTimeMillis() - this.slavePoolStartTime.get()) < SLAVE_CHECK_EXCEPTION_INTERVAL) {
                    this.slavePoolCheckCount.incrementAndGet();
                } else {
                    if (this.slavePoolCheckCount.get() < SLAVE_CHECK_EXCEPTION_COUNT) { // 超过检查间隔，计数器归0
                        this.slavePoolCheckCount.set(0);
                    } else {
                        isAlived = false;
                        logger.info("redis(" + poolType + ") is died.");
                    }
                }
            }
        }

        return isAlived;
    }

    /**
     * 回收 非切片客户端链接
     */
    public void returnMasterResource(Jedis resource) {
        resource.close();
    }

    /**
     * 销毁 非切片客户端链接
     */
    public void returnMasterBrokenResourceObject(Jedis resource) {
        resource.close();
    }

    /**
     * 回收 非切片客户端链接
     */
    public void returnSlaveResource(Jedis resource) {
        resource.close();
    }

    /**
     * 销毁 非切片客户端链接
     */
    public void returnSlaveBrokenResourceObject(Jedis resource) {
        resource.close();
    }

    private void initConfig() {
        try {
            initJedisPoolConfig();

            initMaster();

            initSlave();
        } catch (Exception e) {
            throw e;
        } finally {

        }
    }

    // @Deprecated
    private void initMaster() {
        if (null == this.masterPoolCheckCount) {
            this.masterPoolCheckCount = new AtomicInteger(0);
        } else {
            this.masterPoolCheckCount.set(0);
        }
        if (null == this.masterPoolStartTime) {
            this.masterPoolStartTime = new AtomicLong(0);
        } else {
            this.masterPoolStartTime.set(0);
        }

        if (null != this.masterJedisPoolConfig && null != this.masterJedisPoolConfig.jedisPoolConfig) {
            this.masterpool = new JedisPool(this.masterJedisPoolConfig.jedisPoolConfig, this.masterJedisPoolConfig.host,
                    this.masterJedisPoolConfig.port, this.masterJedisPoolConfig.socketTimeout,
                    this.masterJedisPoolConfig.password);
            logger.info("init master redis pool ");
        }
    }

    // @Deprecated
    private void initSlave() {
        if (null == this.slavePoolCheckCount) {
            this.slavePoolCheckCount = new AtomicInteger(0);
        } else {
            this.slavePoolCheckCount.set(0);
        }
        if (null == this.slavePoolStartTime) {
            this.slavePoolStartTime = new AtomicLong(0);
        } else {
            this.slavePoolStartTime.set(0);
        }

        if (null != this.slaveJedisPoolConfig && null != this.slaveJedisPoolConfig.jedisPoolConfig) {
            this.slavepool = new JedisPool(this.slaveJedisPoolConfig.jedisPoolConfig, this.slaveJedisPoolConfig.host,
                    this.slaveJedisPoolConfig.port, this.slaveJedisPoolConfig.socketTimeout,
                    this.slaveJedisPoolConfig.password);
            logger.info("init Slave redis pool ");
        }
    }

    private void initJedisPoolConfig() {
        this.masterJedisPoolConfig = new MsJedisPoolConfig();
        String redis_master_pass = null;
        if (jedisConfig == null) {
            return;
        }
        if (StringUtil.isNotBlank(jedisConfig.getMaster().getPass())) {
            redis_master_pass = jedisConfig.getMaster().getPass();
        }
        JedisPoolConfig masterConfig = new JedisPoolConfig();
        masterConfig.setMaxTotal(jedisConfig.getPool().getMaxActive());
        masterConfig.setMaxIdle(jedisConfig.getPool().getMaxIdle());
        masterConfig.setMaxWaitMillis(jedisConfig.getPool().getMaxWait());
        masterConfig.setTestOnBorrow(jedisConfig.getPool().isTestOnBorrow());
        masterConfig.setTestOnReturn(jedisConfig.getPool().isTestOnReturn());
        this.masterJedisPoolConfig.jedisPoolConfig = masterConfig;
        this.masterJedisPoolConfig.password = redis_master_pass;
        this.masterJedisPoolConfig.host = jedisConfig.getMaster().getIp();
        this.masterJedisPoolConfig.port = jedisConfig.getMaster().getPort();
        this.masterJedisPoolConfig.socketTimeout = jedisConfig.getMaster().getSocketTimeout();
        this.MASTER_CHECK_EXCEPTION_COUNT = masterConfig.getMaxTotal();

        this.slaveJedisPoolConfig = new MsJedisPoolConfig();
        String redis_slave_pass = null;
        if (StringUtil.isNotBlank(jedisConfig.getSlave().getPass())) {
            redis_slave_pass = jedisConfig.getSlave().getPass();
        }
        JedisPoolConfig slaveConfig = new JedisPoolConfig();

        slaveConfig.setMaxTotal(jedisConfig.getPool().getMaxActive());
        slaveConfig.setMaxIdle(jedisConfig.getPool().getMaxIdle());
        slaveConfig.setMaxWaitMillis(jedisConfig.getPool().getMaxWait());
        slaveConfig.setTestOnBorrow(jedisConfig.getPool().isTestOnBorrow());
        slaveConfig.setTestOnReturn(jedisConfig.getPool().isTestOnReturn());

        this.slaveJedisPoolConfig.jedisPoolConfig = slaveConfig;
        this.slaveJedisPoolConfig.password = redis_slave_pass;
        this.slaveJedisPoolConfig.host = jedisConfig.getMaster().getIp();
        this.slaveJedisPoolConfig.port = jedisConfig.getMaster().getPort();
        this.slaveJedisPoolConfig.socketTimeout = jedisConfig.getMaster().getSocketTimeout();
        this.SLAVE_CHECK_EXCEPTION_COUNT = slaveConfig.getMaxTotal();
    }

    class MsJedisPoolConfig {
        public JedisPoolConfig jedisPoolConfig;
        public String host;
        public int port;
        public int socketTimeout;
        public String password;
    }
}
