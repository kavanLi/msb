package com.msb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

 //模拟高并发的接口调用     ---看看有没有什么问题


@SpringBootTest(classes = KafkaTrafficClientApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestInvokeRemote {

    RestTemplate restTemplate = new RestTemplate();

    private static final int num = 1000;

    private final String url = "http://127.0.0.1:8090/buyTicket";
    //发令枪  ，让所有请求都可以并发的去访问
    private static CountDownLatch cdl = new CountDownLatch(num);

    @Test
    public void testInvokeRemote() throws InterruptedException {
        //模拟高并发
        for(int i = 0; i <num; i++){
            new Thread(new TicketQuest()).start();
            cdl.countDown(); //, 所有线程同时起跑（发枪员1000个人）  -》0  发枪
        }
        Thread.currentThread().sleep(3000);
    }

    // 内部类继承线程接口，用于模拟用户买票请求
    public class TicketQuest implements Runnable{

        @Override
        public void run() {
            try {
                cdl.await();//在起跑线等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String str = restTemplate.getForEntity(url, String.class).getBody();
            System.out.println(str);
        }

    }

}
