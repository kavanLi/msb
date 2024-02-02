package com.mashibing.example02;

/**
 * 密码支付
 * @author spikeCong
 * @date 2022/9/26
 **/
public class PayCypher implements IPayMode {

    @Override
    public boolean security(String uId) {
        System.out.println("密码支付,风控校验-环境安全");
        return true;
    }
}
