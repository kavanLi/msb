package com.mashibing.strategy.example03;

import com.mashibing.strategy.example02.Receipt;

/**
 * @author spikeCong
 * @date 2022/10/13
 **/
public class MT2101ReceiptHandleStrategy implements ReceiptHandleStrategy {

    @Override
    public void handleReceipt(Receipt receipt) {
        System.out.println("解析报文 MT2101: " + receipt.getMessage());
    }
}
