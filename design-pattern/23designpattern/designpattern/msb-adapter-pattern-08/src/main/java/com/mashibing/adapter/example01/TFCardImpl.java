package com.mashibing.adapter.example01;

/**
 * @author spikeCong
 * @date 2022/9/28
 **/
public class TFCardImpl implements TFCard {
    @Override
    public String readTF() {
        String msg = "tf card reading data";
        return msg;
    }

    @Override
    public void writeTF(String msg) {
        System.out.println("tf card write data : " + msg);
    }
}
