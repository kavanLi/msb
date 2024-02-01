package com.mashibing.iterator.example02;

/**
 * 具体迭代器
 * @author spikeCong
 * @date 2022/10/18
 **/
public class TopicIterator implements IteratorIterator<Topic> {

    //Topic数组
    private Topic[] topics;

    //记录存储位置的有游标
    private int position;

    public TopicIterator(Topic[] topics) {
        this.topics = topics;
        position = 0;
    }

    @Override
    public void reset() {
        position = 0;
    }

    @Override
    public Topic next() {
        return topics[position++];
    }

    @Override
    public Topic currentItem() {
        return topics[position];
    }

    @Override
    public boolean hasNext() {
        if(position >= topics.length){
            return false;
        }
        return true;
    }
}
