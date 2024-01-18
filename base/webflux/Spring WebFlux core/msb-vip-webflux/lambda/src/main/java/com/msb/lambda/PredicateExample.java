package com.msb.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class PredicateExample {
    public static boolean isNullOrEmpty(Map map) {

        //这里需要指定范型，这里是Map，指定好了范型后，方法里面才能用Map对象的方法
        Predicate<Map> predicate = (map1)->{
            if (null == map1 || 0 == map1.size()) {
                System.out.println("map 的 size is 0");
                return true;
            } else {
                return false;
            }
        };
        return predicate.test(map);
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        //使用predicate方法
        System.out.println( isNullOrEmpty(map));
    }
}
