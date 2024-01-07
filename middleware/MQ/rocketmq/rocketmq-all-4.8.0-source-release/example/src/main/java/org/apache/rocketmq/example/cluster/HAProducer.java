package org.apache.rocketmq.example.cluster;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 消息生产的高可用机制
 */
public class HAProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("produce_ha");
        producer.setNamesrvAddr("106.55.246.66:9876;94.191.83.120:9876;");
        //todo 同步模式下内部尝试发送消息的最大次数  默认值是2
        producer.setRetryTimesWhenSendFailed(2);
        //todo 异步模式下内部尝试发送消息的最大次数 默认值是2
        producer.setRetryTimesWhenSendAsyncFailed(2);
        //todo 默认不启用Broker故障延迟机制（规避策略）  true
        producer.setSendLatencyFaultEnable(false);
        // 启动Producer实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicCluster", "TagA", "OrderID888",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);e.printStackTrace();
                }
            });
        }
        Thread.sleep(10000);
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
