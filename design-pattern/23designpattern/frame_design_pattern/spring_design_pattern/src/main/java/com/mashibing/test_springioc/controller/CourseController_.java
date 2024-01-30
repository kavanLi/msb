package com.mashibing.test_springioc.controller;

import com.mashibing.framework.context.support.ClassPathXmlApplicationContext;
import com.mashibing.test_springioc.service.CourseService;

/**
 * @author spikeCong
 * @date 2022/10/28
 **/
public class CourseController_ {

    public static void main(String[] args) {
        //1.创建Spring的容器对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2.从容器对象中获取CourseService对象
        CourseService courseService = context.getBean("courseService", CourseService.class);

        //3.调用UserService的add方法
        courseService.add();
    }
}
