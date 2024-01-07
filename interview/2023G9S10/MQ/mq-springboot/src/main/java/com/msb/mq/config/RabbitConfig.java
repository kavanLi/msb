package com.msb.mq.config;


import com.msb.mq.service.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**

 *类说明：RabbitMQ的配置类
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    public Receiver receiver;


    //TODO 连接工厂
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        // 如果要进行消息的回调，这里必须要设置为true
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
    //TODO rabbitAdmin类封装对RabbitMQ的管理操作
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    //TODO 使用Template，给生产者、消费者  方便发消息 5672
    @Bean
    public RabbitTemplate newRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        //进行 发送确认的回调方法的设置
        template.setConfirmCallback(confirmCallback());
        // 开启路由失败通知
        template.setMandatory(true);
        // 路由失败的回调----这里只关注路由失败的
        template.setReturnCallback(returnCallback()) ;
        return template;
    }

    @Bean
    public DirectExchange Directexchange() {
        return new DirectExchange("DirectExchange");
    }
    //TODO 申明交换器(Fanout交换器)
    //TODO 申明死信交换器(Fanout交换器)

    @Bean
    public FanoutExchange Fanoutexchange() {
        return new FanoutExchange("FanoutExchange");
    }
    @Bean
    public FanoutExchange DlxExchange() {
        return new FanoutExchange("exchange-dlx");
    }


    //TODO 申明队列
    @Bean
    public Queue queue1() {
        return new Queue("queue1");
    }
    //TODO 申明消息过期队列 --队列ttl
    @Bean
    public Queue queueTTL() {
        Map<String, Object> arguments = new HashMap<>();
        // 所有消息存活时间，时间单位是毫秒,30秒没消费，-》死信
        arguments.put("x-message-ttl", 30*1000);
        // 绑定该队列到死信交换机
        arguments.put("x-dead-letter-exchange","exchange-dlx");
        arguments.put("x-dead-letter-routing-key","*");
        return new Queue("queue_ttl",true,false,false,arguments);
    }
    //TODO 申明专门存放死信消息的队列
    @Bean
    public Queue queueDLX() {
        return new Queue("queue_dlx");
    }

    //TODO 绑定关系 绑定直连direct交换器
    @Bean
    public Binding bindingDirectExchange() {
        return BindingBuilder
                .bind(queue1())
                .to(Directexchange())
                .with("lijin.mq");
    }

    //消息过期队列绑定
    @Bean
    public Binding bindingExchangeTTL() {
        return BindingBuilder
                .bind(queueTTL())
                .to(Fanoutexchange());
    }






    //TODO 绑定死信交换器到死信队列中
    @Bean
    public Binding bindingDlxExchange() {
        return BindingBuilder
                .bind(queueDLX())
                .to(DlxExchange());
    }



    //===============生产者发送确认==========
    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback(){
        return new RabbitTemplate.ConfirmCallback(){
            @Override
            public void confirm(CorrelationData correlationData,
                                boolean ack, String cause) {
                if (ack) {
                    System.out.println("发送者确认发送给mq-（Exchange）成功");
                } else {
                    //处理失败的消息
                    System.out.println("发送者发送给mq-（Exchange）失败,考虑重发:"+cause);
                }
            }
        };
    }

        //===============路由（交换器上）失败通知==========
    @Bean
    public RabbitTemplate.ReturnCallback returnCallback(){
        return new RabbitTemplate.ReturnCallback(){

            @Override
            public void returnedMessage(Message message,
                                        int replyCode,
                                        String replyText,
                                        String exchange,
                                        String routingKey) {
                System.out.println("无法路由的消息，需要考虑另外处理。");
                System.out.println("Returned replyText："+replyText);
                System.out.println("Returned exchange："+exchange);
                System.out.println("Returned routingKey："+routingKey);
                String msgJson  = new String(message.getBody());
                System.out.println("Returned Message："+msgJson);
            }
        };
    }
    //===============手动消费者确认==========
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container
                = new SimpleMessageListenerContainer(connectionFactory());
        //TODO 绑定了这个队列（消费queue1）
        container.setQueues(queue1());
        //TODO 手动提交
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //TODO 消费确认方法
        container.setMessageListener(receiver);
        return container;
    }




}
