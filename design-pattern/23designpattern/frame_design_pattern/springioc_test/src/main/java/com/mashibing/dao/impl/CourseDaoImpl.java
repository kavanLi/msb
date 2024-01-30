package com.mashibing.dao.impl;

import com.mashibing.dao.CourseDao;

/**
 * @author spikeCong
 * @date 2022/10/30
 **/
public class CourseDaoImpl implements CourseDao {

    //value注入
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseDaoImpl() {
        System.out.println("CourseDaoImpl创建了......");
    }

    @Override
    public void add() {
        System.out.println("CourseDaoImpl执行了......,课程名: " + courseName);
    }
}
