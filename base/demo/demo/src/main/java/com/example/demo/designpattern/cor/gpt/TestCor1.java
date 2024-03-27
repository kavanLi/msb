package com.example.demo.designpattern.cor.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 15:15
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestCor1 {
    /**
     * 责任链模式简介
     * 定义:
     *
     * 责任链模式是一种行为设计模式，它将请求沿着处理者链进行发送，直到其中一个处理者对其进行处理。该模式允许多个对象来对请求进行处理，而无需让发送者类与具体接收者类相耦合。链可在运行时由遵循标准处理者接口的任意处理者动态生成。
     *
     * 特点:
     *
     * 将请求的处理职责分解成多个独立的处理者。
     * 使请求的发送者和接收者之间解耦。
     * 提高系统的灵活性，可动态添加新的处理者。
     * 应用场景:
     *
     * 消息处理系统
     * 权限控制系统
     * 过滤器链
     *
     * 示例一：日志记录
     *
     * 假设我们有一个日志记录系统，需要将日志输出到不同的目标，例如文件、控制台和网络服务器。我们可以使用责任链模式来实现这个功能。
     *
     * 在这个例子中，我们定义了 Logger 抽象类，它定义了 log() 方法用于处理日志信息。然后，我们定义了三个具体的日志处理者：FileLogger、ConsoleLogger 和 HttpLogger。它们分别负责将日志写入文件、输出到控制台和发送到网络服务器。
     *
     * 在 Main 类中，我们创建了一个日志处理链，其中包含 FileLogger、ConsoleLogger 和 HttpLogger。然后，我们可以将日志信息传递给链中的第一个处理者，它会依次将日志信息传递给下一个处理者，直到所有处理者都完成处理。
     */
    public static void main(String[] args) {
        Logger logger = new HttpLogger(new ConsoleLogger(new FileLogger(null)));
        logger.log("这是一条日志信息");
    }
}

abstract class Logger {
    protected Logger nextLogger;

    public Logger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public abstract void log(String message);
}

class FileLogger extends Logger {

    public FileLogger(Logger nextLogger) {
        super(nextLogger);
    }

    @Override
    public void log(String message) {
        // 将日志写入文件
        System.out.println("将日志写入文件：" + message);
        if (nextLogger != null) {
            nextLogger.log(message);
        }
    }
}

class ConsoleLogger extends Logger {

    public ConsoleLogger(Logger nextLogger) {
        super(nextLogger);
    }

    @Override
    public void log(String message) {
        // 将日志输出到控制台
        System.out.println("将日志输出到控制台：" + message);
        if (nextLogger != null) {
            nextLogger.log(message);
        }
    }
}

class HttpLogger extends Logger {

    public HttpLogger(Logger nextLogger) {
        super(nextLogger);
    }

    @Override
    public void log(String message) {
        // 将日志发送到网络服务器
        System.out.println("将日志发送到网络服务器：" + message);
        if (nextLogger != null) {
            nextLogger.log(message);
        }
    }
}