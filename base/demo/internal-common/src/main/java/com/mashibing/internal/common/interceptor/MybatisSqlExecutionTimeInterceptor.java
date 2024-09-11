package com.mashibing.internal.common.interceptor;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: kavanLi-R7000
 * @create: 2024-08-15 15:26
 * To change this template use File | Settings | File and Code Templates.
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class MybatisSqlExecutionTimeInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisSqlExecutionTimeInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String sql = statementHandler.getBoundSql().getSql();
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            logger.info("SQL executed in {} ms: {}\n", executionTime, sql);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以根据需要设置一些配置属性
    }
}
