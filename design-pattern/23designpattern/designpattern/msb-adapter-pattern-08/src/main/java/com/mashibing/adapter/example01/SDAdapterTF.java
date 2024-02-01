package com.mashibing.adapter.example01;

/**
 * 适配器类(SD兼容TF)
 * @author spikeCong
 * @date 2022/9/28
 **/
public class SDAdapterTF  extends TFCardImpl implements SDCard{


    @Override
    public String readSD() {
        System.out.println("adapter read tf card");
        return readTF();
    }

    @Override
    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        writeTF(msg);
    }
}
