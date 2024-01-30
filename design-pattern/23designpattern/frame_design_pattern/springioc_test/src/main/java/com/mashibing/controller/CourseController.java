package com.mashibing.controller;

import com.mashibing.framework.context.support.ClassPathXmlApplicationContext;
import com.mashibing.service.CourseService;

/**
 * @author spikeCong
 * @date 2022/10/30
 **/
public class CourseController {

    public static void main(String[] args) {
        //1.创建Spring容器对象
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //2.从容器中获取CourseService对象
        CourseService courseService = context.getBean("courseService", CourseService.class);

        //3.调用CourseService的add方法
        courseService.add();
    }
}
