package com.mashibing.service.impl;

import com.mashibing.dao.CourseDao;
import com.mashibing.service.CourseService;

/**
 * @author spikeCong
 * @date 2022/10/30
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
        System.out.println("CourseServiceImpl执行了......");
        courseDao.add();
    }
}
