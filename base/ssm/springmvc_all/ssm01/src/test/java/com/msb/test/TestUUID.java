package com.msb.test;

import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class TestUUID {

    @Test
    public void getUUID(){
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        System.out.println(s);

        //   灯塔.jpg
        //   72f3ab5f-dc70-4760-9eb4-804c39eb6722
        //   72f3ab5f-dc70-4760-9eb4-804c39eb6722.jpg

    }
}
