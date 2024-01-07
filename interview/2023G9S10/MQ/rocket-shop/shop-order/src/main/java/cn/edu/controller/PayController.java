package cn.edu.controller;

import cn.edu.model.ShopOrder;
import cn.edu.service.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * 类说明：
 */
@RestController
public class PayController {
    private static final Logger logger = LoggerFactory.getLogger(PayController.class);

    private static final String SUCCESS = "success";
    private static final String FAILUER = "failure";
    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private OrderServiceImpl orderService;
    //  http://localhost:8080/confirmOrder?orderId=154892
    //提交订单---微信、支付宝（第三方来触发）
    @RequestMapping("/confirmOrder")
    public String confirmOrder(@RequestParam("orderId")long orderid){
        try {
            //支付订单
            orderService.ConfirmOrder(orderid);
        } catch (Exception e) {
            logger.error("确认订单失败：[" + orderid + "]");
            e.printStackTrace();
            return FAILUER;
        }
        return SUCCESS;
    }
}
