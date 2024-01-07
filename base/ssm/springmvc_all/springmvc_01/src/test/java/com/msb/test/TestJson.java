package com.msb.test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msb.pojo.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class TestJson {

    @Test
    public void testA() throws  Exception{
        Pet pet =new Pet("Tom","cat");
        ObjectMapper om =new ObjectMapper();
        String asString = om.writeValueAsString(pet);
        System.out.println(asString);
        Consumer runnable = TestJson::aa;
    }

    private static void aa(Object o) {
    }

    public static void aa(int a) {
        System.out.println("Task instance created.");
    }


}
