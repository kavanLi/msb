package com.mashibing.example02;

/**
 * MQ连接客户端
 * @author spikeCong
 * @date 2022/9/19
 **/
public class RabbitMQClient1 {

    private String host = "127.0.0.1";

    private int port = 5672;

    private int mode;

    private String exchange;

    private String queue;

    private boolean isDurable = true;

    int connectionTimeout = 1000;

    public RabbitMQClient1(String host, int port, int mode, String exchange, String queue, boolean isDurable, int connectionTimeout) {
        this.host = host;
        this.port = port;
        this.mode = mode;
        this.exchange = exchange;
        this.queue = queue;
        this.isDurable = isDurable;
        this.connectionTimeout = connectionTimeout;

        if(mode == 1){ //工作队列模式不需要设计交换机,但是队列名称一定要有
            if(exchange != null){
                throw new RuntimeException("工作队列模式无需设计交换机");
            }
            if(queue == null || queue.trim().equals("")){
                throw new RuntimeException("工作队列模式名称不能为空");
            }
            if(isDurable == false){
                throw new RuntimeException("工作队列模式必须开启持久化");
            }
        }else if(mode == 2){ //路由模式必须设计交换机,但是不能设计队列
            if(exchange == null){
                throw new RuntimeException("路由模式下必须设置交换机");
            }
            if(queue != null){
                throw new RuntimeException("路由模式无须设计队列名称");
            }
        }

        //其他验证方式,
    }

    public void sendMessage(String msg){

        System.out.println("发送消息......");
    }

    public static void main(String[] args) {
        //每一种模式,都需要根据不同的情况进行实例化,构造方法会变得过于复杂.
        RabbitMQClient1 client1 = new RabbitMQClient1("192.168.52.123",5672,
                2,"sample-exchange",null,true,5000);

        client1.sendMessage("Test-MSG");
    }
}
