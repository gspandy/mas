package com.letv.mas.router.iptv.tvproxy.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * SnowFlake的序列号生成：64位ID＝42(时间戳)+5(机器ID)+5(业务编码)+12(重复累加)
 */
public class IdGenerator {

    private final static long TWEPOCH = 1288834974657L;

    // 机器标识位数
    private final static long WORKER_ID_BITS = 5L;

    // 数据中心标识位数
    private final static long DATA_CENTER_ID_BITS = 5L;

    // 机器ID最大值 31
    private final static long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    // 数据中心ID最大值 31
    private final static long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);

    // 毫秒内自增位
    private final static long SEQUENCE_BITS = 12L;

    // 机器ID偏左移12位
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;

    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    // 时间毫秒左移22位
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

    private final static long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    private long lastTimestamp = -1L;

    private long sequence = 0L;
    private long workerId = 0L;
    private long dataCenterId = 0L;

    //private final AtomicBoolean lock = new AtomicBoolean(false);

    public IdGenerator() {
        byte[] ipBytes = this.getLocalIP();
        if (null != ipBytes) {
            this.init(0x000001F & ipBytes[1], 0x000001F & ipBytes[3]);
        } else {
            this.init(0, 0);
        }
    }

    public IdGenerator(long workerId) {
        this.init(workerId, 0);
    }

    /**
     * 构造
     *
     * @param workerId     机器ID(0-31)
     * @param dataCenterId 数据中心ID(0-31)
     */
    public IdGenerator(long workerId, long dataCenterId) {
        this.init(workerId, dataCenterId);
    }

    /**
     * 初始化
     *
     * @param workerId     机器ID(0-31)
     * @param dataCenterId 数据中心ID(0-31)
     */
    private void init(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("%s must range from %d to %d", workerId, 0,
                    MAX_WORKER_ID));
        }

        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("%s must range from %d to %d", dataCenterId, 0,
                    MAX_DATA_CENTER_ID));
        }

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 生成ID（线程安全）
     *
     * @return id
     */
    public synchronized long nextId() throws IllegalArgumentException {
        long timestamp = this.timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟被修改过，回退在上一次ID生成时间之前应当抛出异常！
        if (timestamp < lastTimestamp) {
            throw new IllegalArgumentException("Clock moved backwards, refuse to generate id for "
                    + (lastTimestamp - timestamp) + " milliseconds");
        }

        // 如果是同一时间生成的，则进行毫秒内sequence生成
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;

        // ID位移组合生成最终的ID
        long nextId = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;

        return nextId;
    }

    /**
     * 阻塞到下一毫秒,获得新时间戳
     *
     * @param lastTimestamp 上次生成ID时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 获取以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }


    /**
     * 获取当前本地IP
     *
     * @return
     */
    private byte[] getLocalIP() {
        byte[] ret = null;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            ret = ip.getAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private int ipBytesToInt(byte[] bytes) {
        int addr = bytes[3] & 0xFF;
        addr |= ((bytes[2] << 8) & 0xFF00);
        addr |= ((bytes[1] << 16) & 0xFF0000);
        addr |= ((bytes[0] << 24) & 0xFF000000);
        return addr;
    }
}
