package cn.edu.controller;

import cn.edu.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;

/**
 * @author King老师
 * 类说明：优惠券
 */
@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String SUCCESS = "success";
    private static final String FAILUER = "failure";
    @Autowired
    private UserServiceImpl saveUser;
    //  http://localhost:8099/useCoupon?orderId=98&couponId=1
    //提交订单
    @RequestMapping("/useCoupon")
    public String submitOrder(@RequestParam("orderId")int orderId,@RequestParam("couponId")int couponId){
        try {
            saveUser.useCoupon(orderId,couponId);
        } catch (Exception e) {
            logger.error("使用优惠券失败：[" + orderId + "]");
            e.printStackTrace();
            return FAILUER;
        }
        return SUCCESS;
    }
}
