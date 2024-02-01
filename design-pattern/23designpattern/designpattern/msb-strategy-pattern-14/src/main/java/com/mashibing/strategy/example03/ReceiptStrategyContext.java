package com.mashibing.strategy.example03;

import com.mashibing.strategy.example02.Receipt;

/**
 * 上下文类, 持有策略接口,决定执行哪一个具体的策略类
 * @author spikeCong
 * @date 2022/10/13
 **/
public class ReceiptStrategyContext {

    private ReceiptHandleStrategy receiptHandleStrategy;

    public void setReceiptHandleStrategy(ReceiptHandleStrategy receiptHandleStrategy) {
        this.receiptHandleStrategy = receiptHandleStrategy;
    }

    //调用策略类中方法
    public void handleReceipt(Receipt receipt){
        if(receipt != null){
            receiptHandleStrategy.handleReceipt(receipt);
        }
    }
}
