package com.msb;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@DisplayName("JUnit5参数化测试")
class SpringbootMybatisplusApplicationTests7 {

    @ParameterizedTest
    @ValueSource(ints ={1,2,3,4,5,6,7})
    public void testParam1(int a){
        Assertions.assertTrue(a>0 && a<4);
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    public void testParam2(String str){
        Assertions.assertNotNull(str,"str为null了,测试失败");
    }


    public static Stream<String> stringProvider(){
        List<String> strs =new ArrayList<>();
        strs.add("a");
        strs.add("b");
        strs.add("c");
        strs.add(null);
        Stream<String> stream = strs.stream();
        return stream;

    }

}
