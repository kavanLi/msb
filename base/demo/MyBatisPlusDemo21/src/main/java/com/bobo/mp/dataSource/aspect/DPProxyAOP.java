package com.bobo.mp.dataSource.aspect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.bobo.mp.dataSource.config.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-25 20:08
 * To change this template use File | Settings | File and Code Templates.
 */

@Aspect
@Component
@Slf4j
public class DPProxyAOP {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApplicationContext ctx;

    public void printDatabaseInfo(DataSource dataSource) {
        try (Connection connection = DataSourceUtils.getConnection(dataSource)) {
            DatabaseMetaData metaData = connection.getMetaData();

            String databaseUrl = metaData.getURL();
            String databaseUserName = metaData.getUserName();
            String databaseProductName = metaData.getDatabaseProductName();
            String databaseProductVersion = metaData.getDatabaseProductVersion();

            System.out.println("Database URL: " + databaseUrl);
            System.out.println("Username: " + databaseUserName);
            System.out.println("Database Product: " + databaseProductName);
            System.out.println("Database Version: " + databaseProductVersion);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Around("@annotation(com.bobo.mp.dataSource.annotation.ConfigDB)")
    public Object choosePrimaryDataSource(final ProceedingJoinPoint pjp) throws Throwable {
        log.info("切换到ConfigDB");
        DataSourceConfig.DataSourceHolder.setRouteKey(DataSourceConfig.DSKey.CONFIG);
        final Object result = pjp.proceed();
        DataSourceConfig.DataSourceHolder.clear();
        return result;
    }

    @Around("@annotation(com.bobo.mp.dataSource.annotation.ReportDB)")
    public Object chooseReportDataSource(final ProceedingJoinPoint pjp) throws Throwable {
        log.info("切换到ReportDB");
        DataSourceConfig.DataSourceHolder.setRouteKey(DataSourceConfig.DSKey.REPORT);
        final Object result = pjp.proceed();
        DataSourceConfig.DataSourceHolder.clear();
        return result;
    }

}
