package com.msb.redis.redisbase.advtypes.bitmap;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.nio.charset.Charset;

/*仿Google的布隆过滤器实现，基于redis支持分布式*/
public class RedisBloomFilter {

    public final static String RS_BF_NS = "rbf:";
    private int numApproxElements; /*预估元素数量*/
    private double fpp; /*可接受的最大误差*/
    private int numHashFunctions; /*自动计算的hash函数个数*/
    private int bitmapLength; /*自动计算的最优Bitmap长度*/

    @Autowired
    private JedisPool jedisPool;

    /**
     * 构造布隆过滤器
     * @param numApproxElements 预估元素数量
     * @param fpp 可接受的最大误差
     * @return
     */
    public RedisBloomFilter init(int numApproxElements,double fpp){
        this.numApproxElements = numApproxElements;
        this.fpp = fpp;
        /*位数组的长度*/
        //this.bitmapLength = (int) (-numApproxElements*Math.log(fpp)/(Math.log(2)*Math.log(2)));
        this.bitmapLength=128;
        /*算hash函数个数*/
        //this.numHashFunctions = Math.max(1, (int) Math.round((double) bitmapLength / numApproxElements * Math.log(2)));
        this.numHashFunctions=2;
        return  this;
    }

    /**
     * 计算一个元素值哈希后映射到Bitmap的哪些bit上
     * 用两个hash函数来模拟多个hash函数的情况
     *     * @param element 元素值
     * @return bit下标的数组
     */
    private long[] getBitIndices(String element){
        long[] indices = new long[numHashFunctions];
        /*会把传入的字符串转为一个128位的hash值，并且转化为一个byte数组*/
        byte[] bytes = Hashing.murmur3_128().
                hashObject(element, Funnels.stringFunnel(Charset.forName("UTF-8"))).
                asBytes();

        long hash1 = Longs.fromBytes(bytes[7],bytes[6],bytes[5],bytes[4],bytes[3],bytes[2],bytes[1],bytes[0]);
        long hash2 = Longs.fromBytes(bytes[15],bytes[14],bytes[13],bytes[12],bytes[11],bytes[10],bytes[9],bytes[8]);

        /*用这两个hash值来模拟多个函数产生的值*/
        long combinedHash = hash1;
        for(int i=0;i<numHashFunctions;i++){
            //数组下标
            indices[i]=(combinedHash&Long.MAX_VALUE) % bitmapLength;
            combinedHash = combinedHash + hash2;
        }

        System.out.print(element+"数组下标");
        for(long index:indices){
            System.out.print(index+",");
        }
        System.out.println(" ");
        return indices;
    }

    /**
     * 插入元素
     *
     * @param key       原始Redis键，会自动加上前缀
     * @param element   元素值，字符串类型
     * @param expireSec 过期时间（秒）
     */
    public void insert(String key, String element, int expireSec) {
        if (key == null || element == null) {
            throw new RuntimeException("键值均不能为空");
        }
        String actualKey = RS_BF_NS.concat(key);

        try (Jedis jedis = jedisPool.getResource()) {
            try (Pipeline pipeline = jedis.pipelined()) {
                for (long index : getBitIndices(element)) {
                    pipeline.setbit(actualKey, index, true);
                }
                pipeline.syncAndReturnAll();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            jedis.expire(actualKey, expireSec);
        }
    }

    /**
     * 检查元素在集合中是否（可能）存在
     *
     * @param key     原始Redis键，会自动加上前缀
     * @param element 元素值，字符串类型
     */
    public boolean mayExist(String key, String element) {
        if (key == null || element == null) {
            throw new RuntimeException("键值均不能为空");
        }
        String actualKey = RS_BF_NS.concat(key);
        boolean result = false;

        try (Jedis jedis = jedisPool.getResource()) {
            try (Pipeline pipeline = jedis.pipelined()) {
                for (long index : getBitIndices(element)) {
                    pipeline.getbit(actualKey, index);
                }
                result = !pipeline.syncAndReturnAll().contains(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "RedisBloomFilter{" +
                "numApproxElements=" + numApproxElements +
                ", fpp=" + fpp +
                ", numHashFunctions=" + numHashFunctions +
                ", bitmapLength=" + bitmapLength +
                '}';
    }
}
