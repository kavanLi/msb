package com.bobo.mp.dataSource.Interceptor;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-26 21:38
 * To change this template use File | Settings | File and Code Templates.
 */
@Intercepts(@Signature(type= Executor.class, method="query", args={MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}))
public class DataSourceInterceptor implements Interceptor, InnerInterceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        // 打印数据源
        SqlSession sqlSession = (SqlSession) invocation.getArgs()[0];

        // 从SqlSession中获取Configuration和Environment
        DataSource dataSource = sqlSession.getConfiguration().getEnvironment().getDataSource();
        System.out.println("Current DataSource: " + dataSource);
        printDatabaseInfo(dataSource);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    public void printDatabaseInfo(DataSource dataSource) {
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            DatabaseMetaData metaData = connection.getMetaData();

            String databaseUrl = metaData.getURL();
            String databaseUserName = metaData.getUserName();
            String databaseProductName = metaData.getDatabaseProductName();
            String databaseProductVersion = metaData.getDatabaseProductVersion();

            System.out.println("Interceptor Database URL: " + databaseUrl);
            System.out.println("Interceptor Username: " + databaseUserName);
            System.out.println("Interceptor Database Product: " + databaseProductName);
            System.out.println("Interceptor Database Version: " + databaseProductVersion);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean willDoQuery(org.apache.ibatis.executor.Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        return InnerInterceptor.super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public void beforeQuery(org.apache.ibatis.executor.Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }

    @Override
    public boolean willDoUpdate(org.apache.ibatis.executor.Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        return InnerInterceptor.super.willDoUpdate(executor, ms, parameter);
    }

    @Override
    public void beforeUpdate(org.apache.ibatis.executor.Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        InnerInterceptor.super.beforePrepare(sh, connection, transactionTimeout);
    }
}