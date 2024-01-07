package com.msb;

import com.msb.pojo.Emp;
import com.msb.service.EmpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */

@SpringBootTest(classes = Springboot03Application.class)
class Springboot03AppliactionTests {
    @Autowired
    private EmpService empService;

    @Autowired
    private Emp emp;

    @Test
    void testFindAll() {
        List<Emp> list = empService.findAll();
        list.forEach(System.out::println);
        System.out.println(emp);
    }
}

