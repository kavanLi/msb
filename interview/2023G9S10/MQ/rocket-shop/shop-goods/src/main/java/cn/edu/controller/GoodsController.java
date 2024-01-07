package cn.edu.controller;

import cn.edu.service.GoodsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * 类说明：
 */
@RestController
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
    private static final String SUCCESS = "success";
    private static final String FAILUER = "failure";
    @Autowired
    private GoodsServiceImpl goodsService;
    /**
     * http://127.0.0.1:8089/updateGoods?orderId=98&goodsId=13&goodsNumber=1
     * 扣减存库的接口，地址为：http://127.0.0.1:8089/updateGoods?orderId=98&goodsId=13&goodsNumber=1
     * @throws Exception
     */
    @RequestMapping("/updateGoods")
    public String updateGoods(@RequestParam("orderId")int orderId,@RequestParam("goodsId")long goodsId, @RequestParam("goodsNumber")int goodsNumber){
        int ireturn;
        try {
            ireturn =goodsService.updateGoods(orderId,goodsId,goodsNumber);
        } catch (Exception e) {
            logger.error("扣减存库失败：[" + orderId + "]");
            e.printStackTrace();
            return FAILUER;
        }
        if(ireturn>0){
            return SUCCESS;
        }else {
            logger.error("扣减存库失败：[" + orderId + "]");
            return FAILUER;
        }
    }
}
