package com.mashibing.strategy.example03;

import com.mashibing.strategy.example02.Receipt;

/**
 * 具体策略类
 * @author spikeCong
 * @date 2022/10/13
 **/
public class MT1101ReceiptHandleStrategy implements ReceiptHandleStrategy {

    @Override
    public void handleReceipt(Receipt receipt) {
        System.out.println("解析报文MT1101: " + receipt.getMessage());
    }
}
