package com.msb.test;

import com.msb.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@SpringJUnitConfig(locations = "classpath:applicationContext.xml")
public class Test1 {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testMapper(){
        userMapper.findUser("asdf", "asdfd");

    }


}
