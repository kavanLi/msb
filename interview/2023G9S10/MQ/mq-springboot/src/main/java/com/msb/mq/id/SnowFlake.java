package com.msb.mq.id;

public class SnowFlake {
    private long workerId; //工作机器ID(0~31)
    private long datacenterId; //数据中心ID(0~31)
    private long sequence = 0L; //1毫秒内序号(0~4095)
    private long twepoch = 1288834974657L;
    private long workerIdBits = 5L; //机器id所占的位数
    private long datacenterIdBits = 5L;//数据中心所占的位数
    private long maxWorkerId = -1L ^ (-1L << workerIdBits); //支持的最大机器id，结果是31
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); //支持的最大数据中心id，结果是31
    private long sequenceBits = 12L; //序列在id中占的位数
    private long workerIdShift = sequenceBits; //机器ID向左移12位
    private long datacenterIdShift = sequenceBits + workerIdBits; //数据中心id向左移17位(12+5)
    private long timestampLeftShift =   + workerIdBits + datacenterIdBits;//时间戳向左移22位(5+5+12)
    private long sequenceMask = -1L ^ (-1L << sequenceBits); //自增长最大值4095，0开始
    private long lastTimestamp = -1L; //上次生成ID的时间截
    //构造函数(机器id 、数据中心ID )
    public SnowFlake(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenterId can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    //获得下一个ID
    public synchronized long nextId() {
        long timestamp = timeGen();
        //如果当前时间戳小于上次ID的时间戳，说明系统出现了“时钟回拨”，抛出异常(并不完善)
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间戳，则需要进行毫秒内的序号排序
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {//同一毫秒的序号数达到最大，只能等待下一毫秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;//时间戳改变(到了下一毫秒)，毫秒内序列重置
        }
        lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) |  // 时间戳左移22位
                (datacenterId << datacenterIdShift) | //数据标识左移17位
                (workerId << workerIdShift) | //机器id标识左移12位
                sequence;
    }
    //阻塞到下一个毫秒，直到获得新的时间戳
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) { //乐观锁的机制确保是获取下一毫秒
            timestamp = timeGen();
        }
        return timestamp;
    }
    //返回以毫秒为单位的当前时间
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
