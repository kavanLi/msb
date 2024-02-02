package com.maishibing.decorator.example02;

/**
 * @author spikeCong
 * @date 2022/9/27
 **/
public class Client {

    public static void main(String[] args) {

        String info = "name:tom,age:30";

//        DataLoader loader = new BaseFileDataLoader("demo.txt");
//        loader.write(info);
//
//        String read = loader.read();
//        System.out.println(read);
          DataLoaderDecorator decorator = new EncryptionDataDecorator(new BaseFileDataLoader("demo2.txt"));
          //decorator.write(info);
        String read = decorator.read();
        System.out.println(read);
    }
}
