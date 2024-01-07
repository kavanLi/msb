package com.msb;


import com.msb.pojo.Dept;
import com.msb.service.DeptService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@DisplayName("JUnit5断言")
class SpringbootMybatisplusApplicationTests4 {


    @DisplayName("简单断言1")
    @Test
    public void testAssertions1(){
        int x= add(1,2);
        Assertions.assertEquals(3,x,"add结果运算错误" );
    }
    public int add(int a,int b){
        return a+b;
    }

    @DisplayName("简单断言2")
    @Test
    public void testAssertions2(){
        String s ="aaa";
        String s2=new String("aaa");
        Assertions.assertSame(s,s2,"两个对象不一样");
    }

    @DisplayName("组合断言")
    @Test
    public void testAssertAll(){
        Assertions.assertAll("AssertAll",
                ()-> Assertions.assertTrue(1>0),
                ()-> Assertions.assertTrue(2>3)
                );
    }

    @DisplayName("异常断言")
    @Test // 断言它会出现指定的异常,如果没出现,则表示测试失败
    public void testAssertException(){
        Assertions.assertThrows(ArithmeticException.class, ()->{int i =1/1;}, "竟然没有出现异常");

    }

    @DisplayName("超时断言")
    @Test //
    public void testAssertTimeOut(){
        Assertions.assertTimeout(Duration.ofMillis(1000), ()->Thread.sleep(900));

    }

    @Autowired
    private DeptService deptService;

    @DisplayName("快速失败")
    @Test // 如果测试时出现了某些情况,直接生成测试失败的报告.后续也不再测试了
    public void testFail(){
        if(null == deptService){
            Assertions.fail("测试失败");
        }
        List<Dept> list = deptService.list();
        Assertions.assertTrue(list!=null && list.size()>0);

    }

}
