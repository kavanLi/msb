package org.apache.rocketmq.example.simple;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class test {
    public static void main(String[] args) throws Exception {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("xx5");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.start();
        Collection<MessageQueue> bala = consumer.fetchMessageQueues("TopicTest");

        List<MessageQueue> list = new ArrayList<>(bala);
        List<MessageQueue> assignList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            assignList.add(list.get(i));
        }
        consumer.assign(assignList);
       // consumer.seek(assignList.get(0), 10);
        try {
            while (true) {
                List<MessageExt> messageExts = consumer.poll();
                System.out.printf("%s %n", messageExts);
                consumer.commitSync();
            }
        } finally {
            consumer.shutdown();
        }

    }
}
