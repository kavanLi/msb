package com.msb;


import com.msb.pojo.Dept;
import com.msb.service.DeptService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;


@DisplayName("JUnit5前置条件")
class SpringbootMybatisplusApplicationTests5 {
    @DisplayName("测试前置条件")
    @Test
    public void testAssumptions(){
        // 假设的条件为true的时候才会执行测试,否则取消测试
        Assumptions.assumeTrue(false,"前置条件不成立");
        System.out.println("测试了");
    }

}
