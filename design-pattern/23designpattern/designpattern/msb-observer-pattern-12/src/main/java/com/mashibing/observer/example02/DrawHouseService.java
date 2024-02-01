package com.mashibing.observer.example02;

/**
 * 模拟买房摇号服务
 * @author spikeCong
 * @date 2022/10/12
 **/
public class DrawHouseService {

    //摇号抽签
    public String lots(String uId){
        if(uId.hashCode() % 2 == 0){
            return "恭喜ID为: " + uId + " 的用户,在本次摇号中中签!" ;
        }else{
            return "很遗憾,ID为: " + uId + "的用户,您本次未中签!" ;
        }
    }
}
