package com.mashibing.facade.example02;

/**
 * @author spikeCong
 * @date 2022/10/5
 **/
public class Client {

    public static void main(String[] args) {

        //创建外观对象
        SmartAppliancesFacade facade = new SmartAppliancesFacade();

        facade.say("打开家电");
        facade.say("关闭家电");
    }
}
