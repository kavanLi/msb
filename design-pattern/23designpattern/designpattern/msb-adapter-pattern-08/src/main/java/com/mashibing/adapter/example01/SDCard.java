package com.mashibing.adapter.example01;

/**
 * SD卡接口
 * @author spikeCong
 * @date 2022/9/28
 **/
public interface SDCard {

    //读取SD卡
    String readSD();

    //写入SD卡
    void writeSD(String msg);
}
