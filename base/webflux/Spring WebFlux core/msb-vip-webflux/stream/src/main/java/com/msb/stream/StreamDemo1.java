package com.msb.stream;

import java.util.stream.IntStream;

public class StreamDemo1 {
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        // 外部迭代
        int sum1 = 0;
        for(int i : nums){
            sum1 += i;
        }
        System.out.println("结果为：" + sum1);
        // 使用Stream的内部迭代
        int sum2 = IntStream.of(nums).sum();
        System.out.println("结果为：" + sum2);
        // map 是中间操作 (返回Stream的操作)
        // sum 是终止操作
        int sum3 = IntStream.of(nums).map(StreamDemo1::doubleNum).sum();
        System.out.println("惰性求值就是最终没有调用的情况下，中间操作不会执行");
        IntStream.of(nums).map(StreamDemo1::doubleNum);
    }
    public static int doubleNum(int i){
        System.out.println("执行了乘以2");
        return i * 2;
    }

}
