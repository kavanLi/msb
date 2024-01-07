package com.bobo.mp.dataSource.aspect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-26 21:07
 * To change this template use File | Settings | File and Code Templates.
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Before("execution(* com.bobo.mp.mapper.*.*(..))")
    public void before(JoinPoint point) {
        DataSource dataSource = sqlSessionTemplate.getConfiguration().getEnvironment().getDataSource();
        printDatabaseInfo(dataSource);
        log.info("Current DataSource: " + dataSource);
    }

    public void printDatabaseInfo(DataSource dataSource) {
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            DatabaseMetaData metaData = connection.getMetaData();

            String databaseUrl = metaData.getURL();
            String databaseUserName = metaData.getUserName();
            String databaseProductName = metaData.getDatabaseProductName();
            String databaseProductVersion = metaData.getDatabaseProductVersion();

            System.out.println("AOP Database URL: " + databaseUrl);
            System.out.println("AOP Username: " + databaseUserName);
            System.out.println("AOP Database Product: " + databaseProductName);
            System.out.println("AOP Database Version: " + databaseProductVersion);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}