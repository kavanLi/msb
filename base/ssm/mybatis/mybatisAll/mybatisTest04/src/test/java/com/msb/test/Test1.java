package com.msb.test;

import com.msb.mapper.DeptMapper;
import com.msb.pojo.Dept;
import com.msb.pojo.Emp;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test1 {

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
    public void testFindDeptByDetpno()   {
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = deptMapper.findDeptByDeptno(20);
       // dept.getDeptno();

        // 暂时不需要员工信息
        List<Emp> empList = dept.getEmpList();
        empList.forEach(System.out::println);


    }

    @Test
    public void testAddDept()   {
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        deptMapper.addDept(new Dept(null, "总部", "北京"));
        sqlSession.commit();


    }

    @Test
    public void testUpdateDept()   {
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        deptMapper.updateDept(new Dept(43, "后勤", "北京文教园"));
        sqlSession.commit();
    }

    @Test
    public void testFindByDetpno()   {
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        Dept dept = deptMapper.findDeptByDeptno(43);
        System.out.println(dept);
    }


    @Test
    public void testRemoveDept()   {
        DeptMapper deptMapper = sqlSession.getMapper(DeptMapper.class);
        deptMapper.removeDept(43);
        sqlSession.commit();
    }


    @After
    public void release(){
        // 关闭SQLSession
        sqlSession.close();
    }

}
