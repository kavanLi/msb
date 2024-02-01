package com.mashibing.command.example01;

/**
 * 厨师类 -> Receiver接收者角色
 * @author spikeCong
 * @date 2022/10/20
 **/
public class Chef {

    public void makeFood(int num , String foodName){
        System.out.println(num + "份, " + foodName);
    }
}
