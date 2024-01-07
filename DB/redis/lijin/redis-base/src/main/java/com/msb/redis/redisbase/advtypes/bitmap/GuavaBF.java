package com.msb.redis.redisbase.advtypes.bitmap;


import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/*单机下无Redis的布隆过滤器：使用Google的Guava的BloomFilter*/
public class GuavaBF {
    public static void main(String[] args) {
        long expectedInsertions = 100000;
        double fpp = 0.00005;

        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);

        bloomFilter.put("10081");
        bloomFilter.put("10082");
        bloomFilter.put("10083");
        bloomFilter.put("10084");
        bloomFilter.put("10085");
        bloomFilter.put("10086");

        System.out.println("123456:BF--"+bloomFilter.mightContain("123456"));//false
        System.out.println("10086:BF--"+bloomFilter.mightContain("10086"));//true
        System.out.println("10084:BF--"+bloomFilter.mightContain("10084"));//true

    }
}
