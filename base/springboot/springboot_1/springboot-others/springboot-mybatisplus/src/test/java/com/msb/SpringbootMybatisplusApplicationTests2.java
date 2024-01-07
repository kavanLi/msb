package com.msb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.msb.pojo.Dept;
import com.msb.service.DeptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//在测试 的时候可以导入spring中的一些组件
@SpringBootTest
class SpringbootMybatisplusApplicationTests2 {

    @Autowired
    private DeptService deptService;

    @Transactional
    @Test
    public void test1(){
        System.out.println(deptService);
    }


}
