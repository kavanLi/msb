package com.mashibing.internal.common.config.dataSource.config;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-25 19:53
 * To change this template use File | Settings | File and Code Templates.
 */

@Configuration
public class DataSourceConfig {
    /* fields -------------------------------------------------------------- */

    public enum DSKey {REPORT, CONFIG}

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

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "yunst.datasource.config")
    public DataSource configDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "yunst.datasource.report")
    public DataSource reportDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public AbstractRoutingDataSource routingDataSourceProxy(
            @Qualifier("reportDataSource") final DataSource reportDataSource,
            @Qualifier("configDataSource") final DataSource configDataSource
            ) {
        final AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                DSKey routeKey = DataSourceHolder.getRouteKey();
                // 打印切换的数据源
                //Map <Object, DataSource> resolvedDataSources = this.getResolvedDataSources();
                //printDatabaseInfo(resolvedDataSources.get(routeKey));
                return routeKey;
            }
        };

        final Map <Object, Object> dataSources = new HashMap <>();
        dataSources.put(DSKey.CONFIG, configDataSource);
        dataSources.put(DSKey.REPORT, reportDataSource);

        proxy.setTargetDataSources(dataSources);
        proxy.setDefaultTargetDataSource(configDataSource);
        // 很重要！！！ afterPropertiesSet()方法调用时用来将targetDataSources的属性写入resolvedDataSources中的
        proxy.afterPropertiesSet();
        return proxy;
    }

    public static class DataSourceHolder {

        private DataSourceHolder() {
        }

        /**
         * 注意 注意 注意！！！！ ThreadLocal是线程独有的，如果通过注解实现异步的执行执行数据操作，切面的SetKey会失效。导致使用Primary默认库
         */
        private static ThreadLocal <DSKey> routeKey = new ThreadLocal <>();

        public static void setRouteKey(final DSKey dsKey) {
            routeKey.remove();
            routeKey.set(dsKey);
        }

        public static DSKey getRouteKey() {
            return routeKey.get() == null ? DSKey.CONFIG : routeKey.get();
        }

        public static void clear() {
            routeKey.remove();
        }
    }

}
