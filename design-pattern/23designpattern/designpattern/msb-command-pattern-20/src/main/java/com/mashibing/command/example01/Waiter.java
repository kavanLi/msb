package com.mashibing.command.example01;

import java.util.ArrayList;

/**
 * 服务员 -> Invoker 调用者
 * @author spikeCong
 * @date 2022/10/20
 **/
public class Waiter {

    //可以持有多个命令对象
    private ArrayList<Command> commands;

    public Waiter() {
        this.commands = new ArrayList<>();
    }

    public Waiter(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public void setCommand(Command command) {
        this.commands.add(command);
    }

    //发出指令
    public void orderUp(){
        System.out.println("叮咚! 服务员: 有新的订单,请师傅开始制作......");
        for (Command command : commands) {
            if(command != null){
                command.execute();
            }
        }
    }
}
