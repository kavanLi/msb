package com.msb.test;

import com.msb.mapper.EmpMapper;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void testFindAll(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);
        List<Emp> emps = empMapper.findAll();
        emps.forEach(System.out::println);
    }

    @Test
    public void testFindByEmpno(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);
        Emp emp = empMapper.findByEmpno(7499);
        System.out.println(emp);
    }

    @Test
    public void testFindByDeptnoAndSal(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);

        List<Emp> emps = empMapper.findByDeptnoAndSal(20,3000.0);
        emps.forEach(System.out::println);

    }
    @Test
    public void testFindByDeptnoAndSal2(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);
        Map<String,Object> map=new HashMap<>();
        map.put("deptno", 20);
        map.put("sal", 3000.0);
        List<Emp> emps = empMapper.findByDeptnoAndSal2(map);
        emps.forEach(System.out::println);

    }

    @Test
    public void testFindByDeptnoAndSal3(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);
        Emp emp=new Emp();
        emp.setDeptno(20);
        emp.setSal(3000.0);
        List<Emp> emps = empMapper.findByDeptnoAndSal3(emp);
        emps.forEach(System.out::println);

    }

    @Test
    public void testFindByDeptnoAndSal4(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);
        Emp empa=new Emp();
        empa.setDeptno(10);
        Emp empb=new Emp();
        empb.setSal(3000.0);
        List<Emp> emps = empMapper.findByDeptnoAndSal4(empa,empb);
        emps.forEach(System.out::println);

    }

    @Test
    public void testFindByEname(){
        EmpMapper empMapper=sqlSession.getMapper(EmpMapper.class);
        List<Emp> emps = empMapper.findByEname("a");
        emps.forEach(System.out::println);

    }


    @After
    public void release(){
        // 关闭SQLSession
        sqlSession.close();
    }

}
