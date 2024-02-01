package com.mashibing.mediator.example02;

/**
 * 具体同事类-房租拥有者
 * @author spikeCong
 * @date 2022/10/21
 **/
public class HouseOwner extends Person {

    public HouseOwner(String name, Mediator mediator) {
        super(name, mediator);
    }

    //与中介联系
    public void contact(String message){
        mediator.contact(message,this);
    }

    //获取信息
    public void getMessage(String message){
        System.out.println("房主: " + name +",获取到的信息: " + message);
    }
}
