package com.mashibing.strategy.example03;

import com.mashibing.strategy.example02.Receipt;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂类
 * @author spikeCong
 * @date 2022/10/13
 **/
public class ReceiptHandleStrategyFactory {

    public ReceiptHandleStrategyFactory() {
    }

    //使用Map集合存储策略信息,彻底的消除if...else
    private static Map<String,ReceiptHandleStrategy> strategyMap;

    //初始化具体策略,保存到map集合
    public static void init(){
        strategyMap = new HashMap<>();
//        strategyMap.put("MT1101",new MT1101ReceiptHandleStrategy());
//        strategyMap.put("MT2101",new MT2101ReceiptHandleStrategy());
        try {
            SAXReader reader = new SAXReader();
            String file = "I:\\MSB\\msb_work\\designpattern\\msb-strategy-pattern-14\\src\\main\\resources\\config.xml";

            Document document = reader.read(file);
            Node node = document.selectSingleNode("/confing/className");
            String className = node.getText();
            Class clazz = Class.forName(className);
            ReceiptHandleStrategy strategy = (ReceiptHandleStrategy) clazz.newInstance();
            strategyMap.put("MT1101",strategy);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //根据回执类型,获取对应的策略对象
    public static ReceiptHandleStrategy getStrategy(String receiptType){
        return strategyMap.get(receiptType);
    }
}
