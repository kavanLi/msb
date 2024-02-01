package com.maishibing.decorator.example02;

/**
 * 抽象的文件读取接口
 * @author spikeCong
 * @date 2022/9/27
 **/
public interface DataLoader {

    String read();

    void write(String data);
}
