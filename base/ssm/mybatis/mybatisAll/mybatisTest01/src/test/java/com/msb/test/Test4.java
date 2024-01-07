package com.msb.test;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        sqlSession=factory.openSession(true);
    }

    @Test
    public void testInsert(){
        Emp emp =new Emp(null,"按住啦Baby","SALESMAN",7839,new Date(),3100.0, 200.0,10 );
        int rows = sqlSession.insert("addEmp", emp);
        System.out.println(rows);
        // 手动提交事务
        //sqlSession.commit();
        /*增删改 要提交事务
        * sqlSession.commit();手动提交事务
        * sqlSession=factory.openSession(true); 设置事务自动提交
        * */
    }

    @Test
    public void testUpdate(){
        Emp emp =new Emp( );
        emp.setEname("晓明");
        emp.setEmpno(7937);
        int rows = sqlSession.update("updateEmp", emp);
        System.out.println(rows);

    }

    @Test
    public void testDelete(){

        int rows = sqlSession.delete("deleteEmp", 7936);
        System.out.println(rows);

    }






    @After
    public void release(){
        // 关闭SQLSession
        sqlSession.close();
    }

}
