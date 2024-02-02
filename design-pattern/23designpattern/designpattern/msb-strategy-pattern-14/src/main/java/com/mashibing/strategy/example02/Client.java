package com.mashibing.strategy.example02;

import java.util.List;

/**
 * @author spikeCong
 * @date 2022/10/13
 **/
public class Client {

    public static void main(String[] args) {

        List<Receipt> receiptList = ReceiptBuilder.getReceiptList();

        //回执类型: MT1101、MT2101、MT4101、MT8104
        for (Receipt receipt : receiptList) {
            if("MT1011".equals(receipt.getType())){
                System.out.println("接收到MT1011的回执信息");
                System.out.println("解析回执内容");
                System.out.println("执行业务逻辑A......");
            }else if("MT2101".equals(receipt.getType())){
                System.out.println("接收到MT2101的回执信息");
                System.out.println("解析回执内容");
                System.out.println("执行业务逻辑B......");
            }else if("MT4101".equals(receipt.getType())){
                System.out.println("接收到MT4101的回执信息");
                System.out.println("解析回执内容");
                System.out.println("执行业务逻辑C......");
            }else if("MT8104".equals(receipt.getType())){
                System.out.println("接收到MT8104的回执信息");
                System.out.println("解析回执内容");
                System.out.println("执行业务逻辑D......");
            }

            //.........
        }
    }
}
