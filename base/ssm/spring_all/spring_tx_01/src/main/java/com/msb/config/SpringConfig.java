package com.msb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
/*@Configuration // 配置类的标志注解
@ComponentScan(basePackages = "com.msb") // spring组件扫描
@PropertySource("classpath:jdbc.properties")// 读取属性配置文件
@EnableTransactionManagement // 开启事务注解*/
public class SpringConfig {
    @Value("${jdbc_driver}")
    private String driver;
    @Value("${jdbc_username}")
    private String username;
    @Value("${jdbc_password}")
    private String password;
    @Value("${jdbc_url}")
    private String url;

    @Bean
    public DruidDataSource getDruidDataSource(){
        DruidDataSource dataSource=new DruidDataSource();
        dataSource.setPassword(password);
        dataSource.setUsername(username);
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate=new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager getPlatformTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager =new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
