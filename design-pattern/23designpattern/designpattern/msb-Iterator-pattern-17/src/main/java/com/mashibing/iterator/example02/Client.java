package com.mashibing.iterator.example02;

/**
 * @author spikeCong
 * @date 2022/10/18
 **/
public class Client {

    public static void main(String[] args) {
        Topic[] topics = new Topic[4];
        topics[0] = new Topic("t1");
        topics[1] = new Topic("t2");
        topics[2] = new Topic("t3");
        topics[3] = new Topic("t4");

        TopicList topicList = new TopicList(topics);
        IteratorIterator<Topic> iterator = topicList.iterator();

        while(iterator.hasNext()){
            Topic topic = iterator.next();
            System.out.println(topic.getName());
        }
    }
}
