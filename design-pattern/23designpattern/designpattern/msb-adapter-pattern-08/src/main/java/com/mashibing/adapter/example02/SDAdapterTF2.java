package com.mashibing.adapter.example02;

import com.mashibing.adapter.example01.SDCard;
import com.mashibing.adapter.example01.TFCard;

/**
 * 对象适配器-组合形式
 * @author spikeCong
 * @date 2022/9/29
 **/
public class SDAdapterTF2  implements SDCard {

    private TFCard tfCard;

    public SDAdapterTF2(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    @Override
    public String readSD() {
        System.out.println("adapter read tf card");
        return tfCard.readTF();
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        tfCard.writeTF(msg);
    }
}
