package com.msb.test;

import com.msb.mapper.DeptMapper;
import com.msb.mapper.EmpMapper;
import com.msb.mapper.ProjectMapper;
import com.msb.pojo.Dept;
import com.msb.pojo.Emp;
import com.msb.pojo.Project;
import com.msb.pojo.ProjectRecord;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test2 {

    private SqlSession sqlSession;
    @Before
    public void init(){
        SqlSessionFactoryBuilder ssfb =new SqlSessionFactoryBuilder();
        InputStream resourceAsStream = null;
        try {
            resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory factory=ssfb.build(resourceAsStream) ;
        sqlSession=factory.openSession();
    }


    @Test
    public void testOneToOne() throws ParseException {
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.findEmpJoinDeptByEmpno(7499);
        System.out.println(emp);


    }

    @Test
    public void testOneToMany() throws ParseException {
        DeptMapper mapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = mapper.findDeptJoinEmpsByDeptno(20);
        System.out.println(dept);
        System.out.println("---------");
        List<Emp> empList = dept.getEmpList();
        empList.forEach(System.out::println);

    }

    @Test
    public void testManyToMany() throws ParseException {
        ProjectMapper mapper = sqlSession.getMapper(ProjectMapper.class);
        Project project = mapper.findProjectJoinEmpsByPid(2);
        System.out.println(project.getPid());
        System.out.println(project.getPname());
        System.out.println(project.getMoney());

        List<ProjectRecord> projectRecords = project.getProjectRecords();
        for (ProjectRecord projectRecord : projectRecords) {
            Emp emp = projectRecord.getEmp();
            System.out.println(emp);
        }

    }






    @After
    public void release(){
        // 关闭SQLSession
        sqlSession.close();
    }

}
