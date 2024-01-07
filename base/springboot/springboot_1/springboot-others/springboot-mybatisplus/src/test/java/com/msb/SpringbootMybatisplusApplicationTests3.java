package com.msb;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@DisplayName("JUnit5常用注解测试类")
class SpringbootMybatisplusApplicationTests3 {

    @BeforeAll// 在所有的测试方法执行之前先执行一次
    public static void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll// 在所有的测试方法执行之后再执行一次
    public static void afterAll(){
        System.out.println("afterAll");
    }


    @BeforeEach// 在每一个测试方法之前先执行该方法
    public void beforeEach(){
        System.out.println("beforeEach");
    }

    @AfterEach// 在每一个测试方法之后先执行该方法
    public void afterEach(){
        System.out.println("afterEach");
    }

    @RepeatedTest(3)// 重复测试3次
    @Timeout(value = 1000,unit= TimeUnit.MICROSECONDS)
    @DisplayName("JUnit测试方法1")
    @Test
    public void test1() throws InterruptedException {
        System.out.println("a");

    }
    @Disabled
    @DisplayName("JUnit测试方法2")
    @Test
    public void test2(){
        System.out.println("b");
    }


}
