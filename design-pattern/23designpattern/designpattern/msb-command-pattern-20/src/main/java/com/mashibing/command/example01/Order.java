package com.mashibing.command.example01;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单类
 * @author spikeCong
 * @date 2022/10/20
 **/
public class Order {

    private int diningTable;  //餐桌号码

    private Map<String,Integer> foodMenu = new HashMap<>();  //存储菜名和份数

    public int getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(int diningTable) {
        this.diningTable = diningTable;
    }

    public Map<String, Integer> getFoodMenu() {
        return foodMenu;
    }

    public void setFoodMenu(Map<String, Integer> foodMenu) {
        this.foodMenu = foodMenu;
    }
}
