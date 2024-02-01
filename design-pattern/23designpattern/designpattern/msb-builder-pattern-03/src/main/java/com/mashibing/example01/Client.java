package com.mashibing.example01;

import com.mashibing.example01.builder.MobikeBuilder;
import com.mashibing.example01.director.Director;
import com.mashibing.example01.product.Bike;

/**
 * 客户端
 * @author spikeCong
 * @date 2022/9/19
 **/
public class Client {

    public static void main(String[] args) {

        //1.创建指挥者
        Director director = new Director(new MobikeBuilder());

        //2.获取自行车
        Bike bike = director.construct();
        System.out.println(bike.getFrame() + "," + bike.getSeat());
    }

}
