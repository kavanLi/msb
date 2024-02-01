package com.mashibing.example02;

import java.math.BigDecimal;

/**
 * 支付渠道-微信支付
 * @author spikeCong
 * @date 2022/9/26
 **/
public class WxPay extends Pay{


    public WxPay(IPayMode payMode) {
        super(payMode);
    }

    @Override
    public String transfer(String uId, String tradeId, BigDecimal amount) {

        System.out.println("微信渠道支付划账开始......");
        boolean security = payMode.security(uId);
        System.out.println("微信渠道支付风险校验: " + uId + "," + tradeId +"," + security);

        if(!security){
            System.out.println("微信渠道支付划账失败! ! ");
            return "500";
        }

        System.out.println("微信渠道划账成功! 金额: " + amount);
        return "200";
    }
}
