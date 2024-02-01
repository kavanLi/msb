package com.maishibing.decorator.example01;

/**
 * 具体装饰类
 * @author spikeCong
 * @date 2022/9/27
 **/
public class ConcreteDecorator extends Decorator {

    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void operation() {
        super.operation(); //调用原有的业务方法
        add(); //调用新增的方法
    }

    //新增业务方法
    public void add(){
        //......
    }
}
