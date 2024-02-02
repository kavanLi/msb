package com.mashibing.command.example01;

/**
 * @author spikeCong
 * @date 2022/10/20
 **/
public class Client {

    public static void main(String[] args) {

        Order order1 = new Order();
        order1.setDiningTable(10);
        order1.getFoodMenu().put("鲍鱼炒饭",1);
        order1.getFoodMenu().put("海参炒面",1);

        Order order2 = new Order();
        order2.setDiningTable(15);
        order2.getFoodMenu().put("回锅肉盖饭",1);
        order2.getFoodMenu().put("木须肉盖饭",1);

        //创建接收者
        Chef chef = new Chef();

        //将订单和接收者封装成命令对象
        OrderCommand cmd1 = new OrderCommand(chef, order1);
        OrderCommand cmd2 = new OrderCommand(chef, order2);

        //创建调用者
        Waiter waiter = new Waiter();
        waiter.setCommand(cmd1);
        waiter.setCommand(cmd2);

        //将订单发送给厨师 上菜
        waiter.orderUp();
    }
}
