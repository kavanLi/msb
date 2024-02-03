package com.msb.redis.limit;


import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 李瑾老师
 * 类说明：接口类，抢购接口
 * http请求控制类  Contoller
 */
@RestController
public class Controller {
    @Autowired
    IsAcquire isAcquire;//手下的分布式限流

    //final  RateLimiter rateLimiter = RateLimiter.create(5);  //guava引入的令牌桶限流（非分布式，单机）
    //秒杀接口
    @RequestMapping("/order")
    public String killProduct(@RequestParam(required = true) String name) throws Exception{
        //rateLimiter.tryAcquire(1); //调用
        if(isAcquire.acquire("iphone",10,60)){//60秒只能进行10次
            System.out.println("业务成功！");
            return "恭喜("+name+")，抢到iphone!";
        }else{
            System.out.println("-----------业务被限流");
            return "对不起，你被限流了!";
        }

    }
}

