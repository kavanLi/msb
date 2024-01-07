package com.msb;
/**
 * 类说明：如何根据消费分组找ConsumerOffsets文件
 */
public class ConsumerOffsets {
    public static void main(String[] args) {
        String groupID = "c_test_1";
        System.out.println(Math.abs(groupID.hashCode()) % 50);

        //消费者 是以群组 名 进行消费

        //topic   msb(100条)
        //group    test   100条
        //group    test1   100条
        //group    test2   100条
    }
}
