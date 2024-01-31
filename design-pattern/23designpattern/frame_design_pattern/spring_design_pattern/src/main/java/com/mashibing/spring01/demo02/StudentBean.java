package com.mashibing.spring01.demo02;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 生产自定义bean
 * @author spikeCong
 * @date 2022/10/25
 **/
@Component("studentBean")
public class StudentBean implements FactoryBean {


    //返回工厂中的实例
    @Override
    public Object getObject() throws Exception {

        //return new StudentBean();
        //这里返回的不一定是自身的实例,可以是任何对象的实例
        return new TeacherBean();
    }

    //该方法返回的是在IOC容器中getBean所匹配的类型
    @Override
    public Class<?> getObjectType() {
        return StudentBean.class;
    }

    public void study(){
        System.out.println("学生正在学习......");
    }
}
