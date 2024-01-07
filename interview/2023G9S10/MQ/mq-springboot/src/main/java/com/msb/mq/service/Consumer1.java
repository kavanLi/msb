package com.msb.mq.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**

 *类说明：监听类---自动提交（）
 */
@Component
@RabbitListener(queues = "queue1")
public class Consumer1 {

    @RabbitHandler
    public void process(String msg) {
        int i =0;
        System.out.println("Consumer1-Receiver : " + msg);
        //业务代码，异常 怎么办？  调用其他接口    返回值0代表处理失败

    }

}
