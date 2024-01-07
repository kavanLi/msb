package com.msb;

import com.msb.service.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.CountDownLatch;

//模拟高并发的通过MQ 进行异步、削峰填谷的操作   ---与接口调用进行对比
@SpringBootTest(classes = KafkaTrafficClientApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestKafka {

	// 并发量
	private static final int USER_NUM = 1000;
	// 倒计时器，用于模拟高并发
	private static CountDownLatch cdl = new CountDownLatch(USER_NUM);

	@Autowired
	private KafkaSender kafkaSender;

	/**
	* 并发模拟
	* @throws Exception
	*/
	@Test
	public void testKafkaMq() throws InterruptedException {
		// 循环实例化USER_NUM个并发请求（线程）
		for (int i = 0; i < USER_NUM; i++) {
			new Thread(new UserRequst(i)).start();
			cdl.countDown();// 倒计时器减一
		}
		Thread.currentThread().sleep(60000);
	}

//	@Test
//	public void consumer() throws InterruptedException {
//		Thread.currentThread().sleep(60000);
//	}
	/**
	* 内部类继承线程接口，用于模拟买票请求
	* @throws Exception
	*/
	public class UserRequst implements Runnable {
		private int id;

		public UserRequst(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			try {
				// 当前线程等待，等所以线程实例化完成后，同时停止等待后调用接口代码
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			String topic = "traffic-shaping";//主题
			String key = "user-"+id;//键
			String value = "123456="+System.currentTimeMillis();
			kafkaSender.messageSender(topic,key,value);
		}
		}
	}
