package com.mashibing.adapter.example01;

/**
 * TF卡接口
 * @author spikeCong
 * @date 2022/9/28
 **/
public interface TFCard {

    //读取TF卡
    String readTF();

    //写入TF卡
    void writeTF(String msg);
}
