package org.hxm.thread.Demo1;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 在分布式系统中，经常出现生成唯一分布式id的技术场景，Twitter的雪花算法解决了这类技术问题。
 * 雪花算法的核心原理：符号位1位+毫秒级时间41位+机器ID10位+毫秒内序列12位。
 * 其原理结构如下，我分别用一个0表示一位，用—分割开部分的作用： 0---0000000000 0000000000
 * 0000000000 0000000000 0 --- 00000 ---00000 ---0000000000 00
 * 第一位为未使用（实际上也可作为long的符号位），41位为毫秒级时间、5位datacenter标识位、5位机器ID、12位该毫秒内的当前毫秒内的计数。
 * 加起来刚好64位，为一个Long型。整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（datacenter和机器ID作区分），并且效率较高。
 * 理论上每台机器每秒生成4096000个id。
 */

/**
 * Filename: DistrIdGenerator.java
 * @Copyright: Copyright (c)2017
 * @Company: jd
 * @author: huanglaoxie
 * @function:
 * @version: 1.0
 * @Create at: 2017年6月7日 下午2:49:47
 */
public class DistrIdGenerator {

    static AtomicLong counter = new AtomicLong(0);
    /**
     * 开始时间截 (2016-6-1 00:00:00)
     */
    private final long startTime = 1464710400L;

    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据标识id所占的位数
     */
    private final long dataCenterIdBits = 5L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);

    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIdShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long dataCenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerId;

    /**
     * 数据中心ID(0~31)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    public DistrIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDataCenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long generateDistId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - startTime) << timestampLeftShift) //
                | (datacenterId << dataCenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static void parseId(long id) {
        long miliSecond = id >>> 22;
        long shardId = (id & (0xFFF << 10)) >> 10;
        System.err.println("分布式id-"+id+"生成的时间是："+new SimpleDateFormat("yyyy-MM-dd").format(new Date(miliSecond)));
    }

//    public static void main(String[] args)throws Exception {
//        DistrIdGenerator worker = new DistrIdGenerator(0, 0);
//        ExecutorService executor = Executors.newFixedThreadPool(800);
//        int count=    1000000;
//        CountDownLatch countDownLatch = new CountDownLatch(count);
//        Runnable run = () -> {
//            System.out.println("第"+counter.incrementAndGet()+"次生成分布式id， "+worker.generateDistId());
//            countDownLatch.countDown();
//        };
//
//        long startTime = System.currentTimeMillis();
//        for (int i = 0; i < count; i++) {
//            executor.execute(run);
//        }
//        countDownLatch.await();
//        System.out.println(" 总共生成 "+counter.get()+" 个分布式id  ,  总共花费 "+(System.currentTimeMillis() - startTime)+" ms");
//        executor.shutdown();
//    }


}
