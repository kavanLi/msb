package com.mashibing.templtemethod.example02;

/**
 * 账户抽象类
 * @author spikeCong
 * @date 2022/10/12
 **/
public abstract  class Account {

    //step1 具体方法-验证用户信息是否正确
    public boolean validate(String account,String password){
        System.out.println("账号: " + account + ",密码: " + password);
        if(account.equalsIgnoreCase("tom") && password.equalsIgnoreCase("123456")){
            return true;
        }else{
            return false;
        }
    }

    //step2 抽象方法-计算利息
    public abstract void calculate();

    //step3 具体方法-显示利息
    public void display(){
        System.out.println("显示利息!");
    }

    //模板方法
    public void handle(String account,String password){
        if(!validate(account,password)){
            System.out.println("账户或者密码错误!");
            return;
        }

        calculate();
        display();
    }
}
