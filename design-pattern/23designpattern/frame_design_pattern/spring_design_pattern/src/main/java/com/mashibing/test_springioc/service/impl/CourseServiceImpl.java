package com.mashibing.test_springioc.service.impl;

import com.mashibing.test_springioc.dao.CourseDao;
import com.mashibing.test_springioc.service.CourseService;

/**
 * @author spikeCong
 * @date 2022/10/28
 **/
public class CourseServiceImpl implements CourseService {

    public CourseServiceImpl() {
        System.out.println("CourseServiceImpl创建了......");
    }

    private CourseDao courseDao;

    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public void add() {
        System.out.println("CourseServiceImpl的add方法执行了......");
        courseDao.add();
    }
}
