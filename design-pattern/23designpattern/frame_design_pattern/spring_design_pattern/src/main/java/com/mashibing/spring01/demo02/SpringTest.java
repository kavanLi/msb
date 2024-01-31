package com.mashibing.spring01.demo02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author spikeCong
 * @date 2022/10/25
 **/
public class SpringTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

//        StudentBean studentBean = (StudentBean) context.getBean("studentBean");
//        System.out.println(studentBean);

        //当用户使用容器本身时,可以使用转义符 "&" 来得到FactoryBean本身,以此来区分Factorybean产生的实例对象和FactoryBean本身
        StudentBean studentBean = (StudentBean) context.getBean("&studentBean");
        studentBean.study();

        TeacherBean teacherBean = (TeacherBean) context.getBean("studentBean");
        teacherBean.teach();
    }
}
