package com.example.demo.designpattern.cor.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 15:15
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestCor2 {
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
     示例二：权限控制
     假设我们有一个权限控制系统，需要根据用户的角色来判断用户是否具有访问某个资源的权限。我们可以使用责任链模式来实现这个功能。

     在这个例子中，我们定义了 Handler 抽象类，它定义了 handle() 方法用于判断用户是否有权限访问某个资源。然后，我们定义了两个具体的权限处理者：RoleHandler 和 ResourceHandler。它们分别负责根据用户角色和资源类型判断用户是否有权限。

     在 Main 类中，我们创建了一个权限处理链，其中包含 ResourceHandler 和 RoleHandler。然后，我们可以将用户角色和资源类型传递给链中的第一个处理者，它会依次将信息传递给下一个处理者，直到所有处理者都完成判断。最终，我们可以得到用户是否有权限访问该资源的结果。

     总结:

     责任链模式是一种常用的设计模式，它可以将请求的处理职责分解成多个独立的处理者，并将其组织成一条链。该模式可以提高系统的灵活性，并使请求的发送者和接收者之间解耦。

     其他:

     责任链模式可以应用于各种场景，例如消息处理、权限控制、过滤器链等。
     在实际应用中，责任链模式通常与其他设计模式一起使用，例如工厂模式、单例模式等。
     */
    public static void main(String[] args) {
        Handler handler = new ResourceHandler(new RoleHandler(null));
        // 用户角色为 "user"，资源类型为 "private"
        boolean hasPermission = handler.handle("user", "private");
        System.out.println("是否有权限：" + hasPermission);
    }

}

abstract class Handler {
    protected Handler nextHandler;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handle(String role, String resource);
}

class RoleHandler extends Handler {

    public RoleHandler(Handler nextHandler) {
        super(nextHandler);
    }

    @Override
    public boolean handle(String role, String resource) {
        // 根据用户角色判断是否有权限
        if ("admin".equals(role)) {
            return true;
        } else {
            if (nextHandler != null) {
                return nextHandler.handle(role, resource);
            }
        }
        return false;
    }
}

class ResourceHandler extends Handler {

    public ResourceHandler(Handler nextHandler) {
        super(nextHandler);
    }

    @Override
    public boolean handle(String role, String resource) {
        // 根据资源类型判断是否有权限
        if ("public".equals(resource)) {
            return true;
        } else {
            if (nextHandler != null) {
                return nextHandler.handle(role, resource);
            }
        }
        return false;
    }
}