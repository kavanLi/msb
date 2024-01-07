package com.msb;


import org.junit.jupiter.api.*;


@DisplayName("JUnit5嵌套测试")
class SpringbootMybatisplusApplicationTests6 {


    @BeforeEach
    public void outerBeforEach(){
        System.out.println("outerBeforeEach");
    }

    @AfterEach
    public void outerAfterEach(){
        System.out.println("outerAfterEach");
    }
    @Test
    public void outerTest1(){
        System.out.println("outerTest");
    }

    @Nested
    @DisplayName("内部嵌套测试")
    class InnerClass{


        @BeforeEach
        public void innerBeforeEach(){
            System.out.println("innerBeforeEach");
        }
        @Test
        public void innerTest(){
            System.out.println("innerTest");
        }
        @AfterEach
        public void innerAfterEach(){
            System.out.println("innerAfterEach");
        }
    }


}
