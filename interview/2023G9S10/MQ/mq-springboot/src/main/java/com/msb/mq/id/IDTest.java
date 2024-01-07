package com.msb.mq.id;

import redis.clients.jedis.Jedis;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class IDTest {

    static CountDownLatch ctl = new CountDownLatch(1000);

    static SnowFlake snowFlake = new SnowFlake(9,20);
    public static void main(String[] args) {

        for (int i =0 ; i <1000;i++){ //同时启动1000个线程
            new Thread(()->{
                try {
                    ctl.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                getUUID();
            }).start();
            ctl.countDown();
        }


    }
    //uuid去生成 分布式ID
    public static void  getUUID(){
        UUID uuid= UUID.randomUUID();
        System.out.println("insert into order(order_id) values('"+uuid+"')");
    }
    //基于Redis 自增 去实现分布式ID
    public static void  getRedis(){
        Jedis jedis = new Jedis("127.0.0.1",6379);
        String key ="orderid";
        String prefix =getPrefix(new Date());
        long value= jedis.incr(key); //加入  淘宝 订单  用户id+日期->long+order_id ->Redis中的key
        String id = prefix+String.format("%1$05d",value);
        System.out.println("insert into order(order_id) values('"+id+"')");
    }
    //加入时间和日期的前缀
    private  static String getPrefix(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year =c.get(Calendar.YEAR);
        int day =c.get(Calendar.DAY_OF_YEAR); //一年中的第多少天
        int hour = c.get(Calendar.HOUR_OF_DAY);//一天当中的第几个小时
        String dayString =String.format("%1$03d",day); //转成3位字符串
        String hourString =String.format("%1$02d",hour);//转成2位字符串
        return (year-2000)+dayString+hourString;
    }
    //基于雪花算法去实现分布式ID
    public static void  getsonwflake(){
        long value= snowFlake.nextId(); //雪花算法
        System.out.println("insert into order(order_id) values('"+value+"')");
    }
}
