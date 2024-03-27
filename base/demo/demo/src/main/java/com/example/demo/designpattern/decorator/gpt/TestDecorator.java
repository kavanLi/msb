package com.example.demo.designpattern.decorator.gpt;

/**
 * @author: kavanLi-R7000
 * @create: 2024-03-04 15:28
 * To change this template use File | Settings | File and Code Templates.
 */
public class TestDecorator {
    /**
     * 装饰器模式简介
     * 定义:
     *
     * 装饰器模式是一种设计模式，它允许在不改变对象自身结构的情况下，动态地向对象添加新的功能。装饰器模式通常通过将装饰器对象包装在被装饰对象周围来实现。
     *
     * 特点:
     *
     * 将对象的职责和功能解耦，提高代码的灵活性。
     * 可以在运行时动态地添加新的功能，无需修改原有代码。
     * 扩展对象功能的一种灵活方式，易于维护和扩展。
     * 应用场景:
     *
     * 日志记录
     * 权限控制
     * 缓存
     * 过滤器链
     * 代码案例:
     *
     实际上，装饰器模式可以被视为责任链模式的一种特殊形式。

     原因如下:

     在装饰器模式中，装饰器对象和被装饰对象通常都实现相同的接口。这使得装饰器对象可以无缝地替换被装饰对象，并提供额外的功能。
     在责任链模式中，处理者对象通常也实现相同的接口。这使得处理者对象可以链接在一起，形成一条处理链。
     因此，我们可以将装饰器模式理解为一种特殊的责任链模式，其中处理链只包含两个对象：装饰器对象和被装饰对象。



     示例：文本格式化

     假设我们有一个文本编辑器，需要提供不同的文本格式化功能，例如加粗、倾斜和下划线。我们可以使用装饰器模式来实现这个功能。

     */
    public static void main(String[] args) {
        TextComponent textComponent = new PlainText("Hello World");
        textComponent = new BoldDecorator(textComponent);
        textComponent = new ItalicDecorator(textComponent);
        textComponent = new UnderlineDecorator(textComponent);
        String formattedText = textComponent.getText();
        System.out.println(formattedText);
    }


}

interface TextComponent {
    String getText();
}

class PlainText implements TextComponent {

    private String text;

    public PlainText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}

abstract class Decorator implements TextComponent {

    protected TextComponent component;

    public Decorator(TextComponent component) {
        this.component = component;
    }

    @Override
    public abstract String getText();
}

class BoldDecorator extends Decorator {

    public BoldDecorator(TextComponent component) {
        super(component);
    }

    @Override
    public String getText() {
        return "<b>" + component.getText() + "</b>";
    }
}

class ItalicDecorator extends Decorator {

    public ItalicDecorator(TextComponent component) {
        super(component);
    }

    @Override
    public String getText() {
        return "<i>" + component.getText() + "</i>";
    }
}

class UnderlineDecorator extends Decorator {

    public UnderlineDecorator(TextComponent component) {
        super(component);
    }

    @Override
    public String getText() {
        return "<u>" + component.getText() + "</u>";
    }
}
