package com.mashibing.rabbitmqboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zjw
 * @description
 * @date 2022/2/10 15:04
 */
@Configuration
public class DeadLetterConfig {

    public static final String NORMAL_EXCHANGE = "normal-exchange";
    public static final String NORMAL_QUEUE = "normal-queue";
    public static final String NORMAL_ROUTING_KEY = "normal.#";

    public static final String DEAD_EXCHANGE = "dead-exchange";
    public static final String DEAD_QUEUE = "dead-queue";
    public static final String DEAD_ROUTING_KEY = "dead.#";


    @Bean
    public Exchange normalExchange(){
        return ExchangeBuilder.topicExchange(NORMAL_EXCHANGE).build();
    }

    @Bean
    public Queue normalQueue(){
        return QueueBuilder.durable(NORMAL_QUEUE)
                .deadLetterExchange(DEAD_EXCHANGE)
                .deadLetterRoutingKey("dead.abc")
    //                .ttl(10000)
//                .maxLength(1)
                .build();
    }

    @Bean
    public Binding normalBinding(Queue normalQueue,Exchange normalExchange){
        return BindingBuilder.bind(normalQueue).to(normalExchange).with(NORMAL_ROUTING_KEY).noargs();
    }


    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.topicExchange(DEAD_EXCHANGE).build();
    }

    @Bean
    public Queue deadQueue(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    @Bean
    public Binding deadBinding(Queue deadQueue,Exchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DEAD_ROUTING_KEY).noargs();
    }

}
