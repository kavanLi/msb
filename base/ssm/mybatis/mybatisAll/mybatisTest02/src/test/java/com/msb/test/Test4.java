package com.msb.test;

import com.msb.mapper.EmpMapper;
import com.msb.mapper.EmpMapper2;
import com.msb.pojo.Emp;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
public class Test4 {

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
    public void testFindByConditon() throws ParseException {
        EmpMapper2 mapper = sqlSession.getMapper(EmpMapper2.class);
        Emp emp =new Emp();
        emp.setEname("A");
        //emp.setEmpno(7521);
        //emp.setSal(3000.0);
        //emp.setHiredate(new SimpleDateFormat("yyyy-MM-dd").parse("1981-04-02"));
        List<Emp> emps = mapper.findByCondition(emp);
        for (Emp emp1 : emps) {
            System.out.println(emp1);
        }
    }

    @Test
    public void testFindByConditon2() throws ParseException {
        EmpMapper2 mapper = sqlSession.getMapper(EmpMapper2.class);
        Emp emp =new Emp();
        emp.setEname("A");
        emp.setEmpno(7521);
        //emp.setSal(3000.0);
        //emp.setHiredate(new SimpleDateFormat("yyyy-MM-dd").parse("1981-04-02"));
        List<Emp> emps = mapper.findByCondition2(emp);
        for (Emp emp1 : emps) {
            System.out.println(emp1);
        }
    }

    @Test
    public void testUpdateEmpByCondition() throws ParseException {
        EmpMapper2 mapper = sqlSession.getMapper(EmpMapper2.class);
        Emp emp =new Emp();
        emp.setEmpno(7521);
        //emp.setEname("TOM");
        //emp.setSal(2350.0);
        //emp.setHiredate(new SimpleDateFormat("yyyy-MM-dd").parse("1984-04-02"));
        emp.setDeptno(20);
        mapper.updateEmpByCondtion(emp);
        sqlSession.commit();
    }

    @Test
    public void testUpdateEmpByCondition2() throws ParseException {
        EmpMapper2 mapper = sqlSession.getMapper(EmpMapper2.class);
        Emp emp =new Emp();
        emp.setEmpno(7521);
        emp.setEname("TOM");
        //emp.setSal(2350.0);
        //emp.setHiredate(new SimpleDateFormat("yyyy-MM-dd").parse("1984-04-02"));
        emp.setDeptno(20);
        mapper.updateEmpByCondtion(emp);
        sqlSession.commit();
    }

    @Test
    public void testFindByEmpnos() throws ParseException {
        EmpMapper2 mapper = sqlSession.getMapper(EmpMapper2.class);
        //List<Emp> emps = mapper.findByEmpnos1(new int[]{7521, 7839, 7499});
        List<Integer> empnos=new ArrayList<>();
        Collections.addAll(empnos,7521, 7839, 7499);
        List<Emp> emps = mapper.findByEmpnos2(empnos);
        emps.forEach(System.out::println);
    }





    @After
    public void release(){
        // 关闭SQLSession
        sqlSession.close();
    }

}
