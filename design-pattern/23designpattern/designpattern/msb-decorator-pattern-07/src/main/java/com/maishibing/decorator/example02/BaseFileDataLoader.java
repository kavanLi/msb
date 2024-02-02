package com.maishibing.decorator.example02;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 具体组件: 抽象文件读取接口的实现类
 * @author spikeCong
 * @date 2022/9/27
 **/
public class BaseFileDataLoader implements DataLoader{

    private String filePath;

    public BaseFileDataLoader(String filePath) {
        this.filePath = filePath;
    }

    //读
    public String read() {
        try {
            String result = FileUtils.readFileToString(new File(filePath), "utf-8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //写
    public void write(String data) {
        try {
            FileUtils.writeStringToFile(new File(filePath),data,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
