package com.msb.lambda;

import java.util.stream.IntStream;

public class MinDemo {
    public static void main(String[] args) {
        int[] nums = {12,23,34,78,67,24};
        int min = Integer.MAX_VALUE;
        for(int i : nums){
            if(i < min){
                min = i;
            }
        }
        System.out.println(min);

        // jdk8
        int min2 = IntStream.of(nums).parallel().min().getAsInt();
        System.out.println(min2);
    }
}
