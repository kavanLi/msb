package com.mashibing.adapter.example02;

import com.mashibing.adapter.example01.*;

/**
 * @author spikeCong
 * @date 2022/9/29
 **/
public class Client {

    public static void main(String[] args) {

        Computer computer = new Computer();
        SDCard sdCard = new SDCardImpl();
        System.out.println(computer.read(sdCard));

        System.out.println("=======================");
        TFCard tfCard = new TFCardImpl();

        SDAdapterTF2 adapterTF2 = new SDAdapterTF2(tfCard);
        System.out.println(computer.read(adapterTF2));
    }
}
