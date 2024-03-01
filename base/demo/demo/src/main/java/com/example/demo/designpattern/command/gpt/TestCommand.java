package com.example.demo.designpattern.command.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-01 10:10
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestCommand {
    /**
     * 命令模式
     * 命令模式是一种设计模式，它将请求封装成对象，从而使请求可以独立于请求的发送者和接收者而传递。
     *
     * 命令模式的核心思想是将请求和执行分离。请求由命令对象封装，执行由接收者对象负责。发送者对象只负责将命令对象传递给接收者对象**，接收者对象根据命令对象的内容执行相应的操作。
     *
     * 命令模式的优点:
     *
     * 解耦: 将请求和执行分离，使得代码更加灵活和易于维护。
     * 可重用: 可以将命令对象封装成可重用的组件。
     * 可扩展: 可以很容易地添加新的命令。
     * 命令模式的应用场景:
     *
     * 撤销/重做功能
     * 事务处理
     * 日志记录
     * 菜单/工具栏
     * 代码案例:
     *
     * 示例：遥控器控制电视
     *
     * 假设我们有一个遥控器，可以控制电视的开关、音量和频道。可以使用命令模式来实现这个功能，如下代码所示：
     */
    public static void main(String[] args) {
        TV tv = new TVImpl();

        // 创建命令对象
        OnCommand onCommand = new OnCommand(tv);
        OffCommand offCommand = new OffCommand(tv);
        SetVolumeCommand setVolumeCommand = new SetVolumeCommand(tv, 10);
        SetChannelCommand setChannelCommand = new SetChannelCommand(tv, 3);

        // 执行命令
        onCommand.execute();
        setVolumeCommand.execute();
        setChannelCommand.execute();
        offCommand.execute();
    }

}

interface TV {
    void on();

    void off();

    void setVolume(int volume);

    void setChannel(int channel);
}

abstract class TVCommand {
    protected TV tv;

    public TVCommand(TV tv) {
        this.tv = tv;
    }

    public abstract void execute();
}

class OnCommand extends TVCommand {
    public OnCommand(TV tv) {
        super(tv);
    }

    @Override
    public void execute() {
        tv.on();
    }
}

class OffCommand extends TVCommand {
    public OffCommand(TV tv) {
        super(tv);
    }

    @Override
    public void execute() {
        tv.off();
    }
}

class SetVolumeCommand extends TVCommand {
    private int volume;

    public SetVolumeCommand(TV tv, int volume) {
        super(tv);
        this.volume = volume;
    }

    @Override
    public void execute() {
        tv.setVolume(volume);
    }
}

class SetChannelCommand extends TVCommand {
    private int channel;

    public SetChannelCommand(TV tv, int channel) {
        super(tv);
        this.channel = channel;
    }

    @Override
    public void execute() {
        tv.setChannel(channel);
    }
}

class TVImpl implements TV {
    @Override
    public void on() {
        System.out.println("电视机开机");
    }

    @Override
    public void off() {
        System.out.println("电视机关机");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("设置音量为 " + volume);
    }

    @Override
    public void setChannel(int channel) {
        System.out.println("设置频道为 " + channel);
    }
}
