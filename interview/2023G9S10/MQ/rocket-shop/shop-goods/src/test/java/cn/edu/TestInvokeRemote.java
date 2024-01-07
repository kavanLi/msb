package cn.edu;

import cn.edu.db.DBService;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopGoodsApplication.class)
public class TestInvokeRemote {
    @Autowired
    private DBService dbService;

    private static final int num = 1000;

    private static CountDownLatch cdl = new CountDownLatch(num);

    @Test
    public void testInvokeRemote() throws InterruptedException {

        for(int i=0;i< num;i++){
            final long orderId =100+i;
            Thread thread =new Thread(()->{
                try {
                    cdl.await(); //阻塞
                    dbService.useDb("select ticket ");
                    // System.out.println(orderInfo);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            );
            thread.start();
            cdl.countDown(); //计数器减一 （10000个请求中，最后一个 就会把计数器清0）
        }
        Thread.sleep(30000);
    }


}
