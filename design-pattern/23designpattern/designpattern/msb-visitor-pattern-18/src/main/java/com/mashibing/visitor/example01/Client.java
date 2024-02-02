package com.mashibing.visitor.example01;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author spikeCong
 * @date 2022/10/18
 **/
public class Client {

    public static void main(String[] args) {

//        Candy candy = new Candy("德芙巧克力", LocalDate.of(2022, 1, 1), 10.0);
//
//        Visitor visitor = new DiscountVisitor(LocalDate.of(2022,10,5));
//        visitor.visit(candy);

        //将3件商品加入购物车
//        List<Product> products = Arrays.asList(
//                new Candy("金丝猴奶糖",LocalDate.of(2022,10,1),10),
//                new Wine("郎酒",LocalDate.of(2022,10,1),1000),
//                new Fruit("草莓",LocalDate.of(2022,10,8),50,1)
//                );

//        Visitor visitor = new DiscountVisitor(LocalDate.of(2022,10,5));
//        for (Product product : products) {
//            //visitor.visit();
//        }

        //模拟添加多个商品
        List<Acceptable> list = Arrays.asList(
                new Candy("金丝猴奶糖",LocalDate.of(2022,10,1),10),
                new Wine("郎酒",LocalDate.of(2022,10,1),1000),
                new Fruit("草莓",LocalDate.of(2022,10,8),50,1)
                );

        Visitor visitor = new DiscountVisitor(LocalDate.of(2022,10,11));
        for (Acceptable product : list) {
            product.accept(visitor);
        }
    }
}
