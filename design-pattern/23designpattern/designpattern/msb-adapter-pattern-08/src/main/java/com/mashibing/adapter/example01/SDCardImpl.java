package com.mashibing.adapter.example01;

/**
 * SD卡实现类
 * @author spikeCong
 * @date 2022/9/28
 **/
public class SDCardImpl implements SDCard {

    @Override
    public String readSD() {
        String msg = "sd card reading data";
        return msg;
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("sd card write data : " + msg);
    }
}
