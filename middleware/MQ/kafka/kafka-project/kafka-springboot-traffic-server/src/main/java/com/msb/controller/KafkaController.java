package com.msb.controller;

import com.msb.service.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类说明：
 */
@RestController
public class KafkaController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DBService dbService;

    /**
     * 对外开放的接口，地址为：http://127.0.0.1:8090/buyTicket
     *
     * @return TicketInfo对象
     * @throws Exception
     */
    @RequestMapping("/buyTicket")
    public String buyTicket() {
        try {
            //模拟出票……  这里业务逻辑非常简单--- 能够出现故障
            System.out.println("开始购票业务－－－－－－");
            return dbService.useDb("select ticket "); //这里是一个伪sql语句
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
