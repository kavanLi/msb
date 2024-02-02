package com.mashibing.strategy.example02;

import java.util.ArrayList;
import java.util.List;

/**
 * 回执信息生成类
 * @author spikeCong
 * @date 2022/10/13
 **/
public class ReceiptBuilder {

    public static List<Receipt> getReceiptList(){

        //模拟回执信息
        List<Receipt> list = new ArrayList<>();
        //MT1101、MT2101、MT4101、MT8104
        list.add(new Receipt("MT1101回执信息","MT1101"));
//        list.add(new Receipt("MT2101回执信息","MT2101"));
//        list.add(new Receipt("MT4101回执信息","MT4101"));
//        list.add(new Receipt("MT8104回执信息","MT8104"));

        //......
        return list;
    }

}
