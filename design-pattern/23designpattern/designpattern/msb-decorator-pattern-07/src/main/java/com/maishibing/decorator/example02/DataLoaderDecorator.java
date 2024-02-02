package com.maishibing.decorator.example02;

/**
 * 抽象装饰者类
 * @author spikeCong
 * @date 2022/9/27
 **/
public class DataLoaderDecorator  implements DataLoader{

    private DataLoader dataLoader;

    public DataLoaderDecorator(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    public String read() {
        return dataLoader.read();
    }

    public void write(String data) {
        dataLoader.write(data);
    }
}
