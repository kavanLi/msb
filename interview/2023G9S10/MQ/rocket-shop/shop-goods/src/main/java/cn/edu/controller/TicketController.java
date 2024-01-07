package cn.edu.controller;

import cn.edu.db.DBService;
import cn.edu.service.GoodsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    private DBService dbService;

    @Autowired
    private GoodsServiceImpl goodsService;

    @RequestMapping("/buyTicket")
    public String buyTicket()throws Exception{
        //模拟出票……
        System.out.println("开始购票业务－－－－－－");
        return dbService.useDb("select ticket ");
    }
    //改造后的：通过MQ进行异步、缓冲处理，达到削峰填谷的效果
    @RequestMapping("/rocketmq/buyTicket")
    public String buyTicketToRocket()throws Exception{
        if(goodsService.MQShopOrder("123")==1){
            return "success";
        }else {
            throw new Exception("购票失败");
        }
    }
}
