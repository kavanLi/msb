package com.mashibing.adapter.example01;

/**
 * @author spikeCong
 * @date 2022/9/28
 **/
public class Client {

    public static void main(String[] args) {

        Computer computer = new Computer();
        SDCard sdCard = new SDCardImpl();
        String read = computer.read(sdCard);
        System.out.println(read);

        System.out.println("=====================");
        SDAdapterTF adapterTF = new SDAdapterTF();
        System.out.println(computer.read(adapterTF));
    }
}
