package com.mashibing.rabbitmqboot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zjw
 * @description
 */
@Configuration
public class DelayedConfig {

    public static final String DELAYED_EXCHANGE = "delayed-exchange";
    public static final String DELAYED_QUEUE = "delayed-queue";
    public static final String DELAYED_ROUTING_KEY = "delayed.#";

    @Bean
    public Exchange delayedExchange(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type","topic");
        Exchange exchange = new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,arguments);
        return exchange;
    }

    @Bean
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    @Bean
    public Binding delayedBinding(Queue delayedQueue,Exchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }





}
