package com.mashibing.example02;

/**
 * 刷脸支付
 * @author spikeCong
 * @date 2022/9/26
 **/
public class PayFaceMode implements IPayMode {

    @Override
    public boolean security(String uId) {
        System.out.println("人脸支付,风控校验-->脸部识别");
        return true;
    }
}
