package com.mashibing.mediator.example02;

/**
 * @author spikeCong
 * @date 2022/10/21
 **/
public class Client {

    public static void main(String[] args) {

        //中介机构
        MediatorStructure mediator =  new MediatorStructure();

        //房主
        HouseOwner houseOwner = new HouseOwner("张三", mediator);

        //租房者
        Tenant tenant = new Tenant("李四", mediator);

        //中介收集房主及租房者信息
        mediator.setHouseOwner(houseOwner);
        mediator.setTenant(tenant);

        //租房人的需求
        tenant.contact("需要在天通苑附近找一个,两室一厅的房子一家人住,房租在5000~6000之间");

        //房主的需求
        houseOwner.contact("出租一套天通苑地跌站附近的两室一厅,房租6000,可谈");
    }
}
