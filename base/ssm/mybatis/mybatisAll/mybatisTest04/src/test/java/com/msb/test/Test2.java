package com.msb.test;

import com.msb.mapper.EmpMapper;
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
    public void testFindDeptByDetpno()   {
        EmpMapper mapper = sqlSession.getMapper(EmpMapper.class);
        Emp emp = mapper.findByEmpno(7521);
        System.out.println(emp);


        // 中间发生了增删改或者是调用了SqlSession调用了commit,会自动清空缓存
        sqlSession.commit();// 增删改的时候调用

        EmpMapper mapper2 = sqlSession.getMapper(EmpMapper.class);
        Emp emp2 = mapper2.findByEmpno(7521);
        System.out.println(emp2);

        System.out.println(emp==emp2);
        System.out.println(mapper==mapper2);

    }



    @After
    public void release(){
        // 关闭SQLSession
        sqlSession.close();
    }

}
