package com.msb.redis.redismq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;
import redis.clients.jedis.params.XReadGroupParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现消费组消费，不考虑单消费者模式
 */
@Component
public class StreamVer {
    public final static String RS_STREAM_MQ_NS = "rsm:";

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发布消息到Stream
     * @param key
     * @param message
     * @return
     */
    public StreamEntryID produce(String key,Map<String,String> message){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            StreamEntryID id = jedis.xadd(RS_STREAM_MQ_NS+key, StreamEntryID.NEW_ENTRY, message);
            System.out.println("发布消息到"+RS_STREAM_MQ_NS+key+" 返回消息id="+id.toString());
            return id;
        } catch (Exception e) {
            throw new RuntimeException("发布消息失败！");
        } finally {
            jedis.close();
        }
    }


    /**
     * 创建消费群组,消费群组不可重复创建
     * @param key
     * @param groupName
     * @param lastDeliveredId
     */
    public void createCustomGroup(String key, String groupName, String lastDeliveredId){
        Jedis jedis = null;
        try {
            StreamEntryID id = null;
            if (lastDeliveredId==null){
                lastDeliveredId = "0-0";
            }
            id = new StreamEntryID(lastDeliveredId);
            jedis = jedisPool.getResource();
            /*makeStream表示没有时是否自动创建stream，但是如果有，再自动创建会异常*/
            jedis.xgroupCreate(RS_STREAM_MQ_NS+key,groupName,id,false);
            System.out.println("创建消费群组成功："+groupName);
        } catch (Exception e) {
            throw new RuntimeException("创建消费群组失败！",e);
        } finally {
            jedis.close();
        }
    }


    /**
     * 消息消费
     * @param key
     * @param customerName
     * @param groupName
     * @return
     */
    public List<Map.Entry<String, List<StreamEntry>>> consume(String key, String customerName,String groupName){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            /*消息消费时的参数*/
            XReadGroupParams xReadGroupParams = new XReadGroupParams().block(0).count(1);
            Map<String, StreamEntryID> streams = new HashMap<>();
            streams.put(RS_STREAM_MQ_NS+key,StreamEntryID.UNRECEIVED_ENTRY);
            List<Map.Entry<String, List<StreamEntry>>> result
                    = jedis.xreadGroup(groupName, customerName, xReadGroupParams, streams);
            System.out.println(groupName+"从"+RS_STREAM_MQ_NS+key+"接受消息, 返回消息："+result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("消息消费失败！",e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 消息确认
     * @param key
     * @param groupName
     * @param msgId
     */
    public void ackMsg(String key, String groupName,StreamEntryID msgId){
        if (msgId==null) throw new RuntimeException("msgId为空！");
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            System.out.println(jedis.xack(key,groupName,msgId));
            System.out.println(RS_STREAM_MQ_NS+key+"，消费群组"+groupName+" 消息已确认");
        } catch (Exception e) {
            throw new RuntimeException("消息确认失败！",e);
        } finally {
            jedis.close();
        }
    }

    /*
    检查消费者群组是否存在，辅助方法
    * */
    public boolean checkGroup(String key, String groupName){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<StreamGroupInfo> xinfoGroupResult = jedis.xinfoGroup(RS_STREAM_MQ_NS+key);
            for(StreamGroupInfo groupinfo : xinfoGroupResult) {
                if(groupName.equals(groupinfo.getName())) return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("检查消费群组失败！",e);
        } finally {
            jedis.close();
        }
    }

    public final static int MQ_INFO_CONSUMER = 1;
    public final static int MQ_INFO_GROUP = 2;
    public final static int MQ_INFO_STREAM = 0;
    /**
     * 消息队列信息查看
     * @param type
     */
    public void MqInfo(int type,String key, String groupName){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if(type==MQ_INFO_CONSUMER){
                List<StreamConsumersInfo> xinfoConsumersResult = jedis.xinfoConsumers(RS_STREAM_MQ_NS+key, groupName);
                System.out.println(RS_STREAM_MQ_NS+key+" 消费者信息:" + xinfoConsumersResult);
                for( StreamConsumersInfo consumersinfo : xinfoConsumersResult) {
                    System.out.println("-ConsumerInfo:" + consumersinfo.getConsumerInfo());
                    System.out.println("--Name:" + consumersinfo.getName());
                    System.out.println("--Pending:" + consumersinfo.getPending());
                    System.out.println("--Idle:" + consumersinfo.getIdle());
                }
            }else if (type==MQ_INFO_GROUP){
                List<StreamGroupInfo> xinfoGroupResult = jedis.xinfoGroup(RS_STREAM_MQ_NS+key);
                System.out.println(RS_STREAM_MQ_NS+key+"消费者群组信息:" + xinfoGroupResult);
                for(StreamGroupInfo groupinfo : xinfoGroupResult) {
                    System.out.println("-GroupInfo:" + groupinfo.getGroupInfo());
                    System.out.println("--Name:" + groupinfo.getName());
                    System.out.println("--Consumers:" + groupinfo.getConsumers());
                    System.out.println("--Pending:" + groupinfo.getPending());
                    System.out.println("--LastDeliveredId:" + groupinfo.getLastDeliveredId());
                }
            }else{
                StreamInfo xinfoStreamResult = jedis.xinfoStream(RS_STREAM_MQ_NS+key);
                System.out.println(RS_STREAM_MQ_NS+key+"队列信息:" + xinfoStreamResult);
                System.out.println("-StreamInfo:" + xinfoStreamResult.getStreamInfo());
                System.out.println("--Length:" + xinfoStreamResult.getLength());
                System.out.println("--RadixTreeKeys:" + xinfoStreamResult.getRadixTreeKeys());
                System.out.println("--RadixTreeNodes():" + xinfoStreamResult.getRadixTreeNodes());
                System.out.println("--Groups:" + xinfoStreamResult.getGroups());
                System.out.println("--LastGeneratedId:" + xinfoStreamResult.getLastGeneratedId());
                System.out.println("--FirstEntry:" + xinfoStreamResult.getFirstEntry());
                System.out.println("--LastEntry:" + xinfoStreamResult.getLastEntry());
            }
        } catch (Exception e) {
            throw new RuntimeException("消息队列信息检索失败！",e);
        } finally {
            jedis.close();
        }
    }

}
