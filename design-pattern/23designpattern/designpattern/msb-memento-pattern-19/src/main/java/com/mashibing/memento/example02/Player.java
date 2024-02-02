package com.mashibing.memento.example02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 玩家类
 * @author spikeCong
 * @date 2022/10/19
 **/
public class Player {

    private int money;  //金币

    private List<String> fruits = new ArrayList<>();  //玩家获得的水果

    private static String[] fruitsName ={   //表示水果种类的数组
            "苹果","葡萄","香蕉","橘子"
    };

    Random random = new Random();

    public Player(int money) {
        this.money = money;
    }

    //获取当前所有金币
    public int getMoney(){
        return money;
    }

    //获取一个水果
    public String getFruit(){
        String prefix = "";
        if(random.nextBoolean()){
            prefix = "好吃的";
        }

        //从数组中拿一个水果
        String f = fruitsName[random.nextInt(fruitsName.length)];
        return prefix + f;
    }

    //掷骰子方法
    public void yacht(){

        int dice = random.nextInt(6) + 1; //掷骰子

        if(dice == 1){
            money += 100;
            System.out.println("所持有的金币增加了...");
        }else if(dice == 2){
            money /= 2;
            System.out.println("所持有的金币减少一半");
        }else if(dice == 6){ //获取水果
            String fruit = getFruit();
            System.out.println("获取了水果: " + fruit);
            fruits.add(fruit);
        }else{
            //其他结果
            System.out.println("无效数字,继续投掷!");
        }
    }

    //拍摄快照
    public Memento createMemento(){
        Memento memento = new Memento(money);
        for (String fruit : fruits) {

            //判断: 只保存 '好吃的'水果
            if(fruit.startsWith("好吃的")){
                memento.addFruit(fruit);
            }
        }

        return memento;
    }


    //撤销方法
    public void restoreMemento(Memento memento){
        this.money = memento.getMoney();
        this.fruits = memento.getFruits();
    }


    @Override
    public String toString() {
        return "Player{" +
                "money=" + money +
                ", fruits=" + fruits +
                '}';
    }
}
