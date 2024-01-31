package com.mashibing;

import com.mashibing.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author spikeCong
 * @date 2022/10/29
 **/
public class MainApp {

    public static void main(String[] args) throws IOException {

        // 加载核心配置文件
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");

        // 获取SqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        // 获取SqlSession会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 执行sql
//        List<User> list = sqlSession.selectList("com.mashibing.mapper.UserMapper.findAll");
//        for (User user : list) {
//            System.out.println(user);
//        }

        User user = sqlSession.selectOne("com.mashibing.mapper.UserMapper.findById", 1);
        System.out.println(user);

        sqlSession.delete("com.mashibing.mapper.UserMapper.deleteById",3);
        sqlSession.commit();

        User user2 = sqlSession.selectOne("com.mashibing.mapper.UserMapper.findById", 1);
        System.out.println(user2);

        System.out.println(user == user2);

        // 释放资源
        sqlSession.close();
    }
}
